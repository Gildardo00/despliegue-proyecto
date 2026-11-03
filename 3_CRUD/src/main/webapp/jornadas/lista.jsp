<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, mx.uaa.arena.model.Jornada" %>
<% String titulo = "Jornadas"; String activo = "jornadas"; %>
<%@ include file="/partials/header.jsp" %>
<%
    List<Jornada> lista = (List<Jornada>) request.getAttribute("lista");
    String err = request.getParameter("error");
%>
<div class="toolbar">
  <div class="section-title" style="margin:0">Jornadas programadas</div>
  <a class="btn btn-primary" href="<%= ctx %>/jornadas?accion=nuevo">+ Nueva jornada</a>
</div>

<% if ("rel".equals(err)) { %>
  <div class="alert alert-error">No se puede eliminar: esta jornada tiene partidos asociados.</div>
<% } %>

<div class="table-wrap">
  <table>
    <thead><tr>
      <th>ID</th><th>No. Jornada</th><th>Ronda</th><th>Fecha inicio</th><th>Fecha fin</th><th>Acciones</th>
    </tr></thead>
    <tbody>
    <% if (lista != null && !lista.isEmpty()) {
         for (Jornada j : lista) { %>
      <tr>
        <td><%= j.getIdJornada() %></td>
        <td>Jornada <%= j.getNumeroJornada() %></td>
        <td><span class="badge badge-blue"><%= esc(j.getNombreRonda()) %></span></td>
        <td><%= j.getFechaInicio() %></td>
        <td><%= j.getFechaFin() == null ? "-" : j.getFechaFin() %></td>
        <td><div class="acciones">
          <a class="btn btn-edit btn-sm" href="<%= ctx %>/jornadas?accion=editar&id=<%= j.getIdJornada() %>">Editar</a>
          <a class="btn btn-danger btn-sm" href="<%= ctx %>/jornadas?accion=eliminar&id=<%= j.getIdJornada() %>"
             onclick="return confirm('Eliminar esta jornada? (tambien se borraran sus partidos)');">Eliminar</a>
        </div></td>
      </tr>
    <%   }
       } else { %>
      <tr><td colspan="6" class="empty">No hay jornadas registradas.</td></tr>
    <% } %>
    </tbody>
  </table>
</div>
<%@ include file="/partials/footer.jsp" %>
