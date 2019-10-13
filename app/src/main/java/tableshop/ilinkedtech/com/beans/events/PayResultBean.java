package tableshop.ilinkedtech.com.beans.events;

/*
 *  @文件名:   MenberSateBean
 *  @创建者:   Wilson
 *  @创建时间:  2018/1/23 10:19
 *  @描述：    TODO
 */

public class PayResultBean {

    public static final int    TYPE_SUSSECE = 1;
    public static final int    TYPE_ERROR   = 2;
    public static final int    TYPE_CANCLE  = 3;

    public int  resultState=-1;//1付款成功
    public String userName;

    public PayResultBean(int resultState) {
        super();
        this.resultState=resultState;
    }


    public static PayResultBean newInstance(int  resultState) {
        return new PayResultBean(resultState);
    }
}
