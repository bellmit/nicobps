<!DOCTYPE html>
<!-- @author Decent Khongstia -->
<html>
	<head>
	<title>OBPS</title>
	<%@include file="../common/headerfiles.jsp"%>
	<link href="resources/css/bpa-style.css" rel="stylesheet" />
	<style type="text/css">
	.line-through {
		text-decoration: line-through;
	}
	.textarea{
		width: 90%;
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
					<form name="bpaform">
						<div class="card-body">
							<label class="h4">Application details</label> <label
								class="h6 alert alert-dark">Application No. 
								<core:out value="${applicationcode}"/>
							</label>
						</div>
						<task-status></task-status>
						<basic-details></basic-details>
						<scrutiny-details></scrutiny-details>
						<owner-details></owner-details>
						<document-details></document-details>
						<site-inspection-details></site-inspection-details>
						<file-view-modal></file-view-modal>
						<div class="card mb-4">
							<div class="card-body">
								<div class="card-title h4">Conditions</div>
								<div class="row" style="display: none">
									<div class="col">
										<div class="form-group">
											<label class="col-sm-12">Applicationcode</label>
											<div class="col-sm-12">
												<input type="text" name="applicationcode"
													class="form-control custom-form-control"
													ng-model="bpa.applicationcode" value="" readonly />
											</div>
										</div>
									</div>
								</div>
								<div class="">
									<div class="form-group row"
										ng-repeat="C in Conditions | orderBy: 'conditioncode'">
										<div class="col-sm-1" style="text-align: center">
											<input type="checkbox" name="response_{{$index}}"
												ng-model="C.checked" />
										</div>
										<div class="col-sm-9">
											<div>
												<span style="vertical-align: top">{{$index+1}}:</span> 
												<span
													ng-if="C.conditioncode != null && C.conditiondescription != '' && C.conditioncode < 999"
													ng-class="(C.checked)?'':'line-through'">
													{{C.conditiondescription}} 
												</span>
												<textarea
													ng-if="C.conditiondescription == null || C.conditiondescription == '' || C.conditioncode > 999"
													ng-model="C.conditiondescription" 
													class="textarea" placeholder="Enter your conditions here">
												</textarea>
											</div>
										</div>
										<div class="col-sm-2 row">
											<div class="plus" style="margin-top: 1rem" 
												ng-if="$index+1 == Conditions.length">
												<i class="fa fa-plus" title="Add more condition"
													ng-click="addRemoveMoreConditions(1)"></i>
											</div>
											<div class="minus" style="margin-top: 1rem"
												ng-if="$index+2 > Conditions.length && C.conditioncode > 999">
												<i class="fa fa-minus" title="Remove condition"
													ng-click="addRemoveMoreConditions(2)"></i>
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
	<script src="resources/js/util/ngdirectives.js" type="text/javascript"></script>
	<script src="resources/js/application/models/bpa.js" type="text/javascript"></script>
	<script src="resources/js/commons/bpaService.js" type="text/javascript"></script>
	<script src="resources/js/application/bpa/directives/common.js" type="text/javascript"></script>
	<script src="resources/js/application/bpa/administrativeapproval.js" type="text/javascript"></script>
	<script type="text/javascript">
		const APPCODE = '${applicationcode}';
	</script>
</html>