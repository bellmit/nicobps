package obps.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obps.services.ServiceStakeholderInterface;

@Service 
public class StakeHolderValidator implements StakeHolderValidatorInterface{
	@Autowired
	private ServiceStakeholderInterface SSI;
	@Override
	public String validateStackHolder(Integer officecode, String applicationcode, Integer usercode,
			Integer toprocesscode, String remarks) {
		String res = "";
		int size = 0;
		if(officecode==null || officecode==0)
			res="officecodenull";
		if(applicationcode==null || applicationcode=="")
			res="applicationcodenull";
		else {
			if(applicationcode.length()>20)
				res="applicationcodelength";
		}
		if(usercode==null)
			res="usercodenull";
		if(toprocesscode==null || toprocesscode==0)
			res="toprocesscodenull";
		if(remarks!=null ||remarks!="") {
			size = remarks.length();			
		}
		if (size > 500)
			res = "500";
		
		
		return res;
	}

}
