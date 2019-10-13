package tableshop.ilinkedtech.com.beans.responbeans;

/*
 *  @文件名:   LoginUserResponBean
 *  @创建者:   Wilson
 *  @创建时间:  2017/11/22 15:03
 *  @描述：    TODO
 */

import java.util.List;

import tableshop.ilinkedtech.com.beans.reques.MemberDetailsBean;
import tableshop.ilinkedtech.com.utils.StringUtils;

public class LoginMenberResponBean {


    /**
     * message : 查询成功
     * status : 1
     * totalElements : 0
     * totalPages : 0
     * datas : [{"username":"15919987852","memberUserBean":{"username":null,"password":null,"createBy":null,"createUid":null,"token":null,"tokenExpiredDate":null,"disabledUser":null,"confirmationId":null,"recommendedByMobileNumber":null,"platform":null,"memberUser":null,"enabled":null,"orgcode":null,"suffix":null,"alias":null,"email":null,"nickname":null,"title":null,"company":null,"department":null,"division":null,"role":null,"userLicense":null,"profile":null,"mobileConfiguration":null,"callCenter":null,"phone":null,"extension":null,"fax":null,"mobile":null,"smsAccessCode":null,"smsAccessCodeExpireDate":null,"emailEncoding":null,"employeeNumber":null,"street":null,"city":null,"state":null,"postalcode":null,"country":null,"timezone":null,"locale":null,"language":null,"newsletter":null,"lastLoginTime":null,"updateAt":null,"updateBy":null,"updateUid":null,"createAt":null,"displayCustomerNameOrder":null,"displaySortOrder":null,"wechatQrcode":null,"firstName":null,"lastName":null,"mobileNumber":null,"birthdayDate":null},"enabled":null,"orgcode":null,"suffix":null,"alias":null,"email":null,"nickname":null,"title":null,"company":null,"department":null,"division":null,"role":null,"userLicense":null,"profile":null,"mobileConfiguration":null,"callCenter":null,"phone":null,"extension":null,"fax":null,"mobile":null,"smsAccessCode":null,"smsAccessCodeExpireDate":null,"emailEncoding":null,"employeeNumber":null,"street":null,"city":null,"state":null,"postalcode":null,"country":null,"timezone":null,"locale":null,"language":null,"newsletter":null,"lastLoginTime":null,"updateAt":null,"updateBy":null,"updateUid":null,"createAt":null,"displayCustomerNameOrder":null,"displaySortOrder":null,"wechatQrcode":null,"firstName":"Bbbbbbb","lastName":"Aaaaaaa","mobileNumber":null,"birthdayDate":null}]
     */

    public String                  message;
    public String                  status;
    public int                     totalElements;
    public int                     totalPages;
    public List<MemberDetailsBean> datas;
    public List<OssResponsBean> ossResponseList;


    public String getUserName(){
        if (datas!=null&&datas.size()>0){
            MemberDetailsBean memberDetailsBean = datas.get(0);
            if (memberDetailsBean!=null){
                return StringUtils.getCustomerName(memberDetailsBean.firstName,memberDetailsBean.lastName);
            }
        }
        return "";
    }

    public String getUserIconUrl(){
        if (ossResponseList != null && ossResponseList.size() > 0) {
            return ossResponseList.get(0).url;
        }
        return "";
    }

}
