package obps.daos;

import java.util.HashMap;

import obps.models.BpaApplication;

public interface DaoBPAInterface {

	boolean saveBPA(BpaApplication bpa, Integer USERCODE, HashMap<String, Object> response);

}
