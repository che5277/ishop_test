package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @文件名:   ActAgreementDetailFragment
 *  @创建者:   Wilson
 *  @创建时间:  2018/2/23 15:21
 *  @描述：    TODO 协议详情页面
 */

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.Unbinder;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.utils.RxTextUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;

public class ActAgreementDetailFragment
        extends IShopBaseFragment
{
    @BindView(R.id.tv_content)
    TextView mTvContent;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.recycle_view;
    }

    @Override
    protected void initView() {
        RxTextUtils.getBuilder("")
                   .append("1.无法验货："+"\n").setBold()
                   .append("如您购买电器类、品牌\\仿牌类、门票、卡类、模型等商品，我们将无法为您定购的商品做专业性（质量、真伪、完整性等）检查；质检员验收时，只能检查商品外观是否完好无损，配件是否齐全，而无法开机检查商品是否有质量问题。"+"\n")
                   .append("2.定制商品："+"\n").setBold()
                   .append("如您提交的订单为定制商品，建议您与商家进行协商。按照您的想法以及创意订购该商品。与商家协商成功后需要您提供与商家联系的旺旺、联系方式或暗号即可。此类商品我们无法为您做专业性的检查，由此产生的损失需您自行承担。"+"\n")
                   .append("\n"+"\n"+"\n")
                   .append("如您提交的订单存在以上风险，我们将视为您已阅读并接受以上条款，请您务必仔细阅读，领骏达保留一切解释权！")
                   .into(mTvContent);
    }

    @Override
    protected void initEvent() {
        mTvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                UIUtils.activityBackToMain(getActivity());
            }
        });
    }

    @Override
    public void refreshDatas() {

    }

}
