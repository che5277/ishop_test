package tableshop.ilinkedtech.com.db;

/*
 *  @项目名：  iShop
 *  @包名：    ishop.ilinkedtech.com.db
 *  @文件名:   DBHelper
 *  @创建者:   Wilson
 *  @创建时间:  2017/6/27 14:36
 *  @描述：    TODO
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;


public class DBHelper {

    private DaoSession mDaoSession;
    private static        DBHelper   mDBHelper;

    private DBHelper(Context context){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, SharedStorage.userName+".db", null);
        DaoMaster               daoMaster     = new DaoMaster(devOpenHelper.getWritableDb());
        LogUtils.e("DBHelper===db name:"+devOpenHelper.getDatabaseName());
        mDaoSession = daoMaster.newSession();
    }

    public static DBHelper getInstance() {
        if (mDBHelper == null){
            synchronized (DBHelper.class) {
                if (mDBHelper==null) {
                    mDBHelper = new DBHelper(UIUtils.getContext());
                }
            }
        }
        return mDBHelper;
    }


    public static void clearDBHelper(){
        getInstance().mDaoSession=null;
        mDBHelper=null;
    }

    /**
     * 获取DBBeanDao操作数据库
     * @return
     */
    public DBJsonBeanDao getDBBeanDao(){
        return mDaoSession.getDBJsonBeanDao();
    }

    private static String DB_PATH = "/data/data/ishop.ilinkedtech.com/databases/";

    /**
     * 判断是否存在该数据库文件
     * @param username
     * @return
     */
    public static boolean hasDbFile(String username){
        try {
            SQLiteDatabase database = SQLiteDatabase.openDatabase(DB_PATH+username+".db", null,
                                                                  SQLiteDatabase.OPEN_READONLY);
            if (database!=null){
                return true;
            }
        }catch (SQLiteException e){
            return false;
        }
        return false;
    }
}
