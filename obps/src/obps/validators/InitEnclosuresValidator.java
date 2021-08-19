package obps.validators;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import obps.daos.DaoEnclosureManagementInterface;
import obps.util.common.Patterns;

@Component
public class InitEnclosuresValidator {

	@Autowired
	private DaoEnclosureManagementInterface DEMI;

	public String validateInitEnclosure(Map<String, Object> param) {
		String response = "";
		String encldesc = "";
		String enclname = "";

		if (param.get("enclosurename") != null)
			enclname = ((String) param.get("enclosurename")).trim();

		if (param.get("enclosuredescription") != null)
			encldesc = ((String) param.get("enclosuredescription")).trim();
		String pattern = "[^A-Za-z_ 0-9\\'\\/\\.\\,\\-\\(\\)\\_]";
		Pattern p = Pattern.compile(pattern);

		Matcher m1 = p.matcher(enclname);

		boolean b1 = m1.find();

		if (b1) {
			response = "Special Characters allowed in Enclosure Name are , . / ( ) - _";
			return response;
		}

		if (enclname.length() > 255) {
			response = "Enclosure Name Cannot be more than 50 characters";
			return response;
		}

		if (encldesc.length() > 255) {
			response = "Enclosure Description Cannot be more than 255 characters";
			return response;
		}

		return response;

	}

	public String validateModulesEnclosure(List<Map<String, Object>> param) {
		String response = "";
//		System.out.println(param);
		Integer modulecode = 0;
		Integer officecode = 0;
		Integer processcode = 0;
		Integer licenseetypecode = 0;

		for (Map<String, Object> up : param) {
			if (up.get("modulecode") != null) {
				if (up.get("modulecode").toString().trim() != "" && Patterns.PatternCompileMatche(
						Patterns.PATTERN_POSITIVEINTEGER, up.get("modulecode").toString().trim())) {
					modulecode = Integer.parseInt(up.get("modulecode").toString().trim());
				}

			}

			if (up.get("modulecode") == null || modulecode == 0) {
				response = "modulecodenull"; // null or invalid pattern
				return response;
			}

			if (((Map<String, Object>) up.get("enclosurecode")).get("enclosurecode") == null) {
				response = "enclosurecodenull";
				return response;
			}

			if (up.get("officecode") != null) {
				if (up.get("officecode").toString().trim() != "") {
					if (!Patterns.PatternCompileMatche(Patterns.PATTERN_POSITIVEINTEGER,
							up.get("officecode").toString().trim())) {
					
						response = "officecodeinvalid";
						return response;
					}
				}

			}



			if (up.get("processcode") != null) {
				if (up.get("processcode").toString().trim() != "" && Patterns.PatternCompileMatche(
						Patterns.PATTERN_POSITIVEINTEGER, up.get("processcode").toString().trim())) {
					processcode = Integer.parseInt(up.get("processcode").toString());
				}
			}

			if (up.get("processcode") == null || processcode == 0) {
				response = "processcodenull"; // null or invalid pattern
				return response;
			}

			if (up.get("licenseetypecode") != null) {
				if (up.get("licenseetypecode").toString().trim() != "") {
					if (!Patterns.PatternCompileMatche(Patterns.PATTERN_POSITIVEINTEGER,
							up.get("licenseetypecode").toString().trim())) {
					
						response = "licenseetypecodeinvalid";
						return response;
					}
				}

			}


//			System.out.println(up.get("modulecode"));
			System.out.println("response=" + response);
//			System.out.println(((Map<String, Object>) up.get("url")).get("urlcode"));
		}
		return response;
	}

}
