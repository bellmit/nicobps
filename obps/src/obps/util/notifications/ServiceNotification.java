package obps.util.notifications;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service("serviceNotification")
public class ServiceNotification {
	@Autowired
	private DaoNotification daoNotification;

	@Autowired
	private ServiceSms serviceSms;
	@Autowired
	private ServiceEmailApi serviceEmail;

	///////////////////////SINGLE MESSAGE//////////////////////////////////////////////////////////////////

	public void sentNotification(Integer officecode, String messageid, String recipientMobileno,String recipientEmailid, String[] params) 
	{
		sentNotification(officecode, messageid, recipientMobileno, recipientEmailid, Arrays.asList(params));
	}

	public void sentNotification(Integer officecode, String messageid, String recipientMobileno,String recipientEmailid, List<String> params) {
		Notification notification = daoNotification.notificationDetails(officecode, messageid);
		String msg = createMessage(notification.getSmsbody(), params);
		notification.setSmsbody(msg);
		msg = createMessage(notification.getEmailbody(), params);
		notification.setEmailbody(msg);
		notification.setRecipientMobileno(recipientMobileno);
		notification.setRecipientEmailid(recipientEmailid);
		serviceSms.sendSingleSMS(notification);
		serviceEmail.sendEmails(notification);
	}

	public void sentSMS(Integer officecode, String messageid, String recipientMobileno, String[] params) {
		sentSMS(officecode, messageid, recipientMobileno, Arrays.asList(params));
	}

	public void sentSMS(Integer officecode, String messageid, String recipientMobileno, List<String> params) {
		Notification notification = daoNotification.notificationDetails(officecode, messageid);
		String msg = createMessage(notification.getSmsbody(), params);
		notification.setSmsbody(msg);
		notification.setRecipientMobileno(recipientMobileno);
		serviceSms.sendSingleSMS(notification);
	}

	public void sentEmail(Integer officecode, String messageid, String recipientEmailid, String[] params) {
		sentEmail(officecode, messageid, recipientEmailid, Arrays.asList(params));
	}

	public void sentEmail(Integer officecode, String messageid, String recipientEmailid, List<String> params) {

		Notification notification = daoNotification.notificationDetails(officecode, messageid);
		String msg = createMessage(notification.getEmailbody(), params);
		notification.setEmailbody(msg);
		notification.setRecipientEmailid(recipientEmailid);
		serviceEmail.sendEmails(notification);
	}
///////////////////////BULK MESSAGE//////////////////////////////////////////////////////////////////
	
////////////////////////////////////////////////////////////////////////////////////////////////////
	public String createMessage(String message, List<String> params) {
		String str = "";
		for (int i = 0; i < params.size(); i++) {
			str.replace("{" + i + "}", params.get(i));
		}
		return str;
	}

}
