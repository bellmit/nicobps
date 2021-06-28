package obps.validators;

import java.io.IOException;
import java.util.Map;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obps.daos.DaoUploadBpaEnclosuersInterface;
import obps.util.common.Patterns;

@Service("uploadEnclosuresValidator")
public class UploadEnclosuresValidator implements UploadEnclosuresValidatorInterface{
	
	@Autowired
	private DaoUploadBpaEnclosuersInterface DaoUploadBpaEnclosuersInterface;
	
	@Override
	public boolean validateEnclosureDetails(Map<String, Object> param) {
		return DaoUploadBpaEnclosuersInterface.validateEnclosureDetails(param);
	}
	
}
