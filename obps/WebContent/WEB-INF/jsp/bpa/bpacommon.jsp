<!DOCTYPE html>
<html>
<head>
<title>OBPS</title>
<%@include file="../common/headerfiles.jsp"%>
<link href="resources/css/bpa-style.css" rel="stylesheet" />
<style type="text/css">
.displayRecordsTable {
	cursor: pointer;
	content: "";
}

.ajax-loading {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	/*padding-top: 20%;  Location of the box*/
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: scroll; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

.ajax-loading img {
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	margin: auto;
}
</style>

</head>
<body ng-app="CommonApp" ng-controller="CommonCtrl"
	ng-init="init('${page}','${processcode}','${applicationcode}')">
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4"
					style="font-size: 32px; border-bottom: 3px solid #005776">
					<ng-element ng-if="page == 'bpainbox' "> Applications</ng-element>
					<ng-element ng-if="page == 'bpastatus' "> Application Status</ng-element>
					<ng-element ng-if="page == 'buildingpermit' "> Applications</ng-element>
					<ng-element ng-if="page == 'bpasearch' "> Search Applications</ng-element>
				</h3>



				<div class="card" ng-show="search">
					<div class="card-body">
						<form >
							<div class="input-group" style="padding: 2rem 5rem">
								<input type="text"
									class="form-control custom-form-control text-center"
									id="searchParam"
									placeholder="Search by: applicationcode, edcrnumber, permitnumber or owner's name"
									ng-model="searchParam"> <span
									class="input-group-addon login-input-group-addon search">
									<i class="fa fa-search" title="Search"
									ng-click="searchApplication()"></i> <input type="button"
									class="fa fa-search" ng-click="searchApplication()"
									style="display: none">
								</span>
							</div>
						</form>
						<div class="text-center">
							<span class="text-danger"
								ng-if="errorMsg != null && errorMsg != '' && errorMsg.length  > 0">
								{{errorMsg}} </span>
						</div>

					</div>
				</div>
				<br /> <br />
				<div class="table-responsive" id="displayRecordsDiv">
					<div id="displayRecords"
						style='width: 100%; margin: 0px auto 50px auto; padding: 0px 20px' ng-class=" page == 'bpainbox' || page == 'buildingpermit' ? 'displayRecordsTable'  : ''"></div>
				</div>

			</div>
		</div>
	</div>
</body>

<script src="resources/js/util/ngdirectives.js" type="text/javascript"></script>
<script src="resources/js/application/models/bpa.js"
	type="text/javascript"></script>
<script src="resources/js/commons/bpaService.js" type="text/javascript"></script>
<script src="resources/js/application/bpa/bpacommon.js"
	type="text/javascript"></script>

	
</html>