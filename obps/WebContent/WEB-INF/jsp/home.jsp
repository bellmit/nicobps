<html>
	<head>
		<title>OBPS | Home</title>
		<%@include file="common/headerfiles.jsp" %>     		
	</head>
	<body>
	<div class="d-flex" id="wrapper">
		<%@include file="common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4" style="font-size: 32px;">Home</h3>
				<div class="row">
					<div class="container">
						<core:if test="${processess != null }">
							<div class="row">
								<core:forEach items="${processess}" var="process">
									<div class="col-lg-12 col-sm-12 p-3 b-customize">
										<div class="bg-light p-4 b-dbcard">
											<i
												class='fas ${(process.parenticon!=null)?process.parenticon:"fa-info-circle"} position-absolute'
												style="font-size: 35px; right: 40px; top: 40px;"></i>
											<div class="row">
												<h5 class="col-lg-4 text-left font-weight-bold">${process.officename1}</h5>
												<div class="col-lg-4">
													<h5 class="text-left font-weight-bold"
														style="padding-top: 3px; color: #005776">${process.flowname}</h5>
													<div>
														<span style="font-size: 13px;"> <a
															href='${(process.pageurl!=null)?process.pageurl:"#"}?applicationcode=${process.applicationcode}&officecode=${process.officecode}'>
																${process.parent } </a>
														</span>
													</div>
												</div>
												<div class="col-lg-4">
													<div class='text-left'>
														Application No. -<span class='font-weight-bold'>${process.applicationcode }</span>
													</div>
													<div class='text-left'>
														Application Date. -<span class='font-weight-bold'>
															${process.entrydate }</span>
													</div>
												</div>
											</div>
										</div>
									</div>
								</core:forEach>
							</div>
						</core:if>
						
						
 						<core:if test="${listUserApplications.size()>0 || listStakeholderApplications.size()>0}">	
 							<h5>Click to perform an action</h5>		
						</core:if>							
						
						<core:if test="${listStakeholderApplications.size()>0}">						
	 						<div class="row text-center pl-4" id="sortable-cards"> 						 
	 							<core:forEach items="${listStakeholderApplications}" var="app">
	 						    <div class="col-lg-3 col-sm-12 p-3 b-customize"> 
	 						        <div class="bg-light p-4 b-dbcard"> 
	 						        	<!--<i class="fas fa-user-graduate position-absolute" style="font-size:35px; right: 40px; top: 40px;"></i>-->  
	 						        	<div class="">  
	 						        		<p class="text-left font-weight-bold" style="font-size: 14px;">
	 						        			<a href="srverify.htm?processcode=${app.processcode}">${app.processname}</a>
	 						        		</p> 
	 						        		<h3 class="text-left font-weight-bold" style="margin-top: -5px">${app.totalac}</h3> 
	 						        		<!--
	 						        		 <div class="text-left" style="margin: 10px 0px 5px;"> 
	 						        			<span class="badge badge-success">+4%</span>  
	 						        			<span style="font-size:13px;"> From previous period</span> 
	 						        		 </div> 
	 						        		--> 						        		
	 						        	</div> 
	 						        </div> 
	 						    </div> 
								</core:forEach>
	 						</div> 
 						</core:if>			
	
						<core:if test="${listUserApplications.size()>0}">									
 						<div class="row text-center pl-4" id="sortable-cards"> 						 
 							<core:forEach items="${listUserApplications}" var="app">
 						    <div class="col-lg-3 col-sm-12 p-3 b-customize"> 
 						        <div class="bg-light p-4 b-dbcard">  						        	 
 						        	<div class="">  
 						        		<p class="text-left font-weight-bold" style="font-size: 14px;">
 						        			<a href="bpainbox.htm?processcode=${app.processcode}">${app.processname}</a>
 						        		</p> 
 						        		<h3 class="text-left font-weight-bold" style="margin-top: -5px">${app.totalac}</h3> 					        		
 						        	</div> 
 						        </div> 
 						    </div> 
							</core:forEach>
 						</div> 
 						</core:if> 						
						
						
					
						
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@include file="./common/footer.jsp" %> 
	</body>
</html>