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
		String licenseedescription="",licenseeregistrationcode="";
		if(param.get("licenseeregistrationcode")!=null) {
			licenseeregistrationcode=param.get("licenseeregistrationcode").toString(); 
			if(licenseeregistrationcode.length()>5) {
				response= "licenseeregistrationcodesizeerror";
				return response;
			}
		}
		else {
			response= "licenseeregistrationcodenull";
			return response;
		}
		if(param.get("licenseedescription")!=null) {
			licenseedescription=((String) param.get("licenseedescription")).trim();
			
			if(licenseedescription.length()>255) {
				response= "licenseedescriptionsizeerror";
				return response;
			}
		}
		else {
			response= "licenseedescriptionnull";
			return response;
		}
		
		return response;
		
	}
}
