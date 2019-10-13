package tableshop.ilinkedtech.com.utils;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.utils
 *  @文件名:   DialogUtils
 *  @创建者:   Wilson
 *  @创建时间:  2017/8/15 15:52
 *  @描述：    TODO
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import tableshop.ilinkedtech.com.R;

import static tableshop.ilinkedtech.com.utils.UIUtils.getString;

public class DialogUtils {

    /**
     * 弹出显示订单的二维码图片对话框
     * @param stringtoBitmap
     * @param activity
     */
    public static void showQRDialog(Bitmap stringtoBitmap, final Activity activity) {
        AlertDialog.Builder builder   = new AlertDialog.Builder(activity);
        ImageView           imageView =new ImageView(activity);
        LinearLayout.LayoutParams mImageViewLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                               ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(mImageViewLP);
        imageView.setImageBitmap(stringtoBitmap);
        builder.setView(imageView);
        builder.setCancelable(false);
        builder.setTitle(activity.getString(R.string.已经保存到相册根目录));
        builder.setNeutralButton(getString(R.string.确定), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                activity.finish();
                UIUtils.activityBackToMain(activity);
            }
        });
        builder.show();

    }
}
