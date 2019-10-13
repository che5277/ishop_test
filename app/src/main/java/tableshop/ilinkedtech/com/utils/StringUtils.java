package tableshop.ilinkedtech.com.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import tableshop.ilinkedtech.com.MainApp;
import tableshop.ilinkedtech.com.beans.RulesItem;
import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.views.flowlayout.TagFlowLayout;


/*
 *  @项目名：  iShop
 *  @包名：    com.ilinkedtech.utils
 *  @文件名:   StringUtils
 *  @创建者:   Wilson
 *  @创建时间:  2016/11/26 11:28
 *  @描述：    TODO
 */
public class StringUtils {
    private static final String TAG                     = "StringUtils";
    public static        int    nameOrderType           = -1;
    //姓名排序
    public static final  int    ORDER_LAST_FIRST        = 0;
    public static final  int    ORDER_FIRST_LAST        = 1;
    public static final  int    ORDER_LAST_FIRST_MIDDLE = 2;
    public static final  int    ORDER_FIRST_MIDDLE_LAST = 3;

    public static boolean isEmpty(String reason) {
        if (!TextUtils.isEmpty(reason)&&!"null".equals(reason.toLowerCase())){
            return false;
        }
        return true;
    }


    public static String checkString(String text){
        if (TextUtils.isEmpty(text)||"null".equals(text.toLowerCase())){
            return "";
        }
        return text;
    }


    public static String stream2String(InputStream inputStream)
            throws IOException
    {
        int          len          =0;
        byte[]       buffer       = new byte[1024];
        StringBuffer stringBuffer =new StringBuffer();
        while ((len=inputStream.read(buffer))!=-1){
            stringBuffer.append(new String(buffer, 0, len, "UTF-8"));
        }
        inputStream.close();
        return stringBuffer.toString();
    }

    /**
     * 获取标签选中的选项，默认为最后一个(单选)，常用于获取搜索的条件
     * @param sortTagItems
     * @param sortFlowlayout
     * @return
     */
    public static ListRequestBean getTagRequestBean(ArrayList<RulesItem> sortTagItems, TagFlowLayout sortFlowlayout) {
        Set<Integer> selectedList = sortFlowlayout.getSelectedList();
        if (selectedList!=null&&selectedList.size()>0) {
            return sortTagItems.get(selectedList.iterator().next())
                               .requestBean;
        }else {
            return null;
        }
    }
    /**
     * 获取标签选中的选项，默认为最后一个(单选)，常用于获取搜索的条件
     * @param sortTagItems
     * @param sortFlowlayout
     * @return
     */
    public static RulesItem getSeletedTagItem(ArrayList<RulesItem> sortTagItems, TagFlowLayout sortFlowlayout) {
        Set<Integer> selectedList = sortFlowlayout.getSelectedList();
        if (selectedList!=null&&selectedList.size()>0) {
            return sortTagItems.get(selectedList.iterator().next());
        }else {
            return null;
        }
    }


    public static <T> ArrayList<T> jsonToArrayObj(String response, Class<T> clazz)
    {
        ArrayList<T> dataBean = new ArrayList<T>();
        try {
            Gson     gson     =new Gson();
            if (response != null) {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    T respondatas = gson.fromJson(jsonArray.getString(
                            i), clazz);
                    dataBean.add(respondatas);
                }
            }
        }catch (JSONException e){

        }

        return dataBean;
    }


    public static ArrayList<String> getArrObjKeyName(Object json){
        ArrayList<String> arrayList=new ArrayList<>();
        try {
            JSONObject       jsonObject =new JSONObject(new Gson().toJson(json));
            Iterator<String> keys       = jsonObject.keys();
            while (keys.hasNext()) {
                String key=(String) keys.next();
                arrayList.add(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


//    public static String getTenThousandStringFromDouble(double price){
//        if (price>10000){
//            return formatDoublePriceToString(price/10000);
//        }
//        return price+"";
//    }
    public static String getTenThousandStringFromDouble(String price){
        String result="";
        try {
            if (!StringUtils.isEmpty(price)) {
                result = formatDoublePriceToString(Double.parseDouble(price));
            }
        }catch (Exception e){

        }
        return result;
    }

    public static String getCarDetailPrice(double price){
        if (price>10000){
            DecimalFormat df0 = new DecimalFormat("######0.00");
            return df0.format(price/10000);
        }
        return price+"";
    }
    public static String formatDoublePriceToString(double price){
        DecimalFormat df0 = new DecimalFormat("#,###,###.######");
        return df0.format(price);
    }

    public static String getCustomerName(String fName, String lName){
        if (nameOrderType==-1){
            String customerNameOrder = SharedStorage.getInstance(MainApp.getContext())
                                                    .getCustomerNameOrder().trim();
            if (StringUtils.isEmpty(customerNameOrder)){
                nameOrderType=ORDER_LAST_FIRST;
            }else {
                try {
                    nameOrderType= Integer.parseInt(customerNameOrder);
                }catch (Exception e){
                    nameOrderType=ORDER_LAST_FIRST;
                }
            }
        }
        fName=checkString(fName);
        lName=checkString(lName);
        String name =lName + " "+fName;
        if (nameOrderType!=-1){
            switch (nameOrderType){
                case ORDER_LAST_FIRST:
                    //                    name=lName + " "+fName;
                    break;
                case ORDER_FIRST_LAST:
                    name=fName+ " "+lName;
                    break;
                case ORDER_LAST_FIRST_MIDDLE:
                    break;
                case ORDER_FIRST_MIDDLE_LAST:
                    break;
                default:
                    break;
            }
        }
        return name;
    }

}
