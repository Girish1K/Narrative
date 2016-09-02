<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ page import="org.apache.wink.json4j.JSONArray"%>  
<%@ page import="org.apache.wink.json4j.JSONObject"%>
<%@ page import="java.io.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
table, th, td {
    border: 1px solid black;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function openPage(pageName){
	window.location.href=pageName;
}
</script>

<title>Insert title here</title>
</head>
<body>
<form method="post" action="UploadServlet" enctype="multipart/form-data">
CaseID	<input type="text" name="caseID" id="caseID" /><br><br>
Upload File<input type="file" name="upload" value="Browse"/><br>
<input type="submit" value="Submit" />     
</form>

<% System.out.println("\n\nIn jsp page==>>\n"+request.getSession().getAttribute("jsonArray"));
if(request.getSession().getAttribute("jsonArray")!=null){
   JSONArray jsonArray=(JSONArray)request.getSession().getAttribute("jsonArray");
   ArrayList<String> keyList=new ArrayList();
   
   for(Object o:jsonArray){
	   JSONObject jsonObject=(JSONObject) o;
	   Iterator keysToCopyIterator =jsonObject.keys();	   
	   while(keysToCopyIterator.hasNext()) {
	       String key = (String) keysToCopyIterator.next();
	       keyList.add(key);
	   }
	break;   
   }   
%>
 <center>
 <%-- ${requestScope.jsonArray} --%>
        <%-- <h2>${requestScope.message}</h2> --%>
 <center><input type="button" id="btn" value="Click Here" onclick="openPage('questions.jsp')"/></center>       
 <table style="width: 100%" >
 <tr>
 <%for(int i=0;i<keyList.size();i++){ %>
 <th><%=keyList.get(i)%></th>
 
 <%} %>
 </tr>
 <%for(Object o:jsonArray){
	  JSONObject jsonObject=(JSONObject) o;
	  %>
	  <tr>
	  <%for(int j=0;j<keyList.size();j++){%>
	    <td><%=jsonObject.get(keyList.get(j)) %></td>	  
	  <%}%>
	  </tr>
	  <%}%>
 
 </table>       
 <%} %>
 </center>
</body>
</html>