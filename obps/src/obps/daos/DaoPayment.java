package obps.daos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import obps.util.application.CountAndMax;
import obps.util.application.DaoUtilInterface;

@Transactional
@Repository("DaoPayment")
public class DaoPayment implements DaoPaymentInterface {
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private DaoUtilInterface daoUtilInterface;

	@Autowired
	public void createTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public boolean InitiatePayment(Map<String, Object> param) {

		boolean response = false;
		String sql = null;
		try {
			Integer usercode = Integer.valueOf((String) param.get("usercode"));
			SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
			Date date = sd.parse(((String) param.get("entrydate")).trim());
			sql = "INSERT INTO nicobps.transactions(transactioncode,usercode,feecode,amount,paymentmodecode,paymentstatus,sentparameters,entrydate) "
					+ "VALUES (?,?,?,?,?,?,?,?) ";
//			System.out.println(" Integer.valueOf((String) param.get(\"transactioncode\")) " + Integer.valueOf( param.get("transactioncode").toString()));
//			System.out.println(" Integer.valueOf((String) param.get(\"feecode\")) "+ Integer.valueOf((String) param.get("feecode")));
			
			Object[] values = { Integer.valueOf( param.get("transactioncode").toString()), usercode,
					Integer.valueOf(param.get("feecode").toString()), param.get("amount"), param.get("paymentmodecode"),
					param.get("paymentstatus"), param.get("sentparameters"), date };
			response = jdbcTemplate.update(sql, values) > 0;
			
		} catch (Exception e) {
			response = false;
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoPayment.InitiatePayment  : " + e);
		}
		return response;
	}

	@Override
	public boolean SavePaymentMap(Map<String, Object> param) {

		boolean response = false;
		String sql = null;
		try {

			SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
			Date date = sd.parse(((String) param.get("entrydate")).trim());
			sql = "INSERT INTO nicobps.applicationstransactionmap(applicationcode,transactioncode,entrydate) "
					+ "VALUES (?,?,?) ";
			Object[] values = { ((String) param.get("applicationcode")).trim(),
					Integer.valueOf( param.get("transactioncode").toString()), date };
			response = jdbcTemplate.update(sql, values) > 0;
		} catch (Exception e) {
			response = false;
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoPayment.SavePaymentMap  : " + e);
		}
		return response;
	}

	@Override
	public boolean UpdatePayment(String paymentstatus, String responseparameters, Integer transactioncode,
			Integer usercode) {
		
		boolean response = false;
		String sql = null;
		try {

			sql = " Update nicobps.transactions set paymentstatus=?,responseparameters=? where transactioncode=? and usercode=?";
			Object[] values = { paymentstatus, responseparameters.trim(), transactioncode, usercode };
			response = jdbcTemplate.update(sql, values) > 0;
		} catch (Exception e) {
			response = false;
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoPayment.UpdatePayment  : " + e);
		}
		return response;
	}

	@Override
	public boolean saveTransactionReceipt(Integer paymentstatus, String receiptno) {
//		System.out.println("saveTransactionReceipt");

		boolean response = false;
		String sql = null;
		try {

			sql = " insert into nicobps.transactionsreceipts(transactioncode,receiptno,entrydate)values(?,?,NOW())";
			Object[] values = { paymentstatus, receiptno };
			response = jdbcTemplate.update(sql, values) > 0;
		} catch (Exception e) {
			response = false;
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoPayment.UpdatePayment  : " + e);
		}
		return response;
	}

	@Override
	public Map<String, Object> getFeeDetails(Integer feecode) {

		Map<String, Object> resp = null;
		String sql = null;
		try {

			sql = "select a.* ,b.feetypedescription from masters.feemaster a inner join masters.feetypes b on a.feetypecode=b.feetypecode and a.feecode=? and a.enabled='Y' and b.enabled='Y' order by feecode";
			Object[] values = { feecode };

			resp = jdbcTemplate.queryForMap(sql, values);

		} catch (EmptyResultDataAccessException e) {
			return null;

		} catch (Exception e) {

			e.getStackTrace();

			System.out.println("Error in DaoPayment.getFeeDetails  : " + e);
		}
		return resp;
	}

	@Override
	public List<Map<String, Object>> getPaymentStatus(String applicationcode, Integer feecode) {

		List<Map<String, Object>> resp = null;
		String sql = null;
		try {

			sql = " select atm.applicationcode,t.transactioncode,t.feecode,t.paymentstatus,t.entrydate from nicobps.transactions t  "
					+ " inner join  nicobps.applicationstransactionmap atm on t.transactioncode= atm.transactioncode "
					+ " where  atm.applicationcode=? and t.feecode=? and    t.paymentstatus='S' "
					+ " and t.entrydate=(SELECT MAX(t.entrydate)  "
					+ " from nicobps.transactions t inner join  nicobps.applicationstransactionmap atm on t.transactioncode= atm.transactioncode  "
					+ " where  atm.applicationcode=? and t.feecode=? );";

			Object[] values = { applicationcode, feecode, applicationcode, feecode };
			resp = jdbcTemplate.queryForList(sql, values);
		} catch (Exception e) {

			e.getStackTrace();

			System.out.println("Error in DaoPayment.getPaymentStatus  : " + e);
		}
		return resp;

	}

	@Override
	public int getApplicationCount(String applicationcode) {

		int resp = 0;
		String sql = null;
		try {

			sql = " select count(*) from nicobps.applications where applicationcode=?; ";
			Object[] values = { applicationcode };
			resp = daoUtilInterface.getCount(sql, values);

		} catch (Exception e) {

			e.getStackTrace();

			System.out.println("Error in DaoPayment.getApplicationCount  : " + e);
		}
		return resp;

	}

	public Map<String, Object> getTransaction(Integer transactioncode) {
		Map<String, Object> resp = null;
		String sql = null;
		try {

			sql = " select t.transactioncode,t.responseparameters,atm.applicationcode,ft.feetypedescription,pm.\"mode\",pm.paymentmodedescription,t.paymentstatus,ul.fullname "
					+ " from nicobps.transactions t "
					+ " inner join nicobps.applicationstransactionmap atm on t.transactioncode=atm.transactioncode "
					+ " inner join masters.feemaster fm on fm.feecode=t.feecode "
					+ " inner join masters.feetypes ft on ft.feetypecode=fm.feetypecode "
					+ " inner join masters.paymentmodes pm on pm.paymentmodecode=t.paymentmodecode "
					+ " inner join nicobps.userlogins ul on ul.usercode=t.usercode " + " where t.transactioncode=? ;";

			Object[] values = { transactioncode };
			resp = jdbcTemplate.queryForMap(sql, values);
		} catch (Exception e) {

			e.getStackTrace();

			System.out.println("Error in DaoPayment.getTransaction  : " + e);
		}
		return resp;

	}

	public List<Map<String, Object>> getTransactionList(Integer transactioncode) {
		List<Map<String, Object>> resp = null;
		String sql = null;
		try {

			sql = " select t.transactioncode,t.responseparameters,atm.applicationcode,ft.feetypedescription,pm.\"mode\",pm.paymentmodedescription,t.paymentstatus,ul.fullname "
					+ " from nicobps.transactions t "
					+ " inner join nicobps.applicationstransactionmap atm on t.transactioncode=atm.transactioncode "
					+ " inner join masters.feemaster fm on fm.feecode=t.feecode "
					+ " inner join masters.feetypes ft on ft.feetypecode=fm.feetypecode "
					+ " inner join masters.paymentmodes pm on pm.paymentmodecode=t.paymentmodecode "
					+ " inner join nicobps.userlogins ul on ul.usercode=t.usercode  where t.transactioncode=? ;";

			Object[] values = { transactioncode };
			resp = jdbcTemplate.queryForList(sql, values);
		} catch (Exception e) {

			e.getStackTrace();

			System.out.println("Error in DaoPayment.getTransactionList  : " + e);
		}
		return resp;

	}

	public List<Map<String, Object>> getTransaction(String applicationcode) {
		List<Map<String, Object>> resp = null;
		String sql = null;
		try {

			sql = " select  max(t.transactioncode) as transactioncode,t.feecode,ft.feetypedescription "
					+ " from nicobps.transactions t "
					+ " inner join nicobps.applicationstransactionmap at on t.transactioncode=at.transactioncode "
					+ " inner join masters.feemaster fm on t.feecode=fm.feecode"
					+ " inner join masters.feetypes ft on fm.feetypecode=ft.feetypecode"
					+ " where t.amount<>0 and applicationcode=? " + " group by t.feecode,ft.feetypedescription";

			Object[] values = { applicationcode };
			resp = jdbcTemplate.queryForList(sql, values);
		} catch (Exception e) {

			e.getStackTrace();

			System.out.println("Error in DaoPayment.getTransaction with Aplicationcode  : " + e);
		}
		return resp;

	}
}
