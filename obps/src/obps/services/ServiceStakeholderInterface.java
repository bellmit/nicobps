package obps.services;

import java.util.List;
import java.util.Map;

public interface ServiceStakeholderInterface {

	public List<Map<String, Object>> listLicensees();

	public byte[] getEnclosure(Integer usercode, Integer enclosurecode);

	public boolean updateStakeholder(Integer officecode, String applicationcode, Integer usercode, String remarks);

	public boolean updateStakeholder(Integer officecode, String applicationcode, Integer usercode,
			Integer nextprocessode, String remarks);

	public Map<String, Object> getFeeMaster(Integer officecode, Integer usercode, Integer feetypecode);

	public String ulbRegistration(Integer officecode, Integer usercode);
 
	public boolean validateStackHolder(String remarks);
	
	public boolean extendValidity(Short officecode, Integer usercode, String extendedto, Integer extendedby);

	public List<Map<String, Object>> getValidity(Integer usercode);
}
