package obps.validators;

import java.util.Map;

import obps.models.FeeMaster;

public interface InitFeeMasterValidatorInterface {
	public String validateInitFeeMaster(Map<String, Object> param);
	public String validateInitFeeMaster(FeeMaster param);
}
