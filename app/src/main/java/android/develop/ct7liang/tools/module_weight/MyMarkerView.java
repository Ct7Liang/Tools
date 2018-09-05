package android.develop.ct7liang.tools.module_weight;

import android.content.Context;
import android.develop.ct7liang.tools.R;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

/**
 * Created by Administrator on 2018-07-25.
 *
 */
public class MyMarkerView extends MarkerView {

    private TextView tv;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     */
    public MyMarkerView(Context context) {
        super(context, R.layout.my_marker_view);
        tv = findViewById(R.id.tv);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tv.setText(e.getY()+"kg");
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight() - 10);
    }
}
