<html>
<head>
<title>OBPS</title>
<%@include file="../common/headerfiles.jsp"%>

</head>
<body ng-app="CommonApp" ng-controller="edcrscrutinyController">
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4"
					style="font-size: 32px; border-bottom: 3px solid #005776">eDCR
					Scrutiny</h3>

				<form class="ng-scope" id="edcrscrutinyForm" name="edcrscrutinyForm"
					autocomplete="off">
					<script src="resources/js/application/edcrScrutiny/scrutiny.js"></script>

					<div class="row">

						<div class="col-md-12 py-12 px-12">
							<input type="button" value="Generate Scrutiny Report"
								id="genReport" name="genReport"
								ng-click="generateReport(edcrscrutiny.planReport);"
								formtarget="_blank" class="btn btn-primary b-btn"
								style="float: right; visibility: hidden; margin-right: 70px">
							<br>
							<h5 style="">
								Click on Browse and select a .DXF File<span class="mandatory">*</span>
							</h5>
							<br>
							<table id="dxftable">
								<tr>

									<td colspan="0">Please Select Office<span
										class="mandatory">*</span>: <select class=" " id="validoffice"
										name="validoffice" ng-model="validoffice" required>
											<option selected value="">-- select an option --</option>
											<option ng-repeat='item in userofficelist'
												value="{{item.officecode}}">{{item.officename1}}</option>
									</select> <span class=" " style="color: red;">{{errorSelectmsg}}
									</span>
									</td>
									<td><input type="file" id="dxffile" name="dxffile"
										ng-model="dxffile" class="btn btn-info btn-sm" /><span
										class="" style="color: red;">{{errorDxfFile}}</span></td>

								</tr>
								<tr>
									<td style="height: 40px"></td>
								</tr>
								<tr>

									<td><input type="button" value="SCRUTINIZE"
										ng-click="submitDetails();" class="btn btn-primary b-btn"></td>
									<td></td>
									<td></td>
								</tr>

								<tr class="edcrResp" style="visibility: hidden;">
									<td style="font-size: 20px; font-weight: bold;"><label
										for="edcrstatus">EDCR Status :</label><label id="edcrstatus"
										name="edcrstatus">{{edcrscrutiny.status}}</label></td>
									<td style="font-size: 20px; font-weight: bold;"><label
										for="edcrnumber">EDCR Number :</label><label id="edcrnumber"
										name="edcrnumber">{{edcrscrutiny.edcrnumber}}</label></td>
									<td></td>
								</tr>

								<tr class="edcrResp" style="visibility: hidden;">
									<td><p>
											Click on <span
												style="font-size: 20; font-style: italic; color: blue;">*Generate
												Scrutiny Report</span> Button to view details of Report
										</p></td>
									<td></td>
									<td></td>
								</tr>
							</table>

						</div>
						<!-- 						<div class="col-md-12 py-12 px-12"> -->
						<!-- 							<table id="entrytable" -->
						<!-- 								style="border-top: 3px solid #005776; border-bottom: 3px solid #005776"> -->
						<!-- 								<tr> -->
						<!-- 									<td></td> -->
						<!-- 									<td> -->
						<!-- 										<div class="form-group"> -->
						<!-- 											<label for="jcaptcha" class="">Captcha <span -->
						<!-- 												class="mandatory">*</span></label> <img src="./jcaptcha.jpg" -->
						<!-- 												id="jcaptchaimg" onclick="changeCaptcha();" -->
						<!-- 												title="Click To Reload" style="cursor: pointer;" /> <input -->
						<!-- 												type="text" -->
						<!-- 												ng-model="applicationEnclosures.userresponsecaptcha" -->
						<!-- 												id="jcaptcha" name="jcaptcha" value="" class="form-control" -->
						<!-- 												autocomplete="off"> <span id="jcaptchaMsg" -->
						<!-- 												class="formErrorContent"></span> -->
						<!-- 											<div style="text-align: center; padding-top: 10px"> -->
						<!-- 												<input type="button" value="Submit" -->
						<!-- 													ng-click="submitDetails();" class="btn btn-primary b-btn"> -->
						<!-- 												<input type="button" value="Reset" -->
						<!-- 													ng-click="resetDetails();" class="btn btn-primary b-btn"> -->
						<!-- 											</div> -->
						<!-- 											<br /> -->
						<!-- 											<div id="msg" class="formErrorContent" style="width: 100%"></div> -->
						<!-- 										</div> -->
						<!-- 									</td> -->
						<!-- 									<td></td> -->
						<!-- 								</tr> -->
						<!-- 							</table> -->

						<!-- 						</div> -->

						<div class="col-md-12 py-12 px-12"
							style="border-top: 3px solid #005776; border-bottom: 3px solid #005776">

							<table class="table col-md-10">
								<tr>
									<td colspan="5"
										style="text-align: center; font-style: italic; font-size: 22px">--------------
										eDCR Scrutiny List-------------</td>
								</tr>

								<tr>
									<th>Slno</th>
									<th>Office</th>
									<th>eDCR Number</th>
									<th>Uploaded On</th>
									<th>Status</th>

									<th></th>
								</tr>
								<tr ng-repeat='item in edcrscrutinylist'>
									<td>{{$index + 1}}</td>

									<td>{{item.officename}}</td>
									<td>{{item.edcrnumber}}</td>
									<td>{{item.entrydate}}</td>
									<td>{{item.status}}</td>

									<td><input type="button" value="Download Report"
										ng-click="" class="btn btn-info btn-sm"/></td>
								</tr>
							</table>

						</div>
						<div class="col-md-2"></div>
						<div class="col-md-2"></div>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>

</html>