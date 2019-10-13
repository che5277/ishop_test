package tableshop.ilinkedtech.com.consts;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.consts
 *  @文件名:   Const
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/6 10:28
 *  @描述：    TODO
 */

import android.widget.EditText;

import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;

public class Const {

    public static int windowWidth=0;
    public static int windowHeight=0;
    public static boolean offNet;
    public static final int MY_PERMISSION_REQUEST_STORAGE = 1;

    public static final String  DEBUG_TAG                = "iShop";
    public static final String  WX_APP_ID                = "wxe3c9f2c48e686c5f";//微信
    public static final String  JG_APP_ID                = " bbe1058032e0979e94fc55e6";//极光推送
    public static final String  WX_PACKEGE_VALUE         = "Sign=WXPay";//微信
    public static final String  BAIDU_MAP_AK             = "W0DbUK57obVhZUObUHxZOzkCu8rOGI3s";//百度地图
    public static final String  GAODE_MAP_KEY            = "6f7f2d9fcefccda74c5a77ac9c36b36a";//高德地图
    public static final String  DEBUG_SNAKBAR_ACTION_TEX = "Action";
    public static final boolean DEBUG_ENABLE             = false;// 是否调试模式


    public static final String SOCKET_TIMEOUT     = HttpURLConnection.HTTP_MOVED_TEMP + "";//302
    public static final String CONNECTION_TIMEOUT = HttpURLConnection.HTTP_SEE_OTHER + "";//303
    public static final String MALFORMED_URL      = HttpURLConnection.HTTP_USE_PROXY + "";//"305"
    public static final String IO_EXCEPTION       = "306";
    public static final String PARAMES_EXCEPTION  = "422";

    public static final String PROJECK_NAME     = "iCaniShop/";

    public static final String TRAVELLER_HOST   = "http://linkedauto.travellerdata.com/";
//    public static final String TRAVELLER_HOST   = "http://test.travellerdata.com/";
    public static       String DEV_HOST         = TRAVELLER_HOST;
    public static       String PD_HOST          = "http://www.linkedauto.cn/";
    public static       String TE_HOST          = "http://test.travellerdata.com/";

    public static final String LINKED_AUTO_HOST = "http://linkedauto.car4s.shop/";
    public static final String TEST_HOST = "http://test.travellerdata.com/";
    public static       String BASE_HOST        = PD_HOST + PROJECK_NAME;

    public static String CUSTOM_BASE_URL = TEST_HOST;

    //TODO 配置后台
    public static String BACKEDTYPE = KeyConst.BackendType.PD;//"DEV" "TE" "QA" "PD" "CUSTOM"

    public static final String GET_VEHICLE              = "shop/getVehicle";//TODO 根据品牌请求所有车辆
    public static final String GET_RECOMMENDED          = "shop/getRecommended";

    public static final String GET_SEARCH               = "shop/getVehicleModelOnSalesSearch";
    public static final String GET_VEHICLE_BRANDS_SALES = "shop/getVehicleBrandsSales";//TODO 请求所有在售的品牌
    public static final String GET_VEHICLE_BRAND_CODE_ON_SALES = "shop/getVehicleBrandCodeOnSales";//TODO 请求所有在售的品牌
    public static final String GET_VEHICLE_TOP_BRAND = "shop/getVehicleTopBrand";//TODO 请求热门的品牌
    public static final String DISPLAY_SOLD_VEHICLE = "shop/displaySoldVehicle";//TODO 请求 最新成交 最近成交


    public static final String GET_VEHICLE_SALES = "shop/getVehicleSales";//TODO 请求在售车辆列表（根据品牌和车系）
    public static final String GET_POPULAR_VEHICLE = "shop/getPopularVehicle";//TODO 请求编辑之选的车辆
  //  public static final String GET_VEHICLE_DETAILS_BY_UID = "shop/getVehicleDetailsByUid";//TODO 请求车辆详情
    public static final String GET_VEHICLE_DETAILS_BY_UID = "shop/getVehicleDetailsByUidAndType";//TODO 请求车辆详情
    public static final String GET_VEHICLE_SERIES_CODE_ON_SALES = "shop/getVehicleSeriesCodeOnSales";//TODO 请求车系列表（根据品牌）

//    public static final String WEB_CAR_DETAIL_URL         = getBaseUrl(BASE_HOST) + "index.html#/detail/";//TODO web版本的车辆详情链接
    public static final String WEB_CAR_DETAIL_URL         = "#/new-car-detail/";//TODO web版本的车辆详情链接
    public static final String WEB_PRODUCT_DETAIL_URL         = "#/product-detail/";//TODO web版本的商品详情链接
    public static final String TOKEN_OVEDUE               = "\"value\": \"Invalid Login\"";
    public static final String LOGIN_FAULT                = "login_fault";

    public static final String LOGIN           = KeyConst.HttpsContentUrlHead.ICANUSER + "/j_spring_security";//登录
    public static final String MEMBER_FINDBYID = KeyConst.HttpsContentUrlHead.ICANUSER + "/member/findById";//是否注册
    public static final String MEMBER_REGISTER = KeyConst.HttpsContentUrlHead.ICANUSER + "/member/save";//注册

 //   public static final String FIND_PRODUCT_DETAILS_BY_UID         =KeyConst.HttpsContentUrlHead.ICANUSER + "/product/findProductDetailsByUid";//TODO 查找商品详情
    public static final String FIND_PRODUCT_DETAILS_BY_UID         =KeyConst.HttpsContentUrlHead.ICANUSER + "/product/findProductDetailsByUidAndType";//TODO 查找商品详情
 //   public static final String PRODUCT_FIND         = KeyConst.HttpsContentUrlHead.ICANUSER + "/product/find";//商品列表
    public static final String PRODUCT_FIND         = KeyConst.HttpsContentUrlHead.ICANUSER + "/product/findByType";//商品列表
    public static final String SHOPPING_CREATEORDER = KeyConst.HttpsContentUrlHead.ICANUSER + "/order/shopping/createOrder";//创建订单
    public static final String PRODUCT_CREATEORDER  = KeyConst.HttpsContentUrlHead.ICANUSER + "/product/createOrder";//离线创建订单
    public static final String SHOPPING_SAVECART           = KeyConst.HttpsContentUrlHead.ICANUSER + "/order/shopping/saveCart";//购物车
    public static final String GET_PURCHASE_ORDER_LIST           = KeyConst.HttpsContentUrlHead.ICANUSER + "/order/shopping/getPurchaseOrderList";//我的订单列表
    public static final String SHOPPING_GETSHOPPINGCART    = KeyConst.HttpsContentUrlHead.ICANUSER + "/order/shopping/getShoppingCart";//购物车列表
    public static final String SHOPPING_DELETESHOPPINGCART = KeyConst.HttpsContentUrlHead.ICANUSER + "/order/shopping/deleteShoppingCart";//删除购物车
    public static final String GET_SHOP_LIST = KeyConst.HttpsContentUrlHead.ICANUSER + "/order/shopping/getShopList";//TODO 获取附近的店
    public static final String SAVE_SALES_AREA = KeyConst.HttpsContentUrlHead.ICANUSER + "/order/shopping/saveSalesArea";//TODO 选店
    public static final String STORE_MACHINE_REQUEST          = KeyConst.HttpsContentUrlHead.ICANUSER +"/order/shopping/storeMachineRequest";
    public static final String IS_ORDER_FULL          = KeyConst.HttpsContentUrlHead.ICANUSER +"/order/shopping/isOrderFull";//TODO 判断订单是否已满

    public static final String AI_SUBMIT                   = KeyConst.HttpsContentUrlHead.ICANAI + "/ai/submit";//ai

    public static final String  TEST_WXPAY      = KeyConst.HttpsContentUrlHead.ICANPAYMENT + "/shop/testWXPay";
    public static final String  TEST_ALIPAY     = KeyConst.HttpsContentUrlHead.ICANPAYMENT + "/shop/testAliPay";
    public static final String  GENERATE_QRCODE = KeyConst.HttpsContentUrlHead.ICANPAYMENT + "/shop/generateQRCode";
    public static       boolean isOpened        = false;

    public static final class WebUrl{
        public static final String  LOGIN_AGREEMENT= "tac.html";
        public static final String  ORDER_LIST= "#/order";

    }

    public static String getBaseHttpsUrl() {
        return getBaseHttpsUrl(BASE_HOST);
    }

    public static void setBaseUrl() {
        switch (BACKEDTYPE.toUpperCase()) {
            case KeyConst.BackendType.DEV:
                BASE_HOST = DEV_HOST + PROJECK_NAME;
                break;
            case KeyConst.BackendType.PD:
                BASE_HOST = PD_HOST + PROJECK_NAME;
                break;
            case KeyConst.BackendType.TE:
                BASE_HOST = TE_HOST + PROJECK_NAME;
                break;
            case KeyConst.BackendType.CUSTOM:
                BASE_HOST = CUSTOM_BASE_URL + PROJECK_NAME;
                break;
        }
    }

    /**
     * 验证电子邮箱
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        boolean b = false;
        Pattern p = Pattern.compile(
                "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$"); // 验证电子邮箱
        //        String  regularKey = ValidationHelper.Customer.getRegularKey(KeyConst.CommunicationChannels.EMAIL);
        //        if (!StringUtils.isEmpty(regularKey)){
        //            p          = Pattern.compile(regularKey); // 验证电子邮箱
        //        }
        Matcher m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 验证手机号码
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        boolean b = false;
        Pattern p = null; // 验证手机号
        //        String  regularKey = ValidationHelper.Customer.getRegularKey(KeyConst.CommunicationChannels.MOBILE);
        String regularKey = null;
        if (StringUtils.isEmpty(regularKey)) {
            p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        } else {
            p = Pattern.compile(regularKey); // 验证手机号
        }
        Matcher m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static boolean isMobile(EditText editText) {
        boolean b = false;
        if (editText == null) {
            return b;
        }
        String mobile = editText.getText()
                                .toString()
                                .trim();
        if (StringUtils.isEmpty(mobile)) {
            ToastUtil.toast(UIUtils.getString(R.string.请输入手机号码));
        } else if (!isMobile(mobile)) {
            ToastUtil.toast(UIUtils.getString(R.string.请输入正确的手机号));
        } else {
            b = true;
        }
        return b;
    }


    //    if ([url hasPrefix:@"/iCanPayment"] || [url hasPrefix:@"/iCanAI"] || [url hasPrefix:@"/iCanUser"])
    public static String getUrlForRequest(String mainUrl) {
        if (mainUrl.startsWith("http")) {
            return mainUrl;
        } else if (mainUrl.startsWith(KeyConst.HttpsContentUrlHead.ICANAI) ||
                mainUrl.startsWith(KeyConst.HttpsContentUrlHead.ICANPAYMENT) ||
                mainUrl.startsWith(KeyConst.HttpsContentUrlHead.ICAN_ADMIN) ||
                mainUrl.startsWith(KeyConst.HttpsContentUrlHead.ICANUSER))
        {
            return getBaseHttpsUrl() + mainUrl;
        }
        return Const.BASE_HOST + mainUrl;
    }


    /*
      * Get validate Base URl
      * */
    public static String getBaseUrl(String baseUrl) {
        baseUrl = baseUrl.replaceAll(PROJECK_NAME, "")
                         .replaceAll(" ", "")
                         .trim();
        while (baseUrl.endsWith("//")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }

        if (baseUrl.endsWith("//")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            getBaseUrl(baseUrl);
        } else if (!baseUrl.endsWith("/")) {
            baseUrl = baseUrl.concat("/");
        }

        return baseUrl;
    }


    public static String getBaseHttpsUrl(String baseUrl) {
        baseUrl = getBaseUrl(baseUrl);
        if (!baseUrl.startsWith("https")) {
            baseUrl = baseUrl.replace("http", "https")
                             .replace(":81", "");
        }
        return baseUrl;
    }


}
