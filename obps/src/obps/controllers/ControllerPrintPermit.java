package obps.controllers;


import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import obps.services.ServicePrintPermitInterface;

@Controller
public class ControllerPrintPermit {

	@Autowired
	private ServicePrintPermitInterface serviceprintpermit;

	@GetMapping(value = "/printPermit.htm")
	public String printpermit() {

		return "reports/printpermit";

	}

	@PostMapping(value = "/getPermitList.htm")
	public @ResponseBody List<Map<String, Object>> getpermitList(@RequestParam Map<String, String> params) {

		return serviceprintpermit.getPermitList(params.get("applicationcode"), params.get("edcrnumber"),
				params.get("ownername"), params.get("fromentrydate"), params.get("toentrydate"),
				params.get("criteria"));

	}

}
