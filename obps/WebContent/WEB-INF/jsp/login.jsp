<!DOCTYPE html>
<html lang="en">
<head>
<title>OBPS | Login</title>
<!-- <meta charset="utf-8"> -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@include file="common/headerfiles.jsp"%>
<script src="resources/js/util/sha256.min.js"></script>
<script src="resources/js/application/login.js "></script>
</head>


<body>
	<%@include file="common/headerext.jsp"%>
	<!-- Background -->
	<div class="b-bg-image py-5" style="padding-bottom: 200px !important">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 d-flex">
					<div class="align-self-center pr-5">
						<h1 class="text-left mt-5 b-left-head">Online Building
							Permission System</h1>
						<hr />
						<h5 class="text-left mt-5 b-left-head"></h5>
						<table class="datatable1">
							<thead>
								<tr>
									<th>Office Name</th>
									<th>No. of Applications Received</th>
									<th>No. of Applications Approved</th>
									<th>No. of Applications Under Processing</th>
								</tr>
							</thead>
							<tbody>
								<core:forEach items="${listDashboardData}" var="I">
									<tr>
										<td><core:out value="${I.officename}" escapeXml="true"></core:out></td>
										<td><core:out value="${I.totalac}" escapeXml="true"></core:out></td>
										<td><core:out value="${I.approvedac}" escapeXml="true"></core:out></td>
										<td><core:out value="${I.pendingac}" escapeXml="true"></core:out></td>
									</tr>
								</core:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<div class="col-lg-4 b-login-sec">
					<div class="d-block px-5 pt-5 pb-4 border-bottom-0 b-login-w">
						<h2 class="b-login-head">Log In</h2>
					</div>

					<div class="">
						<form class="form" id="loginForm" name='f' action='/obps/login'
							method='POST' autocomplete="off">
							<div class="form-group ">
								<label for="username" class="">Email :</label> <input
									class="form-control" type="text" id="username" name="username"
									value="gmc@gmail.com" autocomplete="off" required>
							</div>
							<div class="form-group">
								<label for="password" class="">Password:</label> <input
									class="form-control" type="password" id="password"
									name="password" value="Nicnet@10" autocomplete="off" required>
							</div>
							<!-- <div class="form-group custom-control custom-checkbox">
								<input class="custom-control-input" id="login-rem-1" type="checkbox" name="remember"> 
								<label class="custom-control-label " for="login-rem-1">Remember me</label>
							</div> -->
							<div class="form-group">
								<label for="jcaptchaimg" class="">Captcha:</label> 
								<img src="./jcaptchalogin.jpg" id="jcaptchaimg" title="Click To Reload" class="reloadCaptchaButton" style="cursor: pointer;" /> 
								<i title="Click To Reload" class="reloadCaptchaButton fa fa-refresh ml-2" style="cursor: pointer;"></i>
									<input class="form-control mt-2" placeholder="Enter Captcha"
									type="text" id="jcaptchalogin" name="jcaptchalogin" value=""
									autocomplete="off">
							</div>
							<div class="container" align="center">
								<span style="color: red; width: 100%;"> <core:out
										value="${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}" />
								</span>
							</div>
							<div class="text-center py-4">
								
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" /> <input type="submit" name="submit"
									value="Login" class="btn btn-primary b-btn" />
							</div>
							<!-- <p class="text-left b-notreg">Don't have an account? <a href="" data-toggle="modal" data-target="#signup-modal" data-dismiss="modal">Sign Up</a></p> -->
							
						</form>
					</div>
				</div>

			</div>
		</div>
	</div>

	<!-- Dashboard -->
	<!-- 	<div class="mt-5" id="b-homedb" style="position: relative; top: -170px; margin-bottom: -110px;"> -->
	<!-- 		<div class="container"> -->
	<!-- 			<div class="row text-center"> -->
	<!-- 				<h2 class="col-md-12">Figures tell the story</h2> -->
	<!-- 				<div class="col-lg-4 p-4"> -->
	<!-- 					<div class="bg-light py-4 b-dbcard"> -->
	<!-- 						<p><span class="fas fa-university" style="font-size:40px;"></span></p> -->
	<!-- 						<h3 style="font-size: 16px;"><strong>No. of Applications Received</strong></h3> -->
	<!-- 						<div class="text-left "> -->
	<!-- 							<p class="px-5">Last year  <span class="float-right">1.11 Lakh</span></p> -->

	<!-- 							<p class="px-5">Current year <span class="float-right">1.23 Lakh</span></p> -->
	<!-- 						</div> -->
	<!-- 					</div> -->
	<!-- 				</div> -->

	<!-- 				<div class="col-lg-4 p-4"> -->
	<!-- 					<div class="bg-light py-4 b-dbcard"> -->
	<!-- 						<p><span class="fa fa-check-square" style="font-size:40px"></span></p> -->
	<!-- 						<h3 style="font-size: 16px;"><strong>No. of Applications Processed</strong></h3> -->
	<!-- 						<div class="text-left "> -->
	<!-- 							<p class="px-5">Last year  <span class="float-right">1.00 Lakh</span></p> -->

	<!-- 							<p class="px-5">Current year <span class="float-right">1.00 Lakh</span></p> -->
	<!-- 						</div>						 -->
	<!-- 					</div> -->
	<!-- 				</div> -->

	<!-- 				<div class="col-lg-4 p-4"> -->
	<!-- 					<div class="bg-light py-4 b-dbcard"> -->
	<!-- 						<p><span class="fa fa-times-square" style="font-size:40px"></span></p> -->
	<!-- 						<h3 style="font-size: 16px;"><strong>No. of Applications Rejected</strong></h3> -->
	<!-- 						<div class="text-left "> -->
	<!-- 							<p class="px-5">Last year  <span class="float-right">11,000</span></p> -->

	<!-- 							<p class="px-5">Current year <span class="float-right">13,000</span></p> -->
	<!-- 						</div> -->
	<!-- 					</div> -->
	<!-- 				</div>				 -->
	<!-- 			</div> -->
	<!-- 		</div> -->
	<!-- 	</div>	 -->
	<%@include file="common/footerext.jsp"%>
</body>

</html>


