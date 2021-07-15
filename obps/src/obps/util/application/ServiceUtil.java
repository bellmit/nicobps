/*@author Avijit Debnath*/

package obps.util.application;

import java.util.Map;

import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.beans.factory.annotation.Autowired;

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
				+ "WHERE E.enabled='Y' AND mandatory='Y' AND M.modulecode=? AND licenseetypecode=? "
				+ "AND E.enclosurecode NOT IN (SELECT enclosurecode FROM  nicobps.licenseesenclosures WHERE usercode=?) "
				+ "ORDER BY mandatory DESC,E.enclosurecode";
		Object[] criteria = { modulecode, licenseetypecode, usercode };
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
	public List<Map<String, Object>> listUserValidOffices(Integer usercode) {
//		String sql = "select * from nicobps.licenseeofficesvalidities b "
//				+ "inner join masters.offices c on b.officecode=c.officecode and c.enabled='Y' "
//				+ "where b.validto>current_date and b.usercode= ? ";

		String sql = "SELECT  U.USERCODE, USERNAME, O2.OFFICECODE, O2.OFFICENAME1, VALIDTO, EXTENDEDTO, "
				+ " Case when EXTENDEDTO is null then b.validto else   EXTENDEDTO  END AS VALIDTO  "
				+ " FROM MASTERS.OFFICES O1, MASTERS.OFFICES O2 , nicobps.licenseeofficesvalidities  b, USERLOGINS U "
				+ " WHERE O1.OFFICECODE = O2.REGISTERINGOFFICECODE AND O1.OFFICECODE = B.OFFICECODE "
				+ " AND U.USERCODE = B.USERCODE "
				+ " AND case when EXTENDEDTO is null then b.validto>current_date else   EXTENDEDTO > CURRENT_DATE END "
				+ " AND U.USERCODE = ?  " + "ORDER BY O1.OFFICENAME1";

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
		String sql = "select * from masters.processflow where modulecode=? and fromprocesscode=? and processflowstatus='N'";
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
		String sql = "SELECT distinct l.usercode as key,l.applicantsname as value FROM nicobps.licensees l \r\n"
				+ "								INNER JOIN nicobps.licenseeofficesvalidities li on li.usercode=l.usercode \r\n"
				+ "								where li.officecode IN(SELECT officecode FROM masters.offices WHERE enabled='Y' and isregisteringoffice='N' and registeringofficecode=? ORDER BY officename1) ORDER BY l.applicantsname DESC \r\n";

		return this.listGeneric(sql, new Object[] { officecode });
	}

	@Override
	public List<Map<String, Object>> listStakeholdersMain(Integer officecode) {
		String sql = "SELECT distinct l.usercode as key,l.applicantsname as value FROM nicobps.licensees l \r\n"
				+ "								INNER JOIN nicobps.licenseeofficesvalidities li on li.usercode=l.usercode \r\n"
				+ "								where li.officecode IN(SELECT officecode FROM masters.offices WHERE enabled='Y'  and registeringofficecode=? ORDER BY officename1) OR officecode=? ORDER BY l.applicantsname DESC \r\n";

		return this.listGeneric(sql, new Object[] { officecode, officecode });
	}

	@Override
	public boolean updateextendValidity(Short officecode, Integer usercode, String extendedto, Integer extendedby) {

		return daoUtilInterface.updateextendValidity(officecode, usercode, extendedto, extendedby);
	}
}
