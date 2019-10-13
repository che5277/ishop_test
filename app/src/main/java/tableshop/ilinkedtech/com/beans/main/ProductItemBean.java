package tableshop.ilinkedtech.com.beans.main;

/*
 *  @文件名:   ProductItemBean
 *  @创建者:   Wilson
 *  @创建时间:  2018/2/5 10:18
 *  @描述：    TODO
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import tableshop.ilinkedtech.com.utils.StringUtils;

public class ProductItemBean
        implements Parcelable
{

    public String uid;
    public String productUid;
    public String                  stringTypePrice;
    public int                     stock;
    public String                     sellingPrice;
    public String                  productCode;
    public long                    createAt;
    public String                  createBy;
    public String                  createUid;
    public long                    updateAt;
    public String                  updateBy;
    public String                  updateUid;
    public String                  publicProductLineses;
    public String                  publicProductSuppliers;
    public String                  publicProductDetailsBeans;
    public List<String> productCodeList;
    public String       sellingCount;
    public String       locale;
    public String       productName;
    public String       name;
    public String       productDescription;
    public String       colorName;
    public String       type;
    public String       price;
    public String       productQrData;
    public String       userName;
    public String       productDefaultImgUrl;
    public String       defaultImgUrl;
    public String       img1Url;
    public String       img2Url;
    public String       img3Url;
    public String       img4Url;
    public String       img5Url;
    public int          num;
    public String       shoppingCartUid;
    public String       category;
    public int          firstResult;
    public int          maxResult;
    public String       sortType;
    public List<String> productImgUrl;
    public String                  sortOrder;
    public boolean                 recommend;
    public boolean                 shelf;
    public List<ColorBeanListBean> colorBeanList;
    public boolean isFavorite;
    public boolean isSeleted;

    public static class ColorBeanListBean
            implements Parcelable
    {
        /**
         * colorId : 8e23209c-6c1d-4e5e-9f02-034f7b89b7a5
         * colorName : 黑
         * stock : 63
         */

        public String colorId;
        public String colorName;
        public int    stock;

        @Override
        public int describeContents() { return 0; }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.colorId);
            dest.writeString(this.colorName);
            dest.writeInt(this.stock);
        }

        public ColorBeanListBean() {}

        protected ColorBeanListBean(Parcel in) {
            this.colorId = in.readString();
            this.colorName = in.readString();
            this.stock = in.readInt();
        }

        public static final Creator<ColorBeanListBean> CREATOR = new Creator<ColorBeanListBean>() {
            @Override
            public ColorBeanListBean createFromParcel(Parcel source) {
                return new ColorBeanListBean(source);
            }

            @Override
            public ColorBeanListBean[] newArray(int size) {return new ColorBeanListBean[size];}
        };
    }

    @Override
    public boolean equals(Object o) {
        ProductItemBean o1 = (ProductItemBean) o;
        if (o1!=null&&this!=null) {
            if (!StringUtils.isEmpty(o1.uid)&&!StringUtils.isEmpty(this.uid)) {
                return o1.uid.equals(this.uid);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        ProductItemBean o1 = (ProductItemBean) this;
        if (o1!=null&&this!=null) {
            if (!StringUtils.isEmpty(o1.uid)&&!StringUtils.isEmpty(this.uid)) {
                return o1.uid.hashCode();
            }
        }
        return super.hashCode();
    }

    public ProductItemBean() {}

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.productUid);
        dest.writeString(this.stringTypePrice);
        dest.writeInt(this.stock);
        dest.writeString(this.sellingPrice);
        dest.writeString(this.productCode);
        dest.writeLong(this.createAt);
        dest.writeString(this.createBy);
        dest.writeString(this.createUid);
        dest.writeLong(this.updateAt);
        dest.writeString(this.updateBy);
        dest.writeString(this.updateUid);
        dest.writeString(this.publicProductLineses);
        dest.writeString(this.publicProductSuppliers);
        dest.writeString(this.publicProductDetailsBeans);
        dest.writeStringList(this.productCodeList);
        dest.writeString(this.sellingCount);
        dest.writeString(this.locale);
        dest.writeString(this.productName);
        dest.writeString(this.name);
        dest.writeString(this.productDescription);
        dest.writeString(this.colorName);
        dest.writeString(this.type);
        dest.writeString(this.price);
        dest.writeString(this.productQrData);
        dest.writeString(this.userName);
        dest.writeString(this.productDefaultImgUrl);
        dest.writeString(this.defaultImgUrl);
        dest.writeString(this.img1Url);
        dest.writeString(this.img2Url);
        dest.writeString(this.img3Url);
        dest.writeString(this.img4Url);
        dest.writeString(this.img5Url);
        dest.writeInt(this.num);
        dest.writeString(this.shoppingCartUid);
        dest.writeString(this.category);
        dest.writeInt(this.firstResult);
        dest.writeInt(this.maxResult);
        dest.writeString(this.sortType);
        dest.writeStringList(this.productImgUrl);
        dest.writeString(this.sortOrder);
        dest.writeByte(this.recommend
                       ? (byte) 1
                       : (byte) 0);
        dest.writeByte(this.shelf
                       ? (byte) 1
                       : (byte) 0);
        dest.writeTypedList(this.colorBeanList);
        dest.writeByte(this.isFavorite
                       ? (byte) 1
                       : (byte) 0);
        dest.writeByte(this.isSeleted
                       ? (byte) 1
                       : (byte) 0);
    }

    protected ProductItemBean(Parcel in) {
        this.uid = in.readString();
        this.productUid = in.readString();
        this.stringTypePrice = in.readString();
        this.stock = in.readInt();
        this.sellingPrice = in.readString();
        this.productCode = in.readString();
        this.createAt = in.readLong();
        this.createBy = in.readString();
        this.createUid = in.readString();
        this.updateAt = in.readLong();
        this.updateBy = in.readString();
        this.updateUid = in.readString();
        this.publicProductLineses = in.readString();
        this.publicProductSuppliers = in.readString();
        this.publicProductDetailsBeans = in.readString();
        this.productCodeList = in.createStringArrayList();
        this.sellingCount = in.readString();
        this.locale = in.readString();
        this.productName = in.readString();
        this.name = in.readString();
        this.productDescription = in.readString();
        this.colorName = in.readString();
        this.type = in.readString();
        this.price = in.readString();
        this.productQrData = in.readString();
        this.userName = in.readString();
        this.productDefaultImgUrl = in.readString();
        this.defaultImgUrl = in.readString();
        this.img1Url = in.readString();
        this.img2Url = in.readString();
        this.img3Url = in.readString();
        this.img4Url = in.readString();
        this.img5Url = in.readString();
        this.num = in.readInt();
        this.shoppingCartUid = in.readString();
        this.category = in.readString();
        this.firstResult = in.readInt();
        this.maxResult = in.readInt();
        this.sortType = in.readString();
        this.productImgUrl = in.createStringArrayList();
        this.sortOrder = in.readString();
        this.recommend = in.readByte() != 0;
        this.shelf = in.readByte() != 0;
        this.colorBeanList = in.createTypedArrayList(ColorBeanListBean.CREATOR);
        this.isFavorite = in.readByte() != 0;
        this.isSeleted = in.readByte() != 0;
    }

    public static final Creator<ProductItemBean> CREATOR = new Creator<ProductItemBean>() {
        @Override
        public ProductItemBean createFromParcel(Parcel source) {return new ProductItemBean(source);}

        @Override
        public ProductItemBean[] newArray(int size) {return new ProductItemBean[size];}
    };
}
