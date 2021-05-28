package obps.daos;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JasperPrint;

public interface DaoReportInterface {

	public JasperPrint exportPdfFile(HttpServletRequest request, String fileName, Map<String, String> reportParams);

}
