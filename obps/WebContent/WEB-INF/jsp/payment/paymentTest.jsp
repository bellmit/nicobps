<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title></title>
</head>
<body>
	<h3>Payment Test Post Page</h3>
	<form  name="paymentTestForm"autocomplete="off" action="./paymentconfirmation.htm" method="post">
				
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<div class="row">
						<div class="col-md-8">
							<div class=" bg-light text-black  m-4 p-3 border rounded"
								style="width: 100%;">

								<table>
									 
									<tr>
										<td colspan="2" style="border-bottom: 2px solid #005776"></td>
									</tr>
									<tr>
										<td class=" font-weight-bold w-25 ">Application Code :</td>
										<td class="col-xs-5 "><input type="text" name="applicationcode" value="1391" /></td>
									</tr>
									<tr>
										<td class=" font-weight-bold">Fee Code :</td>
										<td class="col-xs-5 "><input type="text" name="feecode" value="1" /></td>
									</tr>

									<tr>
										<td class=" font-weight-bold">Fee Amount :</td>
										<td class="col-xs-5 "><input type="text" name="feeamount" value="100"  /></td>

									</tr>
								</table>

							</div>

							<input type="submit" class="" value="PAY"  />


						</div>
					</div>
				</form>
</body>
</html>