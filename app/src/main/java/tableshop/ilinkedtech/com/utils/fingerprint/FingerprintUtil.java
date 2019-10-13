package tableshop.ilinkedtech.com.utils.fingerprint;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Wilson on 2017/12/6.
 */

public class FingerprintUtil {

    private static final String ACTION_SETTING = "android.settings.SETTINGS";

    public static void openFingerPrintSettingPage(Context context) {
        Intent intent = new Intent(ACTION_SETTING);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }
}
