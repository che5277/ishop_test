package tableshop.ilinkedtech.com.fragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ChannelFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 10:39
 *  @描述：    TODO
 */

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import org.json.JSONException;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.MainApp;
import tableshop.ilinkedtech.com.adapters.ChannelAdapter;
import tableshop.ilinkedtech.com.base.RecyclerListFragment;
import tableshop.ilinkedtech.com.beans.ChannelEntityBean;
import tableshop.ilinkedtech.com.beans.main.CarModelListRequestBean;
import tableshop.ilinkedtech.com.callBacks.OnMyChannelItemClickListener;
import tableshop.ilinkedtech.com.consts.FilterTagJson;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.DBJsonBean;
import tableshop.ilinkedtech.com.db.DBJsonHelper;
import tableshop.ilinkedtech.com.protocols.GetBrandsSalesList;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ThreadPoolUtils;
import tableshop.ilinkedtech.com.views.channelview.ItemDragHelperCallback;

public class ChannelFragment extends RecyclerListFragment<ChannelAdapter> {

    private static final String TAG = "ChannelFragment";
    private ArrayList<ChannelEntityBean> mItems;
    private ArrayList<ChannelEntityBean> mOtherItems;

    public static ChannelFragment newInstance() {
        ChannelFragment fragment = new ChannelFragment();
        Bundle               args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ChannelAdapter setRecycleAdapter() {
        mItems = new ArrayList<>();
        mOtherItems = new ArrayList<>();
        ItemDragHelperCallback        callback   = new ItemDragHelperCallback();
        ItemTouchHelper               helper     = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecy);
        adapter    = new ChannelAdapter(getContext(), helper, mItems, mOtherItems);
        adapter.setOnMyChannelItemClickListener(new OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                getBaseActivity().showSnackbar(mView,mItems.get(position).description);
            }
        });
        return adapter;
    }

    @Override
    protected RecyclerView.LayoutManager setRecycleLayoutManager() {

        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == KeyConst.ItemView.TYPE_MY || viewType == KeyConst.ItemView.TYPE_OTHER ? 1 : 4;
            }
        });

        return manager;
    }


    @Override
    public void initEvent() {
        super.initEvent();

    }

    @Override
    public void refreshDatas() {
        if (mOtherItems == null || isFragmentApiCall) {
            MainApp.getHandler()
                   .postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           setRefreshWidget(false);
                       }
                   }, 1000);
            return;
        }

        CarModelListRequestBean brandListRequestBean = new CarModelListRequestBean();
        GetBrandsSalesList brandsSalesList = new GetBrandsSalesList(getActivity(),
                                                                    brandListRequestBean,isLoadDataFromNet)
        {
            @Override
            public void doOnSusses(final ArrayList<ChannelEntityBean> dataBean, int id) {
                if (mOtherItems!=null&&mItems!=null) {
                    mItems.clear();
                    mOtherItems.clear();
                    if (dataBean != null && dataBean.size() > 0) {
                        ThreadPoolUtils.getInstance()
                                       .addTask(new Runnable() {
                                           @Override
                                           public void run() {
                                               int size = dataBean.size();
                                               ArrayList<ChannelEntityBean> localLists = addDefaultChannelList();
                                               if (localLists!=null&&localLists.size()>0) {
                                                   for (int i = 0; i < localLists.size(); i++) {
                                                       if (!dataBean.contains(localLists.get(i))){
                                                           dataBean.add(localLists.get(i));
                                                       }
                                                   }
                                               }
                                               doAffterGetChannelList(dataBean, size);
                                               MainApp.getHandler()
                                                      .post(new Runnable() {
                                                          @Override
                                                          public void run() {
                                                              adapter.notifyDataSetChanged();
                                                          }
                                                      });
                                           }
                                       });

                    }else {
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void doOnError(Exception e, int id) {
            }

            @Override
            public void doAffertRequest() {
                super.doAffertRequest();
                isFragmentApiCall = false;
                setRefreshWidget(false);
            }
        };

    }

    private ArrayList<ChannelEntityBean> addDefaultChannelList() {
        ArrayList<ChannelEntityBean> defaultChannelList=null;
        try {
            defaultChannelList = StringUtils.jsonToArrayObj(
                    FilterTagJson.defaultChannel,
                    ChannelEntityBean.class);
//            mOtherItems.addAll(defaultChannelList);
        } catch (Exception e) {
            e.printStackTrace();
            defaultChannelList=new ArrayList<>();
        }

        return defaultChannelList;
    }

    /**
     * @param dataBean
     * @param size 从网络中请求回来的频道数
     */
    private void doAffterGetChannelList(ArrayList<ChannelEntityBean> dataBean, int size) {
        try {
            boolean hasLocalList=false;
            ArrayList<ChannelEntityBean> localChanelList=null;
            DBJsonBean jsonBean = DBJsonHelper.getJsonBean(KeyConst.DBDataKey.KET_DB_CHANNEL_LIST);
            if (jsonBean != null && !StringUtils.isEmpty(jsonBean.getData())) {
                localChanelList = StringUtils.jsonToArrayObj(jsonBean.getData(),ChannelEntityBean.class);
                if (localChanelList!=null&&localChanelList.size()>0){
                    hasLocalList=true;
                }
            }
            for (int i = 0; i < dataBean.size(); i++) {
                ChannelEntityBean channelEntityBean = dataBean.get(i);
                //遍历下载回来的列表，如果本地有包含则加入我的频道，否则加入其它频道
                if (i<size) {
                    CarModelListRequestBean requestBean = new CarModelListRequestBean();
                    requestBean.brandId = dataBean.get(i).uid;
                    dataBean.get(i).requestBean = requestBean;
                }
                if (hasLocalList&&localChanelList.contains(channelEntityBean)){
                    if (!mItems.contains(channelEntityBean)) {
                        mItems.add(channelEntityBean);
                    }
                }else if (!mOtherItems.contains(channelEntityBean)){
                    mOtherItems.add(dataBean.get(i));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
