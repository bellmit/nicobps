package obps.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import obps.reports.ReportGenerate;
import obps.services.payment.ServicePaymentCommon;

@Controller
public class ControllerReceipt {
	@Autowired
	private ServicePaymentCommon serviceCommon;

	@Autowired
	private ReportGenerate reportgenerate;

	@GetMapping("/generateReceipt.htm")
	@ResponseBody
	public void generateReceipt(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> params) {
		System.out.println("----Payment receipt---");
		try {

//			String applicationcode = params.get("applicationcode");
			Integer transactioncode = Integer.valueOf(params.get("transactioncode"));

			System.out.println("trans code " + transactioncode);

			Map<String, Object> transList = serviceCommon.getTransaction(transactioncode);

			int listsize = transList.size();

			String responseparams = transList.get("responseparameters").toString();

			System.out.println("resp ::" + responseparams);

			responseparams = responseparams.replace("%7C", "|");
			responseparams = responseparams.replace("%3A", ":");
			String[] resp = responseparams.split("\\|");

			System.out.println("length :: " + resp.length);

			for (int i = 0; i < resp.length; i++)
				System.out.println(i + " : " + resp[i]);

			String fileName = "PaymentReceipt";
			String outputfilename = "receiptpayment";

			Map<String, String> reportParams = new HashMap<String, String>();

			reportParams.put("applicationcode", transList.get("applicationcode").toString());
			reportParams.put("transactioncode", transList.get("transactioncode").toString());
			reportParams.put("feetype", transList.get("feetypedescription").toString());
			reportParams.put("transactionno", resp[2]);
			reportParams.put("date", resp[13]);
			reportParams.put("paymentgateway", transList.get("paymentmodedescription").toString());
			reportParams.put("payername", transList.get("fullname").toString());
			reportParams.put("paymode", transList.get("mode").toString());
			reportParams.put("amount", resp[4]);
			reportParams.put("payresponse", getmessage(resp[14]));


			reportgenerate.generateReport(request, response, fileName, reportParams, outputfilename);

		} catch (Exception e) {
			System.out.println("exception :: " + e);
		}

	}

	public String getmessage(String paymentstatuscode) {
		String message = "";
		switch (paymentstatuscode) {
		case "0300":
			message = "Payment Successful. ";

			break;
		case "0399":
			message = "Payment Unsucessful - Invalid Authentication in Bank / Cancelled By User.";

			break;
		case "NA":
			message = "Invalid Input in Payment Request. Please Contact Admin.";

			break;
		case "0002":
			message = "Billdesk Waiting Response From Bank.";

		case "0001":
			message = "An Error has occured at billdesk";

			break;
		default:
			message = "An error occured";

			break;
		}

		return message;

	}

}
