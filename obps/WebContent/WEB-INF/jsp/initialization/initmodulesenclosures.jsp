<html>
	<head>
		<title>OBPS | Map Enclosures</title>
		<%@include file="../common/headerfiles.jsp" %>     		
		<script src="resources/js/util/sha256.min.js"></script>
		<style>
			.iconSelection{
				border: 1px solid black; 
				width: 100%; 
				height: 200px; 
				overflow: hidden;overflow-y:scroll; 
				background-color: whitesmoke;
				position: absolute;
				left:0;
				z-index:10;
			}
			.hide{
				display:none;
			}
			.table-condensed td{
				padding-bottom:10px;
			}
		</style> 
	</head>
	<body>
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp" %>  		   
	    <div id="page-content-wrapper">	
		  <%@include file="../common/menutop.jsp" %>     
	      <div class="container-fluid">
	        <h3 class="mt-4" style="font-size:32px;">Map Enclosures</h3>
	        
	        <div class="row" ng-app="CommonApp">	         	
	        	<div class="col-md-12 py-12 px-12">
	        		<div class="containerBody" id="initmodulesenclosuresCtrl" ng-controller="initmodulesenclosuresCtrl">	        		
		        		<form>
		                    <table class="" style="width:100%;margin: 0px auto"> 
		                        <tr class="form-group has-feedback">
		                            <td class="title" style="width: 15%">Module Name :</td>
		                            <td>
		                                <span >{{module.modulename}}</span>
		                            </td>                                     
		                            <td rowspan="15" style="width:65%;border: 1px solid blue;">
		                                <div style="width:100%;max-height:230px;overflow-y: auto ">
		                                    <table border="1" cellspacing="0"width="100%">
		                                        <tr>
		                                            <th></th>
		                                            <th>Enclosure Name</th>    
		                                            <th>Enclosure Description</th>
		                                           
		                                        </tr>
		                                        <tr ng-repeat='item in enclosures track by $index' style="width:100%;">
		                                            <td><input style="margin:8px" type='checkbox' ng-model="item.checked"/></td>
		                                            <td >&nbsp;{{item.enclosurename}}</td>
		                                            <td>&nbsp;{{item.enclosuredescription}}</td>
		                                            
		                                        </tr>
		                                    </table>
		                                </div>
		                            </td>
		                        </tr> <tr><td>&nbsp;</td></tr>
<!-- 		                        <tr> -->
<!-- 		                            <td class="title">Module Description :</td> -->
<!-- 		                            <td > -->
<!-- 		                                <span >{{module.username}}</span> -->
<!-- 		                            </td> -->
<!-- 		                        </tr><tr><td>&nbsp;</td></tr> -->
<!-- 		                        <tr > -->
<!-- 		                            <td class="title">User Access :</td> -->
<!-- 		                            <td> -->
<!-- 		                                <span ng-if='user.enabled=="Y"'>Enabled</span> -->
<!-- 		                                <span ng-if='user.enabled=="F"'>Disabled</span> -->
<!-- 		                            </td> -->
<!-- 		                        </tr><tr><td>&nbsp;</td></tr> -->
<!-- 		                        <tr > -->
		                            <td colspan="2" align="center">
		                                <input type="button" value="Save" ng-click="save()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                                <input type="reset" value="RESET" ng-click="reset()"/>
		                            </td>
		                        </tr><tr><td>&nbsp;</td></tr>
		                        <tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr>
		                    </table>
	                	</form>
	        		</div>		
	        		<div id="displayRecords" style='width:80%;margin: 15px auto 50px auto;'></div>	
	        	</div>			        	       	               	
	        </div>           
	             
	      </div>	      
	    </div> 	    
	  </div>
	</body>	
  <script src="resources/js/application/models/initializations.js"></script>	 
  <script src="resources/js/application/initialization/initmodulesenclosures.js"></script>
</html>