package obps.validators;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import obps.daos.DaoEnclosureManagementInterface;

@Component
public class InitOfficesValidator {
	@Autowired
	private DaoEnclosureManagementInterface DEMI;

	public String validateInitOffices(Map<String, Object> param) {
		String response = "";
		String pattern = "[^A-Za-z_ 0-9\\'\\/\\.\\,\\-\\(\\)\\_]";
		String pattern1 = "[^A-Za-z_ 0-9\\-]";
		String XPATTERN_STRING_SPACE = "^(?![ .]+$)[a-zA-Z .]*$";
		String XPATTERN_POSITIVE_INTEGER = "[1-9]";

		Pattern p = Pattern.compile(pattern);
		Pattern p1 = Pattern.compile(pattern1);
		Pattern p3 = Pattern.compile(XPATTERN_STRING_SPACE);
		Pattern p4 = Pattern.compile(XPATTERN_POSITIVE_INTEGER);

		String officename1 = "", officename2 = "", officename3 = "", officeshortname = "", signatoryname = "",
				signatorydesignation = "", smsusername = "", emailidpassword = "", smspassword = "", smssenderid = "",
				isregisteringoffice = "", registeringofficecode = "", stateid = "", tenantid = "";
		if (param.get("officename1") != null)
			officename1 = ((String) param.get("officename1")).trim();
		Matcher of1 = p.matcher(officename1);
		boolean b1 = of1.find();
		if (b1) {
			response = "Special Characters allowed in Office Name1 are , . / ( ) - _ ";
			return response;
		}
		if (param.get("officename2") != null)
			officename2 = ((String) param.get("officename2")).trim();
		Matcher of2 = p.matcher(officename2);
		b1 = of2.find();
		if (b1) {
			response = "Special Characters allowed in Office Name2 are , . / ( ) - _ ";
			return response;
		}
		if (param.get("officename3") != null) {
			officename3 = ((String) param.get("officename3")).trim();
			Matcher of3 = p.matcher(officename3);
			b1 = of3.find();
			if (b1) {
				response = "Special Characters allowed in Office Name3 are , . / ( ) - _";
				return response;
			}
		}

		if (param.get("signatoryname") != null) {
			signatoryname = ((String) param.get("signatoryname")).trim();
			Matcher offsignatory = p.matcher(signatoryname);
			b1 = offsignatory.find();
			if (b1) {
				response = "Special Characters allowed in Signatory Name are , . / ( ) - _";
				return response;
			}
		}
		if (param.get("signatorydesignation") != null) {
			signatorydesignation = ((String) param.get("signatorydesignation")).trim();
			Matcher offshortdes = p.matcher(signatorydesignation);
			b1 = offshortdes.find();
			if (b1) {
				response = "Special Characters allowed in Signatory Designation are , . / ( ) - _";
				return response;
			}
		}
		if (param.get("officeshortname") != null) {
			officeshortname = ((String) param.get("officeshortname")).trim();
			Matcher offshort = p.matcher(officeshortname);
			b1 = offshort.find();
			if (b1) {
				response = "No Special Characters or Numbers allowed in Office ShortHand Name";
				return response;
			}
		}

		if (param.get("smsusername") != null) {
			smsusername = ((String) param.get("smsusername")).trim();
			Matcher sms = p.matcher(smsusername);
			b1 = sms.find();
			if (b1) {
				response = "Special Characters allowed in SMS Username are , . / ( ) - _";
				return response;
			} 
		}

		if (param.get("senderemailid") == "") 
			System.out.println("");
		else if (param.get("senderemailid") != null) {
			String emailid = ((String) param.get("senderemailid")).trim();
			String regex = "^(.+)@(.+)$";
			final Pattern p2 = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
			Matcher matcher = p2.matcher(emailid);
			Boolean b = matcher.find();
			if (!b) {
				response = "Please Enter correct emailid";
				return response;
			}
			if (emailid.length() > 255) {
				response = "emailid Cannot be more than 255 characters";
				return response;
			}
		}
		if (param.get("emailidpassword") != null) {
			emailidpassword = ((String) param.get("emailidpassword")).trim();
		}
		if (param.get("smspassword") != null) {
			smspassword = ((String) param.get("smspassword")).trim();
		}

		if (param.get("smssenderid") != null) {
			smssenderid = ((String) param.get("smssenderid")).trim();
			if (smssenderid.length() > 10) {
				response = " smssenderid exceed more than 10 characters";
				return response;
			}
		}

		if (param.get("isregisteringoffice") != null) {
			if (!((String) param.get("isregisteringoffice")).trim().isEmpty()) {
				isregisteringoffice = ((String) param.get("isregisteringoffice")).trim();
				System.out.println("isregisteringoffice in validation ==" + isregisteringoffice);
				if (isregisteringoffice.length() > 1) {
					response = " is registering office Cannot exceed more than 2 characters";
					return response;
				}
			}
		}

		if (((String) param.get("isregisteringoffice")).trim() != "Y") {
			if (param.get("registeringofficecode") != null) {
				registeringofficecode = param.get("registeringofficecode").toString().trim();
				System.out.println("registeringofficecode in validation ==" + registeringofficecode);
				if (registeringofficecode.length() > 2) {
					response = " registering office code Cannot exceed more than 2 characters";
					return response;
				}
			}
		}
		if (param.get("stateid") != null) {
			stateid = param.get("stateid").toString().trim();
			Matcher m = p1.matcher(stateid);
			b1 = m.find();
			if (b1) {
				response = "Special Characters allowed in State id are - _";
				return response;
			}
			if (stateid.length() > 25) {
				response = "State ID Cannot exceed more than 255 characters ";
				return response;
			}
		}
		if (param.get("tenantid") != null) {
			tenantid =  param.get("tenantid").toString().trim();
			Matcher m = p1.matcher(tenantid);
			b1 = m.find();
			if (b1) {
				response = "Special Characters allowed in Tenant id are - _";
				return response;
			}
			if (tenantid.length() > 25) {
				response = "Tenant ID Cannot exceed more than 255 characters";
				return response;
			}
		}

		if (officename1.length() > 255)
			return "Office Name 1 Cannot exceed more than 255 characters";
		if (officename2.length() > 255)
			return "Office Name 2 Cannot exceed more than 255 characters";
		if (officename3.length() > 255)
			return "Office Name 3 Cannot exceed more than 255 characters";
		if (officeshortname.length() > 50)
			return "Office Short Hand Name Cannot exceed more than 50 characters";
		if (signatoryname.length() > 255)
			return "Signatory Name Cannot exceed more than 255 characters";
		if (signatorydesignation.length() > 50)
			return "Signatory Designation Cannot exceed more than 255 characters";
		if (smsusername.length() > 25)
			return "sms username Cannot exceed more than 25 characters";
		if (smspassword.length() > 100)
			return "sms password Cannot exceed more than 100 characters";

		if (emailidpassword.length() > 255)
			return "emailidpassword Cannot exceed more than 255 characters";

		String ext = "";
		Integer size = 0;

		if (param.get("logo") != null) {
			if (param.get("extension") != null) {
				ext = ((String) param.get("extension")).trim().toLowerCase();
				System.out.println(ext);

				if (ext.equalsIgnoreCase("jpg"))
					System.out.println(ext);
				else if (ext.equalsIgnoreCase("jpeg"))
					System.out.println(ext);
				else if (ext.equalsIgnoreCase("png"))
					System.out.println(ext);
				else
					return "Invalid File Type. Only jpeg,jpg and png files are allowed";
			}
			if (param.get("filesize") != null) {
				size = (Integer) param.get("filesize");
				float res = size / 1024;
				res = res / 1024;
				System.out.println(res);
				if (res > 5)
					return "File Size must not exceed 5MB";
			}
		}
		return response;
	}

	public String validateOfficesPaymentModes(List<Map<String, Object>> param) {
		String response = "";
 
		Integer officecode = 0;
		for (Map<String, Object> up : param) {
			if (up.get("officecode") != null)
				officecode = (Integer) up.get("officecode");
			if (up.get("officecode") == null || officecode == 0) {
				response = "officecodenull";
				return response;
			}

			if (((Map<String, Object>) up.get("paymentmodecode")).get("paymentmodecode") == null) {
				response = "paymentmodecodenull";
				return response;
			}
			System.out.println(up.get("officecode"));
			System.out.println("response=" + response);
//			System.out.println(((Map<String, Object>) up.get("url")).get("urlcode"));
		}
		return response;
	}

}
