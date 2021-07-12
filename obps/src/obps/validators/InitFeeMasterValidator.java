package obps.validators;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import obps.models.FeeMaster;
@Service
public class InitFeeMasterValidator implements InitFeeMasterValidatorInterface{

	@Override
	public String validateInitFeeMaster(Map<String, Object> param) {
		String response = "";
		System.out.println(param);
//		Pattern p = Pattern.compile("[^A-Za-z_ ]");
		Pattern p3 = Pattern.compile("^[0-9][0-9]*$");
		Matcher m;
		Boolean b=false;
		String licencetypecode="",officecode="",feetypecode="",feecode="",feeamount="";
		if(param.get("licenseetypecode")!=null) {
			licencetypecode=param.get("licenseetypecode").toString();
			m = p3.matcher(licencetypecode);
			boolean b1 = m.find();
			if(!b1)
				response= "licensetypecodecharactererror";
			
			if(licencetypecode.length()>5) {
				System.out.println(licencetypecode.length()>5);
				response= "licensetypecodesizeerror";
			}
		}
	
			
		if(param.get("officecode")!=null) {
			officecode=param.get("officecode").toString();
			m = p3.matcher(officecode);
			boolean b1 = m.find();
			if(!b1)
				response= "officecodecharactererror";
			
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "officecodesizeerror";
			}
		}
		if(param.get("feecode")!=null) {
			feecode=param.get("feecode").toString();
			m = p3.matcher(feecode);
			boolean b1 = m.find();
			if(!b1)
				response= "feecodecharactererror";
			
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "feecodesizeerror";
			}
		}
		else
			response= "feecodenullerror";
		
		if(param.get("feetypecode")!=null) {
			feetypecode=param.get("feetypecode").toString();
			m = p3.matcher(feetypecode);
			boolean b1 = m.find();
			if(!b1)
				response= "feetypecodecharactererror";		
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "feetypecodesizeerror";
			}
		}
		else
			response= "feetypecodenullerror";
		
		
		if(param.get("feeamount")!=null) {
			feeamount= param.get("feeamount").toString();
			m = p3.matcher(feeamount);
			boolean b1 = m.find();
			if(!b1)
				response= "feeamountnumbererror";
			
			if(feeamount.length()>5) {
				System.out.println(feeamount.length()>5);
				response= "feeamountsizeerror";
			}
		}
		else
			response= "feeamountnullerror";
			
		return response;
	}

	@Override
	public String validateInitFeeMaster(FeeMaster param) {
		
		String response = "";
		System.out.println(param);
//		Pattern p = Pattern.compile("[^A-Za-z_ ]");
		Pattern p3 = Pattern.compile("^[0-9][0-9]*$");
		Matcher m;
		Boolean b=false;
		String licencetypecode="",officecode="",feetypecode="",feecode="",feeamount="";
		if(param.getLicenseetypecode()!=null) {
			licencetypecode=param.getLicenseetypecode().toString();
			m = p3.matcher(licencetypecode);
			boolean b1 = m.find();
			if(!b1)
				response= "licensetypecodecharactererror";
			
			if(licencetypecode.length()>5) {
				System.out.println(licencetypecode.length()>5);
				response= "licensetypecodesizeerror";
			}
		}
	
			
		if(param.getOfficecode()!=null) {
			officecode=param.getOfficecode().toString();
			m = p3.matcher(officecode);
			boolean b1 = m.find();
			if(!b1)
				response= "officecodecharactererror";
			
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "officecodesizeerror";
			}
		}
		if(param.getFeecode()!=null) {
			feecode=param.getFeecode().toString();
			m = p3.matcher(feecode);
			boolean b1 = m.find();
			if(!b1)
				response= "feecodecharactererror";
			
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "feecodesizeerror";
			}
		}
		else
			response= "feecodenullerror";
		
		if(param.getFeetypecode()!=null) {
			feetypecode=param.getFeetypecode().toString();
			m = p3.matcher(feetypecode);
			boolean b1 = m.find();
			if(!b1)
				response= "feetypecodecharactererror";		
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "feetypecodesizeerror";
			}
		}
		else
			response= "feetypecodenullerror";
		
		
		if(param.getFeeamount()!=null) {
			feeamount= param.getFeeamount().toString();
			m = p3.matcher(feeamount);
			boolean b1 = m.find();
			if(!b1)
				response= "feeamountnumbererror";
			
			if(feeamount.length()>5) {
				System.out.println(feeamount.length()>5);
				response= "feeamountsizeerror";
			}
		}
		else
			response= "feeamountnullerror";
			
		return response;
	}

	

}
