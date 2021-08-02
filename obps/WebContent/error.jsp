<%@ page import="java.util.HashMap,java.util.Map"%>
<%
	Map<String, String> msg = new HashMap<>();
msg.put("REPORT_STATUS_MISSING", "Status code missing");
msg.put("REPORT_PARAM_MISSING", "Parameter missing");
msg.put("REPORT_VALIDATION_ERROR", "Validation Failed - Incorrect Parameters");
%>
<html>
<head>
<title>Error</title>
</head>
<body>
	
		<h4>
		
		Sorry, there seems to be a problem while we are trying to process your request. 
		<br/>Please try again later.
		<br/>If the problem persists, please contact the Administrator.
		</h4>
	
	<span style="color: red;"><%=(request.getParameter("msg") != null) ? msg.get(request.getParameter("msg").toString()) : ""%></span>
</body>
</html>