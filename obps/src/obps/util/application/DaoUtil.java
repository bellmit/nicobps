package obps.util.application;


import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("daoUtil")
public class DaoUtil implements DaoUtilInterface
{
    private JdbcTemplate jdbcTemplate;        
    @Autowired
    public void createTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
        
    @Override
    public List<CommonMap> listCommonMap(final String sql) { 
    	List<CommonMap> list = new ArrayList<>();
		try {
			list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(CommonMap.class));
		} catch (Exception e) {
			System.out.println("Error in DaoUtil.listCommonMap(final String sql) : " + e);
		}
		return list;    	    	
    }
    @Override    
    public List<CommonMap> listCommonMap(final String sql,final Object[] criteria) {
    	List<CommonMap> list = new ArrayList<>();
		try {
			list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(CommonMap.class),criteria);
		} catch (Exception e) {
			System.out.println("Error in DaoUtil.listCommonMap(final String sql,final Object[] criteria) : " + e);
		}
		return list;     	        
    }
    
    @Override    
    public int getCount(final String sql) { 
        Integer count = Integer.valueOf("0"); 
        try 
        {        
            CountAndMax data = jdbcTemplate.queryForObject(sql,BeanPropertyRowMapper.newInstance(CountAndMax.class));               
            if(data.getCount()!=null){
                count=data.getCount();
            }            
        } catch (Exception e) {            
            System.out.println("Error in DaoUtil.listData(String sql, Object[] criteria) : " + e);
        } 
        return count;
    }      
    @Override    
    public int getCount(final String sql,final Object[] criteria) { 
        Integer count = Integer.valueOf("0"); 
        try 
        {        
            CountAndMax data = jdbcTemplate.queryForObject(sql,BeanPropertyRowMapper.newInstance(CountAndMax.class), criteria);               
            if(data.getCount()!=null){
                count=data.getCount();
            }            
        } catch (Exception e) {            
            System.out.println("Error in DaoUtil.listData(String sql, Object[] criteria) : " + e);
        } 
        return count;
    }    
    
    @Override    
    public Long getMaxValue(final String sql) { 
        Long max = Long.valueOf("0"); 
        try 
        {         
            CountAndMax data = jdbcTemplate.queryForObject(sql,BeanPropertyRowMapper.newInstance(CountAndMax.class),new Object[]{});   
            if(data.getMax()!=null){
                max=data.getMax();
            }
        } catch (Exception e) {            
            System.out.println("Error in DaoUtil.listData(String sql, Object[] criteria) : " + e);
        } 
        return max;
    }     
    @Override    
    public Long getMaxValue(final String sql,final Object[] criteria) { 
        Long max = Long.valueOf("0"); 
        try 
        {         
            CountAndMax data = jdbcTemplate.queryForObject(sql,BeanPropertyRowMapper.newInstance(CountAndMax.class), criteria);   
            if(data.getMax()!=null){
                max=data.getMax();
            }
        } catch (Exception e) {            
            System.out.println("Error in DaoUtil.listData(String sql, Object[] criteria) : " + e);
        } 
        return max;
    }  
  
    @Override
    public boolean checkExistance(String sql) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        return rowSet.next();
    }         
    @Override
    public boolean checkExistance(String sql, Object[] criteria) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, criteria);
        return rowSet.next();
    } 
    
    @Override
    public String getStringObject(String sql) 
    {                
        String data=null;
        try 
        {
	        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);	        
	        while(rowSet.next()){
	            data=rowSet.getString(1);
	        }        
        } catch (Exception e) {            
            System.out.println("Error in DaoUtil.getStringObject(String sql) : " + e);
        }            
        return data;
    }     
    
    @Override
    public String getStringObject(String sql, Object[] criteria) 
    {                
        String data=null;
        try 
        {
        	//data=(String)jdbcTemplate.queryForObject(sql, criteria, String.class);
	        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, criteria);	        
	        while(rowSet.next()){
	            data=rowSet.getString(1);
	        }        
        } catch (Exception e) {            
            System.out.println("Error in DaoUtil.getStringObject(String sql, Object[] criteria) : " + e);
        }            
        return data;
    } 
    
    @Override
    public byte[] getFileByte(String sql, Object[] criteria) 
    {
        byte[] data=null;
        try 
        {        
            List<FileByte> list = (List<FileByte>) jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(FileByte.class),criteria);        
            for(FileByte F:list){
                data=F.getFile();
            }
        } catch (Exception e) {            
            System.out.println("Error in DaoUtil. getFileByte(String sql, Object[] criteria) : " + e);
        } 
        return data;
    }    
   //===========================================//	
	
	@Override
	public Integer getMax(String schema, String table,String column) {
		List<Map<String, Object>> list = null;
		Integer i=0;
		try {
			String sql = (new StringBuilder("Select max(")).append(column)
			.append(") From ")
			.append(schema).append(".").append(table).toString();
			 i= jdbcTemplate.queryForObject(sql,Integer.class);
		} catch (Exception e) {
			System.out.println("Error in DaoUtil.getMax(String schema, String table,String column) : " + e);
		}
		return (i!=null) ? i : 0;
	}
	
	@Override
	public <T> List<T> listGeneric(Class<T> clazz, String sql) {
		List<T> list = new ArrayList<>();
		try {
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(clazz));
		} catch (Exception e) {
			System.out.println("Error in DaoUtil.listGeneric(Class<T> clazz, String sql) : " + e);
		}
		return list;
	}

	@Override
	public <T> List<T> listGeneric(Class<T> clazz, String sql, Object[] params) {
		List<T> list = new ArrayList<>();
		try {
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(clazz), params);			
		} catch (Exception e) {
			System.out.println("Error in DaoUtil.listGeneric(Class<T> clazz, String sql, Object[] params) : " + e);
		}
		return list;
	}

	@Override
	public List<Map<String,Object>> listGeneric(String sql) {
		List<Map<String,Object>> list = new ArrayList<>();
		try {
			list = jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			System.out.println("Error in DaoUtil.listGeneric(String sql) : " + e);
		}
		return list;
	}
	
	@Override
	public List<Map<String,Object>> listGeneric(String sql, Object[] params) {
		List<Map<String,Object>> list = new ArrayList<>();
		try {
			list = jdbcTemplate.queryForList(sql, params);			
		} catch (Exception e) {
			System.out.println("Error in DaoUtil.listGeneric(String sql, Object[] params) : " + e);
		}
		return list;
	}
	
	@Override
	public <T> boolean update(String tablename,String sql, Object[] params) {	 
		boolean response=false;		
		try {
			response=jdbcTemplate.update(sql,params)>=0;
		}catch(Exception e) {
			response=false;
			System.out.println("Error in DaoUtil.update(String tablename,String sql, Object[] params) : " + e);
		}
		return response;
	}    
    
    
    
    
    
    
}
