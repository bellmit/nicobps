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

							<br>
							<h5 style="">
								Select the Office and Click on Browse and select a .DXF File<span
									class="mandatory">*</span>
							</h5>
							<br>
							<table id="dxftable">
								<tr>

									<td>Please Select Office<span class="mandatory">*</span>:
										<select class=" " id="validoffice" name="validoffice"
										ng-model="validoffice" required )>
											<option selected value="">-- select an option --</option>
											<option ng-repeat='item in userofficelist' value="{{item}}">{{item.officename1}}</option>
									</select> <span class=" " style="color: red;">{{errorSelectmsg}}
									</span>
									</td>
									<td colspan="2"><input type="file" id="dxffile"
										name="dxffile" ng-model="dxffile" class="btn btn-info btn-sm" /><span
										class="" style="color: red;">{{errorDxfFile}}</span></td>
								</tr>
								<tr>
									<td style="height: 20px" colspan="3"></td>
								</tr>
								<tr>
									<td colspan="3"><input type="button" value="SCRUTINIZE"
										ng-click="submitDetails();" class="btn btn-primary b-btn"></td>
								</tr>
								<tr>
									<td style="height: 20px" colspan="3" id=" "></td>
								</tr>
								<tr class="edcrResp" style="visibility: hidden;">
									<td style="font-size: 20px; font-weight: bold;"><label
										for="edcrstatus">EDCR Status :</label><label id="edcrstatus"
										name="edcrstatus">{{edcrscrutiny.status}}</label></td>
									<td style="font-size: 20px; "><label
										for="edcrnumber">EDCR Number :</label><label id="edcrnumber"
										name="edcrnumber" style="font-weight: bold;">{{edcrscrutiny.edcrnumber}}</label><br><div id="applybpa"></div></td>
									<td><input type="button" value="Generate Scrutiny Report"
										id="genReport" name="genReport"
										ng-click="generateReport(edcrscrutiny.planReport);"
										formtarget="_blank" class="btn btn-info btn-sm"
										style="margin-left: 70px"></td>
								</tr>
								 <tr>
									<td style="height: 20px" colspan="3" id=" "></td>
								</tr>
								<tr class="edcrResp" style="visibility: hidden;">
									<td><p>
											Click on <span
												style="font-size: 15px; font-style: italic; color: blue;">*Generate
												Scrutiny Report</span> Button to view details of Report
										</p></td>
									<td></td>
									<td></td>
								</tr>
							</table>
							<div id="errmsg"></div>
						</div>
						<div class="col-md-12 py-12 px-12"
							style="border-top: 3px solid #005776; border-bottom: 3px solid #005776; padding-top: 20px; align-content: center;">

							<h4 style="padding-left: 30%;">eDCR Scrutiny List</h4>
							<div class="table-responsive" id="displayRecordsDiv">
								<div id="displayRecords"
									style='width: 100%; margin: 0px auto 50px auto; padding: 0px 20px'></div>
							</div>

						</div>

					</div>
				</form>
			</div>
		</div>
	</div>
	<%@include file="../common/footer.jsp"%>
</body>

<script src="resources/js/util/ngdirectives.js" type="text/javascript"></script>
</html>