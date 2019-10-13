package tableshop.ilinkedtech.com.beans.responbeans;

/*
 *  @文件名:   SeleteShopResponBean
 *  @创建者:   Wilson
 *  @创建时间:  2017/12/26 18:19
 *  @描述：    TODO
 */

import java.util.List;

public class SeleteShopResponBean {

    /**
     * message : save successfully!
     * status : 1
     * totalElements : 0
     * totalPages : 0
     * data : [{"uid":null,"num":null,"type":null,"productUid":null,"sumPrice":null,"price":null,"status":"MEMBER_SELECT_SHOP","vehicleSalesUid":"8c52efdf-8d08-49cc-814c-7c4f22e6566c","vehicleQrCode":null,"defaultImgUrl":null,"backgroundImgUrl":null,"vehicleBrandName":null,"vehicleSeriesName":null,"vehicleModelName":null,"colorName":null,"tabSysShopId":"238","tabSysShopAddress":"深圳市南山区学府路95号怡化金融科技大厦15楼1508","tabSysShopName":"办公室展厅"}]
     * orderUid : null
     * transactionId : null
     * createAt : null
     * orderQrCode : null
     * shopList : null
     */

    public String message;
    public String          status;
    public int             totalElements;
    public int             totalPages;
    public Object          orderUid;
    public Object          transactionId;
    public Object          createAt;
    public Object          orderQrCode;
    public Object          shopList;
    public List<DatasBean> datas;

    public static class DatasBean {
        /**
         * uid : null
         * num : null
         * type : null
         * productUid : null
         * sumPrice : null
         * price : null
         * status : MEMBER_SELECT_SHOP
         * vehicleSalesUid : 8c52efdf-8d08-49cc-814c-7c4f22e6566c
         * vehicleQrCode : null
         * defaultImgUrl : null
         * backgroundImgUrl : null
         * vehicleBrandName : null
         * vehicleSeriesName : null
         * vehicleModelName : null
         * colorName : null
         * tabSysShopId : 238
         * tabSysShopAddress : 深圳市南山区学府路95号怡化金融科技大厦15楼1508
         * tabSysShopName : 办公室展厅
         */

        public Object uid;
        public Object num;
        public Object type;
        public Object productCode;
        public Object sumPrice;
        public Object price;
        public String status;
        public String vehicleSalesUid;
        public Object vehicleQrCode;
        public Object defaultImgUrl;
        public Object backgroundImgUrl;
        public Object vehicleBrandName;
        public Object vehicleSeriesName;
        public Object vehicleModelName;
        public Object colorName;
        public String tabSysShopId;
        public String tabSysShopAddress;
        public String tabSysShopName;
    }
}
