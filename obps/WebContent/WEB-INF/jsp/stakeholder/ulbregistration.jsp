<html>
<head>
<title>OBPS</title>
<%@include file="../common/headerfiles.jsp"%>
<style>
</style>
</head>
<body ng-app="CommonApp" ng-controller="CommonCtrl">
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4"
					style="font-size: 32px; border-bottom: 3px solid #005776">ULB
					Registration</h3>
				<div class="row">
					<div class="col-md-12 py-4 px-5 row" >
						<div class="col-md-6 py-4 px-5">
							<h4>Select Registering Office</h4> 
							<select ng-model='registeringofficecode'
								style="min-width: 350px;"
								ng-change="getOffices(registeringofficecode)">
								<core:forEach items="${registeringoffices}" var="item">
									<option value="${item.officecode}">${item.officename1}</option>
								</core:forEach>
							</select>
							<form id="form" style="display:none;" method="get" action="./paysrappfee.htm">
								<input type="hidden" name='applicationcode' value=""/>
								<input type="hidden" name='feeamount' value="{{fee.feeamount}}"/>
								<input type="hidden" name='feecode' value="{{fee.feecode}}"/>
							</form>
						</div> 
						<div class="col-md-6 px-5 pt-5" >
							<button class="btn btn-primary" ng-disabled="registeringofficecode==0" ng-click="registerStakeholder()">Proceed to Pay</button>
						</div>
					<span class="mt-4 pl-5" ng-show="fee!==-1">Application Fees applicable : <span style="font-style: bold">{{fee.feeamount}}/-</span></span> 
					</div>
					<div class="col-md-12 pb-4 px-5 mt-1">
						<h5 class="col-md-12" style="border-top: 3px solid #005776"
							ng-show="Offices.length>0">Offices falling under this
							Registering office:</h5>
						<div>
							<ol>
								<li ng-repeat="i in Offices">{{i.officename1}}</li>
							</ol>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
<script src="resources/js/application/stakeholder/ulbregistration.js"></script>
</html>