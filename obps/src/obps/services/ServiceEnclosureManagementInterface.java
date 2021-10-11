package obps.services;

import java.util.List;
import java.util.Map;

import obps.models.Enclosures;
import obps.models.Filetypes;
import obps.models.ModulesEnclosures;
import obps.models.Offices;
import obps.models.PaymentModes;

public interface ServiceEnclosureManagementInterface {

	boolean initenclosures(Map<String, Object> param);

	boolean initoffices(Map<String, Object> param);

	boolean updateInitEnclosure(Map<String, Object> enclosures);

	boolean updateinitoffices(Map<String, Object> offices);

	List<Enclosures> listEnclosures();
	
	List<Filetypes> listFiletypes();

	List<Offices> listOffices();

	List<ModulesEnclosures> listModulesAndEnclosures(Integer modulecode,Integer processcode,Integer officecode,Integer licenseetypecode);

	String saveUserpages(List<Map<String, Object>> modulesenclosures);

	List<PaymentModes> listPaymentModes();

	List<Offices> listOfficesAndPaymentModes();

	String saveOfficePayment(List<Map<String, Object>> officespayments);


}
