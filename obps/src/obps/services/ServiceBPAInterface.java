package obps.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import obps.models.BpaApplication;
import obps.util.application.CommonMap;

public interface ServiceBPAInterface {

	List<CommonMap> listOfficelocations();

	List<CommonMap> listOfficelocations(Integer officecode);

	List<CommonMap> listOwnershiptypes();

	List<CommonMap> listRelationshiptypes();

	List<CommonMap> listSalutations();

	Map<String, Object> getEdcrDetails(Integer USERCODE, String edcrnumber);

	List<Map<String, Object>> listAppScrutinyDetailsForBPA(Integer USERCODE);

	/* CREATE */
	boolean saveBPA(BpaApplication bpa, Integer USERCODE, HashMap<String, Object> response);

}
