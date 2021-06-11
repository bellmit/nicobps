package obps.controllers;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import obps.reports.ReportService;
import obps.reports.ServicePrintPermitInterface;

@Controller
public class ControllerPrintPermit {

	@Autowired
	private ServicePrintPermitInterface serviceprintpermit;

	@Autowired
	private ReportService reportservice;

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

	@PostMapping(value = "/printPermit.htm")
	public void printPermit(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> params) {
		System.out.println("-------------------Print Permit--------------------");
//		System.out.println(params);
		String edcrnumber = params.get("param_edcrnumber");

	
		JasperPrint jasperPrint = null;
		String fileName = "BPA_Permit";
		String outputfilename = "BuildingPermit";

		try {
			Map<String, String> reportParams = new HashMap<String, String>();
			reportParams.put("edcrnumber", edcrnumber);

			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition",
					String.format("attachment; filename=\"" + outputfilename + ".pdf\""));

			OutputStream out = response.getOutputStream();
			jasperPrint = reportservice.exportPdfFile(request, fileName, reportParams);

			JasperExportManager.exportReportToPdfStream(jasperPrint, out);

			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println("Exception in printPermit :" + e);
			e.printStackTrace();
		}

	}
}
