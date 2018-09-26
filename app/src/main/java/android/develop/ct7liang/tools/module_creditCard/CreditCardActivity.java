package android.develop.ct7liang.tools.module_creditCard;

import android.app.Dialog;
import android.content.Intent;
import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.base.BaseActivity;
import android.develop.ct7liang.tools.bean.CreditCardBean;
import android.develop.ct7liang.tools.bean.CreditReturnBean;
import android.develop.ct7liang.tools.module_creditCard.adapter.CreditListAdapter;
import android.develop.ct7liang.tools.module_creditCard.adapter.CreditReturnListAdapter;
import android.develop.ct7liang.tools.module_creditCard.ziyuan.CreditInfo;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ct7liang.tangyuan.recyclerview.OnItemClickListener;
import com.ct7liang.tangyuan.utils.ToastUtils;
import com.ct7liang.tangyuan.utils.window.BottomWindow;
import com.ct7liang.tangyuan.view_titlebar.TitleBarView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tools.greendao.gen.CreditCardBeanDao;
import tools.greendao.gen.CreditReturnBeanDao;
import tools.greendao.gen.GreenDaoHelper;

import static android.develop.ct7liang.tools.R.id.card;
import static java.lang.Integer.parseInt;

public class CreditCardActivity extends BaseActivity implements OnItemClickListener, View.OnLongClickListener{


    private RecyclerView creditList;               //控件: 银行卡列表
    private CreditListAdapter creditListAdapter;   //银行卡列表适配器
    private CreditCardBeanDao creditCardBeanDao;   //数据: 银行卡数据管理
    private List<CreditCardBean> creditCardBeen;   //数据: 银行卡数据集合

    private CreditCardBean creditCardBean;

    private ImageView cardImg;      //银行卡图片
    private TextView cardNum;       //银行卡卡号
    private TextView recycleDate;   //当前账单周期
    private TextView returnDate;    //最迟还款日期
    private TextView endTime;       //有效期
    private TextView userName;      //持卡人姓名

    private RecyclerView returnList;    //还款信息列表
    private CreditReturnListAdapter creditReturnListAdapter;    //还款记录列表
    private CreditReturnBeanDao creditReturnBeanDao;   //数据: 还款记录数据管理
    private List<CreditReturnBean> creditReturnBeen;   //数据: 还款记录数据集合

    private ArrayList<CreditReturnBean> tagReturnList; //还款记录缓存集合

    private CreditInfo creditInfo;
    private boolean isReturnable;

    private Long tag;        //银行卡标识
    private String format;  //当天日期

    private Dialog returnDialog;
    private EditText name;
    private EditText number;
    private TextView btn;

    @Override
    public int setLayout() {
        return R.layout.activity_credit_card;
    }

    @Override
    public void findView() {
        initStatusBar();
        creditList = (RecyclerView) findViewById(R.id.credit_card_list);
        creditList.setLayoutManager(new LinearLayoutManager(this));

        cardImg = (ImageView) findViewById(R.id.cardImg);
        cardNum = (TextView) findViewById(R.id.cardNum);
        recycleDate = (TextView) findViewById(R.id.recycleDate);
        returnDate = (TextView) findViewById(R.id.returnDay);
        endTime = (TextView) findViewById(R.id.endTime);
        userName = (TextView) findViewById(R.id.name);

        returnList = (RecyclerView) findViewById(R.id.credit_card_desc_list);
        returnList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        findViewById(card).setOnLongClickListener(this);
    }

    @Override
    protected void setStatusBar() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titleBarView);
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
        //
        creditCardBeanDao = GreenDaoHelper.getDaoSession().getCreditCardBeanDao();
        creditCardBeen = creditCardBeanDao.loadAll();
        creditListAdapter = new CreditListAdapter(this, creditCardBeen);
        creditListAdapter.setOnItemClickListener(this);
        creditList.setAdapter(creditListAdapter);
        //
        creditReturnBeanDao = GreenDaoHelper.getDaoSession().getCreditReturnBeanDao();
        creditReturnBeen = creditReturnBeanDao.loadAll();
        tagReturnList = new ArrayList<>();
        //
        if (creditCardBeen.size()!=0){
            initTop(creditCardBeen.get(0));
            findViewById(R.id.empty).setVisibility(View.GONE);
        }else{
            findViewById(R.id.empty).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initFinish() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<CreditCardBean> creditCardBeen1 = creditCardBeanDao.loadAll();
        if (creditCardBeen1.size()!=creditCardBeen.size()){
            creditCardBeen.clear();
            creditCardBeen.addAll(creditCardBeen1);
            creditListAdapter.notifyDataSetChanged();
            if (creditCardBeen.size()!=0){
                initTop(creditCardBeen.get(0));
                findViewById(R.id.empty).setVisibility(View.GONE);
            }else{
                findViewById(R.id.empty).setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.parent:
                returnDialog.dismiss();
                break;
            case R.id.return_btn:
                String cardName = name.getText().toString();
                String cardNumber = number.getText().toString();
                String replace = cardNum.getText().toString().replace(" ", "");
                if (!cardName.equals(userName.getText().toString()) || !cardNumber.equals(replace)){
                    ToastUtils.showStatic(mAct, "请输入正确信息,确认已还款");
                    return;
                }
                CreditReturnBean creditReturnBean = new CreditReturnBean(null, tag, format, getReturnRecycle(), getReturnEndDay());
                creditReturnBeanDao.insert(creditReturnBean);
                creditReturnBeen.add(creditReturnBean);
                tagReturnList.add(creditReturnBean);
                if (tagReturnList.size() > 12){
                    CreditReturnBean creditReturnBean1 = tagReturnList.get(0);
                    creditReturnBeanDao.delete(creditReturnBean1);
                    creditReturnBeen.remove(creditReturnBean1);
                    tagReturnList.remove(creditReturnBean1);
                }
                creditReturnListAdapter.notifyDataSetChanged();
                returnDialog.dismiss();
                ToastUtils.showStatic(mAct, "还款记录成功!");
                break;
            case R.id.cardNum:
                Intent i = new Intent(mAct, CreditCardEditActivity.class);
                i.putExtra("data", new Gson().toJson(creditCardBean));
                startActivityForResult(i, 111);
                break;
        }
    }

    @Override
    public void onItemClick(View view, int i) {
        creditListAdapter.setOnClickPosition(i);
        creditListAdapter.notifyDataSetChanged();
        initTop(creditCardBeen.get(i));
    }

    @Override
    public boolean onLongClick(View view) {
        if (isReturnable&&getReturnEndDay()==null){
            showReturnWindow("本月账单已经还清", false);
        }else if (isReturnable&&getReturnEndDay()!=null){
            showReturnWindow("", true);
        }else{
            showReturnWindow("当前未处于还款周期内, 无法还款", false);
        }
        return false;
    }

    /**
     * 设置信用卡头布局
     */
    private void initTop(CreditCardBean cardBean){
        creditCardBean = cardBean;
        //设置图片
        int i = Arrays.binarySearch(creditInfo.tag, cardBean.getTag());
        cardImg.setImageResource(creditInfo.bank_img[i]);
        //设置卡号
        String cardNum = cardBean.cardNum;
        StringBuilder sb = new StringBuilder();
        for (int j = 1; j <cardNum.length()+1; j++) {
            if (j%4==0){
                sb.append(cardNum.charAt(j-1)).append(" ");
            }else{
                sb.append(cardNum.charAt(j-1));
            }
        }
        this.cardNum.setText(sb.toString());
        this.cardNum.setOnClickListener(this);
        //设置有效期
        int cardMonth = cardBean.cardMonth;
        if (cardMonth<10){
            endTime.setText(cardBean.cardYear+"/0"+cardMonth);
        }else{
            endTime.setText(cardBean.cardYear+"/"+cardMonth);
        }
        //设置姓名
        userName.setText(cardBean.name);
        //设置周期
        //设置日期
        int startDay = cardBean.getStartDay();
        int endDay = cardBean.getEndDay();
        int returnDay = cardBean.getReturnDay();

        boolean isKuayue = returnDay<=endDay;

        format = new SimpleDateFormat("yyyy-M-d", Locale.CHINA).format(new Date());
        String[] split = format.split("-");

        if (Integer.parseInt(split[2])>=startDay){
            int i1 = Integer.parseInt(split[1]) + 1;
            int i2 = Integer.parseInt(split[0]);
            if (i1>12){
                i1 = 1;
                i2 = i2+1;
            }
            recycleDate.setText(split[0]+"-"+split[1]+"-"+startDay + "\n" + i2+"-"+i1+"-"+endDay);
            if (isKuayue){
                int i3 = i2;
                int i4 = i1;
                i4++;
                if (i4==13){
                    i4 = 1;
                    i3 = i3+1;
                }
                returnDate.setText(i3+"-"+i4+"-"+returnDay);
            }else{
                returnDate.setText(i2+"-"+i1+"-"+returnDay);
            }
        }else{
            int i1 = Integer.parseInt(split[1]) - 1;
            int i2 = Integer.parseInt(split[0]);
            if (i1==0){
                i1 = 12;
                i2 = i2 -1;
            }
            recycleDate.setText(i2+"-"+i1+"-"+startDay+"\n"+split[0]+"-"+split[1]+"-"+endDay);
            if (isKuayue){
                int i3 = Integer.parseInt(split[0]);
                int i4 = Integer.parseInt(split[1]);
                i4++;
                if (i4 == 13){
                    i4 = 1;
                    i3 = i3 + 1;
                }
                returnDate.setText(i3+"-"+i4+"-"+returnDay);
            }else{
                returnDate.setText(split[0]+"-"+split[1]+"-"+returnDay);
            }
        }

        //计算当日是否在还款周期内
        int i1 = parseInt(split[2]);
        if (returnDay > endDay){
            //还款周期在同一个月
            isReturnable = i1 > endDay && i1 <= returnDay;
        }else{
            //还款周期不在同一个月
            isReturnable = i1 > endDay || i1 <= returnDay;
        }

        tag = cardBean.getId();
        initReturnList(tag);
    }

    /**
     * 设置还款列表
     * @param tag 银行卡类型
     */
    private void initReturnList(Long tag){
       tagReturnList.clear();
        for (int i = 0; i < creditReturnBeen.size(); i++) {
            CreditReturnBean creditReturnBean = creditReturnBeen.get(i);
            if (creditReturnBean.getTag().equals(tag)){
                tagReturnList.add(creditReturnBean);
            }
        }
        if (creditReturnListAdapter == null){
            creditReturnListAdapter = new CreditReturnListAdapter(this, tagReturnList);
            returnList.setAdapter(creditReturnListAdapter);
        }else{
            creditReturnListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取最迟还款日期
     */
    private String getReturnEndDay() {
        String text = returnDate.getText().toString();
        String[] split = text.split("-");
        int y = Integer.parseInt(split[0]);
        int m = Integer.parseInt(split[1]);
        if (m == 1){
            m = 12;
            y = y -1;
        }else{
            m = m-1;
        }
        String endDay = y+"-"+m+"-"+split[2];
        for (int i = 0; i < tagReturnList.size(); i++) {
            if (tagReturnList.get(i).getReturnEndDay().equals(endDay)){
                return null;
            }
        }
        return endDay;
    }

    /**
     * 获取还款周期
     */
    private String getReturnRecycle() {
        String text = recycleDate.getText().toString();
        String[] split = text.split("-");
        int y1 = Integer.parseInt(split[0]);
        int m1 = Integer.parseInt(split[1]);
        String[] split1 = split[2].split("\n");
        int y2 = Integer.parseInt(split1[1]);
        int m2 = Integer.parseInt(split[3]);
        if (m1 == 1){
            m1 = 12;
            y1 = y1 -1;
        }else{
            m1 = m1 -1;
        }
        if (m2 == 1){
            m2 = 12;
            y2 = y2 -1;
        }else{
            m2 = m2 -1;
        }
        return y1 + "-" + m1 + "-" + split1[0]+"\n"+y2+"-"+m2+"-"+split[4];
    }

    /**
     * 展示还款窗口
     * @param title
     * @param isEnable
     */
    private void showReturnWindow(final String title, final boolean isEnable){
        BottomWindow.getInstance().show(mAct, R.layout.window, -1, new BottomWindow.ViewSetting() {
            @Override
            public void onSetting(Dialog dialog, View view) {
                returnDialog = dialog;
                view.findViewById(R.id.parent).setOnClickListener(CreditCardActivity.this);
                btn = (TextView) view.findViewById(R.id.return_btn);
                if (!isEnable){
                    btn.setText(title);
                    btn.setEnabled(false);
                    btn.setBackgroundResource(R.drawable.commit_bg_false);
                }else{
                    btn.setOnClickListener(CreditCardActivity.this);
                    btn.setEnabled(true);
                    btn.setBackgroundResource(R.drawable.commit_bg);
                    name = (EditText) view.findViewById(R.id.name);
                    number = (EditText) view.findViewById(R.id.number);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 112:
                //修改数据
                String data1 = data.getStringExtra("data");
                CreditCardBean card = new Gson().fromJson(data1, CreditCardBean.class);
                creditCardBean.setTag(card.getTag());
                creditCardBean.setCardNum(card.getCardNum());
                creditCardBean.setStartDay(card.getStartDay());
                creditCardBean.setEndDay(card.getEndDay());
                creditCardBean.setReturnDay(card.getReturnDay());
                creditCardBean.setCardYear(card.getCardYear());
                creditCardBean.setCardMonth(card.getCardMonth());
                creditCardBean.setName(card.getName());
                initTop(creditCardBean);
                break;
//            case 113:
//                //删除数据
//                creditCardBeen.remove(creditCardBean);
//                creditListAdapter.notifyDataSetChanged();
//                if (creditCardBeen.size()!=0){
//                    initTop(creditCardBeen.get(0));
//                }
//                break;
        }
    }
}
