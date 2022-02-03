package obps.validators;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import obps.models.AppEnclosures;
import obps.models.BpaEnclosures;
import obps.models.LicenseesEnclosures;
import obps.util.common.Patterns;
import obps.util.common.UtilFile;

@Component
public class ValidateBpaEnclosures implements Validator 
{

    @Autowired
    MessageSource messages;
    @Resource
	private Environment environment;

    @Override
    public boolean supports(Class<?> type) {
        return LicenseesEnclosures.class.isAssignableFrom(type);
    }


    @Override  
    public void validate(Object o, Errors errors) 
    {
        String validate_captcha = messages.getMessage("validate.captcha", null, "invalid", null);
        String validate_enclosures = messages.getMessage("validate.enclosures", null, "invalid", null);
        String validate_enclosures_count = messages.getMessage("validate.enclosures_count", null, "invalid", null);
        String validate_enclosures_size = messages.getMessage("validate.enclosures_size", null, "invalid", null);
        
        BpaEnclosures bpaenclosures = null;
        if (o instanceof BpaEnclosures) {
        	bpaenclosures = (BpaEnclosures) o;
        }
        
        boolean errorFlag = false;
        if (bpaenclosures.getAppenclosures()!= null && bpaenclosures.getAppenclosures().size()>0) 
        {
            if(environment.getProperty("captcha_enabled").equals("true")) {
	        	if (bpaenclosures.getSessioncaptcha() != null && bpaenclosures.getUserresponsecaptcha().equalsIgnoreCase(bpaenclosures.getSessioncaptcha())) {
	                errorFlag = false;
	            } else {
	                errorFlag = true;
	            }
            }else {
            	errorFlag = false;
            }
        }
        if (errorFlag) {
            errors.rejectValue("userresponsecaptcha", null, validate_captcha);
            return;
        }             
        
        //=========================================================================                
        
        CommonsMultipartFile commonsMultipartFile = null;
        String contenttype=null;         
        int index =0;
        int count =0;        
        for(AppEnclosures U : bpaenclosures.getAppenclosures())
        {                       
            if(U.getFileContent()!=null)
            {
                commonsMultipartFile = U.getFileContent();
                contenttype=commonsMultipartFile.getContentType();                
                //if(Patterns.PatternCompileMatche(Patterns.PATTERN_DOCUMENT_CONTANT,contenttype)){
                if(Patterns.PatternCompileMatche(Patterns.PATTERN_DOCUMENT_CONTANT,contenttype) && UtilFile.isValidFile(commonsMultipartFile.getBytes(), Patterns.PATTERN_DOCUMENT_CONTANT) ){                
                                
                                      
                }else{
                    errors.rejectValue("appenclosures["+index+"].fileContent", null, validate_enclosures);
                    return;                
                }	
                System.out.println("commonsMultipartFile.getSize() : "+commonsMultipartFile.getSize());
                if(commonsMultipartFile.getSize()>Long.valueOf("1048576")){                    
                    errors.rejectValue("appenclosures["+index+"].fileContent", null, validate_enclosures_size);
                    return;                                                                 
                }                
                count++;
            }
            index++;
        }                                      
        
        if (count==0) {
            errors.rejectValue("userresponsecaptcha", null, validate_enclosures_count);
            return;
        }           

    }
}
