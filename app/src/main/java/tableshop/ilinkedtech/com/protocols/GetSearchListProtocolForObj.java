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

import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;
import tableshop.ilinkedtech.com.beans.responbeans.SearchResponBean;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.protocols.basepros.BaseObjProtocol;

public abstract class GetSearchListProtocolForObj
        extends BaseObjProtocol<ListRequestBean,SearchResponBean>
{

    public GetSearchListProtocolForObj(Activity activity,
                                       ListRequestBean carModelListRequestBean)
    {
        this(activity,carModelListRequestBean ,false);
    }
    public GetSearchListProtocolForObj(Activity activity,
                                       ListRequestBean carModelListRequestBean, boolean isLoadDataFromNet)
    {
        super(activity, isLoadDataFromNet,Const.GET_SEARCH,carModelListRequestBean,SearchResponBean.class);
    }

}
