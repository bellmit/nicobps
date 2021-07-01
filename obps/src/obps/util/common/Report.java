package obps.util.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class Report extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("----------------Reports-----------");
		ServletOutputStream outstream;
		PrintWriter out = null;
		HashMap params = new HashMap();
		ArrayList al = new ArrayList();
		String reportName = "";
		// HttpSession sessionHttp = request.getSession();
		String status = request.getParameter("status");
		Connection con = null;

		try {
			WebApplicationContext springContext = WebApplicationContextUtils
					.getWebApplicationContext(getServletContext());
			DataSource ds = (DataSource) springContext.getBean("dataSource");
			con = ds.getConnection();

			String reportDirectory = getServletContext().getRealPath("/") + "reports/";
			// String logoPath = reportDirectory+"db.png";
			// String embPath = reportDirectory+"nei.gif";

			if (status != null && status.equals("1")) {
				Long transactioncode = Long.parseLong(request.getParameter("transactioncode"));

				params.put("transactioncode", transactioncode);
				reportName = "reports/PaymentReceipt.jrxml";
			} else if (status != null && status.equals("2")) {

				String permitnumber = request.getParameter("permitnumber");
				params.put("permitnumber", permitnumber);
				reportName = "reports/BuildingPermit.jrxml";
			}

			// System.out.println("status : "+status);
			// System.out.println("reportName : "+reportName);

			// System.out.println("reportName : "+reportName);
			// params.put("embPath", embPath);
			// params.put("logoPath", logoPath);
			params.put("SUBREPORT_DIR", reportDirectory);

			JasperDesign jasperDesign = JRXmlLoader.load(getServletContext().getRealPath("/") + reportName);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperprint = JasperFillManager.fillReport(jasperReport, params, con);

			// export report to pdf and stream back to browser
			byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperprint);
			outstream = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setContentLength(pdfasbytes.length);

			response.setHeader("Content-disposition", "inline; filename=\"Report.pdf\"");
			outstream.write(pdfasbytes);
			// response.sendRedirect("error.jsp");

		} catch (IOException | SQLException | JRException | BeansException e) {
			out = response.getWriter();
			response.sendRedirect("error.jsp");
			System.out.println(
					"Exception thrown by class " + this.getClass() + " at " + new java.util.Date() + " :: " + e);
			// Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (con != null) {
					con.close();
				}
				outstream = null;
				al = null;
				params = null;
			} catch (SQLException e) {
				// System.out.println("Exception thrown by class " + this.getClass() + " at " +
				// new java.util.Date() + " :: " + e);
				// Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, e);
			}
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
	// + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
