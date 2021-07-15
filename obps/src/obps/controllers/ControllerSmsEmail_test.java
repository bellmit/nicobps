package obps.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import obps.util.notifications.Notification;
import obps.util.notifications.ServiceNotification;
import obps.util.notifications.ServiceSms;

@Controller
public class ControllerSmsEmail_test {
	@Autowired
	ServiceNotification serviceNotification;

	@RequestMapping(value = "/sentSms.htm", method = { RequestMethod.GET, RequestMethod.POST })
	public String Sendsms(@RequestParam Map<String, String> params) {
		System.out.println("test sms");
		List<String> list=null;
			list.add("123456");
				
		 serviceNotification.sentNotification (Integer.valueOf("1"), "REGISTRATION","7005883247","leroymarbaniang@gmail.com",list);
	 
	 
		
		return "";
	}

	@RequestMapping(value = "/sentBulkSms.htm", method = { RequestMethod.GET, RequestMethod.POST })
	public String sentBulkSms(@RequestParam Map<String, String> params) {
		System.out.println("test sms");

		return "";
	}

 
}
