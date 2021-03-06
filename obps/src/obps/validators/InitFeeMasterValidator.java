package obps.validators;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import obps.models.FeeMaster;
import obps.models.FeeTypes;
import obps.models.Questionnaire;
@Component
public class InitFeeMasterValidator{

	
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
				response= "Only Numeric Characters Allowed in License Type Code";
				return response;
			}
			if(licencetypecode.length()>5) {
				System.out.println(licencetypecode.length()>5);
				response= "License Type Code Size Exceeds limit in License Type Code. Enter less than 5 digits";
				return response;
			}
		}
	
			
		if(param.get("officecode")!=null) {
			officecode=param.get("officecode").toString();
			m = p3.matcher(officecode);
			boolean b1 = m.find();
			if(!b1) {
				response= "Only Numeric Characters Allowed in Office Code";
				return response;
			}
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "Office Code Size Exceeds limit. Enter less than 5 digits";
				return response;
			}
		}
		if(param.get("feecode")!=null) {
			feecode=param.get("feecode").toString();
			m = p3.matcher(feecode);
			boolean b1 = m.find();
			if(!b1) {
				response= "Only Numeric Characters Allowed in Fee Code";
				return response;
			}
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "Fee Code Size Exceeds limit. Enter less than 5 digits";
				return response;
			}
		}
		else {
			response= "Fee Code Cannot be Null";
			return response;
		}
		if(param.get("feetypecode")!=null) {
			feetypecode=param.get("feetypecode").toString();
			m = p3.matcher(feetypecode);
			boolean b1 = m.find();
			if(!b1) {
				response= "Only Numeric Characters Allowed in Fee Type Code";
				return response;
			}
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "Fee Type Code Size Exceeds limit. Enter less than 5 digits";
				return response;
			}
		}
		else {
			response= "Fee Type Code Cannot be Null";
			return response;
		}
		
		if(param.get("feeamount")!=null) {
			feeamount= param.get("feeamount").toString();
			m = p3.matcher(feeamount);
			boolean b1 = m.find();
			if(!b1) {
				response= "Only Numeric Characters Allowed in Fee Amount";
				return response;
			}
			if(feeamount.length()>5) {
				System.out.println(feeamount.length()>5);
				response= "Fee Amount Size Exceeds limit. Enter less than 5 digits";
				return response;
			}
		}
		else {
			response= "Fee Amount Cannot be Null";
			return response;
		}
		return response;
	}

	
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
				response= "Only Numeric Characters Allowed in License Type Code";
				return response;
			}
			if(licencetypecode.length()>5) {
				System.out.println(licencetypecode.length()>5);
				response= "License Type Code Size Exceeds limit in License Type Code. Enter less than 5 digits";
				return response;
			}
		}
	
			
		if(param.getOfficecode()!=null) {
			officecode=param.getOfficecode().toString();
			m = p3.matcher(officecode);
			boolean b1 = m.find();
			if(!b1) {
				response= "Only Numeric Characters Allowed in Office Code";
				return response;
			}
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "Office Code Size Exceeds limit. Enter less than 5 digits";
				return response;
			}
		}
		if(param.getFeecode()!=null) {
			feecode=param.getFeecode().toString();
			m = p3.matcher(feecode);
			boolean b1 = m.find();
			if(!b1) {
				response= "Only Numeric Characters Allowed in Fee Code";
				return response;
			}
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "Fee Code Size Exceeds limit. Enter less than 5 digits";
				return response;
			}
		}
		else {
			response= "Fee Code Cannot be Null";
			return response;
		}
		if(param.getFeetypecode()!=null) {
			feetypecode=param.getFeetypecode().toString();
			m = p3.matcher(feetypecode);
			boolean b1 = m.find();
			if(!b1) {
				response= "Only Numeric Characters Allowed in Fee Type Code";
				return response;
			}
			if(officecode.length()>5) {
				System.out.println(officecode.length()>5);
				response= "Fee Type Code Size Exceeds limit. Enter less than 5 digits";
				return response;
			}
		}
		else {
			response= "Fee Type Code Cannot be Null";
			return response;
		}
		
		if(param.getFeeamount()!=null) {
			feeamount= param.getFeeamount().toString();
			m = p3.matcher(feeamount);
			boolean b1 = m.find(); 
			if(!b1) {
				response= "Only Numeric Characters Allowed in Fee Amount";
				return response;
			}
			if(feeamount.length()>5) {
				System.out.println(feeamount.length()>5);
				response= "Fee Amount Size Exceeds limit. Enter less than 5 digits";
				return response;
			}
		}
		else {
			response= "Fee Amount Cannot be Null";
			return response;
		}
		return response;
	}

	
	public String validateInitFeeTypes(Map<String, Object> param) {
		String response = "";
		System.out.println(param);

		String feetypedescription="";
		if(param.get("feetypedescription")!=null) {
			feetypedescription=param.get("feetypedescription").toString();
			if(feetypedescription.length()>255) {
				System.out.println(feetypedescription.length()>255);
				response= "Fee Type Description Cannot Exceed More Than 255 Characters";
				return response;
			}
		}
		else {
			response= "Fee Type Description Cannot be Null";
			return response;
		}
		return response;
	}
	public String validateQuestionaires(Map<String, Object> param) {
		String response = "";
		System.out.println(param);
		
		String questiondescription="";
		if(param.get("questiondescription")!=null) {
			questiondescription=param.get("questiondescription").toString();
			if(questiondescription.length()>255) {
				System.out.println(questiondescription.length()>255);
				response= "Question Description Cannot Exceed More Than 255 Characters";
				return response;
			}
		}
		else {
			response= "Question Description Cannot be Null";
			return response;
		}
		return response;
	}

	
	public String validateInitFeeTypes(FeeTypes param) { 
		String response = "";
		System.out.println(param);

		
		String feetypedescription="";
		if(param.getFeetypedescription()!=null) {
			feetypedescription=param.getFeetypedescription().toString();
			if(feetypedescription.length()>255) {
				System.out.println(feetypedescription.length()>255);
				response= "Fee Type Description Cannot Exceed More Than 255 Characters";
				return response;
			}
		}
		else {
			response= "Fee Type Description Cannot be Null";
			return response;
		}
		return response;
	}
	public String validateQuestionaires(Questionnaire param) { 
		String response = "";
		System.out.println(param);
		
		
		String questiondescription="";
		if(param.getQuestiondescription()!=null) {
			questiondescription=param.getQuestiondescription().toString();
			if(questiondescription.length()>255) {
				System.out.println(questiondescription.length()>255);
				response= "Question Description Cannot Exceed More Than 255 Characters";
				return response;
			}
		}
		else {
			response= "Question Description Cannot be Null";
			return response;
		}
		return response;
	}

	

}
