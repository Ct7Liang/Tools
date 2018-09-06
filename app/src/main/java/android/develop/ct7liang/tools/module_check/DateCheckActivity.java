package android.develop.ct7liang.tools.module_check;

import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.base.BaseActivity;
import android.develop.ct7liang.tools.bean.MonthCheck;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.ct7liang.tangyuan.view_titlebar.TitleBarView;
import com.google.gson.Gson;

public class DateCheckActivity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    public int setLayout() {
        return R.layout.activity_date_check;
    }

    @Override
    public void findView() {
        initStatusBar();
    }

    @Override
    protected void setStatusBar() {
        TitleBarView titleBarView = findViewById(R.id.titleBarView);
        titleBarView.setStatusBar(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
    }

    @Override
    public void initData() {
        String data = getIntent().getStringExtra("data");
        MonthCheck monthcheck = new Gson().fromJson(data, MonthCheck.class);
        CheckAdapter adapter = new CheckAdapter(this, monthcheck.checks);
        recyclerView.setAdapter(adapter);
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