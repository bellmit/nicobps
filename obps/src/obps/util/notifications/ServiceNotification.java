package obps.util.notifications;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service("serviceNotification")
public class ServiceNotification
{    
    @Autowired private DaoNotification daoNotification;
    
    public Notification notificationDetails(Integer officecode,String messageid) {		
    	return daoNotification.notificationDetails(officecode,messageid);
    }
    
    public String createMessage(List<String> params, String message) {
        String str="";
        for(int i=0;i<params.size();i++) {
        	str.replace("{"+i+"}", params.get(i));
        } 		
        return str;
	}
	

    
    
}
