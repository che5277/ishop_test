package tableshop.ilinkedtech.com.utils;

/*
 *  @项目名：  iShop
 *  @包名：    com.ilinkedtech.utils
 *  @文件名:   ThreadPoolUtils
 *  @创建者:   wilson
 *  @创建时间:  2016/11/5 11:54
 *  @描述：    TODO
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//线程池工具类
public class ThreadPoolUtils {
    private ExecutorService service;

    private ThreadPoolUtils() {
        //获取当前cup核数
        int num = Runtime.getRuntime().availableProcessors();
        service = Executors.newFixedThreadPool(num * 2);
    }

    private static ThreadPoolUtils manager;

    public static ThreadPoolUtils getInstance() {
        if (manager==null){
            synchronized (ThreadPoolUtils.class){
                if (manager==null){
                    manager=new ThreadPoolUtils();
                }
            }
        }
        return manager;
    }

    public void addTask(Runnable runnable) {
        service.execute(runnable);
    }

    public void shudown(){
        service.shutdownNow();
        manager=null;
        //        service.shutdown();
    }

    public static String getThreadName(){
        return Thread.currentThread().getName();
    }

}
