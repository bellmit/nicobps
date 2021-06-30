<!DOCTYPE html>
<!-- @author Decent Khongstia -->
<html>
	<head>
		<title>OBPS</title>
		<%@include file="../common/headerfiles.jsp"%>
		<link href="resources/css/bpa-style.css" rel="stylesheet"/>
	</head>
	<body ng-app="CommonApp" ng-controller="CommonCtrl">
		<div class="d-flex" id="wrapper">
			<%@include file="../common/menuside.jsp"%>
			<div id="page-content-wrapper">
				<%@include file="../common/menutop.jsp"%>
				<div class="container-fluid">
					<h3 class="mt-4"
						style="font-size: 32px; border-bottom: 3px solid #005776">
						{{Fees.feetypedescription}}
					</h3>
					<form name="bpaform">
						<div class="card">
							<div class="card-body">
								<div class="row">
									<div class="col">
										<div class="form-group">
											<label class="col-sm-12 card-subtitle">Applicationcode</label>
											<div class="col-sm-12">
												<input type="text" name="applicationcode" class="form-control custom-form-control"
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
												<div class="form-group mt-5 row" ng-if="Fees.calculatedFee == null && Fees.calculatedFee.length <= 0">
													<label for="appfee" class="col-sm-4 card-subtitle">{{Fees.feetypedescription}}</label>
													<div class="col-sm-8">
														<i class="fa fa-inr" style="font-size: small; font-weight: normal"></i>
														<input type="number" class="custom-form-control-borderless" ng-model="bpa.amount" readonly><br/>
													</div>
												</div>
												<div class="form-group mt-5" ng-repeat="F in Fees.calculatedFee">
													<div class="col row" ng-if="F.code != FeeType.FormAndScrutinizationFee">
														<label for="appfee" class="col-sm-4 card-subtitle">{{($index+1)+' - '+F.name}}</label>
														<div class="col-sm-8">
															<i class="fa fa-inr" style="font-size: small; font-weight: normal"></i>
															<span class="custom-form-control-borderless">{{F.amount}}</span>
														</div>
													</div>
													<div ng-if="F.code == FeeType.FormAndScrutinizationFee">
														<label for="appfee" class="col-sm-12 card-subtitle mb-3">{{($index+1)+' - '+F.name}}</label>
														<label for="appfee" class="col-sm-12 card-subtitle mb-3">Plot area = {{F.plotArea}} m<sup>2</sup></label>
														<div class="row col-sm-12 card-subtitle mb-2" ng-repeat="D in F.details">
															<label for="appfee" class="col-sm-4 card-subtitle">{{D.name}}</label>
															<div class="col-sm-8">
																<i class="fa fa-inr" style="font-size: small; font-weight: normal"></i>
																<span class="custom-form-control-borderless">{{D.amount}}</span>
															</div>
														</div>
													</div>
												</div>
												<div class="form-group">
													<div class="col-sm-5 divider"></div>
												</div>
												<div class="form-group row">
													<label for="appfee" class="col-sm-4 sfont-weight-bold">Total Amount</label>
													<div class="col-sm-8">
														<i class="fa fa-inr" style="font-size: small; font-weight: normal"></i>
														<span>{{bpa.amount}}</span>
													</div>
												</div>
												
											</div>
										</div>
									</div>
								</div>
								<div class="row mt-2">
									<div class="form-group">
										<div class="col-sm-12">
											<button class="btn btn-outline-primary" type="button" ng-click="pay()" ng-disabled="bpaform.$invalid">Make Payment</button>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col" ng-if="serverMsg">
							   			<span class="alert alert-danger" style="display: block" ng-if="serverResponseError">{{serverMsg}}</span>
							   			<span class="alert alert-danger" style="display: block" ng-if="serverResponseFail">{{serverMsg}}</span>
							   			<span class="alert alert-info" style="display: block" ng-if="serverResponseInfo">{{serverMsg}}</span>
							   			<span class="alert alert-success" style="display: block" ng-if="serverResponseSuccess">{{serverMsg}}</span>
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
	<script src="resources/js/application/bpa/paypermfee.js" type="text/javascript"></script>
	<script type="text/javascript">
		const APPCODE = '${applicationcode}';
	</script>
</html>