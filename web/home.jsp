<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sql:query var="listCustomer" dataSource="jdbc/vakamisu">
    select * from customer;
</sql:query>

<html>
<head>
    <title>DataSource</title>
</head>
<body>
<table border="1">
    <caption><h2>List of users</h2></caption>

    <thead>
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="customer" items="${listCustomer.rows}">
        <tr>
            <td><c:out value="${customer.id}"/></td>
            <td><c:out value="${customer.first_name}"/></td>
            <td><c:out value="${customer.last_name}"/></td>
            <td><c:out value="${customer.email}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
