<html>
<head>
<title>OBPS</title>
<%@include file="../common/headerfiles.jsp"%>
</head>
<body ng-app="CommonApp" ng-controller="CommonCtrl">
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4" style="font-size: 32px;">EDCR Scrutiny</h3>

				<form class="ng-scope" ng-app="applicationApp"
					ng-controller="applicationController" id="applicationForm"
					name="applicationForm" autocomplete="off">

					<div class="row">
						<div class="col-md-4 py-12 px-12">

							<table id="entrytable">

								<tr>


									<td>
										<div class="form-group">
											<label for="dxffile" class="">DXF File <span
												class="mandatory">*</span></label> <input type="file"
												class="form-control" id="dxffile" name="dxffile" />
										</div> <span id="dxffileMsg" class="formErrorContent"></span>
									</td>


								</tr>


							</table>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
<script src="resources/js/application/stakeholder/srverify.js"></script>
</html>