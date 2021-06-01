package obps.daos;

import java.util.HashMap;

import obps.models.BpaApplication;

public interface DaoBPAInterface {

	boolean saveBPA(BpaApplication bpa, Integer USERCODE, HashMap<String, Object> response);
	
	public boolean saveBPAStepTwo(BpaApplication bpa, Integer USERCODE, Integer fromprocesscode, HashMap<String, Object> response);

}
