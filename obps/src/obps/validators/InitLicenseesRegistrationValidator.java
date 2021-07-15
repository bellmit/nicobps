package obps.validators;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
@Component
public class InitLicenseesRegistrationValidator {
	public String validateInitLicenseesRegistration(Map<String, Object> param) {
		String response = "";
		Matcher m;
		boolean b=false;
		Pattern p = Pattern.compile("[^A-Za-z_ ]");
		Pattern p3 = Pattern.compile("^[1-9][0-9]*$"); 
		String licenseedescription="",licenseeregistrationcode="";
		if(param.get("licenseeregistrationcode")!=null) {
			m=p3.matcher(licenseeregistrationcode);
			b=m.find();
			if(b) {
				response= "Only Numbers Allowed in Licensee Code";
				return response;
			}
			licenseeregistrationcode=param.get("licenseeregistrationcode").toString(); 
			if(licenseeregistrationcode.length()>5) {
				response= "Licensee Code Size exceeding limit";
				return response;
			}
		}
		else {
			response= "Licensee Code Cannot Be Null";
			return response;
		}
		if(param.get("licenseedescription")!=null) {
			licenseedescription=((String) param.get("licenseedescription")).trim();
			
			if(licenseedescription.length()>255) {
				response= "Licensee Description Cannot be more than 255 characters";
				return response;
			}
		}
		else {
			response= "Licensee Description Cannot be Null";
			return response;
		}
		
		return response;
		
	}
}
