package obps.validators;

import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import obps.util.common.Patterns;

@Component
public class RegistrationsStakeholderValidator {

	public boolean validateUserDetails(Map<String, Object> param) {

		if (param.get("licenseetypecode") == null || param.get("licenseetypecode") == "") {
			return true;
		} else if (!validatelicenseetypecode((String) param.get("licenseetypecode"))) {
			return true;
		} else

		if (param.get("firmindividual") == null || param.get("firmindividual") == "") {
			return true;
		} else if (!validatefirmindividual((String) param.get("firmindividual"))) {
			return true;
		} else

		if (param.get("firmindividual") == null || param.get("firmindividual") == "") {
			return true;
		} else if (((String) param.get("firmindividual")).equals("F")) {
			if (param.get("firmname") == null || param.get("firmname") == "") {
				return true;
			} else {
				if (!validatefirmname((String) param.get("firmname"))) {
					return true;
				}

			}
		} else

		if (param.get("fullname") == null || param.get("fullname") == "") {
			System.out.println("validate fullname");
			return true;
		} else if (!validateFullname((String) param.get("fullname"))) {
			return true;
		} else

		if (param.get("gender") == null || param.get("gender") == "") {
			return true;
		} else if (!validategender((String) param.get("gender"))) {
			return true;
		} else if (param.get("username") == null || param.get("username") == "") {
			return true;
		} else if (!validateUsername((String) param.get("username"))) {
			return true;
		} else if (param.get("mobileno") == null || param.get("mobileno") == "") {
			return true;
		} else if (!validateMobileno((String) param.get("mobileno"))) {
			return true;
		} else 

		 if (param.get("preaddressline1") != null && param.get("preaddressline1") != "") {
			if (!validatepreaddressline1((String) param.get("preaddressline1")))
				return true;
		} else if (param.get("preaddressline2") != null && param.get("preaddressline2") != "") {
			if (!validatepreaddressline2((String) param.get("preaddressline2")))
				return true;
		} else if (param.get("previllagetown") == null || param.get("previllagetown") == "") {
			return true;
		} else if (!validateprevillagetown((String) param.get("previllagetown"))) {
			return true;
		} else if (param.get("predistrictcode") == null || param.get("predistrictcode") == "") {
			return true;
		} else if (!validatepredistrictcode((String) param.get("predistrictcode"))) {
			return true;
		} else if (param.get("prepincode") == null || param.get("prepincode") == "") {
			return true;
		} else if (!validateprepincode((String) param.get("prepincode"))) {
			return true;
		} else if (param.get("peraddressline1") != null && param.get("peraddressline1") != "") {
			if (!validatepreaddressline1((String) param.get("peraddressline1")))
				return true;
		} else if (param.get("peraddressline2") != null && param.get("peraddressline2") != "") {
			if (!validatepreaddressline2((String) param.get("peraddressline2")))
				return true;
		} else if (param.get("pervillagetown") == null || param.get("pervillagetown") == "") {
			return true;
		} else if (!validatepervillagetown((String) param.get("pervillagetown"))) {
			return true;
		} else if (param.get("perdistrictcode") == null || param.get("perdistrictcode") == "") {
			return true;
		} else if (!validateperdistrictcode((String) param.get("perdistrictcode"))) {
			return true;
		} else if (param.get("perpincode") == null || param.get("perpincode") == "") {
			return true;
		} else if (!validateperpincode((String) param.get("perpincode"))) {
			return true;
		} else if ((String) param.get("listLicenseesregistrationsm") == null
				|| (String) param.get("listLicenseesregistrationsm") == "") {
			return true;
		} else if ((String) param.get("listLicenseesregistrationsm") != null
				&& (String) param.get("listLicenseesregistrationsm") != "") {
			JSONParser parser = new JSONParser();
			Object obj;
			try {
				obj = parser.parse((String) param.get("listLicenseesregistrationsm"));
				JSONArray listLicenseesregistrationsm = (JSONArray) obj;

				if (listLicenseesregistrationsm.size() > 0) {
					for (int i = 0; i < listLicenseesregistrationsm.size(); i++) {
						JSONObject row = (JSONObject) listLicenseesregistrationsm.get(i);
						Boolean ischecked = (Boolean) row.get("ischecked");
						if (param.get("licenseeregistrationcode") == null
								|| param.get("licenseeregistrationcode") == "") {
							return true;
						} else if (!validatelicenseeregistrationcode((String) row.get("licenseeregistrationcode"))) {
							return true;
						}

						if (param.get("registrationdescription") == null
								|| param.get("registrationdescription") == "") {
							return true;
						} else if (!validateregistrationdescription((String) row.get("registrationdescription"))) {
							return true;
						}

					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return false;
		}

		// end validate
		return false;
	}

	public boolean validateUsercode(String usercode) {
		System.out.println("validate usercode");
		if (usercode == null || usercode.equals("") || !usercode.toString().matches("^[1-9]{1,10}$")
				|| usercode.length() > 10) {
//           System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validateUsername(String username) {
		// TODO Auto-generated method stub
		if (!Patterns.PatternCompileMatche(Patterns.PATTERN_EMAIL, username) || username.length() > 99) {
			return false;
		}

		return true;
	}

	public boolean validateUserpassword(String userpassword) {
		System.out.println("validate password");
		if (userpassword.length() > 512 || !Patterns.PatternCompileMatche(Patterns.PATTERN_ALLOW_ALL, userpassword)) {
//           System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validateFullname(String fullname) {
		if (fullname.length() > 99 || !Patterns.PatternCompileMatche(Patterns.PATTERN_NAME, fullname)) {
//          System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validateMobileno(String mobileno) {
		if (mobileno.length() > 10 || !Patterns.PatternCompileMatche(Patterns.PATTERN_MOBILE, mobileno)) {
//          System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validateDesignation(String designation) {
		if (designation.length() > 99 || !Patterns.PatternCompileMatche(Patterns.PATTERN_NAME, designation)) {
//          System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validatelicenseetypecode(String licenseetypecode) {
		System.out.println("validator licenseetypecode");
		if (!licenseetypecode.toString().matches("^[1-9]{1,5}$") || licenseetypecode.length() > 5) {
//           System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validatefirmindividual(String firmindividual) {
		if (firmindividual.length() > 1
				|| !Patterns.PatternCompileMatche(Patterns.PATTERN_FIRMINDIVIDUAL, firmindividual)) {
//          System.out.println("invalid format");
			return false;
		}
		return true;

	}

	public boolean validateapplicantsname(String applicantsname) {
		if (applicantsname.length() > 99 || !Patterns.PatternCompileMatche(Patterns.PATTERN_NAME, applicantsname)) {
//          System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validategender(String gender) {
		if (gender.length() > 1 || !Patterns.PatternCompileMatche(Patterns.PATTERN_GENDER, gender)) {
//          System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validatepreaddressline1(String preaddressline1) {
		if (preaddressline1 == null || preaddressline1.equals("") || preaddressline1.length() > 99
				|| !Patterns.PatternCompileMatche("^[a-zA-Z0-9\\s\\,\\@\\.\\-]*$", preaddressline1)) {
//          System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validatepreaddressline2(String preaddressline2) {
		if (preaddressline2 == null || preaddressline2.equals("") || preaddressline2.length() > 99
				|| !Patterns.PatternCompileMatche("^[a-zA-Z0-9\\s\\,\\@\\.\\-]*$", preaddressline2)) {
//          System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validateprevillagetown(String previllagetown) {
		if (previllagetown == null || previllagetown.equals("") || previllagetown.length() > 99
				|| !Patterns.PatternCompileMatche("^[a-zA-Z0-9\\s\\,\\@\\.\\-]*$", previllagetown)) {
//          System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validatepredistrictcode(String predistrictcode) {
		System.out.println("validator predistrictcode");
		if (predistrictcode == null || predistrictcode.equals("") || !predistrictcode.toString().matches("^[1-9]{1,5}$")
				|| predistrictcode.length() > 5) {
//           System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validateprepincode(String prepincode) {
		if (prepincode == null || prepincode.equals("") || prepincode.length() > 99
				|| !Patterns.PatternCompileMatche(Patterns.PATTERN_PINCODE, prepincode)) {
//          System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validateperaddressline1(String peraddressline1) {
		if (peraddressline1 == null || peraddressline1.equals("") || peraddressline1.length() > 99
				|| !Patterns.PatternCompileMatche("^[a-zA-Z0-9\\s\\,\\@\\.\\-]*$", peraddressline1)) {
//          System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validateperaddressline2(String peraddressline2) {
		if (peraddressline2 == null || peraddressline2.equals("") || peraddressline2.length() > 99
				|| !Patterns.PatternCompileMatche("^[a-zA-Z0-9\\s\\,\\@\\.\\-]*$", peraddressline2)) {
//          System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validatepervillagetown(String pervillagetown) {
		if (pervillagetown == null || pervillagetown.equals("") || pervillagetown.length() > 99
				|| !Patterns.PatternCompileMatche("^[a-zA-Z0-9\\s\\,\\@\\.\\-]*$", pervillagetown)) {
//          System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validateperdistrictcode(String perdistrictcode) {
		System.out.println("validator perdistrictcode");
		if (perdistrictcode == null || perdistrictcode.equals("") || !perdistrictcode.toString().matches("^[1-9]{1,5}$")
				|| perdistrictcode.length() > 5) {
//           System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validateperpincode(String perpincode) {
		if (perpincode == null || perpincode.equals("") || perpincode.length() > 10
				|| !Patterns.PatternCompileMatche(Patterns.PATTERN_PINCODE, perpincode)) {
//          System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validatefirmname(String firmname) {
		if (firmname == null || firmname.equals("") || firmname.length() > 99
				|| !Patterns.PatternCompileMatche(Patterns.PATTERN_NAME, firmname)) {
			System.out.println("invalid firmname");
			return false;
		}
		return true;
	}

	public boolean validatelicenseeregistrationcode(String licenseeregistrationcode) {
		System.out.println("validator licenseeregistrationcode");
		if (licenseeregistrationcode == null || licenseeregistrationcode.equals("")
				|| !licenseeregistrationcode.toString().matches("^[1-9]{1,5}$")
				|| licenseeregistrationcode.length() > 5) {
//           System.out.println("invalid format");
			return false;
		}
		return true;
	}

	public boolean validateregistrationdescription(String registrationdescription) {
		if (registrationdescription == null || registrationdescription.equals("")
				|| registrationdescription.length() > 99
				|| !Patterns.PatternCompileMatche(Patterns.PATTERN_NAME, registrationdescription)) {
//          System.out.println("invalid format");
			return false;
		}
		return true;
	}

}
