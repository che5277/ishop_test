package tableshop.ilinkedtech.com.views.flowlayout.multitag;

/**
 * @author : Joe
 * @version : 1.0
 * @editor : Joe
 * @created : 2017/12/6
 * @updated : 2017/12/6
 * @description : <Description>
 * @update_reason : <UpdateReason>
 */

public class RulesTagItem
        extends TagItemData {

    public RulesTagItem(int position, String data) {
        super(position, data);
    }
    public RulesTagItem() {
        super(0, null);
    }

    public String validationRules;
    public String displayText;

    public String brandName;
    public String brandSeriesName;
    public String brandModelName;
    public String brandVINNo;
    public String engineNo;
    public String brandYear;
    public String bodyColorId;
    public String interiorColorId;

    int ruleType;

    //====================TODO wilson begin===========================
    public boolean isSelected;
    public boolean isEnable=true;//下拉框数据 显示、隐藏
    //====================TODO wilson end===========================
    /*
    * Default constructor
    **/

    public String getValidationRules() {
        return validationRules;
    }

    public void setValidationRules(String validationRules) {
        this.validationRules = validationRules;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandSeriesName() {
        return brandSeriesName;
    }

    public void setBrandSeriesName(String brandSeriesName) {
        this.brandSeriesName = brandSeriesName;
    }

    public String getBrandModelName() {
        return brandModelName;
    }

    public void setBrandModelName(String brandModelName) {
        this.brandModelName = brandModelName;
    }

    public String getBrandVINNo() {
        return brandVINNo;
    }

    public void setBrandVINNo(String brandVINNo) {
        this.brandVINNo = brandVINNo;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getBrandYear() {
        return brandYear;
    }

    public void setBrandYear(String brandYear) {
        this.brandYear = brandYear;
    }

    public String getBodyColorId() {
        return bodyColorId;
    }

    public void setBodyColorId(String bodyColorId) {
        this.bodyColorId = bodyColorId;
    }

    public String getInteriorColorId() {
        return interiorColorId;
    }

    public void setInteriorColorId(String interiorColorId) {
        this.interiorColorId = interiorColorId;
    }

    public int getRuleType() {
        return ruleType;
    }

    public void setRuleType(int ruleType) {
        this.ruleType = ruleType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
