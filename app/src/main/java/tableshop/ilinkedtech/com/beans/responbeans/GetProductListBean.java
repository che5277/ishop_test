package tableshop.ilinkedtech.com.beans.responbeans;

/*
 *  @文件名:   GetProductListBean
 *  @创建者:   Wilson
 *  @创建时间:  2018/2/5 10:20
 *  @描述：    TODO
 */

import java.util.List;

import tableshop.ilinkedtech.com.beans.main.ProductItemBean;

public class GetProductListBean {
    public String                message;
    public String                status;
    public int                   totalElements;
    public int                   totalPages;
    public int                   count;
    public List<ProductItemBean> datas;

}
