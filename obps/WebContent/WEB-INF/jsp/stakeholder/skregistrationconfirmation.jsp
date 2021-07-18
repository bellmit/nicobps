<html>
<head>
<title>OBPS</title>
<%@include file="../common/headerfiles.jsp"%>
<style>
</style>
</head>
<body ng-app="CommonApp" ng-controller="CommonCtrl">
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4" style="font-size: 32px;">ULB Registration</h3>
				<div class="row">
					<div class="col-md-12 py-4 px-5">
						<div class="card text-white bg-warning mb-3" style="width: 100%;">
							<div class="card-body">
								<h5 class="card-title">Your application has been submitted successfully and forwarded to the concerned office. 
								<br>
								You will be intimated for payment of Registration Fees once your application and documents have been verified. 
								<br>
								The Application Code is <b>${applicationcode}</b></h5>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../common/footer.jsp" %> 
</body>
</html>