<html>
	<head>
		<title>OBPS | Offices</title>
		<%@include file="../common/headerfiles.jsp" %>     		
		<script src="resources/js/util/sha256.min.js"></script>
	</head>
	<body>
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp" %>  		   
	    <div id="page-content-wrapper">	
		  <%@include file="../common/menutop.jsp" %>     
	      <div class="container-fluid">
	        <h3 class="mt-4" style="font-size:32px;">Create Office</h3>
	        
	        <div class="row" ng-app="CommonApp">	         	
	        	<div class="col-md-12 py-12 px-12">
	        		<div class='containerBody' id="officesCtrl" ng-controller="officesCtrl">            
			            <div  style='width:100%;margin: 15px auto 0'>
			                <form id="officeForm" name="officeForm">
			                    <table class="entrytable" style="width:70%;margin: 0px auto; border-spacing: 10px"> 
			                        
			                        <tr class="form-group has-feedback">
			                            <td class="title">Office Name1:*</td>
			                            <td class="col-xs-5 selectContainer">
											<input type="text" class="form-control" id="officename1" name="officename1" maxlength="99"
												ng-model="offices.officename1" required autocomplete="off"/>        
											<span id="officename1Msg"></span>
<!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
			                            </td>
			                        </tr>
			                        <tr class="form-group has-feedback">
			                            <td class="title">Office Name2:*</td>
			                            <td class="col-xs-5 selectContainer">
											<input type="text" class="form-control" id="officename2" name="officename2" maxlength="99"
												ng-model="offices.officename2" required autocomplete="off"/>        
											<span id="officename2Msg"></span>
<!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
			                            </td>
			                        </tr>
			                        <tr class="form-group has-feedback">
			                            <td class="title">Office Name3:</td>
			                            <td class="col-xs-5 selectContainer">
											<input type="text" class="form-control" id="officename3" name="officename3" maxlength="99"
												ng-model="offices.officename3" autocomplete="off"/>        
											<span id="officename3Msg"></span>
<!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
			                            </td>
			                        </tr>
			                        <tr class="form-group has-feedback">
			                            <td class="title">Office Short Name:</td>
			                            <td class="col-xs-5 selectContainer">
											<input type="text" class="form-control" id="officeshortname" name="officeshortname" maxlength="99"
												ng-model="offices.officeshortname"  autocomplete="off"/>        
											<span id="officeshortnameMsg"></span>
<!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
			                            </td>
			                        </tr>
			                        
			                       
			                        <tr class="form-group has-feedback">
			                            <td class="title">Signatory Name:*</td>
			                            <td class="col-xs-5 selectContainer">
											<input type="text" class="form-control" id="signatoryname" name="signatoryname" maxlength="99"
												ng-model="offices.signatoryname" required autocomplete="off"/>        
											<span id="signatorynameMsg"></span>
<!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
			                            </td>
			                        </tr>
			                        <tr class="form-group has-feedback">
			                            <td class="title">Signatory Designation:*</td>
			                            <td class="col-xs-5 selectContainer">
											<input type="text" class="form-control" id="signatorydesignation" name="signatorydesignation" maxlength="99"
												ng-model="offices.signatorydesignation" required autocomplete="off"/>        
											<span id="signatorydesignationMsg"></span>
<!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
			                            </td>
			                        </tr>
			                        <tr class="form-group has-feedback">
			                            <td class="title">Email ID:</td>
			                            <td class="col-xs-5 selectContainer">
											<input type="text" class="form-control" id="emailid" name="emailid" maxlength="99"
												ng-model="offices.emailid"  autocomplete="off"/>        
											<span id="emailidMsg"></span>
<!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
			                            </td>
			                        </tr>
			                        <tr class="form-group has-feedback">
			                            <td class="title">Email ID Password:</td>
			                            <td class="col-xs-5 selectContainer">
											<input type="password" class="form-control" id="emailidpassword" name="emailidpassword" maxlength="99"
												ng-model="offices.emailidpassword"  autocomplete="off"/>        
											<span id="emailidpasswordMsg"></span>
<!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
			                            </td>
			                        </tr>
			                        <tr class="form-group has-feedback">
			                            <td class="title">SMS Username:</td>
			                            <td class="col-xs-5 selectContainer">
											<input type="text" class="form-control" id="smsusername" name="smsusername" maxlength="99"
												ng-model="offices.smsusername"  autocomplete="off"/>        
											<span id="smsusernameMsg"></span>
<!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
			                            </td>
			                        </tr>
			                        <tr class="form-group has-feedback">
			                            <td class="title">SMS Password:</td>
			                            <td class="col-xs-5 selectContainer">
											<input type="password" class="form-control" id="smspassword" name="smspassword" maxlength="99"
												ng-model="offices.smspassword"  autocomplete="off"/>        
											<span id="smspasswordMsg"></span>
<!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
			                            </td>
			                        </tr>
			                       
			                         <tr class="form-group has-feedback">
			                            <td colspan="2" align="center">
			                                <button type="submit" id="add" ng-click="save()" class="button-primary" ng-if="actionButton === 1" ng-disabled="officeForm.$invalid">Save</button>
			                              <button name="submit" id="add" ng-click="update()" class="button-primary" ng-if="actionButton === 2" ng-disabled="officeForm.$invalid">Update</button> 
			                                <input type="reset" value="Reset" ng-click="reset()" class="button-default"/>
			                            </td>
			                        </tr>
			                        
			                        
			                   
			                    </table>
			                </form>
			            </div> 
			            <div id="displayRecords" style='width:80%;margin: 15px auto 50px auto;'></div>
			        </div>
	        	</div>			        	       	               	
	        </div>           
	             
	      </div>	      
	    </div> 	    
	  </div>
	</body>	
<script src="resources/js/application/models/initializations.js"></script>
<script src="resources/js/application/initialization/offices.js"></script>
</html>