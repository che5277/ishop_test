package tableshop.ilinkedtech.com.beans;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.beans
 *  @文件名:   WeChatPayResponBean
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/21 18:56
 *  @描述：    TODO
 */

public class WeChatPayResponBean {

    /**
     {
     "appid": "wxe3c9f2c48e686c5f",
     "noncestr": null,
     "package": "Sign=WXPay",
     "partnerid": "1484924312",
     "prepayid": null,
     "sign": "D3BFCF220EE3BDEE1009580A416AADE6",
     "timestamp": "1500867297"
     }
     */

    public String timestamp;/** 时间戳，防重发 */
    public String noncestr;/** 随机串，防重发 */
    public String appid="wxe3c9f2c48e686c5f";
    public String sign;/** 商家根据微信开放平台文档对数据做的签名 */
//    public String package;
    public String trade_type;
    public String return_msg;
    public String result_code;
    public String partnerid;/** "partnerid"商家向财付通申请的商家id TODO 可以写死*/
    public String return_code;
    public String prepayid;/** 预支付订单 */
}
