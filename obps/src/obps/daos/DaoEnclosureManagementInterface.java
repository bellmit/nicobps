package obps.daos;
 
import java.util.List;
import java.util.Map;

import obps.models.Enclosures;
import obps.models.Filetypes;
import obps.models.Modules;
import obps.models.ModulesEnclosures;
import obps.models.Offices;
import obps.models.Pageurls;
import obps.models.PaymentModes;
import obps.models.Userlogin;

public interface DaoEnclosureManagementInterface{
	
	
	public boolean initenclosures(Map<String, Object> param);

	public List<Enclosures> listEnclosures();
	
	public List<Filetypes> listFiletypes();

	public boolean updateInitEnclosure(Map<String, Object> enclosure);

	public boolean initoffices(Map<String, Object> param);

	public List <Offices> listOffices();
	public boolean updateInitOffices(Map<String, Object> offices);

	public List<Enclosures> getMappedEnclosures(Integer processcode);

	public List<Modules> listModules();
	
	public List<ModulesEnclosures> listModulesEnc(Integer modulecode,Integer processcode,Integer officecode,Integer licenseetypecode);

	

	boolean mapModuleEnclosures(List<Map<String, Object>> modulesenclosures);

	public List<PaymentModes> listPaymentModes();

	public List<PaymentModes> getMappedPaymentModes(Integer officecode);

	public boolean mapOfficesPayments(List<Map<String, Object>> officespayments);
	boolean checkExistance(String sql, Object[] values);
}
