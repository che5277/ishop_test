package tableshop.ilinkedtech.com.consts;


/*
 *  @项目名：  iShop
 *  @包名：    ishop.ilinkedtech.com.consts
 *  @文件名:   FilterTagJson
 *  @创建者:   Wilson
 *  @创建时间:  2017/5/25 9:55
 *  @描述：    TODO    过滤 搜索的json
 */
public class FilterTagJson {






//    {"description":"30-50万","requestBean":{"highestPrice":"500000","lowestPrice":"300000"}},

    public static final String defaultChannel = "[{\"description\":\"50-80万\",\"requestBean\":{\"highestPrice\":\"800000\",\"lowestPrice\":\"500000\"}},{\"description\":\"80-150万\",\"requestBean\":{\"highestPrice\":\"1500000\",\"lowestPrice\":\"800000\"}},{\"description\":\"150万以上\",\"requestBean\":{\"lowestPrice\":\"1500000\"}},{\"description\":\"2座\",\"requestBean\":{\"noOfSeat\":\"2\"}},{\"description\":\"4座\",\"requestBean\":{\"noOfSeat\":\"4\"}},{\"description\":\"5座\",\"requestBean\":{\"noOfSeat\":\"5\"}},{\"description\":\"7座以上\",\"requestBean\":{\"noOfSeat\":\"7\"}}]";


    //TODO 车型刷选
    public static final String categoryRequestBeansJson = "[{\"displayText\":\"轿车\",\"requestBean\":{\"category\":\"轿车\"}}," +
            "{\"displayText\":\"SUV\",\"requestBean\":{\"category\":\"SUV\"}}," +
            "{\"displayText\":\"MPV\",\"requestBean\":{\"category\":\"MPV\"}}," +
            "{\"displayText\":\"跑车\",\"requestBean\":{\"category\":\"跑车\"}}]";

    //TODO 车价刷选
    public static final String priceRequestBeansJson = "[{\"displayText\":\"30万以下\",\"requestBean\":{\"lowestPrice\":\"0\",\"highestPrice\":\"30\"}}," +
            "{\"displayText\":\"30-50万\",\"requestBean\":{\"highestPrice\":\"50\",\"lowestPrice\":\"30\"}}," +
            "{\"displayText\":\"50-80万\",\"requestBean\":{\"highestPrice\":\"80\",\"lowestPrice\":\"50\"}}," +
            "{\"displayText\":\"80-100万\",\"requestBean\":{\"highestPrice\":\"100\",\"lowestPrice\":\"80\"}}," +
            "{\"displayText\":\"100-150万\",\"requestBean\":{\"highestPrice\":\"150\",\"lowestPrice\":\"100\"}}," +
            "{\"displayText\":\"150万以上\",\"requestBean\":{\"lowestPrice\":\"150\",\"highestPrice\":\"10000\"}}]";


//     <item>4座以下</item>
//        <item>4-5座</item>
//        <item>5-7座</item>
//        <item>7座以上</item>
//TODO 座位数刷选
    public static final String seatsRequestBeansJson ="[{\"displayText\":\"2座\",\"requestBean\":{\"leastNoOfSeat\":\"1\",\"mostNoOfSeat\":\"2\"}}," +
        "{\"displayText\":\"4-5座\",\"requestBean\":{\"leastNoOfSeat\":\"4\",\"mostNoOfSeat\":\"5\"}}," +
        "{\"displayText\":\"6-7座\",\"requestBean\":{\"leastNoOfSeat\":\"6\",\"mostNoOfSeat\":\"7\"}}," +
            "{\"displayText\":\"7座以上\",\"requestBean\":{\"leastNoOfSeat\":\"7\",\"mostNoOfSeat\":\"999\"}}]";


}
