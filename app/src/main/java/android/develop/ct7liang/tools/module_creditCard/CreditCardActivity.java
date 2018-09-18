package android.develop.ct7liang.tools.module_creditCard;

import android.content.Intent;
import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.base.BaseActivity;
import android.develop.ct7liang.tools.bean.CreditCardBean;
import android.develop.ct7liang.tools.module_creditCard.adapter.CreditListAdapter;
import android.develop.ct7liang.tools.module_creditCard.ziyuan.CreditInfo;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ct7liang.tangyuan.recyclerview.OnItemClickListener;
import com.ct7liang.tangyuan.view_titlebar.TitleBarView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tools.greendao.gen.CreditCardBeanDao;
import tools.greendao.gen.GreenDaoHelper;

public class CreditCardActivity extends BaseActivity implements OnItemClickListener {


    private RecyclerView creditList;               //控件: 银行卡列表
    private CreditListAdapter creditListAdapter;   //银行卡列表适配器
    private CreditCardBeanDao creditCardBeanDao;   //数据: 银行卡数据管理
    private List<CreditCardBean> creditCardBeen;   //数据: 银行卡数据集合

    private ImageView cardImg;      //银行卡图片
    private TextView cardNum;       //银行卡卡号
    private TextView recycleDate;   //当前账单周期
    private TextView returnDate;    //最迟还款日期
    private TextView endTime;       //有效期

    private CreditInfo creditInfo;

    @Override
    public int setLayout() {
        return R.layout.activity_credit_card;
    }

    @Override
    public void findView() {
        initStatusBar();
        creditList = findViewById(R.id.credit_card_list);
        creditList.setLayoutManager(new LinearLayoutManager(this));

        cardImg = findViewById(R.id.cardImg);
        cardNum = findViewById(R.id.cardNum);
        recycleDate = findViewById(R.id.recycleDate);
        returnDate = findViewById(R.id.returnDay);
        endTime = findViewById(R.id.endTime);
    }

    @Override
    protected void setStatusBar() {
        TitleBarView titleBarView = findViewById(R.id.titleBarView);
        titleBarView.setStatusBar(this);
        titleBarView.setOnRightImgClick(new TitleBarView.OnRightImgClick() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mAct, CreditCardAddActivity.class));
            }
        });
    }

    @Override
    public void initData() {
        creditInfo = CreditInfo.getInstance();
        creditCardBeanDao = GreenDaoHelper.getDaoSession().getCreditCardBeanDao();
        creditCardBeen = creditCardBeanDao.loadAll();
        creditListAdapter = new CreditListAdapter(this, creditCardBeen);
        creditListAdapter.setOnItemClickListener(this);
    }

    @Override
    public void initView() {
        creditList.setAdapter(creditListAdapter);
        if (creditCardBeen.size()!=0){
            initTop(creditCardBeen.get(0));
        }
    }

    @Override
    public void initFinish() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(View view, int i) {

    }

    private void initTop(CreditCardBean cardBean){
        //设置图片
        int i = Arrays.binarySearch(creditInfo.tag, cardBean.getTag());
        cardImg.setImageResource(creditInfo.bank_img[i]);
        //设置卡号
        String cardNum = cardBean.cardNum;
        StringBuffer sb = new StringBuffer();
        for (int j = 1; j <cardNum.length()+1; j++) {
            if (j%4==0){
                sb.append(cardNum.charAt(j-1)).append(" ");
            }else{
                sb.append(cardNum.charAt(j-1));
            }
        }
        this.cardNum.setText(sb.toString());
        //设置有效期
        int cardMonth = cardBean.cardMonth;
        if (cardMonth<10){
            endTime.setText(cardBean.cardYear+"-0"+cardMonth);
        }else{
            endTime.setText(cardBean.cardYear+"-"+cardMonth);
        }
        //设置周期
        //设置日期
        int startDay = cardBean.getStartDay();
        int endDay = cardBean.getEndDay();
        int returnDay = cardBean.getReturnDay();

        boolean isKuayue = returnDay<=endDay;

        String format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date());
        String[] split = format.split("-");

    }
}
