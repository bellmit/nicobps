package obps.validators;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obps.daos.DaoEnclosureManagementInterface;
@Service
public class InitOfficesValidator implements InitOfficesValidatorInterface{
	@Autowired
	private DaoEnclosureManagementInterface DEMI;
	@Override
	public String validateInitOffices(Map<String, Object> param) {
		return DEMI.validateInitOffices(param);
	}

}
