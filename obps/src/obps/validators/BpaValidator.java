package obps.validators;

import java.util.List;
import java.util.Map;

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
	public void BpaValidateSaveBpa(BpaApplication bpaApplication, Map<String, String> errorMap) {
		BpaApplication bpa = bpaApplication;

		if (bpa.getApplicationcode() != null) {
			if (bpa.getApplicationcode().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_POSITIVEINTEGER, bpa.getApplicationcode())
					|| bpa.getApplicationcode().length() > 20) {
				errorMap.put("INVALID APPLICATION CODE", "ENTER A VALID APPLICATION CODE");
			}
		} else {
			errorMap.put("APPLICATION CODE NULL", "ENTER A VALID APPLICATION CODE");
		}

		if (bpa.getEdcrnumber() != null) {
			if (bpa.getEdcrnumber().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_ALPHA_NUMERIC, bpa.getEdcrnumber())
					|| bpa.getEdcrnumber().length() > 30) {
				errorMap.put("INVALID EDCR NUMBER", "ENTER A VALID EDCR NUMBER ");
			}
		} else {
			errorMap.put("EDCR NUMBER NULL ", "ENTER A VALID EDCR NUMBER ");
		}

		if (bpa.getOwnershiptypecode() != null) {
			if (!Patterns.PatternMatche(Patterns.PATTERN_POSITIVEINTEGER, bpa.getOwnershiptypecode().toString())
					|| bpa.getOwnershiptypecode().toString().length() > 1) {
				errorMap.put("INVALID OWENER_SHIP_TYPE_CODE", "ENTER A VALID OWENER_SHIP_TYPE_CODE ");
			}
		} else {
			errorMap.put("OWENER_SHIP_TYPE_CODE NULL", "ENTER A VALID OWENER_SHIP_TYPE_CODE ");
		}

		if (bpa.getOwnershipsubtype() != null) {
			if (bpa.getOwnershipsubtype().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_STRING_SPACE, bpa.getOwnershipsubtype())
					|| bpa.getOwnershipsubtype().length() > 20) {
				errorMap.put("INVALID OWENER_SHIP_SUB_TYPE", "ENTER A VALID OWENER_SHIP_SUB_TYPE");
			}
		} else {
			errorMap.put(" OWENER_SHIP_SUB_TYPE NULL", "ENTER A VALID OWENER_SHIP_SUB_TYPE");
		}

		if (bpa.getPlotaddressline1() != null) {
			if (bpa.getPlotaddressline1().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_STRING_SPACE, bpa.getPlotaddressline1())
					|| bpa.getPlotaddressline1().length() > 99) {
				errorMap.put("INVALID PLOT_ADDRESS_LINE_1", "ENTER A VALID PLOT_ADDRESS_LINE_1");
			}
		} else {
			errorMap.put("PLOT_ADDRESS_LINE_1 NULL", "ENTER A VALID PLOT_ADDRESS_LINE_1");
		}

		if (bpa.getPlotaddressline2() != null) {
			if (bpa.getPlotaddressline2().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_STRING_SPACE, bpa.getPlotaddressline2())
					|| bpa.getPlotaddressline2().length() > 30) {
				errorMap.put("INVALID PLOT_ADDRESS_LINE_2", "ENTER A VALID PLOT_ADDRESS_LINE_1");
			}
		}

		if (bpa.getPlotvillagetown() != null) {
			if (bpa.getPlotvillagetown().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_STRING_SPACE, bpa.getPlotvillagetown())
					|| bpa.getPlotvillagetown().length() > 30) {
				errorMap.put("INVALID PLOT_VILLAGE_TOWN", "ENTER A VALID PLOT_VILLAGE_TOWN");
			}
		} else {
			errorMap.put(" PLOT_VILLAGE_TOWN NULL", "ENTER A VALID PLOT_VILLAGE_TOWN");
		}

		if (bpa.getPlotpincode() != null) {
			if (!Patterns.PatternMatche(Patterns.PATTERN_PINCODE, bpa.getPlotpincode().toString())
					|| bpa.getPlotpincode().toString().length() != 6) {
				errorMap.put("INVALID PLOT_PINCODE", "ENTER A VALID PLOT_PINCODE ");
			}
		} else {
			errorMap.put("PLOT_PINCODE NULL", "ENTER A VALID PLOT_PINCODE ");
		}

		if (bpa.getOfficelocationcode() != null) {
			if (!Patterns.PatternMatche(Patterns.PATTERN_POSITIVEINTEGER, bpa.getOfficelocationcode().toString())
					|| bpa.getOfficelocationcode().toString().length() > 5) {
				errorMap.put("INVALID OFFICE_LOCATION_CODE", "ENTER A VALID OFFICE_LOCATION_CODE");
			}
		} else {
			errorMap.put(" OFFICE_LOCATION_CODE NULL", "ENTER A VALID OFFICE_LOCATION_CODE");
		}

		if (bpa.getLandregistrationdetails() != null) {
			if (bpa.getLandregistrationdetails().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_STRING_SPACE, bpa.getLandregistrationdetails())
					|| bpa.getLandregistrationdetails().length() > 99) {
				errorMap.put("INVALID LAND_REGISTRATION_DETAILS", "ENTER A VALID LAND_REGISTRATION_DETAILS");
			}
		}

		if (bpa.getLandregistrationno() != null) {
			if (bpa.getLandregistrationno().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_STRING_SPACE, bpa.getLandregistrationno())
					|| bpa.getLandregistrationno().length() > 20) {
				errorMap.put("INVALID LAND_REGISTRATION_DETAILS_NUMBER",
						"ENTER A VALID LAND_REGISTRATION_DETAILS_NUMBER");

			}
		}
		if (bpa.getPlotidentifier1() != null) {
			if (bpa.getPlotidentifier1().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_STRING_SPACE, bpa.getPlotidentifier1())
					|| bpa.getPlotidentifier1().length() > 10) {
				errorMap.put("INVALID PLOT_IDENTIFIER_1", "ENTER A VALID PLOT_IDENTIFIER_1");
			}
		}
		if (bpa.getPlotidentifier2() != null) {
			if (bpa.getPlotidentifier2().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_STRING_SPACE, bpa.getPlotidentifier2())
					|| bpa.getPlotidentifier2().length() > 10) {
				errorMap.put("INVALID PLOT_IDENTIFIER_3", "ENTER A VALID PLOT_IDENTIFIER_3");
			}
		}
		if (bpa.getPlotidentifier3() != null) {
			if (bpa.getPlotidentifier3().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_STRING_SPACE, bpa.getPlotidentifier3())
					|| bpa.getPlotidentifier3().length() > 10) {
				errorMap.put("INVALID PLOT_IDENTIFIER_3", "ENTER A VALID PLOT_IDENTIFIER_3");
			}
		}

		if (bpa.getHoldingno() != null) {
			if (bpa.getHoldingno() == null || bpa.getHoldingno().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_STRING_SPACE, bpa.getHoldingno())
					|| bpa.getHoldingno().length() > 30) {
				errorMap.put("INVALID HOLDING_NUMBER", "ENTER A VALID HOLDING_NUMBER");
			}
		}

		if (bpa.getEntrydate() != null) {
			if (!Patterns.PatternMatche(Patterns.PATTERN_DATE, bpa.getEntrydate())
					|| bpa.getEntrydate().length() > 30) {
				errorMap.put("INVALID EDCR NUMBER", "ENTER A VALID EDCR NUMBER FEILD CANNIOT BE EMPTY");
			}
		}

	}

	public void BpaValidatebpaMakePayment(BpaApplicationFee bpaApplicationFee, Map<String, String> errorMap) {
		BpaApplicationFee bpa = bpaApplicationFee;
		if (bpa.getApplicationcode() != null) {
			if (bpa.getApplicationcode().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_POSITIVEINTEGER, bpa.getApplicationcode())
					|| bpa.getApplicationcode().length() > 20) {
				errorMap.put("INVALID APPLICATION CODE", "ENTER A VALID APPLICATION CODE");
			}
		} else {
			errorMap.put(" APPLICATION CODE NULL", "ENTER A VALID APPLICATION CODE");
		}

	}

	public void BpaValidatesaveBPAStepTwo(BpaApplication bpaApplication, Map<String, String> errorMap) {

		BpaApplication bpa = bpaApplication;
		if (bpa.getApplicationcode() != null) {
			if (bpa.getApplicationcode().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_POSITIVEINTEGER, bpa.getApplicationcode())
					|| bpa.getApplicationcode().length() > 20) {
				errorMap.put("INVALID APPLICATION CODE", "ENTER A VALID APPLICATION CODE");
			}
		} else {
			errorMap.put(" APPLICATION CODE NULL", "ENTER A VALID APPLICATION CODE");
		}

	}

//----------------------------------------------------ControllerBpaProcessing----------------------------------------

	public void ValidateapproveApplication(BpaApproval bpaAl, Map<String, String> errorMap) {

		List<CommonMap> conditions = bpaAl.getConditions();
		conditions.forEach(c -> {
			if (c.getValue() != null) {
				if (c.getValue().trim().isEmpty() || c.getValue().length() > 999) {
					errorMap.put("INVALID CONDITION", "ENTER A VALID CONDITION");
				}
			} else {
				errorMap.put(" CONDITION NULL", "ENTER A VALID CONDITION");
			}
		});

		if (bpaAl.getProcessflow().getApplicationcode() != null) {
			if (bpaAl.getProcessflow().getApplicationcode().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_POSITIVEINTEGER,
							bpaAl.getProcessflow().getApplicationcode())
					|| bpaAl.getProcessflow().getApplicationcode().length() > 20) {
				errorMap.put("INVALID APPLICATION CODE", "ENTER A VALID APPLICATION CODE");
			}
		} else {
			errorMap.put(" APPLICATION CODE NULL", "ENTER A VALID APPLICATION CODE");
		}

		if (bpaAl.getProcessflow().getFromusercode() != null) {
			if (!Patterns.PatternMatche(Patterns.PATTERN_POSITIVEINTEGER,
					bpaAl.getProcessflow().getFromusercode().toString())
					|| bpaAl.getProcessflow().getFromusercode() > 7) {
				errorMap.put("INVALID FROM_USERCODE", "ENTER A VALID FROM_USERCODE");
			}
		} else {
			errorMap.put(" FROM_USERCODE  NULL", "ENTER A VALID FROM_USERCODE ");
		}

		if (bpaAl.getProcessflow().getRemarks() != null) {
			if (!Patterns.PatternMatche(Patterns.NO_SPECIAL, bpaAl.getProcessflow().getRemarks())
					|| bpaAl.getProcessflow().getRemarks().length() > 255) {
				errorMap.put("INVALID REMARKS SIZE", "ENTER REMARKS NOT MORE THEN 255 IN LENGTH");
			}
		}
		
		if (bpaAl.getProcessflow().getTousercode() != null) {
			if (!Patterns.PatternMatche(Patterns.PATTERN_POSITIVEINTEGER,
					bpaAl.getProcessflow().getTousercode().toString()) || bpaAl.getProcessflow().getTousercode() > 7) {
				errorMap.put("INVALID TO_USERCODE", "ENTER A VALID TO_USERCODE");
			}
		} else {
			errorMap.put(" TO_USERCODE  NULL", "ENTER A VALID TO_USERCODE ");
		}
	}

	public void Validateprocessbpapplication(BpaProcessFlow bpaPf, Map<String, String> errorMap) {

		if (bpaPf.getApplicationcode() != null) {
			if (bpaPf.getApplicationcode() == null || bpaPf.getApplicationcode().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_POSITIVEINTEGER, bpaPf.getApplicationcode())
					|| bpaPf.getApplicationcode().length() > 20) {
				errorMap.put("INVALID APPLICATION CODE", "ENTER A VALID APPLICATION CODE");
			}
		} else {
			errorMap.put(" APPLICATION CODE NULL", "ENTER A VALID APPLICATION CODE");
		}

		if (bpaPf.getFromusercode() != null) {
			if (!Patterns.PatternMatche(Patterns.PATTERN_POSITIVEINTEGER, bpaPf.getFromusercode().toString())
					|| bpaPf.getFromusercode().toString().length() > 7) {
				errorMap.put("INVALID FROM_USERCODE", "ENTER A VALID FROM_USERCODE");
			}
		} else {
			errorMap.put(" FROM_USERCODE NULL", "ENTER A VALID FROM_USERCODE");
		}

		if (bpaPf.getRemarks() != null) {
			if (!Patterns.PatternMatche(Patterns.NO_SPECIAL, bpaPf.getRemarks()) || bpaPf.getRemarks().length() > 255) {
				errorMap.put("INVALID REMARKS SIZE", "ENTER REMARKS NOT MORE THEN 255 IN LENGTH");
			}
		}

		if (bpaPf.getTousercode() != null) {
			if (!Patterns.PatternMatche(Patterns.PATTERN_POSITIVEINTEGER, bpaPf.getTousercode().toString())
					|| bpaPf.getTousercode().toString().length() > 7) {
				errorMap.put("INVALID TO_USERCODE", "ENTER A VALID TO_USERCODE");
			}
		} else {
			errorMap.put("TO_USERCODE NULL", "ENTER A VALID TO_USERCODE");
		}

	}

	public void ValidaterejectBPApplication(BpaProcessFlow bpaPf, Map<String, String> errorMap) {

		if (bpaPf.getApplicationcode() != null) {
			if (bpaPf.getApplicationcode().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_POSITIVEINTEGER, bpaPf.getApplicationcode())
					|| bpaPf.getApplicationcode().length() > 20) {
				errorMap.put("INVALID APPLICATION CODE", "ENTER A VALID APPLICATION CODE");
			}
		} else {
			errorMap.put("APPLICATION CODE NULL", "ENTER A VALID APPLICATION CODE");
		}

		if (bpaPf.getRemarks() != null) {
			if (!Patterns.PatternMatche(Patterns.NO_SPECIAL, bpaPf.getRemarks()) || bpaPf.getRemarks().length() > 255) {
				errorMap.put("INVALID REMARKS SIZE", "ENTER REMARKS NOT MORE THEN 255 IN LENGTH");
			}
		} else {
			errorMap.put("REMARKS NULL", "ENTER VALID REMARKS");
		}

		if (bpaPf.getFromusercode() != null) {
			if (!Patterns.PatternMatche(Patterns.PATTERN_POSITIVEINTEGER, bpaPf.getFromusercode().toString())
					|| bpaPf.getFromusercode().toString().length() > 7) {
				errorMap.put("INVALID FROM_USERCODE", "ENTER A VALID FROM_USERCODE");
			}
		} else {
			errorMap.put("FROM_USERCODE NULL", "ENTER VALID FROM_USERCODE");
		}
	}

	public void ValidatesaveBPASiteInspection(BpaSiteInspection bpaSi, Map<String, String> errorMap) {
		if (bpaSi.getApplicationcode() != null) {
			if (bpaSi.getApplicationcode().trim().isEmpty()
					|| !Patterns.PatternMatche(Patterns.PATTERN_POSITIVEINTEGER, bpaSi.getApplicationcode())
					|| bpaSi.getApplicationcode().length() > 20) {
				errorMap.put("INVALID APPLICATION CODE", "ENTER A VALID APPLICATION CODE");
			}
		} else {
			errorMap.put("APPLICATION CODE NULL", "ENTER A VALID APPLICATION CODE");
		}
	}
}
