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
			}
		}
		else
			response= "licenseeregistrationcodenull";
		
		if(param.get("licenseedescription")!=null) {
			licenseedescription=((String) param.get("licenseedescription")).trim();
			m= p.matcher(licenseedescription);
			b = m.find();
			if(b)
				response= "licenseedescriptioncharactererror";
			if(licenseedescription.length()>255) {
				response= "licenseedescriptionsizeerror";
			}
		}
		else
			response= "licenseedescriptionnull";
			
		
		return response;
		
	}
}
