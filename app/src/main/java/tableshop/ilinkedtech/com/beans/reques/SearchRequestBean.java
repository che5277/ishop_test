package tableshop.ilinkedtech.com.beans.reques;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.beans
 *  @文件名:   SearchRequestBean
 *  @创建者:   Administrator
 *  @创建时间:  2017/7/12 18:50
 *  @描述：    TODO
 */

public class SearchRequestBean {
    public String searchText;
    public String sortOrder;
    public String sortType;
    public String lowestPrice;
    public String highestPrice;
    public String mostNoOfSeat;
    public String leastNoOfSeat;
    public String outTradeNumber;//获取二维码图片接口，成交单号

    @Override
    public String toString() {
        return "SearchRequestBean{" + "searchText='" + searchText + '\'' + ", sortOrder='" + sortOrder + '\'' + ", sortType='" + sortType + '\'' + '}';
    }
}
