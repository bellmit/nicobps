package obps.util.application;

import java.util.List;
import java.util.Map;

public interface DaoUtilInterface {
    public List<CommonMap> listCommonMap(final String sql); 
    public List<CommonMap> listCommonMap(final String sql,final Object[] criteria);
    public int getCount(final String sql);
    public int getCount(String sql, Object[] criteria);
    public Long getMaxValue(final String sql);
    public Long getMaxValue(final String sql,final Object[] criteria);
    public boolean checkExistance(String sql);
    public boolean checkExistance(String sql, Object[] criteria);
    public String getStringObject(String sql);
    public String getStringObject(String sql, Object[] criteria); 
    public byte[] getFileByte(String sql, Object[] criteria);    
    //////////////////////////////////////////////////    
	public Integer getMax(String schema, String table, String column);
	public <T> List<T> listGeneric(Class<T> clazz, String sql);
	public <T> List<T> listGeneric(Class<T> clazz, String sql, Object[] params);
	public List<Map<String, Object>> listGeneric(String sql);
	public List<Map<String, Object>> listGeneric(String sql, Object[] params);
	public <T> boolean update(String tablename, String sql, Object[] params);    
}
