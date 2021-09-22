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

#displayRecords tbody tr:hover {
	background-color: #dee2e6;
	cursor: pointer;
}

</style>
</head>
<body ng-app="CommonApp" ng-controller="CommonCtrl">
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<span id="pcode" style="display:block">${processcode}</span>
				<h3 class="mt-4" style="font-size: 32px;">Verifications/Approvals of Stakeholder Registrations</h3>
				<div class="row">
					<div class="col-md-12 py-4 px-5" id='displayRecords'>
						<!-- <table class="table" style='border-bottom:'>
							<thead>
								<tr>
									<th>Application No.</th>
									<th>Licence</th>
									<th>Office</th>
									<th>Firm/Individual</th>
									<th>Firm/Applicant Name</th>
									<th>District</th>
									<th>Status</th>
									<th></th>
								</tr>
							</thead>
							<tbody id='content'>
								<tr ng-repeat='item in Licensees' ng-click="viewDetails(item)">
									<td>{{item.applicationcode}}</td>
									<td>{{item.licenseetypename}}</td>
									<td>{{item.officename1}}</td>
									<td>{{item.firmindividual=='I'?'Individual':'Firm'}}</td>
									<td>{{item.firmindividual=='I'?item.applicantsname:item.firmname}}</td>
									<td>{{item.districtname}}</td>
									<td>{{item.nextprocessname}}</td>
									<td><input type="button" value="View Details"
									ng-click="viewDetails(item)" /></td>
								</tr>
							</tbody>
							<tfoot>
								<tr>
									<td colspan='7'></td>
								</tr>
							</tfoot>
						</table> -->

					</div>

				</div>
			</div>
		</div>
	</div>
	<div id="detailView" style="display: none" class="h-100"
		style="display:block">
		<div class="container-fluid h-100" style="display: block">
			<div class="row h-100" style="display: flex">
				<div class="col-md-12 py-4">
					<h5 style="border-bottom: 3px solid #005776">License Details</h5>
					<table id="entrytable">
						<tr>
							<td>
								<div class="form-group">
									<label for="applicationcode" class="">Application No. </span></label>
									<input id="applicationcode" type="text"
										ng-value="applicant.applicationcode" class="form-control"
										readonly="readonly">
								</div>
							</td>
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
									<label for="officecode" class="">Office </span></label> <input
										id="officecode" type="text" ng-value="applicant.officename1"
										class="form-control" readonly="readonly">

								</div>
							</td>
						</tr>
						<tr>
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
							<td>
								<div class="form-group">
									<label for="fullname" class="">Applicant's Name </label> <input
										id="fullname" type="text" ng-value="applicant.applicantsname"
										class="form-control" readonly="readonly">
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label for="gender" class="">Gender</label> <input id="gender"
										type="text" ng-value="applicant.gender=='M'?'Male':'Female'"
										class="form-control" readonly="readonly">
								</div>
							</td>
							<td>
								<div class="form-group">
									<label for="mobileno" class="">Mobile No.</label> <input
										id="mobileno" type="text" ng-value="applicant.mobileno"
										class="form-control" readonly="readonly">
								</div>
							</td>
							<td>
								<div class="form-group">
									<label for="email" class="">Email </label> <input id="email"
										type="text" ng-value="applicant.email" class="form-control"
										readonly="readonly">
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
									<input id="preaddressline1" type="text"
										ng-value="applicant.preaddressline1" class="form-control"
										readonly="readonly">
								</div>
							</td>
							<td>
								<div class="form-group">
									<label for="preaddressline2" class="">Address Line - 2</label>
									<input id="preaddressline2" type="text"
										ng-value="applicant.preaddressline2" class="form-control"
										readonly="readonly">
								</div>
							</td>
							<td>
								<div class="form-group">
									<label for="previllagetown" class="">Village/Town</label> <input
										id="preaddressline1" type="text"
										ng-value="applicant.previllagetown" class="form-control"
										readonly="readonly">
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label for="prestatecode" class="">State</label> <input
										id="prestatecode" type="text" ng-value="applicant.statename"
										class="form-control" readonly="readonly">
								</div>
							</td>
							<td>
								<div class="form-group">
									<label for="predistrictcode" class="">District </label> <input
										id="predistrictcode" type="text"
										ng-value="applicant.districtname" class="form-control"
										readonly="readonly">
								</div>
							</td>
							<td>
								<div class="form-group">
									<label for="prepincode" class="">Pin Code</label> <input
										id="prepincode" type="text" ng-value="applicant.prepincode"
										class="form-control" readonly="readonly">
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="col-md-12 py-12 px-12" style="display: block">
					<h5 style="border-bottom: 3px solid #005776">
						Documents<span style='font-size: 15px'>(click on document
							to view)</span>
					</h5>
				</div>
				<div class="col-md-4 py-2 px-2 h-50" style="display: block">
					<table class="table mb-5" id="enclosures">
						<tr ng-repeat="item in applicant.enclosures">
							<td><span style="cursor: pointer"
								ng-click="getEnclosure(applicant.usercode,item.enclosurecode)">{{item.enclosurename}}</span>
							</td>
						</tr>
					</table>
					<div style="position: absolute; bottom: 0"
						class="text-center w-100 row">
						<div class="dropup col-md-6"
							ng-show="(applicant.transactions!=null && applicant.transactions.length !=0)">
							<button class="dropbtn">
								Print<span class="fa fa-caret-up"
									style="padding-top: 5px; padding-bottom: 5px; float: right;"></span>
							</button>
							<div class="dropup-content">
							
								<a href="./Report?status=4&applicationcode={{applicant.applicationcode}}" target="_blank">Application</a>
								<a href="./Report?status=1&transactioncode={{fee.transactioncode}}" target="_blank"
									ng-repeat="fee in applicant.transactions">{{fee.feetypedescription}}</a>
							</div>
						</div>
						<div class="dropup col-md-6"
							ng-show="applicant.processcode==4||applicant.processcode==6">
							<button class="dropbtn">
								Action<span class="fa fa-caret-up"
									style="padding-top: 5px; padding-bottom: 5px; float: right;"></span>
							</button>
							<div class="dropup-content">
								<a href="#" ng-repeat="p in nextProcesses"
									ng-click="showRemarks(p)">{{p.action||p.flowname}}</a>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-8 py-2 px-2" id="enclosureWindow"></div>
			</div>
		</div>
	</div>
	<div style="display: none">
		<div id="remarksTable"
			style="width: 100%; height: 50vh; display: flex; align-items: center">
			<table class="table">
				<tr>
					<td>Remarks</td>
					<td><textarea ng-model="process.remarks"
							style="width: 350px; height: 200px;"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><button class="btn btn-primary b-btn" 
							ng-click="updateStakeholder(applicant,process)">Submit</button></td>
				</tr>
			</table>
		</div>
	</div>
	<%@include file="../common/footer.jsp" %> 
</body>
<script src="resources/js/application/stakeholder/srverify.js"></script>
</html>