package tableshop.ilinkedtech.com.beans.events;

/*
 *  @文件名:   BaseUrlChangeBean
 *  @创建者:   Wilson
 *  @创建时间:  2018/2/1 14:33
 *  @描述：    TODO
 */

public class BackToHome {

    public int position;

    public BackToHome(int i) {
        this.position=i;
    }

    public static BackToHome newInstance(int fragmentHome) {
        return new BackToHome(fragmentHome);
    }
}
