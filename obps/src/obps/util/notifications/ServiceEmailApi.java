/*@author Avijit Debnath*/

package obps.util.notifications;
import java.io.IOException;
import java.io.DataInputStream;

import java.net.URL;
import java.net.URLEncoder;
import java.net.URLConnection;
//import javax.net.ssl.SSLSession;
//import javax.net.ssl.HostnameVerifier;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

public class ServiceEmailApi 
{
    public static void sendEmails(Notification noti) {
    	sendEmails(noti.getSenderemailid(),noti.getEmailidpassword(),noti.getRecipientEmailid(),noti.getEmailsubject(),noti.getEmailbody());
    }	
	
    @SuppressWarnings("deprecation")
	public static String sendEmails(String fromemail,String password,String toemail,String subject,String message) {
        String resp = "4";
        URLConnection urlConn;
        DataInputStream input;
        String str = "";

        try 
        {
            message = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
            subject = URLEncoder.encode(subject, StandardCharsets.UTF_8.toString());

            String urlString = "http://164.100.149.217/nicavi/ApiEmail?";
            urlString += "toemail=" + toemail + "&subject=" + subject + "&message=" + message;
            urlString += "&fromemail="+fromemail+"&password="+fromemail+"";

//            HostnameVerifier hv = new HostnameVerifier() {
//                public boolean verify(String urlHostName, SSLSession session) {
//                    System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
//                    return true;
//                }
//            };

            URL url = new URL(urlString);
            urlConn = url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            input = new DataInputStream(urlConn.getInputStream());

            while (null != ((str = input.readLine()))) {
                if (str.length() > 0) {
                    str = str.trim();
                    if (!str.equals("")) {
                        //System.out.println(str);
                        resp += str;
                    }
                }
            }
            input.close();
            resp = "2";
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
            resp = "4";
        } catch (IOException ioe) {
            ioe.printStackTrace();
            resp = "4";
        }
        return resp;
    }
    

    
//    public static void main(String arg[]) {
//    	String fromemail="avijit.debnath@nic.in";
//    	String password="*****";
//    	String toemail="avijitdebnath@gmail.com";
//    	String subject="Test......!";
//    	String message="Test......!";
//    	sendEmails(fromemail,password,toemail,subject,message);
//    }  
    
    
    
    
}
