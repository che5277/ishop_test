package tableshop.ilinkedtech.com.callBacks;

import java.io.File;

/*
 *  @项目名：  iShop
 *  @包名：    com.ilinkedtech.callBacks
 *  @文件名:   MyICanFileCallBack
 *  @创建者:   Wilson
 *  @创建时间:  2017/4/9 13:52
 *  @描述：    TODO
 */
public abstract class MyICanFileCallBack {
    public String destFileDir;
    public String destFileName;
    public String filePath;

    public MyICanFileCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        filePath=destFileDir+destFileName;
    }

    /**
     * 决定是否继续下载
     * @param requestFileLength
     * @return  true 不下载，使用本地文件       fale 继续下载
     */
    public boolean doBeforDownLoad(long requestFileLength){
        return false;
    };

    public abstract void onResponse(File respone, int id);

    public abstract void onError(Exception e, int id);

    public void inProgress(float progress, long total, int id){

    };

}
