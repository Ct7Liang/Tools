package android.develop.ct7liang.tools.module_creditCard;

import android.content.Intent;
import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.base.BaseActivity;
import android.view.View;

import com.ct7liang.tangyuan.view_titlebar.TitleBarView;

public class CreditCardActivity extends BaseActivity {

    @Override
    public int setLayout() {
        return R.layout.activity_credit_card;
    }

    @Override
    public void findView() {
        initStatusBar();
    }

    @Override
    protected void setStatusBar() {
        TitleBarView titleBarView = findViewById(R.id.titleBarView);
        titleBarView.setStatusBar(this);
        titleBarView.setOnRightImgClick(new TitleBarView.OnRightImgClick() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mAct, CreditCardAddActivity.class));
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initFinish() {

    }

    @Override
    public void onClick(View view) {

    }
}
