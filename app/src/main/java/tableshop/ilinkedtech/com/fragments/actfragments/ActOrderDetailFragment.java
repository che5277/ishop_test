package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @文件名:   ActOrderDetailFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/12/25 16:22
 *  @描述：    TODO
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.main.OrderListItemBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.FileUtil;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.SystemApiUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.imag.ImageLoadUtils;
import tableshop.ilinkedtech.com.viewholders.OrderListItemViewHolder;

@SuppressLint("ValidFragment")
public class ActOrderDetailFragment
        extends IShopBaseFragment
        implements View.OnClickListener
{


    OrderListItemBean orderListDataBean;
    @BindView(R.id.ll_order_item)
    LinearLayout mLlOrderItem;
    @BindView(R.id.divider_view)
    View         mDividerView;
    @BindView(R.id.tv_shop_name)
    TextView     mTvShopName;
    @BindView(R.id.tv_shop_address)
    TextView     mTvShopAddress;
    @BindView(R.id.iv_qrcode)
    ImageView    mIvQrcode;
    @BindView(R.id.tv_order_alert)
    TextView     mTvOrderAlert;
    @BindView(R.id.btn_save)
    Button       mBtnSave;
    @BindView(R.id.progress_bar)
    ProgressBar  mProgressBar;
    Unbinder unbinder;
    private Bitmap mBitmap;
    private OrderListItemViewHolder mOrderListItemViewHolder;

    public ActOrderDetailFragment(OrderListItemBean json) {
        this.orderListDataBean = json;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView() {
        int virtualBarHeigh = UIUtils.getVirtualBarHeigh(getContext());
        if (virtualBarHeigh > 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBtnSave.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, virtualBarHeigh);
            mBtnSave.setLayoutParams(layoutParams);
        }

        View                    detailHolder            =View.inflate(getActivity(),R.layout.item_order_list,mLlOrderItem);
        mOrderListItemViewHolder = new OrderListItemViewHolder(detailHolder);

    }

    @Override
    protected void initEvent() {
        mBtnSave.setOnClickListener(this);
    }

    @Override
    public void refreshDatas() {
        if (orderListDataBean != null) {
            String name = StringUtils.checkString(orderListDataBean.vehicleBrandName) + StringUtils.checkString(
                    orderListDataBean.vehicleSeriesName) + StringUtils.checkString(orderListDataBean.vehicleModelName);
            mTvShopName.setText(StringUtils.checkString(orderListDataBean.tabSysShopName));
            mTvShopAddress.setText(StringUtils.checkString(orderListDataBean.tabSysShopAddress));
            mBitmap = ImageLoadUtils.stringtoBitmap(orderListDataBean.vehicleQrCode);
            mIvQrcode.setImageBitmap(mBitmap);

            mOrderListItemViewHolder.refreshViewWithDatas(orderListDataBean);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                if (mBitmap != null) {
                    if (getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                      KeyConst.RequestCode.REQUEST_CODE_ASK_CAMERA))
                    {
                        saveBitmapToLocal();
                    }
                }
                break;
        }
    }

    public boolean getPermission(String permision, int requesCode) {
        String[] PERMISSIONS = {permision};
        if (!SystemApiUtil.hasPermissions(getActivity(), PERMISSIONS)) {
            getActivity().requestPermissions(PERMISSIONS, requesCode);
            return false;
        } else {
            return true;
        }
    }

    private void saveBitmapToLocal() {
        File file = new File(Environment.getExternalStorageDirectory(),
                             orderListDataBean.vehicleModelName + FileUtil.SUFFIX_JPG);
        // 先把图片本地保存，其次把文件插入到系统图库
        try {
            FileOutputStream out = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            String des = StringUtils.checkString(orderListDataBean.vehicleBrandName) + StringUtils.checkString(
                    orderListDataBean.vehicleSeriesName) + StringUtils.checkString(orderListDataBean.vehicleModelName);
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                                                file.getAbsolutePath(),
                                                file.getName(),
                                                des);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        //        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        AlertUtils.showErrorDialog(getContext(), UIUtils.getString(R.string.保存成功));
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
