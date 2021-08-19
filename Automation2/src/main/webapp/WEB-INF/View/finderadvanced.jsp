<%@ page contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="jstl"   %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FINDER-ADVANCED</title>
    <style><%@include file="style/styleFinderAdvanced.css"%></style>
</head>
<body>
<center>
    <h1 id="headerMain">ADVANCED Search</h1>
    <hr/><br/>
    <spring:form method="get" action="/searchUserByAttrs">
        <table id="tabMain">
            <tr>
                <td>UserName</td>
                <td class="tabData">
                    <spring:input path="userName" cssClass="springInput"/>
                </td>
            </tr>
            <tr>
                <td>UserAge</td>
                <td class="tabData">
                    <spring:input path="userAge" cssClass="springInput"/>
                </td>
            </tr>
            <tr>
                <td>UserEmail</td>
                <td class="tabData">
                    <spring:input path="userEmail" cssClass="springInput"/>
                </td>
            </tr>
            <tr>
                <td>UserGender</td>
                <td class="tabData">
                    <spring:select path="userGenderText" cssStyle="width: 51%">
                        <spring:options items="${genderListJSP}" />
                    </spring:select>
                </td>
            </tr>
        </table>
        <br/>
        <input id="btnSearchCommitAttr" type="submit" value="Search" />
    </spring:form>
</center>
</body>
</html>
