package tableshop.ilinkedtech.com.beans.events;

/*
 *  @文件名:   BaseUrlChangeBean
 *  @创建者:   Wilson
 *  @创建时间:  2018/2/1 14:33
 *  @描述：    TODO
 */

import tableshop.ilinkedtech.com.beans.main.CarDetailBean;

public class SeletedBrand {

    public CarDetailBean carDetailBean;

    public SeletedBrand(CarDetailBean carDetailBean) {
        this.carDetailBean=carDetailBean;
    }

    public static SeletedBrand newInstance(CarDetailBean carDetailBean) {
        return new SeletedBrand(carDetailBean);
    }
}
