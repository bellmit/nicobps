<html>
<head>
<title>OBPS</title>
<%@include file="../common/headerfiles.jsp"%>
<style>
</style>
</head>
<body ng-app="CommonApp" ng-controller="CommonCtrl">
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4"
					style="font-size: 32px; border-bottom: 3px solid #005776">ULB
					Registration</h3>
				<div class="row">
					<core:if test="${errorMsg ne 'REQD_DOCUMENTS_INCOMPLETE'}">
						<div class="col-md-12 py-4 px-5 row">
							<h4>Select Registering Office</h4>
						</div>
						<div class="col-md-12 py-4 px-5 row">
							<div class="col-md-6 px-5">
								<form id="form" method="POST"
									action="ulbregistration.htm?${_csrf.parameterName}=${_csrf.token}">
									<select class="my-2" name='officecode'
										ng-model='registeringofficecode'
										ng-value='registeringofficecode' style="min-width: 100%;"
										ng-change="getOffices(registeringofficecode)">
										<core:forEach items="${registeringoffices}" var="item">
											<option value="${item.officecode}">${item.officename1}</option>
										</core:forEach>
									</select>
								</form>
							</div>
							<div class="col-md-6 px-5">
								<button class="btn btn-primary"
									ng-disabled="registeringofficecode==0"
									ng-click="registerStakeholder()">Register</button>
							</div>
							<span class="mt-4 pl-5" ng-show="fee!==-1">Application
								Fees applicable : <span style="font-style: bold">{{fee.feeamount}}/-</span>
							</span>
						</div>
					</core:if>
					<core:if test="${errorMsg ne null}">
						<span class="mt-4 pl-5" style="color: red">
							${(errorMsg=='ALREADY_REPORTED')?'Office is already registered and valid.':(errorMsg=='REQD_DOCUMENTS_INCOMPLETE')?'Please upload the remaining documents indicated as mandatory before being allowed to empanel with ULBs.':'Please try again.' }
						</span>
					</core:if>
					<div class="col-md-12 pb-4 px-5 mt-1">
						<h5 class="col-md-12" style="border-top: 3px solid #005776"
							ng-show="Offices.length>0">Offices falling under this
							Registering office:</h5>
						<div>
							<ol>
								<li ng-repeat="i in Offices">{{i.officename1}}</li>
							</ol>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
<script src="resources/js/application/stakeholder/ulbregistration.js"></script>
</html>