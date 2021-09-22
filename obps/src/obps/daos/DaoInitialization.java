package obps.daos;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import obps.models.FeeMaster;
import obps.models.FeeTypes;
import obps.models.LicenseesRegistrationsm;
import obps.models.Occupancies;
import obps.models.Offices;
import obps.models.PaymentModes;
import obps.models.Questionnaire;
import obps.models.SubOccupancies;
import obps.models.Usages;
import obps.util.application.DaoUtilInterface;

@Transactional
@Repository("daoInitialization")
public class DaoInitialization implements DaoInitializationInterface {

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
	public boolean updateLicenseesRegistrationsm(LicenseesRegistrationsm licensee) {
		String sql = "";
		boolean response = false;
		try {
			sql = "UPDATE masters.licenseesregistrationsm SET licenseedescription = ? WHERE licenseeregistrationcode = ?";
			Object[] param = new Object[] { licensee.getLicenseedescription(), licensee.getLicenseeregistrationcode() };
			response = jdbcTemplate.update(sql, param) > 0;
		} catch (Exception e) {
			response = false;
			e.getStackTrace();
			response = false;
			System.out.println(
					"Error in DaoUserManagement.updateLicenseesRegistrationsm(LicenseesRegistrationsm licensee)  : "
							+ e);
		}
		return response;
	}

	@Override
	public boolean updatefeetypes(FeeTypes feetype) {
		String sql = "";
		boolean response = false;
		try {
			sql = "UPDATE masters.feetypes SET feetypedescription = ? WHERE feetypecode = ?";
			Object[] param = new Object[] { feetype.getFeetypedescription(), feetype.getFeetypecode() };
			response = jdbcTemplate.update(sql, param) > 0;
		} catch (Exception e) {
			response = false;
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.updatefeetypes(updatefeetypes )  : " + e);
		}
		return response;
	}

	@Override
	public boolean updatequestionaires(Questionnaire questionaire) {
		String sql = "";
		boolean response = false;
		try {
			sql = "UPDATE masters.questionaires SET questiondescription = ? WHERE questioncode = ?";
			Object[] param = new Object[] { questionaire.getQuestiondescription(), questionaire.getQuestioncode() };
			response = jdbcTemplate.update(sql, param) > 0;
		} catch (Exception e) {
			response = false;
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.updatequestionaires(updatequestionaires )  : " + e);
		}
		return response;
	}

	@Override
	public boolean updatefeemaster(FeeMaster feemaster) {
		String sql = "";
		boolean response = false;
		try {
			System.out.println("DAO feemaster::" + feemaster.getOfficecode() + "::" + feemaster.getLicenseetypecode()
					+ "::" + feemaster.getFeetypecode() + "::" + feemaster.getFeeamount() + "::"
					+ feemaster.getFeecode());
			sql = "UPDATE masters.feemaster SET officecode = ?,licenseetypecode=?,feetypecode=?,feeamount=? WHERE feecode = ?";
			Object[] param = new Object[] { feemaster.getOfficecode(), feemaster.getLicenseetypecode(),
					feemaster.getFeetypecode(), feemaster.getFeeamount(), feemaster.getFeecode() };
			response = jdbcTemplate.update(sql, param) > 0;
		} catch (Exception e) {
			response = false;
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.updatefeemaster(updatefeetypes )  : " + e);
		}
		return response;
	}

	@Override
	public boolean updatesuboccupancy(SubOccupancies suboccupancies) {
		String sql = "";
		boolean response = false;
		try {
			System.out.println("DAO feemaster::" + suboccupancies.getOccupancycode() + "::"
					+ suboccupancies.getSuboccupancycode() + "::" + suboccupancies.getSuboccupancyname() + "::"
					+ suboccupancies.getDescription() + "::");
			sql = "UPDATE masters.suboccupancies SET occupancycode = ?,suboccupancycode=?,suboccupancyname=?,description=? WHERE suboccupancycode = ?";
			Object[] param = new Object[] { suboccupancies.getOccupancycode(), suboccupancies.getSuboccupancycode(),
					suboccupancies.getSuboccupancyname(), suboccupancies.getDescription(),
					suboccupancies.getSuboccupancycode() };
			response = jdbcTemplate.update(sql, param) > 0;
		} catch (Exception e) {
			response = false;
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.updatefeemaster(updatefeetypes )  : " + e);
		}
		return response;
	}

	@Override
	public boolean updateusages(Usages usages) {
		String sql = "";
		boolean response = false;
		try {
			System.out.println("DAO updateusages::" + usages.getSuboccupancycode() + "::" + usages.getUsagecode() + "::"
					+ usages.getUsagename() + "::" + usages.getDescription() + "::");
			sql = "UPDATE masters.usages SET suboccupancycode=?,usagecode = ?,usagename=?,description=? WHERE usagecode = ?";
			Object[] param = new Object[] { usages.getSuboccupancycode(), usages.getUsagecode(), usages.getUsagename(),
					usages.getDescription(), usages.getUsagecode() };
			response = jdbcTemplate.update(sql, param) > 0;
		} catch (Exception e) {
			response = false;
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.updateusages(updateusages )  : " + e);
		}
		return response;
	}

	@Override
	public boolean updateoccupancy(Occupancies occupancies) {
		String sql = "";
		boolean response = false;
		try {
			sql = "UPDATE masters.occupancies SET occupancycode = ?, occupancyname=?,occupancyalias=? WHERE occupancycode = ?";
			Object[] param = new Object[] { occupancies.getOccupancycode(), occupancies.getOccupancyname(),
					occupancies.getOccupancyalias(), occupancies.getOccupancycode() };
			response = jdbcTemplate.update(sql, param) > 0;
		} catch (Exception e) {
			response = false;
			e.getStackTrace();
			response = false;
//			System.out.println("Error in DaoUserManagement.updateoccupancy(updateoccupancy )  : " + e);
		}
		return response;
	}

	@Override
	public List<Occupancies> listOccupancies() {
		System.out.println("inside dao begin");
		List<Occupancies> list = null;
		try {
			String sql = "Select occupancycode, occupancyname, occupancyalias "
					+ "From masters.occupancies Order by occupancycode";
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Occupancies>(Occupancies.class));
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Error in DaoUserManagement.listUsers()  : " + e);
		}
		System.out.println("inside dao end");
		return (list != null) ? list : new LinkedList();
	}

	@Override
	public List<FeeMaster> listFeeMaster() {
		System.out.println("inside listFeeMaster dao begin");
		List<FeeMaster> list = null;

		try {
			String sql = "Select fm.feecode,fm.feetypecode, fm.officecode,o.officename1 || ' ' || o.officename2 as officename1,l.licenseetypename as licenseetypename,f.feetypedescription as feetypedescription,fm.licenseetypecode,fm.feeamount From masters.feemaster fm \r\n"
					+ "					left outer join masters.offices o on o.officecode=fm.officecode \r\n"
					+ "					inner join masters.feetypes f on f.feetypecode=fm.feetypecode \r\n"
					+ "					left outer join masters.licenseetypes l on l.licenseetypecode=fm.licenseetypecode \r\n"
					+ "					Order by officename1";
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<FeeMaster>(FeeMaster.class));
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Error in DaoUserManagement.listUsers()  : " + e);
		}
		System.out.println("inside dao listFeeMaster end");
		return (list != null) ? list : new LinkedList();
	}

	@Override
	public List<SubOccupancies> listSubOccupancy() {
		System.out.println("inside listSubOccupancy dao begin");
		List<SubOccupancies> list = null;

		try {
			String sql = "Select so.occupancycode,so.suboccupancycode,so.suboccupancyname,so.description,o.occupancyname From masters.suboccupancies so\r\n"
					+ "					inner join masters.occupancies o on o.occupancycode=so.occupancycode\r\n"
					+ "					Order by so.occupancycode";
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<SubOccupancies>(SubOccupancies.class));
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Error in DaoUserManagement.listSubOccupancy()  : " + e);
		}
		System.out.println("inside dao listSubOccupancy end");
		return (list != null) ? list : new LinkedList();
	}

	@Override
	public List<Usages> listUsages() {
		System.out.println("inside listUsages dao begin");
		List<Usages> list = null;

		try {
			String sql = "Select so.suboccupancycode,u.usagecode,u.usagename,u.description From masters.usages u	\r\n"
					+ "	inner join masters.suboccupancies so on so.suboccupancycode=u.suboccupancycode\r\n"
					+ "	Order by so.suboccupancycode";
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Usages>(Usages.class));
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Error in DaoUserManagement.listUsages()  : " + e);
		}
		System.out.println("inside dao listUsages end");
		return (list != null) ? list : new LinkedList();
	}

	@Override
	public List<LicenseesRegistrationsm> listLicenseesRegistrationsms() {
		List<LicenseesRegistrationsm> list = null;
		try {
			String sql = "Select licenseeregistrationcode, licenseedescription, enabled from"
					+ " masters.licenseesregistrationsm Order by licenseedescription";
			list = jdbcTemplate.query(sql,
					new BeanPropertyRowMapper<LicenseesRegistrationsm>(LicenseesRegistrationsm.class));
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Error in DaoUserManagement.listLicenseesRegistrationsms()  : " + e);
		}
		return (list != null) ? list : new LinkedList();
	}

	@Override
	public List<FeeTypes> listFeeTypes() {
		List<FeeTypes> list = null;
		try {
			String sql = "Select feetypecode, feetypedescription, enabled from"
					+ " masters.feetypes Order by feetypedescription";
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<FeeTypes>(FeeTypes.class));
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Error in DaoUserManagement.listFeeTypes()  : " + e);
		}
		return (list != null) ? list : new LinkedList();
	}

	@Override
	public List<Questionnaire> listQuestionaires() {
		List<Questionnaire> list = null;
		try {
			String sql = "Select questioncode, questiondescription, enabled from"
					+ " masters.questionaires Order by questiondescription";
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Questionnaire>(Questionnaire.class));
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Error in DaoUserManagement.listQuestionaires()  : " + e);
		}
		return (list != null) ? list : new LinkedList();
	}

	@Override
	public boolean initfeetypes(Map<String, Object> param) {
		boolean response = false;
		String sql = null;
		Integer feetypecode = Integer.valueOf((String) param.get("feetypecode"));
		try {
			sql = "INSERT INTO masters.feetypes(feetypecode,feetypedescription) " + "VALUES (?,?) ";
			Object[] values = { feetypecode, ((String) param.get("feetypedescription")).trim() };
			response = jdbcTemplate.update(sql, values) > 0;

		} catch (Exception e) {
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.createUser(Map<String,String> param) : " + e);
		}
		return response;
	}

	@Override
	public boolean initQuestionaires(Map<String, Object> param) {
		boolean response = false;
		String sql = null;
		Integer questioncode = Integer.valueOf((String) param.get("questioncode"));
		try {
			sql = "INSERT INTO masters.questionaires(questioncode,questiondescription) " + "VALUES (?,?) ";
			Object[] values = { questioncode, ((String) param.get("questiondescription")).trim() };
			response = jdbcTemplate.update(sql, values) > 0;

		} catch (Exception e) {
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.initQuestionaires(Map<String,String> param) : " + e);
		}
		return response;
	}

	@Override
	public boolean initoccupancy(Map<String, Object> param) {
		boolean response = false;
		String sql = null;

		try {
			sql = "INSERT INTO masters.occupancies(occupancycode,occupancyname,occupancyalias) " + "VALUES (?,?,?) ";
			Object[] values = { ((String) param.get("occupancycode")).trim(),
					((String) param.get("occupancyname")).trim(), ((String) param.get("occupancyalias")).trim() };
			response = jdbcTemplate.update(sql, values) > 0;

		} catch (Exception e) {
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.createUser(Map<String,String> param) : " + e);
		}
		return response;
	}

	@Override
	public boolean initfeemaster(Map<String, Object> param) {
		boolean response = false;
		String sql = null;
		System.out.println("dao officecode::::" + param.get("officecode"));
		System.out.println("dao licenseetypecode::::" + param.get("licenseetypecode"));
		System.out.println("dao feetypecode::::" + param.get("feetypecode"));
		Integer feecode = Integer.valueOf((String) param.get("feecode"));
		try {
			sql = "INSERT INTO masters.feemaster(feecode,officecode,licenseetypecode,feetypecode,feeamount) "
					+ "VALUES (?,?,?,?,?) ";
			Object[] values = { feecode,
					Integer.parseInt(param.get("officecode").toString()) == 0 ? null
							: Integer.parseInt(param.get("officecode").toString()),
					Integer.parseInt(param.get("licenseetypecode").toString()) == 0 ? null
							: Integer.parseInt(param.get("licenseetypecode").toString()),
					Integer.valueOf(param.get("feetypecode").toString()),
					Integer.parseInt(param.get("feeamount").toString()) };
			response = jdbcTemplate.update(sql, values) > 0;

		} catch (Exception e) {
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.initfeemaster(Map<String,String> param) : " + e);
		}
		return response;
	}

	@Override
	public boolean initsuboccupancies(Map<String, Object> param) {
		boolean response = false;
		String sql = null;
		System.out.println("dao occupancycode::::" + param.get("occupancycode"));
		System.out.println("dao suboccupancycode::::" + param.get("suboccupancycode"));
		System.out.println("dao suboccupancyname::::" + param.get("suboccupancyname"));
		System.out.println("dao description::::" + param.get("description"));
//		Integer feecode = Integer.valueOf((String) param.get("description"));
		try {

			sql = "INSERT INTO masters.suboccupancies(occupancycode,suboccupancycode,suboccupancyname,description) "
					+ " VALUES (?,?,?,?) ";
			Object[] values = { param.get("occupancycode").toString(), param.get("suboccupancycode").toString(),
					param.get("suboccupancyname").toString(), param.get("description").toString() };
			response = jdbcTemplate.update(sql, values) > 0;

		} catch (Exception e) {
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.initsuboccupancies(Map<String,String> param) : " + e);
		}
		return response;
	}

	@Override
	public boolean initusages(Map<String, Object> param) {
		boolean response = false;
		String sql = null;
		System.out.println("dao usagecode::::" + param.get("usagecode"));
		System.out.println("dao suboccupancycode::::" + param.get("suboccupancycode"));
		System.out.println("dao usagename::::" + param.get("usagename"));
		System.out.println("dao description::::" + param.get("description"));
//		Integer feecode = Integer.valueOf((String) param.get("description"));
		try {

			sql = "INSERT INTO masters.usages(suboccupancycode,usagecode,usagename,description) "
					+ " VALUES (?,?,?,?) ";
			Object[] values = { param.get("suboccupancycode").toString(), param.get("usagecode").toString(),
					param.get("usagename").toString(), param.get("description").toString() };
			response = jdbcTemplate.update(sql, values) > 0;

		} catch (Exception e) {
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.initsuboccupancies(Map<String,String> param) : " + e);
		}
		return response;
	}

	@Override
	public boolean createLicenseeRegistration(Map<String, Object> param) {
		boolean response = false;
		String sql = null;
		Integer licenseeregistrationcode = Integer.valueOf((String) param.get("licenseeregistrationcode"));
		try {
			sql = "INSERT INTO masters.licenseesregistrationsm(licenseeregistrationcode,licenseedescription) "
					+ "VALUES (?,?) ";
			Object[] values = { licenseeregistrationcode, ((String) param.get("licenseedescription")).trim() };
			response = jdbcTemplate.update(sql, values) > 0;

		} catch (Exception e) {
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.createUser(Map<String,String> param) : " + e);
		}
		return response;
	}

	@Override
	public boolean checkExistance(String sql, Object[] values) {
		System.out.println("Check Exist!!!");
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, values);
//		 System.out.println("rowset"+rowSet.next());
		return rowSet.next();
	}

	@Override
	public List<Offices> listOffices() {
		List<Offices> list = null;
		try {
			String sql = "Select * From masters.offices";
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Offices>(Offices.class));
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Error in DaoInitialization.listOffices()  : " + e);
		}
		return (list != null) ? list : new LinkedList();
	}

	@Override
	public List<Questionnaire> getMappedQuestionaires(Integer officecode) {
		System.out.println("officecode" + officecode);
		ObjectMapper mapper = new ObjectMapper();
		List<Questionnaire> questionlist = null;
		List<Map<String, Object>> rowList = null;
		try {
			String sql = " Select officequestionaires.questioncode From masters.questionaires up,masters.officequestionaires officequestionaires "
					+ "	WHERE up.questioncode=officequestionaires.questioncode and officecode=:officecode";
			MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("officecode", officecode);
			rowList = (List<Map<String, Object>>) namedParameterJdbcTemplate.queryForList(sql, parameters);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("\n\nError in getMappedQuestionaires(Integer officecode) " + ex);
		}
		if (rowList != null) {
			questionlist = new LinkedList<Questionnaire>();
			for (Map<String, Object> row : rowList) {
				questionlist.add(mapper.convertValue(row, Questionnaire.class));
			}
		}
		return (questionlist != null) ? questionlist : new LinkedList();
	}

	@Override
	public boolean mapOfficesQuestioniares(List<Map<String, Object>> officesquestions) {

		boolean response = false;
		try {
			String sql = "DELETE From masters.officequestionaires WHERE officecode=? ";
			if (jdbcTemplate.update(sql, officesquestions.get(0).get("officecode")) < 0) {
				return false;
			}
			/////////////////////////////////////
			sql = "INSERT INTO masters.officequestionaires(officecode, questioncode) VALUES (?, ?)";
			for (Map<String, Object> up : officesquestions) {

				jdbcTemplate.update(sql, up.get("officecode"),
						((Map<String, Object>) up.get("questioncode")).get("questioncode"));
			}
			response = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out
					.println("\n\nError in mapOfficesQuestioniares (List<Map<String,Object>> officesquestions) " + ex);
		}
		return response;
	}
}
