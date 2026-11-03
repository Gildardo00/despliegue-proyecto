<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, mx.uaa.arena.model.Jugador, mx.uaa.arena.model.Equipo, mx.uaa.arena.model.JuegoDisciplina" %>
<% String titulo = "Jugadores"; String activo = "jugadores"; %>
<%@ include file="/partials/header.jsp" %>
<%
    Jugador j = (Jugador) request.getAttribute("jugador");
    List<Equipo> equipos = (List<Equipo>) request.getAttribute("equipos");
    List<JuegoDisciplina> disciplinas = (List<JuegoDisciplina>) request.getAttribute("disciplinas");
    String error = (String) request.getAttribute("error");
    boolean edicion = (j != null);
    int jJuego = (edicion && j.getIdJuego() != null) ? j.getIdJuego() : 0;
    int jEquipo = (edicion && j.getIdEquipo() != null) ? j.getIdEquipo() : 0;
    String catActual = edicion ? j.getCategoria() : "Universitaria";
    String ramaActual = edicion ? j.getRama() : "Mixto";
    String[] cats = {"Universitaria", "Libre", "Juvenil"};
    String[] ramas = {"Varonil", "Femenil", "Mixto"};
%>
<div class="section-title"><%= edicion ? "Editar jugador" : "Nuevo jugador" %></div>
<% if (error != null) { %><div class="alert alert-error"><%= error %></div><% } %>
<div class="form-card">
  <form method="post" action="<%= ctx %>/jugadores">
    <% if (edicion) { %><input type="hidden" name="idJugador" value="<%= j.getIdJugador() %>"><% } %>
    <div class="form-grid">
      <div class="field">
        <label>Nombre completo</label>
        <input type="text" name="nombreCompleto" required value="<%= edicion ? esc(j.getNombreCompleto()) : "" %>">
      </div>
      <div class="field">
        <label>Disciplina</label>
        <select name="idJuego" required>
          <option value="0">-- Selecciona --</option>
          <% if (disciplinas != null) for (JuegoDisciplina d : disciplinas) { %>
            <option value="<%= d.getIdJuego() %>" <%= jJuego == d.getIdJuego() ? "selected" : "" %>><%= esc(d.getNombreDisciplina()) %></option>
          <% } %>
        </select>
      </div>
      <div class="field">
        <label>Equipo (opcional)</label>
        <select name="idEquipo">
          <option value="0">-- Sin equipo (bolsa) --</option>
          <% if (equipos != null) for (Equipo eq : equipos) { %>
            <option value="<%= eq.getIdEquipo() %>" <%= jEquipo == eq.getIdEquipo() ? "selected" : "" %>><%= esc(eq.getNombreEquipo()) %></option>
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
        <label>Edad</label>
        <input type="number" name="edad" min="10" max="99" value="<%= (edicion && j.getEdad() != null) ? j.getEdad() : "" %>">
      </div>
    </div>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary"><%= edicion ? "Guardar cambios" : "Registrar" %></button>
      <a href="<%= ctx %>/jugadores" class="btn btn-ghost">Cancelar</a>
    </div>
  </form>
</div>
<%@ include file="/partials/footer.jsp" %>
