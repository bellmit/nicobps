package obps.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import obps.util.common.Patterns;

@Component
public class ExtendValidityValidator {
	public boolean validateExtendValidity(Map<String, String> params, Integer extendedby) {
		if (params.get("usercode") == null || params.get("usercode") == "") {
			return true;
		}else
		if (validateUsercode((String) params.get("usercode"))) {
			return true;
		}else
		if (params.get("officecode") == null || params.get("officecode") == "") {
			return true;
		}else
		if (validateOfficecode((String) params.get("officecode"))) {
			return true;
		}else

		if (params.get("extendedto") == null || params.get("extendedto") == "") {
			return true;
		}else
		if (validatedate((String) params.get("extendedto"))) {
			return true;
		}else

		if (params.get("validto") == null || params.get("validto") == "") {
			return true;
		}else
		if (validatedate((String) params.get("validto"))) {
			return true;
		}else

		if (extendedby== null ) {
			return true;
		}else
		if (validateUsercode(extendedby.toString())) {
			return true;
		}else

		if (params.get("validto") == null || params.get("validto") == "" || params.get("extendedto") == null
				|| params.get("extendedto") == "" || params.get("maxdate") == null || params.get("maxdate") == "") {
			return true;
		}else
		if (validateExtendedtoDate(params.get("validto"), params.get("extendedto"), params.get("maxdate"))) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validateUsercode(String usercode) {

		System.out.println("validate usercode");
		if (usercode == null || usercode.equals("") || !usercode.toString().matches("^[1-9]{1,10}$") || usercode.length()>10) {
//           System.out.println("invalid format");
			return true;
		}
		return false;
	}

	public boolean validateOfficecode(String licenseetypecode) {

		System.out.println("validator validateOfficecode");
		if (licenseetypecode == null || licenseetypecode.equals("")
				|| !licenseetypecode.toString().matches("^[1-9]{1,5}$") || licenseetypecode.length()>5
				) {
//           System.out.println("invalid format");
			return true;
		}
		return false;
	}

	public boolean validatedate(String date) {
		System.out.println("validator date");
		if (date == null || date.equals("") || !Patterns.PatternCompileMatche(Patterns.PATTERN_DATE, date)) {
//          System.out.println("invalid format");
			return true;
		}
		return false;

	}

	public boolean validateExtendedtoDate(String validtodate, String extendedtodate, String maxdate) {
		System.out.println("dates" + validtodate + "ext date::" + extendedtodate);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		try {
			Date date1 = sdf.parse(validtodate);

			Date date2 = sdf.parse(extendedtodate);
			Date date3 = sdf.parse(maxdate);
			System.out.println("date1:::" + date1 + "date2:::" + date2 + "date3:::" + date3);

			int result = date2.compareTo(date3);
			int result2 = date1.compareTo(date2);
			// System.out.println("result: " + result);
			if (result > 0) {
				System.out.println("Date2 is after Date3");
				return true;
			}
			if (result2 > 0) {
				System.out.println("Date1 is after Date2");
				return true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
