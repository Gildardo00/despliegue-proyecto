<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, mx.uaa.arena.model.Participar, mx.uaa.arena.model.Jugador, mx.uaa.arena.model.Partido" %>
<% String titulo = "Participaciones"; String activo = "participaciones"; %>
<%@ include file="/partials/header.jsp" %>
<%
    Participar pa = (Participar) request.getAttribute("participacion");
    List<Jugador> jugadores = (List<Jugador>) request.getAttribute("jugadores");
    List<Partido> partidos  = (List<Partido>) request.getAttribute("partidos");
    boolean edicion = (pa != null);
%>
<div class="section-title"><%= edicion ? "Editar participacion" : "Nueva participacion" %></div>
<div class="form-card">
  <form method="post" action="<%= ctx %>/participaciones">
    <% if (edicion) { %>
      <input type="hidden" name="editar" value="1">
      <input type="hidden" name="idJugador" value="<%= pa.getIdJugador() %>">
      <input type="hidden" name="idPartido" value="<%= pa.getIdPartido() %>">
    <% } %>
    <div class="form-grid">
      <% if (edicion) { %>
      <div class="field">
        <label>Jugador</label>
        <input type="text" value="<%= esc(pa.getNombreJugador()) %>" disabled>
      </div>
      <div class="field">
        <label>Partido</label>
        <input type="text" value="<%= esc(pa.getEtiquetaPartido()) %>" disabled>
      </div>
      <% } else { %>
      <div class="field">
        <label>Jugador</label>
        <select name="idJugador" required>
          <% if (jugadores != null) for (Jugador j : jugadores) { %>
            <option value="<%= j.getIdJugador() %>"><%= esc(j.getNombreCompleto()) %> (<%= esc(j.getNombreEquipo()) %>)</option>
          <% } %>
        </select>
      </div>
      <div class="field">
        <label>Partido</label>
        <select name="idPartido" required>
          <% if (partidos != null) for (Partido p : partidos) { %>
            <option value="<%= p.getIdPartido() %>">Partido #<%= p.getIdPartido() %> - Jornada <%= p.getNumeroJornada() %> (<%= esc(p.getSede()) %>)</option>
          <% } %>
        </select>
      </div>
      <% } %>
      <div class="field">
        <label>Numero del jugador</label>
        <input type="number" name="numeroP" min="0" required
               value="<%= edicion ? String.valueOf(pa.getNumeroP()) : "0" %>">
      </div>
      <div class="field">
        <label>Goles individuales</label>
        <input type="number" name="golesIndividuales" min="0" required
               value="<%= edicion ? String.valueOf(pa.getGolesIndividuales()) : "0" %>">
      </div>
      <div class="field full">
        <label class="check">
          <input type="checkbox" name="partidaGanada" value="1" <%= (edicion && pa.isPartidaGanada()) ? "checked" : "" %>>
          Partida ganada
        </label>
      </div>
    </div>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary"><%= edicion ? "Guardar cambios" : "Registrar" %></button>
      <a href="<%= ctx %>/participaciones" class="btn btn-ghost">Cancelar</a>
    </div>
  </form>
</div>
<%@ include file="/partials/footer.jsp" %>
