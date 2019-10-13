package tableshop.ilinkedtech.com.views.flowlayout.multitag;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.views.flowlayout.FlowLayout;
import tableshop.ilinkedtech.com.views.flowlayout.TagView;

/*
 *  @项目名：  mysales
 *  @包名：    com.ilinkedtech.views
 *  @文件名:   TagFlowLayout
 *  @创建者:   Wilson
 *  @创建时间:  2017/4/23 17:49
 *  @描述：    TODO 标签布局
 */
public class MultiTagFlowLayout
        extends FlowLayout
        implements MultiTagAdapter.OnDataChangedListener {
    private MultiTagAdapter mTagAdapter;
    private              boolean mAutoSelectEffect = true;
    private              int     mSelectedMax      = -1;//-1为不限制数量
    private static final String  TAG               = "TagFlowLayout";
    private MotionEvent mMotionEvent;

    public Set<Integer> mSelectedView = new HashSet<Integer>();


    public MultiTagFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        mAutoSelectEffect = ta.getBoolean(R.styleable.TagFlowLayout_auto_select_effect, true);
        mSelectedMax = ta.getInt(R.styleable.TagFlowLayout_max_select, -1);
        ta.recycle();

        if (mAutoSelectEffect) {
            setClickable(true);
        }
    }

    public MultiTagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTagFlowLayout(Context context) {
        this(context, null);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            MultiTagView tagView = (MultiTagView) getChildAt(i);
            if (tagView.getVisibility() == View.GONE) continue;
            if (tagView.getTagView().getVisibility() == View.GONE) {
                tagView.setVisibility(View.GONE);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public interface OnSelectListener {
        void onSelected(Set<Integer> selectPosSet);
    }

    private OnSelectListener mOnSelectListener;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
        if (mOnSelectListener != null) setClickable(true);
    }

    public interface OnTagClickListener {
        boolean onTagClick(View view, int position, FlowLayout parent);
    }

    private OnTagClickListener mOnTagClickListener;

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        mOnTagClickListener = onTagClickListener;
        if (onTagClickListener != null) setClickable(true);
    }


    public void setAdapter(MultiTagAdapter adapter) {
        mTagAdapter = adapter;
        mTagAdapter.setOnDataChangedListener(this);
        mSelectedView.clear();
        changeAdapter();

    }

    private void changeAdapter() {
        removeAllViews();
        MultiTagAdapter adapter          = mTagAdapter;
        MultiTagView    tagViewContainer = null;
        HashSet         preCheckedList   = mTagAdapter.getPreCheckedList();
        for (int i = 0; i < adapter.getCount(); i++) {
            /**
             * todo :
             */
//            View tagView = adapter.getView(this, i, adapter.getItem(i));
            /**
             * adapter存放一个flowlayout的数据
             */
            TagItemData item = adapter.getItem(i); // 每个tag的数据,内部dataList为生序和降序状态
            int position = item.getPosition();
            // get selected todo : NullPointerExceptionmjava.util.List.get(int)
            TagItemData tagItemData = item.getDataList().get(position); // 当前生序或降序状态

            View tagView = adapter.getView(this, i, tagItemData);

//            tagViewContainer = new MultiTagView(getContext(), adapter.getmTagDatas());
            tagViewContainer = new MultiTagView(getContext(), item.getDataList());
            // set selected
            tagViewContainer.setSelected(tagItemData);

            tagView.setDuplicateParentStateEnabled(true);
            if (tagView.getLayoutParams() != null) {
                tagViewContainer.setLayoutParams(tagView.getLayoutParams());
            } else {
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(dip2px(getContext(), 5),
                        dip2px(getContext(), 5),
                        dip2px(getContext(), 5),
                        dip2px(getContext(), 5));
                tagViewContainer.setLayoutParams(lp);
            }
            tagViewContainer.addView(tagView);
            addView(tagViewContainer);


            if (preCheckedList.contains(i)) {
                tagViewContainer.setChecked(true);
            }

            if (mTagAdapter.setSelected(i, adapter.getItem(i))) {
                mSelectedView.add(i);
                tagViewContainer.setChecked(true);
            }
        }
        mSelectedView.addAll(preCheckedList);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mMotionEvent = MotionEvent.obtain(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        if (mMotionEvent == null) return super.performClick();

        int x = (int) mMotionEvent.getX();
        int y = (int) mMotionEvent.getY();
        mMotionEvent = null;

        MultiTagView child = findChild(x, y);
        int pos = findPosByView(child);
        if (child != null) {
            doSelect(child, pos);

            if (mOnTagClickListener != null) {
                return mOnTagClickListener.onTagClick(child.getTagView(), pos, this);
            }
        }
        return true;
    }


    public void setMaxSelectCount(int count) {
        if (mSelectedView.size() > count) {
            LogUtils.w(TAG, "you has already select more than " + count + " views , so it will be clear .");
            mSelectedView.clear();
        }
        mSelectedMax = count;
    }

    public Set<Integer> getSelectedList() {
        return new HashSet<Integer>(mSelectedView);
    }

    /**
     * todo : updated by Joe
     *
     * @param child
     * @param position
     */
    private void doSelect(MultiTagView child, int position) {
        if (mAutoSelectEffect) {
            if (!child.isChecked()) {

//                FATAL EXCEPTION: main
//                Process: com.ilinkedtech.test, PID: 15028
//                java.util.NoSuchElementException
//                at java.util.HashMap$HashIterator.nextEntry(HashMap.java:789)
//                at java.util.HashMap$KeyIterator.next(HashMap.java:814)
//                at com.ilinkedtech.views.MultiTagFlowLayout.doSelect(MultiTagFlowLayout.java:222)
//                at com.ilinkedtech.views.MultiTagFlowLayout.performClick(MultiTagFlowLayout.java:184)
//                at android.view.View$PerformClick.run(View.java:19884)
//                at android.os.Handler.handleCallback(Handler.java:739)
//                at android.os.Handler.dispatchMessage(Handler.java:95)
//                at android.os.Looper.loop(Looper.java:135)
//                at android.app.ActivityThread.main(ActivityThread.java:5343)
//                at java.lang.reflect.Method.invoke(Native Method)
//                at java.lang.reflect.Method.invoke(Method.java:372)
//                at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:905)
//                at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:700)

                Iterator<Integer> iterator = mSelectedView.iterator();
                Integer           preIndex = null;
                MultiTagView      pre      = null;
                if (iterator.hasNext()) {
                    preIndex = iterator.next();
                    pre = (MultiTagView) getChildAt(preIndex);
                }
                //处理max_select=1的情况
                if (mSelectedMax == 1 && mSelectedView.size() == 1) {

                    /**
                     * todo : 切换到下一个状态，返回是否切换选中的状态
                     */
                    boolean checked = child.changeNextTagState(child.isChecked());
                    /**
                     * todo :
                     */
//                    int position1 = child.getSelected().getPosition();
//                    TagItemData item = mTagAdapter.getItem(position);
//                    item.setPosition(position1);

                    if (pre != null) {
                        pre.setChecked(false);
                    }
                    child.setChecked(true);
                    if (checked) {
                        if (preIndex != null) {
                            mSelectedView.remove(preIndex);
                        }
                        mSelectedView.add(position);
                    } else {
                        if (preIndex != null) {
                            mSelectedView.add(preIndex);
                        }
                        mSelectedView.remove(position);
                    }

//                    pre.setChecked(false);
//                    child.setChecked(true);
//                    mSelectedView.remove(preIndex);
//                    mSelectedView.add(position);
                } else {
                    if (mSelectedMax > 0 && mSelectedView.size() >= mSelectedMax)
                        return;

                    boolean checked = child.changeNextTagState(child.isChecked());

                    /**
                     * todo :
                     */
//                    int position1 = child.getSelected().getPosition();
//                    TagItemData item = mTagAdapter.getItem(position);
//                    item.setPosition(position1);

                    child.setChecked(true);
//                    if (checked) {
//                        mSelectedView.add(position);
//                    } else {
//                        mSelectedView.remove(position);
//                    }

                    child.setChecked(true);
                    mSelectedView.add(position);
                }

            } else {

                Iterator<Integer> iterator = mSelectedView.iterator();
                Integer           preIndex = iterator.next();
                MultiTagView      pre      = (MultiTagView) getChildAt(preIndex);

//                child.setChecked(false);
//                mSelectedView.remove(position);

                if (mSelectedMax == 1 && mSelectedView.size() == 1) {
                    boolean checked = child.changeNextTagState(child.isChecked());
                    if (checked) {
                        if (pre == child) {
                            child.setChecked(true);
                        } else {
                            pre.setChecked(false);
                            child.setChecked(true);
                            mSelectedView.remove(preIndex);
                            mSelectedView.add(position);
                        }
                    } else {
                        if (pre == child) {
                            child.setChecked(false);
                            mSelectedView.remove(preIndex);
                        } else {
                            pre.setChecked(true);
                            child.setChecked(false);
                            mSelectedView.remove(position);
                            mSelectedView.add(preIndex);
                        }
                    }
                } else {
                    boolean checked = child.changeNextTagState(child.isChecked());
                    if (checked) {
                        child.setChecked(true);
                        mSelectedView.add(position);
                    } else {
                        child.setChecked(false);
                        mSelectedView.remove(position);
                    }
                }
            }

            /**
             * todo : bugfix
             */
            int position1 = child.getSelected().getPosition();
            TagItemData item = mTagAdapter.getItem(position);
            item.setPosition(position1);

            if (mOnSelectListener != null) {
                mOnSelectListener.onSelected(new HashSet<Integer>(mSelectedView));
            }
        }

        child.updateText();

//        LogUtils.d(TAG, "mSelectedView : " + GsonUtils.fromJsonObjectToJsonString(mSelectedView, true));
        List list = mTagAdapter.getmTagDatas();
//        LogUtils.d(TAG, "adapter list : " + GsonUtils.fromJsonObjectToJsonString(list, true));
        TagItemData selected = child.getSelected();
//        LogUtils.d(TAG, "selected : " + GsonUtils.fromJsonObjectToJsonString(selected, true));


//        mTagAdapter.notifyDataChanged();
    }



    public MultiTagAdapter getAdapter() {
        return mTagAdapter;
    }


    private static final String KEY_CHOOSE_POS = "key_choose_pos";
    private static final String KEY_DEFAULT    = "key_default";


    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DEFAULT, super.onSaveInstanceState());

        String selectPos = "";
        if (mSelectedView.size() > 0) {
            for (int key : mSelectedView) {
                selectPos += key + "|";
            }
            selectPos = selectPos.substring(0, selectPos.length() - 1);
        }
        bundle.putString(KEY_CHOOSE_POS, selectPos);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle     = (Bundle) state;
            String mSelectPos = bundle.getString(KEY_CHOOSE_POS);
            if (!TextUtils.isEmpty(mSelectPos)) {
                String[] split = mSelectPos.split("\\|");
                for (String pos : split) {
                    int index = Integer.parseInt(pos);
                    mSelectedView.add(index);

                    TagView tagView = (TagView) getChildAt(index);
                    if (tagView != null)
                        tagView.setChecked(true);
                }

            }
            super.onRestoreInstanceState(bundle.getParcelable(KEY_DEFAULT));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    private int findPosByView(View child) {
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View v = getChildAt(i);
            if (v == child) return i;
        }
        return -1;
    }

    private MultiTagView findChild(int x, int y) {
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            MultiTagView v = (MultiTagView) getChildAt(i);
            if (v.getVisibility() == View.GONE) continue;
            Rect outRect = new Rect();
            v.getHitRect(outRect);
            if (outRect.contains(x, y)) {
                return v;
            }
        }
        return null;
    }

    @Override
    public void onChanged() {
        mSelectedView.clear();
        changeAdapter();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
