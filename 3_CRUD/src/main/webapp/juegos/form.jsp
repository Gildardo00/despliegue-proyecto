<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="mx.uaa.arena.model.JuegoDisciplina" %>
<% String titulo = "Juegos / Disciplinas"; String activo = "juegos"; %>
<%@ include file="/partials/header.jsp" %>
<%
    JuegoDisciplina jd = (JuegoDisciplina) request.getAttribute("juego");
    String error = (String) request.getAttribute("error");
    boolean edicion = (jd != null);
    String tipoActual = edicion ? jd.getTipoCompetencia() : "Eliminacion directa";
    String modActual = edicion ? jd.getModalidad() : "Equipos";
    boolean activaActual = !edicion || jd.isActiva();
    String[] tipos = {"Eliminacion directa", "Round Robin"};
    String[] mods = {"Equipos", "Individual"};
%>
<div class="section-title"><%= edicion ? "Editar disciplina" : "Nueva disciplina" %></div>
<% if (error != null) { %><div class="alert alert-error"><%= error %></div><% } %>
<div class="form-card">
  <form method="post" action="<%= ctx %>/juegos">
    <% if (edicion) { %><input type="hidden" name="idJuego" value="<%= jd.getIdJuego() %>"><% } %>
    <div class="form-grid">
      <div class="field">
        <label>Nombre de la disciplina</label>
        <input type="text" name="nombreDisciplina" required
               value="<%= edicion ? esc(jd.getNombreDisciplina()) : "" %>">
      </div>
      <div class="field">
        <label>Tipo de competencia</label>
        <select name="tipoCompetencia">
          <% for (String t : tipos) { %>
            <option value="<%= t %>" <%= t.equals(tipoActual) ? "selected" : "" %>><%= t %></option>
          <% } %>
        </select>
      </div>
      <div class="field">
        <label>Modalidad</label>
        <select name="modalidad">
          <% for (String m : mods) { %>
            <option value="<%= m %>" <%= m.equals(modActual) ? "selected" : "" %>><%= m %></option>
          <% } %>
        </select>
      </div>
      <div class="field">
        <label>Estado</label>
        <label class="check"><input type="checkbox" name="activa" value="1" <%= activaActual ? "checked" : "" %>> Disciplina activa</label>
      </div>
      <div class="field full">
        <label>Regla de puntuacion</label>
        <textarea name="reglaPuntuacion" required placeholder="Ej. Gana el equipo con mas goles..."><%= edicion ? esc(jd.getReglaPuntuacion()) : "" %></textarea>
      </div>
    </div>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary"><%= edicion ? "Guardar cambios" : "Registrar" %></button>
      <a href="<%= ctx %>/juegos" class="btn btn-ghost">Cancelar</a>
    </div>
  </form>
</div>
<%@ include file="/partials/footer.jsp" %>
