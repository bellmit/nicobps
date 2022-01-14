<html>
<head>
<title>OBPS | Offices</title>
<%@include file="../common/headerfiles.jsp"%>
<script src="resources/js/util/sha256.min.js"></script>
</head>
<body>
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h2 class="mt-4" style="font-size: 32px;">Create Office</h2>

				<div class="row" ng-app="CommonApp">
					<div class="col-md-12 py-12 px-12">
						<div class='containerBody' id="officesCtrl"
							ng-controller="officesCtrl">
							<div style='width: 100%; margin: 15px auto 0'>
								<form id="officeForm" name="officeForm">
									<table class="entrytable"
										style="width: 100%; margin: 0px auto; border-spacing: 10px">

										<tr class="form-group has-feedback">
											<td class="col-md-2"><b>Office Name1:<span style="color:red">*</span> </b></td>
											<td class="col-md-4 selectContainer"><input type="text"
												class="form-control" id="officename1" name="officename1"
												maxlength="99" ng-model="offices.officename1" required
												autocomplete="off" /> <span id="officename1Msg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
											
											<td style="border-left: 1px solid #d9d4d4" class="col-md-2"><b>Office Name2:<span style="color:red">*</span> </b></td>
											<td class="col-md-4 selectContainer"><input type="text"
												class="form-control" id="officename2" name="officename2"
												maxlength="99" ng-model="offices.officename2" required
												autocomplete="off" /> <span id="officename2Msg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										</tr>
										<tr class="form-group has-feedback">
											<td class="col-md-2"><b>Office Name3:</b></td>
											<td class="col-md-4 selectContainer"><input type="text"
												class="form-control" id="officename3" name="officename3"
												maxlength="99" ng-model="offices.officename3"
												autocomplete="off" /> <span id="officename3Msg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										
											<td style="border-left: 1px solid #d9d4d4" class="col-md-2"><b>Office Short Name:</b></td>
											<td class="col-md-4 selectContainer"><input type="text"
												class="form-control" id="officeshortname"
												name="officeshortname" maxlength="99"
												ng-model="offices.officeshortname" autocomplete="off" /> <span
												id="officeshortnameMsg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										</tr>


										<tr class="form-group has-feedback">
											<td class="col-md-2"><b>Signatory Name:<span style="color:red">*</span> </b></td>
											<td class="col-md-4 selectContainer"><input type="text"
												class="form-control" id="signatoryname" name="signatoryname"
												maxlength="99" ng-model="offices.signatoryname" required
												autocomplete="off" /> <span id="signatorynameMsg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										
											<td style="border-left: 1px solid #d9d4d4" class="col-md-2"><b>Signatory Designation:<span style="color:red">*</span> </b></td>
											<td class="col-md-4 selectContainer"><input type="text"
												class="form-control" id="signatorydesignation"
												name="signatorydesignation" maxlength="99"
												ng-model="offices.signatorydesignation" required
												autocomplete="off" /> <span id="signatorydesignationMsg"></span>
												<!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										</tr>
										<tr class="form-group has-feedback">
											<td class="col-md-2"><b>Email ID:</b></td>
											<td class="col-md-4 selectContainer"><input type="text"
												class="form-control" id="senderemailid" name="senderemailid"
												maxlength="99" ng-model="offices.senderemailid" autocomplete="off" />
												<span id="emailidMsg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										
											<td style="border-left: 1px solid #d9d4d4" class="col-md-2"><b>Email ID Password:</b></td>
											<td class="col-md-4 selectContainer"><input
												type="password" class="form-control" id="emailidpassword"
												name="emailidpassword" maxlength="99"
												ng-model="offices.emailidpassword" autocomplete="off" /> <span
												id="emailidpasswordMsg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										</tr>
										<tr class="form-group has-feedback">
											<td class="col-md-2"><b>SMS Username:</b></td>
											<td class="col-md-4 selectContainer"><input type="text"
												class="form-control" id="smsusername" name="smsusername"
												maxlength="99" ng-model="offices.smsusername"
												autocomplete="off" /> <span id="smsusernameMsg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										
											<td style="border-left: 1px solid #d9d4d4" class="col-md-2"><b>SMS Password:</b></td>
											<td class="col-md-4 selectContainer"><input
												type="password" class="form-control" id="smspassword"
												name="smspassword" maxlength="99"
												ng-model="offices.smspassword" autocomplete="off" /> <span
												id="smspasswordMsg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										</tr>
										<tr class="form-group has-feedback">
											<td class="col-md-2"><b>Tenant ID:<span style="color:red">*</span> </b></td>
											<td class="col-md-4 selectContainer"><input type="text"
												class="form-control" id="tenantid" name="tenantid"
												ng-model="offices.tenantid" required autocomplete="off" />
												<span id="tenantidMsg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										
											<td style="border-left: 1px solid #d9d4d4" class="col-md-2"><b>State ID:<span style="color:red">*</span> </b></td>
											<td class="col-md-4 selectContainer"><input type="text"
												class="form-control" id="stateid" name="stateid"
												ng-model="offices.stateid" required autocomplete="off" /> <span
												id="stateidMsg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										</tr>
										<tr class="form-group has-feedback">
											
										
											<td class="col-md-2"><b>Is Registering Office:<span style="color:red">*</span> </b></td>
											<td class="col-md-4 selectContainer"><div
													class="form-check form-check-inline">
													<input class="form-check-input" type="radio" value="Y"
														name="isregisteringoffice"
														ng-model="offices.isregisteringoffice"
														id="isregisteringofficeY"> <label
														class="form-check-label" for="isregisteringofficeY">
														YES </label>
												</div>
												<div class="form-check form-check-inline">
													<input class="form-check-input" type="radio" value="N"
														name="isregisteringoffice"
														ng-model="offices.isregisteringoffice"
														id="isregisteringofficeN" checked> <label
														class="form-check-label" for="isregisteringofficeN">
														NO </label>
												</div> <span id="tenantidMsg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
											<td style="border-left: 1px solid #d9d4d4" class="col-md-2"><b>Sms Sender Id:<span style="color:red">*</span> </b></td>
											<td class="col-md-4 selectContainer"><input type="text"
												class="form-control" id="smssenderid" name="smssenderid"
												ng-model="offices.smssenderid" required autocomplete="off" />
												<span id="smssenderid"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										</tr>
										<tr class="form-group has-feedback"
											ng-show="offices.isregisteringoffice == 'N'">
											<td class="col-md-2"><b>Registering Office:<span style="color:red">*</span> </b></td>
											<td class="col-md-4 selectContainer"><select
												class="form-control my-2"ng-model='offices.registeringofficecode'
												ng-value='registeringofficecode' style="max-width: 100%;"
												ng-change="getOffices(registeringofficecode)">
													<option value="0">Select Office</option>
													<core:forEach items="${registeringoffices}" var="item">
														<option value="${item.officecode}">${item.officename1}</option>
													</core:forEach>
											</select> <span class=" " style="color: red;">{{errorSelectmsg}}
											</span></td>
											<td style="border-left: 1px solid #d9d4d4"></td>
										</tr>
										
										<tr class="form-group has-feedback">
											<td class="col-md-2"><b>Logo:</b></td>
											<td class="col-md-4 selectContainer"><input type="file"
												class="form-control" id="logo" name="logo" ng-model="logo"
												autocomplete="off"  />
												<a id="viewlogo" href="#" ng-click="showFile(offices.username)">View
													Logo</a>
													<div class="col-md-12" id="enclosureWindow"></div>
													 <span
												id="logoMsg"></span> <!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
											</td>
										
											<td style="border-left: 1px solid #d9d4d4">
												
											</td>
											
										</tr>


										<tr form-group has-feedback">  
											<td colspan="5"  align="center"><br/>
												<button type="submit" id="add" ng-click="save()"
													class="btn btn-primary b-btn" ng-if="actionButton === 1"
													ng-disabled="officeForm.$invalid">Save</button>
												<button name="submit" id="add" ng-click="update()"
													class="btn btn-primary b-btn" ng-if="actionButton === 2"
													ng-disabled="officeForm.$invalid">Update</button> <input
												type="reset" value="Reset" ng-click="reset()"
												class="btn btn-secondary" />
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
<script src="resources/js/application/initialization/offices.js"></script>
</html>