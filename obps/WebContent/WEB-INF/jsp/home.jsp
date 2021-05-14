<html>
	<head>
		<title>OBPS | Home</title>
		<%@include file="common/headerfiles.jsp" %>     		
	</head>
	<body>
	<div class="d-flex" id="wrapper">
		<%@include file="common/menuside.jsp" %>  		   
	    <div id="page-content-wrapper">	
		  <%@include file="common/menutop.jsp" %>     
	      <div class="container-fluid">
	        <h3 class="mt-4" style="font-size:32px;">Home</h3>	        
	        <div class="row" >	               						
				<div class="container">
					<core:if test="${process != null }">
						<div class="row">
							<div class="col-lg-6 col-sm-12 p-3 b-customize">
						        <a href="${(process.pageurl!=null)?process.pageurl:'#'}" style="text-decoration: none;color:black">
									<div class="bg-light p-4 b-dbcard">
							        	<i class='fas ${(process.parenticon!=null)?process.parenticon:"fa-info-circle"} position-absolute' style="font-size:35px; right: 40px; top: 40px;"></i> 
							        	<div class=""> 
							        		<h4 class="text-left font-weight-bold" style="margin-top: -5px">${process.flowname }</h4>							        		
							      	  	</div>
							        </div>
						        </a>
						    </div>
						</div>
					</core:if>
					<div class="pl-4 text-right" style="font-size: 24px">
						<span class="mr-2" id="one-item-row" style="cursor: pointer;">
							<i class="fas fa-bars"></i>
						</span>
						<span class="mr-2" id="two-item-row" style="cursor: pointer;">
							<i class="fas fa-th-large"></i>
						</span>
						<span class="mr-2" id="three-item-row" style="cursor: pointer;">
							<i class="fas fa-th"></i>
						</span>
					</div>
					<h4 class="text-center mb-3 b-latest-data">Latest Data</h4>					
					<div class="">
						<div class="row text-center pl-4" id="sortable-cards">
						    <div class="col-lg-6 col-sm-12 p-3 b-customize">
						        <div class="bg-light p-4 b-dbcard">
						        	<i class="fas fa-user-graduate position-absolute" style="font-size:35px; right: 40px; top: 40px;"></i> 
						        	<div class=""> 
						        		<p class="text-left font-weight-bold" style="font-size: 14px;">No. of Seats</p>
						        		<h3 class="text-left font-weight-bold" style="margin-top: -5px">1,1870</h3>
						        		<div class="text-left" style="margin: 10px 0px 5px;">
						        			<span class="badge badge-success">+4%</span> 
						        			<span style="font-size:13px;"> From previous period</span>
						        		</div>
						        	</div>
						        </div>
						    </div>
	
						    <div class="col-lg-6 col-sm-12 p-3 b-customize">
						        <div class="bg-light p-4 b-dbcard">
						        	<i class="fas fa-university position-absolute" style="font-size:35px; right: 40px; top: 40px;"></i> 
						        	<div class=""> 
						        		<p class="text-left font-weight-bold" style="font-size: 14px;">Deemed University</p>
						        		<h3 class="text-left font-weight-bold" style="margin-top: -5px">612</h3>
						        		<div class="text-left" style="margin: 10px 0px 5px;">
						        			<span class="badge badge-success">2%</span> 
						        			<span style="font-size:13px;"> From previous period</span>
						        		</div>
						        	</div>
						        </div>
						    </div>
	
						    <div class="col-lg-6 col-sm-12 p-3 b-customize">
						        <div class="bg-light p-4 b-dbcard">
						        	<i class="fas fa-users position-absolute" style="font-size:35px; right: 40px; top: 40px;"></i> 
						        	<div class=""> 
						        		<p class="text-left font-weight-bold" style="font-size: 14px;">Candidates Registered</p>
						        		<h3 class="text-left font-weight-bold" style="margin-top: -5px">152,679</h3>
						        		<div class="text-left" style="margin: 10px 0px 5px;">
						        			<span class="badge badge-success">12%</span> 
						        			<span style="font-size:13px;"> From previous period</span>
						        		</div> 
						        	</div>
						        </div>
						    </div>
	
						    <div class="col-lg-6 col-sm-12 p-3 b-customize">
						        <div class="bg-light p-4 b-dbcard">
						        	<i class="fas fa-question-circle position-absolute" style="font-size:35px; right: 40px; top: 40px;"></i> 
						        	<div class=""> 
						        		<p class="text-left font-weight-bold" style="font-size: 14px;">No. of Queries Received</p>
						        		<h3 class="text-left font-weight-bold" style="margin-top: -5px">39,891</h3>
						        		<div class="text-left" style="margin: 10px 0px 5px;">
						        			<span class="badge badge-success">+28%</span> 
						        			<span style="font-size:13px;"> From previous period</span>
						        		</div>
						        	</div>
						        </div>
						    </div>
	
						</div>
					</div>
	
				</div>	
				<script>
				  $(function() {
				    $("#one-item-row").on("click", function() {
				    	$(".b-customize").addClass("col-lg-12", 300);
				    	$(".b-customize").removeClass("col-lg-4", 300);
				    	$(".b-customize").removeClass("col-lg-6", 300);
				        
				    });
			
				    $("#two-item-row").on("click", function() {
				    	$(".b-customize").addClass("col-lg-6", 300);
				    	$(".b-customize").removeClass("col-lg-4", 300);
				    	$(".b-customize").removeClass("col-lg-12", 300);
				        
				    });
			
				    $("#three-item-row").on("click", function() {
				    	$(".b-customize").addClass("col-lg-4", 300);
				    	$(".b-customize").removeClass("col-lg-6", 300);
				    	$(".b-customize").removeClass("col-lg-12", 300);
				        
				    });
				 
				  });
			  </script>       	
	        </div>   	        	             
	      </div>	      
	    </div> 	    
	  </div>
	</body>
</html>