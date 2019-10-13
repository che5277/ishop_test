package tableshop.ilinkedtech.com.beans.main;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.beans
 *  @文件名:   CarModelListItemBean
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 16:00
 *  @描述：    TODO 获取model的列表数据，用于选车页面
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import tableshop.ilinkedtech.com.utils.StringUtils;

public class CarModelListItemBean
        implements Parcelable
{
    public double                         price;
    public boolean isFavorite;
    public boolean isContrast;
    /**
     * uid : null
     * seriesDefaultImageURL : http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/volkswagen/volkswagen/xianggang/xianggang/data/details/The Beetle_7589.png
     * seriesSmallImageURL : http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/volkswagen/volkswagen/xianggang/xianggang/data/details/The Beetle_4323.png
     * seriesBackgroundURL : http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/volkswagen/volkswagen/xianggang/xianggang/data/details/beetle_9477.png
     * seriesShowVideoURL : http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/volkswagen/volkswagen/xianggang/xianggang/data/details/iPadBlankScreen_4490.wmv
     * seriesQRCodeText : http://www.vw.com.hk/en/models/beetle/gallery.html
     * seriesQRCodeImageURL : http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/volkswagen/volkswagen/xianggang/xianggang/data/details/20130225140244508.jpg
     * seriesShareURL : http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/volkswagen/volkswagen/xianggang/xianggang/data/details/The Beetle_7589.png
     * seriesPdfURL : ["http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/volkswagen/volkswagen/xianggang/xianggang/data/details/The Beetle GT 1.4TSI 160PS 20150611_2472.pdf","http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/","http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/","http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/","http://icansell.oss-cn-shenzhen.aliyuncs.com/icandcr/"]
     * brandId : 0052
     * brandName : D-大众
     * seriesId : 0052_0005
     * seriesName : Golf Cross
     * modelId : 0052_0005_0001
     * modelName : 2011款 Crossgolf 1.4T 手自一体 减税
     * lowestPrice : 0
     * count : 1
     * vehicleStockSingleViewList : [{"price":0,"bodyColorId":"black","bodyColorName":null,"interiorColorId":"gray","interiorColorName":null,"feature":{"price":{"value":110000,"type":"Integer"},"seat":{"value":5,"type":"Integer"}}}]
     *  "message": "2018年热门新车",
     "status": null,
     "title": "热门之选"
     */


    public String                         uid;
    public String                         message;
    public String                         status;
    public String                         state;
    public String                         title;
    public String                         seriesDefaultImageURL;
    public String                         seriesSmallImageURL;
    public String                         seriesBackgroundURL;
    public String                         seriesShowVideoURL;
    public String                         seriesQRCodeText;
    public String                         seriesQRCodeImageURL;
    public String                         seriesShareURL;
    public List<CarDetailBean> vehicleStockSingleViewList;//TODO 旧的是 vehicleStockSingleViewList
    public List<CarDetailBean> purchaseVehicleViewList;//TODO  已预订
    public List<CarDetailBean> deliveryVehicleViewList;//TODO  已售
    public String                           brandId;
    public String                           brandName;
    public String                           seriesId;
    public String                           seriesName;
    public String                           modelId;
    public String                           modelName;
    public double                              lowestPrice;
    public String                              minPrice;
    public int                              count;
    public List<String>                     seriesPdfURL;


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
     * lowestPrice : 9.99999999999999E12
     * vehicleStockSingleViewList : []
     */


    @Override
    public String toString() {
        return "CarModelListItemBean{" + "price='" + price + '\'' + ", uid=" + uid + ", seriesDefaultImageURL='" + seriesDefaultImageURL + '\'' + ", seriesSmallImageURL='" + seriesSmallImageURL + '\'' + ", seriesBackgroundURL='" + seriesBackgroundURL + '\'' + ", seriesShowVideoURL='" + seriesShowVideoURL + '\'' + ", seriesQRCodeText='" + seriesQRCodeText + '\'' + ", seriesQRCodeImageURL='" + seriesQRCodeImageURL + '\'' + ", seriesShareURL='" + seriesShareURL + '\'' + ", brandId='" + brandId + '\'' + ", brandName='" + brandName + '\'' + ", seriesId='" + seriesId + '\'' + ", seriesName='" + seriesName + '\'' + ", modelId='" + modelId + '\'' + ", modelName='" + modelName + '\'' + ", lowestPrice=" + lowestPrice + ", count=" + count + ", seriesPdfURL=" + seriesPdfURL + ", vehicleStockSingleViewList=" + vehicleStockSingleViewList + '}';
    }

    @Override
    public boolean equals(Object o) {
        CarModelListItemBean o1 = (CarModelListItemBean) o;
        if (o1!=null&&this!=null) {
            if (!StringUtils.isEmpty(o1.modelId)&& !StringUtils.isEmpty(this.modelId)) {
                return o1.modelId.equals(this.modelId);
            }
            if (!StringUtils.isEmpty(o1.seriesId) && !StringUtils.isEmpty(this.seriesId)) {
                return o1.seriesId.equals(this.seriesId);
            }
        }
        return super.equals(o);
    }

//    @Override
//    public int hashCode() {
//        CarModelListItemBean carModelListItemBean = (CarModelListItemBean) this;
//        if (carModelListItemBean !=null&&!StringUtils.isEmpty(carModelListItemBean.modelId)) {
//            return carModelListItemBean.modelId.hashCode();
//        }else {
//            return super.hashCode();
//        }
//    }

    public CarModelListItemBean() {}

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.price);
        dest.writeByte(this.isFavorite
                       ? (byte) 1
                       : (byte) 0);
        dest.writeByte(this.isContrast
                       ? (byte) 1
                       : (byte) 0);
        dest.writeString(this.uid);
        dest.writeString(this.message);
        dest.writeString(this.status);
        dest.writeString(this.state);
        dest.writeString(this.title);
        dest.writeString(this.seriesDefaultImageURL);
        dest.writeString(this.seriesSmallImageURL);
        dest.writeString(this.seriesBackgroundURL);
        dest.writeString(this.seriesShowVideoURL);
        dest.writeString(this.seriesQRCodeText);
        dest.writeString(this.seriesQRCodeImageURL);
        dest.writeString(this.seriesShareURL);
        dest.writeTypedList(this.vehicleStockSingleViewList);
        dest.writeTypedList(this.purchaseVehicleViewList);
        dest.writeTypedList(this.deliveryVehicleViewList);
        dest.writeString(this.brandId);
        dest.writeString(this.brandName);
        dest.writeString(this.seriesId);
        dest.writeString(this.seriesName);
        dest.writeString(this.modelId);
        dest.writeString(this.modelName);
        dest.writeDouble(this.lowestPrice);
        dest.writeString(this.minPrice);
        dest.writeInt(this.count);
        dest.writeStringList(this.seriesPdfURL);
    }

    protected CarModelListItemBean(Parcel in) {
        this.price = in.readDouble();
        this.isFavorite = in.readByte() != 0;
        this.isContrast = in.readByte() != 0;
        this.uid = in.readString();
        this.message = in.readString();
        this.status = in.readString();
        this.state = in.readString();
        this.title = in.readString();
        this.seriesDefaultImageURL = in.readString();
        this.seriesSmallImageURL = in.readString();
        this.seriesBackgroundURL = in.readString();
        this.seriesShowVideoURL = in.readString();
        this.seriesQRCodeText = in.readString();
        this.seriesQRCodeImageURL = in.readString();
        this.seriesShareURL = in.readString();
        this.vehicleStockSingleViewList = in.createTypedArrayList(CarDetailBean.CREATOR);
        this.purchaseVehicleViewList = in.createTypedArrayList(CarDetailBean.CREATOR);
        this.deliveryVehicleViewList = in.createTypedArrayList(CarDetailBean.CREATOR);
        this.brandId = in.readString();
        this.brandName = in.readString();
        this.seriesId = in.readString();
        this.seriesName = in.readString();
        this.modelId = in.readString();
        this.modelName = in.readString();
        this.lowestPrice = in.readDouble();
        this.minPrice = in.readString();
        this.count = in.readInt();
        this.seriesPdfURL = in.createStringArrayList();
    }

    public static final Creator<CarModelListItemBean> CREATOR = new Creator<CarModelListItemBean>() {
        @Override
        public CarModelListItemBean createFromParcel(Parcel source) {
            return new CarModelListItemBean(source);
        }

        @Override
        public CarModelListItemBean[] newArray(int size) {return new CarModelListItemBean[size];}
    };
}
