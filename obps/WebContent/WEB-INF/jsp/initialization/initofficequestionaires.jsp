<html>
	<head>
		<title>OBPS | Map Office Questionnaires</title>
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
	        <h3 class="mt-4" style="font-size:32px;">Map Office Questionaires</h3>
	        
	        <div class="row" ng-app="CommonApp">	         	
	        	<div class="col-md-12 py-12 px-12">
	        		<div class="containerBody" id="initofficesquestionairesCtrl" ng-controller="initofficequestionairesCtrl">	        		
		        		<form>
		                    <table class="" style="width:100%;margin: 0px auto"> 
		                        <tr class="form-group has-feedback">
		                            <td class="title" style="width: 15%">Office Name :</td>
		                            <td>
		                                <span >{{office.officename1}}</span>
		                            </td>    
		                                                             
		                            <td rowspan="15" style="width:65%;border: 1px solid blue;">
		                                <div style="width:100%;max-height:300px;overflow-y: auto ">
		                                    <table border="1" cellspacing="0"width="100%">
		                                        <tr>
		                                            <th></th>
		                                            <th>Question Description</th>    
		                                           		                                           
		                                        </tr>
		                                        <tr ng-repeat='item in questionaires track by $index' style="width:100%;">
		                                            <td><input style="margin:8px" type='checkbox' ng-model="item.checked"/></td>
		                                            <td >&nbsp;{{item.questiondescription}}</td>
		                                          
		                                            
		                                        </tr>
		                                    </table>
		                                </div>
		                            </td>
		                        </tr> <tr><td>&nbsp;</td></tr>
		                        
		                     
		                            <td colspan="2" align="center">
		                                <input class="btn btn-primary b-btn" type="button" value="Save" ng-click="save()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                                
		                            </td>
		                        </tr><tr><td>&nbsp;</td></tr>
		                        <tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr>
		                    </table>
	                	</form>
	        		</div>	
	        		<br/>	
	        		<div id="displayRecords" style='width:80%;margin: 15px auto 50px auto;'></div>	
	        	</div>			        	       	               	
	        </div>           
	             
	      </div>	      
	    </div> 	    
	  </div>
	</body>	
  <script src="resources/js/application/models/initializations.js"></script>	 
  <script src="resources/js/application/initialization/initofficequestionaires.js"></script>
</html>