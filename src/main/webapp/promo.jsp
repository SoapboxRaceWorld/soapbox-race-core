<html>
<head></head>
<body>
	<form action="Engine.svc/PromoCode/createPromoCode" method="post">
		<input type="hidden" name="promoCodeToken"
			value="<%=request.getParameter("promoCodeToken")%>" /> <input
			type="submit" value="Generate Promo Code!" />
	</form>
</body>
</html>