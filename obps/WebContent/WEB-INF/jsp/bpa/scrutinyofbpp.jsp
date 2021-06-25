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
						Apply for building permit: Scrutiny of BPP</h3>
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
						<site-report-details></site-report-details>
						<file-view-modal></file-view-modal>
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
	<script src="resources/js/application/bpa/scrutinyofbpp.js" type="text/javascript"></script>
	<script type="text/javascript">
		const APPCODE = '${applicationcode}';
	</script>
</html>