package obps.daos;

import java.util.List;
import java.util.Map;

import obps.models.FeeMaster;
import obps.models.FeeTypes;
import obps.models.LicenseesEnclosures;
import obps.models.LicenseesRegistrationsm;
import obps.models.Occupancies;
import obps.models.Pageurls;
import obps.models.SubOccupancies;
import obps.models.Usages;
import obps.models.Userlogin;

public interface DaoUserManagementInterface {
	public boolean createUser(Map<String, Object> param);

	public boolean submitLicenseesenclosures(LicenseesEnclosures licenseesenclosures);

	public Userlogin getUserlogin(final String username);

	public boolean updatePassword(Map<String, Object> param);

	public List<Pageurls> getPageUrls();

	public List<Pageurls> getPageUrls(final Integer usercode);

	public boolean updateUser(Userlogin user);

	public List<Userlogin> listOfficeUsers();

	public List<Userlogin> listOfficeUsers(Integer office);

	public boolean savePageurlsDao(Pageurls url);

	public List<Pageurls> getMappedPageurls(Integer usercode);

	public boolean mapUserpages(List<Map<String, Object>> upage);

}
