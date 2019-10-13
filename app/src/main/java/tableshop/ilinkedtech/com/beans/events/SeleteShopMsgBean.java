package tableshop.ilinkedtech.com.beans.events;

/*
 *  @文件名:   SeleteShopMsgBean
 *  @创建者:   Wilson
 *  @创建时间:  2018/2/9 10:06
 *  @描述：    TODO
 */

import com.baidu.location.BDLocation;

public class SeleteShopMsgBean {
    public int msgWhat=0;
    public int position=0;
    public static final int MSG_LOCATION=1;
    public static final int MSG_SELETE_SHOP=2;
    public BDLocation location;
    public String tabSysShopId;

    public SeleteShopMsgBean(String tabSysShopId){
        this.tabSysShopId=tabSysShopId;
        msgWhat=MSG_SELETE_SHOP;
    }
    public SeleteShopMsgBean(BDLocation location){
        this.location=location;
        msgWhat=MSG_LOCATION;
    }
    public SeleteShopMsgBean(int position){
        this.position=position;
        msgWhat=MSG_LOCATION;
    }
}
