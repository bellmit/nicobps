package obps.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obps.daos.DaoUploadBpaEnclosuersInterface;
import obps.util.application.ServiceUtilInterface;

@Service("ServiceUploadBpaEnclosuers")
public class ServiceUploadBpaEnclosuers implements ServiceUploadBpaEnclosuersInterface{


	@Autowired
	private ServiceUtilInterface serviceUtilInterface;
	@Autowired
	private DaoUploadBpaEnclosuersInterface DaoUploadBpaEnclosuersInterface;

	@Override
	public Long getMaxAfrCode() {
		String sql = "SELECT MAX(afrcode) FROM nicobps.applicationflowremarks ";
		return serviceUtilInterface.getMaxValue(sql) + 1;
	}

	@Override
	public Long getMaxAppEnclosureCode() {
		String sql = "SELECT MAX(appenclosurecode) FROM nicobps.bpaenclosures ";
		return serviceUtilInterface.getMaxValue(sql) + 1;
	}

	@Override
	public boolean submitBpaEnclosureDetails(Map<String, Object> param) {
		return DaoUploadBpaEnclosuersInterface.submitBpaEnclosureDetails(param);
	}	
}
