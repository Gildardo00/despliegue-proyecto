<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, mx.uaa.arena.model.Jornada, mx.uaa.arena.model.Ronda" %>
<% String titulo = "Jornadas"; String activo = "jornadas"; %>
<%@ include file="/partials/header.jsp" %>
<%
    Jornada j = (Jornada) request.getAttribute("jornada");
    List<Ronda> rondas = (List<Ronda>) request.getAttribute("rondas");
    boolean edicion = (j != null);
%>
<div class="section-title"><%= edicion ? "Editar jornada" : "Nueva jornada" %></div>
<div class="form-card">
  <form method="post" action="<%= ctx %>/jornadas">
    <% if (edicion) { %><input type="hidden" name="idJornada" value="<%= j.getIdJornada() %>"><% } %>
    <div class="form-grid">
      <div class="field">
        <label>Numero de jornada</label>
        <input type="number" name="numeroJornada" min="1" required
               value="<%= edicion ? String.valueOf(j.getNumeroJornada()) : "1" %>">
      </div>
      <div class="field">
        <label>Ronda (Contener)</label>
        <select name="idRonda" required>
          <% if (rondas != null) for (Ronda r : rondas) {
               boolean sel = edicion && j.getIdRonda() == r.getIdRonda(); %>
            <option value="<%= r.getIdRonda() %>" <%= sel ? "selected" : "" %>><%= esc(r.getNombreRonda()) %></option>
          <% } %>
        </select>
      </div>
      <div class="field">
        <label>Fecha de inicio</label>
        <input type="date" name="fechaInicio" required value="<%= edicion ? j.getFechaInicio() : "" %>">
      </div>
      <div class="field">
        <label>Fecha de fin (opcional)</label>
        <input type="date" name="fechaFin" value="<%= (edicion && j.getFechaFin()!=null) ? j.getFechaFin() : "" %>">
      </div>
    </div>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary"><%= edicion ? "Guardar cambios" : "Registrar" %></button>
      <a href="<%= ctx %>/jornadas" class="btn btn-ghost">Cancelar</a>
    </div>
  </form>
</div>
<%@ include file="/partials/footer.jsp" %>
