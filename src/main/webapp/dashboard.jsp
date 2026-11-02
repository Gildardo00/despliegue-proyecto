<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List, mx.uaa.arena.model.Ronda, mx.uaa.arena.model.Partido" %>

<% String titulo = "Dashboard"; String activo = "dashboard"; %>

<%@ include file="/partials/header.jsp" %>
<%

    List<Ronda>   rondas   = (List<Ronda>)   request.getAttribute("rondas");
    List<Partido> partidos = (List<Partido>) request.getAttribute("partidos");
%>

<div class="cards">

  <div class="card accent">
    <div class="label">Juegos / Disciplinas</div>

    <div class="num"><%= request.getAttribute("totalJuegos") %></div>
  </div>
  <div class="card">
    <div class="label">Equipos</div>
    <div class="num"><%= request.getAttribute("totalEquipos") %></div>
  </div>
  <div class="card">
    <div class="label">Jugadores</div>
    <div class="num"><%= request.getAttribute("totalJugadores") %></div>
  </div>
  <div class="card">
    <div class="label">Rondas</div>
    <div class="num"><%= request.getAttribute("totalRondas") %></div>
  </div>
  <div class="card">
    <div class="label">Jornadas</div>
    <div class="num"><%= request.getAttribute("totalJornadas") %></div>
  </div>
  <div class="card">
    <div class="label">Partidos</div>
    <div class="num"><%= request.getAttribute("totalPartidos") %></div>
  </div>
</div>

<div class="section-title">Rondas del torneo</div>
<div class="table-wrap">
  <table>
    <thead>
      <tr><th>Ronda</th><th>Estatus</th></tr>
    </thead>
    <tbody>
    <% if (rondas != null && !rondas.isEmpty()) {
         for (Ronda r : rondas) {
           String est = r.getEstatus();

           String cls = "En curso".equals(est) ? "badge-green"
                      : ("Programada".equals(est) ? "badge-blue" : "badge-gray"); %>
      <tr>

        <td><%= esc(r.getNombreRonda()) %></td>

        <td><span class="badge <%= cls %>"><%= esc(est) %></span></td>
      </tr>
    <%   }
       } else { %>
      <tr><td colspan="2" class="empty">No hay rondas registradas.</td></tr>
    <% } %>
    </tbody>
  </table>
</div>

<div class="section-title">Ultimos partidos</div>
<div class="table-wrap">
  <table>
    <thead>
      <tr><th>Jornada</th><th>Disciplina</th><th>Local</th><th>Marcador</th><th>Visitante</th><th>Sede</th></tr>
    </thead>
    <tbody>
    <% if (partidos != null && !partidos.isEmpty()) {
         for (Partido p : partidos) { %>
      <tr>
        <td>Jornada <%= p.getNumeroJornada() %></td>

        <td><span class="badge badge-blue"><%= esc(p.getNombreDisciplina()) %></span></td>

        <td><%= esc(p.getNombreLocal()) %></td>

        <td><strong><%= p.getGolesLocal() %> - <%= p.getGolesVisitante() %></strong></td>

        <td><%= esc(p.getNombreVisitante()) %></td>
        <td><%= esc(p.getSede()) %></td>
      </tr>
    <%   }
       } else { %>
      <tr><td colspan="6" class="empty">No hay partidos registrados.</td></tr>
    <% } %>
    </tbody>
  </table>
</div>

<%@ include file="/partials/footer.jsp" %>
