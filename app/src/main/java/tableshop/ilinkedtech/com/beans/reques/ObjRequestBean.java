package tableshop.ilinkedtech.com.beans.reques;

/*
 *  @文件名:   ObjRequestBean
 *  @创建者:   Wilson
 *  @创建时间:  2018/1/29 18:05
 *  @描述：    TODO
 */

import java.util.List;

public class ObjRequestBean {
    public String vehicleId;
    public String flag;//车辆详情用于判断是否已售
    public String userName;
    public String fromShoppingCart;
    public String state;//编辑之选
    public Object data;
    public List<Object>   datas;
    public List<String>   shoppingCartUidList;
    public String typesWithoutWatermark="N";
}
