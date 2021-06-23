package obps.daos;

import java.util.HashMap;

import obps.models.BpaApplication;
import obps.models.BpaApplicationFee;
import obps.models.BpaProcessFlow;
import obps.models.BpaSiteInspection;

public interface DaoBPAInterface {

	boolean processBPApplication(BpaProcessFlow data, HashMap<String, Object> response);

	boolean processAppPayment(Integer uSERCODE, BpaApplicationFee bpa, HashMap<String, Object> response);

	boolean rejectBPApplication(BpaProcessFlow data, HashMap<String, Object> response);

	boolean saveBPA(BpaApplication bpa, Integer USERCODE, HashMap<String, Object> response);

	boolean saveBPAStepTwo(BpaApplication bpa, Integer USERCODE, Integer fromprocesscode,
			HashMap<String, Object> response);

	boolean saveBPASiteInspection(BpaSiteInspection bpa, Integer uSERCODE, Integer fromprocesscode,
			HashMap<String, Object> response);

}
