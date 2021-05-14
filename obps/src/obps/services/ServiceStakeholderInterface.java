package obps.services;

import java.util.List;
import java.util.Map;

public interface ServiceStakeholderInterface {

	public List<Map<String,Object>> listLicensees();
	public byte[] getEnclosure(Integer usercode,Integer enclosurecode);
	public boolean updateStakeholder(Integer usercode, Integer nextprocessode,String remark) ;
	public boolean processPayment(Integer usercode,Integer nextprocessode,Integer slno) ;
}
