package obps.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obps.services.ServiceStakeholderInterface;

@Service 
public class StakeHolderValidator implements StakeHolderValidatorInterface{
	@Autowired
	private ServiceStakeholderInterface SSI;
	@Override
	public boolean validateStackHolder(String remarks) {
		Boolean res = false;
		int size = 0;
		if(remarks!=null ||remarks!="") {
			size = remarks.length();
			
		}
		if (size > 500)
			res = true;
		
		System.out.println("Remarks" + remarks);
		
		return res;
	}

}
