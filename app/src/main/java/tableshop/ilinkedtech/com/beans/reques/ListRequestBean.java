package tableshop.ilinkedtech.com.beans.reques;

import android.os.Parcel;
import android.os.Parcelable;

import tableshop.ilinkedtech.com.consts.KeyConst;

/*
 *  @项目名：  ICan2 
 *  @包名：    com.ilinkedtech.beans.dcc
 *  @文件名:   ListRequestBean
 *  @创建者:   Wilson
 *  @创建时间:  2017/3/30 16:08
 *  @描述：    TODO  列表请求的bean类
 */
public class ListRequestBean
        implements Parcelable
{
    private static final String TAG = "ListRequestBean";

    public String maxResults= KeyConst.ListD.MAX_RESULTS+"";
    public String pageNumber;
    public String brandId;
    public String brandName;
    public String seriesId;
    public String state;
    public String leastNoOfSeat;
    public String mostNoOfSeat;

    public String sortOrder;
    public String sortType;
    public String lowestPrice;
    public String highestPrice;
    public String outTradeNumber;
    public String searchText;
    public String category;//TODO 筛选车型
    public String locale="zh_CN";//TODO 语言

    public ListRequestBean() {}

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.maxResults);
        dest.writeString(this.pageNumber);
        dest.writeString(this.brandId);
        dest.writeString(this.brandName);
        dest.writeString(this.seriesId);
        dest.writeString(this.leastNoOfSeat);
        dest.writeString(this.mostNoOfSeat);
        dest.writeString(this.sortOrder);
        dest.writeString(this.sortType);
        dest.writeString(this.lowestPrice);
        dest.writeString(this.highestPrice);
        dest.writeString(this.outTradeNumber);
        dest.writeString(this.searchText);
        dest.writeString(this.category);
    }

    protected ListRequestBean(Parcel in) {
        this.maxResults = in.readString();
        this.pageNumber = in.readString();
        this.brandId = in.readString();
        this.brandName = in.readString();
        this.seriesId = in.readString();
        this.leastNoOfSeat = in.readString();
        this.mostNoOfSeat = in.readString();
        this.sortOrder = in.readString();
        this.sortType = in.readString();
        this.lowestPrice = in.readString();
        this.highestPrice = in.readString();
        this.outTradeNumber = in.readString();
        this.searchText = in.readString();
        this.category = in.readString();
    }

    public static final Creator<ListRequestBean> CREATOR = new Creator<ListRequestBean>() {
        @Override
        public ListRequestBean createFromParcel(Parcel source) {return new ListRequestBean(source);}

        @Override
        public ListRequestBean[] newArray(int size) {return new ListRequestBean[size];}
    };
}
