package obps.util.notifications;


import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("daoNotification")
public class DaoNotification{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Environment env;
	
	
	@Autowired
	public void createTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Notification notificationDetails(Integer officecode,String messageid) {
		String sql = "SELECT O.officecode,officeshortname,smsusername,smspassword,smssenderid,securekey,senderemailid,emailidpassword, "
				   + "OFN.messageid,templateid,smsbody,emailbody,emailsubject " 
				   + "FROM masters.offices O " 
				   + "INNER JOIN masters.officenotications OFN ON OFN.officecode=O.officecode " 
				   + "INNER JOIN masters.notifications N ON N.messageid=OFN.messageid " 
				   +"WHERE O.officecode=? AND OFN.messageid=? ";
		Object[] criteria = { officecode, messageid };
		List<Notification> notification = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Notification.class), criteria);
		
		Notification nf = new Notification();
		if(notification.size()>0) {
			nf = notification.get(0);
			nf.setSms_url(env.getProperty("sms.url"));
			nf.setEmail_api_hostnane(env.getProperty("email.api.hostnane"));
			nf.setEmail_hostname(env.getProperty("email.hostname"));
			nf.setEmail_port(env.getProperty("email.port"));
		}	
		return nf;
	}


}
