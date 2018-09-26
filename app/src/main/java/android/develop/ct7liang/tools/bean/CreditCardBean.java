package android.develop.ct7liang.tools.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018-09-14.
 *
 */
@Entity
public class CreditCardBean {
    @Id(autoincrement = true)
    public Long id;
    @NotNull
    public int tag;
    @NotNull
    public String name;
    @NotNull
    public String cardNum;
    @NotNull
    public int startDay;
    @NotNull
    public int endDay;
    @NotNull
    public int returnDay;
    @NotNull
    public int cardYear;
    @NotNull
    public int cardMonth;
    @Generated(hash = 1363520629)
    public CreditCardBean(Long id, int tag, @NotNull String name,
            @NotNull String cardNum, int startDay, int endDay, int returnDay,
            int cardYear, int cardMonth) {
        this.id = id;
        this.tag = tag;
        this.name = name;
        this.cardNum = cardNum;
        this.startDay = startDay;
        this.endDay = endDay;
        this.returnDay = returnDay;
        this.cardYear = cardYear;
        this.cardMonth = cardMonth;
    }
    @Generated(hash = 1504834693)
    public CreditCardBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getTag() {
        return this.tag;
    }
    public void setTag(int tag) {
        this.tag = tag;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCardNum() {
        return this.cardNum;
    }
    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
    public int getStartDay() {
        return this.startDay;
    }
    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }
    public int getEndDay() {
        return this.endDay;
    }
    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }
    public int getReturnDay() {
        return this.returnDay;
    }
    public void setReturnDay(int returnDay) {
        this.returnDay = returnDay;
    }
    public int getCardYear() {
        return this.cardYear;
    }
    public void setCardYear(int cardYear) {
        this.cardYear = cardYear;
    }
    public int getCardMonth() {
        return this.cardMonth;
    }
    public void setCardMonth(int cardMonth) {
        this.cardMonth = cardMonth;
    }

   
}
