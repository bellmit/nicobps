<!DOCTYPE html>
<html lang="en">
<head>
	<title>OBPS | Upload Enclosures</title>
	<!-- <meta charset="utf-8"> -->
    <meta name="viewport" content="width=device-width, initial-scale=1">        
    <%@include file="../common/headerfiles.jsp" %>     
	<script src="resources/js/application/bpa/uploadbpaenclouseres.js"></script>  		  	  
</head>

<body>
	<%@include file="../common/headerext.jsp" %> 

	<!-- Breadcrumb -->
	<div class="bg-light">
		<div class="container">
			<ul class="breadcrumb bg-light pl-0">
				<li><a href="login.htm">Home</a></li>
				<li>Upload Bpa Enclosures</li>
			</ul>
		</div>
	</div>

	<div class="mb-5">
		<div class="container">		
			<div class="mt-5">
				<%@include file="uploadbpaenclosures.jsp" %> 				 
			</div>
		</div>
	</div>
	

	<%@include file="../common/footerext.jsp" %> 
</body>
</html>


