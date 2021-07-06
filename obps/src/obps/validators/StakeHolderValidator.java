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
		return SSI.validateStackHolder(remarks);
	}

}
