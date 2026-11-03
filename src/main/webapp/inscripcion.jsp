<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, mx.uaa.arena.model.JuegoDisciplina" %>
<%
    String ctx   = request.getContextPath();
    String error = (String) request.getAttribute("error");
    String exito = (String) request.getAttribute("exito");
    Boolean abiertasObj = (Boolean) request.getAttribute("abiertas");
    boolean abiertas = (abiertasObj == null) || abiertasObj;
    List<JuegoDisciplina> disciplinas = (List<JuegoDisciplina>) request.getAttribute("disciplinas");
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Inscripcion de jugadores | Arena UAA</title>
<link rel="stylesheet" href="<%= ctx %>/css/estilos.css?v=2">
</head>
<body class="auth" style="background:#0A2C6B url('<%= ctx %>/img/fondo_login.jpeg') center center / cover no-repeat fixed;">
  <div class="auth-card">
    <div class="auth-band" style="background:#0E3A8C;text-align:center;padding:20px;">
      <img src="<%= ctx %>/img/gallos-eagle.png" alt="GALLOS eSports" style="width:230px;max-width:80%;height:auto;display:block;margin:0 auto;">
    </div>
    <div class="auth-stripe" style="height:6px;margin:0 -30px 22px;background:linear-gradient(90deg,#0E3A8C 0%,#1E56C9 45%,#C8102E 100%);"></div>
    <div class="auth-sub">Inscripcion de participantes al torneo</div>

    <% if (error != null) { %><div class="alert alert-error"><%= error %></div><% } %>
    <% if (exito != null) { %><div class="alert alert-ok"><%= exito %></div><% } %>

    <% if (!abiertas) { %>
      <div class="alert alert-error">Las inscripciones estan cerradas en este momento. Vuelve mas tarde.</div>
      <div class="auth-foot">Eres administrador? <a href="<%= ctx %>/login">Inicia sesion</a></div>
    <% } else { %>
    <form method="post" action="<%= ctx %>/inscripcion">
      <div class="field">
        <label>Nombre completo</label>
        <input type="text" name="nombreCompleto" required autofocus>
      </div>
      <div class="field">
        <label>Disciplina</label>
        <select name="idJuego" required>
          <option value="">-- Selecciona la disciplina --</option>
          <% if (disciplinas != null) for (JuegoDisciplina d : disciplinas) { %>
            <option value="<%= d.getIdJuego() %>"><%= d.getNombreDisciplina() %></option>
          <% } %>
        </select>
      </div>
      <div class="field">
        <label>Categoria</label>
        <select name="categoria" required>
          <option value="Universitaria">Universitaria</option>
          <option value="Libre">Libre</option>
          <option value="Juvenil">Juvenil</option>
        </select>
      </div>
      <div class="field">
        <label>Rama</label>
        <select name="rama" required>
          <option value="Varonil">Varonil</option>
          <option value="Femenil">Femenil</option>
          <option value="Mixto">Mixto</option>
        </select>
      </div>
      <div class="field">
        <label>Edad</label>
        <input type="number" name="edad" min="10" max="99">
      </div>
      <button class="btn btn-primary" type="submit">Enviar inscripcion</button>
    </form>
    <div class="auth-foot">Eres administrador? <a href="<%= ctx %>/login">Inicia sesion</a></div>
    <% } %>
  </div>
</body>
</html>
