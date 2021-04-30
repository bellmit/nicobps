<!DOCTYPE html>
<html lang="en">
<head>
	<title>OBPS | Sign Up</title>
	<!-- <meta charset="utf-8"> -->
    <meta name="viewport" content="width=device-width, initial-scale=1">        
    <%@include file="../common/headerfiles.jsp" %>     
   	<script src="resources/js/application/signup.js"></script>   	  
</head>

<body>
	<%@include file="../common/headerext.jsp" %> 

	<!-- Breadcrumb -->
	<div class="bg-light">
		<div class="container">
			<ul class="breadcrumb bg-light pl-0">
				<li><a href="index.html">Home</a></li>
				<li>Sign Up</li>
			</ul>
		</div>
	</div>

	<div class="mb-5">
		<div class="container">		
			<div class="mt-5">

				  <form class="ng-scope" ng-app="signupApp" ng-controller="signupController" id="signupForm" name="signupForm" autocomplete="off">
				      <div class="row">	        				     	
				     	<div class="col-md-4 py-4 px-4">
				     		<h5 style="border-bottom:3px solid #005776">License Details</h5>		
				     		<table id="entrytable">				     		
				     			<tr>
				     				<td><label for="licenseetypecode" class="">Licensee Type <span class="mandatory">*</span></label></td>
				     				<td>
			     						<select ng-model="signupDetails.licenseetypecode" id="licenseetypecode" name="licenseetypecode" class="form-control" ng-options="T.key as T.value for T in listLicenseetypes">
	                                       	<option value="">-</option>
	                                       </select> 
			     					</td>
				     			</tr>		
				     			<tr>
				     				<td><label for="firmindividual" class="">Firm/Individual <span class="mandatory">*</span></label></td>
				     				<td>
			     						<select ng-model="signupDetails.firmindividual" id="firmindividual" name="firmindividual" class="form-control">
	                                       	<option value="">-</option>
	                                       	<option value="F">Firm</option>
	                                       	<option value="I">Individual</option>
                                        </select> 
			     					</td>
				     			</tr>
				     			<tr ng-if="signupDetails.firmindividual=='F'">
				     				<td><label for="firmname" class="">Firm Name <span class="mandatory">*</span></label></td>
				     				<td><input type="text" ng-model="signupDetails.firmname" id="firmname" name="firmname" maxlength="99" class="form-control" autocomplete="off" required></td>
				     			</tr>				     			
				     			
				     			
				     			<tr>
				     				<td><label for="fullname" class="">Applicant's Name <span class="mandatory">*</span></label></td>
				     				<td><input type="text" ng-model="signupDetails.fullname" id="fullname" name="fullname" maxlength="99" class="form-control" autocomplete="off" required></td>
				     			</tr>				     							     						     						     	
				     			<tr>
				     				<td><label for="username" class="">Email <span class="mandatory">*</span></label></td>
				     				<td><input type="text" ng-model="signupDetails.username" id="username" name="username" maxlength="99" class="form-control" autocomplete="off" required></td>
				     			</tr>		
			     				<tr>
				     				<td><label for="password" class="">Password <span class="mandatory">*</span></label></td>
				     				<td><input type="password" ng-model="signupDetails.userpassword" id="userpassword" name="password" maxlength="512" class="form-control" autocomplete="off" required></td>
				     			</tr>	
			     				<tr>
				     				<td><label for="userpasswordconfirm" class="">Confirm Password <span class="mandatory">*</span></label></td>
				     				<td><input type="password" ng-model="signupDetails.userpasswordconfirm" id="userpasswordconfirm" name="userpasswordconfirm" maxlength="512" class="form-control" autocomplete="off" required></td>
				     			</tr>				
				     			<tr>
				     				<td><label for="mobileno" class="">Mobile No. <span class="mandatory">*</span></label></td>
				     				<td><input type="text" ng-model="signupDetails.mobileno" id="mobileno" name="mobileno" maxlength="10" class="form-control" autocomplete="off" required></td>
				     			</tr>				     			
				     		</table>	     																													 													    
				     	</div>	
				     		
				     	<div class="col-md-4 py-4 px-4">
				     		<h5 style="border-bottom:3px solid #005776">Present Address</h5>		
				     		<table id="entrytable">				     			
				     			<tr>
				     				<td><label for="preaddressline1" class="">Address Line - 1</label></td>
				     				<td><input type="text" ng-model="signupDetails.preaddressline1" id="preaddressline1" name="preaddressline1" maxlength="99" class="form-control" autocomplete="off" required></td>
				     			</tr>	
				     			<tr>
				     				<td><label for="preaddressline2" class="">Address Line - 1 </label></td>
				     				<td><input type="text" ng-model="signupDetails.preaddressline2" id="preaddressline2" name="preaddressline2" maxlength="99" class="form-control" autocomplete="off" required></td>
				     			</tr>
				     			<tr>
				     				<td><label for="previllagetown" class="">Village/Town <span class="mandatory">*</span></label></td>
				     				<td><input type="text" ng-model="signupDetails.previllagetown" id="previllagetown" name="previllagetown" maxlength="99" class="form-control" autocomplete="off" required></td>
				     			</tr>							     				     	
				     			<tr>
				     				<td><label for="prestatecode" class="">State <span class="mandatory">*</span></label></td>
				     				<td>
			     						<select ng-model="signupDetails.prestatecode" id="prestatecode" name="prestatecode" class="form-control" ng-options="T.key as T.value for T in listStates" ng-change="populateCombo(1)">
	                                       	<option value="">-</option>
	                                    </select> 
			     					</td>
				     			</tr>					     		
				     			<tr>
				     				<td><label for="predistrictcode" class="">District <span class="mandatory">*</span></label></td>
				     				<td>
			     						<select ng-model="signupDetails.predistrictcode" id="predistrictcode" name="predistrictcode" class="form-control" ng-options="T.key as T.value for T in listDistrictsPre">
	                                       	<option value="">-</option>
	                                    </select> 
			     					</td>
				     			</tr>			
				     			<tr>
				     				<td><label for="prepincode" class="">Pin Code <span class="mandatory">*</span></label></td>
				     				<td><input type="text" ng-model="signupDetails.prepincode" id="prepincode" name="prepincode" maxlength="6" class="form-control" autocomplete="off" required></td>
				     			</tr>				     			
				     		</table>	
				     	</div>	 	
				     	
				     	<div class="col-md-4 py-4 px-4">
				     		<h5 style="border-bottom:3px solid #005776">Present Address</h5>		
				     		<table id="entrytable">				     			
				     			<tr>
				     				<td><label for="peraddressline1" class="">Address Line - 1</label></td>
				     				<td><input type="text" ng-model="signupDetails.peraddressline1" id="peraddressline1" name="peraddressline1" maxlength="99" class="form-control" autocomplete="off" required></td>
				     			</tr>	
				     			<tr>
				     				<td><label for="peraddressline2" class="">Address Line - 1 </label></td>
				     				<td><input type="text" ng-model="signupDetails.peraddressline2" id="peraddressline2" name="peraddressline2" maxlength="99" class="form-control" autocomplete="off" required></td>
				     			</tr>
				     			<tr>
				     				<td><label for="pervillagetown" class="">Village/Town <span class="mandatory">*</span></label></td>
				     				<td><input type="text" ng-model="signupDetails.pervillagetown" id="pervillagetown" name="pervillagetown" maxlength="99" class="form-control" autocomplete="off" required></td>
				     			</tr>							     				     	
				     			<tr>
				     				<td><label for="perstatecode" class="">State <span class="mandatory">*</span></label></td>
				     				<td>
			     						<select ng-model="signupDetails.perstatecode" id="perstatecode" name="perstatecode" class="form-control" ng-options="T.key as T.value for T in listStates" ng-change="populateCombo(2)">
	                                       	<option value="">-</option>
	                                    </select> 
			     					</td>
				     			</tr>					     		
				     			<tr>
				     				<td><label for="perdistrictcode" class="">District <span class="mandatory">*</span></label></td>
				     				<td>
			     						<select ng-model="signupDetails.perdistrictcode" id="perdistrictcode" name="perdistrictcode" class="form-control" ng-options="T.key as T.value for T in listDistrictsPer">
	                                       	<option value="">-</option>
	                                    </select> 
			     					</td>
				     			</tr>			
				     			<tr>
				     				<td><label for="perpincode" class="">Pin Code <span class="mandatory">*</span></label></td>
				     				<td><input type="text" ng-model="signupDetails.perpincode" id="perpincode" name="perpincode" maxlength="6" class="form-control" autocomplete="off" required></td>
				     			</tr>				     			
				     		</table>
				     		<input type="button" value="Submit" ng-click="submitDetails();" class="btn btn-primary"  >	
				     	</div>					     	
				     	        	       	        				    		    
				     </div> 
			     </form>	
			     
			</div>
		</div>
	</div>
	

	<%@include file="../common/footerext.jsp" %> 
</body>
</html>


