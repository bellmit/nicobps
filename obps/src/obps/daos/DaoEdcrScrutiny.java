package obps.daos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import obps.models.EdcrScrutiny;
import obps.models.Userlogin;
import obps.util.application.DaoUtilInterface;

@Transactional
@Repository("DaoEdcrScrutiny")
public class DaoEdcrScrutiny implements DaoEdcrScrutinyInterface {
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
	public boolean createEdcrScrutiny(Map<String, Object> param) {

		boolean response = false;
		String sql = null;
		try {
			Integer usercode = Integer.valueOf((String) param.get("usercode"));
			Integer useroffice = Integer.valueOf((String) param.get("useroffice"));
			System.out.println(useroffice);
			SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
			Date date = sd.parse(((String) param.get("log_date")).trim());
			  sql = "INSERT INTO nicobps.edcrScrutiny(usercode,edcrnumber,planinfoobject,status,entrydate,officecode,dxffile,scrutinyreport) "
					+ "VALUES (?,?,?,?,?,?,?,?) ";
			Object[] values = { usercode, ((String) param.get("edcrnumber")).trim(),
					((String) param.get("response")).trim(), ((String) param.get("status")).trim(), date, useroffice, null,
					null };
			response = jdbcTemplate.update(sql, values) > 0;
		} catch (Exception e) {
			response = false;
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoEdcrScrutiny.createEdcrScrutiny  : " + e);
		}
		return response;
	}

	@Override
	public EdcrScrutiny fetchEdcr(String edcrnumber) {
		EdcrScrutiny edcr = new EdcrScrutiny();
		try {
			String sql = "SELECT * FROM nicobps.edcrscrutiny WHERE edcrnumber=?";
			Object[] criteria = { edcrnumber };
			edcr = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(EdcrScrutiny.class), criteria);
		} catch (Exception e) {
			System.out.println("Error in DaoEdcrScrutiny.fetchEdcr(final String edcrnumber) : " + e);
		}
		return edcr;
	}

	@Override
	public List<EdcrScrutiny> fetchEdcr_usercd(String usercd) {
		Integer usercode = Integer.valueOf((String) usercd);
		List<EdcrScrutiny> list = null;
		try {
			String sql = "SELECT a.*,b.officename1 as officename FROM nicobps.edcrscrutiny a\r\n"
					+ "inner join masters.offices b on a.officecode=b.officecode\r\n"
					+ "\r\n"
					+ "WHERE a.usercode=?";
			Object[] criteria = { usercode };
			list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(EdcrScrutiny.class), criteria);
		} catch (Exception e) {
			System.out.println("Error in DaoEdcrScrutiny.fetchEdcr(final String edcrnumber) : " + e);
		}
		return list;
	}
	
	 
}
