<html>
<head>
<title>OBPS</title>
<%@include file="../common/headerfiles.jsp"%>
<link
	href="resources/js/vendor/bootstrap/vendor/jquery-ui/jquery-ui.css"
	rel="stylesheet" />
<style>
.icons i {
	position: absolute;
}

.fas {
	padding: 15px;
}

input {
	padding: 10px;
	text-align: center;
	margin-left: 10px;
}
</style>
</head>
<body ng-app="CommonApp" ng-controller="extendvalidityCtrl"
	id="extendvalidityForm">
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4"
					style="font-size: 32px; border-bottom: 3px solid #005776">Extend
					Stakeholder Registration</h3>
				<div class="row">
					<div class="col-md-12 py-4 px-5 row">
						<div class="col-md-6  px-5">
							<h4>Select Office</h4>
							<select ng-model='officecode' style="min-width: 350px;"
								ng-change="listStakeholders()">
								<option value="">--Select--</option>
								<core:forEach items="${offices}" var="item">

									<option value="${item.key}">${item.value}</option>
								</core:forEach>
							</select> <span id="officecodeMsg" class="formErrorContent"></span>
						</div>

					</div>

				</div>
				<div class="row">
					<div class="col-md-12 py-4 px-5 row">
						<div class="col-md-6  px-5">
							<h4>Select Stakeholder</h4>
							<select ng-model='usercode' id='usercode'
								style="min-width: 350px;"
								ng-options="T.key as T.value for T in stakeholdersList"
								ng-change="getValidity(usercode)">
								<option value="">--Select--</option>

							</select>

						</div>
						<span id="usercodeMsg" class="formErrorContent"></span>
					</div>

				</div>

				<div style="display: none">
					<div id="dateTable"
						style="width: 100%; height: 20vh; display: flex; align-items: center">
						<table class="table">

							<tr>
								<td><label> Extend To : </label></td>
								<td><span id="extendedtoMsg" class="formErrorContent"></span>
									<div class="col-sm-12 icons">
										<i class="fas fa-calendar calendar"></i><input type="text"
											id="extendedto" name="extendedto" ng-model="extendedto"
											class="form-control" autocomplete="off" />
									</div>
									
								</td>
								<td colspan="2" align="center"><button
										class="btn btn-primary"
										ng-click="updateStakeholderValidity()">Submit</button></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="card d-none " id="searchresults">
					<!--start of panel default 1-->
					<div class="card-header bg-dark  text-white">Search Result</div>
					<div class="card-body" id="displayRecords" style='width: 100%;'></div>
				</div>
				<!--End of Search result -->
			</div>
		</div>
	</div>
	<%@include file="../common/footer.jsp" %> 
</body>
<script
	src="resources/js/application/stakeholder/extendstakeholdervalidity.js"></script>
</html>