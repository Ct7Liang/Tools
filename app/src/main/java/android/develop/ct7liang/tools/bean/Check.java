package android.develop.ct7liang.tools.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018-09-05.
 *
 */
@Entity
public class Check {

    @Id(autoincrement = true)
    public Long id;

    @NotNull
    public int year;

    @NotNull
    public int month;

    @NotNull
    public int date;

    @NotNull
    public int type;

    @NotNull
    public float cash;

    @NotNull
    public String desc;

    @NotNull
    public String time;

    @Generated(hash = 1005106176)
    public Check(Long id, int year, int month, int date, int type, float cash,
            @NotNull String desc, @NotNull String time) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.date = date;
        this.type = type;
        this.cash = cash;
        this.desc = desc;
        this.time = time;
    }

    @Generated(hash = 1080183208)
    public Check() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getCash() {
        return this.cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    

}
