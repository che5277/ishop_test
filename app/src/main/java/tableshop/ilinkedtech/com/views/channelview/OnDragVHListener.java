package tableshop.ilinkedtech.com.views.channelview;

/**
 *  @项目名：  iShop
 *  @包名：    ishop.ilinkedtech.com.views.channelview
 *  @文件名:   OnDragVHListener
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/6 17:45
 * ViewHolder 被选中 以及 拖拽释放 触发监听器
 */
public interface OnDragVHListener {
    /**
     * Item被选中时触发
     */
    void onItemSelected();


    /**
     * Item在拖拽结束/滑动结束后触发
     */
    void onItemFinish();
}
