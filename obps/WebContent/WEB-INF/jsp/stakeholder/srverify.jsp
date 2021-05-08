<html>
<head>
<title>OBPS</title>
<%@include file="../common/headerfiles.jsp"%>
</head>
<body ng-app="CommonApp" ng-controller="CommonCtrl">
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4" style="font-size: 32px;">Verification</h3>
				<div class="row">
					<div class="col-md-12 py-4 px-5">
						<table class="table">
							<tr>
								<th>Licences</th>
								<th>Firm/Individual</th>
								<th>Firm/Applicant Name</th>
								<th>District</th>
								<th></th>
							</tr>
							<tr ng-repeat='item in Licensees'>
								<td>{{item.licenseetypename}}</td>
								<td>{{item.firmindividual=='I'?'Individual':'Firm'}}</td>
								<td>{{item.firmindividual=='I'?item.applicantsname:item.firmname}}</td>
								<td>{{item.districtname}}</td>
								<td><input type="button" value="View Details"
									ng-click="viewDetails(item)" /></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="detailView" style="display: none" class="h-100" style="display:block">
		<div class="container-fluid h-100" style="display:block">
			<div class="row h-100" style="display:flex">
				<div class="col-md-4 py-2 px-2 h-100" style="display:block">
					<table class="table">
						<tr>
							<th>Document</th>
						</tr>
						<tr ng-repeat="item in Enclosures"><td><span style="cursor: pointer" 
						ng-click="getEnclosure(applicant.usercode,item.key)">{{item.value}}</span></td></tr>
					</table>
					<div style="position: absolute;bottom:0" class="text-center w-100">
						<input type="button" value="Verify" class="btn btn-info"/>
						<input type="button" value="Approve" class="btn btn-info"/>
					</div>
				</div>
				<div class="col-md-8 py-2 px-2" id="enclosureWindow">
				</div>
			</div>
		</div>
	</div>
</body>
<script src="resources/js/application/stakeholder/srverify.js"></script>
</html>