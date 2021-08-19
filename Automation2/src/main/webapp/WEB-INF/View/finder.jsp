<%@ page contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="jstl"   %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FINDER</title>
    <style><%@include file="style/styleFinder.css"%></style>
</head>
<body>
<center>
    <h1 id="headerMain">Search</h1>
    <hr/><br/>
    <table id="tabMain">
        <tr><td>
            <spring:form method="get" action="/searchUserById">
                <table class="tabForm">
                    <tr><td class="tabData">UUID</td></tr>
                    <tr class="tabRow"><td class="tabData">
                        <center>
                            <spring:input path="uuidText" cssClass="springInput" />
                        </center>
                    </td></tr>
                    <tr><td class="tabData">
                        <input id="btnSearchCommitId" type="submit" value="Search">
                    </td></tr>
                </table>
                <br/>
            </spring:form>
        </td></tr>
        <tr></tr>
        <tr><td>
            <spring:form method="get" action="/searchUserByAge">
                <table class="tabForm">
                    <tr><td class="tabData">UserAge</td></tr>
                    <tr class="tabRow"><td class="tabData">
                        <center>
                            <spring:input path="userAge" cssClass="springInput" />
                        </center>
                    </td></tr>
                    <tr><td class="tabData">
                        <input id="btnSearchCommitAge" type="submit" value="Search">
                    </td></tr>
                </table>
                <br/>
            </spring:form>
        </td></tr>
        <tr></tr>
        <tr><td>
            <spring:form method="get" action="/searchUserByName">
                <table class="tabForm">
                    <tr><td class="tabData">UserName</td></tr>
                    <tr class="tabRow"><td class="tabData">
                        <center>
                            <spring:input path="userName" cssClass="springInput" />
                        </center>
                    </td></tr>
                    <tr><td class="tabData">
                        <input id="btnSearchCommitName" type="submit" value="Search">
                    </td></tr>
                </table>
                <br/>
            </spring:form>
        </td></tr>
        <tr></tr>
        <tr><td>
            <spring:form method="get" action="/searchUserByEmail">
                <table class="tabForm">
                    <tr><td class="tabData">UserEMail</td></tr>
                    <tr class="tabRow"><td class="tabData">
                        <center>
                            <spring:input path="userEmail" cssClass="springInput" />
                        </center>
                    </td></tr>
                    <tr><td class="tabData">
                        <input id="btnSearchCommitEmail" type="submit" value="Search">
                    </td></tr>
                </table>
                <br/>
            </spring:form>
        </td></tr>
    </table>
    <br/><br/>
    <a href="/gotoSearchUserAdvanced">
        <button>Advanced</button>
    </a>
</center>
</body>
</html>
