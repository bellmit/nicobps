<!DOCTYPE html>
<!-- @author Decent Khongstia -->
<html>
	<head>
		<title>OBPS</title>
		<%@include file="../common/headerfiles.jsp"%>
		<style type="text/css">
			#displayRecordsTable{
				cursor: pointer;
				content: "";
			}
		</style>
		<link href="resources/css/bpa-style.css" rel="stylesheet"/>
	</head>
	<body ng-app="CommonApp" ng-controller="CommonCtrl">
		<div class="d-flex" id="wrapper">
			<%@include file="../common/menuside.jsp"%>
			<div id="page-content-wrapper">
				<%@include file="../common/menutop.jsp"%>
				<div class="container-fluid">
					<h3 class="mt-4" style="font-size: 32px; border-bottom: 3px solid #005776">
						Apply for building permit: Stepper 2 
					</h3>
					<form>
						<div class="element-center" ng-if="${isactionallowed} == 1">
							<button class="btn btn-outline-primary" ng-click="save()">Next</button>
						</div>
						<div class="element-center" ng-if="${isactionallowed} != 1">
							Click<a ng-href="bpatrackstatus.htm">&nbsp;here&nbsp;</a> to check status of your application.
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
	<script src="resources/js/util/ngdirectives.js" type="text/javascript"></script>
	<script src="resources/js/application/models/bpa.js" type="text/javascript"></script>
	<script src="resources/js/commons/bpaService.js" type="text/javascript"></script>
	<script src="resources/js/application/bpa/buildingpermitsteptwo.js" type="text/javascript"></script>
	<script type="text/javascript">
		const APPCODE = '${applicationcode}';
	</script>
</html>