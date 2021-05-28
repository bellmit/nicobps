package obps.controllers;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import obps.reports.ReportService;
import obps.services.payment.ServicePaymentCommon;

@Controller
public class ControllerReceipt {
	@Autowired
	private ServicePaymentCommon serviceCommon;

	@Autowired
	private ReportService reportservice;

	@PostMapping("/generateReceipt.htm")
	public void generateReceiptPost(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> params) {
		System.out.println("----Payment receipt Post---");

		try {
			JasperPrint jasperPrint = null;
			String fileName = "PaymentReceipt";
			String outputfilename = "receiptpayment";

			Integer transactioncode = Integer.valueOf(params.get("transactioncode"));

			Map<String, String> reportParams = new HashMap<String, String>();

			List<Map<String, Object>> transList = serviceCommon.getTransactionList(transactioncode);

			int listsize = transList.size();
			System.out.println("list size :: " + listsize);

			String responseparams = transList.get(0).get("responseparameters").toString();

			System.out.println("resp ::" + responseparams);

			responseparams = responseparams.replace("%7C", "|");
			responseparams = responseparams.replace("%3A", ":");
			String[] resp = responseparams.split("\\|");

			System.out.println("length :: " + resp.length);

			for (int i = 0; i < resp.length; i++)
				System.out.println(i + " : " + resp[i]);

			reportParams.put("applicationcode", transList.get(0).get("applicationcode").toString());
			reportParams.put("transactioncode", transList.get(0).get("transactioncode").toString());
			reportParams.put("feetype", transList.get(0).get("feetypedescription").toString());
			reportParams.put("transactionno", resp[2]);
			reportParams.put("date", resp[13]);
			reportParams.put("paymentgateway", transList.get(0).get("paymentmodedescription").toString());
			reportParams.put("payername", transList.get(0).get("fullname").toString());
			reportParams.put("paymode", transList.get(0).get("mode").toString());
			reportParams.put("amount", resp[4]);
			reportParams.put("payresponse", getmessage(resp[14]));

			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition",
					String.format("attachment; filename=\"" + outputfilename + ".pdf\""));

			OutputStream out = response.getOutputStream();
			jasperPrint = reportservice.exportPdfFile(request, fileName, reportParams);

			JasperExportManager.exportReportToPdfStream(jasperPrint, out);

			out.flush();
			out.close();

		} catch (Exception e) {
			System.out.println("Exception in generateReceiptPost :" + e);
			e.printStackTrace();
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
