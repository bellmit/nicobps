<%     
     session.invalidate();        
     
     response.setHeader("Cache-Control","no-cache"); 
     response.setHeader("Cache-Control","no-store"); 
     response.addHeader("Cache-Control", "must-revalidate");
     response.setHeader("Pragma","no-cache"); 
     response.setDateHeader ("Expires", -1);
     response.sendRedirect("login.htm");
%>
