<html>
<head>
<title>OBPS | Create Usages</title>
<%@include file="../common/headerfiles.jsp"%>
<script src="resources/js/util/sha256.min.js"></script>
</head>
<body>
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4" style="font-size: 32px;">Create Usages</h3>

				<div class="row" ng-app="CommonApp">
					<div class="col-md-12 py-12 px-12">
						<div class='containerBody' id="initusagesCtrl"
							ng-controller="initusagesCtrl">
							<div style='width: 100%; margin: 15px auto 0'>
								<form id="usagesForm" name="usagesForm">


									<table class="entrytable"
										style="width: 70%; margin: 0px auto; border-spacing: 10px">

										<tr class="form-group has-feedback">

											<td>Please Select Sub Occupancy<span class="mandatory">*</span>:
											</td>
											<td class="col-xs-3"><select class="form-control" id='suboccupancycode' 
												ng-model='usage.suboccupancies.suboccupancycode'
												style="min-width: 350px;">
												
													<core:forEach items="${suboccupancies}" var="item">
														
														<option value="${item.key}">${item.value}</option>
													</core:forEach>
											</select> <span class=" " style="color: red;">{{errorSelectmsg}}
											</span></td>

										</tr>
										<tr class="form-group has-feedback">

											<td class="title">Sub Usage Code<span class="mandatory">*</span>:</td>
											<td class="col-xs-3 "><input type="text"
												class="form-control" id="usagecode" name="usagecode"
												maxlength="99" ng-model="usage.usagecode" required ng-disabled="IsDisabled"
												autocomplete="off" /> <span id="feeamountMsg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>

										</tr>

										<tr class="form-group has-feedback">

											<td class="title">Sub Occupancy Name<span class="mandatory">*</span>:</td>
											<td class="col-xs-3 "><input type="text"
												class="form-control" id="usagename" name="usagename"
												maxlength="99" ng-model="usage.usagename" required
												autocomplete="off" /> <span id="usagenameMsg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>

										</tr>
										<tr class="form-group has-feedback">
											<td class="title">Usage Description<span class="mandatory">*</span>:</td>
											<td class="col-xs-3 "><input type="text"
												class="form-control" id="description" name="description"
												maxlength="99" ng-model="usage.description" required
												autocomplete="off" /> <span id="descriptionMsg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										</tr>

										<tr class="form-group has-feedback">
											<td colspan="2" align="center">
												<button type="submit" id="add" ng-click="save()"
													class="button-primary" ng-if="actionButton === 1"
													ng-disabled="feemasterForm.$invalid">Save</button>
												<button name="submit" id="add" ng-click="update()"
													class="button-primary" ng-if="actionButton === 2"
													ng-disabled="feemasterForm.$invalid">Update</button> <input
												type="reset" value="Reset" ng-click="reset()"
												class="button-default" />
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
	</div>
</body>
<script src="resources/js/application/models/initializations.js"></script>
<script src="resources/js/application/initialization/initusages.js"></script>
</html>