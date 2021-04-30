package obps.domains;

import java.util.List;

import obps.models.Pageurls;
import obps.models.Urls;


public interface DomainUserManagementInterface {
	public List<Urls> processUrls(List<Pageurls> urls);
}
