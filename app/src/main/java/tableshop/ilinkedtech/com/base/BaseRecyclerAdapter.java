package tableshop.ilinkedtech.com.base;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.adapters
 *  @文件名:   BaseRecyclerAdapter
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 15:49
 *  @描述：    TODO
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public abstract class BaseRecyclerAdapter<ITEM_DATA>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<ITEM_DATA> mArrayList ;

    public BaseRecyclerAdapter(ArrayList<ITEM_DATA> mArrayList){
        this.mArrayList=mArrayList;
    }

    @Override
    public int getItemCount() {
        return mArrayList==null?0:mArrayList.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v,position);
                }
            });
        }
    }

    OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
