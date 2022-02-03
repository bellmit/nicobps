<html>
<head>
<title>OBPS</title>
<%@include file="common/headerfiles.jsp"%>
<link
	href="resources/js/vendor/bootstrap/vendor/jquery-ui/jquery-ui.css"
	rel="stylesheet" />
<style>
.card-header:hover {
	cursor: pointer;
}
</style>
</head>
<body ng-app="dayendstat" ng-controller="dayendstat">
	<div class="d-flex" id="wrapper">
		<%@include file="common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4"
					style="font-size: 32px; border-bottom: 3px solid #005776">Day
					End Cash Statement</h3>
				<form class="" id="form1">
					<div class="card">
						<div class="card-body">
							<div class="row">
								<div class="col">
									<div class="form-group">
										<label class="col-sm-12">From Date :-<span
											class="fa fa-asterisk"></span></label>
										<div class="col-sm-8 input-group ">
											<input type="text" class="form-control custom-form-control"
												id="fromentrydate" name="fromentrydate" autocomplete="off"
												required />
											<div class="inline-block input-group-append">
												<span class="input-group-text bg-white"><i
													class="fa fa-calendar" aria-hidden="true"></i></span>
											</div>
										</div>
										<div id="fromDate" class="alert">
											<span class="closebtn">&times;</span>
											<strong></strong> From Date is Required !
										</div>
									</div>
								</div>
								<div class="col">
									<div class="form-group">
										<label class="col">To Date :-<span
											class="fa fa-asterisk"></span></label>
										<div class="col-sm-8 input-group">
											<input type="text"
												class="col-sm-10 form-control custom-form-control"
												type="text" id="toentrydate" name="toentrydate"
												autocomplete="off" required>
											<div class="input-group-append">
												<span class="input-group-text bg-white"><i
													class="fa fa-calendar" aria-hidden="true"></i></span>
											</div>
											</input>
										</div>
										<div id="toDate" class="alert">
											<span class="closebtn">&times;</span>
											<strong></strong> To Date is Required !
										</div>
									</div>
								</div>
							</div>
							
							<div>
								<input type="button" style="padding: .1em; margin-left: 35%"
										value="Generate Statement" id="generate"
										 value="Print"
										class=" btn btn-primary" />
								<core:forEach items="${Listofficecode}" var="office" 
									varStatus="loop"> 
										<input type="text" style="display:none"
											value="${office.key}" id="officecode"
											class=" btn btn-primary" />  
								</core:forEach> 
										
							</div>
						</div>
					</div>

				</form>
				<form method="post" action="./dayendStat.htm" id="dayendStat">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</div>
		</div>
	</div>
	</div>
	</div>

</body>

<script src="resources/js/application/dayendstatment.js"></script>
</html>