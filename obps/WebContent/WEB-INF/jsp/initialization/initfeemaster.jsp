<html>
<head>
<title>OBPS | Create Fee Types</title>
<%@include file="../common/headerfiles.jsp"%>
<script src="resources/js/util/sha256.min.js"></script>
</head>
<body>
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4" style="font-size: 32px;">Create Fee Master</h3>

				<div class="row" ng-app="CommonApp">
					<div class="col-md-12 py-12 px-12">
						<div class='containerBody' id="createfeemasterCtrl"
							ng-controller="createfeemasterCtrl">
							<div style='width: 100%; margin: 15px auto 0'>
								<form id="feemasterForm" name="feemasterForm">


									<table class="entrytable"
										style="width: 70%; margin: 0px auto; border-spacing: 10px">

										<tr>

											<td>Please Select Office:
											</td>
											<td><select id='officecode'
												ng-model='feemaster.offices.officecode'
												style="min-width: 350px;">
												
													<core:forEach items="${offices}" var="item">
														
														<option value="${item.key}">${item.value}</option>
													</core:forEach>
											</select> <span class=" " style="color: red;">{{errorSelectmsg}}
											</span></td>

										</tr>
										<tr>

											<td>Please Select Licensee Type:
											</td>
											<td><select id='licenseetypecode'
												ng-model='feemaster.licenseetypes.licenseetypecode'
												style="min-width: 350px;">
													<core:forEach items="${licenseetypes}" var="item">
														<option value="${item.key}">${item.value}</option>
													</core:forEach>
											</select> <span class=" " style="color: red;">{{errorSelectmsg}}
											</span></td>

										</tr>

										<tr>

											<td>Please Select Fee Type<span class="mandatory">*</span>:
											</td>
											<td><select id='feetypecode' ng-model='feemaster.feetypes.feetypecode'
												style="min-width: 350px;">
												
													<core:forEach items="${feetypes}" var="item">
														<option value="${item.key}">${item.value}</option>
													</core:forEach>
											</select> <span class=" " style="color: red;">{{errorSelectmsg}}
											</span></td>

										</tr>
										<tr class="form-group has-feedback">
											<td class="title">Fee Amount:*</td>
											<td class="col-xs-3 "><input type="text"
												class="form-control" id="feeamount" name="feeamount"
												maxlength="99" ng-model="feemaster.feeamount" required
												autocomplete="off" /> <span id="feeamountMsg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
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
<script src="resources/js/application/initialization/initfeemaster.js"></script>
</html>