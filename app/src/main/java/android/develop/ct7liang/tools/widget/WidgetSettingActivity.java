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
    private int lastMin;
    private int lastHour;
    private int currentMin;
    private int currentHour;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 60:
                    //再次发送延迟消息,延迟60s
                    sendEmptyMessageDelayed(60, 60*1000);
                    lastMin = currentMin;
                    lastHour = currentHour;
                    currentMin+=1;
                    if (currentMin==60){
                        currentMin = 0;
                        currentHour+=1;
                        if (lastHour==13){
                            lastHour = 1;
                        }
                    }
                    if (currentMin%12==0){
                        rotateAnimHour();
                    }
                    rotateAnimMinute();
                    break;
            }
        }
    };
    private Animation anim;


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
        String[] split = new SimpleDateFormat("HH:mm:ss", Locale.CHINA).format(new Date()).split(":");
//        String[] split = {"23", "59", "55"};

        int s = Integer.parseInt(split[2]);
        rotateAnimSecond(s);

        //获取当前分钟数
        currentMin = Integer.parseInt(split[1]);
        //获取当前小时数
        currentHour = Integer.parseInt(split[0]);
        currentHour = currentHour%12;

        //设置分针位置
        setMinute();
        //等秒针走够60s的时候,发送延迟消息
        handler.sendEmptyMessageDelayed(60, (60-s)*1000);

        //设置时针针位置
        setHour();
    }

    @Override
    public void onClick(View view) {}


    /**
     * 设置时针位置
     * 由小时数先算出时针的具体位置
     * 再由分钟数算出时针位于两个小时之间的具体位置
     * 每十二分钟,时针走一小格(6度)
     */
    public void setHour(){
        int m0 = currentMin/12;
        Animation anim = new RotateAnimation(0f, currentHour*30+m0*6, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(1); // 设置动画时间
        hour.startAnimation(anim);
    }
    /**
     * 设置分针位置
     */
    public void setMinute(){
        Animation anim = new RotateAnimation(0f, currentMin*6, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(1); // 设置动画时间
        minute.startAnimation(anim);
    }
    /**
     * 时针走一小格
     */
    public void rotateAnimHour() {
        Animation anim = new RotateAnimation(lastHour * 30 + (lastMin / 12) * 6, currentHour * 30 + (currentMin / 12) * 6, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(50); // 设置动画时间
        anim.setInterpolator(new LinearInterpolator()); // 设置插入器
        hour.startAnimation(anim);
    }
    /**
     * 分钟走一格
     */
    public void rotateAnimMinute() {
        int i1 = currentMin * 6;
        if (i1 == 0){
            i1 = 360;
        }
        anim = new RotateAnimation(lastMin * 6, i1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
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