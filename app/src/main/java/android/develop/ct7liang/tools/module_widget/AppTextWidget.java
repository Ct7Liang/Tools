package android.develop.ct7liang.tools.module_widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.bean.TextBean;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;

import java.util.List;

import tools.greendao.gen.GreenDaoHelper;
import tools.greendao.gen.TextBeanDao;

/**
 * Implementation of App Widget functionality.
 */
public class AppTextWidget extends AppWidgetProvider {

    public static final String TAG = "android.develop.ct7liang.zdd";
    private static List<TextBean> textBeen;
    private static TextBeanDao textBeanDao;
    private static int position;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        textBeanDao = GreenDaoHelper.getDaoSession().getTextBeanDao();
        textBeen = textBeanDao.loadAll();
        int size = textBeen.size();
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_text_widget);
        if (size == 0){
            views.setTextViewText(R.id.text, "");
            views.setTextViewText(R.id.text1, "");
            views.setViewVisibility(R.id.tag, View.GONE);
            views.setOnClickPendingIntent(R.id.views, PendingIntent.getActivity(context, 1, new Intent(context, WidgetSettingActivity.class), 0));
        }else{
            position = (int) (Math.random() * size);
            views.setTextViewText(R.id.text, textBeen.get(position).content);
            String title = textBeen.get(position).title;
            if (TextUtils.isEmpty(title)){
                views.setTextViewText(R.id.text1, "");
                views.setViewVisibility(R.id.tag, View.GONE);
            }else{
                views.setTextViewText(R.id.text1, title);
                views.setViewVisibility(R.id.tag, View.VISIBLE);
            }
            views.setOnClickPendingIntent(R.id.views, PendingIntent.getActivity(context, 1, new Intent(context, WidgetSettingActivity.class), 0));

            Intent i = new Intent(TAG);
            i.putExtra("id", appWidgetId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2, i, PendingIntent.FLAG_CANCEL_CURRENT);
            views.setOnClickPendingIntent(R.id.img, pendingIntent);
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (textBeanDao!=null){
            textBeen = textBeanDao.loadAll();
        }
        if(TextUtils.equals(TAG, intent.getAction())){
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_text_widget);
            position++;
            if (position==textBeen.size()){
                position = 0;
            }
            views.setTextViewText(R.id.text, textBeen.get(position).content);
            String title = textBeen.get(position).title;
            if (TextUtils.isEmpty(title)){
                views.setTextViewText(R.id.text1, "");
                views.setViewVisibility(R.id.tag, View.GONE);
            }else{
                views.setTextViewText(R.id.text1, title);
                views.setViewVisibility(R.id.tag, View.VISIBLE);
            }
            AppWidgetManager instance = AppWidgetManager.getInstance(context);
            instance.updateAppWidget(intent.getIntExtra("id", 0), views);
        }
        super.onReceive(context, intent);
    }
}

