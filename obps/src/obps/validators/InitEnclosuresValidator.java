package obps.validators;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obps.daos.DaoEnclosureManagementInterface;
@Service
public class InitEnclosuresValidator implements InitEnclosuresValidatorInterface{ 
	@Autowired
	private DaoEnclosureManagementInterface DEMI;

	@Override
	public String validateInitEnclosure(Map<String, Object> param) {
		String response = "";
		String encldesc="";
		String enclname="";
		
		if(param.get("enclosurename")!=null)
			enclname=((String) param.get("enclosurename")).trim(); 
		
		if(param.get("enclosuredescription")!=null)
			encldesc=((String) param.get("enclosuredescription")).trim();
		String pattern="[^A-Za-z_ 0-9\\'\\/\\.\\,\\-\\(\\)\\_]";
		 Pattern p = Pattern.compile(pattern);
		
	     Matcher m1 = p.matcher(enclname);
	     
	     boolean b1 = m1.find();
	     
	     if (b1) {
	    	 response="m1";
	    	 return response;
	     }
	         

	     if(enclname.length()>255) {
	    	 response="50";
	    	 return response;
	     }
	    	 
	     if(encldesc.length()>255) {
	    	 response="255";
	    	 return response;
	     }
	    	 
		
		return response;
		
	}

	@Override
	public String validateModulesEnclosure(List<Map<String, Object>> param) {
		String response="";
//		System.out.println(param);
		Integer modulecode=0;
		for (Map<String, Object> up : param) {
			if(up.get("modulecode")!=null)
				modulecode = (Integer) up.get("modulecode");
			if(up.get("modulecode")==null || modulecode==0) { 
				response = "modulecodenull";
				return response;
			}
			
			if(((Map<String, Object>) up.get("enclosurecode")).get("enclosurecode")==null) {
				response = "enclosurecodenull";
				return response;
			}
			System.out.println(up.get("modulecode"));
			System.out.println("response="+response);
//			System.out.println(((Map<String, Object>) up.get("url")).get("urlcode"));
		}
		return response;
	}

}
