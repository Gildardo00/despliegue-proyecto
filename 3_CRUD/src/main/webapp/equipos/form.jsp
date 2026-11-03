<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, mx.uaa.arena.model.Equipo, mx.uaa.arena.model.JuegoDisciplina" %>
<% String titulo = "Equipos"; String activo = "equipos"; %>
<%@ include file="/partials/header.jsp" %>
<%
    Equipo e = (Equipo) request.getAttribute("equipo");
    boolean edicion = (e != null);
    List<JuegoDisciplina> disciplinas = (List<JuegoDisciplina>) request.getAttribute("disciplinas");
    String error = (String) request.getAttribute("error");
    String catActual = edicion ? e.getCategoria() : "Universitaria";
    String ramaActual = edicion ? e.getRama() : "Mixto";
    String[] cats = {"Universitaria", "Libre", "Juvenil"};
    String[] ramas = {"Varonil", "Femenil", "Mixto"};
%>
<div class="section-title"><%= edicion ? "Editar equipo" : "Nuevo equipo" %></div>
<% if (error != null) { %><div class="alert alert-error"><%= error %></div><% } %>
<div class="form-card">
  <form method="post" action="<%= ctx %>/equipos">
    <% if (edicion) { %><input type="hidden" name="idEquipo" value="<%= e.getIdEquipo() %>"><% } %>
    <div class="form-grid">
      <div class="field full">
        <label>Nombre del equipo</label>
        <input type="text" name="nombreEquipo" required value="<%= edicion ? esc(e.getNombreEquipo()) : "" %>">
      </div>
      <div class="field">
        <label>Disciplina</label>
        <select name="idJuego">
          <option value="0">-- Sin asignar --</option>
          <% if (disciplinas != null) for (JuegoDisciplina d : disciplinas) {
               boolean sel = edicion && e.getIdJuego() == d.getIdJuego(); %>
            <option value="<%= d.getIdJuego() %>" <%= sel ? "selected" : "" %>><%= esc(d.getNombreDisciplina()) %></option>
          <% } %>
        </select>
      </div>
      <div class="field">
        <label>Categoria</label>
        <select name="categoria">
          <% for (String c : cats) { %>
            <option value="<%= c %>" <%= c.equals(catActual) ? "selected" : "" %>><%= c %></option>
          <% } %>
        </select>
      </div>
      <div class="field">
        <label>Rama</label>
        <select name="rama">
          <% for (String r : ramas) { %>
            <option value="<%= r %>" <%= r.equals(ramaActual) ? "selected" : "" %>><%= r %></option>
          <% } %>
        </select>
      </div>
      <div class="field">
        <label>Siembra (ranking, 0 = sin asignar)</label>
        <input type="number" name="seed" min="0" value="<%= edicion ? e.getSeed() : 0 %>">
      </div>
    </div>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary"><%= edicion ? "Guardar cambios" : "Registrar" %></button>
      <a href="<%= ctx %>/equipos" class="btn btn-ghost">Cancelar</a>
    </div>
  </form>
</div>
<%@ include file="/partials/footer.jsp" %>
