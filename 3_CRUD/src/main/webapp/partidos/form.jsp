<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, mx.uaa.arena.model.Partido, mx.uaa.arena.model.JuegoDisciplina, mx.uaa.arena.model.Jornada, mx.uaa.arena.model.Equipo" %>
<% String titulo = "Partidos"; String activo = "partidos"; %>
<%@ include file="/partials/header.jsp" %>
<%
    Partido p = (Partido) request.getAttribute("partido");
    List<JuegoDisciplina> juegos   = (List<JuegoDisciplina>) request.getAttribute("juegos");
    List<Jornada>         jornadas = (List<Jornada>)         request.getAttribute("jornadas");
    List<Equipo>          equipos  = (List<Equipo>)          request.getAttribute("equipos");
    boolean edicion = (p != null);
%>
<div class="section-title"><%= edicion ? "Editar partido" : "Nuevo partido" %></div>
<div class="form-card">
  <form method="post" action="<%= ctx %>/partidos">
    <% if (edicion) { %><input type="hidden" name="idPartido" value="<%= p.getIdPartido() %>"><% } %>
    <div class="form-grid">
      <div class="field full">
        <label>Sede</label>
        <input type="text" name="sede" required value="<%= edicion ? esc(p.getSede()) : "" %>"
               placeholder="Ej. Cancha Central UAA">
      </div>
      <div class="field">
        <label>Disciplina (Definir)</label>
        <select name="idJuego" required>
          <% if (juegos != null) for (JuegoDisciplina jd : juegos) {
               boolean sel = edicion && p.getIdJuego() == jd.getIdJuego(); %>
            <option value="<%= jd.getIdJuego() %>" <%= sel ? "selected" : "" %>><%= esc(jd.getNombreDisciplina()) %></option>
          <% } %>
        </select>
      </div>
      <div class="field">
        <label>Jornada (Organizar)</label>
        <select name="idJornada" required>
          <% if (jornadas != null) for (Jornada jo : jornadas) {
               boolean sel = edicion && p.getIdJornada() == jo.getIdJornada(); %>
            <option value="<%= jo.getIdJornada() %>" <%= sel ? "selected" : "" %>>Jornada <%= jo.getNumeroJornada() %> - <%= esc(jo.getNombreRonda()) %></option>
          <% } %>
        </select>
      </div>
      <div class="field">
        <label>Equipo local (Enfrentar)</label>
        <select name="idEquipoLocal" required>
          <% if (equipos != null) for (Equipo e : equipos) {
               boolean sel = edicion && p.getIdEquipoLocal() == e.getIdEquipo(); %>
            <option value="<%= e.getIdEquipo() %>" <%= sel ? "selected" : "" %>><%= esc(e.getNombreEquipo()) %></option>
          <% } %>
        </select>
      </div>
      <div class="field">
        <label>Equipo visitante (Enfrentar)</label>
        <select name="idEquipoVisitante" required>
          <% if (equipos != null) for (Equipo e : equipos) {
               boolean sel = edicion && p.getIdEquipoVisitante() == e.getIdEquipo(); %>
            <option value="<%= e.getIdEquipo() %>" <%= sel ? "selected" : "" %>><%= esc(e.getNombreEquipo()) %></option>
          <% } %>
        </select>
      </div>
      <div class="field">
        <label>Goles totales local</label>
        <input type="number" name="golesLocal" min="0" required
               value="<%= edicion ? String.valueOf(p.getGolesLocal()) : "0" %>">
      </div>
      <div class="field">
        <label>Goles totales visitante</label>
        <input type="number" name="golesVisitante" min="0" required
               value="<%= edicion ? String.valueOf(p.getGolesVisitante()) : "0" %>">
      </div>
    </div>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary"><%= edicion ? "Guardar resultado" : "Registrar partido" %></button>
      <a href="<%= ctx %>/partidos" class="btn btn-ghost">Cancelar</a>
    </div>
  </form>
</div>
<%@ include file="/partials/footer.jsp" %>
