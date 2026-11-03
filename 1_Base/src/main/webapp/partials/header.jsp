<%!

    private String esc(String s){
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;")
                .replace(">","&gt;").replace("\"","&quot;");
    }
%>
<%

    mx.uaa.arena.model.Usuario usuarioSesion =
        (mx.uaa.arena.model.Usuario) session.getAttribute("usuario");

    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title><%= titulo %> | Arena UAA</title>

<link rel="stylesheet" href="<%= ctx %>/css/estilos.css?v=2">
</head>
<body>

<div class="layout">

  <aside class="sidebar">

    <img class="brand-logo" src="<%= ctx %>/img/gallos-eagle.png" alt="GALLOS eSports" style="display:block;width:100%;max-width:190px;height:auto;margin:6px auto;">
    <div class="brand-sub">Arena UAA</div>

    <nav class="nav">
      <a href="<%= ctx %>/dashboard"       class="<%= "dashboard".equals(activo)?"active":"" %>">Dashboard</a>
      <a href="<%= ctx %>/juegos"          class="<%= "juegos".equals(activo)?"active":"" %>">Juegos / Disciplinas</a>
      <a href="<%= ctx %>/equipos"         class="<%= "equipos".equals(activo)?"active":"" %>">Equipos</a>
      <a href="<%= ctx %>/jugadores"       class="<%= "jugadores".equals(activo)?"active":"" %>">Jugadores</a>
      <a href="<%= ctx %>/clasificacion"   class="<%= "clasificacion".equals(activo)?"active":"" %>">Clasificacion</a>
      <a href="<%= ctx %>/generar-equipos" class="<%= "generar".equals(activo)?"active":"" %>">Generar equipos</a>
      <a href="<%= ctx %>/torneos"         class="<%= "torneos".equals(activo)?"active":"" %>">Torneos</a>
      <a href="<%= ctx %>/rondas"          class="<%= "rondas".equals(activo)?"active":"" %>">Rondas</a>
      <a href="<%= ctx %>/jornadas"        class="<%= "jornadas".equals(activo)?"active":"" %>">Jornadas</a>
      <a href="<%= ctx %>/partidos"        class="<%= "partidos".equals(activo)?"active":"" %>">Partidos</a>
      <a href="<%= ctx %>/participaciones" class="<%= "participaciones".equals(activo)?"active":"" %>">Participaciones</a>
      <a href="<%= ctx %>/grafica"         class="<%= "grafica".equals(activo)?"active":"" %>">Grafica</a>
      <a href="<%= ctx %>/configuracion"   class="<%= "configuracion".equals(activo)?"active":"" %>">Inscripciones</a>
    </nav>
    <div class="sidebar-foot">Torneos y Disciplinas<br>UAA &copy; 2026</div>
  </aside>

  <div class="main">

    <header class="topbar">
      <div class="topbar-left">
        <h1><%= titulo %></h1>
      </div>
      <div class="userbox">

        <span class="userchip"><%= usuarioSesion != null ? esc(usuarioSesion.getNombre()) : "Invitado" %></span>

        <a class="btn btn-ghost btn-sm" href="<%= ctx %>/logout">Cerrar sesion</a>
      </div>
    </header>

    <main class="content">
