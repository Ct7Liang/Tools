package android.develop.ct7liang.tools.module_check;

import android.content.Intent;
import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.base.BaseActivity;
import android.develop.ct7liang.tools.bean.Check;
import android.develop.ct7liang.tools.bean.MonthCheck;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ct7liang.tangyuan.recyclerview.OnItemClickListener;
import com.ct7liang.tangyuan.view_titlebar.TitleBarView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import tools.greendao.gen.CheckDao;
import tools.greendao.gen.GreenDaoHelper;

public class MonthCheckActivity extends BaseActivity implements OnItemClickListener {

    private CheckDao checkDao;
    private List<Check> checks;
    private List<MonthCheck> monthChecks;
    private RecyclerView recyclerView;
    private MonthCheckAdapter monthCheckAdapter;

    @Override
    public int setLayout() {
        return R.layout.activity_month_check;
    }

    @Override
    public void findView() {
        initStatusBar();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
    }

    @Override
    protected void setStatusBar() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titleBarView);
        titleBarView.setStatusBar(this);
    }

    @Override
    public void initData() {
        checkDao = GreenDaoHelper.getDaoSession().getCheckDao();
        checks = checkDao.loadAll();
        monthChecks = new ArrayList<>();
        int monthTag = -1;
        MonthCheck monthCheck = null;
        for (int i = 0; i < checks.size(); i++) {
            Check check = checks.get(i);
            if (check.getMonth()!=monthTag){
                monthTag = check.getMonth();
                monthChecks.add(monthCheck);
                monthCheck = new MonthCheck();
                monthCheck.year = check.getYear();
                monthCheck.month = check.getMonth();
                monthCheck.checks = new ArrayList<>();
            }
            monthCheck.checks.add(check);
            if (check.getType()==0){
                monthCheck.cash -= check.cash;
            }else{
                monthCheck.cash += check.cash;
            }
            if (i == checks.size()-1){
                monthChecks.add(monthCheck);
            }
        }
        if (monthChecks.size()!=0){
            monthChecks.remove(0);
            monthCheckAdapter = new MonthCheckAdapter(this, monthChecks);
            monthCheckAdapter.setOnItemClickListener(this);
            recyclerView.setAdapter(monthCheckAdapter);
        }
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

    @Override
    public void onItemClick(View view, int i) {
        MonthCheck monthCheck = monthChecks.get(i);
        String s = new Gson().toJson(monthCheck);
        Intent intent = new Intent(this, DateCheckActivity.class);
        intent.putExtra("data", s);
        startActivity(intent);
    }
}
