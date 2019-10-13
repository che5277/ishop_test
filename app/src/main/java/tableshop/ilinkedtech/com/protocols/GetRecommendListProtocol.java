package tableshop.ilinkedtech.com.protocols;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.protocols
 *  @文件名:   GetRecommendListProtocol
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 16:49
 *  @描述：    TODO 获取推荐车辆
 */

import android.app.Activity;

import tableshop.ilinkedtech.com.beans.main.CarModelListRequestBean;
import tableshop.ilinkedtech.com.consts.Const;

public abstract class GetRecommendListProtocol
        extends GetCarDetailListProtocol<CarModelListRequestBean> {

    public GetRecommendListProtocol(Activity activity,
                                    CarModelListRequestBean carModelListRequestBean)
    {
        this(activity,carModelListRequestBean ,true);
    }
    public GetRecommendListProtocol(Activity activity,
                                    CarModelListRequestBean carModelListRequestBean, boolean isLoadDataFromNet)
    {
        super(activity, carModelListRequestBean,Const.GET_RECOMMENDED,isLoadDataFromNet);
    }

}
