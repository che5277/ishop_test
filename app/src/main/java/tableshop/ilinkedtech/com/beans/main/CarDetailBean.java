package tableshop.ilinkedtech.com.beans.main;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.beans.main
 *  @文件名:   CarDetailBean
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/14 14:48
 *  @描述：    TODO
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import tableshop.ilinkedtech.com.utils.StringUtils;

public class CarDetailBean
        implements Parcelable
{
    public boolean isFavorite;
    public boolean isContrast;
    public boolean isSeleted;//是否被选中进行比对

    /**
     * uid : null
     * price : 67
     * bodyColorId : 白
     * bodyColorName : null
     * interiorColorId : 黑
     * interiorColorName : null
     * feature : {}
     * vinNo : 11111111111111103
     * seriesDefaultImageURL : null
     * seriesSmallImageURL : null
     * seriesBackgroundURL : null
     * seriesShowVideoURL : null
     * seriesQRCodeText : null
     * seriesQRCodeImageURL : null
     * seriesShareURL : null
     * seriesPdfURL : []
     * brandId : f2488abc-3839-4951-8647-505048690b78
     * brandName : 吉利
     * seriesId : 6b122739-b740-46d4-9fd2-79095003ace8
     * seriesName : 吉利车系1
     * modelId : 3c3eecd9-d72d-4d22-adee-ded153d363ee
     * modelName : 吉利车型2
     * noOfSeat : 0
     * bodyLength : 0
     * bodyWidth : 0
     * bodyHeight : 0
     * noOfDoors : 0
     * fuseSource : null
     * horsePower : null
     * engineType : null
     * engineLayout : null
     */

    public int sellableState;//TODO 商品状态 自定义
    public String uid;

//    public double         price;
    public String         price;
    public String         createAt;
    public String      bodyColorId;
    public String      bodyColorName;
    public String      interiorColorId;
    public String      interiorColorName;
//    public Object feature;
    public String      vehicleFeatures;
    public String      vinNo;
    public String      seriesDefaultImageURL;
    public String      seriesSmallImageURL;
    public String      seriesBackgroundURL;
    public String      seriesShowVideoURL;
    public String      seriesQRCodeText;
    public String      seriesQRCodeImageURL;
    public String      seriesShareURL;
    public String      logoImageURL;
    public String      brandId;
    public String      brandName;
    public String      seriesId;
    public String      seriesName;
    public String      modelId;
    public String      modelName;
    public int         bodyLength;
    public int         bodyWidth;
    public int         bodyHeight;
    public String         lengthWidthHeight;
    public int         noOfSeat;//座位数
    public int         noOfDoors;//门数
    public String      fuseSource;//燃油
    public String      horsePower;//马力
    public String      engineType;//驱动方式
    public String      gearBox ;//TODO 变速箱
    public String      engineLayout;//TODO 排量
    public String      category;//TODO 车型
    public String oilSupply;//TODO 供油方式
    public String hubSize;//TODO 轮胎尺寸
    public String tankCapacity;//TODO 油箱容积
    public List<String>     seriesPdfURL;
    public List<String>     insideSmallImgUrl;
    public List<String>     outsideSmallImgUrl;
    public List<String>     insideImgUrl;
    public List<String>     outsideImgUrl;
    public List<String>     angleUrl;
    public String     bodyColorUrl;
    public String     interiorColorUrl;
    public String     supplierIdentityNo;//TODO 供应商编号
    public String     vehicleSalesUid;//新增用于支付
    public String     batchNumber;//新增用于支付
    public String     supplierCompanyUid;//新增用于支付
    public String     stringTypePrice;//支付价格
    public String     flag;//车辆销售状态  1 2  已预订    3 已售  0 在售

    public String pys;
    public String count;
    public int type;

    @Override
    public boolean equals(Object o) {
        CarDetailBean o1 = (CarDetailBean) o;
        if (o1!=null&&this!=null) {
            if (!StringUtils.isEmpty(o1.vehicleSalesUid)&&!StringUtils.isEmpty(this.vehicleSalesUid)) {
                return o1.vehicleSalesUid.equals(this.vehicleSalesUid);
            }else if (!StringUtils.isEmpty(o1.uid)&&!StringUtils.isEmpty(this.uid)) {
                return o1.uid.equals(this.uid);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        CarDetailBean carDetailBean = (CarDetailBean) this;
        if (carDetailBean !=null&&!StringUtils.isEmpty(carDetailBean.vehicleSalesUid)) {
//            return (carDetailBean.modelId+carDetailBean.vinNo).hashCode();
            return carDetailBean.vehicleSalesUid.hashCode();
        }else {
            return super.hashCode();
        }
    }

    public CarDetailBean() {}

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isFavorite
                       ? (byte) 1
                       : (byte) 0);
        dest.writeByte(this.isContrast
                       ? (byte) 1
                       : (byte) 0);
        dest.writeByte(this.isSeleted
                       ? (byte) 1
                       : (byte) 0);
        dest.writeInt(this.sellableState);
        dest.writeString(this.uid);
        dest.writeString(this.price);
        dest.writeString(this.bodyColorId);
        dest.writeString(this.bodyColorName);
        dest.writeString(this.interiorColorId);
        dest.writeString(this.interiorColorName);
        dest.writeString(this.vehicleFeatures);
        dest.writeString(this.vinNo);
        dest.writeString(this.seriesDefaultImageURL);
        dest.writeString(this.seriesSmallImageURL);
        dest.writeString(this.seriesBackgroundURL);
        dest.writeString(this.seriesShowVideoURL);
        dest.writeString(this.seriesQRCodeText);
        dest.writeString(this.seriesQRCodeImageURL);
        dest.writeString(this.seriesShareURL);
        dest.writeString(this.logoImageURL);
        dest.writeString(this.brandId);
        dest.writeString(this.brandName);
        dest.writeString(this.seriesId);
        dest.writeString(this.seriesName);
        dest.writeString(this.modelId);
        dest.writeString(this.modelName);
        dest.writeInt(this.bodyLength);
        dest.writeInt(this.bodyWidth);
        dest.writeInt(this.bodyHeight);
        dest.writeString(this.lengthWidthHeight);
        dest.writeInt(this.noOfSeat);
        dest.writeInt(this.noOfDoors);
        dest.writeString(this.fuseSource);
        dest.writeString(this.horsePower);
        dest.writeString(this.engineType);
        dest.writeString(this.gearBox);
        dest.writeString(this.engineLayout);
        dest.writeString(this.category);
        dest.writeString(this.oilSupply);
        dest.writeString(this.hubSize);
        dest.writeString(this.tankCapacity);
        dest.writeStringList(this.seriesPdfURL);
        dest.writeStringList(this.insideSmallImgUrl);
        dest.writeStringList(this.outsideSmallImgUrl);
        dest.writeStringList(this.insideImgUrl);
        dest.writeStringList(this.outsideImgUrl);
        dest.writeStringList(this.angleUrl);
        dest.writeString(this.bodyColorUrl);
        dest.writeString(this.interiorColorUrl);
        dest.writeString(this.vehicleSalesUid);
        dest.writeString(this.batchNumber);
        dest.writeString(this.supplierCompanyUid);
        dest.writeString(this.stringTypePrice);
        dest.writeString(this.flag);
        dest.writeString(this.pys);
        dest.writeString(this.count);
        dest.writeInt(this.type);
    }

    protected CarDetailBean(Parcel in) {
        this.isFavorite = in.readByte() != 0;
        this.isContrast = in.readByte() != 0;
        this.isSeleted = in.readByte() != 0;
        this.sellableState = in.readInt();
        this.uid = in.readString();
        this.price = in.readString();
        this.bodyColorId = in.readString();
        this.bodyColorName = in.readString();
        this.interiorColorId = in.readString();
        this.interiorColorName = in.readString();
        this.vehicleFeatures = in.readString();
        this.vinNo = in.readString();
        this.seriesDefaultImageURL = in.readString();
        this.seriesSmallImageURL = in.readString();
        this.seriesBackgroundURL = in.readString();
        this.seriesShowVideoURL = in.readString();
        this.seriesQRCodeText = in.readString();
        this.seriesQRCodeImageURL = in.readString();
        this.seriesShareURL = in.readString();
        this.logoImageURL = in.readString();
        this.brandId = in.readString();
        this.brandName = in.readString();
        this.seriesId = in.readString();
        this.seriesName = in.readString();
        this.modelId = in.readString();
        this.modelName = in.readString();
        this.bodyLength = in.readInt();
        this.bodyWidth = in.readInt();
        this.bodyHeight = in.readInt();
        this.lengthWidthHeight = in.readString();
        this.noOfSeat = in.readInt();
        this.noOfDoors = in.readInt();
        this.fuseSource = in.readString();
        this.horsePower = in.readString();
        this.engineType = in.readString();
        this.gearBox = in.readString();
        this.engineLayout = in.readString();
        this.category = in.readString();
        this.oilSupply = in.readString();
        this.hubSize = in.readString();
        this.tankCapacity = in.readString();
        this.seriesPdfURL = in.createStringArrayList();
        this.insideSmallImgUrl = in.createStringArrayList();
        this.outsideSmallImgUrl = in.createStringArrayList();
        this.insideImgUrl = in.createStringArrayList();
        this.outsideImgUrl = in.createStringArrayList();
        this.angleUrl = in.createStringArrayList();
        this.bodyColorUrl = in.readString();
        this.interiorColorUrl = in.readString();
        this.vehicleSalesUid = in.readString();
        this.batchNumber = in.readString();
        this.supplierCompanyUid = in.readString();
        this.stringTypePrice = in.readString();
        this.flag = in.readString();
        this.pys = in.readString();
        this.count = in.readString();
        this.type = in.readInt();
    }

    public static final Creator<CarDetailBean> CREATOR = new Creator<CarDetailBean>() {
        @Override
        public CarDetailBean createFromParcel(Parcel source) {return new CarDetailBean(source);}

        @Override
        public CarDetailBean[] newArray(int size) {return new CarDetailBean[size];}
    };
}
