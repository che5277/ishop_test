package tableshop.ilinkedtech.com.beans.events;

/*
 *  @文件名:   MenberSateBean
 *  @创建者:   Wilson
 *  @创建时间:  2018/1/23 10:19
 *  @描述：    TODO
 */

import android.os.Parcel;
import android.os.Parcelable;

public class FilterItemTypeBean
        implements Parcelable
{

    public int  itemType=-1;//1付款成功
    public int  itemPosition=-1;//1付款成功
    public static FilterItemTypeBean newInstance() {
        return new FilterItemTypeBean();
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.itemType);
        dest.writeInt(this.itemPosition);
    }

    public FilterItemTypeBean() {}

    protected FilterItemTypeBean(Parcel in) {
        this.itemType = in.readInt();
        this.itemPosition = in.readInt();
    }

    public static final Parcelable.Creator<FilterItemTypeBean> CREATOR = new Parcelable.Creator<FilterItemTypeBean>() {
        @Override
        public FilterItemTypeBean createFromParcel(Parcel source) {
            return new FilterItemTypeBean(source);
        }

        @Override
        public FilterItemTypeBean[] newArray(int size) {return new FilterItemTypeBean[size];}
    };
}
