package obps.validators;


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

}
