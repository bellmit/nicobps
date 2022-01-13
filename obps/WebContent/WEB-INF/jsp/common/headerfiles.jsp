<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" --%>
<%--     pageEncoding="ISO-8859-1"%> --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<sec:csrfMetaTags />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf" content="<core:out value="${_csrf.token}" escapeXml="true"></core:out>"/>
<meta name="_csrf_header" content="<core:out value="${_csrf.headerName}" escapeXml="true"></core:out>"/>
<noscript>
	<meta http-equiv="refresh" content="0; URL=<core:out value="${contextPath}" escapeXml="true"/>/noscript.jsp"/>
</noscript>
<!-- App CSS -->
<link rel="stylesheet" href="resources/css/style.css" rel="stylesheet" type="text/css"/>

<!-- Jquery -->
<script src="resources/js/vendor/jQuery/jquery.min.js"></script> 
<!-- <link rel="stylesheet" href="resources/js/vendor/jQuery/jquery-ui.min.css" /> -->  
<!-- <script src="resources/js/vendor/jQuery/jquery-ui.min.js"></script>  -->

<!-- Angular -->
<script src="resources/js/vendor/angular/angular1.8.min.js"></script> 
<script src="resources/js/vendor/angular/checklist-model.min.js"></script>
 
<!-- Bootstrap Template -->
<link href="resources/js/vendor/bootstrap/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="resources/js/vendor/bootstrap/css/base.css" rel="stylesheet" />
<link href="resources/js/vendor/bootstrap/css/base-responsive.css" rel="stylesheet" />
<link href="resources/js/vendor/bootstrap/css/animate.min.css" rel="stylesheet" />
<link href="resources/js/vendor/bootstrap/css/slicknav.min.css" rel="stylesheet" />
<link href="resources/js/vendor/bootstrap/css/font-awesome.min.css" rel="stylesheet" />
<link href="resources/js/vendor/bootstrap/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<!-- <script src="resources/js/vendor/bootstrap/vendor/jquery/jquery.min.js"></script> -->
<script type="text/javascript" src="resources/js/vendor/bootstrap/vendor/bootstrap/js/popper.js"></script>
<script src="resources/js/vendor/bootstrap/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="resources/js/vendor/bootstrap/vendor/jquery-ui/jquery-ui.js"></script>    
<script src="resources/js/vendor/bootstrap/js/jquery.slicknav.min.js"></script>
<link rel="stylesheet" href="resources/js/vendor/jQuery/fancybox/jquery.fancybox.css" />
<script type="text/javascript" src="resources/js/vendor/jQuery/fancybox/jquery.fancybox.pack.js"></script>

<!-- utility -->
<!-- <script src="resources/js/util/ngdirectives.js"></script> -->
<script src="resources/js/util/patterns.js"></script>
<script src="resources/js/util/utility.js"></script>

<!-- commons -->
<script src="resources/js/commons/commonmodules.js"></script>
<script src="resources/js/commons/commonInitFactory.js"></script>
<script src="resources/js/commons/commonInitService.js"></script>  
<script src="resources/js/commons/commonheader.js"></script>  
<!-- ////////////////////////////////////////////////////////  --> 
 
<!-- Data Table -->
<link rel="stylesheet" href="resources/js/vendor/jQuery/datatable/jquery.dataTables.min.css" /> 
<script src="resources/js/vendor/jQuery/datatable/jquery.dataTables.min.js"></script> 
<!-- Date -->
<!-- <script src="resources/js/vendor/jQuery/date.js"></script> -->

<!-- MsgBox -->
<script src="resources/js/application/jclasses.js"></script>  


<%@include file="ajaxloading.jsp" %>  

<!-- Application -->
<div style="display: none">
	<div id="MsgBox" style="min-width: 200px; height: auto;"
		align="center">
		<p style="margin: 10px auto;">
			<span style="font-style: 14px;"></span>
		</p>
		<div id="msgboxbuttons">
			
		</div>
	</div>
</div>


   



