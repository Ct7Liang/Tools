package android.develop.ct7liang.tools.module_widget;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.base.BaseActivity;
import android.develop.ct7liang.tools.bean.TextBean;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ct7liang.tangyuan.utils.ToastUtils;
import com.ct7liang.tangyuan.view_titlebar.TitleBarView;
import java.util.List;
import tools.greendao.gen.GreenDaoHelper;
import tools.greendao.gen.TextBeanDao;

public class WidgetSettingActivity extends BaseActivity {

    private EditText editText;
    private EditText editText1;
    private TextBeanDao textBeanDao;
    private List<TextBean> textBeens;
    private ListView listView;
    private MyAdapter adapter;

    @Override
    public int setLayout() {
        return R.layout.activity_widget_setting;
    }

    @Override
    public void findView() {
        initStatusBar();
    }

    @Override
    protected void setStatusBar() {
        TitleBarView titleBarView = findViewById(R.id.titleBarView);
        titleBarView.setStatusBar(this);
    }

    @Override
    public void initData() {
        textBeanDao = GreenDaoHelper.getDaoSession().getTextBeanDao();
        textBeens = textBeanDao.loadAll();
    }

    @Override
    public void initView() {
        editText = findViewById(R.id.edit);
        editText1 = findViewById(R.id.edit1);
        findViewById(R.id.btn).setOnClickListener(this);
        listView = findViewById(R.id.list_view);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mAct);
                builder.setTitle("是否删除");
                final int position = i;
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        textBeanDao.delete(textBeens.get(position));
                        textBeens.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(textBeens.get(i).content);
                editText1.setText(textBeens.get(i).title);
                return true;
            }
        });
    }

    @Override
    public void initFinish() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
                String s = editText.getText().toString();
                String s1 = editText1.getText().toString();
                if (TextUtils.isEmpty(s)){
                    return;
                }
                TextBean textBean = new TextBean(null, s, s1);
                textBeanDao.insert(textBean);
                textBeens.add(textBean);
                adapter.notifyDataSetChanged();
                ToastUtils.showStatic(mAct, "插入数据成功");
                editText.setText("");
                editText1.setText("");
                break;
        }
    }

    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return textBeens.size();
        }
        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = null;
            if (view == null){
                v = View.inflate(mAct, R.layout.item_app_text_widget, null);
            }else{
                v = view;
            }
            ((TextView)v.findViewById(R.id.text)).setText(textBeens.get(i).content);
            String title = textBeens.get(i).title;
            if (TextUtils.isEmpty(title)){
                v.findViewById(R.id.tag).setVisibility(View.GONE);
            }else{
                v.findViewById(R.id.tag).setVisibility(View.VISIBLE);
                ((TextView)v.findViewById(R.id.text1)).setText(title);
            }
            return v;
        }
    }

}