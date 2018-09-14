package android.develop.ct7liang.tools.bean;

/**
 * Created by Administrator on 2018-09-14.
 *
 */

public class CreditCardBean {

    public String cardNum;

    public int cycle_start_day;

    public int cycleIsKuayue;

    public int cycle_end_day;

    public int returnIsKuayue;

    public int return_last_day;

    public int return_day;

    public String return_cycle;

    public String last_date;

    public CreditCardBean(int cycle_start_day, int cycleIsKuayue, int cycle_end_day, int returnIsKuayue, int return_last_day) {
        this.cycle_start_day = cycle_start_day;
        this.cycleIsKuayue = cycleIsKuayue;
        this.cycle_end_day = cycle_end_day;
        this.returnIsKuayue = returnIsKuayue;
        this.return_last_day = return_last_day;
    }
}