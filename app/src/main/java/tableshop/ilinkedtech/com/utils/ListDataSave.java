package tableshop.ilinkedtech.com.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tableshop.ilinkedtech.com.beans.main.ProductItemBean;

/**
 * Created by TO on 2018/5/3.
 */

public class ListDataSave {


    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public ListDataSave(Context mContext, String preferenceName)

    {

        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 保存List
     *
     * @paramtag
     * @paramdatalist
     */
    public void

    setDataList(String tag, List<ProductItemBean> datalist)

    {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
//转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();

    }

    /**
     * 获取List
     *
     * @return
     * @paramtag
     */
    public List<ProductItemBean> getDataList(String tag)
    {
        List<ProductItemBean> datalist = new ArrayList<  >();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken< List < ProductItemBean >>() {
    }.getType());
        return datalist;

    }


}
