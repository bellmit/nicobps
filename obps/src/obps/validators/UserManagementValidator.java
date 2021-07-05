package obps.validators;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obps.daos.DaoUserManagementInterface;
@Service
public class UserManagementValidator implements UserManagementValidatorInterface{
	@Autowired DaoUserManagementInterface DUMI;
	@Override
	public String validateCreateUser(Map<String, Object> param) {
		return DUMI.validateCreateUser(param);
	}

}
