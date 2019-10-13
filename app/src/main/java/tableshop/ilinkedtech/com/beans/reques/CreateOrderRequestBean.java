package tableshop.ilinkedtech.com.beans.reques;

/*
 *  @文件名:   CreateOrderRequestBean
 *  @创建者:   Wilson
 *  @创建时间:  2018/2/6 10:57
 *  @描述：    TODO
 */

import java.util.List;

public class CreateOrderRequestBean {

    /**
     * datas : [{"num":"3","productUid":"188999","type":"黑"},{"num":"1","productUid":"177999","type":"蓝色"},{"num":"2","productUid":"177999","type":"黑"}]
     * fromShoppingCart : 1
     * shoppingCartUidList : ["48ddaf41-f864-47bd-bec9-94d340d90e97","180cbf6f-e8e4-4ddf-850a-f99a6a68a3a6","7af5a48a-8545-4fbf-ae9a-16943e27d9b3"]
     */
    public String fromShoppingCart;
    public List<AddToShoppingCarRequestBean> datas;
    public List<String>    shoppingCartUidList;

}
