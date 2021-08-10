<!DOCTYPE html>
<html lang="en">
<head>
	<title>OBPS | Upload Enclosures</title>
	<!-- <meta charset="utf-8"> -->
    <meta name="viewport" content="width=device-width, initial-scale=1">        
    <%@include file="common/headerfiles.jsp" %>     
	<script src="resources/js/application/uploadenclosures.js"></script>  		  	  
</head>

<body>
	<%@include file="common/headerext.jsp" %> 

	<!-- Breadcrumb -->
	<div class="bg-light">
		<div class="container">
		
			<ul class="breadcrumb bg-light pl-0">
				<li><a href="login.htm">Home</a></li>
				<li>Upload Enclosures</li>
				
			</ul>
		</div>
	</div>

	<div class="mb-5">
		<div class="container">
		<div class="mt-3">
				<h6>Instructions for uploading documents</h6>
				<ul style="font-size:14px;">
				<li>You may upload all the documents now or after <a href="login.htm"><b><u>login</u></b></a>.</li>
				<li>If you choose to upload now, you can either upload one or more documents.
				You can upload the remaining mandatory documents after <a href="login.htm"><b><u>login</u></b></a>.</li>
				</ul>			 
			</div>		
			<div class="mt-3">
				<%@include file="uploadenclosures.jsp" %> 				 
			</div>
			
		</div>
	</div>
	
	
	

	<%@include file="common/footerext.jsp" %> 
</body>
</html>


