package obps.util.application;

import java.util.Map;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service("serviceUtil")
public class ServiceUtil implements ServiceUtilInterface {
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
	public List<CommonMap> listModules() {
		String sql = "SELECT T.modulecode AS key, T.modulename AS value FROM masters.modules T ORDER BY T.modulecode ";
		return this.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listPageurls() {
		String sql = "SELECT T.urlcode AS key, T.parent || ' > ' || T.subsubmenu || ' > ' || T.subsubmenu AS value FROM masters.pageurls T ORDER BY T.urlcode ";
		return this.listCommonMap(sql);
	}

}
