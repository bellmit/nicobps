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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import obps.models.Enclosures;
import obps.models.Modules;
import obps.models.Offices;
import obps.models.Pageurls;
import obps.models.PaymentModes;
import obps.models.Userlogin;
import obps.util.application.CommonMap;
import obps.util.application.DaoUtil;
import obps.util.application.DaoUtilInterface;

@Transactional
@Repository("daoEnclosureManagement")
public class DaoEnclosureManagement implements DaoEnclosureManagementInterface {

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
	public boolean initenclosures(Map<String, Object> param) 
	{
		System.out.println("asdasdasdadasdddddENCMINE");
		boolean response = false;
		String sql = null;
		Long enclosurecode = (Long) param.get("enclosurecode");
		String enclosuredescription = "";
		if(param.get("enclosuredescription")!=null)
			enclosuredescription=((String) param.get("enclosuredescription")).trim();
		
		System.out.println("HERE");
		try 
		{
			sql = "INSERT INTO masters.enclosures(enclosurecode,enclosurename,enclosuredescription,enabled) "
					+ "VALUES (?,?,?,?) ";
			Object[] values = { enclosurecode, ((String) param.get("enclosurename")).trim(),
					enclosuredescription,'Y' };
			response = jdbcTemplate.update(sql, values) > 0;
			
						
			
		} catch (Exception e) {
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.initenclosure(Map<String,String> param) : " + e);
		}
		System.out.println(response);
		return response;
	}
	@Override
	public boolean initoffices(Map<String, Object> param) {
		boolean response = false;
		String sql = null;
		Long officecode = (Long) param.get("officecode");
		
		
		String officename3="",officeshortname="",emailid="",emailidpassword="",smsusername="",smspassword="";
		
		if(param.get("officename3")!=null){
			officename3=((String) param.get("officename3")).trim();
		}
		if(param.get("officeshortname")!=null){
			officeshortname=((String) param.get("officeshortname")).trim();
		}
		if(param.get("emailid")!=null){
			emailid=((String) param.get("emailid")).trim();
		}
		if(param.get("emailidpassword")!=null){
			emailidpassword=((String) param.get("emailidpassword")).trim();
		}
		if(param.get("smsusername")!=null){
			smsusername=((String) param.get("smsusername")).trim();
		}
		if(param.get("smspassword")!=null){
			smspassword=((String) param.get("smspassword")).trim();
		}
		
		
		
		
		
		try 
		{
			sql = "INSERT INTO masters.offices(officecode,officename1,officename2,"
					+ "officename3,officeshortname,signatoryname,"
					+ "signatorydesignation,emailid,emailidpassword,smsusername,smspassword) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
			Object[] values = { officecode, 
					((String) param.get("officename1")).trim(),
					((String) param.get("officename2")).trim(),
					officename3,
					officeshortname,
					
					((String) param.get("signatoryname")).trim(),
					((String) param.get("signatorydesignation")).trim(),
					emailid,
					emailidpassword,
					smsusername,
					smspassword
					};
			response = jdbcTemplate.update(sql, values) > 0;
			
						
			
		} catch (Exception e) {
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.initoffice(Map<String,String> param) : " + e);
		}
		return response;
	}

		
	
	
	@Override
	public boolean updateInitEnclosure(Map<String, Object> param) 
	{
		String sql = "";
		boolean response = false;
		String enclosuredescription = "";
		if(param.get("enclosuredescription")!=null)
			enclosuredescription=((String) param.get("enclosuredescription")).trim();
		try 
		{
			sql = "UPDATE masters.enclosures SET enclosurename = ?, enclosuredescription = ? WHERE enclosurecode = ?";
			Object[] values = new Object[] { ((String) param.get("enclosurename")).trim(), enclosuredescription,param.get("enclosurecode") };
			response = jdbcTemplate.update(sql, values) > 0;
		} catch (Exception e) {
			response = false;
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.updateInitEnclosure(Enclosures enclosure)  : " + e);
		}
		return response;
	}
	
	@Override
	public boolean updateInitOffices(Map<String, Object> offices) {
		String sql = "";
		boolean response = false;
		try 
		{
			sql = "UPDATE masters.offices SET officename1 = ?, officename2 = ?,officename3=?,"
					+ "officeshortname = ?, signatoryname = ?,signatorydesignation=?,"
					+ "emailid = ?, emailidpassword = ?,smsusername=?,"
					+ "smspassword = ?"
					+ " WHERE officecode = ?"					;
			Object[] param = new Object[] { 
					offices.get("officename1"), 
					offices.get("officename2"),
					offices.get("officename3"), 
					offices.get("officeshortname"), 
					offices.get("signatoryname"),
					offices.get("signatorydesignation"), 
					offices.get("emailid"), 
					offices.get("emailid"),
					offices.get("smsusername"), 
					offices.get("smspassword"),
					offices.get("officecode")
					};
			response = jdbcTemplate.update(sql, param) > 0;
		} catch (Exception e) {
			response = false;
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.updateInitOffices(Offices offices)  : " + e);
		}
		return response;
	}

	
	
	@Override
	public List<Enclosures> listEnclosures() {

		List<Enclosures> list = null;
		try {
			String sql = "Select enclosurecode, enclosurename, enclosuredescription, "
					+ "enabled From masters.enclosures Order by enclosurename";
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Enclosures>(Enclosures.class));
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Error in DaoUserManagement.listEnclosures()  : " + e);
		}
		return (list != null) ? list : new LinkedList();
		
	}
	@Override
	public List<Offices> listOffices() {
		List<Offices> list = null;
		try {
			String sql = "Select * From masters.offices";
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Offices>(Offices.class));
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Error in DaoUserManagement.listOffices()  : " + e);
		}
		return (list != null) ? list : new LinkedList();
	}
	@Override
	public List<Enclosures> getMappedEnclosures(Integer modulecode) {
		System.out.println("ModuleCode"+modulecode);
		ObjectMapper mapper = new ObjectMapper();
		List<Enclosures> enclosurelist = null;
		List<Map<String, Object>> rowList = null;
		try 
		{
			String sql ="Select enclosure.enclosurecode From masters.enclosures up,masters.modulesenclosures enclosure "
					   +"WHERE up.enclosurecode=enclosure.enclosurecode and modulecode=:modulecode ";
			MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("modulecode", modulecode);
			rowList = (List<Map<String, Object>>) namedParameterJdbcTemplate.queryForList(sql, parameters);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("\n\nError in getMappedEnclosures(Integer modulecode) " + ex);
		}
		if (rowList != null) {
			enclosurelist = new LinkedList<Enclosures>();
			for (Map<String, Object> row : rowList) {
				enclosurelist.add(mapper.convertValue(row, Enclosures.class));
			}
		}
		return (enclosurelist != null) ? enclosurelist : new LinkedList();
	}
	@Override
	public List<Modules> listModules() {
		List<Modules> list = null;
		try {
			String sql = "Select * From masters.modules";
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Modules>(Modules.class));
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Error in DaoUserManagement.listModules()  : " + e);
		}
		return (list != null) ? list : new LinkedList();
	}
	@Override
	public boolean mapModuleEnclosures(List<Map<String, Object>> modulesenclosures) {
		
		
		
		boolean response = false;
		try 
		{
			String sql = "DELETE From masters.modulesenclosures WHERE modulecode=? ";
			if (jdbcTemplate.update(sql, modulesenclosures.get(0).get("modulecode")) < 0) {
				return false;
			}
			/////////////////////////////////////
			sql = "INSERT INTO masters.modulesenclosures(modulecode, enclosurecode) VALUES (?, ?)";
			for (Map<String,Object> up : modulesenclosures) {

				jdbcTemplate.update(sql, up.get("modulecode"), ((Map<String,Object>)up.get("enclosurecode")).get("enclosurecode"));
			}
			response = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("\n\nError in mapModuleEnclosures(List<Map<String,Object>> modulesenclosures) " + ex);
		}
		return response;
	}
	@Override
	public List<PaymentModes> listPaymentModes() {
		List<PaymentModes> list = null;
		try {
			String sql = "Select * From masters.paymentmodes";
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<PaymentModes>(PaymentModes.class));
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Error in DaoUserManagement.listModules()  : " + e);
		}
		return (list != null) ? list : new LinkedList();
	}
	@Override
	public List<PaymentModes> getMappedPaymentModes(Integer officecode) {
		System.out.println("officecode"+officecode);
		ObjectMapper mapper = new ObjectMapper();
		List<PaymentModes> paymentmodeslist = null;
		List<Map<String, Object>> rowList = null;
		try 
		{
			String sql ="Select paymentmode.paymentmodecode From masters.paymentmodes up,masters.officespaymentmodes paymentmode "
					   +"WHERE up.paymentmodecode=paymentmode.paymentmodecode and officecode=:officecode ";
			MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("officecode", officecode);
			rowList = (List<Map<String, Object>>) namedParameterJdbcTemplate.queryForList(sql, parameters);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("\n\nError in getMappedPaymentModes(Integer officecode) " + ex);
		}
		if (rowList != null) {
			paymentmodeslist = new LinkedList<PaymentModes>();
			for (Map<String, Object> row : rowList) {
				paymentmodeslist.add(mapper.convertValue(row, PaymentModes.class));
			}
		}
		return (paymentmodeslist != null) ? paymentmodeslist : new LinkedList();
	}
	@Override
	public boolean mapOfficesPayments(List<Map<String, Object>> officespayments) {
		
		boolean response = false;
		try 
		{
			String sql = "DELETE From masters.officespaymentmodes WHERE officecode=? ";
			if (jdbcTemplate.update(sql, officespayments.get(0).get("officecode")) < 0) {
				return false;
			}
			/////////////////////////////////////
			sql = "INSERT INTO masters.officespaymentmodes(officecode, paymentmodecode) VALUES (?, ?)";
			for (Map<String,Object> up : officespayments) {

				jdbcTemplate.update(sql, up.get("officecode"), ((Map<String,Object>)up.get("paymentmodecode")).get("paymentmodecode"));
			}
			response = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("\n\nError in mapOfficesPayments(List<Map<String,Object>> officespayments) " + ex);
		}
		return response;
	}
	
	
	
	@Override
	public boolean checkExistance(String sql, Object[] values) {
		Boolean response = false;
		Integer res=0;
		
		try {
			
			

			res = jdbcTemplate.queryForObject(sql, values, Integer.class);

			System.out.println("Response"+res);
			
		}catch (Exception e) {
			e.getStackTrace();
			
			System.out.println("Error in DaoUserManagement.initenclosure(Map<String,String> param) : " + e);
		}
		if(res>0) {
			response=true;
		}
		return response;
	}
	
	@Override
	public String validateInitEnclosure(Map<String, Object> param) {
		System.out.println("asdsdd");
		String response = "";
		String encldesc="";
		String enclname=((String) param.get("enclosurename")).trim();
		if(param.get("enclosuredescription")!=null)
			encldesc=((String) param.get("enclosuredescription")).trim();
		
		 Pattern p = Pattern.compile("[^A-Za-z_ ]");
	     Matcher m1 = p.matcher(enclname);
	     Matcher m2 = p.matcher(encldesc);
	     boolean b1 = m1.find();
	     boolean b2 = m2.find();
	     if (b1)
	         response="m1";
	      
//	     if (b2)
//	         response="m2";
	     if(enclname.length()>50)
	    	 response="50";
	     if(encldesc.length()>255)
	    	 response="255";
	     
	    	 
		
		return response;
		
	}
	@Override
	public String validateInitOffices(Map<String, Object> param) {
		System.out.println(param);
		String response = "";
		Pattern p = Pattern.compile("[^A-Za-z_ ]");
		String officename1,officename2,officename3="",officeshortname="",signatoryname="",signatorydesignation="",smsusername="",emailidpassword="",smspassword="";
		officename1=((String) param.get("officename1")).trim();
		Matcher of1 = p.matcher(officename1);
		boolean b1 = of1.find();
		if(b1)
			response= "officename1";
		officename2=((String) param.get("officename2")).trim();
		Matcher of2 = p.matcher(officename2);
		b1 = of2.find();
		if(b1)
			response = "officename2";
		if(param.get("officename3")!=null){
			officename3=((String) param.get("officename3")).trim();
			Matcher of3 = p.matcher(officename3);
			b1 = of3.find();
			if(b1)
				response = "officename3";
		}
		
		if(param.get("signatoryname")!=null){
			signatoryname=((String) param.get("signatoryname")).trim();
			Matcher offsignatory = p.matcher(signatoryname);
			b1 = offsignatory.find();
			if(b1)
				response = "offsignatory";
		}
		if(param.get("signatorydesignation")!=null){
			signatorydesignation=((String) param.get("signatorydesignation")).trim();
			Matcher offshortdes = p.matcher(signatorydesignation);
			b1 = offshortdes.find();
			if(b1)
				response = "officeshortdes";
		}
		if(param.get("officeshortname")!=null){
			officeshortname=((String) param.get("officeshortname")).trim();
			Matcher offshort = p.matcher(officeshortname);
			b1 = offshort.find();
			if(b1)
				response = "offshort";
		}
				
		if(param.get("smsusername")!=null){
			smsusername=((String) param.get("smsusername")).trim();
			Matcher sms = p.matcher(smsusername);
			b1 = sms.find();
			if(b1)
				response = "sms";
		}
		
		if(param.get("emailid")=="")
			System.out.println("asd");
		else if(param.get("emailid")!=null){
			String emailid=((String) param.get("emailid")).trim();
			String regex = "^(.+)@(.+)$";
		      final Pattern p2 = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	         Matcher matcher = p2.matcher(emailid);
		    Boolean b= matcher.find();
		    if(!b)
		    response="emailidnotcorrect";
		    if(emailid.length()>255)
		    	 response="9";
		}
		if(param.get("emailidpassword")!=null){
			emailidpassword=((String) param.get("emailidpassword")).trim();
		}
		if(param.get("smspassword")!=null){
			smspassword=((String) param.get("smspassword")).trim();
		}
		
		
		 if(officename1.length()>255)
	    	 response="1";
	     if(officename2.length()>255)
	    	 response="2";
	     if(officename3.length()>255)
	    	 response="3";
	     if(officeshortname.length()>50)
	    	 response="4";
	     if(signatoryname.length()>255)
	    	 response="5";
	     if(signatorydesignation.length()>50)
	    	 response="6";
	     if(smsusername.length()>25)
	    	 response="7";
	     if(smspassword.length()>100)
	    	 response="8";
	     
	     if(emailidpassword.length()>255)
	    	 response="10";
	     
	     
		
		return response;
	}

	
	
	
	
	
}
