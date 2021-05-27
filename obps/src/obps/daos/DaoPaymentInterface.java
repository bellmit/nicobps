package obps.daos;

import java.util.List;
import java.util.Map;

public interface DaoPaymentInterface {
	public boolean InitiatePayment(Map<String, Object> param);

	public boolean UpdatePayment(String paymentstatus, String responseparameters, Integer transactioncode,
			Integer usercode);

	public boolean SavePaymentMap(Map<String, Object> param);

	public Map<String, Object> getFeeDetails(Integer feecode);

	public List<Map<String, Object>> getPaymentStatus(String applicationcode, Integer feecode);

	public int getApplicationCount(String applicationcode);

	public Map<String, Object> getTransaction(Integer transactioncode);

}
