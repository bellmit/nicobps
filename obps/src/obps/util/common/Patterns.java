
package obps.util.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.filefilter.FalseFileFilter;


public class Patterns {
    
    public static final String PATTERN_BASIC_RESTRICTED_CHARACTER = "^([^<]|\\<[^a-zA-Z])*[<]?$";
    public static final String PATTERN_ALL_RESTRICTED_CHARACTER = "^([^\\<\\>\\\"\\'\\%\\&]*)$";
//    public static final String PATTERN_ALL_RESTRICTED_CHARACTER = "^([^\\<\\>\\\"\\'\\%\\;\\)\\(\\&\\+]*)$";
    public static final String PATTERN_EMAIL = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PATTERN_EMAIL_OPT = "^([_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))?$";
    
    public static final String PATTERN_NAME = "^[a-zA-z\\.\\s]*$"; 
    //public static final String PATTERN_NAME_SIZE = "^[a-zA-Z\\.\\s]{5,20}$"; 
    //public static final String PATTERN_NAME = "^[a-zA-Z\\. ]*$";    
            
    public static final String PATTERN_ALPHA_NUMERIC = "[a-zA-Z0-9]*";
    public static final String PATTERN_ALLOW_ALL = "^[a-zA-Z0-9\\s\\d\\(\\)\\{\\}\\[\\]\\/\\-@#$%&!_=.'`~]*$";
    
    public static final String PATTERN_YEAR = "^([1-9]){1}([0-9]){3}$";
    public static final String PATTERN_MONTH = "^(0[1-9]|1[0-2])$";
    public static final String PATTERN_DAY = "^(0[1-9]|[1-2][0-9]|3[0-1])$";
    public static final String PATTERN_DATE = "^(0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-([1-9]){1}([0-9]){3}$";
    //public static final String PATTERN_DATE = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$";
    
    public static final String PATTERN_NUMERIC = "^\\d+$";
    public static final String PATTERN_NUMERIC_OPT = "^\\d*$";
    public static final String PATTERN_PERCENTAGE2D = "^\\d+(\\.\\d{1,2})?$";
    public static final String PATTERN_PERCENTAGE2D_OPT = "(^\\d+(\\.\\d{1,2}))?$";
    //public static final String PATTERN_PERCENTAGE2D = "^[0-9]+(\\.[0-9][0-9]?)?$";
    public static final String PATTERN_POSITIVEINTEGER = "^(?!^0)\\d{1,9}$";
    public static final String PATTERN_MONEY = "[1-9][0-9]{0,7}(\\.?([0-9]){2})*";
    public static final String PATTERN_MOBILE = "([1-9]){1}([0-9]){9}";
    public static final String PATTERN_MOBILE_OPT = "(([1-9]){1}([0-9]){9})?";
    public static final String PATTERN_LANDLINE_OPT = "^(([0-9]){10}[0-9]?[0-9]?[0-9]?)?$";
    public static final String PATTERN_LANDLINE = "([0-9]){10}[0-9]?[0-9]?[0-9]?";
    public static final String PATTERN_PINCODE = "([1-9]){1}([0-9]){5}$";
    
    public static final String PATTERN_ROLE = "^(A|U)$";
    public static final String PATTERN_GENDER = "^(M|F|X)$";
    public static final String PATTERN_GENDER_OPT = "^(M|F|X|-)$";
    public static final String PATTERN_MARITALSTATUS = "^(M|U)$";
    public static final String PATTERN_YESNO = "^(Y|N)$";
    public static final String PATTERN_SCSTOBCGEN = "^(SC|ST|OBC|GEN)$";
    public static final String PATTERN_DIVISION = "^(1st|2nd|3rd)$";
    public static final String PATTERN_TRUEFALSE = "^(true|false)$";    
    public static final String PATTERN_RELATION = "^(F|M)$";    
    
    public static final String PATTERN_IMAGE_CONTANT = "^(image/jpeg|image/jpg|image/pjpeg|image/png|image/x-png)$";
    public static final String PATTERN_DOCUMENT_CONTANT = "^(image/jpeg|image/jpg|image/pjpeg|image/png|image/x-png|application/pdf)$";
    public static final String PATTERN_PDF_CONTANT = "^(application/pdf)$";
    
    public static boolean PatternMatche(String pattern, String inputStr) {
        return Pattern.matches(pattern, inputStr);
    }

    public static boolean PatternCompile(String pattern, String inputStr) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(inputStr);
        return m.find();
    }    
    
    public static boolean PatternCompileMatche(String pattern, String inputStr) {
        boolean success = false;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(inputStr);
        if(m.find()==true && Pattern.matches(pattern, inputStr)==true){
            success= true;
        }
        return success;
    }  
    
    
}
/*
- A possible regular expression, which will deny the basic cross site scripting variants might be: 
^([^<]|\<[^a-zA-Z])*[<]?$
- A generic regular expression, which will deny all of the aforementioned characters might be: 
^([^\<\>\"\'\%\;\)\(\&\+]*)$
*/