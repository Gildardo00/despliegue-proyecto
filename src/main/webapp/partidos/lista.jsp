<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, mx.uaa.arena.model.Partido" %>
<% String titulo = "Partidos"; String activo = "partidos"; %>
<%@ include file="/partials/header.jsp" %>
<%
    List<Partido> lista = (List<Partido>) request.getAttribute("lista");
%>
<div class="toolbar">
  <div class="section-title" style="margin:0">Partidos y resultados</div>
  <a class="btn btn-primary" href="<%= ctx %>/partidos?accion=nuevo">+ Nuevo partido</a>
</div>

<div class="table-wrap">
  <table>
    <thead><tr>
      <th>ID</th><th>Jornada</th><th>Disciplina</th><th>Local</th><th>Marcador</th>
      <th>Visitante</th><th>Sede</th><th>Acciones</th>
    </tr></thead>
    <tbody>
    <% if (lista != null && !lista.isEmpty()) {
         for (Partido p : lista) { %>
      <tr>
        <td><%= p.getIdPartido() %></td>
        <td>Jornada <%= p.getNumeroJornada() %></td>
        <td><span class="badge badge-blue"><%= esc(p.getNombreDisciplina()) %></span></td>
        <td><%= esc(p.getNombreLocal()) %></td>
        <td><strong><%= p.getGolesLocal() %> - <%= p.getGolesVisitante() %></strong></td>
        <td><%= esc(p.getNombreVisitante()) %></td>
        <td><%= esc(p.getSede()) %></td>
        <td><div class="acciones">
          <a class="btn btn-edit btn-sm" href="<%= ctx %>/partidos?accion=editar&id=<%= p.getIdPartido() %>">Editar</a>
          <a class="btn btn-danger btn-sm" href="<%= ctx %>/partidos?accion=eliminar&id=<%= p.getIdPartido() %>"
             onclick="return confirm('Eliminar este partido?');">Eliminar</a>
        </div></td>
      </tr>
    <%   }
       } else { %>
      <tr><td colspan="8" class="empty">No hay partidos registrados.</td></tr>
    <% } %>
    </tbody>
  </table>
</div>
<%@ include file="/partials/footer.jsp" %>
