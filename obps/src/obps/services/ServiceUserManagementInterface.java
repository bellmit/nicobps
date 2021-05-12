package obps.services;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import obps.models.Pageurls;
import obps.models.Userlogin;

public interface ServiceUserManagementInterface {
	public Long getMaxUsercode();

	public Long getMaxAfrCode();
	
	public boolean checkEmailExistance(final String username);

	public boolean checkMobileExistance(final String mobileno);

	public boolean createUser(Map<String, Object> param);

	public boolean submitEnclosureDetails(Map<String, Object> param);

	public void settUserSesson(HttpSession session, final String username);

	public boolean updatePassword(Map<String, Object> param);

	public boolean updateUser(Userlogin user);

	public List<Userlogin> listUsers();

	public List<Pageurls> listUrls();

	public String savePageurl(Pageurls url);

	public List<Userlogin> listUserAndMappedPages();
	
	public String saveUserpages(List<Map<String,Object>> upages);
}
