package obps.services;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import obps.daos.DaoUserManagementInterface;
import obps.domains.DomainUserManagementInterface;
import obps.models.FeeMaster;
import obps.models.FeeTypes;
import obps.models.LicenseesEnclosures;
import obps.models.LicenseesRegistrationsm;
import obps.models.Occupancies;
import obps.models.OfficeLocations;
import obps.models.Pageurls;
import obps.models.SubOccupancies;
import obps.models.Usages;
import obps.models.UserDetails;
import obps.models.UserOfficeLocations;
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
    public boolean submitLicenseesenclosures(LicenseesEnclosures licenseesenclosures)  {
    	return DaoUserManagementInterface.submitLicenseesenclosures(licenseesenclosures);
    }
    
    @Override
    public void settUserSesson(HttpSession session,final String username) {
    	Userlogin user= DaoUserManagementInterface.getUserlogin(username);
    	session.setAttribute("usercode", user.getUsercode().toString());    	
    	session.setAttribute("licenseetypecode", user.getLicenseetypecode()!=null?user.getLicenseetypecode().toString():null); 
    	session.setAttribute("user", user);
    	session.setAttribute("menu",DomainUserManagementInterface.processUrls(DaoUserManagementInterface.getPageUrls(user.getUsercode())));
    }
    
    @Override
    public boolean updatePassword(Map<String,Object> param){    	    	
		return DaoUserManagementInterface.updatePassword(param);	
	}    
    
    @Override
    public boolean updateUser(Map<String,Object>  user){    	    	
		return DaoUserManagementInterface.updateUser(user);	
	}    
    
    @Override
    public List<Userlogin> listOfficeUsers(){    	    	
		return DaoUserManagementInterface.listOfficeUsers();	
	}    

    @Override
    public List<Userlogin> listOfficeUsers(Integer officecode){    	    	
    	return DaoUserManagementInterface.listOfficeUsers(officecode);	
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
    public List<Userlogin> listUserAndMappedPages(Integer officecode) {

		List<Userlogin> list = listOfficeUsers(officecode);
		for (Userlogin user : list) {
			user.setMappedpages(DaoUserManagementInterface.getMappedPageurls(user.getUsercode()));
		}
		return list;
	}

    @Override
    public String saveUserpages(List<Map<String,Object>> upages) {

		return (DaoUserManagementInterface.mapUserpages(upages)) ? "Mapped" : "Failed";
	}
    
    public UserDetails getUserDetails(Integer usercode) {
    	return DaoUserManagementInterface.getUserDetails(usercode);
    }
    
    
    
	@Override
	public String saveUserWards(List<Map<String, Object>> uwards) {
		
		return (DaoUserManagementInterface.mapUserWards(uwards)) ? "Mapped" : "Failed";
	}

	@Override
	public List<OfficeLocations> listWards(Integer officecode) {
		
		return 		DaoUserManagementInterface.listWards(officecode);
	}

	@Override
	public List<UserOfficeLocations> listWardsUser(Integer officecode) {
		return DaoUserManagementInterface.listWardsUser(officecode);
	}

	@Override
	public List<UserOfficeLocations> listUserAndMappedWards(Integer officecode) {

		List<UserOfficeLocations> list = listWardsUser(officecode);
		for (UserOfficeLocations wards : list) {
			wards.setMappedofficelocations(DaoUserManagementInterface.getMappedWards(wards.getUsercode()));
		}
		return list;
	}
}
