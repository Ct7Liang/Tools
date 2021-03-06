package android.develop.ct7liang.tools.module_check;

import android.app.Dialog;
import android.content.Intent;
import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.base.BaseActivity;
import android.develop.ct7liang.tools.bean.Check;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ct7liang.tangyuan.recyclerview.OnItemLongClickListener;
import com.ct7liang.tangyuan.view_titlebar.TitleBarView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tools.greendao.gen.CheckDao;
import tools.greendao.gen.GreenDaoHelper;

public class CheckActivity extends BaseActivity implements OnItemLongClickListener {

    private RecyclerView recyclerView;
    private CheckDao checkDao;
    //账单集合
    private List<Check> checks;
    private CheckAdapter checkAdapter;
    private Dialog dialog;
    private Dialog dialog_delete;
    //标记,是否是收入
    private boolean isComing = false;
    private TextView type;
    private EditText cash;
    private EditText desc;
    private TextView total;
    //当前月份账单集合

    @Override
    public int setLayout() {
        return R.layout.activity_check;
    }

    @Override
    public void findView() {
        initStatusBar();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        total = (TextView) findViewById(R.id.total);
        total.setOnClickListener(this);
    }

    @Override
    protected void setStatusBar() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titleBarView);
        titleBarView.setStatusBar(this);
        titleBarView.setOnRightImgClick(new TitleBarView.OnRightImgClick() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
    }

    @Override
    public void initData() {
        checkDao = GreenDaoHelper.getDaoSession().getCheckDao();
        checks = checkDao.loadAll();

//        File file = new File(AppFolder.get().getPath(), "check.txt");
//        String read = FileUtils.read(file);
//        read = "{\"datas\":" + read + "}";
//        Checks cs = new Gson().fromJson(read, Checks.class);
//        for (int i = 0; i < cs.getDatas().size(); i++) {
//            checkDao.insert(cs.getDatas().get(i));
//        }
//        checks.addAll(cs.getDatas());

        checkAdapter = new CheckAdapter(this, checks);
        checkAdapter.setOnItemLongClickListener(this);
        recyclerView.setAdapter(checkAdapter);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initFinish() {
        getTotal();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_type:
                isComing = !isComing;
                type.setText(isComing?"收入":"支出");
                break;
            case R.id.commit:
                String cash_str = cash.getText().toString().trim();
                if (TextUtils.isEmpty(cash_str)){
                    return;
                }
                String desc_str = desc.getText().toString().trim();
                if (TextUtils.isEmpty(desc_str)){
                    desc_str = "暂无备注";
                }
                Date date = new Date();
                String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(date);
                String format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(date);
                String[] split = format1.split("-");
                int type = isComing?1:0;
                Check c = new Check(null, Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), type, Float.valueOf(cash_str), desc_str, format);
                checkDao.insert(c);
                checks.add(c);
                dialog.dismiss();
                checkAdapter.notifyDataSetChanged();
                getTotal();
                break;
            case R.id.total:
                startActivity(new Intent(this, MonthCheckActivity.class));
                break;
        }
    }

    private int onLongClickPoi;
    @Override
    public void onLongClick(View view, int i) {
        onLongClickPoi = i;
        showDeleteDialog(i);
    }

    /**
     * 显示添加账单弹窗
     */
    private void showAddDialog(){
        if (dialog==null){
            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            View contentView = View.inflate(this, R.layout.dialog_add_check_item, null);
            cash = (EditText) contentView.findViewById(R.id.et_cash);
            desc = (EditText) contentView.findViewById(R.id.et_desc);
            type = (TextView) contentView.findViewById(R.id.tv_type);
            type.setOnClickListener(this);
            contentView.findViewById(R.id.commit).setOnClickListener(this);
            dialog.addContentView(contentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        dialog.show();
    }

    /**
     * 显示确认删除账单弹窗
     */
    private void showDeleteDialog(final int position){
        if (dialog_delete==null){
            dialog_delete = new Dialog(this);
            dialog_delete.requestWindowFeature(Window.FEATURE_NO_TITLE);
            View contentView = View.inflate(this, R.layout.dialog_delete_check_item, null);
            contentView.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkDao.delete(checks.get(onLongClickPoi));
                    dialog_delete.dismiss();
                    checks.remove(onLongClickPoi);
                    checkAdapter.notifyDataSetChanged();
                    getTotal();
                }
            });
            contentView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog_delete.dismiss();
                }
            });
            dialog_delete.addContentView(contentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog_delete.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        dialog_delete.show();
    }

    /**
     * 获取当月总金额
     */
    private void getTotal() {
        if (checks.size()!=0){
            int tag =  checks.get(checks.size() - 1).getMonth();
            float totalnum = 0;
            for (int i = checks.size()-1; i > -1; i--) {
                Check check = checks.get(i);
                int month = check.getMonth();
                if (tag != month){
                    break;
                }
                if (check.getType()==1){
                    totalnum += check.getCash();
                }else{
                    totalnum -= check.getCash();
                }
            }
            total.setText(String.valueOf(tag + "月: " + totalnum));
        }else{
            total.setText(String.valueOf("暂无数据"));
        }
    }

}
