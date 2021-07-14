package obps.validators;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import obps.models.FeeMaster;
import obps.models.FeeTypes;
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
			if(!b1) {
				response= "licensetypecodecharactererror";
				return response;
			}
			if(licencetypecode.length()>5) {
				System.out.println(licencetypecode.length()>5);
				response= "licensetypecodesizeerror";
				return response;
			}
		}
	
			
		if(param.get("officecode")!=null) {
			officecode=param.get("officecode").toString();
			m = p3.matcher(officecode);
			boolean b1 = m.find();
			if(!b1) {
				response= "officecodecharactererror";
				return response;
			}
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "officecodesizeerror";
				return response;
			}
		}
		if(param.get("feecode")!=null) {
			feecode=param.get("feecode").toString();
			m = p3.matcher(feecode);
			boolean b1 = m.find();
			if(!b1) {
				response= "feecodecharactererror";
				return response;
			}
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "feecodesizeerror";
				return response;
			}
		}
		else {
			response= "feecodenullerror";
			return response;
		}
		if(param.get("feetypecode")!=null) {
			feetypecode=param.get("feetypecode").toString();
			m = p3.matcher(feetypecode);
			boolean b1 = m.find();
			if(!b1) {
				response= "feetypecodecharactererror";
				return response;
			}
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "feetypecodesizeerror";
				return response;
			}
		}
		else {
			response= "feetypecodenullerror";
			return response;
		}
		
		if(param.get("feeamount")!=null) {
			feeamount= param.get("feeamount").toString();
			m = p3.matcher(feeamount);
			boolean b1 = m.find();
			if(!b1) {
				response= "feeamountnumbererror";
				return response;
			}
			if(feeamount.length()>5) {
				System.out.println(feeamount.length()>5);
				response= "feeamountsizeerror";
				return response;
			}
		}
		else {
			response= "feeamountnullerror";
			return response;
		}
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
			if(!b1) {
				response= "licensetypecodecharactererror";
				return response;
			}
			if(licencetypecode.length()>5) {
				System.out.println(licencetypecode.length()>5);
				response= "licensetypecodesizeerror";
				return response;
			}
		}
	
			
		if(param.getOfficecode()!=null) {
			officecode=param.getOfficecode().toString();
			m = p3.matcher(officecode);
			boolean b1 = m.find();
			if(!b1) {
				response= "officecodecharactererror";
				return response;
			}
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "officecodesizeerror";
				return response;
			}
		}
		if(param.getFeecode()!=null) {
			feecode=param.getFeecode().toString();
			m = p3.matcher(feecode);
			boolean b1 = m.find();
			if(!b1) {
				response= "feecodecharactererror";
				return response;
			}
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "feecodesizeerror";
				return response;
			}
		}
		else {
			response= "feecodenullerror";
			return response;
		}
		if(param.getFeetypecode()!=null) {
			feetypecode=param.getFeetypecode().toString();
			m = p3.matcher(feetypecode);
			boolean b1 = m.find();
			if(!b1) {
				response= "feetypecodecharactererror";
				return response;
			}
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "feetypecodesizeerror";
				return response;
			}
		}
		else {
			response= "feetypecodenullerror";
			return response;
		}
		
		if(param.getFeeamount()!=null) {
			feeamount= param.getFeeamount().toString();
			m = p3.matcher(feeamount);
			boolean b1 = m.find(); 
			if(!b1) {
				response= "feeamountnumbererror";
				return response;
			}
			if(feeamount.length()>5) {
				System.out.println(feeamount.length()>5);
				response= "feeamountsizeerror";
				return response;
			}
		}
		else {
			response= "feeamountnullerror";
			return response;
		}
		return response;
	}

	@Override
	public String validateInitFeeTypes(Map<String, Object> param) {
		String response = "";
		System.out.println(param);

		String feetypedescription="";
		if(param.get("feetypedescription")!=null) {
			feetypedescription=param.get("feetypedescription").toString();
			if(feetypedescription.length()>255) {
				System.out.println(feetypedescription.length()>255);
				response= "feetypedescriptionsizeerror";
				return response;
			}
		}
		else {
			response= "feetypedescriptionnull";
			return response;
		}
		return response;
	}

	@Override
	public String validateInitFeeTypes(FeeTypes param) {
		String response = "";
		System.out.println(param);

		
		String feetypedescription="";
		if(param.getFeetypedescription()!=null) {
			feetypedescription=param.getFeetypedescription().toString();
			if(feetypedescription.length()>255) {
				System.out.println(feetypedescription.length()>255);
				response= "feetypedescriptionsizeerror";
				return response;
			}
		}
		else {
			response= "feetypedescriptionnull";
			return response;
		}
		return response;
	}

	

}
