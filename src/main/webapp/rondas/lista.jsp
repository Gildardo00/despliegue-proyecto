<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, mx.uaa.arena.model.Ronda" %>
<% String titulo = "Rondas"; String activo = "rondas"; %>
<%@ include file="/partials/header.jsp" %>
<%
    List<Ronda> lista = (List<Ronda>) request.getAttribute("lista");
    String err = request.getParameter("error");
%>
<div class="toolbar">
  <div class="section-title" style="margin:0">Rondas del torneo</div>
  <a class="btn btn-primary" href="<%= ctx %>/rondas?accion=nuevo">+ Nueva ronda</a>
</div>

<% if ("rel".equals(err)) { %>
  <div class="alert alert-error">No se puede eliminar: esta ronda tiene jornadas asociadas.</div>
<% } %>

<div class="table-wrap">
  <table>
    <thead><tr>
      <th>ID</th><th>Nombre de la ronda</th><th>Estatus</th><th>Acciones</th>
    </tr></thead>
    <tbody>
    <% if (lista != null && !lista.isEmpty()) {
         for (Ronda r : lista) {
           String est = r.getEstatus();
           String cls = "En curso".equals(est) ? "badge-green"
                      : ("Programada".equals(est) ? "badge-blue" : "badge-gray"); %>
      <tr>
        <td><%= r.getIdRonda() %></td>
        <td><%= esc(r.getNombreRonda()) %></td>
        <td><span class="badge <%= cls %>"><%= esc(est) %></span></td>
        <td><div class="acciones">
          <a class="btn btn-edit btn-sm" href="<%= ctx %>/rondas?accion=editar&id=<%= r.getIdRonda() %>">Editar</a>
          <a class="btn btn-danger btn-sm" href="<%= ctx %>/rondas?accion=eliminar&id=<%= r.getIdRonda() %>"
             onclick="return confirm('Eliminar esta ronda? (tambien se borraran sus jornadas)');">Eliminar</a>
        </div></td>
      </tr>
    <%   }
       } else { %>
      <tr><td colspan="4" class="empty">No hay rondas registradas.</td></tr>
    <% } %>
    </tbody>
  </table>
</div>
<%@ include file="/partials/footer.jsp" %>
