package tableshop.ilinkedtech.com.utils;

/*
 *  @文件名:   AlertUtils
 *  @创建者:   Wilson
 *  @创建时间:  2017/11/1 15:23
 *  @描述：    TODO
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.ishop.CarDetailActivity;


public class AlertUtils {


    public static boolean isNeutralOnRight=true;//“确定”按钮是否显示在右边
    private static Dialog dialog;

    /**
     * @param context
     * @param title 提示标题
     * @param cancleable   是否可以点击外部取消对话框
     * @param view   要包含的view
     * @param msg   提示消息
     * @param neutralMsg    "确定"的提示语（左边）
     * @param negativeMsg   "取消"的提示语（右边）
     * @param neutralListener     "确定"的回调（左边）
     * @param negativeListener  "取消"的回调（右边）
     */
    public static void showDialog(Context context, boolean cancleable, View view,String title, String msg, String neutralMsg, String negativeMsg,
                                  DialogInterface.OnClickListener neutralListener, DialogInterface.OnClickListener negativeListener)
    {
        if (context==null){
            context=UIUtils.getContext();
        }
        if (view!=null){
            dismissDialog();
            dialog = new Dialog(context, R.style.DialogTheme);
            dialog.setContentView(view);
            dialog.setCancelable(cancleable);
            if (context instanceof Activity){
//                Display defaultDisplay = ((Activity) context).getWindowManager()
//                                                             .getDefaultDisplay();
//                int width = defaultDisplay.getWidth();
//                int height = defaultDisplay.getHeight();
//                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
//                params.gravity = Gravity.CENTER_VERTICAL;
//                params.width=width/2;
//                params.height=height/2;
//                params.x=(width*3/4-params.width)/2;
//                dialog.getWindow().setAttributes(params);
//                dialog.getWindow().setWindowAnimations();

            }
            dialog.show();
        }else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogTheme);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            if (!TextUtils.isEmpty(msg)) {
                builder.setMessage(msg);
            }
            builder.setCancelable(cancleable);
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            if (!TextUtils.isEmpty(neutralMsg)) {
                if (isNeutralOnRight)
                    builder.setNegativeButton(neutralMsg, neutralListener);
                else
                    builder.setNeutralButton(neutralMsg,neutralListener);
            }
            if (!TextUtils.isEmpty(negativeMsg)) {
                if (isNeutralOnRight)
                    builder.setNeutralButton(negativeMsg,negativeListener);
                else
                    builder.setNegativeButton(negativeMsg, negativeListener);
            }
            builder.show();
        }

    }

    public static void dismissDialog() {
        if (dialog!=null){
            dialog.dismiss();
            dialog=null;
        }
    }


    public static void showViewDialog(Context context, boolean cancleable, View view,String title){
        showDialog(context,cancleable,view,title,null,null,null,null,null);
    }

    public static void showViewDialog(Context context,View view,String title){
        showDialog(context,false,view,title,null,null,null,null,null);
    }
    public static Dialog showViewDialog(Context context, boolean cancleable, View view){
        Dialog dialog =new Dialog(context, R.style.DialogTheme);
        dialog.setContentView(view);
        dialog.setCancelable(cancleable);
        if (context instanceof CarDetailActivity){//||context instanceof MainActivity
            Display defaultDisplay = ((Activity) context).getWindowManager()
                                                         .getDefaultDisplay();
            int                        width  = defaultDisplay.getWidth();
            int                        height = defaultDisplay.getHeight();
            float                      scanle =0.6f;
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.gravity = Gravity.CENTER_VERTICAL|Gravity.RIGHT;
            params.width= (int) (width*scanle);
            params.height= (int) (height*scanle);
            params.x=(width*3/4-params.width)/2;
            dialog.getWindow().setAttributes(params);
        }
        dialog.show();
        return dialog;
    }

    public static void showViewDialog(Context context,View view){
        showViewDialog(context,view,null);
    }

    public static void showDialog(Context context,boolean cancleable,String title,String msg,String neutralMsg,String negativeMsg,
                                       DialogInterface.OnClickListener neutralListener,DialogInterface.OnClickListener negativeListener){
        showDialog(context,cancleable,null,title,msg,neutralMsg,negativeMsg,neutralListener,negativeListener);
    }

    public static void showDialog(Context context,String msg,String neutralMsg,String negativeMsg,
                                       DialogInterface.OnClickListener neutralListener,DialogInterface.OnClickListener negativeListener){
        showDialog(context,false,UIUtils.getString(R.string.提示),msg,neutralMsg,negativeMsg,neutralListener,negativeListener);
    }

    //修改“确定”和“取消”按钮文字提示语，并处理点击请求  对话框可以点击外部取消
    public static void showCancleableErrorDialog(Context context,String msg,String neutralMsg,String negativeMsg,DialogInterface.OnClickListener neutralListener){
        showDialog(context,true,UIUtils.getString(R.string.提示),msg,neutralMsg,negativeMsg,neutralListener,null);
    }

    //修改“确定”和“取消”按钮文字提示语，并处理点击请求
    public static void showErrorDialog(Context context,String msg,String neutralMsg,String negativeMsg,DialogInterface.OnClickListener neutralListener){
        showDialog(context,msg,neutralMsg,negativeMsg,neutralListener,null);
    }


    //修改“确定”按钮文字提示语，并处理点击请求
    public static void showErrorDialog(Context context,String msg,String neutralMsg,DialogInterface.OnClickListener neutralListener){
        showErrorDialog(context,msg,neutralMsg,null,neutralListener);
    }
    //修改“确定”按钮文字提示语，并处理点击请求
    public static Dialog showVersionUpdateDialog(Context context, String msg, String neutralMsg,String negativeMsg ,DialogInterface.OnClickListener neutralListener,DialogInterface.OnClickListener negativeListener){
        showDialog(context,false,UIUtils.getString(R.string.发现新版本),msg,neutralMsg,negativeMsg,neutralListener,negativeListener);
        return dialog;
    }

    //只做“确定”按钮提示，并处理点击请求
    public static void showErrorDialog(Context context,String msg,DialogInterface.OnClickListener neutralListener){
        showErrorDialog(context,msg,UIUtils.getString(R.string.确定),null,neutralListener);
    }

    public static void showErrorDialog(Context context,String title,String msg){
        showDialog(context,false,title,msg,UIUtils.getString(R.string.确定),null,null,null);
    }

    //只做消息提示，不做其他操作
    public static void showErrorDialog(Context context,String msg){
        DialogInterface.OnClickListener neutralListener=null;
//        showErrorDialog(context,msg,neutralListener);
        showCancleableErrorDialog(context,msg,UIUtils.getString(R.string.确定),null,neutralListener);
    }


}
