package obps.daos;

import java.util.List;
import java.util.Map;

import obps.models.Enclosures;
import obps.models.Modules;
import obps.models.Offices;
import obps.models.Pageurls;
import obps.models.PaymentModes;
import obps.models.Userlogin;

public interface DaoEnclosureManagementInterface{
	
	
	public boolean initenclosures(Map<String, Object> param);

	public List<Enclosures> listEnclosures();

	public boolean updateInitEnclosure(Map<String, Object> enclosure);

	public boolean initoffices(Map<String, Object> param);

	public List <Offices> listOffices();
	public boolean updateInitOffices(Offices offices);

	public List<Enclosures> getMappedEnclosures(Integer modulecode);

	public List<Modules> listModules();

	

	boolean mapModuleEnclosures(List<Map<String, Object>> modulesenclosures);

	public List<PaymentModes> listPaymentModes();

	public List<PaymentModes> getMappedPaymentModes(Integer officecode);

	public boolean mapOfficesPayments(List<Map<String, Object>> officespayments);

	public boolean checkExistEnclosure(Map<String, Object> param);
	public boolean checkExistOffice(Map<String, Object> offices);

	String validateInitEnclosure(Map<String, Object> param);
}
