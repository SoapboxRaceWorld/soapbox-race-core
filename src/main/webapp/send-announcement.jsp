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
    <title>Send Chat Announcement</title>
</head>
<body>
<form action="Engine.svc/SendAnnouncement" method="post">
    Message: <textarea name="message"></textarea>
    <br/> <br/>
    <input type="hidden" name="announcementAuth"
           value="<%=request.getParameter("announcementAuth")%>"/> <br/> <input
        type="submit" value="send announcement"/>
</form>
</body>
</html>
