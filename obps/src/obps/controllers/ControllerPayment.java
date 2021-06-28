package obps.controllers;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import obps.daos.DaoPaymentInterface;
import obps.services.payment.ServiceBilldeskGateway;
import obps.services.payment.ServicePaymentCommon;
import obps.util.application.ServiceUtilInterface;

@Controller
public class ControllerPayment {
	@Autowired
	ServiceBilldeskGateway billdeskgateway;
	@Autowired
	private DaoPaymentInterface daoPaymentInterface;

	@Autowired
	private ServicePaymentCommon serviceCommon;
	@Autowired
	private ServiceUtilInterface serviceUtilInterface;

	@GetMapping(value = "/paymentTest.htm")
	public String paymentConfirmation_Get() {

		return "payment/paymentTest";
	}

	@RequestMapping(value = "/paymentconfirmation.htm", method = { RequestMethod.GET, RequestMethod.POST })
	public String paymentConfirmation_Post(@RequestParam Map<String, String> params, Model model) {

		// need to get amount based on feecode
		Map<String, Object> feeDetails = serviceCommon.getAmount(Integer.parseInt(params.get("feecode")));
		System.out.println("feeDetails--" + feeDetails);
		// -------------------------------------------
		Map<String, String> statusMap = validate_payparams(params.get("applicationcode").toString(), Integer.valueOf(params.get("feecode")));
		model.addAttribute("applicationcode", params.get("applicationcode"));
		model.addAttribute("feecode", params.get("feecode"));
		model.addAttribute("modulecode", params.get("modulecode"));
		model.addAttribute("feetypedescription", feeDetails.get("feetypedescription"));
		model.addAttribute("usercode", params.get("usercode"));
		model.addAttribute("feeamount", params.get("feeamount"));
		model.addAttribute("toprocesscode", params.get("toprocesscode"));
		
		List<Map<String, Object>> userdetails = serviceUtilInterface.getLicensee(Integer.parseInt(params.get("usercode").trim()));
		model.addAttribute("processby", userdetails.get(0).get("applicantsname"));

//		}

		model.addAttribute("status", statusMap);

		return "payment/paymentconfirmation";
	}

	@PostMapping(value = "/paymentinitialized.htm")
	public ResponseEntity<Void> redirect(HttpServletRequest request, @RequestParam Map<String, String> params) {

		String usercode = params.get("usercode");
		String feecode = params.get("feecode");
		String toprocesscode = params.get("toprocesscode");
		String modulecode = params.get("modulecode");
		Integer amount = Integer.parseInt(params.get("feeamount"));
		String applicationcode = params.get("applicationcode");
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(billdeskgateway.generateRedirectURI(usercode, amount, feecode, applicationcode, modulecode, toprocesscode).toString()))
				.build();
	}

	@GetMapping(value = "/CommonPaymentResponse.htm")
	public String CommonPaymentResponse1_Get(@RequestParam Map<String, String> params, Model model) {
		System.out.println("no resubmit:" + params);

		model.addAttribute("status", params.get("status"));
		model.addAttribute("transactioncode", params.get("transactioncode"));
		model.addAttribute("amount", params.get("amount"));
		model.addAttribute("message", params.get("message"));
		model.addAttribute("processby", params.get("processby"));
		model.addAttribute("transactiondate", params.get("transactiondate"));
		return "payment/CommonPaymentResponse";
	}

	@PostMapping(value = "/CommonPaymentResponse.htm")
	public String CommonPayment(@RequestParam Map<String, String> params, Model model) {
		Integer transactioncode = serviceCommon.saveTransaction(params);
		System.out.println("test for resubmission");
		if (transactioncode != 0) {
			serviceUtilInterface.updateApplicationflowremarks(params.get("applicationcode"), Integer.parseInt(params.get("modulecode").trim()), Integer.parseInt(params.get("toprocesscode").trim()),
					Integer.parseInt(params.get("usercode").trim()), null, "Payment Complete");
			List<Map<String, Object>> userdetails = serviceUtilInterface.getLicensee(Integer.parseInt(params.get("usercode").trim()));
			model.addAttribute("processby", userdetails.get(0).get("applicantsname"));
			model.addAttribute("status", "0300");
			model.addAttribute("transactioncode", transactioncode);
			model.addAttribute("amount", "0");
			model.addAttribute("message", "Application submitted succesfully");
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			System.out.println(formatter.format(date));
			model.addAttribute("transactiondate", formatter.format(date));

		} else {
			model.addAttribute("status", "NA");
			model.addAttribute("transactioncode", "NA");
			model.addAttribute("amount", "NA");
			model.addAttribute("message", "Application submitted Failed");

		}

		return "redirect:/CommonPaymentResponse.htm";
	}

	@PostMapping(path = "/BilldeskResponse.htm", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public String paymentResponse(HttpServletRequest request, @RequestParam Map<String, String> params, Model model) {

		String message = null;
		String response = params.get("msg");
		// --------below is a test string response for success payment
		// overwrite the 2nd pipe value to current transaction code..
		// comment out the hash validation to see result
//		String response = "SPSCSKM|13|VHMP9856639406|314858|1.00|HMP|459200|03|INR|VDDIRECT|05-NA|NA|00000005.00|24-03-2021+13:30:58|0300|NA|jk.jammu|DCR52021A50C20101011|2|4|5|3|NA|NA|PGS10001-Success|F3E1804EDFC2A60BF3DD135D4CC7C71DD38E6ECF67645A1CFD8AA7606151B0D0";
		// ----------------------------------
		response = response.replace("%7C", "|");
		response = response.replace("%3A", ":");
		String[] words = response.toString().split("\\|");
		String paymentstatuscode = words[14];
		String usercode = words[21];
		model.addAttribute("status", words[14]);
		try {
			// checksum
			int lastIndexOf = response.lastIndexOf("|");
			String msg = response.substring(0, lastIndexOf);
			String checksum = response.substring(lastIndexOf + 1, response.length());
			Boolean validatehash = billdeskgateway.checkHmac(msg, checksum);
			System.out.println("validatehash:" + validatehash);
			if (validatehash) {
				switch (paymentstatuscode) {
				case "0300":
					List<Map<String, Object>> userdetails = serviceUtilInterface.getLicensee(Integer.parseInt(usercode));
					message = "Payment Successful. ";
					model.addAttribute("payer", userdetails.get(0).get("applicantsname"));
					model.addAttribute("transactioncode", words[1]);
					model.addAttribute("billdeskreferenceNo", words[2]);
					model.addAttribute("bankreferenceno", words[3]);
					model.addAttribute("amount", words[4]);
					model.addAttribute("transactiondate", words[13]);
					model.addAttribute("message", message);
					// ----------------UpdateTransaction
					daoPaymentInterface.UpdatePayment("S", response, Integer.parseInt(words[1]), Integer.parseInt(usercode));
					// ----------------InsertApplicationFlowRemark
					String applicationcode = words[17];
					String toprocesscode = words[19];
					String modulecode = words[18];
					System.out.println(applicationcode);
					serviceUtilInterface.updateApplicationflowremarks(applicationcode, Integer.parseInt(modulecode), Integer.parseInt(toprocesscode), Integer.parseInt(usercode), null,
							"Payment Complete");
					break;
				case "0399":
					message = "Payment Unsucessful - Invalid Authentication in Bank / Cancelled By User.";
					daoPaymentInterface.UpdatePayment("A", response, Integer.parseInt(words[1]), Integer.parseInt(usercode));
					break;
				case "NA":
					message = "Invalid Input in Payment Request. Please Contact Admin.";
					daoPaymentInterface.UpdatePayment("F", response, Integer.parseInt(words[1]), Integer.parseInt(usercode));
					break;
				case "0002":
					message = "Billdesk Waiting Response From Bank.";
					daoPaymentInterface.UpdatePayment("F", response, Integer.parseInt(words[1]), Integer.parseInt(usercode));
					break;
				case "0001":
					message = "An Error has occured at billdesk";
					daoPaymentInterface.UpdatePayment("F", response, Integer.parseInt(words[1]), Integer.parseInt(usercode));
					break;
				default:
					message = "An error occured";
					daoPaymentInterface.UpdatePayment("F", response, Integer.parseInt(words[1]), Integer.parseInt(usercode));
					break;
				}
				model.addAttribute("message", message);
			} else {
				model.addAttribute("message", "Invalid CheckSum");
			}
		} catch (Exception e) {
			System.out.println("Error at PaymentResponse---------" + e);
		}

		return "payment/BilldeskResponse";
	}

	public Map<String, String> validate_payparams(String applicationcode, Integer feecode) {
		String appExist = "";
		String appTransMap = "";
		String payStatus = "";
		String feeAmount = "";

		int appCount = serviceCommon.getApplicationCount(applicationcode);
		System.out.println(" app count : " + appCount);
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

		Map<String, String> statusMap = new HashMap<String, String>();

		statusMap.put("appexist", appExist.trim());
		statusMap.put("apptransmap", appTransMap.trim());
		statusMap.put("paystatus", payStatus.trim());
		statusMap.put("feeamount", feeAmount.trim());

		System.out.println("json obj ::" + statusMap.toString());
		return statusMap;

	}

}
