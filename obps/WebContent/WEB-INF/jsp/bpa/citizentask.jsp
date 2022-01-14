<html>
	<head>
		<title>OBPS | Citizen Task</title>
		<%@include file="../common/headerfiles.jsp"%>
		<link href="resources/css/bpa-style.css" rel="stylesheet"/>
	</head>
	<body ng-app="CommonApp" ng-controller="CommonCtrl"  ng-init="init('${applicationcode}')">
		<div class="d-flex" id="wrapper">
			<%@include file="../common/menuside.jsp"%>
			<div id="page-content-wrapper">
				<%@include file="../common/menutop.jsp"%>
				<div class="container-fluid">
					<h3 class="mt-4"
						style="font-size: 32px; border-bottom: 3px solid #005776">
						Citizen Task</h3>
					<form id="msform" name="bpaform">
						<div class="card-body">
							<label class="h4">Application details</label> <label
								class="h6 alert alert-dark">Application No. <core:out
									value="${applicationcode}"></core:out></label>
						</div>
						<div class="card mb-4" ng-if="${isactionallowed} == 1"> 
							<div class="card-body btn-outline-primary">
								<div class="card-title row">
									<label class="col-sm-6 h5">Task Status</label>
									<label class="col-sm-6 text-right"></label>
								</div>
								<div  ng-if="taskStatus.rejectdate == null || taskStatus.rejectdate == ''">
									<div class="form-group row">
										<div class="col">
											<label class="col"><small>Date</small></label>
											<label class="col">{{taskStatus.taskdate}}</label>
										</div>
										<div class="col">
											<label class="col"><small>From</small></label>	
											<label class="col">{{taskStatus.processname}}<br>{{' By '+taskStatus.updatedby}}</label>
										</div>
										<div class="col">
											<label class="col"><small>Status</small></label>
											<label class="col">{{taskStatus.status}}</label>
										</div>
									</div>
									<div class="form-group row">
										<div class="col">
											<label class="col"><small>Action</small></label>
											<label class="col">{{taskStatus.remarks}}</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<task-status ng-if="${isactionallowed} != 1">></task-status>
						<basic-details></basic-details>
						<scrutiny-details></scrutiny-details>
						<owner-details></owner-details>
						<document-details></document-details>
						<div class="card mb-4" ng-if="${isactionallowed} == 1">
							<div class="card-body">
								<div class="row">
									<div class="col">
										<div class="form-group">
											<div class="col-sm-12">
												<div class="btn-group dropup mt-5">
													<button type="button"
														class="btn btn-primary btn-lg dropdown-toggle"
														data-toggle="dropdown" style="min-width: 10rem">Action</button>
													<div class="dropdown-menu">
														<return-from-citizen-button></return-from-citizen-button>
														<!-- <button class="dropdown-item btn-outline-primary" type="button" data-toggle="modal" data-target="#commonModal" ng-click="setModalTitle(4)">Send</button> -->
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<modal-action></modal-action>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<%@include file="../common/footer.jsp"%>
	</body>
	<script src="resources/js/util/filevalidation.js" type="text/javascript"></script>
	<script src="resources/js/util/ngdirectives.js" type="text/javascript"></script>
	<script src="resources/js/application/models/bpa.js" type="text/javascript"></script>
	<script src="resources/js/commons/bpaService.js" type="text/javascript"></script>
	<script src="resources/js/application/bpa/directives/common.js" type="text/javascript"></script>
	<script src="resources/js/application/bpa/citizentask.js" type="text/javascript"></script>
	
</html>