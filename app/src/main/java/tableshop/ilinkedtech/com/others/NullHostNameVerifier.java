package tableshop.ilinkedtech.com.others;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/*
 *  @项目名：  iShop
 *  @包名：    ishop.ilinkedtech.com.others
 *  @文件名:   NullHostNameVerifier
 *  @创建者:   Wilson
 *  @创建时间:  2017/2/23 17:49
 *  @描述：    TODO
 */
public class NullHostNameVerifier
        implements HostnameVerifier
{
    private static final String TAG = "NullHostNameVerifier";

    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
