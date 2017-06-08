<html>
<head></head>
<body>
	<form action="Engine.svc/PromoCode/sendPromoCode" method="post">
		Email: <input type="text" name="email" /><br />
		Password: <input type="password" name="password" /><br />
		<input type="hidden" name="promoCode" value="<%= request.getParameter("promoCode") %>" />
		<input type="submit" value="Get your premium !" />
	</form>
</body>
</html>