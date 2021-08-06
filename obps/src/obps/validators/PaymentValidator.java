package obps.validators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import obps.services.payment.ServicePaymentCommon;
import obps.util.application.ServiceUtilInterface;
import obps.util.common.Patterns;

@Component
public class PaymentValidator {
	 public static final String PATTERN_AMOUNT = "[0-9]+(\\.[0-9][0-9]?)?";

	@Autowired
	private ServicePaymentCommon serviceCommon;

	@Autowired
	private ServiceUtilInterface serviceUtilInterface;

	public String validate_paymentparams(Map<String, String> params) {

		String applicationcode = params.get("applicationcode");
		String feecode = params.get("feecode");
		String modulecode = params.get("modulecode");
		String usercode = params.get("usercode");
		String feeamount = params.get("feeamount");
		String toprocesscode = params.get("toprocesscode");

		if (applicationcode != null) { // applicationcode
			if (!Patterns.PatternCompileMatche(Patterns.PATTERN_ALPHA_NUMERIC, applicationcode.trim())) {
				return "application code is invalid";
			} else if (applicationcode.length() > 20) {
				return "application code  size error";
			}
		} else {
			return "application code is NULL";
		}

		if (feecode != null) { // feecode
			if (!Patterns.PatternCompileMatche(Patterns.PATTERN_POSITIVEINTEGER, feecode.trim())) {
				return "fee code is invalid";
			}
		} else {
			return "fee code is NULL";
		}

		if (modulecode != null) { // modulecode
			if (!Patterns.PatternCompileMatche(Patterns.PATTERN_POSITIVEINTEGER, modulecode.trim())) {
				return "module code is invalid";
			}
		} else {
			return "module  code is NULL";
		}

		if (usercode != null) { // usercode
			if (!Patterns.PatternCompileMatche(Patterns.PATTERN_POSITIVEINTEGER, usercode.trim())) {
				return "user code is invalid";
			}
		} else {
			return "user  code is NULL";
		}

		if (feeamount != null) { // fee amount
			if (!Patterns.PatternCompileMatche(PATTERN_AMOUNT, feeamount.trim())) {
				return "fee  amount is invalid";
			}
		} else {
			return "fee amount is NULL";
		}

		if (toprocesscode != null) { // to process
			if (!Patterns.PatternCompileMatche(Patterns.PATTERN_POSITIVEINTEGER, toprocesscode.trim())) {
				return "fee  amount is invalid";
			}
		} else {
			return "fee amount is NULL";
		}

		return "1";

	}

	public Map<String, String> validate_params(Map<String, String> params) {

		Map<String, String> response = new HashMap<String, String>();
		String paramstats = "1";

		if (validate_paymentparams(params) != "1") {
			paramstats = validate_paymentparams(params);

		} else {
			response = validate_paymentvalues(params.get("applicationcode").trim(),
					Integer.valueOf(params.get("feecode").trim()), Integer.valueOf(params.get("usercode").trim()));
		}
		response.put("paramstats", paramstats);
		return response;
	}

	public Map<String, String> validate_paymentvalues(String applicationcode, Integer feecode, Integer usercode) {
		String appExist = "";
		String appTransMap = "";
		String payStatus = "";
		String feeAmount = "";
		String userDet = "";
		Map<String, String> statusMap = new HashMap<String, String>();

		if (applicationcode != null && feecode != null) {
			int appCount = serviceCommon.getApplicationCount(applicationcode);
			 
			if (appCount > 0) {
				appExist = "EXIST";
			} else {
				appExist = "NOTEXIST";
			}

			List<Map<String, Object>> payList = serviceCommon.getPaymentStatus(applicationcode, feecode);

			int payListSize = payList.size();
			System.out.println("Pay list size : " + payListSize);

			if (payListSize > 0) {

				appTransMap = "NOTEMPTY";

				String paymentStatus = payList.get(0).get("paymentstatus").toString();
				System.out.println(" Pay Status :: " + paymentStatus);

				if (paymentStatus.equals("S")) {
					payStatus = "PAID";

				} else {
					payStatus = "NOTPAID";

				}

			} else {
				appTransMap = "EMPTY";
				payStatus = "NOTPAID";
			}
			Map<String, Object> fee = serviceCommon.getAmount(feecode);
			if (fee == null) {
				feeAmount = "NOTAVAILABLE";
			} else {
				feeAmount = "AVAILABLE";
			}

			List<Map<String, Object>> userdetails = serviceUtilInterface.getLicensee(usercode);
			if (userdetails != null && !userdetails.isEmpty()) {
				userDet = "AVAILABLE";
			} else {
				userDet = "NOTAVAILABLE";
			}

			statusMap.put("appexist", appExist.trim());
			statusMap.put("apptransmap", appTransMap.trim());
			statusMap.put("paystatus", payStatus.trim());
			statusMap.put("feeamount", feeAmount.trim());
			statusMap.put("userdet", userDet.trim());

			

		} else {
			statusMap.put("error", "NULL");
		}

		return statusMap;

	}

}
