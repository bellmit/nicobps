package obps.validators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import obps.models.BpaApplication;
import obps.models.BpaApplicationFee;
import obps.models.BpaApproval;
import obps.models.BpaProcessFlow;
import obps.models.BpaSiteInspection;
import obps.util.application.CommonMap;
import obps.util.common.Patterns;

@Component
public class BpaValidator {

//	-------------------------------------------------ControllerBuildingPermit validator-----------------------------
	public void BpaValidateSaveBpa(BpaApplication bpaApplication, Map<String, Object> response) {
		BpaApplication bpa = bpaApplication;

		if (bpa.getApplicationcode() != null) {
			if (bpa.getApplicationcode().trim().isEmpty() || bpa.getApplicationcode().length() > 20) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", " THE ENTERED APPLICATION CODE IS INVALID,ENTER A VALID APPLICATION CODE");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " VALUE IN APPLICATION CODE IS NULL, ENTER A VALID APPLICATION CODE");
		}

		if (bpa.getEdcrnumber() != null) {
			if (bpa.getEdcrnumber().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_ALPHA_NUMERIC, bpa.getEdcrnumber())
					|| bpa.getEdcrnumber().length() > 30) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", " THE ENTERED EDCR NUMBER IS INVALID,ENTER A VALID EDCR NUMBER");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " VALUE IN EDCR NUMBER IS NULL, ENTER A VALID EDCR NUMBER");
		}

		if (bpa.getOwnershiptypecode() != null) {
			if (!Patterns.PatternMatche(Patterns.XPATTERN_POSITIVE_INTEGER, bpa.getOwnershiptypecode().toString())
					|| bpa.getOwnershiptypecode().toString().length() > 1) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", " THE ENTEREDOWENER_SHIP_TYPE_CODE IS INVALID,ENTER A VALID OWENER_SHIP_TYPE_CODE");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " VALUE IN OWENER_SHIP_TYPE_CODE IS NULL, ENTER A VALID OWENER_SHIP_TYPE_CODE");
		}

		if (bpa.getOwnershipsubtype() != null) {
			if (bpa.getOwnershipsubtype().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.XPATTERN_STRING_SPACE, bpa.getOwnershipsubtype())
					|| bpa.getOwnershipsubtype().length() > 20) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", " THE ENTERED OWENER_SHIP_SUB_TYPE IS INVALID,ENTER A VALID OWENER_SHIP_SUB_TYPE");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " VALUE IN OWENER_SHIP_SUB_TYPE IS NULL, ENTER A VALID OWENER_SHIP_SUB_TYPE");
		}

		if (bpa.getPlotaddressline1() != null) {
			if (bpa.getPlotaddressline1().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.XPATTERN_STRING_SPACE, bpa.getPlotaddressline1())
					|| bpa.getPlotaddressline1().length() > 99) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", " THE ENTERED PLOT_ADDRESS_LINE_1 IS INVALID,ENTER A VALID PLOT_ADDRESS_LINE_1");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " VALUE IN PLOT_ADDRESS_LINE_1 IS NULL, ENTER A VALID PLOT_ADDRESS_LINE_1");
		}

		if (bpa.getPlotaddressline2() != null) {
			if (!bpa.getPlotaddressline2().trim().isEmpty()) {
				if (!Patterns.PatternMatche(Patterns.XPATTERN_STRING_SPACE, bpa.getPlotaddressline2())
						|| bpa.getPlotaddressline2().length() > 30) {
					response.put("code", HttpStatus.BAD_REQUEST.value());
					response.put("msg", "THE ENTERED PLOT_ADDRESS_LINE_2 IS INVALID,ENTER A VALID PLOT_ADDRESS_LINE_2");
				}
			}
		}

		if (bpa.getPlotvillagetown() != null) {
			if (bpa.getPlotvillagetown().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.XPATTERN_STRING_SPACE, bpa.getPlotvillagetown())
					|| bpa.getPlotvillagetown().length() > 30) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", "PLOT_VILLAGE_TOWN IS INVALID,ENTER A VALID PLOT_VILLAGE_TOWN");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " VALUE IN PLOT_VILLAGE_TOWN IS NULL, ENTER A VALID PLOT_VILLAGE_TOWN");
		}

		if (bpa.getPlotpincode() != null) {
			if (!Patterns.PatternMatche(Patterns.PATTERN_PINCODE, bpa.getPlotpincode().toString())
					|| bpa.getPlotpincode().toString().length() != 6) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", "PLOT_PINCODE IS INVALID,ENTER A VALID OFFICE_LOCATION_CODE");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " VALUE IN PLOT_PINCODE IS NULL, ENTER A VALID PLOT_PINCODE");
		}

		if (bpa.getOfficelocationcode() != null) {
			if (!Patterns.PatternMatche(Patterns.XPATTERN_POSITIVE_INTEGER, bpa.getOfficelocationcode().toString())
					|| bpa.getOfficelocationcode().toString().length() > 5) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", "OFFICE_LOCATION_CODE IS INVALID,ENTER A VALID OFFICE_LOCATION_CODE");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " VALUE IN OFFICE_LOCATION_CODE IS NULL, ENTER A VALID OFFICE_LOCATION_CODE");
		}

		if (bpa.getLandregistrationdetails() != null) {
			if (!bpa.getLandregistrationdetails().trim().isEmpty()) {
				if ( bpa.getLandregistrationdetails().length() > 99) {
					response.put("code", HttpStatus.BAD_REQUEST.value());
					response.put("msg", "LAND_REGISTRATION_DETAILS IS INVALID,ENTER A VALID LAND_REGISTRATION_DETAILS");
				}
			}
		}

		if (bpa.getLandregistrationno() != null) {
			if (!bpa.getLandregistrationno().trim().isEmpty()) {
				if (!Patterns.PatternMatche(Patterns.PATTERN_ALPHA_NUMERIC, bpa.getLandregistrationno())
						|| bpa.getLandregistrationno().length() > 20) {
					response.put("code", HttpStatus.BAD_REQUEST.value());
					response.put("msg",
							"LAND_REGISTRATION_DETAILS_NUMBER IS INVALID,ENTER A VALID LAND_REGISTRATION_DETAILS_NUMBER");
				}
			}
		}

		if (bpa.getHoldingno() != null) {
			if (!bpa.getHoldingno().trim().isEmpty()) {
				if (!Patterns.PatternMatche(Patterns.PATTERN_ALPHA_NUMERIC, bpa.getHoldingno())
						|| bpa.getHoldingno().length() > 5) {
					response.put("code", HttpStatus.BAD_REQUEST.value());
					response.put("msg", " HOLDING_NUMBER IS INVALID,ENTER A VALID HOLDING_NUMBER");
				}
			}
		}
		

		if (bpa.getEntrydate() != null) {
			if (!Patterns.PatternMatche(Patterns.PATTERN_DATE, bpa.getEntrydate())
					|| bpa.getEntrydate().length() > 30) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", " ENTRY DATE IS INVALID,ENTER A VALID ENTRY DATE");

			}
		}

	}

	public void BpaValidatebpaMakePayment(BpaApplicationFee bpaApplicationFee, Map<String, Object> response) {
		BpaApplicationFee bpa = bpaApplicationFee;
		if (bpa.getApplicationcode() != null) {
			if (bpa.getApplicationcode().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.XPATTERN_POSITIVE_START_ZERO, bpa.getApplicationcode())
					|| bpa.getApplicationcode().length() > 20) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", " THE ENTERED APPLICATION CODE IS INVALID,ENTER A VALID APPLICATION CODE");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " VALUE IN APPLICATION CODE IS NULL, ENTER A VALID APPLICATION CODE");
		}

	}

	public void BpaValidatesaveBPAStepTwo(BpaApplication bpaApplication, Map<String, Object> response) {

		BpaApplication bpa = bpaApplication;
		if (bpa.getApplicationcode() != null) {
			if (bpa.getApplicationcode().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.XPATTERN_POSITIVE_START_ZERO, bpa.getApplicationcode())
					|| bpa.getApplicationcode().length() > 20) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", " THE ENTERED APPLICATION CODE IS INVALID,ENTER A VALID APPLICATION CODE");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " VALUE IN APPLICATION CODE IS NULL, ENTER A VALID APPLICATION CODE");
		}

	}

//----------------------------------------------------ControllerBpaProcessing----------------------------------------

	public void ValidateapproveApplication(BpaApproval bpaAl, Map<String, Object> response) {

		List<CommonMap> conditions = bpaAl.getConditions();
		conditions.forEach(c -> {
			if (c.getValue() != null) {
				if (c.getValue().trim().isEmpty() || c.getValue().length() > 999) {

					response.put("code", HttpStatus.BAD_REQUEST.value());
					response.put("msg", " THE ENTERED CONDITION IS INVALID,ENTER A VALID CONDITION");
				}
			} else {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", " VALUE IN CONDITION IS NULL, ENTER A VALID CONDITION ");
			}
		});

		if (bpaAl.getProcessflow().getApplicationcode() != null) {
			if (bpaAl.getProcessflow().getApplicationcode().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.XPATTERN_POSITIVE_START_ZERO,
							bpaAl.getProcessflow().getApplicationcode())
					|| bpaAl.getProcessflow().getApplicationcode().length() > 20) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", " THE ENTERED APPLICATION CODE IS INVALID,ENTER A VALID APPLICATION CODE");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " VALUE IN APPLICATION CODE IS NULL, ENTER A VALID APPLICATION CODE");
		}

		if (bpaAl.getProcessflow().getFromusercode() != null) {
			if (!Patterns.PatternMatche(Patterns.XPATTERN_POSITIVE_INTEGER,
					bpaAl.getProcessflow().getFromusercode().toString())
					|| bpaAl.getProcessflow().getFromusercode() > 7) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", "INVALID VALUE IN FROM_USERCODE, ENTER A VALID FROM_USERCODE");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", "VALUE IN FROM_USERCODE IS NULL, ENTER A VALID FROM_USERCODE");
		}

		if (bpaAl.getProcessflow().getRemarks() != null) {
			if (!Patterns.PatternMatche(Patterns.XPATTERN_NO_SPECIAL_CHAR, bpaAl.getProcessflow().getRemarks())
					|| bpaAl.getProcessflow().getRemarks().length() > 255) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", "INVALID VALUE IN REMARKS, ENTER VALID REMARKS ");
			}
		}

		if (bpaAl.getProcessflow().getTousercode() != null) {
			if (!Patterns.PatternMatche(Patterns.XPATTERN_POSITIVE_INTEGER,
					bpaAl.getProcessflow().getTousercode().toString()) || bpaAl.getProcessflow().getTousercode() > 7) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", " INVALID VALUE IN TO_USERCODE , ENTER A VALID TO_USERCODE");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " VALUE IN TO_USERCODE IS NULL, ENTER A VALID FROM_USERCODE");
		}
	}

	public void Validateprocessbpapplication(BpaProcessFlow bpaPf, Map<String, Object> response) {

		if (bpaPf.getApplicationcode() != null) {
			if (bpaPf.getApplicationcode() == null || bpaPf.getApplicationcode().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.XPATTERN_POSITIVE_START_ZERO, bpaPf.getApplicationcode())
					|| bpaPf.getApplicationcode().length() > 20) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", " THE ENTERED APPLICATION CODE IS INVALID,ENTER A VALID APPLICATION CODE");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " APPLICATION CODE IS NULL, ENTER A VALID APPLICATION CODE");
		}

		if (bpaPf.getFromusercode() != null) {
			if (!Patterns.PatternMatche(Patterns.XPATTERN_POSITIVE_INTEGER, bpaPf.getFromusercode().toString())
					|| bpaPf.getFromusercode().toString().length() > 7) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", "INVALID VALUE IN FROM_USERCODE, ENTER A VALID FROM_USERCODE");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " FROM_USERCODE IS NULL, ENTER A VALID FROM_USERCODE");
		}

		if (bpaPf.getRemarks() != null) {
			if (!Patterns.PatternMatche(Patterns.XPATTERN_NO_SPECIAL_CHAR, bpaPf.getRemarks())
					|| bpaPf.getRemarks().length() > 255) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", "INVALID VALUE IN REMARKS, ENTER VALID REMARKS ");
			}
		}

		if (bpaPf.getTousercode() != null) {
			if (!Patterns.PatternMatche(Patterns.XPATTERN_POSITIVE_INTEGER, bpaPf.getTousercode().toString())
					|| bpaPf.getTousercode().toString().length() > 7) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", " INVALID VALUE IN TO_USERCODE , ENTER A VALID TO_USERCODE");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " TO_USERCODE IS NULL, ENTER A VALID FROM_USERCODE");
		}

	}

	public void ValidaterejectBPApplication(BpaProcessFlow bpaPf, Map<String, Object> response) {

		if (bpaPf.getApplicationcode() != null) {
			if (bpaPf.getApplicationcode().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.XPATTERN_POSITIVE_START_ZERO, bpaPf.getApplicationcode())
					|| bpaPf.getApplicationcode().length() > 20) {

				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", " THE ENTERED APPLICATION CODE IS INVALID,ENTER A VALID APPLICATION CODE");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " APPLICATION CODE IS NULL, ENTER A VALID APPLICATION CODE");
		}

		if (bpaPf.getRemarks() != null) {
			if (!Patterns.PatternMatche(Patterns.XPATTERN_NO_SPECIAL_CHAR, bpaPf.getRemarks())
					|| bpaPf.getRemarks().length() > 255) {
//				errorMap.put("INVALID REMARKS SIZE", "ENTER REMARKS NOT MORE THEN 255 IN LENGTH");
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", "INVALID VALUE IN REMARKS, ENTER  VALID REMARKS");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", "REMARKS FEILD IS NULL ,PLEASE ENTER VALID REMARKS");
		}

		if (bpaPf.getFromusercode() != null) {
			if (!Patterns.PatternMatche(Patterns.XPATTERN_POSITIVE_INTEGER, bpaPf.getFromusercode().toString())
					|| bpaPf.getFromusercode().toString().length() > 7) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", "INVALID VALUE IN FROM_USERCODE, ENTER A VALID FROM_USERCODE");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " FROM_USERCODE IS NULL, ENTER A VALID FROM_USERCODE");
		}
	}

	public void ValidatesaveBPASiteInspection(BpaSiteInspection bpaSi, HashMap<String, Object> response) {

		if (bpaSi.getApplicationcode() != null) {
			if (bpaSi.getApplicationcode().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.XPATTERN_POSITIVE_START_ZERO, bpaSi.getApplicationcode())
					|| bpaSi.getApplicationcode().length() > 20) {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", "THE ENTERED APPLICATION CODE IS INVALID, ENTER A VALID APPLICATION CODE");
			}
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", " APPLICATION CODE IS NULL, ENTER A VALID APPLICATION CODE");
		}
	}

}
