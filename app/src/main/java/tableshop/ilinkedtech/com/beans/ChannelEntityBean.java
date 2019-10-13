package tableshop.ilinkedtech.com.beans;

import tableshop.ilinkedtech.com.beans.main.CarModelListRequestBean;
import tableshop.ilinkedtech.com.utils.StringUtils;

/**
 *  @项目名：  iShop
 *  @包名：    ishop.ilinkedtech.com.views
 *  @文件名:   ChannelEntityBean
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/6 17:45
 * 频道实体类
 */
public class ChannelEntityBean {

    public String                  uid;
    public Object                  vinNoList;
    public String                  description;//频道的名称
    public CarModelListRequestBean requestBean;

    public ChannelEntityBean(){
    }
    public ChannelEntityBean(String name){
        this();
//        this.name=name;
        this.description=name;
    }

    @Override
    public boolean equals(Object o) {
        ChannelEntityBean o1 = (ChannelEntityBean) o;
        if (o1!=null&&this!=null&&!StringUtils.isEmpty(this.description)) {
            return o1.description.equals(this.description);
        }
        return super.equals(o);
    }

}
