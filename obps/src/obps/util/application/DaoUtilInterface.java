package obps.util.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public interface DaoUtilInterface {
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

	public List<Map<String, Object>> listGenericParameterized(String sql, MapSqlParameterSource param);

	public List<Map<String, Object>> listGeneric(String sql);

	public List<Map<String, Object>> listGeneric(String sql, Object[] params);

	public <T> boolean update(String tablename, String sql, Object[] params);

	public <T> boolean update(List<BatchUpdateModel> list);

	public boolean updateApplicationflowremarks(Integer afrcode, String applicationcode, Integer modulecode,
			Integer fromprocesscode, Integer toprocesscode, Integer fromusercode, Integer tousercode, String remarks);

	public boolean updateextendValidity(Short officecode, Integer usercode, String extendedto, Integer extendedby);

	public Map<String, Object> getPlanInfoPermit(String permitnumber);

	public Map<String, Object> getPlanInfoEdcr(String edcrnumber);

	public void initAuditrail(HashMap<String, String> map);
}
