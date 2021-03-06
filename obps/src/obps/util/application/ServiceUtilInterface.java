package obps.util.application;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import obps.models.Audittrail;
import obps.models.DashboardData;
import obps.models.UserApplications;

public interface ServiceUtilInterface {
	public List<CommonMap> listCommonMap(final String sql);

	public List<CommonMap> listCommonMap(final String sql, final Object[] criteria);

	public int getCount(final String sql);

	public int getCount(String sql, Object[] criteria);

	public Long getMaxValue(final String sql);

	public Long getMaxValue(final String sql, final Object[] criteria);

	public boolean checkExistance(String sql);

	public boolean checkExistance(String sql, Object[] criteria);

	public String getStringObject(String sql);

	public String getStringObject(String sql, Object[] criteria);

	public byte[] getFileByte(String sql, Object[] criteria);

	public byte[] getBytes(String sql, Object[] criteria);

	//////////////////////////////////////////////////
	public Integer getMax(String schema, String table, String column);

	public <T> List<T> listGeneric(Class<T> clazz, String sql);

	public <T> List<T> listGeneric(Class<T> clazz, String sql, Object[] params);

	public List<Map<String, Object>> listGeneric(String sql);

	public List<Map<String, Object>> listGeneric(String sql, Object[] params);

	public List<Map<String, Object>> listGenericParameterized(String sql, MapSqlParameterSource param);

	public <T> boolean update(String tablename, String sql, Object[] params);

	public <T> boolean update(List<BatchUpdateModel> list);

	// ===========================================//
	public List<CommonMap> listStates();

	public List<CommonMap> listDistricts();

	public List<CommonMap> listDistricts(final Short statecode);

	public List<CommonMap> listSubDistricts();

	public List<CommonMap> listSubDistricts(final Short districtcode);

	public List<CommonMap> listSalutations();

	public List<CommonMap> listLicenseetypes();

	public List<CommonMap> listFeetypes();

	public List<CommonMap> listLicenseesregistrationsm();

	public List<CommonMap> listEnclosures(final Short modulecode);

	public List<CommonMap> listEnclosures(final Short modulecode, Integer usercode);

	public List<CommonMap> listEnclosures(final Short modulecode, Short licenseetypecode);

	public List<CommonMap> listEnclosures(final Short modulecode, Integer usercode, Short licenseetypecode);

	public List<CommonMap> listEnclosuresNotUploades(final Short modulecode, Integer usercode, Short licenseetypecode);

	public List<CommonMap> listBpaEnclosures(final Short modulecode, String applicationcode);

	public List<CommonMap> listOccupancies();

	public List<CommonMap> listSubOccupancies();

	public List<CommonMap> listSubOccupancies(final String occupancycode);

	public List<CommonMap> listUsages();

	public List<CommonMap> listUsages(final String suboccupancycode);

	public List<CommonMap> listOfficeCategories();

	public List<CommonMap> listOffices();

	public List<CommonMap> listOffices(Integer officecode);

	public List<CommonMap> listUserOffices();
	public List<CommonMap> listLicenseeType();

	public List<Map<String, Object>> listRegisteringOffices();

	public List<Map<String, Object>> listRegisteringOffices(Integer registeringofficecode);

	public List<CommonMap> listModules();

	public List<CommonMap> listPageurls();

	public CommonMap generateApplicationcode(Integer officecode, Integer modulecode, Integer usercode,
			Integer servicetypecode);

	public String generateTransactionReceipt(Integer officecode, String purpose, String... components);

	public boolean updateApplicationflowremarks(String applicationcode, Integer modulecode, Integer fromprocesscode,
			Integer toprocesscode, Integer fromusercode, Integer tousercode, String remarks);

	public boolean updateApplicationflowremarks(String applicationcode, Integer modulecode, Integer toprocesscode,
			Integer fromusercode, Integer tousercode, String remarks);

	public List<Map<String, Object>> getAllNextProcessflows(Integer modulecode, Integer fromprocesscode);

	public List<Map<String, Object>> getNextProcessflow(Integer modulecode, Integer fromprocesscode);

	public Map<String, Object> getNextProcessflow(Integer modulecode, String applicationcode);

	public List<Map<String, Object>> getCurrentProcessStatus(Integer modulecode, String applicationcode);

	public List<Map<String, Object>> getCurrentProcessStatus(Integer modulecode, Integer usercode);

	public List<Map<String, Object>> listUserValidOffices(Integer usercode);

	public List<Map<String, Object>> getLicensee(Integer usercode);

	public List<Map<String, Object>> listStakeholders(Integer officecode);

	public List<Map<String, Object>> listStakeholdersMain(Integer officecode);

	public boolean updateextendValidity(Short officecode, Integer usercode, String extendedto, Integer extendedby);

	public Map<String, Object> getPlanInfoPermit(String permitnumber);
	
	public Map<String, Object> getPlanInfoEdcr(String edcrnumber);

	public List<Map<String, Object>> listUsers(Integer officecode);
	
	public List<DashboardData> listDashboardData();
	
	public List<UserApplications> listUserApplications(Integer usercode);
	
	public List<UserApplications> listStakeholderApplications(Integer usercode); 
	
	public List<Audittrail> listAuditrail();
	
	public void initAuditrail(HttpServletRequest request);
	
     public Map<String, Object> getUserDetails(Integer usercode);
     
     public Map<String, Object> getApplicationDetails(String applicationcode);
	
	public String getOfficeName(Integer officecode);
}
