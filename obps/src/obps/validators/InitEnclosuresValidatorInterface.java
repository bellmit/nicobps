package obps.validators;

import java.util.List;
import java.util.Map;

public interface InitEnclosuresValidatorInterface {
	public String validateInitEnclosure(Map<String, Object> param);
	public String validateModulesEnclosure(List<Map<String, Object>> param);
}
