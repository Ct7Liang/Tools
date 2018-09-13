package android.develop.ct7liang.tools.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018-09-13.
 *
 */
@Entity
public class TextBean {

    @Id(autoincrement = true)
    public Long id;

    @NotNull
    public String content;

    public String title;

    @Generated(hash = 1009182698)
    public TextBean(Long id, @NotNull String content, String title) {
        this.id = id;
        this.content = content;
        this.title = title;
    }

    @Generated(hash = 809912491)
    public TextBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
