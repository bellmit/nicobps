package obps.daos;

import java.util.List;
import java.util.Map;

import obps.models.FeeMaster;
import obps.models.FeeTypes;
import obps.models.LicenseesRegistrationsm;
import obps.models.Occupancies;
import obps.models.Pageurls;
import obps.models.Userlogin;

public interface DaoUserManagementInterface {
	public boolean createUser(Map<String, Object> param);

	public boolean submitEnclosureDetails(Map<String, Object> param);

	public Userlogin getUserlogin(final String username);

	public boolean updatePassword(Map<String, Object> param);

	public List<Pageurls> getPageUrls();

	public List<Pageurls> getPageUrls(final Integer usercode);

	public boolean updateUser(Userlogin user);

	public List<Userlogin> listUsers();

	public boolean savePageurlsDao(Pageurls url);

	public List<Pageurls> getMappedPageurls(Integer usercode);

	public boolean mapUserpages(List<Map<String, Object>> upage);
	
	public List<Occupancies> listOccupancies();
	
	public List<FeeMaster> listFeeMaster();
	
	
	public List<LicenseesRegistrationsm> listLicenseesRegistrationsms();

	public List<FeeTypes> listFeeTypes();
	
	public boolean updateLicenseesRegistrationsm(LicenseesRegistrationsm licensee);

	public boolean updatefeetypes(FeeTypes feetype);

	public boolean updateoccupancy(Occupancies occupancies);
	
	public boolean createLicenseeRegistration(Map<String, Object> param);

	public boolean initfeetypes(Map<String, Object> param);
	
	public boolean initoccupancy(Map<String, Object> param);
	
	public boolean initfeemaster(Map<String, Object> param);
	
}
