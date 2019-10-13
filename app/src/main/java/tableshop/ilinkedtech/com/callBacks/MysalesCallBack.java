package tableshop.ilinkedtech.com.callBacks;

/*
 *  @项目名：  iShop
 *  @包名：    com.ilinkedtech.callBacks
 *  @文件名:   MysalesCallBack
 *  @创建者:   Wilson
 *  @创建时间:  2017/3/6 12:32
 *  @描述：    TODO
 */
public interface MysalesCallBack {
    void onError(Exception e, int id);
    void onResponse(String response, int id);

    void doAffterRequestCall();
}
