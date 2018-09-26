package android.develop.ct7liang.tools.module_creditCard;

import android.app.Dialog;
import android.content.Intent;
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
import com.ct7liang.tangyuan.utils.window.BottomWindow;
import com.ct7liang.tangyuan.view_titlebar.TitleBarView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import tools.greendao.gen.CreditCardBeanDao;
import tools.greendao.gen.GreenDaoHelper;

public class CreditCardEditActivity extends BaseActivity {

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
    private EditText userName;
    private int position;
    private CreditCardBeanDao creditCardBeanDao;
    private ArrayList<String> numList;
    private CreditCardBean creditCardBean;

    @Override
    public int setLayout() {
        return R.layout.activity_credit_card_edit;
    }

    @Override
    public void findView() {
        initStatusBar();
        bank_name = (TextView) findViewById(R.id.bank_name);
        bank_icon = (ImageView) findViewById(R.id.bank_icon);
        cardNum = (EditText) findViewById(R.id.cardNum);
        startDay = (EditText) findViewById(R.id.startDay);
        endDay = (EditText) findViewById(R.id.endDay);
        returnDay = (EditText) findViewById(R.id.returnDay);
        cardYear = (EditText) findViewById(R.id.cardYear);
        cardMonth = (EditText) findViewById(R.id.cardMonth);
        userName = (EditText) findViewById(R.id.userName);
    }

    @Override
    protected void setStatusBar() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titleBarView);
        titleBarView.setStatusBar(this);
    }

    @Override
    public void initData() {
        String data = getIntent().getStringExtra("data");
        creditCardBean = new Gson().fromJson(data, CreditCardBean.class);
        position = creditCardBean.getTag();
        creditInfo = CreditInfo.getInstance();
        creditCardBeanDao = GreenDaoHelper.getDaoSession().getCreditCardBeanDao();
        List<CreditCardBean> creditCardBeen = creditCardBeanDao.loadAll();
        numList = new ArrayList<>();
        for (int i = 0; i < creditCardBeen.size(); i++) {
            String cardNum = creditCardBeen.get(i).cardNum;
            if (!cardNum.equals(creditCardBean.cardNum)){
                numList.add(cardNum);
            }
        }
    }

    @Override
    public void initView() {
        int tag = creditCardBean.getTag();
        bank_name.setText(creditInfo.bank_name[tag]);
        bank_icon.setImageResource(creditInfo.bank_icon[tag]);
        cardNum.setText(creditCardBean.cardNum);
        startDay.setText(creditCardBean.startDay+"");
        endDay.setText(creditCardBean.endDay+"");
        returnDay.setText(creditCardBean.returnDay+"");
        cardYear.setText(creditCardBean.cardYear+"");
        cardMonth.setText(creditCardBean.cardMonth+"");
        userName.setText(creditCardBean.name);
        bank_name.setOnClickListener(this);
        findViewById(R.id.commit).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
    }

    @Override
    public void initFinish() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
                String name = userName.getText().toString();
                if (TextUtils.isEmpty(num)||TextUtils.isEmpty(dayStart)||TextUtils.isEmpty(dayEnd)||
                        TextUtils.isEmpty(dayReturn)||TextUtils.isEmpty(endYear)
                        ||TextUtils.isEmpty(endMonth)||TextUtils.isEmpty(name)||position==-1){
                    ToastUtils.showStatic(mAct, "请把信息补充完整");
                    return;
                }
                if (numList.contains(num)){
                    ToastUtils.showStatic(mAct, "此卡已存在");
                    return;
                }
                creditCardBean.setTag(creditInfo.tag[position]);
                creditCardBean.setName(name);
                creditCardBean.setCardNum(num);
                creditCardBean.setStartDay(Integer.parseInt(dayStart));
                creditCardBean.setEndDay(Integer.parseInt(dayEnd));
                creditCardBean.setReturnDay(Integer.parseInt(dayReturn));
                creditCardBean.setCardYear(Integer.parseInt(endYear));
                creditCardBean.setCardMonth(Integer.parseInt(endMonth));
                creditCardBeanDao.update(creditCardBean);
                ToastUtils.showStatic(mAct, "信用卡信息修改成功");
                Intent i = new Intent();
                i.putExtra("data", new Gson().toJson(creditCardBean));
                setResult(112, i);
                finish();
                break;
            case R.id.delete:
                showDialog();
                break;
        }
    }

    private void showDialog(){
        BottomWindow.getInstance().show(mAct, R.layout.window_delete_card, -1, new BottomWindow.ViewSetting() {
            @Override
            public void onSetting(final Dialog dialog, View view) {
                final EditText et_name = (EditText) view.findViewById(R.id.name);
                final EditText et_number = (EditText) view.findViewById(R.id.number);
                view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                view.findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!et_name.getText().toString().equals(creditCardBean.getName())||
                                !et_number.getText().toString().equals(creditCardBean.getCardNum())){
                            ToastUtils.showStatic(mAct, "请输入正确的信息,再进行操作");
                            return;
                        }
                        creditCardBeanDao.delete(creditCardBean);
                        Intent i1 = new Intent();
                        setResult(113, i1);
                        finish();
                    }
                });
            }
        });
    }

    private void showPopupWindow(){
        if (popupWindow == null){
            View v = View.inflate(this, R.layout.window_select_bank, null);
            ListView listView = (ListView) v.findViewById(R.id.list_view);
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

    private class MyAdapter extends BaseAdapter {
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
            ImageView imageView = (ImageView) v.findViewById(R.id.icon);
            TextView tv = (TextView) v.findViewById(R.id.name);
            imageView.setImageResource(creditInfo.bank_icon[i]);
            tv.setText(creditInfo.bank_name[i]);
            return v;
        }
    }
}
