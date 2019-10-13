package tableshop.ilinkedtech.com.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;
import java.util.Locale;

import tableshop.ilinkedtech.com.MainApp;
import tableshop.ilinkedtech.com.R;


/**
 * 类    名:  UIUtils
 * 创 建 者:  wilson
 * 创建时间:  2016/8/20 10:59
 * 描    述：常见的一些和ui操作相关的方法
 */
public class UIUtils {


    private static final java.lang.String TAG = "UIUtils";

    /**获取虚拟功能键高度 */
    public static int getVirtualBarHeigh(Context context) {
        int            vh            = 0;
        try {
            WindowManager  windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display        display       = windowManager.getDefaultDisplay();
            DisplayMetrics dm            = new DisplayMetrics();
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }
    /**
     * 得到上下文
     *
     * @return
     */
    public static Context getContext() {
        return MainApp.getContext();
    }

    /**
     * 得到Resource对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到String.xml中定义的字符串信息
     */
    public static String getString(@StringRes int resId) {
        return getResources().getString(resId);
    }

    public static Drawable getDrawable(@DrawableRes int drawableId) {
        return getResources().getDrawable(drawableId);
    }

    /**
     * 得到String.xml中定义的字符串数组信息
     */
    public static String[] getStringArray(@ArrayRes int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 得到Color.xml中定义的颜色信息
     */
    public static int getColor(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 得到应用程序的包名
     *
     * @return
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * dp-->px
     *
     * @param dp
     * @return
     */
    public static int dp2Px(int dp) {
        //dp和px相互转换的公式
        //公式一:px/dp = density
        //公式二:px/(ppi/160) = dp
        /*
           480x800  ppi=240    1.5
           1280x720 ppi = 320   2
         */
        float density = getResources().getDisplayMetrics().density;
        int px = (int) (dp * density + .5f);
        return px;
    }

    /**
     * px-->do
     *
     * @param px
     * @return
     */
    public static int px2Dp(int px) {
        //dp和px相互转换的公式
        //公式一:px/dp = density
        //公式二:px/(ppi/160) = dp
        /*
           480x800  ppi=240    1.5
           1280x720 ppi = 320   2
         */
        float density = getResources().getDisplayMetrics().density;
        int dp = (int) (px / density + .5f);
        return dp;
    }

    /**
     * sp-->px
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static void activityAnimInt(Activity activity){
        activity.overridePendingTransition(R.anim.enter, R.anim.exit);;
    }
    public static void activityBackToMain(Activity activity){
        activity.overridePendingTransition(R.anim.reverse_enter, R.anim.reverse);
    }


    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     */
    public static void closeKeyboard(EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) mEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    /**
     * 设置语言
     * @param activity
     */
    public static void setLanguage(Activity activity) {
//        Locale locale = Const.getLanguage(sharedStorage.getDefaultLanguage());
        Locale locale=Locale.JAPANESE;//修改字体
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.locale = locale;
        DisplayMetrics dm = activity.getBaseContext()
                                    .getResources()
                                    .getDisplayMetrics();
        activity.getBaseContext()
                     .getResources()
                     .updateConfiguration(config, dm);
//        SharedStorage.resetDatas();
    }

    private static final float widthHeightScale=(9.0f/16.0f);
    public static void reSetLayoutParams(View view){
        if (view instanceof ViewGroup){
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams!=null&&layoutParams.width>0) {
                layoutParams.height = (int) (layoutParams.width * widthHeightScale);
                view.setLayoutParams(layoutParams);
            }
        }
    }

}
