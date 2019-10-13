package tableshop.ilinkedtech.com.views.flowlayout.multitag;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  mysales
 *  @包名：    com.ilinkedtech.views
 *  @文件名:   TagView
 *  @创建者:   Wilson
 *  @创建时间:  2017/4/23 17:49
 *  @描述：    TODO 标签布局
 */
public class MultiTagView<T extends TagItemData> extends FrameLayout
        implements Checkable
{
    private boolean isChecked;
    private static final int[] CHECK_STATE = new int[]{android.R.attr.state_checked};

    /**
     * todo : 新增用于表示多个状态的切换
     */
    private T selected;
    private List<T> dataList = new ArrayList<>(); // 生序或降序状态

    public MultiTagView(Context context, List<T> dataList)
    {
        super(context);
        this.dataList = dataList;
        init(context, dataList);
    }

    private void init(Context context, List<T> dataList) {
        if (dataList == null || dataList.size() <= 0) {
            throw new IllegalStateException("tag list data need : " + dataList);
        }
        selected = dataList.get(0);
    }

    public View getTagView()
    {
        return getChildAt(0);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace)
    {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked())
        {
            mergeDrawableStates(states, CHECK_STATE);
        }
        return states;
    }

    public boolean changeNextTagState(boolean isChecked) {
        if (isChecked) {

            int currentStatePosition = selected.getPosition();
            TagItemData tagItemData = dataList.get(currentStatePosition);

            if (dataList.size() == 1) {
                return false;
            } else {
                int position = selected.getPosition();
                if (position < dataList.size() - 1) {
                    selected = dataList.get(position + 1);
                    return true;
                } else {
                    selected = dataList.get(0);
                    return false;
                }
            }
        } else {
            if (dataList.size() == 1) {
                return true;
            } else {
                int position = selected.getPosition();
                return true;
            }
        }

    }

    /**
     * Change the checked state of the view
     *
     * @param checked The new checked state
     */
    @Override
    public void setChecked(boolean checked)
    {
        if (this.isChecked != checked)
        {
            this.isChecked = checked;
            refreshDrawableState();
        }
    }

    public void updateText()
    {
        TextView textView = (TextView) getTagView();
        textView.setText(dataList.get(selected.getPosition()).getData());
    }

    /**
     * @return The current checked state of the view
     */
    @Override
    public boolean isChecked()
    {
        return isChecked;
    }

    /**
     * Change the checked state of the view to the inverse of its current state
     */
    @Override
    public void toggle()
    {
        setChecked(!isChecked);
    }

    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        this.selected = selected;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }


}
