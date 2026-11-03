<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, mx.uaa.arena.model.JuegoDisciplina" %>
<% String titulo = "Juegos / Disciplinas"; String activo = "juegos"; %>
<%@ include file="/partials/header.jsp" %>
<%
    List<JuegoDisciplina> lista = (List<JuegoDisciplina>) request.getAttribute("lista");
    String err = request.getParameter("error");
%>
<div class="toolbar">
  <div class="section-title" style="margin:0">Catalogo de juegos / disciplinas</div>
  <a class="btn btn-primary" href="<%= ctx %>/juegos?accion=nuevo">+ Nueva disciplina</a>
</div>

<% if ("rel".equals(err)) { %>
  <div class="alert alert-error">No se puede eliminar: esta disciplina tiene partidos asociados.</div>
<% } %>

<div class="table-wrap">
  <table>
    <thead><tr>
      <th>ID</th><th>Disciplina</th><th>Tipo de competencia</th><th>Modalidad</th><th>Activa</th><th>Regla de puntuacion</th><th>Acciones</th>
    </tr></thead>
    <tbody>
    <% if (lista != null && !lista.isEmpty()) {
         for (JuegoDisciplina jd : lista) { %>
      <tr>
        <td><%= jd.getIdJuego() %></td>
        <td><%= esc(jd.getNombreDisciplina()) %></td>
        <td><%= esc(jd.getTipoCompetencia()) %></td>
        <td><%= esc(jd.getModalidad()) %></td>
        <td>
          <% if (jd.isActiva()) { %><span class="badge badge-green">Activa</span>
          <% } else { %><span class="badge badge-gray">Inactiva</span><% } %>
        </td>
        <td><%= esc(jd.getReglaPuntuacion()) %></td>
        <td><div class="acciones">
          <a class="btn btn-edit btn-sm" href="<%= ctx %>/juegos?accion=editar&id=<%= jd.getIdJuego() %>">Editar</a>
          <a class="btn btn-danger btn-sm" href="<%= ctx %>/juegos?accion=eliminar&id=<%= jd.getIdJuego() %>"
             onclick="return confirm('Eliminar esta disciplina?');">Eliminar</a>
        </div></td>
      </tr>
    <%   }
       } else { %>
      <tr><td colspan="7" class="empty">No hay disciplinas. Agrega la primera.</td></tr>
    <% } %>
    </tbody>
  </table>
</div>
<%@ include file="/partials/footer.jsp" %>
