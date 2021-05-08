package obps.services;

import java.util.List;
import java.util.Map;

public interface ServiceStakeholderInterface {

	public List<Map<String,Object>> listLicensees();
	public byte[] getEnclosure(Integer usercode,Integer enclosurecode);
}
