
<form class="ng-scope" ng-app="applicationApp"
	ng-controller="applicationController" id="applicationForm"
	name="applicationForm" autocomplete="off">
		<script src="resources/js/application/bpa/uploadbpaenclouseres.js"></script>  
	<input class="d-none" type="text" name="appcode" id="appcode"
		value="<core:out value="${applicationcode}" escapeXml="true"></core:out>">
	<div class="row">
		<div class="col-md-12 py-12 px-12">									
			<table id="entrytable">
				<tr ng-repeat="L in listBpaEnclosures" id="tr{{$index}}">
					<td><input type="checkbox" ng-model="L.ischecked"
						id="ischecked_{{$index}}" name="ischecked" /> {{
						L.enclosurename}}</td>
					<td ng-if="L.ischecked==true"><input type="file"
						id="file_{{$index}}" name="file_{{$index}}"
						onchange="addFile(this.id)" /></td>
					<td ng-if="L.ischecked==false"><input type="file"
						id="file_{{$index}}" name="file_{{$index}}" disabled /></td>
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
								type="text" ng-model="applicationEnclosures.userresponsecaptcha"
								id="jcaptcha" name="jcaptcha" value="" class="form-control"
								autocomplete="off"> <span id="jcaptchaMsg"
								class="formErrorContent"></span>
							<div style="text-align: center; padding-top: 10px">
								<input type="button" value="Submit" ng-click="submitDetails();"
									class="btn btn-primary b-btn"> <input type="button"
									value="Reset" ng-click="resetDetails();"
									class="btn btn-primary b-btn">
							</div>
							<br />
							<div id="successMsg" class="formErrorContent" style="width: 100%"></div>
						</div>
					</td>
					<td></td>
				</tr>
			</table>

		</div>

	</div>
</form>