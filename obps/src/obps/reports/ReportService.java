package obps.reports;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperPrint;
import obps.daos.DaoReportInterface;

@Service
public class ReportService {

	@Autowired
	DaoReportInterface daoreportinterface;

	public JasperPrint exportPdfFile(HttpServletRequest request, String fileName, Map<String, String> reportParams) {

		return daoreportinterface.exportPdfFile(request, fileName, reportParams);
	}

}
