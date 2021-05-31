package obps.services;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import obps.daos.DaoUserManagementInterface;
import obps.domains.DomainUserManagementInterface;
import obps.models.FeeMaster;
import obps.models.FeeTypes;
import obps.models.LicenseesRegistrationsm;
import obps.models.Occupancies;
import obps.models.Pageurls;
import obps.models.SubOccupancies;
import obps.models.Usages;
import obps.models.Userlogin;
import obps.util.application.ServiceUtilInterface;

import org.springframework.beans.factory.annotation.Autowired;

@Service("serviceUserManagement")
public class ServiceUserManagement implements ServiceUserManagementInterface
{    
    @Autowired private ServiceUtilInterface serviceUtilInterface;
    @Autowired private DaoUserManagementInterface DaoUserManagementInterface;
    @Autowired private DomainUserManagementInterface DomainUserManagementInterface;
    @Override
	public Long getMaxUsercode() {	    	
		String sql = "SELECT MAX(usercode) FROM nicobps.userlogins ";		
		return serviceUtilInterface.getMaxValue(sql)+1;	
	} 
    @Override
	public Long getMaxAfrCode() {	    	
		String sql = "SELECT MAX(afrcode) FROM nicobps.applicationflowremarks ";		
		return serviceUtilInterface.getMaxValue(sql)+1;	
	}     
    
    @Override
   	public Long getMaxLicenseecode() {	    	
   		String sql = "SELECT MAX(licenseeregistrationcode) FROM masters.licenseesregistrationsm ";		
   		return serviceUtilInterface.getMaxValue(sql)+1;	
   	} 
    
    @Override
   	public Long getMaxFeeTypecode() {	    	
   		String sql = "SELECT MAX(feetypecode) FROM masters.feetypes ";		
   		return serviceUtilInterface.getMaxValue(sql)+1;	
   	} 
	
    @Override
   	public Long getMaxFeeCode() {	    	
   		String sql = "SELECT MAX(feecode) FROM masters.feemaster ";		
   		return serviceUtilInterface.getMaxValue(sql)+1;	
   	} 
	
    
    @Override
	public boolean checkEmailExistance(final String username){
		String sql="SELECT * FROM nicobps.userlogins WHERE TRIM(username)=?"; 
		Object[] criteria ={username.trim()};	
    	return serviceUtilInterface.checkExistance(sql,criteria);
    }
    @Override
	public boolean checkMobileExistance(final String mobileno){
		String sql="SELECT * FROM nicobps.userlogins WHERE TRIM(mobileno)=?"; 
		Object[] criteria ={mobileno.trim()};	
    	return serviceUtilInterface.checkExistance(sql,criteria);
    }	
    
    @Override
    public boolean createUser(Map<String,Object> param) {    	    	
		return DaoUserManagementInterface.createUser(param);	
	}	
    
    @Override
    public boolean submitEnclosureDetails(Map<String,Object> param) {
    	return DaoUserManagementInterface.submitEnclosureDetails(param);
    }
    
    @Override
    public void settUserSesson(HttpSession session,final String username) {
    	Userlogin user= DaoUserManagementInterface.getUserlogin(username);
    	session.setAttribute("usercode", user.getUsercode().toString());    	
    	session.setAttribute("user", user);
    	session.setAttribute("menu",DomainUserManagementInterface.processUrls(DaoUserManagementInterface.getPageUrls(user.getUsercode())));
    }
    
    @Override
    public boolean updatePassword(Map<String,Object> param){    	    	
		return DaoUserManagementInterface.updatePassword(param);	
	}    
    
    @Override
    public boolean updateUser(Userlogin user){    	    	
		return DaoUserManagementInterface.updateUser(user);	
	}    
    
    @Override
    public List<Userlogin> listUsers(){    	    	
		return DaoUserManagementInterface.listUsers();	
	}    
    @Override
    public List<Pageurls> listUrls(){    	    	
		return DaoUserManagementInterface.getPageUrls();	
	}

    @Override
    public String savePageurl(Pageurls url) {
		if(url.getSubsubmenu()!=null)
			url.setSubsubmenu(url.getSubsubmenu().length() == 0 ? null : url.getSubsubmenu());
		if(url.getSubmenu()!=null)
			url.setSubmenu(url.getSubmenu().length() == 0 ? null : url.getSubmenu());
		return (DaoUserManagementInterface.savePageurlsDao(url)) ? "Saved" : "Failed";
	}
    @Override
    public List<Userlogin> listUserAndMappedPages() {

		List<Userlogin> list = listUsers();
		for (Userlogin user : list) {
			user.setMappedpages(DaoUserManagementInterface.getMappedPageurls(user.getUsercode()));
		}
		return list;
	}

    @Override
    public String saveUserpages(List<Map<String,Object>> upages) {

		return (DaoUserManagementInterface.mapUserpages(upages)) ? "Mapped" : "Failed";
	}
    
    @Override
    public boolean updateLicenseesRegistrationsm(LicenseesRegistrationsm licensee){    	    	
		return DaoUserManagementInterface.updateLicenseesRegistrationsm(licensee);	
	} 
    
    @Override
    public boolean updatefeetypes(FeeTypes feeTypes){    	    	
		return DaoUserManagementInterface.updatefeetypes(feeTypes);	
	} 
    
    @Override
    public boolean updatefeemaster(FeeMaster feemaster){    	    	
		return DaoUserManagementInterface.updatefeemaster(feemaster);	
	} 
    @Override
    public boolean updatesuboccupancy(SubOccupancies suboccupancies){    	    	
		return DaoUserManagementInterface.updatesuboccupancy(suboccupancies);	
	} 
    
    @Override
    public boolean updateusages(Usages usages){    	    	
		return DaoUserManagementInterface.updateusages(usages);	
	} 
    
    @Override
    public boolean updateoccupancy(Occupancies occupancies){    	    	
		return DaoUserManagementInterface.updateoccupancy(occupancies);	
	} 
    
    @Override
    public List<Occupancies> listOccupancies(){    	    	
		return DaoUserManagementInterface.listOccupancies();	
	} 
    
    @Override
    public List<FeeMaster> listFeeMaster(){    	    	
		return DaoUserManagementInterface.listFeeMaster();	
	} 
    
    @Override
    public List<SubOccupancies> listSubOccupancy(){    	    	
		return DaoUserManagementInterface.listSubOccupancy();	
	} 
    
    
    @Override
    public List<Usages> listUsages(){    	    	
		return DaoUserManagementInterface.listUsages();	
	} 
    
    
    
    @Override
    public List<LicenseesRegistrationsm> listLicenseesRegistrationsms(){    	    	
		return DaoUserManagementInterface.listLicenseesRegistrationsms();	
	}  
    
    @Override
    public List<FeeTypes> listFeeTypes(){    	    	
		return DaoUserManagementInterface.listFeeTypes();	
	}  
    
    @Override
    public boolean createLicenseeRegistration(Map<String,Object> param) {    	    	
		return DaoUserManagementInterface.createLicenseeRegistration(param);	
	}	
    
    @Override
    public boolean initfeetypes(Map<String,Object> param) {    	    	
		return DaoUserManagementInterface.initfeetypes(param);	
	}	
    
    @Override
    public boolean initoccupancy(Map<String,Object> param) {    	    	
		return DaoUserManagementInterface.initoccupancy(param);	
	}
    
    @Override
    public boolean initfeemaster(Map<String,Object> param) {    	    	
		return DaoUserManagementInterface.initfeemaster(param);	
	}	
    
    @Override
    public boolean initsuboccupancies(Map<String,Object> param) {    	    	
		return DaoUserManagementInterface.initsuboccupancies(param);	
	}	
    
    
    @Override
    public boolean initusages(Map<String,Object> param) {    	    	
		return DaoUserManagementInterface.initusages(param);	
	}	
    
    @Override
    public boolean checkExistance(String sql,Object[] values) {    	    	
		return DaoUserManagementInterface.checkExistance(sql,values);	
	}
    
}
