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
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.adapters.OrderDetailProductAdapter;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.main.OrderListItemBean;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.FileUtil;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.SystemApiUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.imag.ImageLoadUtils;
import tableshop.ilinkedtech.com.views.recyles.RecycleViewDivider;

@SuppressLint("ValidFragment")
public class ActProductOrderDetailFragment
        extends IShopBaseFragment
        implements View.OnClickListener
{


    OrderListItemBean orderListDataBean;
    Unbinder          unbinder;
    @BindView(R.id.recy)
    RecyclerView mRecy;
    @BindView(R.id.tv_totle_price)
    TextView     mTvTotlePrice;
    @BindView(R.id.ll_order_item)
    LinearLayout mLlOrderItem;
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
    private Bitmap                     mBitmap;
    private ArrayList<ProductItemBean> mArrayList;
    private OrderDetailProductAdapter mOrderDetailProductAdapter;

    public ActProductOrderDetailFragment(OrderListItemBean json) {
        this.orderListDataBean = json;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail_product;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initEvent() {
        mBtnSave.setOnClickListener(this);

        mArrayList=new ArrayList<>();
        GridLayoutManager gridLayoutManager =new GridLayoutManager(getContext(),1);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);

        mRecy.setLayoutManager(gridLayoutManager);
        mOrderDetailProductAdapter = new OrderDetailProductAdapter(
                mArrayList,
                getActivity());
        mRecy.setAdapter(mOrderDetailProductAdapter);
        mRecy.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL));
        mRecy.setNestedScrollingEnabled(false);

    }

    @Override
    public void refreshDatas() {
        if (orderListDataBean != null) {
            mArrayList.addAll(orderListDataBean.productDetails);
            mOrderDetailProductAdapter.notifyDataSetChanged();
            mTvTotlePrice.setText("合计：¥"+StringUtils.checkString(orderListDataBean.totalAmount));

            mTvShopName.setText(UIUtils.getString(R.string.深圳市领骏达旗舰店));
            mTvShopAddress.setText(UIUtils.getString(R.string.广东省深圳市南山区海天一路软件产业基地5C大堂));
            mBitmap = ImageLoadUtils.stringtoBitmap(orderListDataBean.vehicleQrCode);
            mIvQrcode.setImageBitmap(mBitmap);
            mTvOrderAlert.setText(R.string.商品二维码描述);

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

}
