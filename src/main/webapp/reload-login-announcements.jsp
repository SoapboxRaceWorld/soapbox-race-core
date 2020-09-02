<%--
  ~ This file is part of the Soapbox Race World core source code.
  ~ If you use any of this code for third-party purposes, please provide attribution.
  ~ Copyright (c) 2020.
  --%>

<%--
  Created by IntelliJ IDEA.
  User: coder
  Date: 1/20/2020
  Time: 11:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reload Login Announcements</title>
</head>
<body>
<p><strong>Note:</strong> This action clears the login announcement cache. It does not immediately display new
    announcements to players already in-game.</p>
<form action="Engine.svc/ReloadLoginAnnouncements" method="post">
    <input type="hidden" name="adminAuth"
           value="<%=request.getParameter("adminAuth")%>"/> <br/> <input
        type="submit" value="Reload login announcements"/>
</form>
</body>
</html>
