package android.develop.ct7liang.tools.widget;

import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.base.BaseActivity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WidgetSettingActivity extends BaseActivity {

    private View second;
    private View minute;
    private View hour;
    private int currentMin;
    private int currentHour;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    //发送延迟一分钟的消息
                    int i = (int) msg.obj;
                    Message m = Message.obtain();
                    m.obj = (i+1)%60;
                    m.what = 0;
                    sendMessageDelayed(m, 60000);
                    //分针走1分
                    rotateAnimMinute(i);
                    break;
                case 1:
                    //收到消息,时针执行一次动画,走一小格,转动6度
                    rotateAnimHour(currentMin, currentHour);
                    //再次发送延迟12分钟的定时消息
                    currentMin+=12;
                    if (currentMin > 60 || currentMin == 60){
                        currentMin = 0;
                        currentHour+=1;
                        if (currentHour > 12){
                            currentHour = 1;
                        }
                    }
                    sendEmptyMessageDelayed(1, 12*60*1000);
                    break;
            }
        }
    };


    @Override
    public int setLayout() {
        return R.layout.activity_widget_setting;
    }

    @Override
    public void findView() {
        setFullScreen();
        second = findViewById(R.id.second);
        minute = findViewById(R.id.minute);
        hour = findViewById(R.id.hour);
    }

    @Override
    public void initData() {}

    @Override
    public void initView() {}

    @Override
    public void initFinish() {
        String format = new SimpleDateFormat("HH:mm:ss", Locale.CHINA).format(new Date());
        String[] split = format.split(":");
//        String[] split = {"23", "59", "50"};
        int s = Integer.parseInt(split[2]);
        rotateAnimSecond(s);

        //获取当前分钟数
        currentMin = Integer.parseInt(split[1]);
        //设置分针位置
        setMinute(currentMin);
        //等秒针走到60的时候,分针走动6刻度
        Message mObtain = Message.obtain();
        mObtain.what = 0;
        mObtain.obj = currentMin;
        //开启消息发送,一分钟分针走一次
        handler.sendMessageDelayed(mObtain, (60-s)*1000);

        //获取当前小时数
        currentHour = Integer.parseInt(split[0]);
        currentHour = currentHour%12;
        //设置时针针位置
        setHour(currentMin, currentHour);
    }

    @Override
    public void onClick(View view) {}

    /**
     * 设置时针位置
     * 由小时数先算出时针的具体位置
     * 再由分钟数算出时针位于两个小时之间的具体位置
     * 每十二分钟,时针走一小格(6度)
     */
    public void setHour(int m, int h){
        int m0 = m/12;
        Animation anim = new RotateAnimation(0f, h*30+m0*6, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(1); // 设置动画时间
        hour.startAnimation(anim);
    }
    /**
     * 时针走一小格
     */
    public void rotateAnimHour(int cmin, int chour) {
        int nMin = cmin+12;
        int nHour = chour;
        if (nMin==60){
            nMin = 0;
            nHour+=1;
            if (nHour>12){
                nHour=1;
            }
        }
        Animation anim = new RotateAnimation(
                chour*30+(cmin/12)*6,
                nHour*30+(nMin/12)*6,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(50); // 设置动画时间
        anim.setInterpolator(new LinearInterpolator()); // 设置插入器
        hour.startAnimation(anim);
    }



    /**
     * 设置分针位置
     */
    public void setMinute(int m){
        Animation anim = new RotateAnimation(0f, m*6, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(1); // 设置动画时间
        minute.startAnimation(anim);
    }
    /**
     * 分钟走一格
     * @param m 当前分钟数
     */
    public void rotateAnimMinute(int m) {
        m = m%60;
        Animation anim = new RotateAnimation(m*6, (m+1)*6, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(50); // 设置动画时间
        anim.setInterpolator(new LinearInterpolator()); // 设置插入器
        minute.startAnimation(anim);
    }

    /**
     * 秒针走一格
     * @param s 当前秒数
     */
    public void rotateAnimSecond(int s) {
        Animation anim = new RotateAnimation(s*6, s*6 + 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(60000); // 设置动画时间
        anim.setRepeatCount(-1);
        anim.setInterpolator(new LinearInterpolator()); // 设置插入器
        second.startAnimation(anim);
    }

}