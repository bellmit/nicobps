package obps.daos;

import java.util.List;
import java.util.Map;

import obps.models.EdcrScrutiny;
import obps.models.Userlogin;

public interface DaoEdcrScrutinyInterface {
	
	public boolean createEdcrScrutiny(Map<String, Object> param);
	
	public EdcrScrutiny fetchEdcr(final String edcrnumber);
	
	public List<EdcrScrutiny> fetchEdcr_usercd(final String usercode);
	
	public String GetOfficeCode(final String usercode);
}
