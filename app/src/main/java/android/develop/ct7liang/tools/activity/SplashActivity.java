package android.develop.ct7liang.tools.activity;

import android.content.Intent;
import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.base.BaseActivity;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public int setLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void findView() {
        setFullScreen();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initFinish() {
        handler.postDelayed(new AutoStartActivity(), 3000);
    }

    @Override
    public void onClick(View view) {

    }

    private class AutoStartActivity implements Runnable{
        @Override
        public void run() {
            startActivity(new Intent(mAct, MainActivity.class));
            finish();
        }
    }
}
