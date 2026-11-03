<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, mx.uaa.arena.model.Equipo" %>
<% String titulo = "Equipos"; String activo = "equipos"; %>
<%@ include file="/partials/header.jsp" %>
<%
    List<Equipo> lista = (List<Equipo>) request.getAttribute("lista");
    String err = request.getParameter("error");
%>
<div class="toolbar">
  <div class="section-title" style="margin:0">Equipos registrados</div>
  <a class="btn btn-primary" href="<%= ctx %>/equipos?accion=nuevo">+ Nuevo equipo</a>
</div>

<% if ("rel".equals(err)) { %>
  <div class="alert alert-error">No se puede eliminar: este equipo tiene partidos asociados.</div>
<% } %>

<div class="table-wrap">
  <table>
    <thead><tr>
      <th>ID</th><th>Nombre del equipo</th><th>Disciplina</th><th>Categoria</th><th>Rama</th><th>Siembra</th><th>Acciones</th>
    </tr></thead>
    <tbody>
    <% if (lista != null && !lista.isEmpty()) {
         for (Equipo e : lista) {
           String disc = e.getNombreDisciplina() == null ? "Sin asignar" : e.getNombreDisciplina(); %>
      <tr>
        <td><%= e.getIdEquipo() %></td>
        <td><%= esc(e.getNombreEquipo()) %></td>
        <td><span class="badge badge-blue"><%= esc(disc) %></span></td>
        <td><%= esc(e.getCategoria()) %></td>
        <td><%= esc(e.getRama()) %></td>
        <td><%= e.getSeed() == 0 ? "-" : e.getSeed() %></td>
        <td><div class="acciones">
          <a class="btn btn-edit btn-sm" href="<%= ctx %>/equipos?accion=editar&id=<%= e.getIdEquipo() %>">Editar</a>
          <a class="btn btn-danger btn-sm" href="<%= ctx %>/equipos?accion=eliminar&id=<%= e.getIdEquipo() %>"
             onclick="return confirm('Eliminar este equipo?');">Eliminar</a>
        </div></td>
      </tr>
    <%   }
       } else { %>
      <tr><td colspan="7" class="empty">No hay equipos registrados.</td></tr>
    <% } %>
    </tbody>
  </table>
</div>
<%@ include file="/partials/footer.jsp" %>
