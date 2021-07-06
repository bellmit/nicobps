package obps.validators;

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
		String enclname=((String) param.get("enclosurename")).trim();
		if(param.get("enclosuredescription")!=null)
			encldesc=((String) param.get("enclosuredescription")).trim();
		
		 Pattern p = Pattern.compile("[^A-Za-z_ ]");
	     Matcher m1 = p.matcher(enclname);
	     Matcher m2 = p.matcher(encldesc);
	     boolean b1 = m1.find();
	     boolean b2 = m2.find();
	     if (b1)
	         response="m1";
	      
//	     if (b2)
//	         response="m2";
	     if(enclname.length()>50)
	    	 response="50";
	     if(encldesc.length()>255)
	    	 response="255";
	     
	    	 
		
		return response;
		
	}

}
