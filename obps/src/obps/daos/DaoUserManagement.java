package obps.daos;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import obps.models.AppEnclosures;
import obps.models.FeeMaster;
import obps.models.FeeTypes;
import obps.models.LicenseesEnclosures;
import obps.models.LicenseesRegistrationsm;
import obps.models.Occupancies;
import obps.models.Offices;
import obps.models.Pageurls;
import obps.models.SubOccupancies;
import obps.models.Usages;
import obps.models.Userlogin;
import obps.util.application.CommonMap;
import obps.util.application.DaoUtil;
import obps.util.application.DaoUtilInterface;
import obps.util.application.ServiceUtilInterface;

@Transactional
@Repository("daoUserManagement")
public class DaoUserManagement implements DaoUserManagementInterface {

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired private ServiceUtilInterface serviceUtilInterface;

	@Autowired
	private DaoUtilInterface daoUtilInterface;

	@Autowired
	public void createTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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
					( param.get("mobileno")).toString().trim(), ((String) param.get("designation")).trim() };
			response = jdbcTemplate.update(sql, values) > 0;

			if (param.get("usertype") == null && param.get("licenseetypecode") != null
					&& ((String) param.get("licenseetypecode")).length() > 0) {
				if (response) {
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
				if (response) {
					JSONParser parser = new JSONParser();
					Object obj = parser.parse((String) param.get("listLicenseesregistrationsm"));
					JSONArray listLicenseesregistrationsm = (JSONArray) obj;

					if (listLicenseesregistrationsm.size() > 0) {
						sql = "INSERT INTO nicobps.licensesregistrationst(usercode,licenseeregistrationcode,registrationdescription) VALUES(?,?,?)";
						List<Object[]> list = new ArrayList<>();
						for (int i = 0; i < listLicenseesregistrationsm.size(); i++) {
							JSONObject row = (JSONObject) listLicenseesregistrationsm.get(i);
							Boolean ischecked = (Boolean) row.get("ischecked");
							Short licenseeregistrationcode = Short
									.valueOf((String) row.get("licenseeregistrationcode"));
							String registrationdescription = (String) row.get("registrationdescription");
							if (ischecked) {
								list.add(new Object[] { usercode, licenseeregistrationcode, registrationdescription });
							}
						}
						response = jdbcTemplate.batchUpdate(sql, list).length > 0;
					}
				}
				if (response) {
					Long afrcode = Long.valueOf((String) param.get("afrcode"));
					String applicationcode = usercode + "";
					Short modulecode = Short.valueOf("1");
					Short fromprocesscode = Short.valueOf("1");
					Short toprocesscode = Short.valueOf("2");
					Integer fromusercode = usercode;
					String remarks = "Initial Registration";

					sql = "INSERT INTO nicobps.applicationflowremarks(afrcode,applicationcode,modulecode,fromprocesscode,toprocesscode,fromusercode,remarks) "
							+ "VALUES (?,?,?,?,?,?,?) ";
					Object[] values2 = { afrcode, applicationcode, modulecode, fromprocesscode, toprocesscode,
							fromusercode, remarks };
					response = jdbcTemplate.update(sql, values2) > 0;
				}
				if (response) {
					Short urlcode = Short.valueOf("0");
					String userpagecode = usercode + "U" + urlcode;
					sql = "INSERT INTO nicobps.userpages(userpagecode,usercode,urlcode) VALUES (?,?,?) ";
					Object[] values3 = { userpagecode, usercode, urlcode };
					response = jdbcTemplate.update(sql, values3) > 0;
				}
			}
			if (param.get("usertype") != null && param.get("usertype").equals("BACKEND_USER")) {
				sql = "INSERT INTO nicobps.useroffices(usercode, officecode)VALUES (?, ?)";
				response = jdbcTemplate.update(sql, new Object[] {usercode,Integer.valueOf(param.get("officecode").toString())}) > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.createUser(Map<String,String> param) : " + e);
		}
		return response;
	}

	@Override
	public boolean updateUser(Map<String,Object> user) {
		String sql = "";
		boolean response = false;
		try {
			sql = "UPDATE nicobps.userlogins SET username = ?, userpassword = ?,fullname = ?, mobileno = ?, designation = ? WHERE usercode = ?";
			Object[] param = new Object[] { user.get("username"), user.get("userpassword"), user.get("fullname"),
					user.get("mobileno"), user.get("designation"), user.get("usercode") };
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
	public List<Userlogin> listOfficeUsers() {
		List<Userlogin> list = null;
		try {
			String sql = "Select u.usercode, username, fullname, mobileno, designation, "
					+ "       u.enabled, u.entrydate, officename1 From nicobps.userlogins u "
					+ "INNER JOIN nicobps.useroffices uo on u.usercode=uo.usercode "
					+ "INNER JOIN masters.offices o on uo.officecode=o.officecode " + "Order by username";
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Userlogin>(Userlogin.class));
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Error in DaoUserManagement.listUsers()  : " + e);
		}
		return (list != null) ? list : new LinkedList();
	}

	@Override
	public List<Userlogin> listOfficeUsers(Integer officecode) {
		List<Userlogin> list = null;
		try {
			String sql = "Select u.usercode, username, fullname, mobileno, designation, "
					+ "       u.enabled, u.entrydate, officename1 From nicobps.userlogins u "
					+ "INNER JOIN nicobps.useroffices uo on u.usercode=uo.usercode "
					+ "INNER JOIN masters.offices o on uo.officecode=o.officecode " + "where o.officecode=? "
//					+ "or registeringofficecode=? "
					+ "Order by username";
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Userlogin>(Userlogin.class),
					new Object[] { officecode });
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Error in DaoUserManagement.listUsers()  : " + e);
		}
		return (list != null) ? list : new LinkedList();
	}

	@Override
	public boolean submitLicenseesenclosures(LicenseesEnclosures licenseesenclosures) 
	{
		boolean response = false;
		String sql = null;
		try 
		{
			Integer usercode = Integer.valueOf(licenseesenclosures.getUsercode());

			if (licenseesenclosures.getAppenclosures().size() > 0) 
			{			
				String sql_insert = "INSERT INTO nicobps.licenseesenclosures(usercode,enclosurecode,enclosureimage) VALUES(?,?,?)";
				String sql_update = "UPDATE nicobps.licenseesenclosures SET enclosureimage=? WHERE usercode=? AND enclosurecode=?";
				List<Object[]> list_insert = new ArrayList<>();
				List<Object[]> list_update = new ArrayList<>();
				for (AppEnclosures AE:licenseesenclosures.getAppenclosures()) 
				{
					if(AE.getEnclosurecode()!=null && AE.getFileContent()!=null) 
					{
						Short enclosurecode = Short.valueOf(AE.getEnclosurecode());
						byte[] file = AE.getFileContent().getBytes();				
						
						String sql_check = "SELECT * FROM  nicobps.licenseesenclosures WHERE usercode=? AND enclosurecode=?";				
						if(serviceUtilInterface.checkExistance(sql_check, new Object[] { usercode,Short.valueOf(AE.getEnclosurecode()) })) {
							list_update.add(new Object[] { file,usercode, enclosurecode });
						}else {
							list_insert.add(new Object[] { usercode, enclosurecode, file });
						}
					}
				}
				response = (jdbcTemplate.batchUpdate(sql_insert, list_insert).length > 0 || jdbcTemplate.batchUpdate(sql_update, list_update).length > 0);
				
				if (response) {					
					Long afrcode = Long.valueOf(licenseesenclosures.getAfrcode());
					String applicationcode = usercode + "";
					Short modulecode = Short.valueOf("1");
					Short fromprocesscode = Short.valueOf("2");
					Short toprocesscode = Short.valueOf("3");
					Integer fromusercode = usercode;
					String remarks = "Upload Enclosures";

					sql = "INSERT INTO nicobps.applicationflowremarks(afrcode,applicationcode,modulecode,fromprocesscode,toprocesscode,fromusercode,remarks) "
							+ "VALUES (?,?,?,?,?,?,?) ";
					Object[] values2 = { afrcode, applicationcode, modulecode, fromprocesscode, toprocesscode,fromusercode, remarks };
					response = jdbcTemplate.update(sql, values2) > 0;
				}
				if (response) {
					Short urlcode = Short.valueOf("16");
					String userpagecode = usercode + "U" + urlcode;

					sql = "DELETE FROM nicobps.userpages WHERE usercode=? AND urlcode=?";
					Object[] values1 = { usercode, urlcode };
					jdbcTemplate.update(sql, values1);

					sql = "INSERT INTO nicobps.userpages(userpagecode,usercode,urlcode) VALUES (?,?,?) ";
					Object[] values3 = { userpagecode, usercode, urlcode };
					response = jdbcTemplate.update(sql, values3) > 0;
				}
			}
		} catch (Exception e) {
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.submitEnclosureDetails(Map<String,String> param) : " + e);
		}
		return response;
	}	
	
	/*
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
				if (response) {
					Long afrcode = Long.valueOf((String) param.get("afrcode"));
					String applicationcode = usercode + "";
					Short modulecode = Short.valueOf("1");
					Short fromprocesscode = Short.valueOf("2");
					Short toprocesscode = Short.valueOf("3");
					Integer fromusercode = usercode;
					String remarks = "Upload Enclosures";

					sql = "INSERT INTO nicobps.applicationflowremarks(afrcode,applicationcode,modulecode,fromprocesscode,toprocesscode,fromusercode,remarks) "
							+ "VALUES (?,?,?,?,?,?,?) ";
					Object[] values2 = { afrcode, applicationcode, modulecode, fromprocesscode, toprocesscode,
							fromusercode, remarks };
					response = jdbcTemplate.update(sql, values2) > 0;
				}
				if (response) {
					Short urlcode = Short.valueOf("16");
					String userpagecode = usercode + "U" + urlcode;

					sql = "DELETE FROM nicobps.userpages WHERE usercode=? AND urlcode=?";
					Object[] values1 = { usercode, urlcode };
					jdbcTemplate.update(sql, values1);

					sql = "INSERT INTO nicobps.userpages(userpagecode,usercode,urlcode) VALUES (?,?,?) ";
					Object[] values3 = { userpagecode, usercode, urlcode };
					response = jdbcTemplate.update(sql, values3) > 0;
				}

			}
		} catch (Exception e) {
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.submitEnclosureDetails(Map<String,String> param) : " + e);
		}
		return response;
	}
	*/

	@Override
	public Userlogin getUserlogin(final String username) {
		Userlogin user = new Userlogin();
		try {
			//String sql = "SELECT usercode,username,fullname,mobileno,designation FROM userlogins WHERE username=?";
			String sql = "SELECT UL.usercode,username,fullname,mobileno,designation,licenseetypecode FROM nicobps.userlogins UL "
					   + "LEFT OUTER JOIN nicobps.licensees L ON L.usercode=UL.usercode "
					   + "WHERE username=?";
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
	public List<Pageurls> getPageUrls() {
		List<Pageurls> urls = null;
		try {
			String sql = "Select * From masters.pageurls /*where showinmenu='Y'*/ ORDER BY parentorder,parent,submenuorder,submenu,subsubmenuorder,subsubmenu ";
			System.out.println(" SQL  1: "  + sql);
			urls = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Pageurls.class));
			
		} catch (Exception e) {
			System.out.println("Error in DaoUserManagement.getPageUrls() : " + e);
		}
		return urls;
	}

	@Override
	public List<Pageurls> getPageUrls(final Integer usercode) {
		List<Pageurls> urls = null;
		try {
			String sql = "Select url.* From nicobps.UserPages up,masters.pageurls url WHERE 1 = 1 and url.showinmenu != 'N' AND up.urlcode=url.urlcode AND up.usercode=? /*and showinmenu='Y' */ "
					+ "ORDER BY parentorder,parent,submenuorder,submenu,subsubmenuorder,subsubmenu";
			System.out.println(" SQL  2 : "  + sql);
			Object[] criteria = { usercode };
			urls = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Pageurls.class), criteria);
		} catch (Exception e) {
			System.out.println("Error in DaoUserManagement.getPageUrls(final Integer usercode) : " + e);
		}
		return urls;
	}

	@Override
	public boolean savePageurlsDao(Pageurls url) {
		boolean response = false;
		try {
			MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("pageurl", url.getPageurl())
					.addValue("subsubmenu", url.getSubsubmenu()).addValue("subsubmenuicon", url.getSubsubmenuicon())
					.addValue("submenu", url.getSubmenu()).addValue("submenuicon", url.getSubmenuicon())
					.addValue("parent", url.getParent()).addValue("parenticon", url.getParenticon())
					.addValue("urlcode", url.getUrlcode()).addValue("parentorder", url.getParentorder())
					.addValue("submenuorder", url.getSubmenuorder())
					.addValue("subsubmenuorder", url.getSubsubmenuorder());

			if (url.getUrlcode() > -1) {
				String sql = (new StringBuilder("UPDATE masters.pageurls "))
						.append("   SET pageurl=:pageurl, subsubmenu=:subsubmenu, subsubmenuicon=:subsubmenuicon,  ")
						.append("   submenu=:submenu, submenuicon=:submenuicon, parent=:parent, parenticon=:parenticon,  ")
						.append("   subsubmenuorder=:subsubmenuorder, submenuorder=:submenuorder, parentorder=:parentorder  ")
						.append(" WHERE urlcode=:urlcode ").toString();
				if (namedParameterJdbcTemplate.update(sql, parameters) < 0) {
					return false;
				}
			} else {
				int max = daoUtilInterface.getMax("masters", "pageurls", "urlcode");
				url.setUrlcode(max + 1);
				parameters.addValue("urlcode", url.getUrlcode());
				String sql = (new StringBuilder("INSERT INTO masters.pageurls("))
						.append("urlcode, pageurl, subsubmenu, subsubmenuicon, ")
						.append("submenu, submenuicon, parent, parenticon,")
						.append("subsubmenuorder, submenuorder, parentorder)")
						.append("VALUES (:urlcode, :pageurl, :subsubmenu, :subsubmenuicon, ")
						.append(":submenu, :submenuicon, :parent, :parenticon, ")
						.append(":subsubmenuorder,:submenuorder, :parentorder)").toString();
				if (namedParameterJdbcTemplate.update(sql, parameters) < 0) {
					return false;
				}
			}
			response = true;
		} catch (Exception ex) {
			System.out.println("Error in savePageurlsDao(Pageurls url) ::" + ex);
		}
		return response;
	}

	@Override
	public List<Pageurls> getMappedPageurls(Integer usercode) {
		ObjectMapper mapper = new ObjectMapper();
		List<Pageurls> urllist = null;
		List<Map<String, Object>> rowList = null;
		try {
			String sql = "Select url.* From nicobps.UserPages up,masters.pageurls url "
					+ "WHERE up.urlcode=url.urlcode and usercode=:usercode /* and showinmenu='Y' */ "
					+ "ORDER BY parentorder,parent,submenuorder,submenu,subsubmenuorder,subsubmenu";
			System.out.println(" SQL  3 : "  + sql);
			MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("usercode", usercode);
			rowList = (List<Map<String, Object>>) namedParameterJdbcTemplate.queryForList(sql, parameters);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("\n\nError in getMappedPageurls(Integer usercode) " + ex);
		}
		if (rowList != null) {
			urllist = new LinkedList<Pageurls>();
			for (Map<String, Object> row : rowList) {
				urllist.add(mapper.convertValue(row, Pageurls.class));
			}
		}
		return (urllist != null) ? urllist : new LinkedList();
	}

	@Override
	public boolean mapUserpages(List<Map<String, Object>> upage) {
		boolean response = false;
		try {
			String sql = "DELETE From nicobps.UserPages WHERE usercode=? ";
			if (jdbcTemplate.update(sql, upage.get(0).get("usercode")) < 0) {
				return false;
			}
			/////////////////////////////////////
			sql = "INSERT INTO nicobps.userpages(userpagecode, usercode, urlcode) VALUES (?, ?, ?)";
			for (Map<String, Object> up : upage) {
				jdbcTemplate.update(sql,
						up.get("usercode") + "U" + ((Map<String, Object>) up.get("url")).get("urlcode"),
						up.get("usercode"), ((Map<String, Object>) up.get("url")).get("urlcode"));
			}
			response = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("\n\nError in mapUserpages(List<Map<String,Object>> upage) " + ex);
		}
		return response;
	}

	

}
