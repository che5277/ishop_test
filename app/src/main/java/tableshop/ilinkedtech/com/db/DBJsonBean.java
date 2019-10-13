package tableshop.ilinkedtech.com.db;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/*
 *  @项目名：  iShop
 *  @包名：    ishop.ilinkedtech.com.db
 *  @文件名:   DBJsonBean
 *  @创建者:   Wilson
 *  @创建时间:  2017/6/27 14:43
 *  @描述：    TODO    网络请求离线本地缓存数据库插入对象
 */
@Entity
public class DBJsonBean {
    @Id
    private Long   id;
    @Property(nameInDb = "APIKEY")
    private String apiKey;
    @Property(nameInDb = "RESPONDATA")
    private String data;
    @Property(nameInDb = "UPDATETIME")
    private String updateTime;
    @Generated(hash = 1731910274)
    public DBJsonBean(Long id, String apiKey, String data, String updateTime) {
        this.id = id;
        this.apiKey = apiKey;
        this.data = data;
        this.updateTime = updateTime;
    }
    @Generated(hash = 339307974)
    public DBJsonBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getApiKey() {
        return this.apiKey;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    


}
