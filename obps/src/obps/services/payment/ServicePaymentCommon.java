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
		Map<String, Object> feeDetails=daoPaymentInterface.getFeeDetails(feecode);
		return feeDetails;
	}

}
