package android.develop.ct7liang.tools.module_creditCard.ziyuan;

import android.develop.ct7liang.tools.R;

/**
 * Created by Administrator on 2018-09-17.
 *
 */
public class CreditInfo {

    private CreditInfo(){}
    private static CreditInfo creditInfo;
    public static CreditInfo getInstance(){
        if (creditInfo == null){
            creditInfo = new CreditInfo();
        }
        return creditInfo;
    }

    public String[] bank_name = {
            "招商银行",
            "交通银行",
            "广发银行",
            "中信银行",
    };

    public int[] bank_icon = {
            R.mipmap.credit_zs,
            R.mipmap.credit_jt,
            R.mipmap.credit_gf,
            R.mipmap.credit_zx,
    };

    public int[] bank_img = {
            R.mipmap.credit_zs_01,
            R.mipmap.credit_jt_01,
            R.mipmap.credit_gf_01,
            R.mipmap.credit_zx_01,
    };

    public int[] tag = {
            0,
            1,
            2,
            3,
    };
}