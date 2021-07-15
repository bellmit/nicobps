package obps.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import obps.models.FeeMaster;
import obps.models.FeeTypes;
import obps.models.LicenseesRegistrationsm;
import obps.models.Occupancies;
import obps.models.SubOccupancies;
import obps.models.Usages;
import obps.services.ServiceEnclosureManagementInterface;
import obps.services.ServiceInitializationInterface;
import obps.util.application.ServiceUtilInterface;
import obps.validators.InitFeeMasterValidator;

import obps.validators.InitLicenseesRegistrationValidator;
import obps.validators.InitOccupanciesValidator;
import obps.validators.InitSubOccupanciesValidator;
import obps.validators.InitUsagesValidator;

@Controller
@Configuration
@PropertySource("classpath:application.properties")
public class ControllerInitialization {

	@Autowired
	private ServiceUtilInterface serviceUtilInterface;
	@Autowired
	private InitLicenseesRegistrationValidator initLicenseesRegistrationValidator;
	@Autowired
	private InitOccupanciesValidator initOccupanciesValidator;
	@Autowired
	private InitUsagesValidator initUsagesValidator;
	@Autowired
	private InitSubOccupanciesValidator initSubOccupanciesValidator;
	@Autowired
	private InitFeeMasterValidator initFeeMasterValidator;
	@Autowired

	private ServiceInitializationInterface serviceInitalizationInterface;
	@Resource
	private Environment environment;

	
	@GetMapping("/initlicenseesregistrationsm.htm")
	public String initlicenseesregistrationsm() {
		return "initialization/initlicenseesregistrationsm";
	}

	@PostMapping(value = "/initlicenseesregistrationsm.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> createlicenseesregistrationsm(
			@RequestBody Map<String, Object> licensee) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		String validate = "";
		if(licensee!=null) {
			String licenseeregistrationcode ="";
			if(serviceInitalizationInterface.getMaxLicenseecode()!=null) {
				licenseeregistrationcode = serviceInitalizationInterface.getMaxLicenseecode() + "";
				licensee.put("licenseeregistrationcode", licenseeregistrationcode);
			}
			validate = initLicenseesRegistrationValidator.validateInitLicenseesRegistration(licensee);
			if(validate!="")
			{
				response.put("code", 200);
				response.put("data",validate);
				return ResponseEntity.ok().body(response);
			}

		String sql = "";
		Object[] values = { licensee.get("licenseedescription") };
		sql = "SELECT * FROM masters.licenseesregistrationsm WHERE  LOWER(licenseedescription)=LOWER(?) ";

		boolean exist = serviceInitalizationInterface.checkExistance(sql, values);
		if(!exist) {
			System.out.println("does not exist");
		if (serviceInitalizationInterface.createLicenseeRegistration(licensee)) {
			System.out.println("insert init!!");
			response.put("code", 200);
			response.put("data", "Success");
			return ResponseEntity.ok().body(response);
		}
		}
		else {
			response.put("code", 200);
			response.put("data", "Exist");
			return ResponseEntity.ok().body(response);
		}
		}
		response.put("code", HttpStatus.OK);
		response.put("data", "Error");
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/listLicensees.htm")
	public @ResponseBody List<LicenseesRegistrationsm> listLicenseesRegistrationsms() {

		return serviceInitalizationInterface.listLicenseesRegistrationsms();
	}

	@GetMapping("/listFeeTypes.htm")
	public @ResponseBody List<FeeTypes> listFeeTypes() {

		return serviceInitalizationInterface.listFeeTypes();
	}

	@PostMapping(value = "/updatelicenseesregistrationsm.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updatelicenseesregistrationsm(
			@RequestBody LicenseesRegistrationsm licensee) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();
		String validate = "";
		if(licensee!=null) {
			if(licensee.getLicenseeregistrationcode()>0)
				param.put("licenseeregistrationcode", licensee.getLicenseeregistrationcode());
			if(licensee.getLicenseedescription()!=null)
				param.put("licenseedescription", licensee.getLicenseedescription());
			validate = initLicenseesRegistrationValidator.validateInitLicenseesRegistration(param);
			if(validate!="")
			{
				response.put("code", 200);
				response.put("data",validate);
				return ResponseEntity.ok().body(response);
			}
			String sql = "";
			Object[] values = { licensee.getLicenseedescription() };
			sql = "SELECT * FROM masters.licenseesregistrationsm WHERE  LOWER(licenseedescription)=LOWER(?) ";

			boolean exist = serviceInitalizationInterface.checkExistance(sql, values);
			if(!exist) {
				if (serviceInitalizationInterface.updateLicenseesRegistrationsm(licensee)) {
					response.put("code", 200);
					response.put("data", "Success");
					return ResponseEntity.ok().body(response);
				}
				}
				else {
					response.put("code", 200);
					response.put("data", "Exist");
					return ResponseEntity.ok().body(response);
				}
			
			
		}
	
				
	
		response.put("code", HttpStatus.OK);
		response.put("data", "Error");
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/initfeetypes.htm")
	public String initfeetypes() {
		return "initialization/initfeetypes";
	}

	@PostMapping(value = "/initfeetypes.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> initfeetypes(@RequestBody Map<String, Object> feetype) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		String feetypecode ="";
		if(serviceInitalizationInterface.getMaxFeeTypecode()!=null) {
			feetypecode = serviceInitalizationInterface.getMaxFeeTypecode() + "";
			feetype.put("feetypecode", feetypecode);
		}
		String validate="";
		validate=initFeeMasterValidator.validateInitFeeTypes(feetype);
		System.out.println(validate);
		if(validate!="") {
			response.put("code", 200);
			response.put("data", validate);
			return ResponseEntity.ok().body(response);
		}
			
//		user.put("usertype", "F");
		// check existance
		String sql = "";
		Object[] values = { feetype.get("feetypedescription") };
		sql = "SELECT * FROM masters.feetypes WHERE  LOWER(feetypedescription)=LOWER(?) ";

		boolean exist = serviceInitalizationInterface.checkExistance(sql, values);
		if(!exist) {
			if (serviceInitalizationInterface.initfeetypes(feetype)) {
				response.put("code", 200);
				response.put("data", "Success");
				return ResponseEntity.ok().body(response);
			}
		}
		else {
			response.put("code", 200);
			response.put("data", "exist");
			return ResponseEntity.ok().body(response);
		}
		
		response.put("code", HttpStatus.OK);
		response.put("data", "Error");
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/updateinitfeetypes.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updateinitfeetypes(@RequestBody FeeTypes feetype) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		String validate="";
		validate=initFeeMasterValidator.validateInitFeeTypes(feetype);
		System.out.println(validate);
		if(validate!="") {
			response.put("code", 200);
			response.put("data", validate);
			return ResponseEntity.ok().body(response);
		}
				String sql = "";
				Object[] values = { feetype.getFeetypedescription() };
				sql = "SELECT * FROM masters.feetypes WHERE  LOWER(feetypedescription)=LOWER(?) ";

				boolean exist = serviceInitalizationInterface.checkExistance(sql, values);
				if(!exist) {
					if (serviceInitalizationInterface.updatefeetypes(feetype)) {
						response.put("code", 200);
						response.put("data", "Success");
						return ResponseEntity.ok().body(response);
					}
				}
				else {
					response.put("code", 200);
					response.put("data", "exist");
					return ResponseEntity.ok().body(response);
				}
					
		
		response.put("code", HttpStatus.OK);
		response.put("data", "Error");
		return ResponseEntity.ok().body(response);
	}
 
	@PostMapping(value = "/updatefeemaster.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updatefeemaster(@RequestBody FeeMaster feemaster) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("feemaster:" + feemaster);
		String validate = initFeeMasterValidator.validateInitFeeMaster(feemaster);
		if(validate!="") {
			response.put("code", 200);
			response.put("data", validate);
			return ResponseEntity.ok().body(response);
		}
		// check existance
		String sql = "";
		Object[] values = { feemaster.getLicenseetypecode(), feemaster.getOfficecode(),
				feemaster.getFeetypecode() };
		sql = "SELECT * FROM masters.feemaster WHERE  licenseetypecode=? AND officecode=? AND feetypecode=? ";

		boolean exist = serviceInitalizationInterface.checkExistance(sql, values);
		if(!exist) {
			if (serviceInitalizationInterface.updatefeemaster(feemaster)) {
				response.put("code", 200);
				response.put("data", "Success");
				return ResponseEntity.ok().body(response);
			}
		}else {
			response.put("code", 200);
			response.put("data", "exist");
			return ResponseEntity.ok().body(response);
		}
		
		response.put("code", HttpStatus.OK);
		response.put("data","Error");
		return ResponseEntity.ok().body(response);
	}

	
	@PostMapping(value = "/initusages.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> initusages(@RequestBody Map<String, Object> usages) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if(usages!=null) {
			String validate="";
			validate=initUsagesValidator.validateInitUsages(usages);
			if(validate!="") {
				response.put("code", 200);
				response.put("data", validate);
				return ResponseEntity.ok().body(response);
			}
			String sql = "";
			Object[] values = { usages.get("usagecode"), usages.get("description"), usages.get("usagename") };
			sql = "SELECT * FROM masters.usages WHERE  LOWER(usagecode)=LOWER(?) OR LOWER(description)=LOWER(?) OR LOWER(usagename)=LOWER(?) ";

			boolean exist = serviceInitalizationInterface.checkExistance(sql, values);

			if (!exist) {
				if (serviceInitalizationInterface.initusages(usages)) {
					response.put("code", 200);
					response.put("data", "Success");
					return ResponseEntity.ok().body(response);
				}
			}else {
				response.put("code", 200);
				response.put("data", "Exist");
				return ResponseEntity.ok().body(response);
			}
		}
		

		response.put("code", HttpStatus.OK);
		response.put("data", "Error");
		return ResponseEntity.ok().body(response);
	}
	@PostMapping(value = "/updateusages.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updateusages(@RequestBody Usages usages) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();
		if(usages!=null) {
			if(usages.getSuboccupancycode()!=null)
				param.put("suboccupancycode", usages.getSuboccupancycode());
			if(usages.getUsagecode()!=null)
				param.put("usagecode", usages.getUsagecode());
			if(usages.getUsagename()!=null)
				param.put("usagename", usages.getUsagename());
			if(usages.getDescription()!=null)
				param.put("description", usages.getDescription());
			String validate="";
			validate=initUsagesValidator.validateInitUsages(param);
			if(validate!="") {
				response.put("code", 200);
				response.put("data", validate);
				return ResponseEntity.ok().body(response);
			}
			String sql = "";
			Object[] values = { usages.getUsagecode(), usages.getDescription(), usages.getUsagename() };
			sql = "SELECT * FROM masters.usages WHERE  LOWER(usagecode)=LOWER(?) AND LOWER(description)=LOWER(?) OR LOWER(usagename)=LOWER(?) ";

			boolean exist = serviceInitalizationInterface.checkExistance(sql, values);
			if (!exist) {
				if (serviceInitalizationInterface.updateusages(usages)) {
					response.put("code", 200);
					response.put("data", "Success");
					return ResponseEntity.ok().body(response);
				}
			}else {
				response.put("code", 200);
				response.put("data", "Exist");
				return ResponseEntity.ok().body(response);
			}
		}

		

		response.put("code", HttpStatus.OK);
		response.put("data", "Error");
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/initoccupancies.htm")
	public String initoccupancies() {
		return "initialization/initoccupancies";
	}

	@GetMapping("/initsuboccupancies.htm")
	public String initsuboccupancies(Model model, HttpServletRequest req) {
		model.addAttribute("occupancies", serviceUtilInterface.listOccupancies());
		return "initialization/initsuboccupancies";
	}

	@GetMapping("/initusages.htm")
	public String initusages(Model model, HttpServletRequest req) {
		model.addAttribute("suboccupancies", serviceUtilInterface.listSubOccupancies());
		return "initialization/initusages";
	}

	@GetMapping("/initfeemaster.htm")
	public String initfeemaster(Model model, HttpServletRequest req) {
		model.addAttribute("offices", serviceUtilInterface.listUserOffices());
		model.addAttribute("licenseetypes", serviceUtilInterface.listLicenseetypes());
		model.addAttribute("feetypes", serviceUtilInterface.listFeetypes());

		return "initialization/initfeemaster";
	}

	@PostMapping(value = "/initfeemaster.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> initfeemaster(@RequestBody Map<String, Object> feemaster) {
		HashMap<String, Object> response = new HashMap<String, Object>();

		String feecode="";
		if(serviceInitalizationInterface.getMaxFeeCode()!=null) {
			feecode = serviceInitalizationInterface.getMaxFeeCode() + "";
			feemaster.put("feecode", feecode);
		}
			
		String validate= initFeeMasterValidator.validateInitFeeMaster(feemaster);
		System.out.println("validate"+validate);
		if(validate!="") {
			response.put("code", 200);
			response.put("data", validate);
			return ResponseEntity.ok().body(response);
		}
		// check existance
				String sql = "";
				Object[] values = { Integer.parseInt(feemaster.get("licenseetypecode").toString()) , Integer.parseInt(feemaster.get("officecode").toString()),
						Integer.parseInt(feemaster.get("feetypecode").toString()) };
				sql = "SELECT * FROM masters.feemaster WHERE  licenseetypecode=? AND officecode=? AND feetypecode=? ";

				boolean exist = serviceInitalizationInterface.checkExistance(sql, values);
				if(!exist) {
					if (serviceInitalizationInterface.initfeemaster(feemaster)) {
						response.put("code", 200);
						response.put("data", "Success");
						return ResponseEntity.ok().body(response);
					}
				}
				else {
					response.put("code", 200);
					response.put("data", "exist");
					return ResponseEntity.ok().body(response);
				}
		
		response.put("code", HttpStatus.OK);
		response.put("data", "Error");
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/initsuboccupancies.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> initsuboccupancies(@RequestBody Map<String, Object> suboccupancies) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		
		if(suboccupancies!=null) {
			String validate="";
			validate = initSubOccupanciesValidator.validateInitSubOccupancies(suboccupancies);
			if(validate!="") {
				response.put("code", 200);
				response.put("data", validate);
				return ResponseEntity.ok().body(response);
			} 
			String sql = "";
			Object[] values = { suboccupancies.get("suboccupancycode"), suboccupancies.get("description"),
					suboccupancies.get("suboccupancyname") };
			sql = "SELECT * FROM masters.suboccupancies WHERE  LOWER(suboccupancycode)=LOWER(?) OR LOWER(description)=LOWER(?) OR LOWER(suboccupancyname)=LOWER(?) ";

			boolean exist = serviceInitalizationInterface.checkExistance(sql, values);

			if (!exist) {
				if (serviceInitalizationInterface.initsuboccupancies(suboccupancies)) {
					response.put("code", 200);
					response.put("data", "Success");
					return ResponseEntity.ok().body(response);
				}
			} 
			else {
				response.put("code", 200);
				response.put("data", "Exist");
				return ResponseEntity.ok().body(response);
			}
		}
		

		response.put("code", HttpStatus.OK);
		response.put("data", "Error");
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping(value = "/updatesuboccupancy.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updatesuboccupancy(@RequestBody SubOccupancies suboccupancies) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();
		if(suboccupancies!=null) {
			if(suboccupancies.getSuboccupancycode()!=null)
				param.put("suboccupancycode", suboccupancies.getSuboccupancycode());
			if(suboccupancies.getDescription()!=null)
				param.put("description", suboccupancies.getDescription());
			if(suboccupancies.getSuboccupancyname()!=null)
				param.put("suboccupancyname", suboccupancies.getSuboccupancyname());
			String validate="";
			validate = initSubOccupanciesValidator.validateInitSubOccupancies(param);
			if(validate!="") {
				response.put("code", 200);
				response.put("data", validate);
				return ResponseEntity.ok().body(response);
			}
			String sql = "";
			Object[] values = { suboccupancies.getSuboccupancycode(), suboccupancies.getDescription(),
					suboccupancies.getSuboccupancyname() };
			sql = "SELECT * FROM masters.suboccupancies WHERE  LOWER(suboccupancycode)=LOWER(?) AND LOWER(description)=LOWER(?) AND LOWER(suboccupancyname)=LOWER(?) ";

			boolean exist = serviceInitalizationInterface.checkExistance(sql, values);
			if (!exist) {
				if (serviceInitalizationInterface.updatesuboccupancy(suboccupancies)) {
					response.put("code", 200);
					response.put("data","Success");
					return ResponseEntity.ok().body(response);
				}
			}else {
				response.put("code", 200);
				response.put("data","Exist");
				return ResponseEntity.ok().body(response);
			}
		}
		// check existance
		

		response.put("code", HttpStatus.OK);
		response.put("data", "Error");
		return ResponseEntity.ok().body(response);
	}

	

	@PostMapping(value = "/initoccupancies.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> initoccupancies(@RequestBody Map<String, Object> occupancy) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		String validate="";
		if(occupancy!=null) {
			validate=initOccupanciesValidator.validateInitOccupancies(occupancy);
			if(validate!="") {
				response.put("code", 200);
				response.put("data",validate);
				return ResponseEntity.ok().body(response);
			}
				
			String sql = "";
			Object[] values = { occupancy.get("occupancycode"), occupancy.get("occupancyname"),
					occupancy.get("occupancyalias") };
			sql = "SELECT * FROM masters.occupancies WHERE  LOWER(occupancycode)=LOWER(?) OR LOWER(occupancyname)=LOWER(?) OR LOWER(occupancyalias)=LOWER(?) ";
			boolean exist = serviceInitalizationInterface.checkExistance(sql, values);
			System.out.println("exist::"+exist);
			if (!exist) {
				if (serviceInitalizationInterface.initoccupancy(occupancy)) {
					response.put("code", 200);
					response.put("data", "Success");
					return ResponseEntity.ok().body(response);
				}
			}
			else {
				response.put("code", 200);
				response.put("data", "Exist");
				return ResponseEntity.ok().body(response);
			}
				
		}
	
		response.put("code", HttpStatus.OK);
		response.put("data", "Error");
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/updateoccupancy.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updateoccupancy(@RequestBody Occupancies occupancy) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();
		String validate="";
		if(occupancy!=null) {
			if(occupancy.getOccupancycode()!=null)
				param.put("occupancycode", occupancy.getOccupancycode());
			if(occupancy.getOccupancyname()!=null)
				param.put("occupancyname", occupancy.getOccupancyname());
			if(occupancy.getOccupancyalias()!=null)
				param.put("occupancyalias", occupancy.getOccupancyalias());
			validate=initOccupanciesValidator.validateInitOccupancies(param);
			if(validate!="") {
				response.put("code", 200);
				response.put("data",validate);
				return ResponseEntity.ok().body(response);
			}
			
		String sql = "";
		Object[] values = { occupancy.getOccupancycode(), occupancy.getOccupancyname(),
				occupancy.getOccupancyalias()};
		sql = "SELECT * FROM masters.occupancies WHERE  LOWER(occupancycode)=LOWER(?) AND LOWER(occupancyname)=LOWER(?) AND LOWER(occupancyalias)=LOWER(?)  ";
		boolean exist = serviceInitalizationInterface.checkExistance(sql, values);
				if (!exist) {
			if (serviceInitalizationInterface.updateoccupancy(occupancy)) {
				response.put("code", 200);
				response.put("data", "Success");
				return ResponseEntity.ok().body(response);
			}
		}else {
			response.put("code", 200);
			response.put("data", "Exist");
			return ResponseEntity.ok().body(response);
		}
				
		}
		response.put("code", HttpStatus.OK);
		response.put("data", "Error");
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/listOccupancies.htm")
	public @ResponseBody List<Occupancies> listOccupancies() {
		System.out.println("list occupancies");
		return serviceInitalizationInterface.listOccupancies();
	}

	@GetMapping("/listUsages.htm")
	public @ResponseBody List<Usages> listUsages() {
		System.out.println("listUsages");
		return serviceInitalizationInterface.listUsages();
	}

	@GetMapping("/listFeeMaster.htm")
	public @ResponseBody List<FeeMaster> listFeeMaster() {
		System.out.println("list fee master");
		return serviceInitalizationInterface.listFeeMaster();
	}

	@GetMapping("/listSubOccupancy.htm")
	public @ResponseBody List<SubOccupancies> listSubOccupancy() {
		System.out.println("listSubOccupancyr");
		return serviceInitalizationInterface.listSubOccupancy();
	}
	
	
}
