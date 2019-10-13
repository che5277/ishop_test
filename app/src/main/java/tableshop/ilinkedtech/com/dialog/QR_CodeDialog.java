package tableshop.ilinkedtech.com.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tableshop.ilinkedtech.com.R;


/**
 * Created by TO on 2018/4/18.
 */

public class QR_CodeDialog extends DialogFragment {

    ImageView iv_qr;
    TextView tv_tonext;
    TextView tv_text;
    Bitmap mBitmap;
    int mImageView;
    boolean mIsOther;
    LinearLayout linearLayout;
    public void setBitmap(Bitmap bitmap){
        this.mBitmap=bitmap;

    }

    public void setImages(int mImagesId){
        this.mImageView=mImagesId;
    }

    public void setQRCodeToshopCallBack(QR_CodeToShopCallBack qrCodeToshopCallBack){
        this.mQR_CodeToShopCallBack =qrCodeToshopCallBack;

    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(500,450);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity()) ;
        final LayoutInflater inflater =getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.qr_view,null);

        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout=view.findViewById(R.id.rl_QRCODE);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        iv_qr=view.findViewById(R.id.iv_QR_code);
        tv_tonext=view.findViewById(R.id.tv_qr_nextCode);
        tv_text=view.findViewById(R.id.tv_qr_text);


        if (tv_text!=null&&tv_tonext!=null&&mIsOther) {
            tv_text.setText("使用APP扫描购买商品");
            tv_tonext.setVisibility(View.GONE);
        }


        initEvent();

       if (mBitmap!=null)
          iv_qr.setImageBitmap(mBitmap);
       else if (mImageView!=0) {
           iv_qr.setImageResource(mImageView);
         //  iv_qr.setMaxHeight(180);
         //  iv_qr.setMaxWidth(180);
         //  iv_qr.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
       }
        builder.setView(view);
        return builder.create();
    }

    private void initEvent(){
        if (tv_tonext!=null)
        tv_tonext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQR_CodeToShopCallBack!=null)
                    mQR_CodeToShopCallBack.OnQR_CodeToShopCallBack();
                    dismiss();
            }
        });
    }

    public void  setVisible(boolean isOther){
        this.mIsOther=isOther;
    }

    QR_CodeToShopCallBack mQR_CodeToShopCallBack;
  public   interface QR_CodeToShopCallBack{
        void OnQR_CodeToShopCallBack();
    }

}
