<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%

    String ctx   = request.getContextPath();

    String error = (String) request.getAttribute("error");
    String exito = (String) request.getAttribute("exito");
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Iniciar sesion | Arena UAA</title>

<link rel="stylesheet" href="<%= ctx %>/css/estilos.css?v=2">
</head>

<body class="auth" style="background:#0A2C6B url('<%= ctx %>/img/fondo_login.jpeg') center center / cover no-repeat fixed;">

  <div class="auth-card">

    <div class="auth-band" style="background:#0E3A8C;text-align:center;padding:20px;">
      <img src="<%= ctx %>/img/gallos-eagle.png" alt="GALLOS eSports" style="width:230px;max-width:80%;height:auto;display:block;margin:0 auto;">
    </div>

    <div class="auth-stripe" style="height:6px;margin:0 -30px 22px;background:linear-gradient(90deg,#0E3A8C 0%,#1E56C9 45%,#C8102E 100%);"></div>
    <div class="auth-sub">Sistema de Gestion de Torneos</div>

    <% if (error != null) { %><div class="alert alert-error"><%= error %></div><% } %>

    <% if (exito != null) { %><div class="alert alert-ok"><%= exito %></div><% } %>

    <form method="post" action="<%= ctx %>/login">
      <div class="field">
        <label>Usuario</label>

        <input type="text" name="usuario" placeholder="admin" required autofocus>
      </div>
      <div class="field">
        <label>Contrasena</label>

        <input type="password" name="contrasena" placeholder="********" required>
      </div>
      <button class="btn btn-primary" type="submit">Entrar</button>
    </form>

    <div class="auth-foot">No tienes cuenta? <a href="<%= ctx %>/registro">Registrate aqui</a></div>

    <div class="auth-links">Eres jugador? <a href="<%= ctx %>/inscripcion">Inscribete al torneo</a></div>
  </div>
</body>
</html>
