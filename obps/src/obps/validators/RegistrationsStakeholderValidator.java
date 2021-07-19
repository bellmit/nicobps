package obps.validators;

import java.util.Map;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import obps.controllers.ControllerBuildingPermit;
import obps.controllers.ControllerUserManagement;
import obps.util.common.Patterns;

@Component
public class RegistrationsStakeholderValidator {
	private static final Logger LOG = Logger.getLogger(ControllerUserManagement.class.toGenericString());

	public String validateUserDetails(Map<String, Object> param) {
		String response = "";
		if (param.get("licenseetypecode") == null || param.get("licenseetypecode") == "") {
			LOG.info("validate licenseetype");
			response = "invalid licenseetype";
			return response;
		}
		if (validatecommoncodes(((String) param.get("licenseetypecode")).trim())) {
			LOG.info(" validate licenseetype");
			response = "invalid licenseetype";
		}
		if (param.get("designation") == null || param.get("designation") == "") {
			LOG.info(" designation validate");
			response = "invalid designation";
			return response;
		}
		if (validatenamedesignationfirm(((String) param.get("designation")).trim())) {
			LOG.info(" designation validate");
			response = "invalid designation";
			return response;
		}

		if (param.get("firmindividual") == null || param.get("firmindividual") == "") {
			LOG.info(" validate firmindividual");
			response = "invalid firmindividual";
			return response;
		}
		if (((String) param.get("firmindividual")).trim().length() > 1 || !Patterns
				.PatternCompileMatche(Patterns.PATTERN_FIRMINDIVIDUAL, ((String) param.get("firmindividual")).trim())) {
			LOG.info(" validate firmindividual");
			response = "invalid firmindividual";
			return response;
		}

		if (((String) param.get("firmindividual")).trim().equals("F")) {
			if (param.get("firmname") == null || param.get("firmname") == "") {
				LOG.info(" validate firmname");
				response = "invalid firmname";
				return response;
			}
			if (validatenamedesignationfirm(((String) param.get("firmname")).trim())) {
				LOG.info(" validate firmname");
				response = "invalid firmname";
				return response;
			}

		}

		if (param.get("fullname") == null || param.get("fullname") == "")

		{
			LOG.info(" validate fullname");
			response = "invalid fullname";
			return response;
		}
		if (validatenamedesignationfirm(((String) param.get("fullname")).trim())) {
			LOG.info(" validate fullname");
			response = "invalid fullname";
			return response;
		}

		if (param.get("gender") == null || param.get("gender") == "") {
			LOG.info(" validate gender");
			response = "invalid gender";
			return response;
		}
		if (validategender(((String) param.get("gender")).trim())) {
			LOG.info(" validate gender");
			response = "invalid gender";
			return response;
		}
		if (param.get("username") == null || param.get("username") == "") {
			LOG.info(" validate username");
			response = "invalid username";
			return response;
		}
		if (!Patterns.PatternCompileMatche(Patterns.PATTERN_EMAIL,((String) param.get("username")).trim())
				|| ((String) param.get("username")).length() > 99) {
			response = "invalid email";
			return response;
		}
		if (param.get("mobileno") == null || param.get("mobileno") == "") {
			LOG.info(" validate mobileno");
			response = "invalid mobileno";
			return response;
		}
		if (!validateMobileno(((String) param.get("mobileno")).trim())) {
			LOG.info(" validate mobileno");
			response = "invalid mobileno";
			return response;
		}
		if (param.get("userpassword") == null || param.get("userpassword") == "") {
			LOG.info(" validate userpassword");
			response = "invalid userpassword";
			return response;
		}
		if (((String) param.get("userpassword")).length() > 512) {
			LOG.info(" validate userpassword");
			response = "invalid userpassword";
			return response;
		}
		
		if (param.get("preaddressline1") != null || ((String) param.get("preaddressline1")).trim().length() > 0 ) {
			if(((String) param.get("preaddressline1")).trim().length() > 99) {
				
					LOG.info(" validate preaddressline1:::"+(String) param.get("preaddressline1"));
					response = "invalid preaddressline1";
					return response;
				}
		}
		
		if (param.get("preaddressline2") != null || ((String) param.get("preaddressline2")).trim().length() > 0 ) {
			if(((String) param.get("preaddressline2")).trim().length() > 99) {
				
					LOG.info(" validate preaddressline2:::"+(String) param.get("preaddressline2"));
					response = "invalid preaddressline2";
					return response;
				}
		}

		
		
		
	
		if (param.get("previllagetown") == null || param.get("previllagetown") == "") {
			LOG.info(" validate previllagetown");
			response = "invalid previllagetown";
			return response;
		}
		if (validateaddressline(((String) param.get("previllagetown")).trim())) {
			LOG.info(" validate previllagetown");
			response = "invalid previllagetown";
			return response;
		}
		if (param.get("prestatecode") == null || param.get("prestatecode") == "") {
			LOG.info(" validate prestatecode");
			response = "invalid prestatecode";
			return response;
		}
		if (param.get("predistrictcode") == null || param.get("predistrictcode") == "") {
			LOG.info(" validate predistrictcode");
			response = "invalid predistrictcode";
			return response;
		}

		if (validatecommoncodes(((String) param.get("predistrictcode")).trim())) {

			LOG.info(" validate predistrictcode");
			response = "invalid";
			return response;
		}

		if (param.get("prepincode") == null || param.get("prepincode") == "") {
			LOG.info(" validate prepincode");
			response = "invalid";
			return response;
		}
		if (validatepincode(((String) param.get("prepincode")).trim())) {
			LOG.info(" validate prepincode");
			response = "invalid";
			return response;
		}
		
		
		
		if (param.get("peraddressline1") != null || ((String) param.get("peraddressline1")).trim().length() > 0 ) {
			if(((String) param.get("peraddressline1")).trim().length() > 99) {
				
					LOG.info(" validate peraddressline1:::"+(String) param.get("peraddressline1"));
					response = "invalid peraddressline1";
					return response;
				}
		}
		
		if (param.get("peraddressline2") != null || ((String) param.get("peraddressline2")).trim().length() > 0 ) {
			if(((String) param.get("peraddressline2")).trim().length() > 99) {
				
					LOG.info(" validate peraddressline2:::"+(String) param.get("peraddressline2"));
					response = "invalid peraddressline2";
					return response;
				}
		}
		
		
		
		
		
		if (param.get("pervillagetown") == null || param.get("pervillagetown") == "") {
			LOG.info(" validate pervillagetown");
			response = "invalid pervillagetown";
			return response;
		}
		if (validateaddressline(((String) param.get("pervillagetown")).trim())) {
			LOG.info(" validate pervillagetown");
			response = "invalid pervillagetown";
			return response;
		}
		if (param.get("perstatecode") == null || param.get("perstatecode") == "") {
			LOG.info(" validate perstatecode");
			response = "invalid perstatecode";
			return response;
		}
		if (param.get("perdistrictcode") == null || param.get("perdistrictcode") == "") {
			LOG.info(" validate perdistrictcode");
			response = "invalid perdistrictcode";
			return response;
		}
		if (validatecommoncodes(((String) param.get("perdistrictcode")).trim())) {
			LOG.info(" validate perdistrictcode");
			response = "invalid perdistrictcode";
			return response;
		}
		if (param.get("perpincode") == null || param.get("perpincode") == "") {
			LOG.info(" validate perpincode");
			response = "invalid perpincode";
			return response;
		}
		if (validatepincode(((String) param.get("perpincode")).trim())) {
			LOG.info(" validate perpincode");
			response = "invalid perpincode";
			return response;
		}
		if ((String) param.get("listLicenseesregistrationsm") == null
				|| (String) param.get("listLicenseesregistrationsm") == "") {
			LOG.info(" validate listLicenseesregistrationsm");
			response = "invalid listLicenseesregistrationsm";
			return response;
		}
		if ((String) param.get("listLicenseesregistrationsm") != null
				|| (String) param.get("listLicenseesregistrationsm") != "") {
			LOG.info(" validate listLicenseesregistrationsm");
			JSONParser parser = new JSONParser();
			Object obj;
			try {
				obj = parser.parse(((String) param.get("listLicenseesregistrationsm")).trim());
				JSONArray listLicenseesregistrationsm = (JSONArray) obj;

				if (listLicenseesregistrationsm.size() > 0) {
					for (int i = 0; i < listLicenseesregistrationsm.size(); i++) {
						JSONObject row = (JSONObject) listLicenseesregistrationsm.get(i);
						Boolean ischecked = (Boolean) row.get("ischecked");
						if (ischecked == false) {
							LOG.info(" validate licenseeregistrationcode:::");
							response = "invalid licenseeregistrationcode";
							return response;
						}
						if (validatecommoncodes(((String) row.get("licenseeregistrationcode")).trim())) {
							LOG.info(" validate licenseeregistrationcode---"
									+ (String) row.get("licenseeregistrationcode"));
							response = "invalid licenseeregistrationcode";
							return response;
						}
						System.out.println(":::::::regis"+row.get("registrationdescription"));
						if ( row.get("registrationdescription")==null || row.get("registrationdescription")=="" ||  row.get("registrationdescription").equals("")) {
							LOG.info(" validate registrationdescription-----");
							response = "invalid registrationdescription";
							return response;
						}
						if (validatelicenseedescription(((String) row.get("registrationdescription")).trim())) {
							LOG.info(" validate registrationdescription:::"
									+ (String) row.get("registrationdescription"));
							response = "invalid registrationdescription";
							return response;
						}

					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// end validate
		return response;
	}

	public boolean validateUsercode(String usercode) {
		if (usercode == null || usercode.equals("") || !usercode.toString().matches("^[1-9]{1,10}$")
				|| usercode.length() > 10) {
//           System.out.println("invalid format");
			return true;
		}
		return false;
	}

	public boolean validateUsername(String email) {
		// TODO Auto-generated method stub
		if (!Patterns.PatternCompileMatche(Patterns.PATTERN_EMAIL, email) || email.length() > 99) {
			return true;
		}

		return false;
	}

	public boolean validateMobileno(String mobileno) {
		if (mobileno.length() > 10 || !Patterns.PatternCompileMatche(Patterns.PATTERN_MOBILE, mobileno)) {
//          System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validatecommoncodes(String data) {
		if (!data.toString().matches("^[1-9]{1,5}$") || data.length() > 5) {
//           System.out.println("invalid format");
			return true;
		}
		return false;
	}

	public boolean validatefirmindividual(String firmindividual) {
		if (firmindividual.length() > 1
				|| !Patterns.PatternCompileMatche(Patterns.PATTERN_FIRMINDIVIDUAL, firmindividual)) {
//          System.out.println("invalid format");
			return true;
		}
		return true;

	}

	public boolean validategender(String gender) {
		if (gender.length() > 1 || !Patterns.PatternCompileMatche(Patterns.PATTERN_GENDER, gender)) {
//          System.out.println("invalid format");
			return true;
		}
		return false;
	}

	public boolean validateaddressline(String address) {
		System.out.println(" Address : " + address);
//		if (address.length() > 99 || !Patterns.PatternCompileMatche("^(?![ .]+$)[a-zA-Z0-9 .]*$", address)) {
			if (address.length() > 99) {
//          System.out.println("invalid format");
			return true;
		}
		return false;
	}

	public boolean validatepincode(String pincode) {
		if (pincode.length() > 6 || !Patterns.PatternCompileMatche(Patterns.PATTERN_PINCODE, pincode)) {
//          System.out.println("invalid format");
			return true;
		}
		return false;
	}

	public boolean validatenamedesignationfirm(String commondata) {
		if (commondata == null || commondata.equals("") || commondata.length() > 99
				|| !Patterns.PatternCompileMatche(Patterns.PATTERN_NAME, commondata)) {
			System.out.println("invalid data" + commondata);
			return true;
		}
		return false;
	}

	public boolean validatelicenseedescription(String licenseedescription) {
		if (licenseedescription.length() > 99
				|| !Patterns.PatternCompileMatche(Patterns.PATTERN_ALPHA_NUMERIC, licenseedescription)) {
			System.out.println("invalid data" + licenseedescription);
			return true;
		}
		return false;
	}

}
