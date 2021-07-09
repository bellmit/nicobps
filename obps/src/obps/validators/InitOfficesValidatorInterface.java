package obps.validators;

import java.util.List;
import java.util.Map;

public interface InitOfficesValidatorInterface {
	public String validateInitOffices(Map<String, Object> param);

	String validateOfficesPaymentModes(List<Map<String, Object>> param);
}
