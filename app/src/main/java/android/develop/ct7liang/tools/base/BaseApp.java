package android.develop.ct7liang.tools.base;

import android.app.Application;
import com.ct7liang.tangyuan.AppFolder;
import com.ct7liang.tangyuan.utils.LogUtils;
import com.ct7liang.tangyuan.utils.SpUtils;
import com.ct7liang.tangyuan.utils.crash.CrashUtils;
import tools.greendao.gen.GreenDaoHelper;

/**
 * Created by Administrator on 2018-08-29.
 *
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SpUtils.init(this);
        LogUtils.setTag("tools");
        LogUtils.setLogEnable(true);
        AppFolder.createAppFolder("A_tools");
        CrashUtils.init(this, true, "App运行错误");

        GreenDaoHelper.initDatabase(this);
    }
}