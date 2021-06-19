<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title></title>
</head>
<body>
	<div>
		<h3>Payment Test Page(Billdesk)</h3>
		<form name="paymentTestForm" autocomplete="off"
			action="./paymentconfirmation.htm" method="post">

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			 

						<table>
 
							<tr>
								<td class=" font-weight-bold w-25 ">Application Code :</td>
								<td class="col-xs-5 "><input type="text"
									name="applicationcode" value="DCR52021A50C20101011" /></td>
							</tr>
							<tr>
								<td class=" font-weight-bold">Fee Code :</td>
								<td class="col-xs-5 "><input type="text" name="feecode"
									value="1" /></td>
							</tr>
							<tr>
								<td class=" font-weight-bold">Module Code :</td>
								<td class="col-xs-5 "><input type="text" name="modulecode"
									value="2" /></td>
							</tr>

							<tr>
								<td class=" font-weight-bold">Fee Amount :</td>
								<td class="col-xs-5 "><input type="text" name="feeamount"
									value="100" /></td>

							</tr>
							<tr>
								<td class=" font-weight-bold">User Code :</td>
								<td class="col-xs-5 "><input type="text" name="usercode"
									value="3" /></td>

							</tr>
							<tr>
								<td class=" font-weight-bold">To Process Code :</td>
								<td class="col-xs-5 "><input type="text"
									name="toprocesscode" value="4" /></td>

							</tr>
						</table>

					

					<input type="submit" class="" value="PAY" />
 
		</form>

	</div>
	<hr>
	<div>
	<h3>Payment Test Page(0(zero) amount)</h3>
		<form name="paymentTestForm" autocomplete="off"
			action="./paymentconfirmation.htm" method="post">

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
	

						<table>

							 
							<tr>
								<td class=" font-weight-bold w-25 ">Application Code :</td>
								<td class="col-xs-5 "><input type="text"
									name="applicationcode" value="DCR52021A50C20101011" /></td>
							</tr>
							<tr>
								<td class=" font-weight-bold">Fee Code :</td>
								<td class="col-xs-5 "><input type="text" name="feecode"
									value="1" /></td>
							</tr>
							<tr>
								<td class=" font-weight-bold">Module Code :</td>
								<td class="col-xs-5 "><input type="text" name="modulecode"
									value="2" /></td>
							</tr>

							<tr>
								<td class=" font-weight-bold">Fee Amount :</td>
								<td class="col-xs-5 "><input type="text" name="feeamount"
									value="0" /></td>

							</tr>
							<tr>
								<td class=" font-weight-bold">User Code :</td>
								<td class="col-xs-5 "><input type="text" name="usercode"
									value="3" /></td>

							</tr>
							<tr>
								<td class=" font-weight-bold">To Process Code :</td>
								<td class="col-xs-5 "><input type="text"
									name="toprocesscode" value="4" /></td>

							</tr>
						</table>
 

					<input type="submit" class="" value="Submit" />
 
		</form>

	</div>

</body>
</html>