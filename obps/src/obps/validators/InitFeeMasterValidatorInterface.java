package obps.validators;

import java.util.Map;

import obps.models.FeeMaster;
import obps.models.FeeTypes;

public interface InitFeeMasterValidatorInterface { 
	public String validateInitFeeMaster(Map<String, Object> param);
	public String validateInitFeeMaster(FeeMaster param);
	public String validateInitFeeTypes(Map<String, Object> param);
	public String validateInitFeeTypes(FeeTypes param);
}
