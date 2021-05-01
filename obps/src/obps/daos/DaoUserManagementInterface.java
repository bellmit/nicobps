package obps.daos;

import java.util.List;
import java.util.Map;

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
}
