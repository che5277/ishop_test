package tableshop.ilinkedtech.com.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;

import tableshop.ilinkedtech.com.R;


/**
 * Created by vondear on 15/6/15.
 *
 */
public class RxDialogShapeLoading {

    private Context            mContext;
    private Dialog             mDialog;
    private RxShapeLoadingView mLoadingView;
    private View               mDialogContentView;


    public RxDialogShapeLoading(Context context) {
        this.mContext=context;
        init();
    }

    private void init() {
        mDialog = new Dialog(mContext, R.style.custom_dialog);
        mDialogContentView= LayoutInflater.from(mContext).inflate(R.layout.dialog_shape_loading_view, null);
        mLoadingView= (RxShapeLoadingView) mDialogContentView.findViewById(R.id.loadView);
        mDialog.setContentView(mDialogContentView);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
    }

    public void setBackground(int color){
        GradientDrawable gradientDrawable = (GradientDrawable) mDialogContentView.getBackground();
        gradientDrawable.setColor(color);
    }

    public void setLoadingText(CharSequence charSequence){
        mLoadingView.setLoadingText(charSequence);
    }

    public void show(){
        mDialog.show();

    }

    public void dismiss(){
        mDialog.dismiss();
    }

    public Dialog getDialog(){
        return  mDialog;
    }

    public void setCanceledOnTouchOutside(boolean cancel){
        mDialog.setCancelable(cancel);
        mDialog.setCanceledOnTouchOutside(cancel);
    }
}
