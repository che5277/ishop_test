package tableshop.ilinkedtech.com.beans.events;

/*
 *  @文件名:   MenberSateBean
 *  @创建者:   Wilson
 *  @创建时间:  2018/1/23 10:19
 *  @描述：    TODO
 */

public class MenberSateBean {

    public boolean isLogin;
    public String userName;
    public static MenberSateBean newInstance() {
        return new MenberSateBean();
    }
}
