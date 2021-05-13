<html>
<head>
<title>OBPS</title>
<%@include file="../common/headerfiles.jsp"%>

</head>
<body>
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4"
					style="font-size: 32px; border-bottom: 3px solid #005776">EDCR
					Scrutiny</h3>
				<form class="ng-scope" ng-app="uploadEdcr"
					ng-controller="edcrscrutinyController" id="edcrscrutinyForm"
					name="edcrscrutinyForm" autocomplete="off">
					<script src="resources/js/application/edcrScrutiny/scrutiny.js"></script>
					<div class="row">
						<div class="col-md-12 py-12 px-12">
							<h5 style="">Click on Browse and select a .DXF File<span class="mandatory">*</span></h5>
							<table id="dxftable">
								<tr>
									<td><input type="file" id="dxffile" name="dxffile" /></td>
								</tr>
								<tr>
								<td style="height: 40px"></td>
								</tr>
								<tr id="edcrResp" style="visibility:hidden;">
									<td style="font-size: 20px;font-weight: bold;"><label for="edcrstatus"  >EDCR Status :</label><label id="edcrstatus" name="edcrstatus">  </label> </td>
									<td style="font-size: 20px;font-weight: bold;"><label for="edcrnumber" >EDCR Number :</label><label id="edcrnumber" name="edcrnumber">  </label> </td>	 
								</tr>
							</table>
						</div>
						<div class="col-md-12 py-12 px-12">
							<table id="entrytable"
								style="border-top: 3px solid #005776; border-bottom: 3px solid #005776">
								<tr>
									<td></td>
									<td>
										<div class="form-group">
											<label for="jcaptcha" class="">Captcha <span
												class="mandatory">*</span></label> <img src="./jcaptcha.jpg"
												id="jcaptchaimg" onclick="changeCaptcha();"
												title="Click To Reload" style="cursor: pointer;" /> <input
												type="text"
												ng-model="applicationEnclosures.userresponsecaptcha"
												id="jcaptcha" name="jcaptcha" value="" class="form-control"
												autocomplete="off"> <span id="jcaptchaMsg"
												class="formErrorContent"></span>
											<div style="text-align: center; padding-top: 10px">
												<input type="button" value="Submit"
													ng-click="submitDetails();" class="btn btn-primary b-btn">
												<input type="button" value="Reset"
													ng-click="resetDetails();" class="btn btn-primary b-btn">
											</div>
											<br />
											<div id="successMsg" class="formErrorContent"
												style="width: 100%"></div>
										</div>
									</td>
									<td></td>
								</tr>
							</table>

						</div>
				 
					</div>
				</form>
			</div>
		</div>
	</div>

</body>

</html>