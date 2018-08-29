package android.develop.ct7liang.tools.weight;

import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.activity.ToolsConstant;
import android.develop.ct7liang.tools.base.BaseActivity;
import android.develop.ct7liang.tools.bean.Weight;
import android.graphics.Color;
import android.view.View;

import com.ct7liang.tangyuan.utils.SpUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tools.greendao.gen.GreenDaoHelper;
import tools.greendao.gen.WeightDao;


public class ChartActivity extends BaseActivity {

    private LineChart lineChart;
    private WeightDao weightDao;
    private List<Weight> weights;

    @Override
    public int setLayout() {
        return R.layout.activity_chart;
    }

    @Override
    public void findView() {
        setOrientation(false);
        lineChart = findViewById(R.id.line_chart);
        initStatusBar();
    }

    @Override
    public void initData() {
        weightDao = GreenDaoHelper.getDaoSession().getWeightDao();
        weights = weightDao.loadAll();
        if (weights.size() > 0){
            Weight weight = weights.get(weights.size() - 1);
            String timeFormat = weight.getTimeFormat();
            String format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date(System.currentTimeMillis()));
            if (timeFormat.equals(format)){
                //数据完整
            }else{
                //可判断出 数据不完整
                String[] split = timeFormat.split("-");
                int i = Integer.parseInt(split[2]);
                String[] split2 = format.split("-");
                int i2 = Integer.parseInt(split2[2]);
                for (int j = 0; j < i2 - i; j++) {
                    weightDao.insert(weight);
                    weights.add(weight);
                }
            }
        }

        List<Entry> lists = new ArrayList<>();
        if (weights.size()!=0){
            for (int i = 0; i < weights.size(); i++) {
                lists.add(new Entry(i, weights.get(i).getValue()));
            }
            LineDataSet lineDataSet = new LineDataSet(lists, "weight");
            lineDataSet.setDrawCircleHole(false);
            lineDataSet.setValueTextSize(10f);
            lineDataSet.setValueTextColor(Color.parseColor("#39D0FF"));
            LineData data = new LineData(lineDataSet);
            lineChart.setData(data);
        }
    }

    @Override
    public void initView() {
        //是否显示边界
        lineChart.setDrawBorders(true);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(-0.1f);
        xAxis.setGranularity(1);

        YAxis axisRight = lineChart.getAxisRight();
        axisRight.setEnabled(false);
        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setAxisMinimum(SpUtils.start().getFloat(ToolsConstant.Y_MIN, 0f));
        axisLeft.setAxisMaximum(SpUtils.start().getFloat(ToolsConstant.Y_MAX, 100f));

        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return new SimpleDateFormat("yy/MM/dd", Locale.CHINA).format(new Date(weights.get((int) value).getTime()));
            }
        });

        lineChart.setMarkerView(new MyMarkerView(this));
    }

    @Override
    public void initFinish() {

    }

    @Override
    public void onClick(View view) {

    }
}
