<html>
	<head>
		<title>OBPS | Create Fee Types</title>
		<%@include file="../common/headerfiles.jsp" %>     		
		<script src="resources/js/util/sha256.min.js"></script>
	</head>
	<body>
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp" %>  		   
	    <div id="page-content-wrapper">	
		  <%@include file="../common/menutop.jsp" %>     
	      <div class="container-fluid">
	        <h3 class="mt-4" style="font-size:32px;">Create Fee Types</h3>
	        
	        <div class="row" ng-app="CommonApp">	         	
	        	<div class="col-md-12 py-12 px-12">
	        		<div class='containerBody' id="createfeetypeCtrl" ng-controller="createfeetypeCtrl">            
			            <div  style='width:100%;margin: 15px auto 0'>
			                <form id="feetypeForm" name="feetypeForm">
			                    <table class="entrytable" style="width:70%;margin: 0px auto; border-spacing: 10px"> 
			                        
			                        <tr class="form-group has-feedback">
			                            <td class="title">Fee Type Description:*</td>
			                            <td class="col-xs-5 selectContainer">
											<input type="text" class="form-control" id="feetypedescription" name="feetypedescription" maxlength="99"
												ng-model="feetype.feetypedescription" required autocomplete="off"/>        
											<span id="feetypedescriptionMsg"></span>
<!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
			                            </td>
			                        </tr>
			                       
			                        <tr class="form-group has-feedback">
			                            <td colspan="2" align="center">
			                                <button type="submit" id="add" ng-click="save()" class="button-primary" ng-if="actionButton === 1" ng-disabled="userForm.$invalid">Save</button>
			                                <button name="submit" id="add" ng-click="update()" class="button-primary" ng-if="actionButton === 2" ng-disabled="userForm.$invalid">Update</button>
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
  <script src="resources/js/application/initialization/initfeetypes.js"></script>
</html>