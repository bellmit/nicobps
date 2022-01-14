<!DOCTYPE html>
<!-- @author Decent Khongstia -->
<html>
	<head>
		<title>OBPS</title>
		<%@include file="../common/headerfiles.jsp"%>
		<link href="resources/css/bpa-style.css" rel="stylesheet"/>
	</head>
	<body ng-app="CommonApp" ng-controller="CommonCtrl"   ng-init="init('${applicationcode}')">
		<div class="d-flex" id="wrapper">
			<%@include file="../common/menuside.jsp"%>
			<div id="page-content-wrapper">
				<%@include file="../common/menutop.jsp"%>
				<div class="container-fluid">
					<h3 class="mt-4" style="font-size: 32px; border-bottom: 3px solid #005776">
						Pay Application Fee
					</h3>
					<form id="form">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
						<input type="hidden" name="applicationcode" ng-model="bpa.applicationcode" readonly />
						<input type="hidden" name="officecode" ng-model="bpa.officecode" readonly>
						<input type="hidden" name="feecode" ng-model="bpa.feecode" readonly>
						<input type="hidden" name="feeamount" ng-model="bpa.amount" readonly>
						<div class="card">
							<div class="card-body">
								<div class="row">
									<div class="col">
										<div class="form-group">
											<label class="col-sm-12 card-subtitle">Applicationcode</label>
											<div class="col-sm-12">
												<input type="text" name="applicationcode"
													class="form-control custom-form-control"
													ng-model="bpa.applicationcode" value="" readonly />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col">
										<div class="card">
											<div class="card-body">
												<h5 class="card-title">Fee Estimate</h5>
												<div class="form-group mt-5 row">
													<label for="appfee" class="col-sm-4 card-subtitle">Application
														Fee</label>
													<div class="col-sm-8">
														<i class="fa fa-inr"
															style="font-size: small; font-weight: normal"></i> <input
															type="number" class="custom-form-control-borderless"
															ng-model="bpa.amount" readonly><br />
													</div>
												</div>
												<div class="form-group">
													<div class="col-sm-5 divider"></div>
												</div>
												<div class="form-group row">
													<label for="appfee" class="col-sm-4 sfont-weight-bold">Total
														Amount</label>
													<div class="col-sm-8">
														<i class="fa fa-inr"
															style="font-size: small; font-weight: normal"></i> <input
															type="number" class="custom-form-control-borderless"
															ng-model="bpa.amount" readonly><br />
													</div>
												</div>
	
											</div>
										</div>
									</div>
								</div>
								<div class="row mt-5">
									<div class="col">
										<div class="form-group">
											<label class="col-sm-12 mb-3"></label>
											<div class="col-sm-12">
												<div class="btn-group dropup mt-5">
													<button type="button"
														class="btn btn-primary btn-lg dropdown-toggle"
														data-toggle="dropdown" style="min-width: 10rem">Action</button>
													<div class="dropdown-menu">
														<button class="dropdown-item btn-outline-primary"
															type="submit" ng-click="pay()"
															ng-disabled="bpaform.$invalid">Pay</button>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col" ng-if="serverMsg">
										<span class="alert alert-danger" style="display: block"
											ng-if="serverResponseError">{{serverMsg}}</span> <span
											class="alert alert-danger" style="display: block"
											ng-if="serverResponseFail">{{serverMsg}}</span> <span
											class="alert alert-info" style="display: block"
											ng-if="serverResponseInfo">{{serverMsg}}</span> <span
											class="alert alert-success" style="display: block"
											ng-if="serverResponseSuccess">{{serverMsg}}</span>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
	<script src="resources/js/util/filevalidation.js" type="text/javascript"></script>
	<script src="resources/js/util/ngdirectives.js" type="text/javascript"></script>
	<script src="resources/js/application/models/bpa.js" type="text/javascript"></script>
	<script src="resources/js/commons/bpaService.js" type="text/javascript"></script>
	<script src="resources/js/application/bpa/payappfee.js" type="text/javascript"></script>

</html>