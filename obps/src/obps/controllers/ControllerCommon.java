package obps.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import obps.util.application.CommonMap;
import obps.util.application.ServiceUtilInterface;

@Controller
public class ControllerCommon {
	@Autowired
	private ServiceUtilInterface serviceUtilInterface;

	@PostMapping("/listOffices.htm")
	public @ResponseBody List<CommonMap> listOffices() {
		return serviceUtilInterface.listOffices();
	}

	@PostMapping("/listOffices.htm/registeringoffice")
	public @ResponseBody List<Map<String, Object>> listRegisteringOffices() {
		return serviceUtilInterface.listRegisteringOffices();
	}

	@PostMapping("/listOffices/registeringoffice.htm")
	public @ResponseBody List<Map<String, Object>> listOfficesforRegisteringOffice(Integer registeringofficecode) {
		return serviceUtilInterface.listRegisteringOffices(registeringofficecode);
	}

	@PostMapping("/listOffices/ValidUserOffices.htm")
	public @ResponseBody List<Map<String, Object>> listValidUserOffice(HttpServletRequest request) {
		System.out.println("List valid offices");
		Integer usercode = Integer.valueOf((String)  request.getSession().getAttribute("usercode"));
		return serviceUtilInterface.listUserValidOffices(usercode);
	}
}
