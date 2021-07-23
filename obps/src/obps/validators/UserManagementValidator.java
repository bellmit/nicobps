package obps.validators;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import obps.models.Pageurls;


@Component
public class UserManagementValidator{
	
	
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
			return "Please Enter a valid Username or email";
		if(username.length()>99)
			return "Enclosure Name Cannot be more than 99 characters";
		if(param.get("userpassword")!=null)
			userpassword=((String) param.get("userpassword")).trim();
		if(param.get("repassword")!=null)
			repassword=((String) param.get("repassword")).trim();
		if(!userpassword.equals(repassword))
			return "Passwords Do Not Match";
		if(param.get("fullname")!=null)
			fullname=((String) param.get("fullname")).trim();
		m1=p.matcher(fullname);
		b1=m1.find();
		if(b1)
			return "No Special Characters or Numbers allowed in Full Name";
		if(fullname.length()>99)
			return "Full Name Cannot be more than 99 characters";
		System.out.println(param.get("mobileno"));
//		Pattern p3 = Pattern.compile("^([1-9]){1}([0-9]){9}$");
		Pattern p3 = Pattern.compile("^[1-9][0-9]*$");
		if(param.get("mobileno")!=null)
			mobileno = param.get("mobileno").toString();
		
		m1=p3.matcher(mobileno);
		b1=m1.find();
		
		if(!b1)
			return "No Special Characters or Characters allowed in Mobile No. Mobile No. Should not start with zero ";
		if(mobileno.length()>10)
			return "Mobile No Cannot be more than 10 characters";
		if(param.get("designation")!=null)
			designation=((String) param.get("designation")).trim();
		m1=p.matcher(designation);
		b1=m1.find();
		if(b1)
			return "No Special Characters or Numbers allowed in Designation";
		if(designation.length()>99)
			return "Designation Cannot be more than 99 characters";
		
		
		System.out.println(response);
		return response;
	}

	
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
	
	public String validatePageUrls(Pageurls url) {
		System.out.println(url);
		String response = "";
		Pattern p = Pattern.compile("[^A-Za-z_ ]");
		String regex = "^(.+)@(.+)$";
		String pattern="[^A-Za-z_ 0-9\\/\\.\\-\\_]";
		Pattern p2=Pattern.compile(pattern);
		Pattern p3 = Pattern.compile("^[0-9][0-9]*$");
	    
		String pageurl="",subsubmenu="",subsubmenuicon="",submenu="",submenuicon="",parent="";
		String parenticon="",parentorder="",submenuorder="",subsubmenuorder="",showinmenu="";
		String urlcode="";
		boolean b;Matcher m;
		
		
		System.out.println(url.getPageurl());
		if(url.getPageurl()!="") {
			System.out.println("Here");
			pageurl=url.getPageurl();
			m = p2.matcher(pageurl);
			b=m.find();
			if(b)
				return "Special Characters allowed in Page Url are . / - _";
			if(pageurl.length()>100)
				return "Page Url Cannot Exceed more than 100 characters";
		}else
			return "Page Url Cannot Be Null";
		
		if(url.getSubsubmenu()!=null || url.getSubsubmenu()=="") {
			subsubmenu=url.getSubsubmenu();
			m = p2.matcher(subsubmenu);
			b=m.find();
			if(b)
				return "Special Characters allowed in Sub Sub Menu are . / - _";
			if(subsubmenu.length()>50)
				return "Sub Sub Menu Cannot Exceed more than 50 characters";
		}
		
		if(url.getSubsubmenuicon()!=null) {
			subsubmenuicon=url.getSubsubmenuicon();
			m = p2.matcher(subsubmenuicon);
			b=m.find();
//			if(b)
//				return "subsubmenucharerror";
			if(subsubmenuicon.length()>25)
				return "Sub Sub Menu Icon Cannot Exceed more than 25 characters";
		}
		
		if(url.getSubmenu()!=null ||url.getSubmenu()=="") {
			submenu=url.getSubmenu();
			m = p2.matcher(submenu);
			b=m.find();
			if(b)
				return "Special Characters allowed in Sub Menu are . / - _";
			if(submenu.length()>50)
				return "Sub Menu Icon Cannot Exceed more than 50 characters";
		}
		
		if(url.getSubmenuicon()!=null||url.getSubmenuicon()=="") {
			submenuicon=url.getSubmenuicon();
			m = p2.matcher(submenuicon);
			b=m.find();
//			if(b)
//				return "subsubmenucharerror";
			if(submenuicon.length()>25)
				return "Sub Menu Icon Cannot Exceed more than 25 characters";
		}
		if(url.getParent()!=null || url.getParent()=="" ) {
			parent=url.getParent();
			m = p2.matcher(parent);
			b=m.find();
			if(b)
				return "Special Characters allowed in Parent are . / - _";
			if(parent.length()>50)
				return "Parent Cannot Exceed more than 50 characters";
		}
		
		if(url.getParenticon()!=null) {
			parenticon=url.getParenticon();
			m = p2.matcher(parenticon);
			b=m.find();
//			if(b)
//				return "subsubmenucharerror";
			if(parenticon.length()>25)
				return "Parent Icon Cannot Exceed more than 25 characters";
		}
		if(url.getParentorder()!=null) {
			parentorder=url.getParentorder().toString();
			m = p3.matcher(parentorder);
			b=m.find();
			if(!b)
				return "Only Numbers are allowed in Parent Order";
			if(parentorder.length()>5)
				return "Parent Order Cannot Exceed more than 5 characters";
		}
		
		if(url.getSubmenuorder()!=null) {
			submenuorder=url.getSubmenuorder().toString();
			m = p3.matcher(submenuorder);
			b=m.find();
			if(!b)
				return "Only Numbers are allowed in Sub Menu Order";
			if(submenuorder.length()>5)
				return "Sub Order Cannot Exceed more than 5 characters";
		}
		if(url.getSubsubmenuorder()!=null) {
			subsubmenuorder=url.getSubsubmenuorder().toString();
			m = p3.matcher(subsubmenuorder);
			b=m.find();
			if(!b)
				return "Only Numbers are allowed in Sub Sub Menu Order";
			if(subsubmenuorder.length()>5)
				return "Sub Sub Order Cannot Exceed more than 5 characters";
		}
		return response;
	}

	
	public String validateUserWards(List<Map<String, Object>> param) { 
		String response="";
		System.out.println("validate"+param);
		Integer usercode=0;
		for (Map<String, Object> up : param) {
			if(up.get("usercode")!=null)
				usercode = (Integer) up.get("usercode");
			if(up.get("usercode")==null || usercode==0) {
				response = "usercodenull";
				return response;
			}
			if(((Map<String, Object>) up.get("ward")).get("locationcode")==null) {
				response = "locationcodenull";
				return response;
			}
			System.out.println(up.get("usercode"));
			System.out.println("response="+response);
//			System.out.println(((Map<String, Object>) up.get("url")).get("urlcode"));
		}
		return response;
	}
}
