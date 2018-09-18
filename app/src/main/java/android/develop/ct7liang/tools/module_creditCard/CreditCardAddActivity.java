package android.develop.ct7liang.tools.module_creditCard;

import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.base.BaseActivity;
import android.develop.ct7liang.tools.bean.CreditCardBean;
import android.develop.ct7liang.tools.module_creditCard.ziyuan.CreditInfo;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ct7liang.tangyuan.utils.ScreenUtil;
import com.ct7liang.tangyuan.utils.ToastUtils;
import com.ct7liang.tangyuan.view_titlebar.TitleBarView;

import java.util.ArrayList;
import java.util.List;

import tools.greendao.gen.CreditCardBeanDao;
import tools.greendao.gen.GreenDaoHelper;

public class CreditCardAddActivity extends BaseActivity {

    private PopupWindow popupWindow;
    private TextView bank_name;
    private ImageView bank_icon;
    private CreditInfo creditInfo;
    private EditText cardNum;
    private EditText startDay;
    private EditText endDay;
    private EditText returnDay;
    private EditText cardYear;
    private EditText cardMonth;
    private int position = -1;
    private CreditCardBeanDao creditCardBeanDao;
    private ArrayList<String> numList;

    @Override
    public int setLayout() {
        return R.layout.activity_credit_card_add;
    }

    @Override
    public void findView() {
        initStatusBar();
        bank_name = findViewById(R.id.bank_name);
        bank_icon = findViewById(R.id.bank_icon);
        cardNum = findViewById(R.id.cardNum);
        startDay = findViewById(R.id.startDay);
        endDay = findViewById(R.id.endDay);
        returnDay = findViewById(R.id.returnDay);
        cardYear = findViewById(R.id.cardYear);
        cardMonth = findViewById(R.id.cardMonth);
    }

    @Override
    protected void setStatusBar() {
        TitleBarView titleBarView = findViewById(R.id.titleBarView);
        titleBarView.setStatusBar(this);
    }

    @Override
    public void initData() {
        creditInfo = CreditInfo.getInstance();
        creditCardBeanDao = GreenDaoHelper.getDaoSession().getCreditCardBeanDao();
        List<CreditCardBean> creditCardBeen = creditCardBeanDao.loadAll();
        numList = new ArrayList<>();
        for (int i = 0; i < creditCardBeen.size(); i++) {
            numList.add(creditCardBeen.get(i).cardNum);
        }
    }

    @Override
    public void initView() {
        bank_name.setOnClickListener(this);
        findViewById(R.id.commit).setOnClickListener(this);
    }

    @Override
    public void initFinish() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bank_name:
                showPopupWindow();
                break;
            case R.id.commit:
                String num = cardNum.getText().toString();
                String dayStart = startDay.getText().toString();
                String dayEnd = endDay.getText().toString();
                String dayReturn = returnDay.getText().toString();
                String endYear = cardYear.getText().toString();
                String endMonth = cardMonth.getText().toString();
                if (TextUtils.isEmpty(num)||TextUtils.isEmpty(dayStart)||TextUtils.isEmpty(dayEnd)||
                        TextUtils.isEmpty(dayReturn)||TextUtils.isEmpty(endYear)
                        ||TextUtils.isEmpty(endMonth)||position==-1){
                    ToastUtils.showStatic(mAct, "请把信息补充完整");
                    return;
                }
                if (numList.contains(num)){
                    ToastUtils.showStatic(mAct, "此卡已存在");
                    return;
                }
                CreditCardBean cardBean = new CreditCardBean(
                        null, creditInfo.tag[position],num,
                        Integer.parseInt(dayStart),
                        Integer.parseInt(dayEnd),
                        Integer.parseInt(dayReturn),
                        Integer.parseInt(endYear),
                        Integer.parseInt(endMonth)
                );
                creditCardBeanDao.insert(cardBean);
                ToastUtils.showStatic(mAct, "信用卡添加成功");
                finish();
                break;
        }
    }

    private void showPopupWindow(){
        if (popupWindow == null){
            View v = View.inflate(this, R.layout.window_select_bank, null);
            ListView listView = v.findViewById(R.id.list_view);
            listView.setAdapter(new MyAdapter());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    bank_icon.setImageResource(creditInfo.bank_icon[i]);
                    bank_name.setText(creditInfo.bank_name[i]);
                    position = i;
                    popupWindow.dismiss();
                }
            });
            int screenWidth = ScreenUtil.getUtils().getScreenWidth(mAct);
            popupWindow = new PopupWindow(v, screenWidth/3*2, screenWidth/2);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.showAsDropDown(bank_name, 0, 0);
        }else {
            if (popupWindow.isShowing()){
                popupWindow.dismiss();
            }else {
                popupWindow.showAsDropDown(bank_name, 0, 0);
            }
        }
    }

    private class MyAdapter extends BaseAdapter{
        private CreditInfo creditInfo;
        public MyAdapter(){
            creditInfo = CreditInfo.getInstance();
        }
        @Override
        public int getCount() {
            return creditInfo.bank_name.length;
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
            View v;
            if (view == null){
                v = View.inflate(mAct, R.layout.item_bank_celect, null);
            }else{
                v = view;
            }
            ImageView imageView = v.findViewById(R.id.icon);
            TextView tv = v.findViewById(R.id.name);
            imageView.setImageResource(creditInfo.bank_icon[i]);
            tv.setText(creditInfo.bank_name[i]);
            return v;
        }
    }
}
