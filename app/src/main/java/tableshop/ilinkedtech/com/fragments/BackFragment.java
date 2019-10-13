package tableshop.ilinkedtech.com.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tableshop.ilinkedtech.com.R;


/**
 * Created by TO on 2018/3/18.
 */

public  class BackFragment extends DialogFragment implements View.OnClickListener {

    ImageView iv_picture;
    TextView tv_money;
    EditText et_number;
    TextView tv_cancel;
    TextView tv_determine;
    IsSuccessCallBack mIsSuccessCallBack;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_payingCancel:
                dismiss();
                break;
            case R.id.tv_Payingdetermine:

                if (mIsSuccessCallBack!=null&&et_number.getText().toString().length()==4&&et_number.getText().toString().equals("2010")) {
                    mIsSuccessCallBack.OnIsSuccessCallBack();
                    dismiss();
                }
                else
                    Toast.makeText(getActivity(),"请输入正确的4位密码！",Toast.LENGTH_SHORT).show();
                break;
        }

    }

  public   interface IsSuccessCallBack{
        void OnIsSuccessCallBack();
    }
    public  void CallBackSuccess(IsSuccessCallBack isSuccessCallBack){
        this.mIsSuccessCallBack=isSuccessCallBack;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater =getActivity().getLayoutInflater();
        View view =layoutInflater.inflate(R.layout.back_fragment,null);
        iv_picture= (ImageView) view.findViewById(R.id.paying_picture);
        tv_money= (TextView) view.findViewById(R.id.paying_money);
        et_number= (EditText) view.findViewById(R.id.paying_number);
        tv_cancel= (TextView) view.findViewById(R.id.tv_payingCancel);
        tv_determine= (TextView) view.findViewById(R.id.tv_Payingdetermine);
        tv_cancel.setOnClickListener(this);
        tv_determine.setOnClickListener(this);

        builder.setView(view);
        return builder.create();
    }
}
