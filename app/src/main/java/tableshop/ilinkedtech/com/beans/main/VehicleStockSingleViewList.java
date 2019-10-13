package tableshop.ilinkedtech.com.beans.main;

import java.io.Serializable;
import java.util.List;

public class VehicleStockSingleViewList implements Serializable{

    /**
     * bodyColorId : 01
     * brandId : 0052
     * brandName : D-大众
     * feature : {"drive":{"type":"String","value":"front"},"energy":{"type":"String","value":"gas"},"height":{"type":"String","value":"1500mm"},"length":{"type":"String","value":"4800mm"},"numberOfDoors":{"type":"Integer","value":5},"numberOfSeats":{"type":"Integer","value":5},"price":{"type":"Integer","value":100000},"vehicleEngineLayout":{"type":"String","value":"4wd"},"vehicleEngineType":{"type":"String","value":"3.0L"},"vehicleFuseSource":{"type":"String","value":"4AT"},"vehicleHorsePower":{"type":"String","value":"197N"},"width":{"type":"String","value":"2500mm"}}
     * interiorColorId : 2
     * modelId : 0052_0005_0001
     * modelName : 2017款 Crossgolf 1.4T 手自一体
     * noOfSeat : 0
     * price : 0.0
     * seriesBackgroundURL : http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/volkswagen/volkswagen/xianggang/xianggang/data/details/beetle_9477.png
     * seriesDefaultImageURL : http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/volkswagen/volkswagen/xianggang/xianggang/data/details/Car%20photo-640_0177.png
     * seriesId : 0052_0005
     * seriesName : Golf Cross
     * seriesPdfURL : ["http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/volkswagen/volkswagen/xianggang/xianggang/data/details/The Beetle GT 1.4TSI 160PS 20150611_2472.pdf"]
     * seriesQRCodeImageURL : http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/volkswagen/volkswagen/xianggang/xianggang/data/details/20130225140244508.jpg
     * seriesQRCodeText : http://www.vw.com.hk/en/models/beetle/gallery.html
     * seriesShareURL : http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/volkswagen/volkswagen/xianggang/xianggang/data/details/The Beetle_7589.png
     * seriesShowVideoURL : http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/volkswagen/volkswagen/xianggang/xianggang/data/details/12%20Tiguan_4742.mp4
     * seriesSmallImageURL : http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/volkswagen/volkswagen/xianggang/xianggang/data/details/The Beetle_4323.png
     * vinNo : 0012
     */

    public String bodyColorId;
    public String brandId;
    public String brandName;
    /**
     * drive : {"type":"String","value":"front"}
     * energy : {"type":"String","value":"gas"}
     * height : {"type":"String","value":"1500mm"}
     * length : {"type":"String","value":"4800mm"}
     * numberOfDoors : {"type":"Integer","value":5}
     * numberOfSeats : {"type":"Integer","value":5}
     * price : {"type":"Integer","value":100000}
     * vehicleEngineLayout : {"type":"String","value":"4wd"}
     * vehicleEngineType : {"type":"String","value":"3.0L"}
     * vehicleFuseSource : {"type":"String","value":"4AT"}
     * vehicleHorsePower : {"type":"String","value":"197N"}
     * width : {"type":"String","value":"2500mm"}
     */

    public FeatureBean feature;
    public String       interiorColorId;
    public String       modelId;
    public String       modelName;
    public int          noOfSeat;
    public double       price;
    public String       seriesBackgroundURL;
    public String       seriesDefaultImageURL;
    public String       seriesId;
    public String       seriesName;
    public String       seriesQRCodeImageURL;
    public String       seriesQRCodeText;
    public String       seriesShareURL;
    public String       seriesShowVideoURL;
    public String       seriesSmallImageURL;
    public String       vinNo;
    public List<String> seriesPdfURL;


    public static class FeatureBean implements Serializable{
        /**
         * type : String
         * value : front
         */

        public DriveBean               drive;
        /**
         * type : String
         * value : gas
         */

        public EnergyBean              energy;
        /**
         * type : String
         * value : 1500mm
         */

        public HeightBean              height;
        /**
         * type : String
         * value : 4800mm
         */

        public LengthBean              length;
        /**
         * type : Integer
         * value : 5
         */

        public NumberOfDoorsBean       numberOfDoors;
        /**
         * type : Integer
         * value : 5
         */

        public NumberOfSeatsBean       numberOfSeats;
        /**
         * type : Integer
         * value : 100000
         */

        public PriceBean               price;
        /**
         * type : String
         * value : 4wd
         */

        public VehicleEngineLayoutBean vehicleEngineLayout;
        /**
         * type : String
         * value : 3.0L
         */

        public VehicleEngineTypeBean   vehicleEngineType;
        /**
         * type : String
         * value : 4AT
         */

        public VehicleFuseSourceBean   vehicleFuseSource;
        /**
         * type : String
         * value : 197N
         */

        public VehicleHorsePowerBean   vehicleHorsePower;
        /**
         * type : String
         * value : 2500mm
         */

        private WidthBean               width;


        public static class DriveBean implements Serializable{
            public String type;
            public String value;
        }

        public static class EnergyBean implements Serializable{
            public String type;
            public String value;
        }

        public static class HeightBean implements Serializable{
            public String type;
            public String value;

        }

        public static class LengthBean implements Serializable{
            public String type;
            public String value;
        }

        public static class NumberOfDoorsBean implements Serializable{
            public String type;
            public int    value;

        }

        public static class NumberOfSeatsBean implements Serializable{
            public String type;
            public int    value;

        }

        public static class PriceBean implements Serializable{
            public String type;
            public int    value;
        }

        public static class VehicleEngineLayoutBean implements Serializable{
            public String type;
            public String value;

        }

        public static class VehicleEngineTypeBean implements Serializable{
            public String type;
            public String value;

        }

        public static class VehicleFuseSourceBean implements Serializable{
            public String type;
            public String value;

        }

        public static class VehicleHorsePowerBean implements Serializable{
            public String type;
            public String value;

        }

        public static class WidthBean implements Serializable{
            public String type;
            public String value;
        }
    }
}