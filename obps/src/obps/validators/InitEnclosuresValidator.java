package obps.validators;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obps.daos.DaoEnclosureManagementInterface;
@Service
public class InitEnclosuresValidator implements InitEnclosuresValidatorInterface{
	@Autowired
	private DaoEnclosureManagementInterface DEMI;

	@Override
	public String validateInitEnclosure(Map<String, Object> param) {
		return DEMI.validateInitEnclosure(param);
		
	}

}
