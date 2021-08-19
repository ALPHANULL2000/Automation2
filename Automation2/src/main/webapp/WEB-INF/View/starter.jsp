<%@ page contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="jstl"   %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>WELCOME</title>
    <style><%@include file="style/styleStarter.css"%></style>
</head>
<body>
<center>
    <h1 id="headerTime">${serverTime}</h1>
    <a href="/gotoUserListView">
        <button id="btnStart">LIST</button>
    </a>
</center>
</body>
</html>
