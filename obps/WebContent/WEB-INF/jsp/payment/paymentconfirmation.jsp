<html>
<head>
<title>OBPS</title>
<%@include file="../common/headerfiles.jsp"%>

</head>
<body ng-app="CommonApp" ng-controller="paymentController">
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4"
					style="font-size: 32px; border-bottom: 3px solid #005776">Payment
					Confirmation</h3>
				<core:choose>
					<core:when test="${feeamount eq '0'}">
						<form class="ng-scope" id="paymentForm" name="paymentForm"
							autocomplete="off" action="./CommonPaymentResponse.htm"
							method="post">
					</core:when>
					<core:otherwise>
						<form class="ng-scope" id="paymentForm" name="paymentForm"
							autocomplete="off" action="./paymentinitialized.htm"
							method="post">
					</core:otherwise>
				</core:choose>

				<script src="resources/js/application/payment/payment.js"></script>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<div class="row">
					<div class="col-md-8">
						<div class=" bg-light text-black  m-4 p-3 border rounded"
							style="width: 100%;">

							<table class=""
								style="width: 70%; margin: 0px auto; border-spacing: 10px"
								ng-init="init(${applicationcode},${feecode},${feeamount})">
								<tr>
									<th colspan="2" class="font-weight-bold text-center h5 ">Payment
										Details</th>
								</tr>
								<tr>
									<td colspan="2" style="border-bottom: 2px solid #005776"></td>
								</tr>
								<tr>
									<td class=" font-weight-bold w-25 ">Application Code</td>
									<td class="col-xs-5 ">:${applicationcode}</td>
								</tr>
								<tr>
									<td class=" font-weight-bold">Fee Code</td>
									<td class="col-xs-5 ">: ${feecode}</td>
								</tr>
								<tr>
									<td class=" font-weight-bold">Fee Type</td>
									<td class="col-xs-5 ">: ${feetypedescription}</td>
								</tr>
								<tr>
									<td class=" font-weight-bold">Fee Amount</td>
									<td class="col-xs-5 ">: {{feeamount | currency:'INR '}}</td>

								</tr>
							</table>
							<input type="hidden" readonly name="applicationcode"
								value="${applicationcode}" /> <input type="hidden" readonly
								name="feecode" value="${feecode}" /> <input type="hidden"
								readonly name="feeamount" value="${feeamount}" /> <input
								type="hidden" readonly name="modulecode" value=" ${modulecode}" />
							<input type="hidden" readonly name="toprocesscode"
								value="${toprocesscode}" /> <input type="hidden" readonly
								name="usercode" value="${usercode}" />
						</div>
						<div class=" m-4 p-3" style="width: 100%;">
							<core:choose>
								<core:when test="${status.appexist eq 'NOTEXIST'}">
									<div class="alert alert-danger text-center  show">
										<strong>Status : </strong>Application does not Exist !!
									</div>

								</core:when>
								<core:otherwise>
									<core:choose>
										<core:when test="${status.feeamount eq 'NOTAVAILABLE'}">
											<div class="alert alert-danger  text-center  show">
												<strong>Status : </strong> Amount not Available !!
											</div>

										</core:when>
										<core:otherwise>
											<core:choose>
												<core:when test="${status.paystatus eq 'PAID'}">
													<div class="alert alert-success  text-center  show">
														<strong>Status : </strong> Fee already Paid !!
													</div>

												</core:when>
												<core:otherwise>
													<input type="button"
														class="offset-5 col-md-2  btn btn-warning" value="PAY"
														ng-click="submitDetails();" />

												</core:otherwise>
											</core:choose>

										</core:otherwise>
									</core:choose>

								</core:otherwise>
							</core:choose>

						</div>




					</div>
				</div>
				</form>
			</div>
		</div>
	</div>

</body>

</html>