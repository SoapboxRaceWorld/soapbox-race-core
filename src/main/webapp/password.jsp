<html>
<head></head>
<body>
	<form action="Engine.svc/RecoveryPassword/resetPassword" method="post">
		New password: <input type="password" name="password" /><br />
		Confirm new password: <input type="password" name="passwordconf" /><br />
		<input type="hidden" name="randomKey" value="<%= request.getParameter("randomKey") %>" />
		<input type="submit" value="Change password" />
	</form>
</body>
</html>