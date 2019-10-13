/*
 *  @项目名：  iShop
 *  @包名：    ishop.ilinkedtech.com.db
 *  @Wilson
 *
 */

package tableshop.ilinkedtech.com.db;

import android.content.Context;
import android.content.SharedPreferences;

import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.utils.Aes;
import tableshop.ilinkedtech.com.utils.FileUtil;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ThreadPoolUtils;


public class SharedStorage {

    private static SharedPreferences mSharedPreferences;
    private static SharedStorage     sharedStorage;
    public static String  userName     ="iShop";
    public static String  speechLocale ="mandarin";//语音识别的语言
    public static String  userRole     ="";//用户类型 dcc sc 用于却分各角色功能判断所用
    public static boolean mIsLogin     =false;
    public static boolean hasTDO       =true;

    /*
    * Constructor
    * */

    private SharedStorage(Context mContext) {
        mSharedPreferences = mContext.getSharedPreferences("iShop", Context.MODE_PRIVATE);
    }

    /*
    * Static Constructor to create instance
    * */

    public static SharedStorage getInstance(Context mContext) {
        if (sharedStorage == null)
            sharedStorage = new SharedStorage(mContext);
        return sharedStorage;
    }

    /*
    * Get system default language
    * */

    public String getDefaultLanguage() {
        return mSharedPreferences.getString("lang", "zh_CN");
//        return "zh_CN";//wilson "zh_cn"
    }

    /*
    * Set System default language
    * */

    public void setDefaultLanguage(String language) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("lang", language);
        editor.commit();
    }

    /*
    * Get Customer token required for all request after login
    * */

    public String getCustomToken() {
        if (StringUtils.isEmpty(MyHttpUtils.token)){
            MyHttpUtils.token=mSharedPreferences.getString(KeyConst.SharePreKey.CUSTOME_TOKEN, "null");
        }
        return MyHttpUtils.token;
    }

    /*
    * Set Custom token get it from every http request Header
    * */

    public void setCustomToken(String customToken) {
        MyHttpUtils.token=customToken;
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KeyConst.SharePreKey.CUSTOME_TOKEN, customToken);
        editor.apply();
    }


    public String getCustomerInfo() {
        return mSharedPreferences.getString("customerInfo", "null");
    }
    public void setCustomerInfo(String customerInfo) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("customerInfo", customerInfo);
        editor.commit();
    }



    public String getUsername() {
        return mSharedPreferences.getString("username", userName);
    }
    /*
    * Set recent login username
    * */
    public void setUsername(String username) {
        userName=username;
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("username", userName);
        editor.commit();
    }


    //缓存验证码到本地
    public String getMobileMsm() {
        return mSharedPreferences.getString("mobile_msm", "null");
    }

    public void setMobileMsm(String mobile_msm) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("mobile_msm", mobile_msm);
        editor.commit();
    }
    //缓存电话号码到本地
    public String getMobile() {
        return mSharedPreferences.getString("mobile", "null");
    }

    public void setMobile(String mobile) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("mobile", mobile);
        editor.commit();
    }

    /*
    * Get recent login password
    * */

    public String getPassword() {
        return mSharedPreferences.getString("password", "7FC7EA8EFA9B2DF754F23C15710747C4");
    }

    /*
    * Set recent login password
    * */

    public void setPassword(String password) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("password", password);
        editor.commit();
    }


    /*
    * Set Base URL
    * */

    public void setBaseUrl(String baseUrl) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("baseUrl", baseUrl);
        editor.commit();
    }

    /*
    * check isRemember for future login
    * */

    public boolean isRemember() {
        return mSharedPreferences.getBoolean("isRemember", true);
    }

    /*
    * Set remember for future login
    * */

    public void setIsRemember(boolean isRemember) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("isRemember", isRemember);
        editor.commit();
    }

    /*
    * Check already login or not
    * */

    public boolean isLogin() {
        return mIsLogin;
    }

    /*
    * Set login true or false
    * */

    public void setIsLogin(boolean isLogin) {
        this.mIsLogin =isLogin;
    }


    /*
    * Clear all saved data from shared preferences
    * */
    public void clearPreferences() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    /*
    * Encrypt username and password for security
    * 加密
    * */

    public String encryptData(String key, String data) {

        try {
            String encrypt = Aes.encrypt(key, data);
            LogUtils.e("=====encrypt:"+encrypt);
            return Aes.encrypt(key, data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /*
    * Decrypt username and password for security and pass it to ws for login
    * 解密
    * */

    public String decryptData(String key, String data) {
        try {
            return Aes.decrypt(key, data);
        } catch (Exception e) {
            return null;
        }
    }

    /*
    * Put string data to shared preference with its key and value
    * */

    public void putStringSharedData(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /*
    * Get string data to shared preference with its key
    * */

    public String getStringSharedData(String key) {
        return mSharedPreferences.getString(key, "null");
    }
    public String getStringSharedData(String key,String defaultValu) {
        return mSharedPreferences.getString(key, defaultValu);
    }

    /*
    * Put boolean data to shared preference with its key and value
    * */

    public void putBooleanSharedData(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /*
    * Get boolean data from shared preference with its key
    * */

    public boolean getBooleanSharedData(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    /*
    * Put int data to shared preference with its key and value
    * */

    public void putIntSharedData(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /*
    * Get int data from shared preference with its key
    * */

    public int getIntSharedData(String key) {
        return mSharedPreferences.getInt(key, 0);
    }


    public void putLongSharedData(String key, long value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public Long getLongSharedData(String key) {
        return mSharedPreferences.getLong(key, 0);
    }
    /*
    * Set user role
    * */

    public void setUserRole(String role) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("user_role", role);
        editor.commit();
        userRole=role.toUpperCase();
    }

    /*
    * Get user role
    * */

    public String getUserRole() {
        return mSharedPreferences.getString("user_role", userRole);
    }

    /*
    * Get user role id
    * */

    public String getUserRoleId() {
        return mSharedPreferences.getString("userRoleId", "null");
    }

    /*
    * Set user role id
    * */

    public void setUserRoleId(String userRoleId) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("userRoleId", userRoleId);
        editor.commit();
    }
    //====================TODO 姓名排序 begin===========================

    public void setCustomerNameOrder(String displayCustomerNameOrder) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("displayCustomerNameOrder", displayCustomerNameOrder);
        editor.commit();
    }
    public String getCustomerNameOrder() {
        return mSharedPreferences.getString("displayCustomerNameOrder", "");
    }
    public void setSpeechLocale(String speechRecognitionLocale) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("speechRecognitionLocale", speechRecognitionLocale);
        speechLocale=speechRecognitionLocale;
        editor.commit();
    }
    public String getSpeechLocale() {
        return mSharedPreferences.getString("speechRecognitionLocale", "mandarin");
    }

    public boolean getHasTDO() {
        return mSharedPreferences.getBoolean("hasTDO", true);
    }

    public void setHasTDO(boolean hasTDO) {
        this.hasTDO=hasTDO;
        putBooleanSharedData("hasTDO",hasTDO);
    }
    //====================TODO 姓名排序 end===========================

    /*
    * Get downloadable validation version number
    * */

    public String getValidationVersion() {
        return mSharedPreferences.getString("validation_version", "null");
    }

    /*
    * Set downloadable validation version number
    * */

    public void setValidationVersion(String validationVersion) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("validation_version", validationVersion);
        editor.commit();
    }


    //清空登录数据，重新登录需要调用
    public static void resetDatas() {
        mIsLogin=false;
        speechLocale ="mandarin";
        StringUtils.nameOrderType=-1;
        deleteCacheFiles();
    }

    /**
     * 清空缓存的数据
     */
    public static void deleteCacheFiles(){
        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
//                FileUtil.deleteDir(FileUtil.getImageDir());
                FileUtil.deleteDir(FileUtil.getVoiceDir());

            }
        });
    }


    public void reSetUserData(){
        setIsLogin(false);
        setCustomToken("null");
        setUsername("null");
        setCustomerInfo("null");
    }
}
