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
				<h3 class="mt-4" style="font-size: 32px; border-bottom: 3px solid #005776">
						{{taskStatus.nextprocessname}}
					</h3>
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
								<div class="card mt-5" ng-hide="Enclosures.length==0">
											<div class="card-header">Documents</div>
											<div class="card-body">
												<div class="form-group row"  ng-repeat="R in SiteEnclosures track by $index">
													<div class="col-sm-7" ng-hide="R.flag === true">
														<select class="form-control custom-form-control"
															name="officelocationcode" ng-model="enclosurecode"
															ng-options="O.enclosurecode as (O.enclosurename) for O in Enclosures| filter: selected === false"
															ng-change="setLabel($index, enclosurecode)"
															required>
															<option selected="selected" disabled="disabled" value="">Select Options</option>
														</select>
														<label style="color:red" ng-if="R.error === true")>{{R.errormsg}}</label>
													</div>
													<label class="col-sm-7" ng-show="R.flag === true">{{($index+1)+":  "+R.name}}<span class="fa fa-asterisk"></span></label>
													<div class="col-sm-5">
														<div class="row mb-5">
															<div class="btn btn-outline-primary btn-sm float-left">
																<input type="file" name="report_{{$index}}" ng-model="R.file" file-model="R.file" ng-change="setLabel($index, null, R.file)" required>
																<span class="col" style="color:red"></span>
																<span class="col" style="color:red" ng-if="bpa.error[$index]">Required</span>
															</div>
															<div class="plus" ng-if="SiteEnclosures.length >= 1 && SiteEnclosures.length == $index+1">
																<i class="fa fa-plus" title="Add more file" ng-click="filterEnclosure(1, enclosurecode)"></i>
															</div>
															<div class="minus" ng-if="SiteEnclosures.length >= 2 && SiteEnclosures.length == $index+1">
																<i class="fa fa-minus" title="Remove file" ng-click="filterEnclosure(2, enclosurecode)"></i>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
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