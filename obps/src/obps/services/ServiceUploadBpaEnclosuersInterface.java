package obps.services;

import java.util.Map;

public interface ServiceUploadBpaEnclosuersInterface {
	public Long getMaxAfrCode();

	public Long getMaxAppEnclosureCode();

	public boolean submitBpaEnclosureDetails(Map<String, Object> param);
}
