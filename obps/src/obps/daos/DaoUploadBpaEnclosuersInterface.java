package obps.daos;

import java.util.Map;

import obps.models.BpaEnclosures;

public interface DaoUploadBpaEnclosuersInterface {
	
	public boolean submitBpaEnclosureDetails(BpaEnclosures bpaenclosures);
	public boolean validateEnclosureDetails(Map<String, Object> param);
}
