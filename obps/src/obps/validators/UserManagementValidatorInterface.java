package obps.validators;

import java.util.Map;

import org.springframework.stereotype.Service;

public interface UserManagementValidatorInterface {
	public String validateCreateUser(Map<String, Object> param);
}
