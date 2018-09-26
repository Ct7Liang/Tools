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
public class CreditReturnBean {

    @Id(autoincrement = true)
    public Long id;

    @NotNull
    public Long tag;

    @NotNull
    public String returnDate;

    @NotNull
    public String returnRecycle;

    @NotNull
    public String returnEndDay;

    @Generated(hash = 2057339170)
    public CreditReturnBean(Long id, @NotNull Long tag, @NotNull String returnDate,
            @NotNull String returnRecycle, @NotNull String returnEndDay) {
        this.id = id;
        this.tag = tag;
        this.returnDate = returnDate;
        this.returnRecycle = returnRecycle;
        this.returnEndDay = returnEndDay;
    }

    @Generated(hash = 151577684)
    public CreditReturnBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTag() {
        return this.tag;
    }

    public void setTag(Long tag) {
        this.tag = tag;
    }

    public String getReturnDate() {
        return this.returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnRecycle() {
        return this.returnRecycle;
    }

    public void setReturnRecycle(String returnRecycle) {
        this.returnRecycle = returnRecycle;
    }

    public String getReturnEndDay() {
        return this.returnEndDay;
    }

    public void setReturnEndDay(String returnEndDay) {
        this.returnEndDay = returnEndDay;
    }

    

}
