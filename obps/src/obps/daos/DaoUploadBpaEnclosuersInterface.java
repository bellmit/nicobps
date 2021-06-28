package obps.daos;

import java.util.Map;

public interface DaoUploadBpaEnclosuersInterface {
	
	public boolean submitBpaEnclosureDetails(Map<String, Object> param);
	public boolean validateEnclosureDetails(Map<String, Object> param);
}
