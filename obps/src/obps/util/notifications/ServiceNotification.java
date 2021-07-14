package obps.util.notifications;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service("serviceNotification")
public class ServiceNotification
{    
    @Autowired private DaoNotification daoNotification;
    
    public List<Notification> listNotificationDetails(Integer officecode,String messageid) {		
    	return daoNotification.listNotificationDetails(officecode,messageid);
    }
    
    
}
