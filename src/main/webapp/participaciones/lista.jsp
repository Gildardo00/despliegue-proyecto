<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, mx.uaa.arena.model.Participar" %>
<% String titulo = "Participaciones"; String activo = "participaciones"; %>
<%@ include file="/partials/header.jsp" %>
<%
    List<Participar> lista = (List<Participar>) request.getAttribute("lista");
%>
<div class="toolbar">
  <div class="section-title" style="margin:0">Participaciones de jugadores</div>
  <a class="btn btn-primary" href="<%= ctx %>/participaciones?accion=nuevo">+ Nueva participacion</a>
</div>

<div class="table-wrap">
  <table>
    <thead><tr>
      <th>Jugador</th><th>Equipo</th><th>Partido</th><th>No.</th>
      <th>Goles</th><th>Gano</th><th>Acciones</th>
    </tr></thead>
    <tbody>
    <% if (lista != null && !lista.isEmpty()) {
         for (Participar pa : lista) {
           String cls = pa.isPartidaGanada() ? "badge-green" : "badge-gray";
           String txt = pa.isPartidaGanada() ? "Si" : "No"; %>
      <tr>
        <td><%= esc(pa.getNombreJugador()) %></td>
        <td><span class="badge badge-blue"><%= esc(pa.getNombreEquipo()) %></span></td>
        <td><%= esc(pa.getEtiquetaPartido()) %></td>
        <td><%= pa.getNumeroP() %></td>
        <td><strong><%= pa.getGolesIndividuales() %></strong></td>
        <td><span class="badge <%= cls %>"><%= txt %></span></td>
        <td><div class="acciones">
          <a class="btn btn-edit btn-sm"
             href="<%= ctx %>/participaciones?accion=editar&idJugador=<%= pa.getIdJugador() %>&idPartido=<%= pa.getIdPartido() %>">Editar</a>
          <a class="btn btn-danger btn-sm"
             href="<%= ctx %>/participaciones?accion=eliminar&idJugador=<%= pa.getIdJugador() %>&idPartido=<%= pa.getIdPartido() %>"
             onclick="return confirm('Eliminar esta participacion?');">Eliminar</a>
        </div></td>
      </tr>
    <%   }
       } else { %>
      <tr><td colspan="7" class="empty">No hay participaciones registradas.</td></tr>
    <% } %>
    </tbody>
  </table>
</div>
<%@ include file="/partials/footer.jsp" %>
