package obps.validators;

import java.util.Map;

import org.springframework.stereotype.Component;

import obps.models.FeeTypes;
import obps.models.Filetypes;
@Component
public class InitFiletypeValidator {
	
	public String validateInitFileTypes(Map<String, Object> param) {
		String response = "";
		System.out.println(param);

		String filetypedescription="";
		if(param.get("filetypedescription")!=null) {
			filetypedescription=param.get("filetypedescription").toString();
			if(filetypedescription.length()>255) {
				System.out.println(filetypedescription.length()>255);
				response= "File Type Description Cannot Exceed More Than 255 Characters";
				return response;
			}
		}
		else {
			response= "File Type Description Cannot be Null";
			return response;
		}
		return response;
	}
	
	public String validateInitFileTypes(Filetypes param) { 
		String response = "";
		System.out.println(param);

		
		String feetypedescription="";
		if(param.getFiletypedescription()!=null) {
			feetypedescription=param.getFiletypedescription().toString();
			if(feetypedescription.length()>255) {
				System.out.println(feetypedescription.length()>255);
				response= "File Type Description Cannot Exceed More Than 255 Characters";
				return response;
			}
		}
		else {
			response= "File Type Description Cannot be Null";
			return response;
		}
		return response;
	}
}
