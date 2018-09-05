package android.develop.ct7liang.tools.module_weight;


import android.content.Intent;
import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.activity.ToolsConstant;
import android.develop.ct7liang.tools.base.BaseActivity;
import android.develop.ct7liang.tools.bean.Weight;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ct7liang.tangyuan.utils.SpUtils;
import com.ct7liang.tangyuan.utils.ToastUtils;
import com.ct7liang.tangyuan.view_titlebar.TitleBarView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import tools.greendao.gen.GreenDaoHelper;
import tools.greendao.gen.WeightDao;


public class SettingActivity extends BaseActivity {

    private EditText input_weight;
    private EditText input_max;
    private EditText input_min;
    private WeightDao weightDao;

    @Override
    public int setLayout() {
        return R.layout.activity_setting;
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
        weightDao = GreenDaoHelper.getDaoSession().getWeightDao();
    }

    @Override
    public void initView() {
        input_weight = findViewById(R.id.input_weight);
        input_max = findViewById(R.id.input_max);
        input_min = findViewById(R.id.input_min);
        findViewById(R.id.btn_weight).setOnClickListener(this);
        findViewById(R.id.btn_max).setOnClickListener(this);
        findViewById(R.id.btn_min).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
    }

    @Override
    public void initFinish() {}

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_weight:
                String weight = input_weight.getText().toString().trim();
                if (TextUtils.isEmpty(weight)){
                    return;
                }
                long time = System.currentTimeMillis();
                String format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date(time));
                String[] split = format.split("-");
                Weight w = weightDao.queryBuilder().where(WeightDao.Properties.TimeFormat.eq(format)).unique();
                if (w == null){
                    weightDao.insert(new Weight(null, time, format, split[0], split[1], split[2], Float.valueOf(weight)));
                    ToastUtils.showStatic(mAct, "数据录入成功");
                }else{
                    w.setTime(time);
                    w.setYear(split[0]);
                    w.setMonth(split[1]);
                    w.setDate(split[2]);
                    w.setValue(Float.valueOf(weight));
                    weightDao.update(w);
                    ToastUtils.showStatic(mAct, "数据更新成功");
                }
                input_weight.setText("");
                break;
            case R.id.btn_max:
                String max = input_max.getText().toString().trim();
                if (TextUtils.isEmpty(max)){
                    return;
                }
                SpUtils.start().saveFloat(ToolsConstant.Y_MAX, Float.valueOf(max));
                input_max.setText("");
                ToastUtils.showStatic(mAct, "体重上限设置成功");
                break;
            case R.id.btn_min:
                String min = input_min.getText().toString().trim();
                if (TextUtils.isEmpty(min)){
                    return;
                }
                SpUtils.start().saveFloat(ToolsConstant.Y_MIN, Float.valueOf(min));
                input_min.setText("");
                ToastUtils.showStatic(mAct, "体重下限设置成功");
                break;
            case R.id.btn_query:
                startActivity(new Intent(mAct, ChartActivity.class));
                break;
        }
    }
}