<html>
<head>
<title>OBPS</title>
<%@include file="../common/headerfiles.jsp"%>
<link
	href="resources/js/vendor/bootstrap/vendor/jquery-ui/jquery-ui.css"
	rel="stylesheet" />
<style>
.card-header:hover {
	cursor: pointer;
}
</style>
</head>
<body ng-app="PermitApp" ng-controller="permitCtrl"
	ng-init="init(applicationcode=('${applicationcode}'))">
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4"
					style="font-size: 32px; border-bottom: 3px solid #005776">Print
					Permits</h3>
				<div class="row">
					<div class="col-md-12">
						<form class="" id="form1">
							<!--  Search Criteria -->
							<div class="card  bg-light">
								<div class="card-header bg-dark text-white ">Search
									Criteria</div>
								<div class="card-body">
									<div id="accordion">
										<!--start of card 1-->
										<div class="card ">

											<div class="card-header bg-info text-white"
												data-toggle="collapse" data-target="#byappcode">Search
												By Application Code</div>
											<div id="byappcode" class="collapse" data-parent="#accordion">

												<div class="card-body ">

													<div class="form-group row ">
														<label class="col-2 control-label">Application
															Code : </label>
														<div class="col-4 ">
															<input type="text" id="applicationcode"
																name="applicationcode" ng-model="applicationcode"
																class="form-control text-uppercase" maxlength="20" />
														</div>
														<div class="col-4 ">
															<input type="button" id="searchbyapplicationcode"
																name="searchbyapplicationcode" value="SEARCH"
																class="btn btn-primary" ng-disabled="!applicationcode"
																ng-click="getpermitlist('byappcode')" />
														</div>
													</div>

												</div>

											</div>
										</div>

										<!--end of card 1-->
										<br />
										<!--start of card 5-->
										<div class="card">

											<div class="card-header bg-info text-white"
												data-toggle="collapse" data-target="#bypermitno">Search
												By Permit Number</div>
											<div id="bypermitno" class="collapse"
												data-parent="#accordion">

												<div class="card-body ">

													<div class="form-group row ">
														<label class="col-2 control-label">Permit Number :
														</label>
														<div class="col-4 ">
															<input type="text" id="permitnumber" name="permitnumber"
																ng-model="permitnumber"
																class="form-control text-uppercase" maxlength="30" />
														</div>
														<div class="col-4 ">
															<input type="button" id="searchbypermitnumber"
																name="searchbypermitnumber" value="SEARCH"
																class="btn btn-primary" ng-disabled="!permitnumber"
																ng-click="getpermitlist('bypermitno')" />
														</div>
													</div>

												</div>

											</div>
										</div>

										<!--end of card 5-->
										<br />
										<!--start of card 2-->
										<div class="card">

											<div class="card-header bg-info text-white"
												data-toggle="collapse" data-target="#byedcrno">Search
												By EDCR Number</div>
											<div id="byedcrno" class="collapse" data-parent="#accordion">

												<div class="card-body ">

													<div class="form-group row ">
														<label class="col-2 control-label">EDCR Number : </label>
														<div class="col-4 ">
															<input type="text" id="edcrnumber" name="edcrnumber"
																ng-model="edcrnumber"
																class="form-control text-uppercase" maxlength="30" />
														</div>
														<div class="col-4 ">
															<input type="button" id="searchbyedcrnumber"
																name="searchbyedcrnumber" value="SEARCH"
																class="btn btn-primary" ng-disabled="!edcrnumber"
																ng-click="getpermitlist('byedcrno')" />
														</div>
													</div>

												</div>

											</div>
										</div>

										<!--end of card 2-->
										<br />
										<!--start of card 3-->
										<div class="card">

											<div class="card-header bg-info text-white"
												data-toggle="collapse" data-target="#byowner">Search
												By Owner Name</div>
											<div id="byowner" class="collapse" data-parent="#accordion">

												<div class="card-body ">

													<div class="form-group row ">
														<label class="col-2 control-label">Owner Name : </label>
														<div class="col-4 ">
															<input type="text" id="ownername" name="ownername"
																ng-model="ownername" class="form-control" maxlength="50" />
														</div>
														<div class="col-4 ">
															<input type="button" id="searchbyownername"
																name="searchbyownername" value="SEARCH"
																class="btn btn-primary" ng-disabled="!ownername"
																ng-click="getpermitlist('byowner')" />
														</div>
													</div>

												</div>

											</div>
										</div>
										<!--end of card 3-->
										<br />
										<!--start of card 4-->
										<div class="card">

											<div class="card-header bg-info  text-white"
												data-toggle="collapse" data-target="#byentrydate">Search
												By Permit Entry Date</div>
											<div id="byentrydate" class="collapse"
												data-parent="#accordion">

												<div class="card-body ">

													<div class="form-group row ">
														<label class="col-1 control-label">From : </label>
														<div class="col-3 input-group date">
															<input type="text" id="fromentrydate"
																name="fromentrydate" ng-model="fromentrydate"
																class="form-control" autocomplete="off" />
															<div class="input-group-append">
																<span class="input-group-text bg-white"><i
																	class="fa fa-calendar" aria-hidden="true"></i></span>

															</div>
														</div>
														<label class="col-1 control-label"> To : </label>
														<div class="col-3 input-group">
															<input type="text" id="toentrydate" name="toentrydate"
																ng-model="toentrydate" class="form-control"
																autocomplete="off" />
															<div class="input-group-append">
																<span class="input-group-text bg-white"><i
																	class="fa fa-calendar" aria-hidden="true"></i></span>

															</div>
														</div>
														<div class="col-4  ">
															<input type="button" id="searchbyentrydate"
																name="searchbyentrydate" value="SEARCH"
																class="btn btn-primary"
																ng-disabled="!(fromentrydate && toentrydate)"
																ng-click="getpermitlist('byentrydate')" />

														</div>
													</div>

												</div>

											</div>
										</div>
										<!--end of card 4-->
										<br />

									</div>

								</div>
							</div>
							<!-- End of search criteria -->
							<br />
							<!-- Search result -->

							<div class="card d-none " id="searchresults">
								<!--start of panel default 1-->
								<div class="card-header bg-dark  text-white">Search Result</div>
								<br/>
								<div class="table-responsive" id="displayRecordsDiv">
									<div id="displayRecords"
										style='width: 100%; margin: 0px auto 50px auto; padding: 0px 20px'></div>
								</div>
							</div>
							<!--End of Search result -->

						</form>
						<form method="post" action="./printPermit.htm" id="printPermit">
							<input type="hidden" readonly name="param_edcrnumber"
								value="{{param_edcrnumber}}" /> <input type="hidden"
								name="${_csrf.parameterName}" value="${_csrf.token}" />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>

<script src="resources/js/application/bpa/printpermit.js"></script>
</html>