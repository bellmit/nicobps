<html>
	<head>
		<title>OBPS | Create Occupancies </title>
		<%@include file="../common/headerfiles.jsp" %>     		
		<script src="resources/js/util/sha256.min.js"></script>
	</head>
	<body>
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp" %>  		   
	    <div id="page-content-wrapper">	
		  <%@include file="../common/menutop.jsp" %>     
	      <div class="container-fluid">
	        <h3 class="mt-4" style="font-size:32px;">Create Occupancies</h3>
	        
	        <div class="row" ng-app="CommonApp">	         	
	        	<div class="col-md-12 py-12 px-12">
	        		<div class='containerBody' id="createoccupanciesCtrl" ng-controller="createoccupanciesCtrl">            
			            <div  style='width:100%;margin: 15px auto 0'>
			                <form id="occupancyForm" name="occupancyForm">
			                    <table class="entrytable" style="width:70%;margin: 0px auto; border-spacing: 10px"> 
			                        
			                        <tr class="form-group has-feedback">
			                            <td class="title">Occupancy Code:*</td>
			                            <td class="col-xs-5 selectContainer">
											<input type="text" class="form-control" id="occupancycode" name="occupancycode" ng-disabled="IsDisabled" maxlength="99"
												ng-model="occupancy.occupancycode" required autocomplete="off"/>        
											<span id="occupancycodeMsg"></span>
<!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
			                            </td>
			                        </tr>
			                        <tr class="form-group has-feedback">
			                            <td class="title">Occupancy Name:*</td>
			                            <td class="col-xs-5 selectContainer">
											<input type="text" class="form-control" id="occupancyname" name="occupancyname" maxlength="99"
												ng-model="occupancy.occupancyname" required autocomplete="off"/>        
											<span id="occupancynameMsg"></span>
<!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
			                            </td>
			                        </tr>
			                        <tr class="form-group has-feedback">
			                            <td class="title">Occupancy Alias:*</td>
			                            <td class="col-xs-5 selectContainer">
											<input type="text" class="form-control" id="occupancyalias" name="occupancyalias" maxlength="99"
												ng-model="occupancy.occupancyalias" required autocomplete="off"/>        
											<span id="occupancyaliasMsg"></span>
<!-- 											<span class="alert alert-danger" ng-show="!userForm.username.$pristine && userForm.username.$invalid"> Required</span> -->
			                            </td>
			                        </tr>
			                       
			                        <tr class="form-group has-feedback">
			                            <td colspan="2" align="center">
			                                <button type="submit" id="add" ng-click="save()" class="btn btn-primary b-btn"  ng-if="actionButton === 1" ng-disabled="userForm.$invalid">Save</button>
			                                <button name="submit" id="add" ng-click="update()" class="btn btn-primary b-btn"  ng-if="actionButton === 2" ng-disabled="userForm.$invalid">Update</button>
			                                <input type="reset" value="Reset" ng-click="reset()"class="btn btn-secondary" />
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
  <script src="resources/js/application/initialization/initoccupancies.js"></script>
</html>