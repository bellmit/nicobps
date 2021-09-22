package obps.validators;


import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class StakeHolderValidator{
	
	
	public String validateStackHolder(Integer officecode, String applicationcode, Integer usercode,
			Integer toprocesscode, String remarks) {
		String res = "";
		int size = 0;
		if(officecode==null || officecode==0)
			return "Office code is null";
		if(applicationcode==null || applicationcode=="") 
			return "Application code is null";
		else {
			if(applicationcode.length()>20)
				return "Application code should not exceed 20 characters";
		}
		if(usercode==null)
			return "User code is null";
		if(toprocesscode==null || toprocesscode==0)
			return "toprocesscode is null";
		if(remarks!=null ||remarks!="") {
			size = remarks.length();			
		}
		if (size > 500)
			return  "No. of characters must not exceed 500 in remarks";
		
		
		return res;
	}
	
	public boolean validateSuspendStakeholder(Map<String, String> params, String usercode2,Long slno) {
		int size = 0;
		if (params.get("usercode") == null || params.get("usercode") == "") {
			return true;
		} else if (validateUsercode((String) params.get("usercode"))) {
			return true;

		} else if (validateUsercode(usercode2)) {
			return true;
		} else if (validateUsercode(usercode2)) {
			return true;

		} else if (params.get("remarks") != null || params.get("remarks") != "") {
			return false;
		} else if (params.get("remarks").length() > 255) {
			return true;
		}else if (params.get("enabledisable") != null || params.get("enabledisable") != "") {
			size = params.get("enabledisable").length();
		} else if (size > 1) {
			return true;
		}
		else {
			return false;
		}
return false;
	}

	public boolean validateUsercode(String usercode) {

		System.out.println("validate usercode");
		if (!usercode.toString().matches("^[1-9]{1,10}$") || usercode.length() > 10) {
           System.out.println("invalid format");
			return true;
		}
		return false;
	}

}
