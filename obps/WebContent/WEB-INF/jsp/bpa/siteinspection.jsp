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
			hr{
			    font-size: 32px;
			    border: none;
    			border-bottom: 1px solid rgba(0, 87, 118, 0.8);
			}
			.fa-asterisk{
				font-size: 8px;
				color: rgba(220, 53, 70, 1);
				padding-left: 0.125em;
				vertical-align: middle;
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
						Apply for building permit: Site Inspection</h3>
					<form name="bpaform">
						<ng-include src="'processtrackstatus.htm'"></ng-include>
						<ng-include src="'basicdetails.htm'"></ng-include>
						<ng-include src="'scrutinydetails.htm'"></ng-include>
						<ng-include src="'documentdetails.htm'"></ng-include>
						<div class="card mb-4">
							<div class="card-body">
								<div class="card-title h4">Inspection Report</div>
								<div class="row" style="display: none">
									<div class="col">
										<div class="form-group">
											<label class="col-sm-12">Applicationcode</label>
											<div class="col-sm-12">
												<input type="text" name="applicationcode" class="form-control custom-form-control"
													ng-model="bpa.applicationcode" value="" readonly />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col">
										<div class="form-group row">
											<label class="col">Site Inspection Report<span class="fa fa-asterisk"></span></label>
											<div class="col">
												<div class="btn btn-outline-primary btn-sm float-left">
													<input type="file" name="report" file-model="bpa.report" 
														required><!-- ng-model="report"  -->
													<span class="col" style="color:red"></span>
													<span class="col" style="color:red" ng-if="bpa.error.report">{{bpa.error.report}}</span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="card mb-4">
							<div class="card-body">
								<div class="row">
									<div class="col">
										<div class="form-group">
											<div class="col-sm-12">
												<div class="btn-group dropup mt-5">
													<button type="button" class="btn btn-primary btn-lg dropdown-toggle" data-toggle="dropdown" style="min-width: 10rem">Action</button>
													<div class="dropdown-menu">
														<button class="dropdown-item btn-outline-primary" type="button" data-toggle="modal" data-target="#commonModal" ng-click="setModalTitle(1)">Forward</button>
													    <button class="dropdown-item btn-outline-danger" type="button" data-toggle="modal" data-target="#commonModal" ng-click="setModalTitle(2)">Reject</button>
													</div>
												</div>
											</div>
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
								<div ng-include="'modal.htm'"></div>
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
	<script src="resources/js/application/bpa/siteinspection.js" type="text/javascript"></script>
	<script type="text/javascript">
		const APPCODE = '${applicationcode}';
	</script>
</html>