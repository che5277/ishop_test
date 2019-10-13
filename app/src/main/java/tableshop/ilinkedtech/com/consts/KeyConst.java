package tableshop.ilinkedtech.com.consts;


/*
 *  @项目名：  iShop
 *  @包名：    ishop.ilinkedtech.com.consts
 *  @文件名:   KeyConst
 *  @创建者:   Wilson
 *  @创建时间:  2017/4/1 17:31
 *  @描述：    TODO  key的值表 常量类
 */
public class KeyConst {
    public static final String TAG = "KeyConst";


    public static final String HAPPY_PLAY_PAKEGE = "com.hpplay.happyplay.aw";//乐播投屏应用包名
    public static final String HAPPY_PLAY_PAKEGE_SERVICE1 = "com.hpplay.happyplay.aw:service1";//乐播投屏应用包名
    public static final String HAPPY_PLAY_PAKEGE_SERVICE2 = "com.hpplay.happyplay.aw:service2";//乐播投屏应用包名

//    if ([url hasPrefix:@"/iCanPayment"] || [url hasPrefix:@"/iCanAI"] || [url hasPrefix:@"/iCanUser"])
    /**
     * http请求的项目名
     */
    public static final class HttpsContentUrlHead{
        public static final String ICANPAYMENT="iCanPayment";
        public static final String ICANAI="iCanAI";
        public static final String ICANUSER="iCanUser";
        public static final String USER_AGENT="android_ishop";//给浏览器设置代理头
        public static final String ICAN_ADMIN="iCanAdmin";
    }
    public static final class VehicleSellableState{
        public static final int ON_SALES_VEHICLE=0;
        public static final int DELIVERY_VEHICLE=3;
        public static final int PURCHASE_VEHICLE=1;
    }
    public static final class LanguageLocal{
        public static final String ZH_CN="zh_CN";
    }



    /**
     * onActivityResult 调用相片
     */
    public static final class RequestCode {
        public static final int SELETED_AVATAR      = 123;//选取图片当做用户头像
        public static final int SELETED_AVATAR_NEXT = 124;//选取图片后，对图片进行裁剪
        public static final int SELETED_PICTURE     = 121;//调用摄像头选择证件图片

        public static final int REQUEST_CODE     = 210;//调用摄像头选择证件图片


        public static final int REQUEST_CODE_ASK_CALL_PHONE = 21;//请求打电话权限
        public static final int REQUEST_CODE_ASK_SEND_SMS   = 22;//请求发送短信权限
        public static final int REQUEST_CODE_ASK_CAMERA = 23;//请求使用摄像头权限
        public static final int REQUEST_READ_EXTERNAL_STORAGE = 24;//请求读取手机存储
        public static final int RESULT_CODE_SELETE_BRAND = 25;//选择品牌
    }

    /**
     * 常用保存文件名
     */
    public static class FilePath {
        public static final int    UPLOAD_PICTURE_SIZE      = 300;//设置上传图片的   像素=UPLOAD_PICTURE_SIZE*UPLOAD_PICTURE_SIZE

    }

    public final class RequestParamsKey{
        public static final String LOGIN_NAME="j_username";
        public static final String LOGIN_PASSWORD="j_password";
        public static final String LOGIN_MOBILE="j_mobile";
        public static final String LOGIN_SMS_CODE="j_sms_code";
        public static final String LOGIN_ISDEALER="isDealer";
    }

    public static final class CarImageType {
        public static final int FIT_CENTER=1;//即缩放图像让图像都测量出来等于或小于 ImageView 的边界范围。该图像将会完全显示，但可能不会填满整个 ImageView
        public static final int CENTER_CROP=2;//即缩放图像让它填充到 ImageView 界限内并且侧键额外的部分。ImageView 可能会完全填充，但图像可能不会完整显示

        public static final String ALL            = "all";//车身+内饰+视频
        public static final String EXTERIOR            = "O";//车身图
        public static final String INTERIOR       = "I";//内饰图
        public static final String TYPE_VIDEO       = "V";//视频
        public static final String TYPE_PICTURE       = "P";//图片文件
    }

    public final class ResponseStatues{
        public static final String SUCCESS="Success";
        public static final String FAIL="Fail";
    }

    public final class ContentType{
        public static final String ContentType="Content-Type";
        public static final String URLENCODED="application/x-www-form-urlencoded";
        public static final String APPLICATION_JSON="application/json";
    }

    //TODO 离线工作功能类型
    public static final class CacheType {
        public static final int NO_CACHE     = 0;//TODO 不做离线保存
        public static final int OFFLINE_WORK = 1;//TODO 离线查看，保存请求结果
    }

    //TODO 离线工作功能类型
    public static final class AliPayKey {
        public static final int SDK_PAY_FLAG = 1;
        public static final int SDK_AUTH_FLAG = 2;
    }
    //TODO 数据缓存的key
    public static final class DBDataKey {
        public static final String KET_DB_CHANNEL_LIST = "Channel_list";//保存我的频道信息
    }
    //TODO 收藏
    public static final class LocalData {
        public static final String KET_DB_FAVORITE = "Favorite";
        public static final String KET_DB_PRODUCT_FAVORITE = "KET_DB_PRODUCT_FAVORITE";
        public static final String KET_DB_CONTRAST = "contrast";
        public static final int    ORIENTATION_DEL = 1;
        public static final int    ORIENTATION_ADD = 2;
    }

    public static final class Lead {
        public static final String OPPORTUNITYID            = "opportunityId";
        public static final String DEFAULT_AVATAR_URL       = "http://icanhk-public.oss-cn-hongkong.aliyuncs.com/avatar/default_avatar.jpg";//默认头像的下载地址
        public static final String DEFAULT_AVATAR_FILE_NAME = "default_avatar.jpg";//默认头像保存的用户名
    }

    public static final class OSS_FILE {
        public static final String FILE_TYPE     = "fileType";//文件类型 ，对应下面的FileType
        public static final String OPPORTUNITYID = "opportunityId";//用户的currentRoleId
        public static final String TASKID        = "taskId";//任务Id
        public static final String FILE          = "file";

        public static final class FileType {
            public static final String CUSTOMER_AVATAR = "CUSTOMER_AVATAR";//上传lead的 头像  必须带opportunityId
            public static final String MEMBER_AVATAR          = "MEMBER_AVATAR";//上传（user）用户的 头像  必须带opportunityId
            public static final String AVATAR          = "AVATAR";//上传（user）用户的 头像  必须带opportunityId
            public static final String DOCUMENT_TASK   = "DOCUMENT_TASK";//上传任务里面的录音文件，必须带taskid
        }
    }


    /**
     * 列表常用
     */
    public static class ListD {
        public static final int MAX_RESULTS    = 10;//每次向服务器请求的条目数
        public static final int MAX_PAGE       = 1;//最大的页数，用于防止后台无线循环给数据
        public static final int MAX_SHOW_ITEMS = 3;//用于工作台每种条目类型 最多显示的条目数

        public static class FileterTag {
            public static final String ALL = "ALL";//全部

            /**
             * 搜索结果的排序类型
             */
            public static final class SortOrder{
                public static final String ASC = "ASC";//升序
                public static final String DESC = "DESC";//降序
            }
            /**
             * 搜索结果的排序类型
             */
            public static final class SortType {
                public static final String BRAND = "BRAND";//品牌
                public static final String SERIES = "SERIES";//车系
                public static final String MODEL = "MODEL";//车型
                public static final String PRICE = "PRICE";//价格
                public static final String SEAT = "SEAT";//座位数
            }


            /**
             * 搜索结果的排序类型
             */
            public static class SearchType {
                public static final String MOBILE    = "MOBILE";//手机号
                public static final String FIRSTNAME = "FIRSTNAME";//firstname
                public static final String LASTNAME  = "LASTNAME";//lastname
            }

        }
    }

    public static final class CategoryType {
        public static final String VEHICLE = "vehicle";//汽车
        public static final String PRODUCT = "product";//商品
    }

    /**
     * TODO ListView SupperBaseAdapter的常用key
     */
    public static class ItemView {
        // 我的频道 标题部分
        public static final int TYPE_MY_CHANNEL_HEADER = 0;
        // 我的频道
        public static final int TYPE_MY = 1;
        // 其他频道 标题部分
        public static final int TYPE_OTHER_CHANNEL_HEADER = 2;
        // 其他频道
        public static final int TYPE_OTHER = 3;

        public static final int TYPE_CATEGORY=1;//车型
        public static final int TYPE_PRICE=2;//价格
        public static final int TYPE_SEAT=3;//座位


    }

    public static final class SharePreKey {
        public static final String HOT_BRAND_LIST = "hotbrandlist";//热销品牌
        public static final String ALL_BRAND_LIST = "allbrandlist";//所有品牌
        public static final String CUSTOME_TOKEN = "custome_token";//所有品牌
    }
    public static final class HeadKey {
        public static final String USERNAME = "X-USERNAME";
        public static final String CUSTOME_TOKEN = "X-CustomToken";
        public static final String X_DEBUG = "X-DEBUG";
    }


    public static final class BundleIntentKey {
        public static final String REQUEST_BEAN_JSON = "requestBeanJson";//请求回来的jasonBean
        public static final String TITLE             = "title";
        public static final String BRAND_ID          = "brand_id";
        //        public static final String START_DATE             = "startDate";
        public static final String SHOW_FILTER_VIEW  = "filter_type";
        public static final String CUSTOMER_STATUS   = "customer_status";
        public static final String DATA_JSON         = "data_json";//用户传送具体数据的json 例如车辆信息
        public static final String BOTTOM_POSITION   = "bottom_position";//设置底部菜单栏显示的页面
        public static final String POSITION   = "position";//筛选的position
        public static final String TOKEN_OVERDUE     = "token_overdue";//用于识别token是否过期
        public static final String TASK_UID          = "task_uid";
        public static final String AVATARLOC         = "avatarLoc";//用于预览大图使用
        public static final String MSG_UID           = "msg_uid";
        public static final String URL               = "url";
        public static final String SALES_ID          = "salesId";
        public static final String VEHICLE_ID          = "vehicleId";
        public static final String VEHICLE_FLAG          = "vehicle_flag";
        public static final String PRODUCT_ID          = "Product_ID";
        public static final String BASE_HOST          = "base_host";
    }


    /**
     * 后台指向
     */
    public static final class BackendType {
        public static final String DEV    = "DEV";
        public static final String PD     = "PD";
        public static final String TE     = "TE";
        public static final String CUSTOM = "CUSTOM";
    }


}
