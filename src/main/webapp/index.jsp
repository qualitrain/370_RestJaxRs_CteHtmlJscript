<%@ page contentType="text/html; charset=ISO-8859-1" import="qtx.web.rest.ServicioRest"%>
<html>
<head>
<title>test Rest</title>
</head>
<body>
	<h3>Hay <span id="nArticulos"><%=ServicioRest.getTotArticulos() %></span> art&iacute;culos en inventario</h3>
	<form action="./webapi/articulo" method="post">
		<label for="idClave">Clave:</label> <input id="idClave" type="text"
			name="clave" size="15"><br>
		<label for="idNombre">Nombre:</label> <input id="idNombre" type="text"
			name="nombre" size="30"><br>
		<label for="idDescripcion">Descripci&oacute;n:</label> <input id="idDescripcion" type="text"
			name="descripcion" size="40"><br>
		<label for="idCosto">Costo:</label> <input id="idCosto" type="text"
			name="costo" size="10"><br>
		<input type="submit" value="Dar de alta">
	</form>
	Pruebe la versi&oacute;n <a href="./appCliente/IUcte.html">SPA</a> (Single Page Application) en JavaScript 
</body>
</html>
