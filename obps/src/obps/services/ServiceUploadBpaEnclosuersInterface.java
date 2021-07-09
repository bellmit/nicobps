package obps.services;

import obps.models.BpaEnclosures;

public interface ServiceUploadBpaEnclosuersInterface {
	public Long getMaxAfrCode();

	public Long getMaxAppEnclosureCode();

	public boolean submitBpaEnclosureDetails(BpaEnclosures bpaenclosures);
}
