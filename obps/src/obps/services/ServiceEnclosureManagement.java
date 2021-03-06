package obps.services;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import obps.daos.DaoEnclosureManagementInterface;
import obps.daos.DaoUserManagementInterface;
import obps.daos.DaoEnclosureManagementInterface;
import obps.domains.DomainUserManagementInterface;
import obps.models.Enclosures;
import obps.models.FeeTypes;
import obps.models.Filetypes;
import obps.models.Modules;
import obps.models.ModulesEnclosures;
import obps.models.Offices;
import obps.models.Pageurls;
import obps.models.PaymentModes;
import obps.models.Userlogin;
import obps.util.application.ServiceUtilInterface;

import org.springframework.beans.factory.annotation.Autowired;

@Service("serviceEnclosureManagement")
public class ServiceEnclosureManagement implements ServiceEnclosureManagementInterface
{    
    @Autowired private ServiceUtilInterface serviceUtilInterface;
    @Autowired private DaoEnclosureManagementInterface DaoEnclosureManagementInterface;
    @Autowired private DomainUserManagementInterface DomainUserManagementInterface;
  
   
    
	@Override
    public boolean initenclosures(Map<String,Object> param) {    	    	
		return DaoEnclosureManagementInterface.initenclosures(param);	
	}	
    @Override
	
	public boolean initoffices(Map<String, Object> param) {
    	return DaoEnclosureManagementInterface.initoffices(param);
	}
   
    
	@Override
	public boolean updateInitEnclosure(Map<String,Object> enclosure){    	    	
		return DaoEnclosureManagementInterface.updateInitEnclosure(enclosure);	
	} 
    
    
   
	@Override
	public boolean updateinitoffices(Map<String,Object> offices) {
    	return DaoEnclosureManagementInterface.updateInitOffices(offices);
	}
  
	@Override
    public List<Enclosures> listEnclosures(){    	    	
		return DaoEnclosureManagementInterface.listEnclosures();	
	} 
	
	@Override
    public List<Filetypes> listFiletypes(){    	    	
		return DaoEnclosureManagementInterface.listFiletypes();	
	} 
	@Override
    public List<ModulesEnclosures> listModulesAndEnclosures(Integer modulecode,Integer processcode,Integer officecode, Integer licenseetypecode) {

		return DaoEnclosureManagementInterface.listModulesEnc(modulecode,processcode,officecode,licenseetypecode);
//		for (ModulesEnclosures process : list) {
//			System.out.println("DAO"+process.getModulecode());
//			process.setMappedenclosures(DaoEnclosureManagementInterface.getMappedEnclosures(process.getProcesscode()));
//		}
	
	}
   
	
	private List<Modules> listModules() {
		return DaoEnclosureManagementInterface.listModules();
	}
	@Override
	public List<Offices> listOffices() {
    	return DaoEnclosureManagementInterface.listOffices();
	}
	@Override
	public String saveUserpages(List<Map<String, Object>> modulesenclosures) {
		return (DaoEnclosureManagementInterface.mapModuleEnclosures(modulesenclosures)) ? "Mapped" : "Failed";
	}
	@Override
	public List<PaymentModes> listPaymentModes() {
		return DaoEnclosureManagementInterface.listPaymentModes();
	}
	@Override
	public List<Offices> listOfficesAndPaymentModes() {
		List<Offices> list = listOffices();
		for (Offices office : list) {
			System.out.println("DAO"+office.getOfficecode());
			office.setMappedpaymentmodes(DaoEnclosureManagementInterface.getMappedPaymentModes(office.getOfficecode()));
		}
		return list;
	}
	@Override
	public String saveOfficePayment(List<Map<String, Object>> officespayments) {
		return (DaoEnclosureManagementInterface.mapOfficesPayments(officespayments)) ? "Mapped" : "Failed";
	}
	
	
	
	
    
   

   
  
	
	
	
}

