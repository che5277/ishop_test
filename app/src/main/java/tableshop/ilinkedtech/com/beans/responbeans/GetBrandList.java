package tableshop.ilinkedtech.com.beans.responbeans;

/*
 *  @文件名:   GetBrandList
 *  @创建者:   Wilson
 *  @创建时间:  2018/1/22 11:43
 *  @描述：    TODO
 */

import java.util.List;

import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.beans.main.CarModelListItemBean;

public class GetBrandList {


    /**
     * brandPage : [{"key":"A","value":[{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"29cb2198-97c8-4437-91ca-38d691d22e98","brandName":"阿斯顿马丁","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null}]},{"key":"B","value":[{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"0028","brandName":"BMW","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null},{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"0028","brandName":"宝马","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null},{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"03606930-429c-42bf-a7e8-6a98f5f4d67e","brandName":"Benz","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null},{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"03606930-429c-42bf-a7e8-6a98f5f4d67e","brandName":"奔驰","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null},{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"4f615cfe-2932-457a-8ea2-9f9cf357d4a8","brandName":"Bentley","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null},{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"4f615cfe-2932-457a-8ea2-9f9cf357d4a8","brandName":"宾利","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null},{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"c6bf5dfa-246c-4caa-bee2-5d6258220405","brandName":"Buick","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null},{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"c6bf5dfa-246c-4caa-bee2-5d6258220405","brandName":"别克","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null}]},{"key":"T","value":[{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"e408a41d-9eea-4d40-8971-4e327a06633c","brandName":"Toyota","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null}]},{"key":"F","value":[{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"afa055da-f60e-459f-b469-d1d0061f09f7","brandName":"福特","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null},{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"e408a41d-9eea-4d40-8971-4e327a06633c","brandName":"丰田","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null}]},{"key":"L","value":[{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"4b263d4d-7556-49d0-9747-7a89f48a8815","brandName":"Land Rover","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null},{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"4b263d4d-7556-49d0-9747-7a89f48a8815","brandName":"路虎","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null},{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"8be9c04f-d3e5-42b4-bbf9-017ddc20ef7d","brandName":"Lamborghini","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null}]},{"key":"M","value":[{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"5a826616-72ca-4481-a294-e475a3022e74","brandName":"玛莎拉蒂","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null}]}]
     * seriesPage : null
     */

    public List<BrandPageBean> brandPage;
    public List<CarDetailBean> topBrandPage;//TODO 热门品牌

    public CarModelListItemBean vehicleSalesPage;//TODO 在售车系（包含具体车辆）

    public List<CarModelListItemBean> seriesPage;//TODO 在售车系（不包含具体车辆）
    public List<CarModelListItemBean> popularVehicle;//TODO 流行的品牌

    public static class BrandPageBean {
        /**
         * key : A
         * value : [{"uid":null,"seriesDefaultImageURL":null,"seriesSmallImageURL":null,"seriesBackgroundURL":null,"seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":[],"brandId":"29cb2198-97c8-4437-91ca-38d691d22e98","brandName":"阿斯顿马丁","seriesId":null,"seriesName":null,"modelId":null,"modelName":null,"lowestPrice":null,"count":0,"vehicleStockSingleViewList":null,"purchaseVehicleViewList":null,"deliveryVehicleViewList":null,"insideSmallImgUrl":null,"outsideSmallImgUrl":null,"insideImgUrl":null,"outsideImgUrl":null,"message":null,"status":null}]
         */

        public String key;
        public List<CarDetailBean> value;

        public static class ValueBean {
            /**
             * uid : null
             * seriesDefaultImageURL : null
             * seriesSmallImageURL : null
             * seriesBackgroundURL : null
             * seriesShowVideoURL : null
             * seriesQRCodeText : null
             * seriesQRCodeImageURL : null
             * seriesShareURL : null
             * seriesPdfURL : []
             * brandId : 29cb2198-97c8-4437-91ca-38d691d22e98
             * brandName : 阿斯顿马丁
             * seriesId : null
             * seriesName : null
             * modelId : null
             * modelName : null
             * lowestPrice : null
             * count : 0
             * vehicleStockSingleViewList : null
             * purchaseVehicleViewList : null
             * deliveryVehicleViewList : null
             * insideSmallImgUrl : null
             * outsideSmallImgUrl : null
             * insideImgUrl : null
             * outsideImgUrl : null
             * message : null
             * status : null
             */


            public int type;
            public String pys;
            public Object uid;
            public Object seriesDefaultImageURL;
            public Object seriesSmallImageURL;
            public Object seriesBackgroundURL;
            public Object seriesShowVideoURL;
            public Object seriesQRCodeText;
            public Object seriesQRCodeImageURL;
            public Object seriesShareURL;
            public String brandId;
            public String brandName;
            public Object seriesId;
            public Object seriesName;
            public Object  modelId;
            public Object  modelName;
            public Object  lowestPrice;
            public int     count;
            public Object  vehicleStockSingleViewList;
            public Object  purchaseVehicleViewList;
            public Object  deliveryVehicleViewList;
            public Object  insideSmallImgUrl;
            public Object  outsideSmallImgUrl;
            public Object  insideImgUrl;
            public Object  outsideImgUrl;
            public Object  message;
            public Object  status;
            public List<?> seriesPdfURL;
        }
    }
}
