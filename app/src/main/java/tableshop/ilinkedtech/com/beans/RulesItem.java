/*
 *  RulesItem
 *  MySales 2.0
 *  @Wilson
 *
 */

package tableshop.ilinkedtech.com.beans;


import tableshop.ilinkedtech.com.beans.events.FilterItemTypeBean;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;
import tableshop.ilinkedtech.com.utils.StringUtils;

/**
 * Created by Wilson on 4/7/16.
 */
public class RulesItem {

    public String validationRules;
    public String displayText;
    public String description;
    public ListRequestBean requestBean;

    public FilterItemTypeBean mFilterItemTypeBean;
    public ProductItemBean.ColorBeanListBean colorBeanListBean;

    //====================TODO wilson begin===========================
    public boolean isSelected;
    public boolean isEnable=true;//下拉框数据 显示、隐藏
    //====================TODO wilson end===========================


    public RulesItem() {
    }


    @Override
    public boolean equals(Object o) {
        RulesItem o1 = (RulesItem) o;
        if (StringUtils.isEmpty(o1.validationRules)||StringUtils.isEmpty(this.validationRules)){
            return false;
        }
        return o1.validationRules.equals(this.validationRules);
    }

    @Override
    public int hashCode() {
        if (!StringUtils.isEmpty(validationRules)) {
            return validationRules.hashCode();
        }
        return super.hashCode();
    }

}
