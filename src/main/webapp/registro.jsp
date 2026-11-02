<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%

    String ctx   = request.getContextPath();

    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Crear cuenta | Arena UAA</title>
<link rel="stylesheet" href="<%= ctx %>/css/estilos.css?v=2">
</head>
<body class="auth" style="background:#0A2C6B url('<%= ctx %>/img/fondo_login.jpeg') center center / cover no-repeat fixed;">
  <div class="auth-card">
    <div class="auth-band" style="background:#0E3A8C;text-align:center;padding:20px;">
      <img src="<%= ctx %>/img/gallos-eagle.png" alt="GALLOS eSports" style="width:230px;max-width:80%;height:auto;display:block;margin:0 auto;">
    </div>
    <div class="auth-stripe" style="height:6px;margin:0 -30px 22px;background:linear-gradient(90deg,#0E3A8C 0%,#1E56C9 45%,#C8102E 100%);"></div>
    <div class="auth-sub">Crear una cuenta de administrador</div>

    <% if (error != null) { %><div class="alert alert-error"><%= error %></div><% } %>

    <form method="post" action="<%= ctx %>/registro">
      <div class="field">
        <label>Nombre completo</label>

        <input type="text" name="nombre" required autofocus>
      </div>
      <div class="field">
        <label>Correo electronico</label>

        <input type="email" name="correo" required>
      </div>
      <div class="field">
        <label>Usuario</label>
        <input type="text" name="usuario" required>
      </div>
      <div class="field">
        <label>Contrasena (minimo 4 caracteres)</label>
        <input type="password" name="contrasena" required>
      </div>
      <button class="btn btn-primary" type="submit">Crear cuenta</button>
    </form>

    <div class="auth-foot">Ya tienes cuenta? <a href="<%= ctx %>/login">Inicia sesion</a></div>
  </div>
</body>
</html>
