<html>
<head>
<title>OBPS</title>
<%@include file="../common/headerfiles.jsp"%>
<style>
	#enclosures td {
		padding-top: 8px;
		padding-bottom: 8px;
	}
	
	#enclosures td:hover {
		background-color: antiquewhite;
	}
	
	.dropbtn {
	  background-color: #3498DB;
	  color: white;
	  padding: 10px 16px;
	  font-size: 16px;
	  border: none;
	  width:100%;
	}
	
	.dropup {
	  width:80%;
	  position: relative;
	  display: inline-block;
	}
	
	.dropup-content {
	  display: none;
	  position: absolute;
	  background-color: #3896d3;
	  min-width: 100%;
	  bottom: 44px;
	  right:0;
	  z-index: 1;
	}
	
	.dropup-content a {
	  color: white;
	  padding: 12px 16px;
	  text-decoration: none;
	  display: block;
	}
	
	.dropup-content a:hover {background-color: #3db1ff}
	
	.dropup:hover .dropup-content {
	  display: block;
	}
	
	.dropup:hover .dropbtn {
	  background-color: #2980B9;
	}
</style>
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
								<th>Licence</th>
								<th>Firm/Individual</th>
								<th>Firm/Applicant Name</th>
								<th>District</th>
								<th>Status</th>
								<th></th>
							</tr>
							<tr ng-repeat='item in Licensees'>
								<td>{{item.licenseetypename}}</td>
								<td>{{item.firmindividual=='I'?'Individual':'Firm'}}</td>
								<td>{{item.firmindividual=='I'?item.applicantsname:item.firmname}}</td>
								<td>{{item.districtname}}</td>
								<td>{{item.nextprocessname}}</td>
								<td><input type="button" value="View Details"
									ng-click="viewDetails(item)" /></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="detailView" style="display: none" class="h-100"
		style="display:block">
		<div class="container-fluid h-100" style="display: block">
			<div class="row h-100" style="display: flex">
				<div class="col-md-4 py-2 px-2 h-100" style="display: block">
					<table class="table">
						<tr>
							<th>Applicant Details</th>
						</tr>
						<tbody>
							<tr>
								<td style="padding-top: 5px; padding-bottom: 5px; border: none;"><span
									style="font-style: italic">Name&nbsp;&nbsp;:&nbsp;</span>{{applicant.firmindividual=='I'?applicant.applicantsname:applicant.firmname}}</td>
							</tr>
							<tr>
								<td style="padding-top: 5px; padding-bottom: 5px; border: none;"><span
									style="font-style: italic">Address&nbsp;&nbsp;:&nbsp;</span>{{applicant.preaddressline1+'
									'+applicant.preaddressline2+','+applicant.pervillagetown}}</td>
							</tr>
							<tr>
								<td style="padding-top: 5px; padding-bottom: 5px; border: none;"><span
									style="font-style: italic">District&nbsp;&nbsp;:&nbsp;</span>{{applicant.districtname}}</td>
							</tr>
							<tr>
								<td style="padding-top: 5px; padding-bottom: 5px; border: none;"><span
									style="font-style: italic">{{applicant.firmindividual=='I'?'Individual':'Firm'}}</span></td>
							</tr>
							<tr>
								<td style="padding-top: 5px; padding-bottom: 5px; border: none;"><span
									style="font-style: italic">Licensee
										type&nbsp;&nbsp;:&nbsp;</span>{{applicant.licenseetypename}}</td>
								</td>
							</tr>
						</tbody>
					</table>
					<table class="table" id="enclosures">
						<tr>
							<th>Documents</th>
						</tr>
						<tr ng-repeat="item in Enclosures">
							<td><span style="cursor: pointer"
								ng-click="getEnclosure(applicant.usercode,item.key)">{{item.value}}</span>
							</td>
						</tr>
					</table>
					<div style="position: absolute; bottom: 0" class="text-center w-100">
						<div class="dropup" ng-show="applicant.processcode==4||applicant.processcode==6">
						  <button class="dropbtn">Action<span class="fa fa-caret-up" style="padding-top:5px;padding-bottom:5px;float:right;"></span></button>
						  <div class="dropup-content">
						    <a href="#" ng-repeat="process in nextProcesses" ng-click="updateStakeholder(applicant,process)">{{process.flowname}}</a>
						  </div>
						</div>
					</div>
				</div>
				<div class="col-md-8 py-2 px-2" id="enclosureWindow"></div>
			</div>
		</div>
	</div>
</body>
<script src="resources/js/application/stakeholder/srverify.js"></script>
</html>