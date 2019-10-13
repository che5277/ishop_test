package tableshop.ilinkedtech.com.beans.main;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.beans
 *  @文件名:   WeChatRequestPayBean
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/21 18:56
 *  @描述：    TODO
 */

public class WeChatRequestPayBean {


    /**
     * outTradeNumber : 3100222293
     */

    public String outTradeNumber="vehicle_";//订单号，为随机数，后期由后台提供
    public String body;//TODO 支付宝支付的关联人
    public String attach;//TODO 微信支付的关联人
    public String batchNumber;
    public String vehicleSalesPrice;
    public String vehicleUid;
    public String supplierId;
}
