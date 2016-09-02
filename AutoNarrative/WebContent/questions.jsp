<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function openPage(pageName){
	window.location.href=pageName;
}
</script>

<title>Questions</title>
</head>
<body>

  <input type="checkbox" name="question1" value="question1">Case is Serious<br>
  <input type="checkbox" name="question2" value="question2">Case is not Serious<br>
  <input type="button" value="Submit" onclick="openPage('narrativeEditor.jsp')">


</body>
</html>