package tableshop.ilinkedtech.com.beans.responbeans;

/*
 *  @文件名:   OrderListResponBean
 *  @创建者:   Wilson
 *  @创建时间:  2017/12/14 18:19
 *  @描述：    TODO
 */

import java.util.List;

import tableshop.ilinkedtech.com.beans.main.OrderListItemBean;

public class OrderListResponBean {

    /*
     * orderUid : null
     * transactionId : null
     * createAt : null
     * orderQrCode : null
     * shopList : null
     */

    public String                  message;
    public String                  status;
    public int                     totalElements;
    public int                     totalPages;
    public Object                  orderUid;
    public Object                  transactionId;
    public Object                  createAt;
    public Object                  orderQrCode;
    public List<Object>            shopList;
    public List<OrderListItemBean> datas;//商品的订单列表
    public List<OrderListItemBean> vehicleOrderDatas;//车辆的订单列表

}
