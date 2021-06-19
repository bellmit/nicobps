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
	width: 100%;
}

.dropup {
	width: 80%;
	position: relative;
	display: inline-block;
}

.dropup-content {
	display: none;
	position: absolute;
	background-color: #3896d3;
	min-width: 100%;
	bottom: 44px;
	right: 0;
	z-index: 1;
}

.dropup-content a {
	color: white;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
}

.dropup-content a:hover {
	background-color: #3db1ff
}

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
				<!-- <div class="col-md-12 py-2 px-2" style="display: block">
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
				</div> -->
				<div class="col-md-12 py-4">
					<h5 style="border-bottom: 3px solid #005776">License Details</h5>
					<table id="entrytable">
						<tr>
							<td>
								<div class="form-group">
									<label for="licenseetypecode" class="">Licensee Type </span></label> <input
										id="licenseetypecode" type="text"
										ng-value="applicant.licenseetypename" class="form-control"
										readonly="readonly">

								</div>
							</td>
							<td>
								<div class="form-group">
									<label for="firmindividual" class="">Firm/Individual</label> <input
										id="firmindividual" type="text"
										ng-value="applicant.firmindividual=='I'?'Individual':'Firm'"
										class="form-control" readonly="readonly">

								</div>
							</td>
							<td>
								<div class="form-group">
									<label for="firmname" ng-if="signupDetails.firmindividual!='F'">Firm
										Name</label> <input id="firmname" type="text"
										ng-value="applicant.firmname" class="form-control"
										readonly="readonly">
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label for="fullname" class="">Applicant's Name </label> <input
										id="fullname" type="text" ng-value="applicant.applicantsname"
										class="form-control" readonly="readonly">
								</div>
							</td>
							<td>
								<div class="form-group">
									<label for="gender" class="">Gender</label> <input id="gender"
										type="text" ng-value="applicant.gender=='M'?'Male':'Female'"
										class="form-control" readonly="readonly">
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label for="mobileno" class="">Mobile No.</label> <input
										id="mobileno" type="text" ng-value="applicant.mobileno"
										class="form-control" readonly="readonly">
								</div>
							</td>
							<td>
								<div class="form-group">
									<label for="email" class="">Email </label> 
									<input id="email" type="text" ng-value="applicant.email" class="form-control" readonly="readonly">
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="col-md-12 py-12 px-12">
					<h5 style="border-bottom: 3px solid #005776">Address</h5>
					<table id="entrytable">
						<tr>
							<td>
								<div class="form-group">
									<label for="preaddressline1" class="">Address Line - 1</label>
									<input id="preaddressline1" type="text" ng-value="applicant.preaddressline1" class="form-control" readonly="readonly">
								</div>
							</td>
							<td>
								<div class="form-group">
									<label for="preaddressline2" class="">Address Line - 2</label>
									<input id="preaddressline2" type="text" ng-value="applicant.preaddressline2" class="form-control" readonly="readonly">
								</div>
							</td>
							<td>
								<div class="form-group">
									<label for="previllagetown" class="">Village/Town</label> 
									<input id="preaddressline1" type="text" ng-value="applicant.previllagetown" class="form-control" readonly="readonly">
								</div> 
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label for="prestatecode" class="">State</label> 
									<input id="prestatecode" type="text" ng-value="applicant.statename" class="form-control" readonly="readonly">
								</div> 
							</td>
							<td>
								<div class="form-group">
									<label for="predistrictcode" class="">District </label> 
									<input id="predistrictcode" type="text" ng-value="applicant.districtname" class="form-control" readonly="readonly">
								</div>
							</td>
							<td>
								<div class="form-group">
									<label for="prepincode" class="">Pin Code</label> 
									<input id="prepincode" type="text" ng-value="applicant.prepincodex" class="form-control" readonly="readonly">
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="col-md-12 py-12 px-12" style="display: block">
					<h5 style="border-bottom: 3px solid #005776">Documents<span style='font-size: 15px'>(click on document to view)</span></h5>
				</div>
				<div class="col-md-4 py-2 px-2 h-50" style="display: block">
					<table class="table" id="enclosures">
						<tr ng-repeat="item in Enclosures">
							<td><span style="cursor: pointer"
								ng-click="getEnclosure(applicant.usercode,item.key)">{{item.value}}</span>
							</td>
						</tr>
					</table>
					<div style="position: absolute; bottom: 0"
						class="text-center w-100">
						<div class="dropup"
							ng-show="applicant.processcode==4||applicant.processcode==6">
							<button class="dropbtn">
								Action<span class="fa fa-caret-up"
									style="padding-top: 5px; padding-bottom: 5px; float: right;"></span>
							</button>
							<div class="dropup-content">
								<a href="#" ng-repeat="p in nextProcesses"
									ng-click="showRemarks(p)">{{p.flowname}}</a>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-8 py-2 px-2" id="enclosureWindow"></div>
			</div>
		</div>
	</div>
	</div>
	<div style="display: none">
		<div id="remarksTable"
			style="width: 100%; height: 40vh; display: flex; align-items: center">
			<table class="table">
				<tr>
					<td>Remarks</td>
					<td><textarea ng-model="process.remarks"
							style="width: 300px; height: 150px;"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><button class="btn btn-primary"
							ng-click="updateStakeholder(applicant,process)">Submit</button></td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script src="resources/js/application/stakeholder/srverify.js"></script>
</html>