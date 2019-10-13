package tableshop.ilinkedtech.com.beans;

/*
 *  @项目名：  ICan 
 *  @包名：    com.ilinkedtech.bean
 *  @文件名:   VersionCheckRequestBean
 *  @创建者:   wilson
 *  @创建时间:  2016/12/3 14:16
 *  @描述：    TODO 检测版本更新的请求体
 */
public class VersionCheckRequestBean {

    /**
     * os_version : 4.2.2
     * version_number : 2.0
     * operation_mode : 1
     * time_information : 1023152156
     * visitor_system : 2
     * position_information :
     * time_zone :
     * landing_number :
     * phone_model : GT-P5210
     * projectname : 1
     */

    public String os_version;//系统版本*
    public String version_number;//版本号*
    public String operation_mode;//请求更新类型：1.被动请求  2.主动请求*
    public String time_information;//请求更新的时间*
    public String visitor_system;//1.IOS  2.ANDROID*
    public String projectname="iTableShop";//工程名* iShop iCan iCanAI
    public String position_information;
    public String time_zone;
    public String landing_number;
    public String phone_model;//机子型号
}
