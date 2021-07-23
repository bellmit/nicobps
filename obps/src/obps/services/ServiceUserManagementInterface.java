package obps.services;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

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

public interface ServiceUserManagementInterface {
	public Long getMaxUsercode();

	public Long getMaxAfrCode();

	public boolean checkEmailExistance(final String username);

	public boolean checkMobileExistance(final String mobileno);

	public boolean createUser(Map<String, Object> param);

	public boolean submitLicenseesenclosures(LicenseesEnclosures licenseesenclosures);

	public void settUserSesson(HttpSession session, final String username);

	public boolean updatePassword(Map<String, Object> param);

	public boolean updateUser(Map<String, Object> user);

	public List<Userlogin> listOfficeUsers();

	public List<Userlogin> listOfficeUsers(Integer officecode);

	public List<Pageurls> listUrls();

	public String savePageurl(Pageurls url);

	public List<Userlogin> listUserAndMappedPages(Integer officecode);

	public String saveUserpages(List<Map<String, Object>> upages);
	
	public UserDetails getUserDetails(Integer usercode);


	public String saveUserWards(List<Map<String, Object>> uwards);

	public List<OfficeLocations> listWards(Integer officecode);

	public List<UserOfficeLocations> listUserAndMappedWards(Integer officecode);

	public List<UserOfficeLocations> listWardsUser(Integer usercode);
}
