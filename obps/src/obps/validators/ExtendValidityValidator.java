package obps.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import obps.util.common.Patterns;

@Component
public class ExtendValidityValidator  {
	
	public boolean validateUsercode(String usercode) {
		usercode = usercode.trim();
		System.out.println("validate usercode");
		if (usercode == null || usercode.equals("") || !usercode.toString().matches("^[1-9]{1,10}$") || Integer.parseInt(usercode) < 0 || Integer.parseInt(usercode)> 2147483647) {
//           System.out.println("invalid format");
			return false;
		}
		return true;
	}
	
	public boolean validateOfficecode(String licenseetypecode) {
		licenseetypecode = licenseetypecode.trim();
		System.out.println("validator validateOfficecode");
		if (licenseetypecode == null || licenseetypecode.equals("") || !licenseetypecode.toString().matches("^[1-9]{1,5}$") || Integer.parseInt(licenseetypecode) < 0 || Integer.parseInt(licenseetypecode) > 32767 ) {
//           System.out.println("invalid format");
			return false;
		}
		return true;
	}
	
	
	public boolean validatedate(String date) {
		date=date.trim();
		System.out.println("validator date");
		if (date == null || date.equals("")  || !Patterns.PatternCompileMatche(Patterns.PATTERN_DATE,date) ) {
//          System.out.println("invalid format");
			return false;
		}
		return true;
		
	}
	
	public boolean validateExtendedtoDate(String validtodate, String extendedtodate,String maxdate) {
		System.out.println("dates"+validtodate+"ext date::"+extendedtodate);
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	        
			try {
				Date date1 = sdf.parse(validtodate);
			
	        Date date2 = sdf.parse(extendedtodate);
	        Date date3 = sdf.parse(maxdate);
	        System.out.println("date1:::"+date1+"date2:::"+date2+"date3:::"+date3);
			
	        int result = date2.compareTo(date3);
	        int result2 = date1.compareTo(date2);
	       // System.out.println("result: " + result);
	        if (result > 0 ) {
	            System.out.println("Date2 is after Date3");
	            return false;
	        } 
	        if (result2 > 0 ) {
	        	System.out.println("Date1 is after Date2");
	        	return false;
	        } 
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return true;
	}
	
}
