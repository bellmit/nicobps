<!DOCTYPE html>
<!-- @author Decent Khongstia -->
<html>
	<head>
		<title>OBPS</title>
		<%@include file="../common/headerfiles.jsp"%>
		<style type="text/css">
			.btn-wrapper{
			    display: flex;
			    align-items: center;
			    justify-content: center;
			    flex-direction: row;
			}
			.custom-form-control {
				background-color: inherit;
				border: none;
				border-bottom: 1px solid rgba(0, 0, 0, 0.150);
				border-radius: 0px;
			}
			
			.custom-form-control[readonly] {
				background-color: inherit;
				opacity: 0.7;
			}
			
			.custom-form-control-borderless {
				border: none;
				border-radius: 0px;
			}
			
			.divider {
				border-bottom: 1px solid rgba(0,0,0,0.125);
			}
		</style>
	</head>
	</script>
	<body ng-app="CommonApp" ng-controller="CommonCtrl">
		<div class="d-flex" id="wrapper">
			<%@include file="../common/menuside.jsp"%>
			<div id="page-content-wrapper">
				<%@include file="../common/menutop.jsp"%>
				<div class="container-fluid">
					<h3 class="mt-4"
						style="font-size: 32px; border-bottom: 3px solid #005776">
						Apply for building permit: {{Fees.feetypedescription}}
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
												<div class="form-group mt-5 row" ng-repeat="F in Fees.calculatedFee">
													<label for="appfee" class="col-sm-4 card-subtitle">{{F.name}}</label>
													<div class="col-sm-8">
														<i class="fa fa-inr" style="font-size: small; font-weight: normal"></i>
														<span class="custom-form-control-borderless">{{F.amount}}</span>
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
	<script src="resources/js/application/bpa/commonfeepayment.js" type="text/javascript"></script>
	<script type="text/javascript">
		const APPCODE = '${applicationcode}';
	</script>
</html>