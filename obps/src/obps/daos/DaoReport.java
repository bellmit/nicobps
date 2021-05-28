package obps.daos;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Transactional
@Repository("DaoReport")
public class DaoReport implements DaoReportInterface {

	@Override
	public JasperPrint exportPdfFile(HttpServletRequest request, String fileName, Map<String, String> reportParams) {

		JasperPrint print = null;
		try {

			String jrxmlFilePath = request.getSession().getServletContext()
					.getRealPath("/reports/" + fileName + ".jrxml");

			System.out.println(" jrxml path :: " + jrxmlFilePath);

			JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFilePath);

			print = JasperFillManager.fillReport(jasperReport, reportParams, new JREmptyDataSource());

		} catch (Exception e) {
		
			System.out.println("Exception  in exportPdfFile ::" + e);
		}
		return print;
	}

}
