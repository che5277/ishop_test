package tableshop.ilinkedtech.com.beans;

/*
 *  @项目名：  ICan 
 *  @包名：    com.ilinkedtech.bean
 *  @文件名:   VersionCheckResponBean
 *  @创建者:   wilson
 *  @创建时间:  2016/12/3 15:37
 *  @描述：    TODO
 */
public class VersionCheckResponBean {
    private static final String TAG = "VersionCheckResponBean";

    /**
     * message : 获取版本[1.0.1]成功!
     * status : 1
     * version : {"url":"#","content":"更新1.0.1","time":"1480750643246","update_type":"1"}
     * request : {"os_version":"4.2.2","version_number":"1.00","operation_mode":"1","position_information":null,"time_information":"1480747788626","time_zone":null,"landing_number":null,"phone_model":"GT-P5210","visitor_system":"2","projectname":"1"}
     */

    /**
     * url : #
     * content : 更新1.0.1
     * time : 1480750643246
     * update_type : 1
     */

    /**
     * os_version : 4.2.2
     * version_number : 1.00
     * operation_mode : 1
     * position_information : null
     * time_information : 1480747788626
     * time_zone : null
     * landing_number : null
     * phone_model : GT-P5210
     * visitor_system : 2
     * projectname : 1
     */
    public String      message;
    public int         status;
    public VersionBean version;

    public VersionCheckRequestBean request;

    public static class VersionBean {
        public String url;
        public String content;
        public String time;
        public String update_type;
    }

}
