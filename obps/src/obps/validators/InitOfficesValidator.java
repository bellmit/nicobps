package obps.validators;

import java.util.Base64;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obps.daos.DaoEnclosureManagementInterface;
@Service
public class InitOfficesValidator implements InitOfficesValidatorInterface{
	@Autowired
	private DaoEnclosureManagementInterface DEMI;
	@Override
	public String validateInitOffices(Map<String, Object> param) {
		String response = "";
		Pattern p = Pattern.compile("[^A-Za-z_ ]");
		String officename1="",officename2="",officename3="",officeshortname="",signatoryname="",signatorydesignation="",smsusername="",emailidpassword="",smspassword="",stateid="",tenantid="";
		if(param.get("officename1")!=null)
			officename1=((String) param.get("officename1")).trim();
		Matcher of1 = p.matcher(officename1);
		boolean b1 = of1.find();
		if(b1)
			response= "officename1";
		if(param.get("officename2")!=null)
			officename2=((String) param.get("officename2")).trim();
		Matcher of2 = p.matcher(officename2);
		b1 = of2.find();
		if(b1)
			response = "officename2";
		if(param.get("officename3")!=null){
			officename3=((String) param.get("officename3")).trim();
			Matcher of3 = p.matcher(officename3);
			b1 = of3.find();
			if(b1)
				response = "officename3";
		}
		
		if(param.get("signatoryname")!=null){
			signatoryname=((String) param.get("signatoryname")).trim();
			Matcher offsignatory = p.matcher(signatoryname);
			b1 = offsignatory.find();
			if(b1)
				response = "offsignatory";
		}
		if(param.get("signatorydesignation")!=null){
			signatorydesignation=((String) param.get("signatorydesignation")).trim();
			Matcher offshortdes = p.matcher(signatorydesignation);
			b1 = offshortdes.find();
			if(b1)
				response = "officeshortdes";
		}
		if(param.get("officeshortname")!=null){
			officeshortname=((String) param.get("officeshortname")).trim();
			Matcher offshort = p.matcher(officeshortname);
			b1 = offshort.find();
			if(b1)
				response = "offshort";
		}
				
		if(param.get("smsusername")!=null){
			smsusername=((String) param.get("smsusername")).trim();
			Matcher sms = p.matcher(smsusername);
			b1 = sms.find();
			if(b1)
				response = "sms";
		}
		
		if(param.get("emailid")=="")
			System.out.println("");
		else if(param.get("emailid")!=null){
			String emailid=((String) param.get("emailid")).trim();
		String regex = "^(.+)@(.+)$";
		final Pattern p2 = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	    Matcher matcher = p2.matcher(emailid);
		Boolean b= matcher.find();
		if(!b)
		    response="emailidnotcorrect";
		if(emailid.length()>255)
		    	 response="9";
		}
		if(param.get("emailidpassword")!=null){
			emailidpassword=((String) param.get("emailidpassword")).trim();
		}
		if(param.get("smspassword")!=null){
			smspassword=((String) param.get("smspassword")).trim();
		}
		if(param.get("stateid")!=null){
			stateid=((String) param.get("stateid")).trim();
			Matcher m = p.matcher(stateid);
			b1 = m.find();
			if(b1)
				response = "stateid";
			if(stateid.length()>25)
				response = "stateidlength";
		}
		if(param.get("tenantid")!=null){
			tenantid=((String) param.get("tenantid")).trim();
			Matcher m = p.matcher(tenantid);
			b1 = m.find();
			if(b1)
				response = "tenantid";
			if(tenantid.length()>25)
				response = "tenantidlength";
		}
		
		
		 if(officename1.length()>255)
	    	 response="1";
	     if(officename2.length()>255)
	    	 response="2";
	     if(officename3.length()>255)
	    	 response="3";
	     if(officeshortname.length()>50)
	    	 response="4";
	     if(signatoryname.length()>255)
	    	 response="5";
	     if(signatorydesignation.length()>50)
	    	 response="6";
	     if(smsusername.length()>25)
	    	 response="7";
	     if(smspassword.length()>100)
	    	 response="8";
	     
	     if(emailidpassword.length()>255)
	    	 response="10";
	     System.out.println(param.get("extension"));
	     String ext="";
	     Integer size=0;
	     System.out.println(param.get("extension"));
	     System.out.println(param);
	     if(param.get("logo")!=null) {
	    	  if(param.get("extension")!=null||param.get("extension")!="")
	    	  {	
	    		  ext=((String) param.get("extension")).trim().toLowerCase();
	    		  System.out.println(ext);
	    		  
	    		 if(ext.equalsIgnoreCase("jpg"))
	    			 System.out.println(ext);
	    		 else if(ext.equalsIgnoreCase("jpeg"))
	    			 System.out.println(ext);
	    		 else if(ext.equalsIgnoreCase("png"))
	    			 System.out.println(ext);
	    		 else
	    			 response = "filetypeerror";
	       	  }
	    	  if(param.get("filesize")!=null||param.get("filesize")!="") {
	    		  size=(Integer) param.get("filesize");
	    		  float res = size/1024;
	    		  res=res/1024;
	    		  System.out.println(res);
	    		  if(res>5)
	    			  response = "filesizeerror";
	    	  }
	     }
		return response;
	}

}
