<%@ page contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="jstl"   %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Database Rows</title>
</head>
<style><%@include file="style/styleListView.css"%></style>
<body>
<center>
    <h1>ListView</h1>
    <hr/><br/>
    <div style="display: inline-block">
        <h3 id="headerMain">Sorting Options</h3>
        <a href="/sortByUserAge">
            <button class="btnFilterOptions">Age</button></a>
        <a href="/sortByUserName">
            <button class="btnFilterOptions">Name</button></a>
        <a href="/sortByUserGender">
            <button class="btnFilterOptions">Gender</button></a>
        <a href="/sortByUserActive">
            <button class="btnFilterOptions">Activation</button></a>
        <a href="/clearFilter">
            <button class="btnFilterOptions">Unordered</button></a>
        <a href="/gotoSearchUser">
            <button class="btnFilterOptions">Search</button></a>
    </div>
    <br/><br/>
    <div style="display: inline-block; width: 66%">
        <a href="/gotoAppendUser">
            <button id="btnAddUser">Append</button>
        </a>
        <a href="/gotoHomePage">
            <button id="btnHomePage">Starter</button>
        </a>
    </div>
    <br/><br/><br/>
    <table>
        <tr>
            <th class="tabHead">UUID</th>
            <th class="tabHead">Name</th>
            <th class="tabHead">EMail</th>
            <th class="tabHead">Age</th>
            <th class="tabHead">Gender</th>
            <th class="tabHead">Active</th>
            <th colspan="2" class="tabHead">Option</th>
        </tr>
        <jstl:forEach var="user" items="${userListServlet}">
            <tr class="trMember">
                <td>${user.uuidText}</td>
                <td>${user.userName}</td>
                <td>${user.userEmail}</td>
                <td>${user.userAge}</td>
                <td>${user.userGenderText}</td>
                <jstl:choose>
                    <jstl:when test="${user.isActiveInt eq 0}">
                        <td style="background: #ffe1fa">${user.isActiveInt}</td>
                    </jstl:when>
                    <jstl:otherwise>
                        <td>${user.isActiveInt}</td>
                    </jstl:otherwise>
                </jstl:choose>
                <td>
                    <a href="/gotoModifyUser/${user.uuid}">
                        <button id="btnUpUser">Modify</button>
                    </a>
                </td>
                <td>
                    <a href="/gotoRemoveUser/${user.uuid}">
                        <button id="btnDelUser">Remove</button>
                    </a>
                </td>
            </tr>
        </jstl:forEach>
    </table>
    <br/><br/><br/>
</center>
</body>
</html>
