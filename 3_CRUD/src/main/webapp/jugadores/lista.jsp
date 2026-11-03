<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, mx.uaa.arena.model.Jugador" %>
<% String titulo = "Jugadores"; String activo = "jugadores"; %>
<%@ include file="/partials/header.jsp" %>
<%
    List<Jugador> lista = (List<Jugador>) request.getAttribute("lista");
%>
<div class="toolbar">
  <div class="section-title" style="margin:0">Jugadores / participantes registrados</div>
  <a class="btn btn-primary" href="<%= ctx %>/jugadores?accion=nuevo">+ Nuevo jugador</a>
</div>

<div class="table-wrap">
  <table>
    <thead><tr>
      <th>ID</th><th>Nombre completo</th><th>Disciplina</th><th>Equipo</th><th>Categoria</th><th>Rama</th><th>Edad</th><th>Acciones</th>
    </tr></thead>
    <tbody>
    <% if (lista != null && !lista.isEmpty()) {
         for (Jugador j : lista) {
           String disc = j.getNombreDisciplina() == null ? "-" : j.getNombreDisciplina();
           String eq = j.getNombreEquipo(); %>
      <tr>
        <td><%= j.getIdJugador() %></td>
        <td><%= esc(j.getNombreCompleto()) %></td>
        <td><span class="badge badge-blue"><%= esc(disc) %></span></td>
        <td>
          <% if (eq == null) { %><span class="badge badge-gray">Bolsa</span>
          <% } else { %><span class="badge badge-green"><%= esc(eq) %></span><% } %>
        </td>
        <td><%= esc(j.getCategoria()) %></td>
        <td><%= esc(j.getRama()) %></td>
        <td><%= j.getEdad() == null ? "-" : j.getEdad() %></td>
        <td><div class="acciones">
          <a class="btn btn-edit btn-sm" href="<%= ctx %>/jugadores?accion=editar&id=<%= j.getIdJugador() %>">Editar</a>
          <a class="btn btn-danger btn-sm" href="<%= ctx %>/jugadores?accion=eliminar&id=<%= j.getIdJugador() %>"
             onclick="return confirm('Eliminar este jugador?');">Eliminar</a>
        </div></td>
      </tr>
    <%   }
       } else { %>
      <tr><td colspan="8" class="empty">No hay jugadores registrados.</td></tr>
    <% } %>
    </tbody>
  </table>
</div>
<%@ include file="/partials/footer.jsp" %>
