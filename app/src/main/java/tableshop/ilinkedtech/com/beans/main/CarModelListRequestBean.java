package tableshop.ilinkedtech.com.beans.main;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.beans.main
 *  @文件名:   CarModelListRequestBean
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 16:48
 *  @描述：    TODO
 */

public class CarModelListRequestBean {
    public String brandId;//TODO 根据 brandId 查找对应品牌的可售车辆列表
    public String lowestPrice;
    public String highestPrice;
    public String noOfSeat;
    public String locale="zh_CN";//TODO 英语是"en_US"  中文是"zh_CN"
}
