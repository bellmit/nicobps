/*@author Avijit Debnath*/

package obps.util.common;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.HttpsURLConnection;


public class ServiceSms 
{       
    String HOST_NAME = "https://164.100.14.211/failsafe/HttpLink?";    
    public String sendSMS(String username,String password,String senderid,String mobileno, String message) throws IOException, NoSuchAlgorithmException, KeyManagementException 
    {
        String res;
        TrustManager[] trustAllCerts = new TrustManager[]
        {
            new X509TrustManager() 
            {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        message = URLEncoder.encode(message, "UTF-8");
        String localuri = HOST_NAME + "username=" + username + "&pin=" + password + "&signature=" + senderid + "&mnumber=" + mobileno + "&message=" + message;
        HttpsURLConnection connection = (HttpsURLConnection) new URL(localuri).openConnection();
        connection.setHostnameVerifier(new CustomizedHostnameVerifier());
        res = connection.getResponseMessage();
        int code = connection.getResponseCode();
        if (code == 200) {
            connection.disconnect();
        }
        return res;
    }
   
}
