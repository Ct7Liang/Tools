package android.develop.ct7liang.tools.module_creditCard;

import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.base.BaseActivity;
import android.develop.ct7liang.tools.module_creditCard.ziyuan.CreditInfo;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ct7liang.tangyuan.utils.ScreenUtil;
import com.ct7liang.tangyuan.view_titlebar.TitleBarView;

public class CreditCardAddActivity extends BaseActivity {

    private TextView bank_name;
    private PopupWindow popupWindow;

    @Override
    public int setLayout() {
        return R.layout.activity_credit_card_add;
    }

    @Override
    public void findView() {
        initStatusBar();
        bank_name = findViewById(R.id.bank_name);
    }

    @Override
    protected void setStatusBar() {
        TitleBarView titleBarView = findViewById(R.id.titleBarView);
        titleBarView.setStatusBar(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        bank_name.setOnClickListener(this);
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
        }
    }

    private void showPopupWindow(){
        if (popupWindow == null){
            View v = View.inflate(this, R.layout.window_select_bank, null);
            ListView listView = v.findViewById(R.id.list_view);
            listView.setAdapter(new MyAdapter());
            int screenWidth = ScreenUtil.getUtils().getScreenWidth(mAct);
            popupWindow = new PopupWindow(v, screenWidth/2, screenWidth/2);
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
