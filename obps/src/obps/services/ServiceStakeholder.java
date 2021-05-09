package obps.services;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import obps.util.application.ServiceUtilInterface;

@Service
public class ServiceStakeholder implements ServiceStakeholderInterface {
	@Autowired private ServiceUtilInterface UI;
	private JdbcTemplate jdbcTemplate;        
    @Autowired
    public void createTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
	@Override
	public List<Map<String,Object>> listLicensees() {
		String sql="Select * from nicobps.licensees l "
				+ "INNER JOIN masters.licenseetypes lt on lt.licenseetypecode=l.licenseetypecode "
				+ "INNER JOIN masters.districts d on d.districtcode=l.predistrictcode "
				+ "order by entrydate DESC";
		return UI.listGeneric(sql);
	}

	@Override
	public byte[] getEnclosure(Integer usercode,Integer enclosurecode) {
		String sql="Select enclosureimage from nicobps.licenseesenclosures "
				+ "WHERE usercode=? AND enclosurecode=? ";
		return UI.getBytes(sql, new Object[] {usercode,enclosurecode});
	}

	@Override
	public boolean verifyApproveStakeHolder(Integer usercode,Integer nextprocessode) {
		String sql="UPDATE nicobps.licensees set processcode= ? "
				+ "WHERE usercode=? ";
		return UI.update("nicobps.licensees",sql, new Object[] {nextprocessode,usercode});
	}
	
}
