<html>
	<head>
		<title>OBPS | Change Password</title>
		<%@include file="common/headerfiles.jsp" %> 
		
	    <link href="resources/js/vendor/jQuery/passwordstrengthmeter/jquery.passwordstrengthmeter.min.css" rel="stylesheet" type="text/css">
		<script src="resources/js/vendor/jQuery/passwordstrengthmeter/jquery.passwordstrengthmeter.min.js"></script>    
		<script src="resources/js/util/sha256.min.js"></script>		
		<script src="resources/js/application/changepassword.js"></script>      		
	</head>
	<body>
	<div class="d-flex" id="wrapper">
		<%@include file="common/menuside.jsp" %>  		   
	    <div id="page-content-wrapper">	
		  <%@include file="common/menutop.jsp" %>     
	      <div class="container-fluid">
	        <h3 class="mt-4" style="font-size:32px;">Change Password</h3>
	        
	        <form class="ng-scope" ng-app="applicationApp" ng-controller="applicationController" id="applicationForm" name="applicationForm" autocomplete="off">
		        <div class="row">	             
		        	<div class="col-md-4 py-4 px-5">
		        		<!-- <h3>Change Password</h3> -->
						<table id="entrytable">						  										
		     				<tr>
			     				<td>
				     				<div class="form-group">
										<label for="username" class="">Email <span class="mandatory">*</span></label>
		     							<input type="text" ng-model="signupDetails.username" id="username" name="username" maxlength="99"  class="form-control" autocomplete="off" readonly>
									</div>		
									<span id="usernameMsg" class="formErrorContent"></span>				     							     				
			     				</td>
			     			</tr>
			     			<tr>					     				
			     				<td>
				     				<div class="form-group">
										<label for="userpassword" class="">Password <span class="mandatory">*</span></label>
		     							<input type="password" ng-model="signupDetails.userpassword" id="userpassword" name="userpassword" maxlength="512" class="form-control" autocomplete="off" >
                                        <span id="percent">0%</span>&nbsp;&nbsp;<span class="Msg" id='results'></span>
                                      		<br/><div class="colorbar" id="colorbar"></div>   
									</div>	
									<span id="userpasswordMsg" class="formErrorContent"></span>						     				
			     				</td>
			     			</tr>
			     			<tr>				     				
			     				<td>
				     				<div class="form-group">
										<label for="userpasswordconfirm" class="">Confirm Password <span class="mandatory">*</span></label>
		     							<input type="password" ng-model="signupDetails.userpasswordconfirm" id="userpasswordconfirm" name="userpasswordconfirm" maxlength="512" class="form-control" autocomplete="off" >
									</div>	
									<span id="userpasswordconfirmMsg" class="formErrorContent"></span>
									<br/>							     				
			     				</td>			     				
			     			</tr>
			     			<tr>	
		     					<td>
									<div class="form-group">
										<label for="jcaptcha" class="">Captcha <span class="mandatory">*</span></label>
										<img src="./jcaptcha.jpg" id="jcaptchaimg"  title="Click To Reload" style="cursor: pointer;"/>
										<input type="text" ng-model="signupDetails.userresponsecaptcha" id="jcaptcha" name="jcaptcha" value="" class="form-control"  autocomplete="off" >
										<span id="jcaptchaMsg" class="formErrorContent"></span>
										<div style="text-align: center;padding-top:10px ">
											<input type="button" value="Submit" ng-click="submitDetails();" class="btn btn-primary b-btn">
											<input type="button" value="Reset" ng-click="resetDetails();" class="btn btn-secondary">
										</div>	
										<br/>											
										<div id="successMsg" class="formErrorContent" style="width:100%"></div>	
									</div>					     				
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
</html>