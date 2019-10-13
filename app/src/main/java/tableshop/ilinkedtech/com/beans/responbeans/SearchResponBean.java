package tableshop.ilinkedtech.com.beans.responbeans;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.beans
 *  @文件名:   SearchResponBean
 *  @创建者:   Administrator
 *  @创建时间:  2017/7/12 18:50
 *  @描述：    TODO
 */

import java.util.List;

import tableshop.ilinkedtech.com.beans.main.CarDetailBean;

public class SearchResponBean {

    /**
     * uid : null
     * seriesDefaultImageURL : null
     * seriesSmallImageURL : null
     * seriesBackgroundURL : null
     * seriesShowVideoURL : null
     * seriesQRCodeText : null
     * seriesQRCodeImageURL : null
     * seriesShareURL : null
     * seriesPdfURL : []
     * brandId : null
     * brandName : null
     * seriesId : null
     * seriesName : null
     * modelId : null
     * modelName : null
     * lowestPrice : null
     * count : 0
     * vehicleStockSingleViewList : [{"uid":"05db55e7-132a-4013-8dc7-6ebc7855106c","price":900000,"bodyColorId":null,"bodyColorUrl":null,"bodyColorName":"Black","interiorColorId":null,"interiorColorUrl":null,"interiorColorName":"Black","feature":{"yaokongboche":{"value":true,"type":"Boolean"},"后视摄像机":{"value":true,"type":"Boolean"},"换挡拨片":{"value":true,"type":"Boolean"}},"vinNo":"1","seriesDefaultImageURL":"http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/series_details/188o_20170904165405_rlc1.png","seriesSmallImageURL":null,"seriesBackgroundURL":"http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/series_details/84lx_20170904165425_7sxm.png","seriesShowVideoURL":"http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/series_details/qfaz_20170905094429_rm6u.mp4","seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":["dcr/series_details/jie1_20170915164431_gokt.pdf"],"brandId":null,"brandName":"B-宝马","seriesId":null,"seriesName":"7系","modelId":"58c21add-a088-4849-8425-84b0475cf697","modelName":"730Li","noOfSeat":0,"bodyLength":5250,"bodyWidth":1902,"bodyHeight":1498,"noOfDoors":4,"fuseSource":"汽油","horsePower":"258","engineType":"2.0T 258 L4","engineLayout":"前置后驱","insideSmallImgUrl":["http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/l5hv_20170904193105_0o04.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/9v3c_20170904193036_9ola.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/ai77_20170904193040_7757.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/wibg_20170904193119_iy3m.jpg"],"outsideSmallImgUrl":["http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/xsmj_20170904193146_kzni.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/fh2p_20170913121520_wexv.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/5mrw_20170904193152_sym6.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/mv6v_20170904193157_94h7.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/58n5_20170904193307_bfgk.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/fcdl_20170904193326_m8py.jpg"],"insideImgUrl":["http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/8e2v_20170904193110_ho7y.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/3la5_20170904193046_d0g0.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/ibmt_20170904193101_o5d7.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/cyyy_20170904193124_xwcd.jpg"],"outsideImgUrl":["http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/4cc8_20170904193221_zn01.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/xab2_20170904193255_tgqf.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/2li3_20170904193226_687p.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/45it_20170904193247_6ip7.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/p5qq_20170904193312_wtsb.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/hh7w_20170904193336_vbz1.jpg"],"angleUrl":["dcr/360/53pm_20170921164258_mve9.png","dcr/360/k3hr_20170921164304_a247.png","dcr/360/japz_20170921164315_mk5w.png","dcr/360/hd35_20170921164335_8jmw.png","dcr/360/fing_20170921164401_sw5c.png","dcr/360/wefy_20170921164413_aldh.png"],"gearBox":"8","category":null,"vehicleSalesUid":null,"qrCode":null},{"uid":"107a374e-4a54-4560-84b4-f4e7662b74d2","price":900000,"bodyColorId":null,"bodyColorUrl":null,"bodyColorName":"黑色","interiorColorId":null,"interiorColorUrl":null,"interiorColorName":"黑色","feature":{"yaokongboche":{"value":true,"type":"Boolean"},"后视摄像机":{"value":true,"type":"Boolean"},"换挡拨片":{"value":true,"type":"Boolean"}},"vinNo":"1","seriesDefaultImageURL":"http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/series_details/188o_20170904165405_rlc1.png","seriesSmallImageURL":null,"seriesBackgroundURL":"http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/series_details/84lx_20170904165425_7sxm.png","seriesShowVideoURL":"http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/series_details/qfaz_20170905094429_rm6u.mp4","seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":["dcr/series_details/jie1_20170915164431_gokt.pdf"],"brandId":null,"brandName":"B-宝马","seriesId":null,"seriesName":"7系","modelId":"58c21add-a088-4849-8425-84b0475cf697","modelName":"730Li","noOfSeat":0,"bodyLength":5250,"bodyWidth":1902,"bodyHeight":1498,"noOfDoors":4,"fuseSource":"汽油","horsePower":"258","engineType":"2.0T 258 L4","engineLayout":"前置后驱","insideSmallImgUrl":["http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/l5hv_20170904193105_0o04.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/9v3c_20170904193036_9ola.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/ai77_20170904193040_7757.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/wibg_20170904193119_iy3m.jpg"],"outsideSmallImgUrl":["http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/xsmj_20170904193146_kzni.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/fh2p_20170913121520_wexv.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/5mrw_20170904193152_sym6.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/mv6v_20170904193157_94h7.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/58n5_20170904193307_bfgk.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/fcdl_20170904193326_m8py.jpg"],"insideImgUrl":["http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/8e2v_20170904193110_ho7y.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/3la5_20170904193046_d0g0.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/ibmt_20170904193101_o5d7.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/cyyy_20170904193124_xwcd.jpg"],"outsideImgUrl":["http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/4cc8_20170904193221_zn01.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/xab2_20170904193255_tgqf.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/2li3_20170904193226_687p.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/45it_20170904193247_6ip7.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/p5qq_20170904193312_wtsb.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/hh7w_20170904193336_vbz1.jpg"],"angleUrl":["dcr/360/53pm_20170921164258_mve9.png","dcr/360/k3hr_20170921164304_a247.png","dcr/360/japz_20170921164315_mk5w.png","dcr/360/hd35_20170921164335_8jmw.png","dcr/360/fing_20170921164401_sw5c.png","dcr/360/wefy_20170921164413_aldh.png"],"gearBox":"8","category":null,"vehicleSalesUid":null,"qrCode":null},{"uid":"4df309a7-12b7-4aaf-a3f8-803a2a4e9843","price":814000,"bodyColorId":null,"bodyColorUrl":null,"bodyColorName":"黑色","interiorColorId":null,"interiorColorUrl":null,"interiorColorName":"黑色","feature":{"后扰流尾翼（带LED 高位刹车灯）_en":{"value":true,"type":"Boolean"},"zhinengjiguangdadeng":{"value":true,"type":"Boolean"}},"vinNo":"51","seriesDefaultImageURL":"http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/series_details/2ed8_20170916125518_w1eg.png","seriesSmallImageURL":null,"seriesBackgroundURL":"http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/series_details/y9qx_20170914172432_t0me.png","seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":["dcr/series_details/ed4r_20170917153818_zer0.pdf"],"brandId":null,"brandName":"F-丰田","seriesId":null,"seriesName":"埃尔法","modelId":"45484c59-e554-4c87-804a-52ce4872342a","modelName":"3.5L","noOfSeat":0,"bodyLength":4925,"bodyWidth":1850,"bodyHeight":1890,"noOfDoors":5,"fuseSource":"汽油","horsePower":"275","engineType":"3.5L 275 V6","engineLayout":"前轮驱动","insideSmallImgUrl":["http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/6aw7_20170916162830_4vz4.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/fidv_20170916162849_mey0.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/hvsc_20170916162842_stbm.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/yxdh_20170916162823_dacf.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/6ge7_20170916162835_e9k4.jpg"],"outsideSmallImgUrl":["http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/wffx_20170916134117_s2u7.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/d779_20170916134008_1i8q.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/dl7k_20170916134027_tvj1.JPG","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/pbdn_20170916134050_ayyw.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/9r65_20170916134148_bm6h.JPG"],"insideImgUrl":["http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/1sxo_20170916162904_ybm8.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/sjsc_20170916162924_hgig.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/gb4o_20170916162918_axw5.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/669r_20170916162857_3xxu.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/dko3_20170916162911_okhc.jpg"],"outsideImgUrl":["http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/z84c_20170916134235_58fy.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/3kjt_20170916134158_phd4.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/2twx_20170916134215_8cub.JPG","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/pzwe_20170916134226_7j5e.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/2zyn_20170916134244_qha7.JPG"],"angleUrl":["dcr/360/oxie_20170916133655_sxl5.png"],"gearBox":"6档手自一体","category":null,"vehicleSalesUid":null,"qrCode":null},{"uid":"cc7861bd-93a3-47e3-b564-c23fa90adea2","price":814000,"bodyColorId":null,"bodyColorUrl":null,"bodyColorName":"Black","interiorColorId":null,"interiorColorUrl":null,"interiorColorName":"Black","feature":{"后扰流尾翼（带LED 高位刹车灯）_en":{"value":true,"type":"Boolean"},"zhinengjiguangdadeng":{"value":true,"type":"Boolean"}},"vinNo":"51","seriesDefaultImageURL":"http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/series_details/2ed8_20170916125518_w1eg.png","seriesSmallImageURL":null,"seriesBackgroundURL":"http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/series_details/y9qx_20170914172432_t0me.png","seriesShowVideoURL":null,"seriesQRCodeText":null,"seriesQRCodeImageURL":null,"seriesShareURL":null,"seriesPdfURL":["dcr/series_details/ed4r_20170917153818_zer0.pdf"],"brandId":null,"brandName":"F-丰田","seriesId":null,"seriesName":"埃尔法","modelId":"45484c59-e554-4c87-804a-52ce4872342a","modelName":"3.5L","noOfSeat":0,"bodyLength":4925,"bodyWidth":1850,"bodyHeight":1890,"noOfDoors":5,"fuseSource":"汽油","horsePower":"275","engineType":"3.5L 275 V6","engineLayout":"前轮驱动","insideSmallImgUrl":["http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/6aw7_20170916162830_4vz4.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/fidv_20170916162849_mey0.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/hvsc_20170916162842_stbm.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/yxdh_20170916162823_dacf.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/6ge7_20170916162835_e9k4.jpg"],"outsideSmallImgUrl":["http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/wffx_20170916134117_s2u7.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/d779_20170916134008_1i8q.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/dl7k_20170916134027_tvj1.JPG","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/pbdn_20170916134050_ayyw.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/9r65_20170916134148_bm6h.JPG"],"insideImgUrl":["http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/1sxo_20170916162904_ybm8.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/sjsc_20170916162924_hgig.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/gb4o_20170916162918_axw5.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/669r_20170916162857_3xxu.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/dko3_20170916162911_okhc.jpg"],"outsideImgUrl":["http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/z84c_20170916134235_58fy.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/3kjt_20170916134158_phd4.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/2twx_20170916134215_8cub.JPG","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/pzwe_20170916134226_7j5e.jpg","http://icansell.oss-cn-hangzhou.aliyuncs.com/dcr/gallery/2zyn_20170916134244_qha7.JPG"],"angleUrl":["dcr/360/oxie_20170916133655_sxl5.png"],"gearBox":"6档手自一体","category":null,"vehicleSalesUid":null,"qrCode":null}]
     * purchaseVehicleViewList : null
     * deliveryVehicleViewList : null
     * insideSmallImgUrl : null
     * outsideSmallImgUrl : null
     * insideImgUrl : null
     * outsideImgUrl : null
     * message : null
     * status : null
     */

    public Object              uid;
    public Object                               seriesDefaultImageURL;
    public Object                               seriesSmallImageURL;
    public Object                               seriesBackgroundURL;
    public Object                               seriesShowVideoURL;
    public Object                               seriesQRCodeText;
    public Object                               seriesQRCodeImageURL;
    public Object                               seriesShareURL;
    public Object                               brandId;
    public Object                               brandName;
    public Object                               seriesId;
    public Object                               seriesName;
    public Object                               modelId;
    public Object                               modelName;
    public Object                               lowestPrice;
    public int                 count;
    public Object              purchaseVehicleViewList;
    public Object              deliveryVehicleViewList;
    public Object              insideSmallImgUrl;
    public Object              outsideSmallImgUrl;
    public Object              insideImgUrl;
    public Object              outsideImgUrl;
    public Object              message;
    public Object              status;
    public List<?>             seriesPdfURL;
    public List<CarDetailBean> vehicleStockSingleViewList;

}
