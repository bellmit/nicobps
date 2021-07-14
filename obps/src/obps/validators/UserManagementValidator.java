package obps.validators;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.springframework.stereotype.Service;


@Service
public class UserManagementValidator implements UserManagementValidatorInterface{
	
	@Override
	public String validateCreateUser(Map<String, Object> param) {
		String response = "";
		Pattern p = Pattern.compile("[^A-Za-z_ ]");
		String regex = "^(.+)@(.+)$";
	    final Pattern p2 = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		String username="",userpassword="",fullname="",designation="",repassword="",mobileno="";
		
		boolean b1;
		if(param.get("username")!=null)
			username=((String) param.get("username")).trim();
		Matcher m1 = p2.matcher(username);
		b1=m1.find();
		if(!b1)
			return "username";
		if(username.length()>99)
			return "userlength";
		if(param.get("userpassword")!=null)
			userpassword=((String) param.get("userpassword")).trim();
		if(param.get("repassword")!=null)
			repassword=((String) param.get("repassword")).trim();
		if(!userpassword.equals(repassword))
			return "passworddeoesnotmatch";
		if(param.get("fullname")!=null)
			fullname=((String) param.get("fullname")).trim();
		m1=p.matcher(fullname);
		b1=m1.find();
		if(b1)
			return "fullname";
		if(fullname.length()>99)
			return "fullnamelength";
		System.out.println(param.get("mobileno"));
//		Pattern p3 = Pattern.compile("^([1-9]){1}([0-9]){9}$");
		Pattern p3 = Pattern.compile("^[1-9][0-9]*$");
		if(param.get("mobileno")!=null)
			mobileno = param.get("mobileno").toString();
		
		m1=p3.matcher(mobileno);
		b1=m1.find();
		
		if(!b1)
			return "mobile";
		if(mobileno.length()>10)
			return "mobilelength";
		if(param.get("designation")!=null)
			designation=((String) param.get("designation")).trim();
		m1=p.matcher(designation);
		b1=m1.find();
		if(b1)
			return "designation";
		if(designation.length()>99)
			return "designationlength";
		
		
		System.out.println(response);
		return response;
	}

	@Override
	public String validateAccessControl(List<Map<String, Object>> param) {
		String response="";
		System.out.println(param);
		Integer usercode=0;
		for (Map<String, Object> up : param) {
			if(up.get("usercode")!=null)
				usercode = (Integer) up.get("usercode");
			if(up.get("usercode")==null || usercode==0) {
				response = "usercodenull";
				return response;
			}
			if(((Map<String, Object>) up.get("url")).get("urlcode")==null) {
				response = "urlcodenull";
				return response;
			}
			System.out.println(up.get("usercode"));
			System.out.println("response="+response);
//			System.out.println(((Map<String, Object>) up.get("url")).get("urlcode"));
		}
		return response;
	}

}
