package android.develop.ct7liang.tools.activity;

import android.content.Intent;
import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.base.BaseActivity;
import android.develop.ct7liang.tools.module_check.CheckActivity;
import android.develop.ct7liang.tools.module_weight.SettingActivity;
import android.develop.ct7liang.tools.module_widget.WidgetSettingActivity;
import android.view.View;

import com.ct7liang.tangyuan.view_titlebar.TitleBarView;

public class MainActivity extends BaseActivity {

    @Override
    public int setLayout() {
        return R.layout.activity_main;
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

    }

    @Override
    public void initView() {
        findViewById(R.id.weight).setOnClickListener(this);
        findViewById(R.id.widget).setOnClickListener(this);
        findViewById(R.id.check).setOnClickListener(this);
    }

    @Override
    public void initFinish() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.weight:
                startActivity(new Intent(mAct, SettingActivity.class));
                break;
            case R.id.widget:
                startActivity(new Intent(mAct, WidgetSettingActivity.class));
                break;
            case R.id.check:
                startActivity(new Intent(mAct, CheckActivity.class));
                break;
        }
    }
}