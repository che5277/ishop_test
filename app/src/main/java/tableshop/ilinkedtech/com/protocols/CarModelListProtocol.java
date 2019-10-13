package tableshop.ilinkedtech.com.protocols;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.protocols
 *  @文件名:   CarModelListProtocol
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 16:49
 *  @描述：    TODO
 */

import android.app.Activity;

import tableshop.ilinkedtech.com.beans.main.CarModelListRequestBean;
import tableshop.ilinkedtech.com.beans.main.CarModelListItemBean;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.protocols.basepros.ListProtocol;

public abstract class CarModelListProtocol
        extends ListProtocol<CarModelListRequestBean,CarModelListItemBean>
{

    public CarModelListProtocol(Activity activity,
                                CarModelListRequestBean carModelListRequestBean)
    {
        this(activity,carModelListRequestBean ,false);
    }
    public CarModelListProtocol(Activity activity,
                                CarModelListRequestBean carModelListRequestBean,boolean isLoadDataFromNet)
    {
        super(activity, false, Const.GET_VEHICLE, isLoadDataFromNet,carModelListRequestBean, CarModelListItemBean.class);
    }

}
