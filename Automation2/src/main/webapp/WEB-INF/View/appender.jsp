<%@ page contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="jstl"   %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>APPENDER</title>
    <style><%@include file="style/styleAppender.css"%></style>
</head>
<body>
<center>
    <h1 id="headerMain">Append</h1>
    <hr/><br/>
    <spring:form method="post" action="/addUserCommitForm">
        <table>
            <tr>
                <td>Name:</td>
                <td><spring:input path="userName" /></td>
            </tr>
            <tr>
                <td>EMail:</td>
                <td><spring:input path="userEmail" /></td>
            </tr>
            <tr>
                <td>Age(Year):</td>
                <td><spring:input path="userAge" /></td>
            </tr>
            <tr>
                <td>Gender:
                    <spring:select path="userGenderText">
                        <spring:options items="${genderListJSP}"/>
                    </spring:select>
                </td>
                <td style="text-align: center">Active:
                    <spring:select path="isActiveInt">
                        <spring:options items="${activeListJSP}"/>
                    </spring:select>
                </td>
            </tr>
        </table>
        <br/>
        <input id="btnAddCommit" type="submit" value="Commit">
    </spring:form>
</center>
</body>
</html>
