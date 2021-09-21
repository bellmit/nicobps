/*@author Avijit Debnath*/

package obps.security;

import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

//import obps.models.Userlogin;
import obps.services.ServiceUserManagementInterface;


@Component
//@SessionAttributes({"user"})
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler 
{		
		@Autowired
	    private ServiceUserManagementInterface serviceUserManagementInterface = null;
	    
	    @Override
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {        	
	    	HttpSession session = request.getSession();
	    	serviceUserManagementInterface.settUserSesson(request,session, SecurityContextHolder.getContext().getAuthentication().getName());	        
	    	//Userlogin user = (Userlogin)session.getAttribute("user");
	        //System.out.println(user.toString());	    	
	        setDefaultTargetUrl("/home.htm");
	        super.onAuthenticationSuccess(request, response, authentication);
	    }
}













