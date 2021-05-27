package obps.reports;

import java.io.File;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class ReportGenerate {

	public void generateReport(HttpServletRequest request, HttpServletResponse response, String fileName,
			Map<String, String> reportParams, String outputfilename) {

		try {
			String jrxmlFileName = request.getSession().getServletContext()
					.getRealPath("/reports/" + fileName + ".jrxml");
			String jasperFileName = request.getSession().getServletContext()
					.getRealPath("/reports/" + fileName + ".jasper");

			System.out.println(" jrxml path :: " + jrxmlFileName);
			System.out.println(" jasper path :: " + jasperFileName);
			File reportFile = new File(jasperFileName);

			System.out.println("file.exists =" + reportFile.exists());

			if (!reportFile.exists()) {
				JasperCompileManager.compileReportToFile(jrxmlFileName, jasperFileName);
			}
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);

			byte[] bytes = null;
			bytes = JasperRunManager.runReportToPdf(jasperReport, reportParams, new JREmptyDataSource());

			response.reset();
			response.resetBuffer();
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			response.setHeader("Content-disposition", "inline; filename=" + outputfilename + ".pdf");
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			System.out.println("Exception while generating report :: " + e);
		}
	}

}
