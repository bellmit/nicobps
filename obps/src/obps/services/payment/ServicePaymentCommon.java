package obps.services.payment;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obps.daos.DaoPaymentInterface;
import obps.util.application.ServiceUtilInterface;

@Service
public class ServicePaymentCommon {
	@Autowired
	DaoPaymentInterface daoPaymentInterface;

	public Map<String, Object> getAmount(Integer feecode) {
		Map<String, Object> feeDetails = daoPaymentInterface.getFeeDetails(feecode);
		return feeDetails;
	}

	public List<Map<String, Object>> getPaymentStatus(String applicationcode, Integer feecode) {

		return daoPaymentInterface.getPaymentStatus(applicationcode, feecode);

	}

	public int getApplicationCount(String applicationcode) {
		return daoPaymentInterface.getApplicationCount(applicationcode);
	}

	public Map<String, Object> getTransaction( Integer transactioncode) {
		return daoPaymentInterface.getTransaction(transactioncode);
	}

}
