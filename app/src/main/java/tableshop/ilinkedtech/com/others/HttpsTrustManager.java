package tableshop.ilinkedtech.com.others;

/*
 *  @文件名:   HttpsTrustManager
 *  @创建者:   Wilson
 *  @创建时间:  2017/11/16 9:38
 *  @描述：    TODO
 */


import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class HttpsTrustManager
        implements X509TrustManager
{
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws
                                                                             CertificateException
    {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws
                                                                             CertificateException
    {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
