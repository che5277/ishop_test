package tableshop.ilinkedtech.com.beans.responbeans;

/*
 *  @文件名:   GetShopListResponBean
 *  @创建者:   Wilson
 *  @创建时间:  2017/12/26 16:35
 *  @描述：    TODO
 */

import java.util.List;

import tableshop.ilinkedtech.com.beans.ShopListBean;

public class GetShopListResponBean {

    /**
     * message : null
     * status : null
     * totalElements : 0
     * totalPages : 0
     * datas : null
     * orderUid : null
     * transactionId : null
     * createAt : null
     * orderQrCode : null
     * shopList : [{"locationX":"22.5233597965","locationY":"113.9413332939","shopAddress":"深圳市南山区软件产业基地5C","shopTitleUrl":null,"shopImageUrl":null,"shopOnlineTime":null,"distance":"654.0","shopName":"5C展厅","tabSysShopId":"239"},{"locationX":"22.5243900000","locationY":"113.9358300000","shopAddress":"深圳市南山区学府路95号怡化金融科技大厦15楼1508","shopTitleUrl":null,"shopImageUrl":null,"shopOnlineTime":null,"distance":"850.0","shopName":"办公室展厅","tabSysShopId":"238"},{"locationX":"22.5390967380","locationY":"113.9366126060","shopAddress":"深圳市深圳大学","shopTitleUrl":null,"shopImageUrl":null,"shopOnlineTime":null,"distance":"1249.0","shopName":"大学展厅","tabSysShopId":"241"},{"locationX":"22.5170565892","locationY":"114.0658092499","shopAddress":"深圳市福田区福田口岸","shopTitleUrl":null,"shopImageUrl":null,"shopOnlineTime":null,"distance":"12775.0","shopName":"口岸展厅","tabSysShopId":"240"}]
     */

    public String             message;
    public String             status;
    public int                totalElements;
    public int                totalPages;
    public List<Object>             datas;
    public String             orderUid;
    public String             transactionId;
    public String             createAt;
    public Object             orderQrCode;
    public List<ShopListBean> shopList;

}
