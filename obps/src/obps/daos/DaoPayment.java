package obps.daos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("DaoPayment")
public class DaoPayment implements DaoPaymentInterface {
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
			sql = "INSERT INTO nicobps.transactions(transactioncode,usercode,feecode,amount,paymentmodecode,paymentstatus,sentparameters,entrydate) " + "VALUES (?,?,?,?,?,?,?,?) ";
			Object[] values = { param.get("transactioncode"), usercode, Integer.valueOf((String) param.get("feecode")), param.get("amount"), param.get("paymentmodecode"), param.get("paymentstatus"),
					param.get("sentparameters"), date };
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
			sql = "INSERT INTO nicobps.applicationstransactionmap(applicationcode,transactioncode,entrydate) " + "VALUES (?,?,?) ";
			Object[] values = { ((String) param.get("applicationcode")).trim(), param.get("transactioncode"), date };
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
	public boolean UpdatePayment(String paymentstatus, String responseparameters, Integer transactioncode, Integer usercode) {

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
	public Map<String, Object> getFeeDetails(Integer feecode) {

		Map<String, Object> resp = null;
		String sql = null;
		try {

			sql = "select a.* ,b.feetypedescription from masters.feemaster a inner join masters.feetypes b on a.feetypecode=b.feetypecode and a.feecode=? and a.enabled='Y' and b.enabled='Y' order by feecode";
			Object[] values = { feecode };
			resp = jdbcTemplate.queryForMap(sql, values);
		} catch (Exception e) {

			e.getStackTrace();

			System.out.println("Error in DaoPayment.getFeeDetails  : " + e);
		}
		return resp;
	}
}
