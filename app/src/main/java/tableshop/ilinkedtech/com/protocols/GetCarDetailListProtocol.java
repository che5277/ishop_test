package tableshop.ilinkedtech.com.protocols;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.protocols
 *  @文件名:   GetRecommendListProtocol
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 16:49
 *  @描述：    TODO 所有获取具体车列表的根代理
 */

import android.app.Activity;

import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.protocols.basepros.ListProtocol;

public abstract class GetCarDetailListProtocol<REQUESTBEAN>
        extends ListProtocol<REQUESTBEAN,CarDetailBean>
{

    public GetCarDetailListProtocol(Activity activity,
                                    REQUESTBEAN carModelListRequestBean, String mainurl,boolean isLoadDataFromNet)
    {
        super(activity, false, mainurl,isLoadDataFromNet, carModelListRequestBean, CarDetailBean.class);
    }

}
