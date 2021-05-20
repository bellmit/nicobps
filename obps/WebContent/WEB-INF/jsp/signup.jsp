<!DOCTYPE html>
<html lang="en">
<head>
	<title>OBPS | Sign Up</title>
	<!-- <meta charset="utf-8"> -->
    <meta name="viewport" content="width=device-width, initial-scale=1">        
    <%@include file="common/headerfiles.jsp" %>     
    
    <link href="resources/js/vendor/jQuery/passwordstrengthmeter/jquery.passwordstrengthmeter.min.css" rel="stylesheet" type="text/css">
	<script src="resources/js/vendor/jQuery/passwordstrengthmeter/jquery.passwordstrengthmeter.min.js"></script>    
	<script src="resources/js/util/sha256.min.js"></script>
   	<script src="resources/js/application/signup.js"></script>   	  
</head>

<body>
	<%@include file="common/headerext.jsp" %> 

	<!-- Breadcrumb -->
	<div class="bg-light">
		<div class="container">
			<ul class="breadcrumb bg-light pl-0">
				<li><a href="login.htm">Home</a></li>
				<li>Register</li>
			</ul>
		</div>
	</div>

	<div class="mb-5">
		<div class="container">		
			<div class="mt-5">

				  <form class="ng-scope" ng-app="applicationApp" ng-controller="applicationController" id="applicationForm" name="applicationForm" autocomplete="off">
				      <div class="row">	        				     	
				     	<div class="col-md-12 py-12 px-12">
				     		<h5 style="border-bottom:3px solid #005776">License Details</h5>								
				     		<table id="entrytable">				     		
				     			<tr>	
				     				<td>			     				
					     				<div class="form-group">
											<label for="licenseetypecode" class="">Licensee Type <span class="mandatory">*</span></label>
				     						<select ng-model="signupDetails.licenseetypecode" id="licenseetypecode" name="licenseetypecode" class="form-control" ng-options="T.key as T.value for T in listLicenseetypes">
		                                       	<option value="">-</option>
	                                       </select> 	                                                                            
										</div>	
										<span id="licenseetypecodeMsg" class="formErrorContent"></span>	
			     					</td>				  
				     				<td>
					     				<div class="form-group">
											<label for="firmindividual" class="">Firm/Individual <span class="mandatory">*</span></label>
			     							<select ng-model="signupDetails.firmindividual" id="firmindividual" name="firmindividual" class="form-control" ng-change="enableFirmName()">
		                                       	<option value="">-</option>
		                                       	<option value="F">Firm</option>
		                                       	<option value="I">Individual</option>
	                                        </select>	                                           
										</div>	
										<span id="firmindividualMsg" class="formErrorContent"></span>	 				     							     				
				     				</td>
 				     				<td>
					     				<div class="form-group">
											<label for="firmname" ng-if="signupDetails.firmindividual!='F'">Firm Name</label>
											<label for="firmname" ng-if="signupDetails.firmindividual=='F'">Firm Name <span class="mandatory">*</span></label>
			     							<input type="text" ng-model="signupDetails.firmname" id="firmname" name="firmname" maxlength="99" class="form-control" autocomplete="off" readonly="true"> 
										</div>	
										<span id="firmnameMsg" class="formErrorContent"></span>													     							     				
				     				</td> 				     		
				     			</tr>				     			
				     			<tr>
				     				<td>
					     				<div class="form-group">
											<label for="fullname" class="">Applicant's Name <span class="mandatory">*</span></label>
			     							<input type="text" ng-model="signupDetails.fullname" id="fullname" name="fullname" maxlength="99" class="form-control" autocomplete="off" >
										</div>	
										<span id="fullnameMsg" class="formErrorContent"></span>						     							     				
				     				</td>	
				     				<td>			     				
					     				<div class="form-group">
											<label for="gender" class="">Gender <span class="mandatory">*</span></label>
				     						<select ng-model="signupDetails.gender" id="gender" name="gender" class="form-control" ng-options="T.key as T.value for T in listGenders">
		                                       	<option value="">-</option>
	                                       </select> 	                                                                            
										</div>	
										<span id="genderMsg" class="formErrorContent"></span>	
			     					</td>				     							     							     			
				     				<td>
					     				<div class="form-group">
											<label for="username" class="">Email <span class="mandatory">*</span></label>
			     							<input type="text" ng-model="signupDetails.username" id="username" name="username" maxlength="99" class="form-control" autocomplete="off" >
										</div>		
										<span id="usernameMsg" class="formErrorContent"></span>				     							     				
				     				</td>			     								     				
				     			</tr>				     							     						     						     	
				   	
			     				<tr>
				     				<td>
					     				<div class="form-group">
											<label for="mobileno" class="">Mobile No. <span class="mandatory">*</span></label>
			     							<input type="text" ng-model="signupDetails.mobileno" id="mobileno" name="mobileno" maxlength="10" class="form-control" autocomplete="off" >
										</div>		
										<span id="mobilenoMsg" class="formErrorContent"></span>	
										<br/>	     				
				     				</td>			     				
				     				<td>
					     				<div class="form-group">
											<label for="userpassword" class="">Password <span class="mandatory">*</span></label>
			     							<input type="password" ng-model="signupDetails.userpassword" id="userpassword" name="userpassword" maxlength="512" class="form-control" autocomplete="off" >
	                                        <span id="percent">0%</span>&nbsp;&nbsp;<span class="Msg" id='results'></span>
                                       		<br/><div class="colorbar" id="colorbar"></div>   
										</div>	
										<span id="userpasswordMsg" class="formErrorContent"></span>						     				
				     				</td>			     				
				     				<td>
					     				<div class="form-group">
											<label for="userpasswordconfirm" class="">Confirm Password <span class="mandatory">*</span></label>
			     							<input type="password" ng-model="signupDetails.userpasswordconfirm" id="userpasswordconfirm" name="userpasswordconfirm" maxlength="512" class="form-control" autocomplete="off" >
										</div>	
										<span id="userpasswordconfirmMsg" class="formErrorContent"></span>
										<br/>							     				
				     				</td>			     				
				     				<td></td>
				     			</tr>										     		
				     		</table>	     																													 													    
				     	</div>	
				     		
				     	<div class="col-md-12 py-12 px-12">
				     		<h5 style="border-bottom:3px solid #005776">Present Address</h5>		
				     		<table id="entrytable">				     			
				     			<tr>
				     				<td>
					     				<div class="form-group">
											<label for="preaddressline1" class="">Address Line - 1</label>
			     							<input type="text" ng-model="signupDetails.preaddressline1" id="preaddressline1" name="preaddressline1" maxlength="99" class="form-control" autocomplete="off" >
										</div>																     			
				     				</td>					     			
				     				<td>
					     				<div class="form-group">
											<label for="preaddressline2" class="">Address Line - 2</label>
			     							<input type="text" ng-model="signupDetails.preaddressline2" id="preaddressline2" name="preaddressline2" maxlength="99" class="form-control" autocomplete="off" >
										</div>															     			
				     				</td>	
				     				<td>
					     				<div class="form-group">
											<label for="previllagetown" class="">Village/Town <span class="mandatory">*</span></label>
			     							<input type="text" ng-model="signupDetails.previllagetown" id="previllagetown" name="previllagetown" maxlength="99" class="form-control" autocomplete="off" >
										</div>		
										<span id="previllagetownMsg" class="formErrorContent"></span>			     				
				     				</td>	
				     			</tr>					     								     				     	
				     			<tr>
				     				<td>
					     				<div class="form-group">
											<label for="prestatecode" class="">State <span class="mandatory">*</span></label>
				     						<select ng-model="signupDetails.prestatecode" id="prestatecode" name="prestatecode" class="form-control" ng-options="T.key as T.value for T in listStates" ng-change="populateCombo(1)">
		                                       	<option value="">-</option>
		                                    </select> 
										</div>
										<span id="prestatecodeMsg" class="formErrorContent"></span>					     				
				     				</td>				     			
				     				<td>
					     				<div class="form-group">
											<label for="predistrictcode" class="">District <span class="mandatory">*</span></label>
				     						<select ng-model="signupDetails.predistrictcode" id="predistrictcode" name="predistrictcode" class="form-control" ng-options="T.key as T.value for T in listDistrictsPre">
		                                       	<option value="">-</option>
		                                    </select> 
										</div>
										<span id="predistrictcodeMsg" class="formErrorContent"></span>					     				
				     				</td>						     			
				     				<td>
					     				<div class="form-group">
											<label for="prepincode" class="">Pin Code <span class="mandatory">*</span></label>
				     						<input type="text" ng-model="signupDetails.prepincode" id="prepincode" name="prepincode" maxlength="6" class="form-control" autocomplete="off" >
										</div>
										<span id="prepincodeMsg" class="formErrorContent"></span>						     				
				     				</td>	
				     			</tr>					     						     			
				     		</table>	
				     	</div>	 	
				     	
				     	<div class="col-md-12 py-12 px-12">
				     		<h5 style="border-bottom:3px solid #005776">Permanent Address</h5>	
				     		<input type="checkbox" id="copyAddress" onclick="copyAddr(this)"> <span class="tinytext">Same as Present Address</span>
				     		<table id="entrytable">				     			
				     			<tr>
				     				<td>
					     				<div class="form-group">
											<label for="peraddressline1" class="">Address Line - 1</label>
			     							<input type="text" ng-model="signupDetails.peraddressline1" id="peraddressline1" name="peraddressline1" maxlength="99" class="form-control" autocomplete="off" >
										</div>					     				
				     				</td>					     			
				     				<td>
					     				<div class="form-group">
											<label for="peraddressline2" class="">Address Line - 2</label>
			     							<input type="text" ng-model="signupDetails.peraddressline2" id="peraddressline2" name="peraddressline2" maxlength="99" class="form-control" autocomplete="off" >
										</div>					     				
				     				</td>	
				     				<td>
					     				<div class="form-group">
											<label for="pervillagetown" class="">Village/Town <span class="mandatory">*</span></label>
			     							<input type="text" ng-model="signupDetails.pervillagetown" id="pervillagetown" name="pervillagetown" maxlength="99" class="form-control" autocomplete="off" >
										</div>	
										<span id="pervillagetownMsg" class="formErrorContent"></span>					     				
				     				</td>	
				     			</tr>					     								     				     	
				     			<tr>
				     				<td>
					     				<div class="form-group">
											<label for="perstatecode" class="">State <span class="mandatory">*</span></label>
				     						<select ng-model="signupDetails.perstatecode" id="perstatecode" name="perstatecode" class="form-control" ng-options="T.key as T.value for T in listStates" ng-change="populateCombo(2)">
		                                       	<option value="">-</option>
		                                    </select> 
										</div>	
										<span id="perstatecodeMsg" class="formErrorContent"></span>				     				
				     				</td>				     			
				     				<td>
					     				<div class="form-group">
											<label for="perdistrictcode" class="">District <span class="mandatory">*</span></label>
				     						<select ng-model="signupDetails.perdistrictcode" id="perdistrictcode" name="perdistrictcode" class="form-control" ng-options="T.key as T.value for T in listDistrictsPer">
		                                       	<option value="">-</option>
		                                    </select> 
										</div>	
										<span id="perdistrictcodeMsg" class="formErrorContent"></span>						     				
				     				</td>						     			
				     				<td>
					     				<div class="form-group">
											<label for="perpincode" class="">Pin Code <span class="mandatory">*</span></label>
				     						<input type="text" ng-model="signupDetails.perpincode" id="perpincode" name="perpincode" maxlength="6" class="form-control" autocomplete="off" >
										</div>	
										<span id="perpincodeMsg" class="formErrorContent"></span>				     				
				     				</td>	
				     			</tr>					     						     			
				     		</table>
			     		</div>				
			     		     		
			     		<div class="col-md-12 py-12 px-12" ng-if="listLicenseesregistrationsm.length > 0">
				     		<h5 style="border-bottom:3px solid #005776">Licensees Registration</h5>					     			     				     					     		
                            <table id="entrytable">			
                                   <tr>
                                       <td class='flathead' style="width:50%">Registered With <span class="mandatory">*</span></td>                                       
                                       <td class='flathead' style="width:50%">Registration No. <span class="mandatory">*</span></td>                                       
                                   </tr>											
                                   <tr ng-repeat="L in listLicenseesregistrationsm" id="tr{{$index}}">
                                       <td>
                                       		<!-- <input type="checkbox" ng-model="L.ischecked" id="licenseeregistrationcode_{{$index}}" name="licenseeregistrationcode" class="languages" ng-change="chechThisJQ(this)"/> -->
                                       		<input type="checkbox" ng-model="L.ischecked" id="licenseeregistrationcode_{{$index}}" name="licenseeregistrationcode" ng-change="chechThis({{$index}})"/>
                                       		{{ L.licenseedescription}} {{ L.licenseeregistrationcode}}
                                       </td>                                       
                                       <td ng-if="L.ischecked==true">
                                       	<input type="text" ng-model="L.registrationdescription" id="registrationdescription_{{$index}}" name="registrationdescription" maxlength="99" class="form-control" autocomplete="off"/>
                                       </td>    
                                       <td ng-if="L.ischecked==false">
                                       	<input type="text" ng-model="L.registrationdescription" id="registrationdescription_{{$index}}" name="registrationdescription" maxlength="99" class="form-control" autocomplete="off" disabled/>
                                       </td>    
                                   </tr>
                            </table> 
                            <div id="listLicenseesregistrationsmMsg" class="formErrorContent" style="width:30%"></div>					                            						     		
				     	</div>	
				     	
				     	
						<div class="col-md-12 py-12 px-12" ng-if="signupDetails.isotp=='Y' && (signupDetails.isemail=='Y' || signupDetails.issms=='Y')">			
							<h5 style="border-bottom:3px solid #005776">Enter the OTP sent to your email and mobile</h5>				     		
				     		<table id="entrytable">				     			
				     			<tr>
				     				<td ng-if="signupDetails.isemail=='Y'">
					     				<div class="form-group">
											<label for="emailotp" class="">Email OTP</label>
			     							<input type="text" ng-model="signupDetails.emailotp" id="emailotp" name="emailotp" maxlength="10" class="form-control" autocomplete="off" >
										</div>	
										<span id="emailotpMsg" class="formErrorContent"></span>					     				
				     				</td>					     			
				     				<td ng-if="signupDetails.issms=='Y'">
					     				<div class="form-group">
											<label for="emailotp" class="">Mobile OTP</label>
			     							<input type="text" ng-model="signupDetails.mobileotp" id="mobileotp" name="mobileotp" maxlength="10" class="form-control" autocomplete="off" >
										</div>				
										<span id="mobileotpMsg" class="formErrorContent"></span>			     				
				     				</td>	
				     			</tr>					     								     				     	
				     			     						     			
				     		</table>
			     		</div>					     	
				     	
				     	
				     	<div class="col-md-12 py-12 px-12">				     						     		
				     		<table id="entrytable" style="border-top:3px solid #005776;border-bottom:3px solid #005776">		     				     					     		
				     			<tr>
				     				<td></td>	
			     					<td>
										<div class="form-group">
											<label for="jcaptcha" class="">Captcha <span class="mandatory">*</span></label>
											<img src="./jcaptcha.jpg" id="jcaptchaimg" onclick="changeCaptcha();" title="Click To Reload" style="cursor: pointer;"/>
											<input type="text" ng-model="signupDetails.userresponsecaptcha" id="jcaptcha" name="jcaptcha" value="" class="form-control"  autocomplete="off" >
											<span id="jcaptchaMsg" class="formErrorContent"></span>
											<div style="text-align: center;padding-top:10px ">
												<input type="button" value="Submit" ng-click="submitDetails();" class="btn btn-primary b-btn">
												<input type="button" value="Reset" ng-click="resetDetails();" class="btn btn-primary b-btn">
											</div>	
											<br/>											
											<div id="successMsg" class="formErrorContent" style="width:100%"></div>	
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
	

	<%@include file="common/footerext.jsp" %> 
</body>
</html>


