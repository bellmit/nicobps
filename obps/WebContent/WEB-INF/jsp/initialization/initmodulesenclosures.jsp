<html>
<head>
<title>OBPS | Map Enclosures</title>
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
				<h3 class="mt-4" style="font-size: 32px;">Map Enclosures</h3>
				<br /> <br />

				<div class="row" ng-app="CommonApp">
					<div class="col-md-12 py-12 px-12">
						<div class="containerBody row " id="initmodulesenclosuresCtrl"
							ng-controller="initmodulesenclosuresCtrl">

							<div class="col-md-8">
								<form>
									<table class="" style="width: 100%; margin: 0px auto">
										<tr>
											<td><b>Module Name<span style="color: red;">*</span>
													:
											</b></td>
											<td><select id='modulecode' style='width: 300px'
												class="form-control" ng-model="modulecode"
												ng-change='listProcesses(modulecode)'>
													<option value="">---Select Module---</option>
													<core:forEach items="${modulesList}" var='i'>
														<option value='${i.key}' selected='selected'>${i.value}</option>
													</core:forEach>
											</select><span id="moduleMsg" style="color: red;"></span></td>
										</tr>

										<tr>
											<td><b> Process<span style="color: red;">*</span> :
													<b></td>
											<td><select id='processcode'
												ng-options="T.key as T.value for T in processesList"
												style='width: 300px' class="form-control"
												ng-model="processcode">

													<option value="">---Select Process---</option>



											</select><span id="processMsg" style="color: red;"></span></td>

										</tr>

										<tr class="form-group has-feedback">
											<td><b>Office :</td>
											<td><select id='officecode' style='width: 300px'
												class="form-control" ng-model="officecode">
													<option value="0">---Select office---</option>
													<core:forEach items="${officeList}" var='i'>
														<option value='${i.key}' selected='selected'>${i.value}</option>
													</core:forEach>
											</select></td>


										</tr>
										<tr>
											<td><b> Licensee Type : </td>
											<td><select id='licenseetypecode' style='width: 300px'
												class="form-control" ng-model="licenseetypecode"
												ng-change='listWards()'>
													<option value="0">---Select Licensee Type---</option>
													<core:forEach items="${licenseeTypeList}" var='i'>
														<option value='${i.key}' selected='selected'>${i.value}</option>
													</core:forEach>
											</select></td>
										</tr>


										<tr>
											<td>&nbsp;</td>
										</tr>





										<tr>
											<td>&nbsp;</td>
										</tr>
							<tr>
											<td></td>
											<td align="left">
												<button style="padding: 5px" class="btn btn-primary b-btn"
													ng-click="getMappedEnclosures(processcode);mapView()">Map
													Enclosures</button>
													
													
											<button style="padding: 5px" class="btn btn-secondary"
													ng-click="reset()">Reset 
													</button></td>
										</tr>
									</table>
								</form>
							</div>
							<div id="mapView" style="display: none" class="h-100"
								style="display:block">
								<div class="container-fluid h-100" style="display: block">
									<div class="row mt-5" style="display: flex">
										<table>
											<tr>
												<td class="title font-weight-bold">Module:</td>
												<td>&nbsp;<span id='module'></span></td>
											</tr>
											<tr>
												<td class="title font-weight-bold">Process:</td>
												<td>&nbsp;<span id='process'></span></td>
											</tr>
											<tr>
												<td class="title font-weight-bold">Office :</td>
												<td>&nbsp;<span id='office'></span></td>
											</tr>

											<tr>
												<td class="title font-weight-bold">Licenseetype:</td>
												<td>&nbsp;<span id='licensee'></span></td>
											</tr>
											<tr>
												<td>&nbsp;</td>
											</tr>

										</table>
									</div>
									<div class="row my-3 mr-2 justify-content-end">
										<div class='fa fa-search left' style='font-size: 25px'></div>
										<input type="text" ng-model="search" />
									</div>
									<div class="row h-50" style="overflow-y: scroll;">
										<div class="col-md-12">
											<div style="width: 100%; overflow-y: auto">
												<table border="1" cellspacing="0" width="100%">
													<tr>
														<th></th>
														<th>Enclosure Name</th>
														<th>Enclosure Description</th>

													</tr>
													<tr ng-repeat='item in enclosures |filter:search'
														style="width: 100%;">
														<td><input style="margin: 8px" type='checkbox'
															ng-model="item.checked" /></td>
														<td>&nbsp;{{item.enclosurename}}</td>
														<td>&nbsp;{{item.enclosuredescription}}</td>

													</tr>
												</table>
											</div>

										</div>
									</div>
									<div class="row mt-3 justify-content-center">
										<input class="float-right btn btn-primary b-btn" type="button" value="Save" 
											ng-click="save()" />
									</div>

								</div>
							</div>


						</div>
						<div class="col-md-12" id="displayRecords"
							style='width: 80%; margin: 15px auto 50px auto;'></div>
					</div>
				</div>

			</div>
		</div>
	</div>


</body>
<script src="resources/js/application/models/initializations.js"></script>
<script
	src="resources/js/application/initialization/initmodulesenclosures.js"></script>
</html>