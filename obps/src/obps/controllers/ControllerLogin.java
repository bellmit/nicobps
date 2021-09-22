package obps.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import obps.models.Audittrail;
import obps.models.DashboardData;
import obps.models.UserApplications;
import obps.util.application.ServiceUtilInterface;

@Controller
public class ControllerLogin {
	@Autowired ServiceUtilInterface SUI;
	
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true); // true == allow create
	}

	@RequestMapping(value = { "/login.htm", "/login" }, method = RequestMethod.GET)
	public String pageLoad_Login(ModelMap model, @RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		if (error != null) {
			model.addAttribute("error", "Invalid username and password!");
		}
		if (logout != null) {
			model.addAttribute("msg", "You've been logged out successfully.");
		}

		List<DashboardData> listDashboardData = SUI.listDashboardData();
		model.addAttribute("listDashboardData",listDashboardData);
		
		return "login";
	}
	
    @RequestMapping(value = "/loginfailed.htm", method = RequestMethod.GET)
    public String loginerror(ModelMap model, HttpServletRequest request,HttpSession session) {
		List<DashboardData> listDashboardData = SUI.listDashboardData();
		model.addAttribute("listDashboardData",listDashboardData);
		
        String userid = SecurityContextHolder.getContext().getAuthentication().getName();
        request.setAttribute("userid",userid);
        request.setAttribute("actiontaken","Loginfailed");        
		SUI.initAuditrail(request);
        return "login";
    }	
	
    @RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {  

        String userid = SecurityContextHolder.getContext().getAuthentication().getName();
        request.setAttribute("userid",userid);
        request.setAttribute("actiontaken","Logout");        
		SUI.initAuditrail(request);	
    	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		if (auth != null) {			
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
        return "logout";
    }

	@RequestMapping("/home.htm")
	public String index(Model model) {
		Integer usercode = Integer.valueOf(session().getAttribute("usercode").toString());
		List<Map<String,Object>> list=SUI.getCurrentProcessStatus(1, usercode);
		if(!list.isEmpty()) {
			model.addAttribute("processess",list);
		}		
		List<UserApplications> listUserApplications = SUI.listUserApplications(usercode);
		model.addAttribute("listUserApplications",listUserApplications);				
		
		List<UserApplications> listStakeholderApplications = SUI.listStakeholderApplications(usercode);
		model.addAttribute("listStakeholderApplications",listStakeholderApplications);		
		
		return "home";
	}
		
	@RequestMapping("/audittrail.htm")
	public String audittrail(Model model) {
		List<Audittrail> listAuditrail = SUI.listAuditrail();
		model.addAttribute("listAuditrail",listAuditrail);			
		return "audittrail";
	}	  	
	
	@RequestMapping("/contactus.htm")
	public String contactus() {
		return "contactus";
	}	    
 
	
}
