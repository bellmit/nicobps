package obps.services.payment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
	@Autowired
	private ServiceUtilInterface serviceUtilInterface;

	public Integer saveTransaction(Map<String, String> params) {
		Integer resp = 0;
		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			Integer transactioncode = serviceUtilInterface.getMax("nicobps", "transactions", "transactioncode");
			transactioncode++;
			Map<String, Object> transaction = new HashMap<String, Object>();
			transaction.put("transactioncode", transactioncode);
			transaction.put("usercode", params.get("usercode"));
			transaction.put("feecode", params.get("feecode"));
			transaction.put("paymentmodecode", 1);
			transaction.put("amount", Integer.parseInt(params.get("feeamount")) );
			transaction.put("paymentstatus", "S");
			transaction.put("sentparameters", "NA");
			transaction.put("entrydate", dateFormat.format(date));
			Boolean resp1 = daoPaymentInterface.InitiatePayment(transaction);
			Map<String, Object> paymentappl = new HashMap<String, Object>();
			paymentappl.put("transactioncode", transactioncode);
			paymentappl.put("applicationcode", params.get("applicationcode"));
			paymentappl.put("entrydate", dateFormat.format(date));
			// ===========save to transaction======================
			Boolean resp2 = daoPaymentInterface.SavePaymentMap(paymentappl);

			if (resp1 && resp2) {
				resp = transactioncode;
			} else {

				resp = 0;
			}
		} catch (Exception e) {
			System.out.println("error in ServicePaymentCommon.saveTransaction---" + e);
			resp = 0;
		}

		return resp;
	}

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

	public Map<String, Object> getTransaction(Integer transactioncode) {
		return daoPaymentInterface.getTransaction(transactioncode);
	}

	public List<Map<String, Object>> getTransactionList(Integer transactioncode) {
		return daoPaymentInterface.getTransactionList(transactioncode);
	}

}
