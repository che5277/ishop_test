package tableshop.ilinkedtech.com.protocols;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.protocols
 *  @文件名:   GetRecommendListProtocol
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 16:49
 *  @描述：    TODO 获取搜索的车辆列表
 */

import android.app.Activity;

import tableshop.ilinkedtech.com.beans.reques.SearchRequestBean;
import tableshop.ilinkedtech.com.consts.Const;

public abstract class GetSearchListProtocol
        extends GetCarDetailListProtocol<SearchRequestBean> {

    public GetSearchListProtocol(Activity activity,
                                 SearchRequestBean carModelListRequestBean)
    {
        this(activity,carModelListRequestBean ,false);
    }
    public GetSearchListProtocol(Activity activity,
                                 SearchRequestBean carModelListRequestBean, boolean isLoadDataFromNet)
    {
        super(activity, carModelListRequestBean,Const.GET_SEARCH,isLoadDataFromNet);
    }

}
