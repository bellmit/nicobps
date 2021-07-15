package obps.validators;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import obps.util.common.Patterns;

@Component
public class PrintPermitValidator {

	public String validate_params(Map<String, String> params) {
		String response = "1";
		String applicationcode = params.get("applicationcode");
		String permitnumber = params.get("permitnumber");
		String edcrnumber = params.get("edcrnumber");
		String ownername = params.get("ownername");
		String fromentrydate = params.get("fromentrydate");
		String toentrydate = params.get("toentrydate");
		String criteria = params.get("criteria");

		if (criteria != null) {
			if (criteria.equals("byappcode")) {
				if (applicationcode != null) {
					if (!Patterns.PatternCompileMatche(Patterns.PATTERN_ALPHA_NUMERIC, applicationcode)) {
						response = "application code is invalid";
					} else if (applicationcode.length() > 20) {
						response = "application code size error";
					}
				} else {
					response = "application code is NULL";
				}
			} else if (criteria.equals("bypermitno")) {
				if (permitnumber != null) {
					if (!Patterns.PatternCompileMatche(Patterns.PATTERN_ALPHA_NUMERIC, permitnumber)) {
						response = "application code is invalid";
					} else if (permitnumber.length() > 20) {
						response = "permit number size error";
					}

				} else {
					response = "permit number is NULL";
				}

			} else if (criteria.equals("byedcrno")) {
				if (edcrnumber != null) {
					if (!Patterns.PatternCompileMatche(Patterns.PATTERN_ALPHA_NUMERIC, edcrnumber)) {
						response = "edcr number is invalid";
					} else if (edcrnumber.length() > 30) {
						response = "edcr number size error";
					}

				} else {
					response = "edcr number is NULL";
				}

			} else if (criteria.equals("byowner")) {
				if (ownername != null) {
					if (!Patterns.PatternCompileMatche(Patterns.PATTERN_NAME, ownername)) {
						response = "owner name is invalid";
					} else if (ownername.length() > 50) {
						response = "owner name size error";
					}

				} else {
					response = "owner name is NULL";
				}
			} else if (criteria.equals("byentrydate")) {
				if (fromentrydate != null && toentrydate != null && !fromentrydate.equals("")
						&& !toentrydate.equals("")) {
					if (!Patterns.PatternCompileMatche(Patterns.PATTERN_DATE, fromentrydate)) {
						response = "from entry date is invalid";
					} else if (!Patterns.PatternCompileMatche(Patterns.PATTERN_DATE, toentrydate)) {
						response = " to entry date is invalid";
					} else {
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						try {
							Date date1 = sdf.parse(fromentrydate);

							Date date2 = sdf.parse(toentrydate);

							System.out.println("date1:::" + date1 + "date2:::" + date2);

							int result = date1.compareTo(date2);

							System.out.println("result: " + result);
							if (result > 0) {
								response = "fromdate should not be after todate";

							}
						} catch (Exception e) {
							response = "Error parsing date";

						}

					}

				} else {
					response = "Date Range cannot be NULL or Empty";
				}

			} else {
				response = "wrong criteria";
			}

		} else {
			response = "criteria is null";
		}

		return response;
	}

	public String validate_transactioncode(Long transactioncode) {
		String response = "";

		if (transactioncode != null) {

			if (!Patterns.PatternCompileMatche(Patterns.PATTERN_POSITIVEINTEGER, transactioncode.toString())) {
				response = "Not a Number";
			}
		} else {
			response = "NULL value";
		}

		return response;
	}

	public String validate_permitnumber(String permitnumber) {
		String response = "";

		if (permitnumber != null) {
			if (!Patterns.PatternCompileMatche(Patterns.PATTERN_ALPHA_NUMERIC, permitnumber)) {
				response = "application code is invalid";
			} else if (permitnumber.length() > 20) {
				response = "permit number  size error";
			}

		} else {
			response = "permit number is NULL";
		}

		return response;
	}

}
