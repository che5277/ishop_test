package tableshop.ilinkedtech.com.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.beans.events.PayResultBean;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;

//支付结果页面
public class WXPayEntryActivity
        extends Activity
        implements IWXAPIEventHandler
{

    private static final String TAG          = "WXPayEntryActivity";

    public static boolean isPayCalling = false;
    @BindView(R.id.iv_code_img)
    ImageView mIvCodeImg;
    @BindView(R.id.tv_result)
    TextView  mTvResult;
    private        IWXAPI api;
    private static String extData;

    public static void setExtData(String data) {
        extData = data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        ButterKnife.bind(this);

        api = WXAPIFactory.createWXAPI(this, Const.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            isPayCalling = false;
            LogUtils.d(TAG, resp.errStr + ":onPayFinish, errCode = " + resp.errCode);
            int type = -1;
            if (resp.errCode == 0) {
                //TODO 进入支付成功页面
                type = PayResultBean.TYPE_SUSSECE;
            } else if (resp.errCode == -2) {
                //TODO 取消支付对应的操作
                type = PayResultBean.TYPE_CANCLE;
            } else {
                type = PayResultBean.TYPE_ERROR;
            }

            PayResultBean payResultBean =new PayResultBean(type);
//            payResultBean.resultState=type;
            EventBus.getDefault().post(payResultBean);
            finish();
//            doOnPayFinish(type);

        }

    }

    private void showImagDialog(Bitmap stringtoBitmap, final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        ImageView imageView =new ImageView(activity);
        LinearLayout.LayoutParams mImageViewLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                               ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(mImageViewLP);
        imageView.setImageBitmap(stringtoBitmap);
        builder.setView(imageView);
        builder.setTitle(R.string.提示);
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