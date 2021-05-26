package obps.services;

import java.util.List;
import java.util.Map;

import obps.models.Enclosures;
import obps.models.Modules;
import obps.models.Offices;
import obps.models.PaymentModes;

public interface ServiceEnclosureManagementInterface {

	boolean initenclosures(Map<String, Object> param);

	boolean initoffices(Map<String, Object> param);

	boolean updateInitEnclosure(Enclosures enclosure);

	boolean updateinitoffices(Offices offices);

	List<Enclosures> listEnclosures();

	List<Offices> listOffices();

	List<Modules> listModulesAndEnclosures();

	String saveUserpages(List<Map<String, Object>> modulesenclosures);

	List<PaymentModes> listPaymentModes();

	List<Offices> listOfficesAndPaymentModes();

	String saveOfficePayment(List<Map<String, Object>> officespayments);

}
