<html>
<head>
<title>OBPS | Access Control</title>
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
<body ng-app="CommonApp" id="accesscontrolCtrl"
	ng-controller="accesscontrolCtrl">
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4" style="font-size: 32px;">Access Control</h3>

				<div class="row">
					<div class="col-md-12 py-12 px-12">
						<div class="containerBody">
							<form>
								<table class="" style="width: 100%; margin: 50px auto 0">
									<tr class="form-group has-feedback">
										<td class="title" style="width: 15%"><b>Select
												Office* :</b></td>
										<td><select id='officecode' style='width: 230px'
											class="form-control" ng-model="officecode"
											ng-change='listUsers()'>
												<core:forEach items="${officeList}" var='i'>
													<option value='${i.key}' selected='selected'>${i.value}</option>
												</core:forEach>
										</select></td>
<!-- 										<td rowspan="15" style="width: 65%; border: 1px solid blue;"> -->
<!-- 											<div style="width: 100%; max-height: 230px; overflow-y: auto"> -->
<!-- 												<table border="1" cellspacing="0" width="100%"> -->
<!-- 													<tr> -->
<!-- 														<th></th> -->
<!-- 														<th>URL</th> -->
<!-- 														<th>Sub Sub Menu</th> -->
<!-- 														<th>Sub Menu -->
<!-- 														</td> -->
<!-- 														<th>Menu Header</th> -->
<!-- 													</tr> -->
<!-- 													<tr ng-repeat='item in URLs track by $index' -->
<!-- 														style="width: 100%;"> -->
<!-- 														<td><input style="margin: 8px" type='checkbox' -->
<!-- 															ng-model="item.checked" /></td> -->
<!-- 														<td>&nbsp;{{item.pageurl}}</td> -->
<!-- 														<td>&nbsp;{{item.subsubmenu}}</td> -->
<!-- 														<td>&nbsp;{{item.submenu}}</td> -->
<!-- 														<td>&nbsp;{{item.parent}}</td> -->
<!-- 													</tr> -->
<!-- 												</table> -->
<!-- 											</div> -->
<!-- 										</td> -->
									</tr>
<!-- 									<tr> -->
<!-- 										<td>&nbsp;</td> -->
<!-- 									</tr> -->
<!-- 									<tr> -->
<!-- 										<td class="title">Full Name :</td> -->
<!-- 										<td><span>{{user.fullname}}</span></td> -->
<!-- 									</tr> -->
<!-- 									<tr> -->
<!-- 										<td>&nbsp;</td> -->
<!-- 									</tr> -->
<!-- 									<tr> -->
<!-- 										<td class="title">User Name :</td> -->
<!-- 										<td><span>{{user.username}}</span></td> -->
<!-- 									</tr> -->
<!-- 									<tr> -->
<!-- 										<td>&nbsp;</td> -->
<!-- 									</tr> -->
<!-- 									<tr> -->
<!-- 										<td class="title">User Access :</td> -->
<!-- 										<td><span ng-if='user.enabled=="Y"'>Enabled</span> <span -->
<!-- 											ng-if='user.enabled=="F"'>Disabled</span></td> -->
<!-- 									</tr> -->
<!-- 									<tr> -->
<!-- 										<td>&nbsp;</td> -->
<!-- 									</tr> -->
<!-- 									<tr> -->
<!-- 										<td colspan="2" align="center"><input type="button" -->
<!-- 											value="Save" ng-click="save()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 										</td> -->
<!-- 									</tr> -->
<!-- 									<tr> -->
<!-- 										<td>&nbsp;</td> -->
<!-- 									</tr> -->
<!-- 									<tr> -->
<!-- 										<td>&nbsp;</td> -->
<!-- 									</tr> -->
<!-- 									<tr> -->
<!-- 										<td>&nbsp;</td> -->
<!-- 									</tr> -->
<!-- 									<tr> -->
<!-- 										<td>&nbsp;</td> -->
<!-- 									</tr> -->
<!-- 									<tr> -->
<!-- 										<td>&nbsp;</td> -->
<!-- 									</tr> -->
								</table>
							</form>
						</div>
						<div id="displayRecords" class="mt-5"
							style='width: 80%; margin: 15px auto 50px auto;'></div>
					</div>
				</div>

			</div>
		</div>
	</div>
	<div id="mapView" style="display: none" class="h-100"
		style="display:block">
		<div class="container-fluid h-100" style="display: block">
			<div class="row mt-5" style="display: flex">
				<table>
					<tr>
						<td class="title font-weight-bold">Full Name :</td>
						<td><span>{{user.fullname}}</span></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td class="title font-weight-bold">User Name :</td>
						<td><span>{{user.username}}</span></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td class="title font-weight-bold">User Access :</td>
						<td><span ng-if='user.enabled=="Y"'>Enabled</span> <span
							ng-if='user.enabled=="F"'>Disabled</span></td>
					</tr>
				</table>
			</div>
			<div class="row my-3 mr-2 justify-content-end">
				<div class='fa fa-search left' style='font-size:25px'></div><input type="text" ng-model="search"/>
			</div>
			<div class="row h-50" style="overflow-y:scroll;">
				<table border="1" cellspacing="0" width="100%">
					<tr>
						<th style="position: sticky;top: 0;background-color:#e2e2e2"></th>
						<th style="position: sticky;top: 0;background-color:#e2e2e2">URL</th>
						<th style="position: sticky;top: 0;background-color:#e2e2e2">Sub Sub Menu</th>
						<th style="position: sticky;top: 0;background-color:#e2e2e2">Sub Menu
						<th style="position: sticky;top: 0;background-color:#e2e2e2">Menu Header </th>
					</tr>
					<tr ng-repeat='item in URLs | filter:search' style="width: 100%;">
						<td><input style="margin: 8px" type='checkbox'
							ng-model="item.checked" /></td>
						<td>&nbsp;{{item.pageurl}}</td>
						<td>&nbsp;{{item.subsubmenu}}</td>
						<td>&nbsp;{{item.submenu}}</td>
						<td>&nbsp;{{item.parent}}</td>
					</tr>
				</table>
			</div>
			<div class="row mt-3 justify-content-center"><input class="float-right" type="button" class="btn btn-primary b-btn"
							value="Save" ng-click="save()" /></div>
							
		</div>
	</div>
</body>
<script src="resources/js/application/models/initializations.js"></script>
<script src="resources/js/application/initialization/accesscontrol.js"></script>
</html>