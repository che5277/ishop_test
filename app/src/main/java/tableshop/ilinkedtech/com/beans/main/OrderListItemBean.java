package tableshop.ilinkedtech.com.beans.main;

/*
 *  @文件名:   OrderListItemBean
 *  @创建者:   Wilson
 *  @创建时间:  2017/12/14 18:19
 *  @描述：    TODO
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class OrderListItemBean
        implements Parcelable
{
    /**
     * uid : null
     * num : null
     * type : null
     * productUid : null
     * sumPrice : null
     * price : null
     * status : MEMBER_SELECT_SHOP
     * vehicleSalesUid : 52a6c668-a26a-4426-be56-2072d747536e
     * vehicleQrCode : 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
     * productDefaultImgUrl : http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/series_details/2t2s_20170904174405_9858.png
     * backgroundImgUrl : http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/series_details/pxyr_20170904174424_n8tk.png
     * vehicleBrandName : L-兰博基尼
     * vehicleSeriesName : Huracan
     * vehicleModelName : 610-4
     * colorName : 磨砂蓝
     * tabSysShopId : 241
     * tabSysShopAddress : 深圳市深圳大学
     * tabSysShopName : 大学展厅
     */

    public String uid;
    public String productUid;
    public String type;

    public Integer num;
    public Double sumPrice;
    public Double price;

    public String productCode;
    public String status;
    public String flag;
    public String vehicleSalesUid;
    public String vehicleQrCode;
    public String defaultImgUrl;
    public String backgroundImgUrl;
    public String vehicleBrandName;
    public String vehicleSeriesName;
    public String vehicleModelName;
    public String colorName;
    public String tabSysShopId;
    public String tabSysShopAddress;
    public String tabSysShopName;

    public String category;//   车："vehicle"
    public List<ProductItemBean>   productDetails;
    public String totalAmount;

    public OrderListItemBean() {}

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.type);
        dest.writeValue(this.num);
        dest.writeValue(this.sumPrice);
        dest.writeValue(this.price);
        dest.writeString(this.productCode);
        dest.writeString(this.status);
        dest.writeString(this.vehicleSalesUid);
        dest.writeString(this.vehicleQrCode);
        dest.writeString(this.defaultImgUrl);
        dest.writeString(this.backgroundImgUrl);
        dest.writeString(this.vehicleBrandName);
        dest.writeString(this.vehicleSeriesName);
        dest.writeString(this.vehicleModelName);
        dest.writeString(this.colorName);
        dest.writeString(this.tabSysShopId);
        dest.writeString(this.tabSysShopAddress);
        dest.writeString(this.tabSysShopName);
        dest.writeString(this.category);
        dest.writeTypedList(this.productDetails);
        dest.writeString(this.totalAmount);
    }

    protected OrderListItemBean(Parcel in) {
        this.uid = in.readString();
        this.type = in.readString();
        this.num = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sumPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.price = (Double) in.readValue(Double.class.getClassLoader());
        this.productCode = in.readString();
        this.status = in.readString();
        this.vehicleSalesUid = in.readString();
        this.vehicleQrCode = in.readString();
        this.defaultImgUrl = in.readString();
        this.backgroundImgUrl = in.readString();
        this.vehicleBrandName = in.readString();
        this.vehicleSeriesName = in.readString();
        this.vehicleModelName = in.readString();
        this.colorName = in.readString();
        this.tabSysShopId = in.readString();
        this.tabSysShopAddress = in.readString();
        this.tabSysShopName = in.readString();
        this.category = in.readString();
        this.productDetails = in.createTypedArrayList(ProductItemBean.CREATOR);
        this.totalAmount = in.readString();
    }

    public static final Creator<OrderListItemBean> CREATOR = new Creator<OrderListItemBean>() {
        @Override
        public OrderListItemBean createFromParcel(Parcel source) {
            return new OrderListItemBean(source);
        }

        @Override
        public OrderListItemBean[] newArray(int size) {return new OrderListItemBean[size];}
    };
}
