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

				<form class="ng-scope" id="paymentForm" name="paymentForm"
					autocomplete="off" action="./paymentinitialized.htm" method="post">
					<script src="resources/js/application/payment/payment.js"></script>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<div class="row">
						<div class="col-md-8">
							<div class=" bg-light text-black  m-4 p-3 border rounded"
								style="width: 100%;">

								<h3>PAYMENT RESPONSE PAGE</h3>
							</div>
							
							
							
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>

</html>