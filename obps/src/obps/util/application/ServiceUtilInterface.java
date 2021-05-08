package obps.util.application;

import java.util.List;
import java.util.Map;

public interface ServiceUtilInterface 
{
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
	public byte[] getBytes(String sql, Object[] criteria);  
    //////////////////////////////////////////////////    
	public Integer getMax(String schema, String table, String column);
	public <T> List<T> listGeneric(Class<T> clazz, String sql);
	public <T> List<T> listGeneric(Class<T> clazz, String sql, Object[] params);
	public List<Map<String, Object>> listGeneric(String sql);
	public List<Map<String, Object>> listGeneric(String sql, Object[] params);
	public <T> boolean update(String tablename, String sql, Object[] params);
	
	//===========================================//	
	public List<CommonMap> listStates();
	public List<CommonMap> listDistricts();
	public List<CommonMap> listDistricts(final Short statecode);
	public List<CommonMap> listSubDistricts();
	public List<CommonMap> listSubDistricts(final Short districtcode);	
	public List<CommonMap> listSalutations();
	public List<CommonMap> listLicenseetypes();
	public List<CommonMap> listLicenseesregistrationsm();	
	public List<CommonMap> listEnclosures(final Short modulecode);
	public List<CommonMap> listOccupancies();
	public List<CommonMap> listSubOccupancies();
	public List<CommonMap> listSubOccupancies(final String occupancycode);	
	public List<CommonMap> listUsages();
	public List<CommonMap> listUsages(final String suboccupancycode);
	public List<CommonMap> listOfficeCategories();
	public List<CommonMap> listOffices();
	public List<CommonMap> listModules();
	public List<CommonMap> listPageurls();	
}
