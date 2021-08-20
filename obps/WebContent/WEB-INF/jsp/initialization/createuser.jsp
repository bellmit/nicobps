<html>
<head>
<title>OBPS | Create User</title>
<%@include file="../common/headerfiles.jsp"%>
<script src="resources/js/util/sha256.min.js"></script>
<style>
input::-webkit-outer-spin-button, input::-webkit-inner-spin-button {
	-webkit-appearance: none;
	margin: 0;
}

/* Firefox */
input[type=number] {
	-moz-appearance: textfield;
}
</style>
</head>
<body>
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4" style="font-size: 32px;">Create User</h3>

				<div class="row" ng-app="CommonApp">
					<div class="col-md-12 py-12 px-12">
						<div class='containerBody' id="createuserCtrl"
							ng-controller="createuserCtrl">
							<div style='width: 100%; margin: 15px auto 0'>
								<form id="userForm" name="userForm">
									<table class="entrytable"
										style="width: 70%; margin: 0px auto; border-spacing: 10px">

										<tr class="form-group has-feedback">
											<td class="title">Office:*</td>
											<td class="col-xs-5 selectContainer"><select
												class="form-control" id="officecode"
												ng-model="user.officecode"
												ng-init='user.officecode=${officeList[0].key }' required
												ng-change='listUsers()'>
													<core:forEach items="${officeList}" var='i'>
														<option value='${i.key}' selected='selected'>${i.value}</option>
													</core:forEach>
											</select></td>
										</tr>
										<tr class="form-group has-feedback">
											<td class="title">Full Name:*</td>
											<td class="col-xs-5 selectContainer"><input type="text"
												class="form-control" id="fullname" name="fullname"
												maxlength="99" ng-model="user.fullname" required
												autocomplete="off" /> <span id="fullnameMsg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										</tr>
										<tr class="form-group has-feedback">
											<td class="title">Username:*</td>
											<td class="col-xs-5 selectContainer"><input type="text"
												class="form-control" id="username" name="username"
												maxlength="99" ng-model="user.username" required
												autocomplete="off" /> <span id="usernameMsg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										</tr>

										<tr class="form-group has-feedback">
											<td class="title">Password :*</td>
											<td class="col-xs-5 selectContainer"><input
												class="form-control" type="password" name="passwords"
												id="passwords" ng-model="user.userpassword" maxlength="512"
												autocomplete="new-password" required /> <!--                                 <label ng-if="user.usercode > 0" style="color:blue"> *Keep BLANK incase password is unchanged</label> -->
												<span id="passwordsMsg"></span> <!-- 			                                <span class="alert alert-danger" ng-show="!userForm.passwords.$pristine && userForm.passwords.$invalid"> Required</span> -->
											</td>
										</tr>
										<tr class="form-group has-feedback">
											<td class="title">Confirm Password :*</td>
											<td class="col-xs-5 selectContainer"><input
												class="form-control" type="password" id="repassword"
												name="repassword" ng-model="user.repassword" maxlength="512"
												autocomplete="new-password" required /> <!--                                 <label ng-if="user.usercode > 0" style="color:blue"> *Keep BLANK incase password is unchanged</label> -->
												<label
												ng-if="user.repassword != user.userpassword && userForm.repassword.$dirty"
												style="color: red">*password is not matching</label> <span
												id="repasswordMsg"></span> <!-- 			                                <span class="alert alert-danger" ng-show="!userForm.repassword.$pristine && userForm.repassword.$invalid"> Required</span> -->
											</td>
										</tr>
										<tr class="form-group has-feedback">
											<td class="title">Designation :*</td>
											<td class="col-xs-5 selectContainer"><input
												class="form-control" type="text" id="designation"
												name="designation" ng-model="user.designation"
												maxlength="99" autocomplete="off" required /> <!--                                 <label ng-if="user.usercode > 0" style="color:blue"> *Keep BLANK incase password is unchanged</label> -->
												<span id="designationMsg"></span> <!-- 			                                <span class="alert alert-danger" ng-show="!userForm.repassword.$pristine && userForm.repassword.$invalid"> Required</span> -->
											</td>
										</tr>
										<tr class="form-group has-feedback">
											<td class="title">Mobile no :*</td>
											<td class="col-xs-5 selectContainer"><input
												type="number" class="form-control" id="mobileno"
												name="mobileno" min="1000000000" max="9999999999"
												pattern="^([1-9]){1}([0-9]){9}$" ng-model="user.mobileno" maxlength="10"
												required autocomplete="off" /> <span id="mobilenoMsg"></span>
												<!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										</tr>
										<tr class="form-group has-feedback">
											<td colspan="2" align="center">
												<button type="submit" id="add" ng-click="save()"
													class="btn btn-primary b-btn"  ng-if="actionButton === 1"
													ng-disabled="userForm.$invalid">Save</button>
												<button name="submit" id="add" ng-click="update()"
													class="btn btn-primary b-btn"  ng-if="actionButton === 2"
													ng-disabled="userForm.$invalid">Update</button> <input
												type="reset" value="Reset" ng-click="reset()"
												class="btn btn-secondary"  />
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
<script src="resources/js/application/initialization/createuser.js"></script>
<script>
	$(document).ready(function() {
		$("#mobileno").numeric();
	});
</script>
</html>