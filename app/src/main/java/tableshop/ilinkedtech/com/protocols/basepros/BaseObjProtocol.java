package tableshop.ilinkedtech.com.protocols.basepros;

import android.app.Activity;

import com.google.gson.Gson;

import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;

/*
 *  @项目名：  iShop
 *  @包名：    com.ilinkedtech.protocols.basepros
 *  @文件名:   BaseObjProtocol
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 17:41
 *  @描述：    TODO
 */
public abstract class BaseObjProtocol<REQUESTBEAN,RESPONDATAS> {
    private static final String TAG = "BaseObjProtocol";
    public BaseObjProtocol(Activity activity, boolean isTakeRole,
                           final String mainurl, REQUESTBEAN requestbean, final Class<RESPONDATAS> clazz){
        final Gson   gson = new Gson();
        final String json = gson.toJson(requestbean);
        MyHttpUtils.postJson(KeyConst.CacheType.OFFLINE_WORK, activity,
                             isTakeRole,
                             mainurl,
                             null,
                             json,
                             new MysalesCallBack() {
                                 @Override
                                 public void onError(Exception e, int id) {
                                     doOnError(e, id);
                                 }

                                 @Override
                                 public void onResponse(String response, int id) {
                                     doAfterGetData(response, id, gson, clazz);

                                 }

                                 @Override
                                 public void doAffterRequestCall() {
                                     doAffertRequest();
                                 }
                             });
    }

    private void doAfterGetData(String response,
                                int id,
                                Gson gson,
                                Class<RESPONDATAS> clazz)
    {
        if (response!=null){
            RESPONDATAS dataBean=null;
            try {
                dataBean = gson.fromJson(response, clazz);
                doOnSusses(dataBean,id);
            }catch (Exception e){
                doOnError(e,id);
                LogUtils.e(e.toString()+"");
            }

        }
    }

    protected abstract void doOnSusses(RESPONDATAS dataBean, int id);

    protected abstract void doOnError(Exception e, int id);

    public void doAffertRequest(){}

}
