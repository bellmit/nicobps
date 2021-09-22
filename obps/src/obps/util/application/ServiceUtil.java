/*@author Avijit Debnath*/

package obps.util.application;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import obps.models.Audittrail;
import obps.models.DashboardData;
import obps.models.UserApplications;
import obps.models.Userlogin;
import obps.util.common.Utilty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

@Service("serviceUtil")
public class ServiceUtil implements ServiceUtilInterface {
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true);
	}

	@Autowired
	private DaoUtilInterface daoUtilInterface;
	// @Autowired private DomainUtilInterface domainUtilInterface;

	@Override
	public List<CommonMap> listCommonMap(final String sql) {
		return daoUtilInterface.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listCommonMap(final String sql, final Object[] criteria) {
		return daoUtilInterface.listCommonMap(sql, criteria);
	}

	@Override
	public int getCount(final String sql) {
		return daoUtilInterface.getCount(sql);
	}

	@Override
	public int getCount(String sql, Object[] criteria) {
		return daoUtilInterface.getCount(sql, criteria);
	}

	@Override
	public Long getMaxValue(final String sql) {
		return daoUtilInterface.getMaxValue(sql);
	}

	@Override
	public Long getMaxValue(final String sql, final Object[] criteria) {
		return daoUtilInterface.getMaxValue(sql, criteria);
	}

	@Override
	public boolean checkExistance(String sql) {
		return daoUtilInterface.checkExistance(sql);
	}

	@Override
	public boolean checkExistance(String sql, Object[] criteria) {
		return daoUtilInterface.checkExistance(sql, criteria);
	}

	@Override
	public String getStringObject(String sql) {
		return daoUtilInterface.getStringObject(sql);
	}

	@Override
	public String getStringObject(String sql, Object[] criteria) {
		return daoUtilInterface.getStringObject(sql, criteria);
	}

	@Override
	public byte[] getFileByte(String sql, Object[] criteria) {
		return daoUtilInterface.getFileByte(sql, criteria);
	}

	@Override
	public byte[] getBytes(String sql, Object[] criteria) {
		return daoUtilInterface.getBytes(sql, criteria);
	}

	//////////////////////////////////////////////////
	@Override
	public Integer getMax(String schema, String table, String column) {
		return daoUtilInterface.getMax(schema, table, column);
	}

	@Override
	public <T> List<T> listGeneric(Class<T> clazz, String sql) {
		return daoUtilInterface.listGeneric(clazz, sql);
	}

	@Override
	public <T> List<T> listGeneric(Class<T> clazz, String sql, Object[] params) {
		return daoUtilInterface.listGeneric(clazz, sql, params);
	}

	@Override
	public List<Map<String, Object>> listGeneric(String sql) {
		return daoUtilInterface.listGeneric(sql);
	}

	@Override
	public List<Map<String, Object>> listGeneric(String sql, Object[] params) {
		return daoUtilInterface.listGeneric(sql, params);
	}

	@Override
	public List<Map<String, Object>> listGenericParameterized(String sql, MapSqlParameterSource param) {
		return daoUtilInterface.listGenericParameterized(sql, param);
	}

	@Override
	public <T> boolean update(String tablename, String sql, Object[] params) {
		return daoUtilInterface.update(tablename, sql, params);
	}

	@Override
	public <T> boolean update(List<BatchUpdateModel> list) {
		return daoUtilInterface.update(list);
	}
	// ===========================================//

	@Override
	public List<CommonMap> listStates() {
		String sql = "SELECT T.statecode AS key,T.statename AS value FROM masters.states T ORDER BY T.statename ";
		return this.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listDistricts() {
		String sql = "SELECT T.districtcode AS key,T.districtname AS value,T.statecode AS value1 FROM masters.districts T ORDER BY T.districtname ";
		return this.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listDistricts(final Short statecode) {
		String sql = "SELECT T.districtcode AS key,T.districtname AS value,T.statecode AS value1 FROM masters.districts T WHERE T.statecode=? ORDER BY T.districtname ";
		Object[] criteria = { statecode };
		return this.listCommonMap(sql, criteria);
	}

	@Override
	public List<CommonMap> listSubDistricts() {
		String sql = "SELECT T.subdistrictcode AS key,T.subdistrictname AS value,T.districtcode AS value1 FROM masters.subdistricts T ORDER BY T.subdistrictname ";
		return this.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listSubDistricts(final Short districtcode) {
		String sql = "SELECT T.subdistrictcode AS key,T.subdistrictname AS value,T.districtcode AS value1 FROM masters.subdistricts T WHERE T.districtcode=? ORDER BY T.subdistrictname ";
		Object[] criteria = { districtcode };
		return this.listCommonMap(sql, criteria);
	}

	@Override
	public List<CommonMap> listSalutations() {
		String sql = "SELECT T.salutationcode AS key,T.salutationdescription AS value FROM masters.salutations T ORDER BY T.salutationdescription ";
		return this.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listLicenseetypes() {
		String sql = "SELECT T.licenseetypecode AS key, T.licenseetypename AS value FROM masters.licenseetypes T WHERE enabled='Y' ORDER BY licenseetypename ";
		return this.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listFeetypes() {
		String sql = "SELECT T.feetypecode AS key, T.feetypedescription AS value FROM masters.feetypes T WHERE enabled='Y' ORDER BY feetypedescription ";
		return this.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listLicenseesregistrationsm() {
		String sql = "SELECT T.licenseeregistrationcode AS key, T.licenseedescription AS value FROM masters.licenseesregistrationsm T WHERE enabled='Y' ORDER BY licenseedescription ";
		return this.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listEnclosures(final Short modulecode) {
		String sql = "SELECT E.enclosurecode AS key,enclosurename AS value FROM masters.enclosures E "
				+ "INNER JOIN masters.modulesenclosures M ON M.enclosurecode=E.enclosurecode "
				+ "WHERE M.modulecode=? ";
		Object[] criteria = { modulecode };
		return this.listCommonMap(sql, criteria);
	}

	@Override
	public List<CommonMap> listEnclosures(final Short modulecode, Integer usercode) {
		// String sql = "SELECT E.enclosurecode AS key,enclosurename AS value,CASE WHEN
		// usercode IS NULL THEN -1 ELSE usercode END AS value1 FROM masters.enclosures
		// E "
		String sql = "SELECT E.enclosurecode AS key,enclosurename AS value,usercode AS value1 FROM masters.enclosures E   "
				+ "INNER JOIN masters.modulesenclosures M ON M.enclosurecode=E.enclosurecode "
				+ "LEFT OUTER JOIN nicobps.licenseesenclosures L ON L.enclosurecode=M.enclosurecode AND L.usercode=? "
				+ "WHERE M.modulecode=? ";
		Object[] criteria = { usercode, modulecode };
		return this.listCommonMap(sql, criteria);
	}

	/////////////////////
	@Override
	public List<CommonMap> listEnclosures(final Short modulecode, Short licenseetypecode) {
		String sql = "SELECT E.enclosurecode AS key,enclosurename AS value,mandatory AS value1 FROM masters.enclosures E  "
				+ "INNER JOIN masters.modulesenclosures M ON M.enclosurecode=E.enclosurecode  "
				+ "WHERE E.enabled='Y' AND M.modulecode=? AND licenseetypecode=? "
				+ "ORDER BY mandatory DESC,E.enclosurecode ";
		Object[] criteria = { modulecode, licenseetypecode };
		return this.listCommonMap(sql, criteria);
	}

	@Override
	public List<CommonMap> listEnclosures(final Short modulecode, Integer usercode, Short licenseetypecode) {
		String sql = "SELECT E.enclosurecode AS key,enclosurename AS value,mandatory AS value1,usercode AS value2 FROM masters.enclosures E   "
				+ "INNER JOIN masters.modulesenclosures M ON M.enclosurecode=E.enclosurecode  "
				+ "LEFT OUTER JOIN nicobps.licenseesenclosures LE ON LE.enclosurecode=M.enclosurecode AND LE.usercode=? "
				+ "WHERE E.enabled='Y' AND  M.modulecode=? AND licenseetypecode=? "
				+ "ORDER BY mandatory DESC,E.enclosurecode ";
		Object[] criteria = { usercode, modulecode, licenseetypecode };
		return this.listCommonMap(sql, criteria);
	}

	@Override
	public List<CommonMap> listEnclosuresNotUploades(final Short modulecode, Integer usercode, Short licenseetypecode) {
		String sql = "SELECT E.enclosurecode AS key,enclosurename AS value,mandatory AS value1 FROM masters.enclosures E  "
				+ "INNER JOIN masters.modulesenclosures M ON M.enclosurecode=E.enclosurecode "
				+ "WHERE E.enabled='Y' AND mandatory='Y' AND M.modulecode=? "
				+ " AND  case when ? != -1 then licenseetypecode=? else licenseetypecode ISNULL end   "
				+ "AND E.enclosurecode NOT IN (SELECT enclosurecode FROM  nicobps.licenseesenclosures WHERE usercode=?) "
				+ "ORDER BY mandatory DESC,E.enclosurecode";
		if (licenseetypecode == null) {
			licenseetypecode = -1;
		}
	
		Object[] criteria = { modulecode, licenseetypecode,licenseetypecode, usercode };
		return this.listCommonMap(sql, criteria);
	}
	/////////////////////
	@Override
	public List<CommonMap> listBpaEnclosures(final Short modulecode, String aplicationcode) {
		String sql = "SELECT E.enclosurecode AS key,enclosurename AS value,mandatory AS value1,applicationcode AS value2 FROM masters.enclosures E     "
				+ "INNER JOIN masters.modulesenclosures M ON M.enclosurecode=E.enclosurecode  "
				+ "LEFT OUTER JOIN nicobps.bpaenclosures BE ON BE.enclosurecode=M.enclosurecode AND BE.applicationcode=? "
				+ "WHERE E.enabled='Y' AND  M.modulecode=? AND M.processcode=3 "
				+ "ORDER BY mandatory DESC,E.enclosurecode";

		Object[] criteria = { aplicationcode, modulecode };
		return this.listCommonMap(sql, criteria);
	}

	@Override
	public List<CommonMap> listOccupancies() {
		String sql = "SELECT T.occupancycode AS key, T.occupancyname AS value, T.occupancyalias AS value1 FROM masters.occupancies T ORDER BY T.occupancycode ";
		return this.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listSubOccupancies() {
		String sql = "SELECT T.suboccupancycode AS key, T.suboccupancyname AS value, T.occupancycode AS value1 FROM masters.suboccupancies T ORDER BY T.suboccupancycode ";
		return this.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listSubOccupancies(final String occupancycode) {
		String sql = "SELECT T.suboccupancycode AS key, T.suboccupancyname AS value, T.occupancycode AS value1 FROM masters.suboccupancies T WHERE T.occupancycode=? ORDER BY T.suboccupancycode ";
		Object[] criteria = { occupancycode };
		return this.listCommonMap(sql, criteria);
	}

	@Override
	public List<CommonMap> listUsages() {
		String sql = "SELECT T.usagecode AS key, T.usagename AS value, T.suboccupancycode AS value1 FROM masters.usages T ORDER BY T.usagename ";
		return this.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listUsages(final String suboccupancycode) {
		String sql = "SELECT T.usagecode AS key, T.usagename AS value, T.suboccupancycode AS value1 FROM masters.usages T WHERE T.suboccupancycode=? ORDER BY T.usagename ";
		Object[] criteria = { suboccupancycode };
		return this.listCommonMap(sql, criteria);
	}

	@Override
	public List<CommonMap> listOfficeCategories() {
		String sql = "SELECT T.officecategorycode AS key, T.officecategorydescription AS value FROM masters.officecategories T WHERE enabled='Y' ORDER BY T.officecategorydescription ";
		return this.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listOffices() {
		String sql = "SELECT T.officecode AS key, T.officename1 || ' ' || T.officename2 AS value FROM masters.offices T WHERE enabled='Y' ORDER BY T.officename1 ";
		return this.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listOffices(Integer officecode) {
		String sql = "SELECT T.officecode AS key, T.officename1 || ' ' || T.officename2 AS value FROM masters.offices T WHERE officecode=? ORDER BY T.officename1 ";
		return this.listCommonMap(sql, new Object[] { officecode });
	}

	@Override
	public List<CommonMap> listUserOffices() {
		Integer usercode = Integer.valueOf(session().getAttribute("usercode").toString());
		if (usercode == 1) {
			return listOffices();
		}

		String sql = "SELECT T.officecode AS key, T.officename1 || ' ' || T.officename2 AS value "
				+ "FROM masters.offices T INNER JOIN nicobps.useroffices uo on T.officecode=uo.officecode "
				+ "WHERE usercode=? ORDER BY T.officename1 ";
		return this.listCommonMap(sql, new Object[] { usercode });
	}

	@Override
	public List<CommonMap> listLicenseeType() {

		String sql = "SELECT T.licenseetypecode AS key, T.licenseetypename AS value "
				+ "				FROM masters.licenseetypes T  " + "				 ORDER BY T.licenseetypename ";
		return this.listCommonMap(sql);
	}

	@Override
	public List<Map<String, Object>> listUserValidOffices(Integer usercode) {

		String sql = "SELECT  U.USERCODE, USERNAME, VALIDTO, EXTENDEDTO, O1.STATEID, O1.TENANTID,O1.OFFICENAME1,O1.OFFICECODE,  O1.STATEID, O1.TENANTID, "
				+ "Case when EXTENDEDTO is null then b.validto else   EXTENDEDTO  END AS NEWVALIDTO  "
				+ "FROM MASTERS.OFFICES O1,  " + "nicobps.licenseeofficesvalidities  B,  "
				+ "nicobps.USERLOGINS U  " + "WHERE O1.REGISTERINGOFFICECODE = B.OFFICECODE  "
				+ "AND U.USERCODE = B.USERCODE " 
				+ " AND    Case when EXTENDEDTO is null then b.validto else   EXTENDEDTO  END >= current_date "
				+ " AND    U.USERCODE = ?   " + "ORDER BY O1.OFFICENAME1";

			
		
		
		Object[] criteria = { usercode };
		return this.listGeneric(sql, criteria);
	}

	@Override
	public List<Map<String, Object>> listRegisteringOffices() {
		String sql = "SELECT * FROM masters.offices WHERE enabled='Y' and isregisteringoffice='Y' ORDER BY officename1 ";
		return this.listGeneric(sql);
	}

	@Override
	public List<Map<String, Object>> listRegisteringOffices(Integer registeringofficecode) {
		String sql = "SELECT * FROM masters.offices WHERE enabled='Y' /*and isregisteringoffice='N'*/ and registeringofficecode=? ORDER BY officename1 ";
		return this.listGeneric(sql, new Object[] { registeringofficecode });
	}

	@Override
	public List<CommonMap> listModules() {
		String sql = "SELECT T.modulecode AS key, T.modulename AS value FROM masters.modules T ORDER BY T.modulecode ";
		return this.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listPageurls() {
		String sql = "SELECT T.urlcode AS key, T.parent || ' > ' || T.subsubmenu || ' > ' || T.subsubmenu AS value FROM masters.pageurls T ORDER BY T.urlcode ";
		return this.listCommonMap(sql);
	}

	@Override
	public List<Map<String, Object>> getLicensee(Integer usercode) {
		String sql = "SELECT l.*,lt.*,d.*,s.statename,u.mobileno,u.username as email,"
				+ "(SELECT json_agg(enclosures)from(select e.enclosurecode,e.enclosurename from nicobps.licenseesenclosures le ,masters.enclosures e where e.enclosurecode=le.enclosurecode and le.usercode=l.usercode)as enclosures)as enclosures "
				+ "FROM nicobps.licensees l  "
				+ "INNER JOIN masters.licenseetypes lt on lt.licenseetypecode=l.licenseetypecode "
				+ "INNER JOIN masters.districts d on d.districtcode=l.predistrictcode "
				+ "INNER JOIN nicobps.userlogins u on l.usercode=u.usercode "
				+ "INNER JOIN masters.states s on s.statecode=d.statecode "
				+ "WHERE l.usercode=? ORDER BY l.entrydate DESC ";
		return listGeneric(sql, new Object[] { usercode });
	}

	@Override
	public CommonMap generateApplicationcode(Integer officecode, Integer modulecode, Integer usercode,
			Integer servicetypecode) {
		Integer applicationslno = getMax("nicobps", "applications", "applicationslno");
		applicationslno++;
		return new CommonMap(
				String.format("%03d", officecode) + String.format("%03d", modulecode) + String.format("%04d", usercode)
						+ String.format("%02d", servicetypecode) + String.format("%06d", applicationslno),
				applicationslno);
	}

	@Override
	public String generateTransactionReceipt(Integer officecode, String purpose, String... components) {
		String sql = "select case when component1 is not null then (case when derivedfixed1 ='FIXED' then component1 else :component1 end) else '' end || "
				+ "	case when component2 is not null then (case when derivedfixed2 ='FIXED' then component2 else :component2 end) else '' end|| "
				+ "	case when component3 is not null then (case when derivedfixed3 ='FIXED' then component3 else :component3 end) else '' end || "
				+ "	case when component4 is not null then (case when derivedfixed4 ='FIXED' then component4 else :component4 end) else '' end|| "
				+ "	case when component5 is not null then (case when derivedfixed5 ='FIXED' then component5 else :component5 end) else '' end as receiptno "
				+ "from masters.officereceiptformats where officecode=:officecode and purpose=:purpose ";

		MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("officecode", officecode)
				.addValue("purpose", purpose);
		for (int i = 1; i <= 5; i++) {
			parameters.addValue("component" + i, (components.length >= i) ? components[i - 1] : "");
		}
		List<Map<String, Object>> list = listGenericParameterized(sql, parameters);
		String receiptno = null;
		if (!list.isEmpty()) {
			receiptno = list.get(0).get("receiptno").toString();
		}
		return receiptno;
	}

	@Override // contains both fromprocesscode & toprocesscode
	public boolean updateApplicationflowremarks(String applicationcode, Integer modulecode, Integer fromprocesscode,
			Integer toprocesscode, Integer fromusercode, Integer tousercode, String remarks) {
		Integer afrcode = this.getMax("nicobps", "applicationflowremarks", "afrcode");
		return daoUtilInterface.updateApplicationflowremarks(afrcode + 1, applicationcode, modulecode, fromprocesscode,
				toprocesscode, fromusercode, tousercode, remarks);
	}

	@Override // contains only toprocesscode
	public boolean updateApplicationflowremarks(String applicationcode, Integer modulecode, Integer toprocesscode,
			Integer fromusercode, Integer tousercode, String remarks) {
		Integer afrcode = this.getMax("nicobps", "applicationflowremarks", "afrcode");
		String sql = "select toprocesscode from nicobps.applicationflowremarks where afrcode=(select max(afrcode) from nicobps.applicationflowremarks where applicationcode=? )";
		Integer fromprocesscode = 0;
		fromprocesscode = (Integer) (listGeneric(sql, new Object[] { applicationcode })).get(0).get("toprocesscode");
		return daoUtilInterface.updateApplicationflowremarks(afrcode + 1, applicationcode, modulecode, fromprocesscode,
				toprocesscode, fromusercode, tousercode, remarks);
	}

	@Override
	public List<Map<String, Object>> getAllNextProcessflows(Integer modulecode, Integer fromprocesscode) {
		String sql = "select * from masters.processflow where fromprocesscode=? and modulecode=? ";
		return this.listGeneric(sql, new Object[] { fromprocesscode, modulecode });
	}

	@Override
	public List<Map<String, Object>> getNextProcessflow(Integer modulecode, Integer fromprocesscode) {
		String sql = "SELECT PF.*, NP.processname " + "FROM masters.processflow PF "
				+ "INNER JOIN masters.processes NP ON NP.processcode = PF.toprocesscode AND NP.modulecode = PF.modulecode  "
				+ "WHERE PF.modulecode=? AND PF.fromprocesscode=? AND PF.processflowstatus='N'";
		return this.listGeneric(sql, new Object[] { modulecode, fromprocesscode });
	}

	@Override
	public Map<String, Object> getNextProcessflow(Integer modulecode, String applicationcode) {
		String sql = "SELECT pf.* FROM nicobps.applicationflowremarks afr "
				+ "INNER JOIN masters.processflow pf on pf.fromprocesscode=afr.toprocesscode and processflowstatus='N' and pf.modulecode=afr.modulecode "
				+ "WHERE afrcode=(select max(afrcode) from nicobps.applicationflowremarks where modulecode=? and applicationcode=?::text) ";
		List<Map<String, Object>> list = this.listGeneric(sql, new Object[] { modulecode, applicationcode });
		return (!list.isEmpty()) ? list.get(0) : new HashMap<>();
	}

	@Override
	public List<Map<String, Object>> getCurrentProcessStatus(Integer modulecode, String applicationcode) {
		String sql = "SELECT pf.*,pu.pageurl,pu.parent,pu.parenticon FROM nicobps.applicationflowremarks afr "
				+ "INNER JOIN masters.processflow pf on pf.fromprocesscode=afr.toprocesscode and processflowstatus='N' and pf.modulecode=afr.modulecode "
				+ "LEFT JOIN masters.pageurls pu on pu.urlcode=pf.urlcode "
				+ "WHERE afrcode=(select max(afrcode) from nicobps.applicationflowremarks where modulecode=? and applicationcode=?::text) ";
		return this.listGeneric(sql, new Object[] { modulecode, applicationcode });
	}

	@Override
	public List<Map<String, Object>> getCurrentProcessStatus(Integer modulecode, Integer usercode) {
		String sql = "SELECT app.applicationcode,off.officecode,off.officename1,pf.*,pu.pageurl,pu.parent,pu.parenticon,"
				+ "(select to_char(min(entrydate),'DD-MON-YYYY') from nicobps.applications appdate where appdate.applicationcode=app.applicationcode)as entrydate FROM nicobps.applications app "
				+ "INNER JOIN nicobps.applicationflowremarks afr on app.applicationcode=afr.applicationcode "
				+ "		and afrcode=(select max(afrcode) from nicobps.applicationflowremarks afr where afr.applicationcode=app.applicationcode and afr.modulecode=app.modulecode) "
				+ "INNER JOIN masters.processflow pf on pf.fromprocesscode=afr.toprocesscode and processflowstatus='N' and pf.modulecode=afr.modulecode "
				+ "LEFT JOIN masters.pageurls pu on pu.urlcode=pf.urlcode "
				+ "LEFT JOIN masters.offices off on off.officecode=app.officecode "
				+ "WHERE app.modulecode=? and app.usercode=? ";
		return this.listGeneric(sql, new Object[] { modulecode, usercode });
	}

	@Override
	public List<Map<String, Object>> listStakeholders(Integer officecode) {
		String sql = "SELECT distinct l.usercode as key,l.applicantsname as value FROM nicobps.licensees l  "
				+ "								INNER JOIN nicobps.licenseeofficesvalidities li on li.usercode=l.usercode  "
				+ "								where "
				+ " CASE WHEN EXTENDEDTO IS NULL THEN VALIDTO ELSE EXTENDEDTO END <=current_date "
				+ " AND li.officecode IN(SELECT officecode FROM masters.offices WHERE enabled='Y' and isregisteringoffice='Y' and registeringofficecode=? ORDER BY officename1) ORDER BY l.applicantsname DESC  ";
		System.out.println("Query 1 "+ sql + "  Ofice code"+ officecode);
		
		return this.listGeneric(sql, new Object[] { officecode });
	}

	@Override
	public List<Map<String, Object>> listStakeholdersMain(Integer officecode) {
		String sql = "SELECT distinct l.usercode as key,l.applicantsname as value FROM nicobps.licensees l  "
				+ "								INNER JOIN nicobps.licenseeofficesvalidities li on li.usercode=l.usercode  "
				+ "								where "
				+ " CASE WHEN EXTENDEDTO IS NULL THEN VALIDTO ELSE EXTENDEDTO END <=current_date "
				+ " li.officecode IN(SELECT officecode FROM masters.offices WHERE enabled='Y'  and registeringofficecode=? ORDER BY officename1) OR officecode=? ORDER BY l.applicantsname DESC  ";

		System.out.println("Query 2 "+ sql + "  Ofice code"+ officecode);
		
		return this.listGeneric(sql, new Object[] { officecode, officecode });
	}

	@Override
	public boolean updateextendValidity(Short officecode, Integer usercode, String extendedto, Integer extendedby) {

		return daoUtilInterface.updateextendValidity(officecode, usercode, extendedto, extendedby);
	}

	@Override
	public Map<String, Object> getPlanInfo(String permitnumber) {
		return daoUtilInterface.getPlanInfo(permitnumber);
	}

	@Override
	public List<Map<String, Object>> listUsers(Integer officecode) {
		System.out.println("officecode:::" + officecode);
		String sql = "SELECT distinct u.usercode as key,u.fullname as value FROM nicobps.userlogins u "
				+ "INNER JOIN nicobps.useroffices uo on uo.usercode=u.usercode  "
				+ "INNER JOIN masters.offices o on o.officecode=uo.officecode "
				+ "where uo.officecode=?   ORDER BY u.fullname DESC ";

		return this.listGeneric(sql, new Object[] { officecode });
	}
	
	
	public List<DashboardData> listDashboardData() 
	{		
		String sql = " SELECT OFFICE AS officename, SUM(totalac) as totalac, sum(approvedac) AS approvedac, SUM(totalac) - sum(approvedac)  AS pendingac  " + 
				"FROM(SELECT " + 
				"OFFICENAME1 || ' ' || CHR(10) || CASE WHEN OFFICENAME2 IS NOT NULL THEN OFFICENAME2 ELSE '' END  " + 
				"||  ' ' ||  CHR(10) || CASE WHEN OFFICENAME3 IS NOT NULL THEN OFFICENAME3 ELSE '' END AS OFFICE, " + 
				"COUNT(A.APPLICATIONCODE) AS totalac,  0 AS approvedac, 0 AS pendingac  " + 
				"FROM NICOBPS.APPLICATIONS A  INNER JOIN MASTERS.OFFICES O ON O.officecode = A.officecode  " + 
				"WHERE MODULECODE = 2  " + 
				"GROUP BY OFFICENAME1 || ' ' || CHR(10) || CASE WHEN OFFICENAME2 IS NOT NULL THEN OFFICENAME2 ELSE '' END  " + 
				"||  ' ' ||  CHR(10) || CASE WHEN OFFICENAME3 IS NOT NULL THEN OFFICENAME3 ELSE '' END  " + 
				"UNION ALL  " + 
				"SELECT " + 
				"OFFICENAME1 || ' ' || CHR(10) || CASE WHEN OFFICENAME2 IS NOT NULL THEN OFFICENAME2 ELSE '' END  " + 
				"||  ' ' ||  CHR(10) || CASE WHEN OFFICENAME3 IS NOT NULL THEN OFFICENAME3 ELSE '' END AS OFFICE, " + 
				"0 AS totalac,  COUNT(A.APPLICATIONCODE)  AS approvedac, 0  AS pendingac  " + 
				"FROM NICOBPS.APPLICATIONS A  INNER JOIN MASTERS.OFFICES O ON O.officecode = A.officecode  " + 
				"INNER JOIN BPAAPPROVEAPPLICATIONS BP ON A.APPLICATIONCODE = BP.APPLICATIONCODE " + 
				"WHERE MODULECODE = 2  " + 
				"GROUP BY OFFICENAME1 || ' ' || CHR(10) || CASE WHEN OFFICENAME2 IS NOT NULL THEN OFFICENAME2 ELSE '' END  " + 
				"||  ' ' ||  CHR(10) || CASE WHEN OFFICENAME3 IS NOT NULL THEN OFFICENAME3 ELSE '' END  " + 
				") S " + 
				"GROUP BY OFFICE " + 
				"ORDER BY OFFICE";
		return this.listGeneric(DashboardData.class, sql, new Object[] { });
	}
	
	public List<UserApplications> listUserApplications(Integer usercode) 
	{		
		String sql = "SELECT PR.processcode,PR.processname,COUNT(APP.applicationcode) AS totalac FROM nicobps.applications APP " 
				   + "INNER JOIN nicobps.applicationflowremarks AF "
				   + "ON (AF.applicationcode, AF.entrydate) = "
				   + "( " 
				   + "   SELECT applicationcode, MAX(entrydate) FROM nicobps.applicationflowremarks " 
				   + "   WHERE applicationcode = APP.applicationcode  AND modulecode = APP.modulecode "
				   + "   GROUP BY applicationcode " 
				   + ") " 
				   + "INNER JOIN masters.processes PR ON PR.processcode = AF.toprocesscode AND PR.modulecode = AF.modulecode " 
				   + "WHERE AF.modulecode=2 AND AF.tousercode = ? " 
				   +"GROUP BY PR.processcode,PR.processname ";
		return this.listGeneric(UserApplications.class, sql, new Object[] {usercode});
	}	
	
	
	public List<UserApplications> listStakeholderApplications(Integer usercode) 
	{		
		String sql = "SELECT PR.processcode,PR.processname,COUNT(APP.applicationcode) AS totalac FROM nicobps.applications APP " 
				   + "INNER JOIN nicobps.applicationflowremarks AF "
				   + "ON (AF.applicationcode, AF.entrydate) = "
				   + "( " 
				   + "   SELECT applicationcode, MAX(entrydate) FROM nicobps.applicationflowremarks " 
				   + "   WHERE applicationcode = APP.applicationcode  AND modulecode = APP.modulecode "
				   + "   GROUP BY applicationcode " 
				   + ") " 
				   + "INNER JOIN masters.processes PR ON PR.processcode = AF.toprocesscode AND PR.modulecode = AF.modulecode " 
				   + "WHERE AF.modulecode=1 AND AF.toprocesscode IN (4,6) AND "
				   + "CASE WHEN AF.tousercode IS NULL THEN APP.officecode IN (SELECT officecode FROM nicobps.useroffices WHERE usercode=2) ELSE tousercode=? END " 
				   + "GROUP BY PR.processcode,PR.processname ";		
		return this.listGeneric(UserApplications.class, sql, new Object[] {usercode});
	}		
	
    @Override
    public List<Audittrail> listAuditrail() 
    {
        Object[] criteria = new Object[]{};  
        String sql = "SELECT username,A.userid,actiontaken,pageurl,browser,os,ipaddress,TO_CHAR(A.entrydate,'YYYY-MM-DD HH:MI:SS AM') AS entrydate FROM nicobps.audittrail A "
                   + "LEFT OUTER JOIN nicobps.userlogins UL ON UL.username=A.userid "
                   + "ORDER BY A.entrydate DESC ";     
        return this.listGeneric(Audittrail.class, sql, criteria);
    }   	
	
    @Override
    public void initAuditrail(HttpServletRequest request) 
    {
        String userid,actiontaken;
        Userlogin user=(Userlogin)request.getSession().getAttribute("user");
        if (user.getUsername()!= null) {
            userid = user.getUsername();
        } else if (request.getParameter("userid")!= null && !request.getParameter("userid").equals("")) {
            userid = request.getParameter("userid");
        }else if (request.getAttribute("userid")!= null && !request.getAttribute("userid").equals("")) {
            userid = (String)request.getAttribute("userid");
        }else{
            userid = "";
        }
        
        if (request.getParameter("actiontaken")!= null && !request.getParameter("actiontaken").equals("")) {
            actiontaken = request.getParameter("actiontaken");
        }else if (request.getAttribute("actiontaken")!= null && !request.getAttribute("actiontaken").equals("")) {
            actiontaken = (String)request.getAttribute("actiontaken");
        }else{
            actiontaken = "";
        }        
        
        HashMap<String, String> map=Utilty.getClientDetails(request);
        map.put("userid",userid);
        map.put("actiontaken",actiontaken);
        
        if(actiontaken.equals("Login")){ 
            map.put("pageurl","/obps/login.htm");        
            map.put("actiontaken","Login Success");        
        }
        if(actiontaken.equals("Logout")){
            map.put("pageurl","/obps/logout.htm");
            map.put("actiontaken","Logout Success");        
        }
        daoUtilInterface.initAuditrail(map);
 
//        System.out.print("\n================Auditrail Log Starts===============\n");
//        System.out.print("userid : "+map.get("userid"));
//        System.out.print("\nactiontaken : "+map.get("actiontaken"));
//        System.out.print("\nos : "+map.get("os"));
//        System.out.print("\nbrowser : "+map.get("browser"));
//        System.out.print("\nipaddress : "+map.get("ipaddress"));
//        System.out.print("\npageurl : "+map.get("pageurl"));    
//        System.out.print("\n================Auditrail Log Ends=================\n");
    }	
	
}
