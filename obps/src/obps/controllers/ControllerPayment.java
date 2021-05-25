package obps.controllers;

import java.net.URI;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import obps.services.payment.ServiceBilldeskGateway;

@Controller
public class ControllerPayment {
	@Autowired
	ServiceBilldeskGateway billdeskgateway;

	@GetMapping(value = "/paymentconfirmation.htm")
	public String paymentConfirmation_Get(@RequestParam Map<String, String> params,Model model) {
		System.out.println(" --- Payment Confirmation Get--");
		System.out.println("params : " + params);
		model.addAttribute("applicationcode","1");
		model.addAttribute("feecode",  "1");
		model.addAttribute("feeamount", "100");
		return "payment/paymentconfirmation";
	}
	
	@PostMapping(value = "/paymentconfirmation.htm")
	public String paymentConfirmation_Post(@RequestParam Map<String, String> params,Model model) {
		System.out.println(" --- Payment Confirmation Post--");
		System.out.println("params : " + params);
		model.addAttribute("applicationcode", params.get("applicationcode"));
		model.addAttribute("feecode",  params.get("feecode"));
		model.addAttribute("feeamount", params.get("feeamount"));
		return "payment/paymentconfirmation";
	}
	@PostMapping(value = "/paymentinitialized.htm")
	public ResponseEntity<Void> redirect(@RequestParam Map<String, String> input) {

		System.out.println("paymentinitialized.htm-------POST");
		System.out.println(input);
		return ResponseEntity.status(HttpStatus.FOUND)
				.location(URI.create(billdeskgateway.generateRedirectURI().toString())).build();
	}

	@PostMapping(path = "/BilldeskResponse.htm", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public String paymentResponse(HttpServletRequest req, @RequestParam Map<String, String> params) {
		System.out.println("/billDesk/paymentResponse.htm");
		System.out.println("Response :: -----------------" + params.get("msg"));

		String response = params.get("msg");
		response = response.replace("%7C", "|");
		response = response.replace("%3A", ":");
		String[] words = response.toString().split("\\|");
		String serverProtocol = words[17];
		String serverPort = words[19];
		String serverName = words[18];
		String txnid = words[1];
		try {
			
		} catch (Exception e) {
			System.out.println("Error at PaymentResponse---------" + e);
		}

		return "payment/BilldeskResponse";
	}
	@GetMapping(path = "/BilldeskResponse.htm")
	public String paymentResponseGet(HttpServletRequest req) {
		System.out.println("/billDesk/paymentResponse.htm");
		return "payment/BilldeskResponse";
	}
}
