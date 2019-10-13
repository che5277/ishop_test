package tableshop.ilinkedtech.com.jpush;

import android.app.Notification;
import android.content.Context;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.MultiActionsNotificationBuilder;
import tableshop.ilinkedtech.com.R;


public class JPushUtils {

    public static void initPushNotification(Context context) {

        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
        builder.statusBarDrawable = R.mipmap.ic_launcher;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(1, builder);

        MultiActionsNotificationBuilder builder2 = new MultiActionsNotificationBuilder(context);
        //添加按钮，参数(按钮图片、按钮文字、扩展数据)
        builder2.addJPushAction(R.drawable.confirm_no, "first", "my_extra1");
        builder2.addJPushAction(R.mipmap.icon, "second", "my_extra2");
        builder2.addJPushAction(R.drawable.filter, "third", "my_extra3");
        JPushInterface.setPushNotificationBuilder(2, builder2);

        CustomPushNotificationBuilder builder3 = new CustomPushNotificationBuilder(context,
                                                                                   R.layout.customer_notitfication_layout,
                                                                                   R.id.icon,
                                                                                   R.id.title,
                                                                                   R.id.text);
        // 指定定制的 Notification Layout
        builder3.statusBarDrawable = R.mipmap.ic_launcher;
        // 指定最顶层状态栏小图标
        builder3.layoutIconDrawable = R.mipmap.ic_launcher;
        // 指定下拉状态栏时显示的通知图标
        JPushInterface.setPushNotificationBuilder(3, builder3);


//        CustomPushNotificationBuilder builder4 = new CustomPushNotificationBuilder(context, R.layout.customer_notitfication_layout_2, R.id.icon, R.id.title, R.id.text);
//        builder4.layoutIconDrawable = R.mipmap.ic_launcher;
//        builder4.developerArg0 = "developerArg2";
//        JPushInterface.setPushNotificationBuilder(4, builder4);

        JPushInterface.setDefaultPushNotificationBuilder(builder3);
    }
}
