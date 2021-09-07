/*@author Avijit Debnath*/

package obps.util.common;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import obps.validators.PrintPermitValidator;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class Report extends HttpServlet {

	@Autowired
	private PlanInfoDetails planinfo;

	@Autowired
	private PrintPermitValidator PPV;

	public void init(ServletConfig config) {
		try {
			super.init(config);
			SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("----------------Reports-----------");
		ServletOutputStream outstream;
		PrintWriter out = null;
		HashMap params = new HashMap();
		ArrayList al = new ArrayList();
		String reportName = "";
		// HttpSession sessionHttp = request.getSession();

		Connection con = null;

		try {
			String status = request.getParameter("status");
			String filename = "Report.pdf";

			WebApplicationContext springContext = WebApplicationContextUtils
					.getWebApplicationContext(getServletContext());
			DataSource ds = (DataSource) springContext.getBean("dataSource");
			con = ds.getConnection();

			String reportDirectory = getServletContext().getRealPath("/") + "reports/";
			String logoPath = reportDirectory + "gmc_logobw.png";
			// String embPath = reportDirectory+"nei.gif";

			if (status != null && status.equals("1")) {

				String validate = PPV.validate_transactioncode(request.getParameter("transactioncode"));

				if (validate.equals("1")) {
					Long transactioncode = Long.parseLong(request.getParameter("transactioncode"));
					System.out.println("transactioncode :" + transactioncode);
					params.put("transactioncode", transactioncode);
					// reportName = "reports/PaymentReceipt.jrxml";
					reportName = "reports/paymentreceipts.jrxml";
					filename = "PaymentReceipt.pdf";
				} else {
					response.sendRedirect("error.jsp?msg=REPORT_VALIDATION_ERROR");
					return;

				}

			} else if (status != null && status.equals("2")) {

				String validate = PPV.validate_permitnumber(request.getParameter("permitnumber"));
				System.out.println(validate);

				if (validate.equals("1")) {
					String permitnumber = request.getParameter("permitnumber").trim();
					System.out.println("permitnumber :" + permitnumber);

					params.put("permitnumber", permitnumber);

					reportName = "reports/buildingpermit02.jrxml";
					filename = "BuildingPermit.pdf";

					HashMap plandet = planinfo.getPlanInfoDetails(permitnumber);
					params.putAll(plandet);

					System.out.println("params for buildingpermit:: " + params.toString());
				} else {
					response.sendRedirect("error.jsp?msg=REPORT_VALIDATION_ERROR");
					return;
				}

			} else if (status != null && status.equals("3")) {

				String fromdate = request.getParameter("fromdate").trim();
				String todate = request.getParameter("todate").trim();
				String officecode = request.getParameter("officecode").trim();

				if (fromdate != null) {
					if (!Patterns.PatternMatche(Patterns.PATTERN_DATE, fromdate) || fromdate.length() > 30) {
						response.sendRedirect("error.jsp");
						return;
					}
				} else {
					response.sendRedirect("error.jsp");
					return;
				}

				if (todate != null) {
					if (!Patterns.PatternMatche(Patterns.PATTERN_DATE, todate) || todate.length() > 30) {
						response.sendRedirect("error.jsp");
						return;
					}
				} else {
					response.sendRedirect("error.jsp");
					return;
				}

				if (officecode != null) {
					if (!Patterns.PatternMatche(Patterns.XPATTERN_POSITIVE_INTEGER, officecode)
							|| officecode.length() > 2) {
						response.sendRedirect("error.jsp");
						return;
					}
				} else {
					response.sendRedirect("error.jsp");
					return;
				}

				params.put("officecode", officecode);
				params.put("fromdate", fromdate);
				params.put("todate", todate);

				reportName = "reports/dayendstatement.jrxml";
				filename = "Dayendstatement.pdf";
			} else if (status != null && status.equals("4")) {
				System.out.println("applicationcode=" + request.getParameter("applicationcode"));
				String applicationcode = (request.getParameter("applicationcode") == null
						|| request.getParameter("applicationcode").equals("")) ? null
								: request.getParameter("applicationcode").trim();
				if (applicationcode == null) {
					response.sendRedirect("error.jsp?msg=REPORT_PARAM_MISSING");
					return;
				}
				params.put("applicationcode", applicationcode);

				reportName = "reports/stakeholder.jrxml";
				filename = "stakeholder_" + applicationcode + ".pdf";
			} else {
				response.sendRedirect("error.jsp?msg=REPORT_STATUS_MISSING");
				return;
			}

			// System.out.println("status : "+status);
			// System.out.println("reportName : "+reportName);

			// System.out.println("reportName : "+reportName);
			// params.put("embPath", embPath);
			params.put("logopath", logoPath);
			params.put("SUBREPORT_DIR", reportDirectory);

			JasperDesign jasperDesign = JRXmlLoader.load(getServletContext().getRealPath("/") + reportName);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperprint = JasperFillManager.fillReport(jasperReport, params, con);

			// export report to pdf and stream back to browser
			byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperprint);
			outstream = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setContentLength(pdfasbytes.length);

			response.setHeader("Content-disposition", "inline; filename=\"" + filename + "\"");
			outstream.write(pdfasbytes);
			// response.sendRedirect("error.jsp");

		} catch (IOException | SQLException | JRException | BeansException e) {
			out = response.getWriter();
			System.out.println(
					"Exception thrown by class " + this.getClass() + " at " + new java.util.Date() + " :: " + e);
			System.out.println("HERE----------");
			e.printStackTrace();
			// Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, e);
			response.sendRedirect("error.jsp");

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
				System.out.println(
						"SQLException thrown by class " + this.getClass() + " at " + new java.util.Date() + " :: " + e);
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
