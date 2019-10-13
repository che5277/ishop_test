package tableshop.ilinkedtech.com.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;

import butterknife.ButterKnife;

/**
 *
 *  @项目名：  ICan2--kuangjia
 *  @包名：    com.ilinkedtech.base
 *  @文件名:   BaseBottomActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/5/31 12:21
 * 	@描述 这是所有controller的父类，定义了一套规则 initView　和 initData ，
 *     并且还提供了getRootView ，用于获取视图
 */
@SuppressLint("ValidFragment")
public abstract class BaseController {
	public Activity mActivity;
	View mView ;
	public BaseController(Activity mActivity){
		this.mActivity = mActivity;
		mView = initView();
		ButterKnife.bind(this,mView);

		initEvent();
	}

	public void initEvent() {

	}

	/**
	 * 初始化view , 这个方法子类必须实现，用于提供自己的视图
	 * @return
	 */
	public abstract View initView();
	
	
	/**
	 * 子类实现，用于实现自己的业务逻辑
	 */
	public abstract void initData();
	
	/**
	 * 用于获取对应的每一个子类的视图
	 * @return
	 */
	public View getRootView(){
		
		return mView;
	}



}
