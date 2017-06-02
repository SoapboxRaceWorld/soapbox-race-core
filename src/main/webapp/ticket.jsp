<html>
<head></head>
<body>
	<br />
	<br />

	<form action="Engine.svc/CreateTicket" method="post">
		Discord Name: <input type="text" name="discordName" /><br /> <br />
		<input type="hidden" name="ticketAuth"
			value="<%=request.getParameter("ticketAuth")%>" /> <br /> <input
			type="submit" value="create ticket" />
	</form>
	<br />
	<br />
	<br />
	<br />
</body>
</html>
