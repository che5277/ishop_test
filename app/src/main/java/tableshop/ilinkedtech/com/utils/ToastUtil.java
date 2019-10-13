package tableshop.ilinkedtech.com.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.consts.Const;


/**
 * 创 建 者:  Wilson
 * 创建时间:  2017/3/17 14:03
 */

public class ToastUtil {

    private static Toast toast;

    public static void toast(String msg){
        toast(UIUtils.getContext(),msg);
    }

    public static void dismiss(){
        if (toast!=null){
            toast.cancel();
        }
    }

    public static void showSnackbar(View view, String msg, String actionTex, View.OnClickListener listener){
        Snackbar mSnackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                            .setAction(StringUtils.isEmpty(actionTex) ? Const.DEBUG_SNAKBAR_ACTION_TEX : actionTex, listener);
        View sbView = mSnackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(UIUtils.getContext(), R.color.item_seleted));
        sbView.setBackgroundColor(UIUtils.getColor(R.color.snackbar_bg));
        mSnackbar.show();
    }

    public static void showSnackbar(View view,String msg){
        showSnackbar(view,msg,null,null);
    }

    public static void toast(Context applicationContext, String msg) {
        if(toast==null) {
            toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT);
        }else {
            toast.setText(msg);
        }
        toast.show();
    }
}
