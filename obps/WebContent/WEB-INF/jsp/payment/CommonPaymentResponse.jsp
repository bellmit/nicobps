<html>
<head>
<title>OBPS</title>
<%@include file="../common/headerfiles.jsp"%>

</head>
<body ng-app="CommonApp" ng-controller="responseController">
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">

				<div class="row">
					<div class="col-md-10 ">


						<div
							class=" col-md-10 bg-light text-black  m-4 p-3 border rounded">
							<div class="text-center p-3 mb-2 " ng-class="getClass(${status})">
								<h4>
									<core:out value="${message}" escapeXml="true" />
								</h4>
							</div>
							<table class=" mt-4 mb-4"
								style="width: 70%; margin: 0px auto; border-spacing: 10px"
								ng-init="init(${transactioncode})">
								<tr>
									<th colspan="2" class="font-weight-bold text-center h5 ">Transaction
										Details</th>
								</tr>
								<tr>
									<td colspan="2" style="border-bottom: 2px solid #005776"></td>
								</tr>
								<tr>
									<td class=" font-weight-bold w-25 ">Reference ID :</td>
									<td class="col-md-5 "><core:out value="${transactioncode}"
											escapeXml="true" /></td>
								</tr>
								<tr>
									<td class=" font-weight-bold w-25 ">Transaction Date :</td>
									<td class="col-md-5 "><core:out value="${transactiondate}"
											escapeXml="true" /></td>
								</tr>
								<tr>
									<td class=" font-weight-bold w-25 ">Process by :</td>
									<td class="col-md-5 "><core:out value="${processby}"
											escapeXml="true" /></td>
								</tr>

								<tr>
									<td class=" font-weight-bold">Amount :</td>
									<td class="col-md-5 ">&#8377; <core:out value="${amount}"
											escapeXml="true" /></td>

								</tr>
								<tr>
									<td colspan="2" style="border-bottom: 2px solid #005776"></td>
								</tr>
								<tr>
									<td colspan="2" ng-show="${amount}!=0 && ${amount} != null && ${amount} !='' "><input type="button" value="Print"
										class="btn btn-primary  col-md-2 " style="float: right;"
										ng-click="print()" /></td>
								</tr>
							</table>
						</div>
					</div>
				</div>

			</div> 
		</div>
	</div>

</body>
<script src="resources/js/application/payment/paymentresponse.js"></script>

</html>