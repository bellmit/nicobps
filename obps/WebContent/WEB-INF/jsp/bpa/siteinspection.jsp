<!DOCTYPE html>
<!-- @author Decent Khongstia -->
<html>
	<head>
		<title>OBPS</title>
		<%@include file="../common/headerfiles.jsp"%>
		<link href="resources/css/bpa-style.css" rel="stylesheet"/>
		<style type="text/css">
			.textarea{
				width: 80%;
				min-width: 80%;
				max-width: 100%;
				min-height: 40px;
				max-height: 60px;
			}
		</style>
	</head>
	<body ng-app="CommonApp" ng-controller="CommonCtrl">
		<div class="d-flex" id="wrapper">
			<%@include file="../common/menuside.jsp"%>
			<div id="page-content-wrapper">
				<%@include file="../common/menutop.jsp"%>
				<div class="container-fluid">
					<h3 class="mt-4" style="font-size: 32px; border-bottom: 3px solid #005776">
						{{taskStatus.nextprocessname}}
					</h3>
					<form name="bpaform">
						<div class="card-body">
							<label class="h4">Application details</label>
							<label class="h6 alert alert-dark">Application No. <core:out value="${applicationcode}"></core:out></label>
						</div>
						<task-status></task-status>
						<basic-details></basic-details>
						<scrutiny-details></scrutiny-details>
						<owner-details></owner-details>
						<document-details></document-details>
						<file-view-modal></file-view-modal>
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
<!-- 										<div class="form-group row"> -->
<!-- 											<label class="col">Site Inspection Report<span class="fa fa-asterisk"></span></label> -->
<!-- 											<div class="col"> -->
<!-- 												<div class="btn btn-outline-primary btn-sm float-left"> -->
<!-- 													<input type="file" name="report" file-model="bpa.report" required> -->
<!-- 													<span class="col" style="color:red"></span> -->
<!-- 													<span class="col" style="color:red" ng-if="bpa.error.report">{{bpa.error.report}}</span> -->
<!-- 												</div> -->
<!-- 											</div> -->
<!-- 										</div> -->
										<div class="form-group row"ng-repeat="Q in Questionnaires | orderBy: 'Q.questiondescription'">
											<div class="col-sm-7">
												<input type="checkbox" name="response_{{$index}}" ng-model="Q.response"/>
												<span>{{$index+1}}: {{Q.questiondescription}}</span>
											</div>
											<div class="col-sm-5">
												<textarea rows="1" cols="10" name="remarks_{{$index}}" class="form-control textarea" 
                               				       		ng-model="Q.remarks" maxlength="99" autocomplete="off" ng-disabled="!Q.response">
                             				       	 </textarea>
                           				       	 <span class="col" style="color:red" ng-if="bpaform.response_($index).modelValue">
                           				       	 	Required
                           				       	 </span>
											</div>
										</div>
										<div class="form-group row mt-5">
											<label class="col">Site Inspection Report<span class="fa fa-asterisk"></span></label>
											<div class="col">
												<div class="row mb-5" ng-repeat="R in bpa.reports track by $index">
													<div class="btn btn-outline-primary btn-sm float-left">
														<input type="file" name="report_{{$index}}" file-model="bpa.reports[$index]" required>
														<span class="col" style="color:red"></span>
<!-- 														<span class="col" style="color:red" ng-if="bpa.error[$index].report">{{bpa.error[$index].report}}</span> -->
														<span class="col" style="color:red" ng-if="bpa.error[$index]">Required</span>
													</div>
													<div class="plus" ng-if="bpa.reports.length >= 1 && bpa.reports.length == $index+1">
														<i class="fa fa-plus" title="Add more file" ng-click="addRemoveMoreFile(1)"></i>
													</div>
													<div class="minus" ng-if="bpa.reports.length >= 2 && bpa.reports.length == $index+1">
														<i class="fa fa-minus" title="Remove file" ng-click="addRemoveMoreFile(2)"></i>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<common-processing-action></common-processing-action>
					</form>
				</div>
			</div>
		</div>
	</body>
	<script src="resources/js/util/filevalidation.js" type="text/javascript"></script>
	<script src="resources/js/util/ngdirectives.js" type="text/javascript"></script>
	<script src="resources/js/application/models/bpa.js" type="text/javascript"></script>
	<script src="resources/js/commons/bpaService.js" type="text/javascript"></script>
	<script src="resources/js/application/bpa/directives/common.js" type="text/javascript"></script>
	<script src="resources/js/application/bpa/siteinspection.js" type="text/javascript"></script>
	<script type="text/javascript">
		const APPCODE = '${applicationcode}';
	</script>
</html>