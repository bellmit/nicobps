<!DOCTYPE html>
<!-- @author Decent Khongstia -->
<html>
	<head>
		<title>OBPS</title>
		<%@include file="../common/headerfiles.jsp"%>
		<link href="resources/css/bpa-style.css" rel="stylesheet"/>
	</head>
	<body ng-app="CommonApp" ng-controller="CommonCtrl" id="appId" ng-init="init('${edcrnumber}')">
		<div class="d-flex" id="wrapper">
			<%@include file="../common/menuside.jsp"%>
			<div id="page-content-wrapper">
				<%@include file="../common/menutop.jsp"%>
				<div class="container-fluid">
					<h3 class="mt-4" style="font-size: 32px; border-bottom: 3px solid #005776">
						Apply for building permit
					</h3>
					<form id="msform" name="bpaform">
						<div ng-if="${isalreadyapplied} != 1">
							<div ng-include="'bpaform.htm'"></div>
							<!-- GoogleMap Modal -->
							<div class="modal fade" id="gmapModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
							  <div class="modal-dialog modal-lg custom-modal-dialog" role="document">
							    <div class="modal-content custom-modal-content">
							      <div class="modal-body custom-modal-body">
							      		<div ng-include="'googlemap.htm'"></div>
							      </div>
							      <div class="modal-footer">
							        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
							        <button type="button" class="btn btn-primary" id="pickCityBtn" data-dismiss="modal">Pick Your City</button>
							      </div>
							    </div> 
							  </div>
							</div>
							<!-- End: GoogleMap Modal -->
						</div>
                   	</form>
                   	<div class="element-center" ng-if="${isalreadyapplied} == 1">
						Click<a ng-href="bpatrackstatus.htm">&nbsp;here&nbsp;</a> to check status of your application.
					</div>
				</div>
			</div>
		</div>
	</body>
	<script src="resources/js/util/ngdirectives.js" type="text/javascript"></script>
	<script src="resources/js/application/models/bpa.js" type="text/javascript"></script>
	<script src="resources/js/commons/bpaService.js" type="text/javascript"></script>
	<script src="resources/js/application/bpa/apply.js" type="text/javascript"></script>

</html>