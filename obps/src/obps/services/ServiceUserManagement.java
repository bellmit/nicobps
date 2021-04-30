package obps.services;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import obps.daos.DaoUserManagementInterface;
import obps.domains.DomainUserManagementInterface;
import obps.models.Pageurls;
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
    
}
