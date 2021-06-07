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
						<button class="btn btn-outline-primary" ng-click="save()">Save</button>
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