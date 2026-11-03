<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="mx.uaa.arena.model.Ronda" %>
<% String titulo = "Rondas"; String activo = "rondas"; %>
<%@ include file="/partials/header.jsp" %>
<%
    Ronda r = (Ronda) request.getAttribute("ronda");
    boolean edicion = (r != null);
    String estActual = edicion ? r.getEstatus() : "Programada";
    String[] estados = {"Programada", "En curso", "Finalizada"};
%>
<div class="section-title"><%= edicion ? "Editar ronda" : "Nueva ronda" %></div>
<div class="form-card">
  <form method="post" action="<%= ctx %>/rondas">
    <% if (edicion) { %><input type="hidden" name="idRonda" value="<%= r.getIdRonda() %>"><% } %>
    <div class="form-grid">
      <div class="field">
        <label>Nombre de la ronda</label>
        <input type="text" name="nombreRonda" required
               value="<%= edicion ? esc(r.getNombreRonda()) : "" %>"
               placeholder="Ej. Ronda Regular, Cuartos de Final...">
      </div>
      <div class="field">
        <label>Estatus</label>
        <select name="estatus" required>
          <% for (String es : estados) { %>
            <option value="<%= es %>" <%= es.equals(estActual) ? "selected" : "" %>><%= es %></option>
          <% } %>
        </select>
      </div>
    </div>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary"><%= edicion ? "Guardar cambios" : "Registrar" %></button>
      <a href="<%= ctx %>/rondas" class="btn btn-ghost">Cancelar</a>
    </div>
  </form>
</div>
<%@ include file="/partials/footer.jsp" %>
