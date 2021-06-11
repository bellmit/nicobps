package obps.reports;

import java.util.List;
import java.util.Map;

public interface ServicePrintPermitInterface {
	
	public List<Map<String, Object>> getPermitList(String applicationcode,String edcrnumber,String ownername,String fromentrydate,String toentrydate,String criteria) ;

}
