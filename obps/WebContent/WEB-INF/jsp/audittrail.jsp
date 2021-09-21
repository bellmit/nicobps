<html>
	<head>
		<title>OBPS | Audit Trail</title>
		<%@include file="common/headerfiles.jsp" %>     		
	</head>
	<body>
	<div class="d-flex" id="wrapper">
		<%@include file="common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4" style="font-size: 32px;">Audit Trail</h3>
				<div class="row">
					<div class="container">
                        <table class="datatable">
                            <thead>
                                <tr>                                                                          
                                    <th>User Id</th>                                        
                                    <th>Action</th>                                        
                                    <th>Page Accessed</th>                                        
                                    <th>Browser/OS</th>                                        
                                    <th>Ip Address</th>                                        
                                    <th>Date-Time</th>                                        
                                </tr>							                                   
                            </thead>
                            <tbody>
                            	<core:forEach items="${listAuditrail}" var="A">
                                <tr>
                                    <td>${A.userid}</td>
                                    <td>${A.actiontaken}</td>
                                    <td>${A.pageurl}</td>
                                    <td>${A.browser} - ${A.os}</td>
                                    <td>${A.ipaddress}</td>
                                    <td>${A.entrydate}$</td>
                                </tr>
                                </core:forEach>
                            </tbody>                                
                        </table> 					
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@include file="./common/footer.jsp" %> 
	</body>
</html>