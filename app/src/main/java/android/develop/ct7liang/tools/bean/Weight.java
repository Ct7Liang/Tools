package android.develop.ct7liang.tools.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by Administrator on 2018-07-25.
 *
 */

@Entity
public class Weight {

    @Id(autoincrement = true)
    public Long id;

    @NotNull
    private long time;

    @NotNull
    private String timeFormat;

    @NotNull
    private String year;

    @NotNull
    private String month;

    @NotNull
    private String date;

    @NotNull
    private float value;

    @Generated(hash = 469038088)
    public Weight(Long id, long time, @NotNull String timeFormat,
                  @NotNull String year, @NotNull String month, @NotNull String date,
                  float value) {
        this.id = id;
        this.time = time;
        this.timeFormat = timeFormat;
        this.year = year;
        this.month = month;
        this.date = date;
        this.value = value;
    }

    @Generated(hash = 1956860650)
    public Weight() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTimeFormat() {
        return this.timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float value) {
        this.value = value;
    }
    
}