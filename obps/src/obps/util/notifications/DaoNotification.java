package obps.util.notifications;


import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("daoNotification")
public class DaoNotification{

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void createTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Notification> listNotificationDetails(Integer officecode,String messageid) {
		String sql = "SELECT O.officecode,officeshortname,smsusername,smspassword,smssenderid,securekey,emailid,emailidpassword, "
				   + "OFN.messageid,templateid,sms,email " 
				   + "FROM masters.offices O " 
				   + "INNER JOIN masters.officenotications OFN ON OFN.officecode=O.officecode " 
				   + "INNER JOIN masters.notifications N ON N.messageid=OFN.messageid " 
				   +"WHERE O.officecode=? AND OFN.messageid=? ";
		Object[] criteria = { officecode, messageid };
		return  jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Notification.class), criteria);
	}


}
