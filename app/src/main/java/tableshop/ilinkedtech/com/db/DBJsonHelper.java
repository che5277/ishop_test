package tableshop.ilinkedtech.com.db;

/*
 *  @项目名：  iShop
 *  @包名：    ishop.ilinkedtech.com.db
 *  @文件名:   DBJsonHelper
 *  @创建者:   Wilson
 *  @创建时间:  2017/6/27 15:39
 *  @描述：    TODO
 */


import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MD5Util;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ThreadPoolUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;

public class DBJsonHelper {

    private static ArrayList<CarDetailBean> favoriteList;
    private static ArrayList<ProductItemBean> productFavoriteList;

    private static DBJsonBeanDao getDBBeanDao() {
        return DBHelper.getInstance().getDBBeanDao();
    }

    /**
     * 更新数据
     * @param apiKey    传入未加密的apiKey
     * @param data
     */
    public static void mdKeyAndUpdateData(String apiKey,String data){
        if (StringUtils.isEmpty(apiKey)||StringUtils.isEmpty(data)){
            return;
        }else {
            String encryptKey = MD5Util.MD5(apiKey);
            updateData(encryptKey,data);
        }
    }
    /**
     * 更新数据
     * @param apiKey    传入已加密的apiKey
     * @param data
     */
    public static void updateData(final String apiKey, final String data){
        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                if (StringUtils.isEmpty(apiKey)||StringUtils.isEmpty(data)){
                    return;
                }else {
                    try {
                        List<DBJsonBean> list = getDBBeanDao().queryBuilder()
                                                              .where(DBJsonBeanDao.Properties.ApiKey.eq(
                                                                      apiKey))
                                                              .build()
                                                              .list();
                        DBJsonBean jsonBean=null;
                        if (list!=null&&list.size()>0){
                            jsonBean=list.get(0);
                        }
                        if (jsonBean == null) {
                            jsonBean = new DBJsonBean(null, apiKey, data, System.currentTimeMillis()+"");
                            getDBBeanDao().insert(jsonBean);
                        } else {
                            jsonBean.setData(data);
                            getDBBeanDao().save(jsonBean);
                        }
                    }catch (Exception e){

                    }

                }
            }
        });

    }


    public static void deleteDataBean(DBJsonBean dbJsonBean){
        if (dbJsonBean!=null)
            getDBBeanDao().delete(dbJsonBean);
    }
    /**
     * 获取已经缓存到本地的json数据
     * @param apiKey 传入未加密的apiKey
     * @return
     */
    public static String mdKeyAndGetJsonData(String apiKey){
        String json =null;
        if (!StringUtils.isEmpty(apiKey)) {
            String     encryptKey = MD5Util.MD5(apiKey);
            DBJsonBean jsonBean   = getJsonBean(encryptKey);
            if (jsonBean != null) {
                json = jsonBean.getData();
            }
        }
        return json;
    }
    /**
     * 获取已经缓存到本地的json数据
     * @param apiKey 传入未加密的apiKey
     * @return
     */
    public static DBJsonBean mdKeyAndGetData(String apiKey){
        DBJsonBean jsonBean =null;
        if (!StringUtils.isEmpty(apiKey)) {
            String     encryptKey = MD5Util.MD5(apiKey);
            jsonBean   = getJsonBean(encryptKey);
        }
        return jsonBean;
    }

    /**读取数据库数据
     * @param encryptKey    传入已加密的apiKey
     * @return TODO 报错org.greenrobot.greendao.DaoException: Expected unique result, but count was 3
     */
    public static DBJsonBean getJsonBean(String encryptKey){
        DBJsonBean jsonBean=null;
        try {
            List<DBJsonBean> dbJsonBeen = getDBBeanDao().queryBuilder()
                                                  .where(DBJsonBeanDao.Properties.ApiKey.eq(encryptKey))
                                                  .build()
                                                  .list();
            if (dbJsonBeen!=null&&dbJsonBeen.size()>0){
                jsonBean=dbJsonBeen.get(0);
            }
        }catch (Exception e){

        }


        return jsonBean;
    }


    public static ArrayList<CarDetailBean> getFavoriteList()
    {
        try {
            return getCarList(KeyConst.LocalData.KET_DB_FAVORITE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<CarDetailBean>();
    }
    public static ArrayList<ProductItemBean> getProductFavoriteList()
    {
        try {
            return getProductCarList(KeyConst.LocalData.KET_DB_PRODUCT_FAVORITE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<ProductItemBean>();
    }


    public static ArrayList<CarDetailBean> getCarList(String listDbKey)
            throws JSONException
    {
        if (favoriteList!=null&&favoriteList.size()>0){
            favoriteList.clear();
        }else {
            favoriteList=new ArrayList<>();
        }
        String                      jsonData  = mdKeyAndGetJsonData(listDbKey);
        if (!StringUtils.isEmpty(jsonData)) {
            favoriteList = StringUtils.jsonToArrayObj(jsonData, CarDetailBean.class);
        }
        return favoriteList;
    }



    public static ArrayList<ProductItemBean> getProductCarList(String listDbKey)
            throws JSONException
    {
        if (productFavoriteList!=null&&productFavoriteList.size()>0){
            productFavoriteList.clear();
        }else {
            productFavoriteList=new ArrayList<>();
        }
        String                      jsonData  = mdKeyAndGetJsonData(listDbKey);
        if (!StringUtils.isEmpty(jsonData)) {
            productFavoriteList = StringUtils.jsonToArrayObj(jsonData, ProductItemBean.class);
        }
        return productFavoriteList;
    }

    public static void upDateFavoriteList(CarDetailBean itemBean, int orientation){
        upDateList(itemBean,orientation,KeyConst.LocalData.KET_DB_FAVORITE);
    }

    public static void upProductFavoriteList(ProductItemBean itemBean, int orientation){
        upProductDateList(itemBean,orientation,KeyConst.LocalData.KET_DB_PRODUCT_FAVORITE);
    }

    public static void upDateList(CarDetailBean itemBean, int orientation, String dbKey){
        if (itemBean==null){
            return;
        }
        try {
            ArrayList<CarDetailBean> arrayList=null;
            if (dbKey.equals(KeyConst.LocalData.KET_DB_FAVORITE)){
                arrayList = getFavoriteList();
            }else if (dbKey.equals(KeyConst.LocalData.KET_DB_CONTRAST)){
                arrayList = getContrastList();
            }
            if (arrayList==null){
                arrayList=new ArrayList<>();
                arrayList.add(itemBean);
                mdKeyAndUpdateData(dbKey, new Gson().toJson(arrayList));
            }else {
                if (orientation== KeyConst.LocalData.ORIENTATION_DEL){
                    if (arrayList.contains(itemBean)){
                        LogUtils.e(arrayList.contains(itemBean)+"：contains----remove："+arrayList.remove(itemBean));
                        mdKeyAndUpdateData(dbKey, new Gson().toJson(arrayList));
                    }
                }else if (orientation== KeyConst.LocalData.ORIENTATION_ADD){
                    if (!arrayList.contains(itemBean)){
                        arrayList.add(0,itemBean);
                        mdKeyAndUpdateData(dbKey, new Gson().toJson(arrayList));
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static void upProductDateList(ProductItemBean itemBean, int orientation, String dbKey){
        if (itemBean==null){
            return;
        }
        try {
            ArrayList<ProductItemBean> arrayList=null;
            if (dbKey.equals(KeyConst.LocalData.KET_DB_PRODUCT_FAVORITE)){
                arrayList = getProductFavoriteList();
            }
            if (arrayList==null){
                arrayList=new ArrayList<>();
                arrayList.add(itemBean);
                mdKeyAndUpdateData(dbKey, new Gson().toJson(arrayList));
            }else {
                if (orientation== KeyConst.LocalData.ORIENTATION_DEL){
                    if (arrayList.contains(itemBean)){
                        LogUtils.e(arrayList.contains(itemBean)+"：contains----remove："+arrayList.remove(itemBean));
                        mdKeyAndUpdateData(dbKey, new Gson().toJson(arrayList));
                    }
                }else if (orientation== KeyConst.LocalData.ORIENTATION_ADD){
                    if (!arrayList.contains(itemBean)){
                        arrayList.add(0,itemBean);
                        mdKeyAndUpdateData(dbKey, new Gson().toJson(arrayList));
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }



    //====================TODO 车辆对比 begin===========================

    /**
     * TODO 获取车辆对比列表
     * @return
     * @throws JSONException
     */
    public static ArrayList<CarDetailBean> getContrastList()

    {
        try {
            return getCarList(KeyConst.LocalData.KET_DB_CONTRAST);
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<CarDetailBean>();
        }
    }


    /**
     * TODO 更新对比车辆列表数据
     * @param itemBean
     * @param orientation
     */
    public static void upDateContrastList(CarDetailBean itemBean, int orientation){
        upDateList(itemBean,orientation,KeyConst.LocalData.KET_DB_CONTRAST);
    }

    //====================TODO 车辆对比 end===========================

    /**
     * 清空数据库
     */
    public static void deleteDBBeanDatas(){
        SharedStorage.getInstance(UIUtils.getContext()).reSetUserData();
        getDBBeanDao().deleteAll();
        DBHelper.clearDBHelper();
    }
}
