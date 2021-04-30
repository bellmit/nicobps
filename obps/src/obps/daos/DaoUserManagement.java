package obps.daos;

import java.util.Map;
import java.util.List;
import java.util.Base64;
import java.util.LinkedList;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import obps.models.Pageurls;
import obps.models.Userlogin;
import obps.util.application.CommonMap;

@Transactional
@Repository("daoUserManagement")
public class DaoUserManagement implements DaoUserManagementInterface {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void createTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean createUser(Map<String, Object> param) {
		boolean response = false;
		String sql = null;
		Integer usercode = Integer.valueOf((String) param.get("usercode"));
		try {
			sql = "INSERT INTO nicobps.userlogins(usercode,username,userpassword,fullname,mobileno,designation) "
					+ "VALUES (?,?,?,?,?,?) ";
			Object[] values = { usercode, ((String) param.get("username")).trim(),
					((String) param.get("userpassword")).trim(), ((String) param.get("fullname")).trim(),
					((String) param.get("mobileno")).trim(), ((String) param.get("designation")).trim() };
			response = jdbcTemplate.update(sql, values) > 0;
			if(param.get("usertype")==null) {
				if (response && param.get("licenseetypecode") != null
						&& ((String) param.get("licenseetypecode")).length() > 0) {
					sql = "INSERT INTO nicobps.licensees" + "( "
							+ "  usercode,licenseetypecode,firmindividual,firmname,applicantsname,gender, "
							+ "  preaddressline1,preaddressline2,previllagetown,predistrictcode,prepincode, "
							+ "  peraddressline1,peraddressline2,pervillagetown,perdistrictcode,perpincode " + ") "
							+ "VALUES " + "( " + " ?,?,?,?,?,?, " + " ?,?,?,?,?, " + " ?,?,?,?,? " + ") ";
					Object[] values1 = { usercode, Short.valueOf((String) param.get("licenseetypecode")),
							((String) param.get("firmindividual")), ((String) param.get("firmname")),
							((String) param.get("fullname")).trim(), (String) param.get("gender"),
							(String) param.get("preaddressline1"), (String) param.get("preaddressline2"),
							((String) param.get("previllagetown")).trim(),
							Short.valueOf((String) param.get("predistrictcode")),
							Integer.valueOf(((String) param.get("prepincode")).trim()), param.get("peraddressline1"),
							param.get("peraddressline2"), ((String) param.get("pervillagetown")).trim(),
							Short.valueOf((String) param.get("perdistrictcode")),
							Integer.valueOf(((String) param.get("perpincode")).trim()) };
					response = jdbcTemplate.update(sql, values1) > 0;
				}
				if (response && param.get("licenseetypecode") != null
						&& ((String) param.get("licenseetypecode")).length() > 0) {
					JSONParser parser = new JSONParser();
					Object obj = parser.parse((String) param.get("listLicenseesregistrationsm"));
					JSONArray listLicenseesregistrationsm = (JSONArray) obj;
	
					if (listLicenseesregistrationsm.size() > 0) {
						sql = "INSERT INTO nicobps.licensesregistrationst(usercode,licenseeregistrationcode,registrationdescription) VALUES(?,?,?)";
						List<Object[]> list = new ArrayList<>();
						for (int i = 0; i < listLicenseesregistrationsm.size(); i++) {
							JSONObject row = (JSONObject) listLicenseesregistrationsm.get(i);
							Boolean ischecked = (Boolean) row.get("ischecked");
							Short licenseeregistrationcode = Short.valueOf((String) row.get("licenseeregistrationcode"));
							String registrationdescription = (String) row.get("registrationdescription");
							if (ischecked) {
								list.add(new Object[] { usercode, licenseeregistrationcode, registrationdescription });
							}
						}
						response = jdbcTemplate.batchUpdate(sql, list).length > 0;
					}
				}
			}
		} catch (Exception e) {
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.createUser(Map<String,String> param) : " + e);
		}
		return response;
	}
	@Override
	public boolean updateUser(Userlogin user) {
		
		String sql = "";
		boolean response = false;
		try {
			sql = "UPDATE nicobps.userlogins SET  " 
					+ "            username = ?, userpassword = ?, "
					+ " 		   fullname = ?, mobileno = ?, designation = ?   "
					+ "    WHERE usercode = ?";
			
			Object[] param = new Object[] { 
					user.getUsername(), user.getUserpassword(), 
					user.getFullname(), user.getMobileno(), user.getDesignation(), user.getUsercode() 
			};
			response = jdbcTemplate.update(sql, param) > 0;
		} catch (Exception e) {
			response = false;
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.updateUser(Userlogin user)  : " + e);
		}
		return response;
	}
	
	@Override
	public List<Userlogin> listUsers() {

		List<Userlogin> list = null;
		try {
			String sql = "Select usercode, username, fullname, mobileno, designation, " + 
					"       enabled, entrydate From nicobps.userlogins Order by username";
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Userlogin>(Userlogin.class));
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Error in DaoUserManagement.listUsers()  : " + e);
		}
		return (list != null) ? list : new LinkedList();
	}

	@Override
	public boolean submitEnclosureDetails(Map<String, Object> param) {
		boolean response = false;
		String sql = null;
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse((String) param.get("listEnclosures"));
			JSONArray listEnclosures = (JSONArray) obj;
			Integer usercode = Integer.valueOf((String) param.get("usercode"));

			if (listEnclosures.size() > 0) {
				sql = "DELETE FROM  nicobps.licenseesenclosures WHERE usercode=?";
				Object[] values = { usercode };
				jdbcTemplate.update(sql, values);

				sql = "INSERT INTO nicobps.licenseesenclosures(usercode,enclosurecode,enclosureimage) VALUES(?,?,?)";
				List<Object[]> list = new ArrayList<>();
				for (int i = 0; i < listEnclosures.size(); i++) {
					JSONObject row = (JSONObject) listEnclosures.get(i);
					Boolean ischecked = (Boolean) row.get("ischecked");
					if (ischecked) {
						Short enclosurecode = Short.valueOf((String) row.get("enclosurecode"));
						byte[] file = Base64.getDecoder().decode(((String) row.get("filecontant")).split(",")[1]);
						list.add(new Object[] { usercode, enclosurecode, file });
					}
				}
				response = jdbcTemplate.batchUpdate(sql, list).length > 0;
			}
		} catch (Exception e) {
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.submitEnclosureDetails(Map<String,String> param) : " + e);
		}
		return response;
	}

	@Override
	public Userlogin getUserlogin(final String username) {
		Userlogin user = new Userlogin();
		try {
			String sql = "SELECT usercode,username,fullname,mobileno,designation FROM userlogins WHERE username=?";
			Object[] criteria = { username };
			user = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Userlogin.class), criteria);
		} catch (Exception e) {
			System.out.println("Error in DaoUserManagement.getUserlogin(final String username) : " + e);
		}
		return user;
	}

	@Override
	public boolean updatePassword(Map<String, Object> param) {
		boolean response = false;
		String sql = null;
		try {
			String userpassword = (String) param.get("userpassword");
			Integer usercode = Integer.valueOf((String) param.get("usercode"));
			sql = "UPDATE nicobps.userlogins SET userpassword=? WHERE usercode=?";
			Object[] values = { userpassword, usercode };
			response = jdbcTemplate.update(sql, values) > 0;
		} catch (Exception e) {
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.updatePassword(Map<String,String> param) : " + e);
		}
		return response;
	}

	@Override
	public List<Pageurls> getPageUrls(final Integer usercode) {
		List<Pageurls> urls=null;
		try {
			String sql = "Select url.* "
					+ "From nicobps.UserPages up,masters.pageurls url "
					+ "WHERE up.urlcode=url.urlcode "
					+ "AND up.usercode=? "
					+ "ORDER BY parentorder,submenuorder,subsubmenuorder";
			Object[] criteria = { usercode };
			urls = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Pageurls.class),criteria);
		} catch (Exception e) {
			System.out.println("Error in DaoUserManagement.getPageUrls(final Integer usercode) : " + e);
		}
		return urls;
	}
}
