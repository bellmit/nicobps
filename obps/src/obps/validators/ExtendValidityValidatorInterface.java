package obps.validators;

public interface ExtendValidityValidatorInterface {
	public boolean validateUsercode(String usercode);
	public boolean validateOfficecode(String officecode);
	public boolean validatedate(String date);
	public boolean validateExtendedtoDate(String validtodate,String extendedtodate,String maxdate);
	
}
