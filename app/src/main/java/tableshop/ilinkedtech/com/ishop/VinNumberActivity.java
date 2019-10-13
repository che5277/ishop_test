package tableshop.ilinkedtech.com.ishop;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com
 *  @文件名:   VinNumberActivity
 *  @创建者:   Administrator
 *  @创建时间:  2017/7/13 18:33
 *  @描述：    TODO 选车条目跳转到的选择具体车辆的view
 */

import java.util.ArrayList;
import java.util.List;

import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.beans.main.CarModelListItemBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.fragments.main.CaDetailListFragment;

public class VinNumberActivity
        extends BaseActionBarActivity
{
    private CaDetailListFragment mCaDetailListFragment;


    @Override
    protected IShopBaseFragment getShowFragment() {
        mCaDetailListFragment = CaDetailListFragment.newInstance(true);
        CarModelListItemBean carModelListItemBean=null;
        try {
//            carModelListItemBean = new Gson().fromJson(getIntent().getStringExtra(
//                    KeyConst.BundleIntentKey.DATA_JSON), CarModelListItemBean.class);
            carModelListItemBean = getIntent().getParcelableExtra(KeyConst.BundleIntentKey.DATA_JSON);
        }catch (Exception e){

        }
        if (carModelListItemBean!=null&&carModelListItemBean.vehicleStockSingleViewList!=null){
            List<CarDetailBean> detailBeanArrayList =carModelListItemBean.vehicleStockSingleViewList;
            CarDetailBean detailBean=null;
            if (carModelListItemBean.purchaseVehicleViewList!=null&&carModelListItemBean.purchaseVehicleViewList.size()>0){
                for (int i = 0; i < carModelListItemBean.purchaseVehicleViewList.size(); i++) {
                    detailBean = carModelListItemBean.purchaseVehicleViewList.get(i);
                    detailBean.sellableState=KeyConst.VehicleSellableState.PURCHASE_VEHICLE;
                    detailBeanArrayList.add(detailBean);
                }
            }
            if (carModelListItemBean.deliveryVehicleViewList!=null&&carModelListItemBean.deliveryVehicleViewList.size()>0){
                for (int i = 0; i < carModelListItemBean.deliveryVehicleViewList.size(); i++) {
                    detailBean = carModelListItemBean.deliveryVehicleViewList.get(i);
                    detailBean.sellableState=KeyConst.VehicleSellableState.DELIVERY_VEHICLE;
                    detailBeanArrayList.add(detailBean);
                }
            }
            mCaDetailListFragment.setArrayDatas((ArrayList<CarDetailBean>) detailBeanArrayList);
        }
        return mCaDetailListFragment;
    }

}
