package obps.validators;
 
public interface StakeHolderValidatorInterface {
	public String validateStackHolder(Integer officecode, String applicationcode, Integer usercode,
			Integer toprocesscode, String remarks);
}
