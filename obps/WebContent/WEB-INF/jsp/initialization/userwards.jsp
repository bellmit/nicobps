<html>
<head>
<title>OBPS | User Wards Locations</title>
<%@include file="../common/headerfiles.jsp"%>
<script src="resources/js/util/sha256.min.js"></script>
<style>
.iconSelection {
	border: 1px solid black;
	width: 100%;
	height: 200px;
	overflow: hidden;
	overflow-y: scroll;
	background-color: whitesmoke;
	position: absolute;
	left: 0;
	z-index: 10;
}

.hide {
	display: none;
}

.table-condensed td {
	padding-bottom: 10px;
}
</style>
</head>
<body>
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4" style="font-size: 32px;">User Wards Locations</h3>

				<div class="row" ng-app="CommonApp">
					<div class="col-md-12 py-12 px-12">
						<div class="containerBody" id="userwardsCtrl"
							ng-controller="userwardsCtrl">
							<form>
								<table class="" style="width: 100%; margin: 50px auto 0">
									<tr class="form-group has-feedback">
										<td><b>Select Office* :</b><select id='officecode'
											style='width: 300px' class="form-control"
											ng-model="officecode" ng-change='listWards()'>
											<option value="" >---Select office---</option>
												<core:forEach items="${officeList}" var='i'>
													<option value='${i.key}' selected='selected'>${i.value}</option>
												</core:forEach>
										</select></td>
										<td rowspan="15" style="width: 60%; border: 1px solid blue;">
											<div id='wardrow'
												style="width: 100%; max-height: 230px; overflow-y: auto">
												<table border="1" cellspacing="0" width="100%">
													<tr>
														<th></th>


														<th>Ward Location</th>

													</tr>
													<tr ng-repeat='item in wards track by $index'
														style="width: 100%;">
														<td><input style="margin: 8px" type='checkbox'
															ng-model="item.checked" /></td>

														<td>&nbsp;{{item.locationname}}</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
									<tr>
										<td><b>Select User* :</b><select id='usercode'
											style='width: 300px' class="form-control" ng-model="usercode"
											ng-options="T.key as T.value for T in usersList"
											ng-change='mappedWards(usercode)'>
												<option value="">--Select--</option>
										</select><br>
										<br></td>
									</tr>
								
	<tr>
										<td colspan="3" align="left"><input type="button"
											value="Save" ng-click="save()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</td>
									</tr>

								</table>
							</form>
						</div>
						<div id="displayRecords"
							style='width: 80%; margin: 15px auto 50px auto;'></div>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
<script src="resources/js/application/models/initializations.js"></script>
<script src="resources/js/application/initialization/userwards.js"></script>
</html>