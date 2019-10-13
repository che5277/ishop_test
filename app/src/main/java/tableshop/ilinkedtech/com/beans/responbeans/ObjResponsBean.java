package tableshop.ilinkedtech.com.beans.responbeans;

/*
 *  @文件名:   ObjResponsBean
 *  @创建者:   Wilson
 *  @创建时间:  2018/2/5 16:02
 *  @描述：    TODO
 */

import java.util.List;

import tableshop.ilinkedtech.com.beans.main.ProductItemBean;

public class ObjResponsBean {


    /**
     * message : 保存购物车
     * status : 1
     * totalElements : 0
     * totalPages : 0
     * datas : null
     * vehicleOrderDatas : null
     * orderUid : null
     * transactionId : null
     * attach : null
     * createAt : null
     * orderQrCode : null
     * shopList : null
     * totalAmount : null
     */

    public String                message;
    public String                status;
    public int                   totalElements;
    public int                   totalPages;
    public List<ProductItemBean> datas;
    public Object                data;
    public Object                vehicleOrderDatas;
    public Object                orderUid;
    public String                transactionId;//TODO 订单号 支付使用
    public String                attach;//TODO 关联人 支付使用
    public Object                createAt;
    public Object                orderQrCode;
    public Object                shopList;
    public Object                totalAmount;
}
