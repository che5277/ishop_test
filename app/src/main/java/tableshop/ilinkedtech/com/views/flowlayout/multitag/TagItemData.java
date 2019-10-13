package tableshop.ilinkedtech.com.views.flowlayout.multitag;


import java.util.List;

/**
 * @author : Joe
 * @version : 1.0
 * @editor : Joe
 * @created : 2017/12/6
 * @updated : 2017/12/6
 * @description : <Description>
 * @update_reason : <UpdateReason>
 */

public class TagItemData {

    private List<RulesTagItem> dataList;

    public TagItemData(int position, String data) {
        this.position = position;
        this.data = data;
    }

    private int    position;
    private String data;

    public List<RulesTagItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<RulesTagItem> dataList) {
        this.dataList = dataList;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TagItemData{" +
                "dataList=" + dataList +
                ", position=" + position +
                ", data='" + data + '\'' +
                '}';
    }
}
