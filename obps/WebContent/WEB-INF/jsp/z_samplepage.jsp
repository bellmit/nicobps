<html>
	<head>
		<title>OBPS | Sample</title>
		<%@include file="common/headerfiles.jsp" %>     		
	</head>
	<body  ng-app="CommonApp" >
	<div class="d-flex" id="wrapper">
		<%@include file="common/menuside.jsp" %>  		   
	    <div id="page-content-wrapper">	
		  <%@include file="common/menutop.jsp" %>     
	      <div class="container-fluid">
	        <h3 class="mt-4" style="font-size:32px;">Sample</h3>
	        
	        <div class="row">	        
	        	<!-- ====================Dynamic Page Content Will Come Starts====================== -->     	
	        	<div class="col-md-12 py-4 px-5">
	        		<h3>My Content-1</h3>
	        		<div>
	        			<p><strong>Keep your page content here</strong></p>						
	        		</div>
	        	</div>		
	        	<div class="col-md-12 py-4 px-5">
	                <h2>My Content-2</h2>	        	
					<p class="lead">Keep your page content here</p>
	        	</div>	 	        	       	        
        		<!-- ====================Dynamic Page Content Will Come Ends====================== -->	        	
	        </div>   	        
	             
	      </div>	      
	    </div> 	    
	  </div>
	  
	<%@include file="common/footer.jsp" %> 
	</body>
</html>