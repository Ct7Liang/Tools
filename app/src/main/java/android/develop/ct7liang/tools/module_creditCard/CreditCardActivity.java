package android.develop.ct7liang.tools.module_creditCard;

import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.base.BaseActivity;
import android.develop.ct7liang.tools.bean.CreditCardBean;
import android.view.View;

public class CreditCardActivity extends BaseActivity {

    @Override
    public int setLayout() {
        return R.layout.activity_credit_card;
    }

    @Override
    public void findView() {

    }

    @Override
    public void initData() {
        CreditCardBean c1 = new CreditCardBean(22, 1, 21, 1, 9);
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
