<html>
	<head>
		<title>OBPS | Profile</title>
		<%@include file="common/headerfiles.jsp" %>     		
	</head>
	<body  ng-app="CommonApp" >
	<div class="d-flex" id="wrapper">
		<%@include file="common/menuside.jsp" %>  		   
	    <div id="page-content-wrapper">	
		  <%@include file="common/menutop.jsp" %>     
	      <div class="container-fluid">
	        <h3 class="mt-4" style="font-size:32px;">Profile</h3>
	        <form:form class="ng-scope" >
	        <div class="row">	        
	        	<!-- ====================Dynamic Page Content Will Come Starts====================== -->     	
	        	<div class="col-md-12 py-4 px-5">
	        		<!-- <h3>My Content-1</h3> -->
	        		<div>
	        			<table id="entrytable" style="width:60%">
			     			<tr>	
			     				<td  style=""width:20%">			     								     				
									<label>Name</label>                                           										
		     					</td>				  
			     				<td style=""width:80%">			     				
				     				${userdetails.fullname}									
		     					</td>				     		
			     			</tr>	        				        			
		        			<core:if  test="${not empty userdetails.office}">	
				     			<tr>	
				     				<td>			     								     				
										<label>Office</label>                                           										
			     					</td>				  
				     				<td>			     				
					     				${userdetails.office}									
			     					</td>				     		
				     			</tr>		        					        			
	        				</core:if> 				     		
			     			<tr>	
			     				<td>			     								     				
									<label>User Name/Email</label>                                           										
		     					</td>				  
			     				<td>			     				
				     				${userdetails.username}									
		     					</td>				     		
			     			</tr>	
		
			     			<tr>	
			     				<td>			     								     				
									<label>Mobile No.</label>                                           										
		     					</td>				  
			     				<td>			     				
				     				(+91) ${userdetails.mobileno}									
		     					</td>				     		
			     			</tr>	
		        			<core:if  test="${not empty userdetails.designation}">	
				     			<tr>	
				     				<td>			     								     				
										<label>Designation</label>                                           										
			     					</td>				  
				     				<td>			     				
					     				${userdetails.designation}									
			     					</td>				     		
				     			</tr>		        					        			
	        				</core:if> 			
							<core:if  test="${not empty userdetails.presentaddress}">	
				     			<tr>	
				     				<td>			     								     				
										<label>Present Address</label>                                           										
			     					</td>				  
				     				<td>			     				
					     				${userdetails.presentaddress}									
			     					</td>				     		
				     			</tr>		        					        			
	        				</core:if> 	
							<core:if  test="${not empty userdetails.permanentaddress}">	
				     			<tr>	
				     				<td>			     								     				
										<label>Permanent Address</label>                                           										
			     					</td>				  
				     				<td>			     				
					     				${userdetails.permanentaddress}									
			     					</td>				     		
				     			</tr>		        					        			
	        				</core:if>	        				
			     		</table>				
	        		</div>
	        	</div>		
	        	<!-- <div class="col-md-12 py-4 px-5">
	                <h2>My Content-2</h2>	        	
					<p class="lead">Keep your page content here</p>
	        	</div> -->	 	        	       	        
        		<!-- ====================Dynamic Page Content Will Come Ends====================== -->	        	
	        </div>   	        
	        </form:form>     
	      </div>	      
	    </div> 	    
	  </div>
	  
	<%@include file="common/footer.jsp" %> 
	</body>
</html>