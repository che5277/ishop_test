package tableshop.ilinkedtech.com.protocols;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.protocols
 *  @文件名:   GetBrandsSalesList
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/17 11:21
 *  @描述：    TODO
 */

import android.app.Activity;

import tableshop.ilinkedtech.com.beans.ChannelEntityBean;
import tableshop.ilinkedtech.com.beans.main.CarModelListRequestBean;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.protocols.basepros.ListProtocol;

public abstract class GetBrandsSalesList extends ListProtocol<CarModelListRequestBean,ChannelEntityBean> {
    public GetBrandsSalesList(Activity activity,
                              CarModelListRequestBean carModelListRequestBean)
    {
        this(activity,carModelListRequestBean ,false);
    }
    public GetBrandsSalesList(Activity activity,
                              CarModelListRequestBean carModelListRequestBean,boolean isLoadDataFromNet)
    {
        super(activity, false, Const.GET_VEHICLE_BRANDS_SALES,isLoadDataFromNet, carModelListRequestBean, ChannelEntityBean.class);
    }

}
