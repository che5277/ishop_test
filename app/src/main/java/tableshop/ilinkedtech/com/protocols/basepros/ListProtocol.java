package tableshop.ilinkedtech.com.protocols.basepros;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.DBJsonBean;
import tableshop.ilinkedtech.com.db.DBJsonHelper;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;

/*
 *  @项目名：  iShop
 *  @包名：    com.ilinkedtech.protocols.basepros
 *  @文件名:   ListProtocol
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 17:41
 *  @描述：    TODO
 */
public abstract class ListProtocol<REQUESTBEAN, RESPONDATAS> {
    private static final String TAG = "ListProtocol";

    public ListProtocol(Activity activity,
                        boolean isTakeRole,
                        String mainurl, boolean isLoadDataFromNet,
                        final REQUESTBEAN requestbean,
                        final Class<RESPONDATAS> clazz)
    {
        final Gson gson = new Gson();
        String body     = gson.toJson(requestbean);
        String apiKey   = mainurl + body;
        if (!isLoadDataFromNet) {
            DBJsonBean dbJsonBean = DBJsonHelper.mdKeyAndGetData(apiKey);
            if (dbJsonBean!=null){
                String jsonData=dbJsonBean.getData();
                if (!StringUtils.isEmpty(jsonData)) {
                    long beforeTime = Long.parseLong(dbJsonBean.getUpdateTime());
//                    long beforeTime = 0l;
                    if (System.currentTimeMillis()-beforeTime<(1000*60*5)) {
                        try {
                            ArrayList<RESPONDATAS> dataBean = StringUtils.jsonToArrayObj(jsonData,
                                                                                         clazz);
                            if (dataBean != null) {
                                doOnSusses(dataBean, 0);
                                doAffertRequest();
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            doOnError(e, 0);
                        }
                    }else {
                        DBJsonHelper.deleteDataBean(dbJsonBean);
                    }
                }else {
                    DBJsonHelper.deleteDataBean(dbJsonBean);
                }
            }

        }
        callToGetData(activity, isTakeRole, mainurl, clazz, body);
    }

    private void callToGetData(Activity activity,
                               boolean isTakeRole,
                               String mainurl,
                               final Class<RESPONDATAS> clazz,
                               String body)
    {
        MyHttpUtils.postJson(KeyConst.CacheType.OFFLINE_WORK, activity,
                             isTakeRole,
                             mainurl,
                             null,
                             body,
                             new MysalesCallBack() {
                                 @Override
                                 public void onError(Exception e, int id) {
                                     doOnError(e, id);
                                 }

                                 @Override
                                 public void onResponse(String response, int id) {
                                     try {
                                         ArrayList<RESPONDATAS> dataBean = StringUtils.jsonToArrayObj(response, clazz);
                                         doOnSusses(dataBean, id);
                                         if (dataBean!=null&&dataBean.size()>0) {
                                             LogUtils.e(dataBean.size() + "===++===" + dataBean.get(0)
                                                                                               .toString());
                                         }
                                     } catch (Exception e) {
                                         e.printStackTrace();
                                         doOnError(e,id);
                                     }


                                 }

                                 @Override
                                 public void doAffterRequestCall() {
                                     doAffertRequest();
                                 }
                             });
    }

    public abstract void doOnSusses(ArrayList<RESPONDATAS> dataBean, int id);

    public abstract void doOnError(Exception e, int id);

    public void doAffertRequest() {}
}
