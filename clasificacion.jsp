<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, mx.uaa.arena.model.Jugador" %>
<% String titulo = "Clasificacion automatica"; String activo = "clasificacion"; %>
<%@ include file="/partials/header.jsp" %>
<%
    List<Jugador> jugadores = (List<Jugador>) request.getAttribute("jugadores");
%>
<div class="section-title">Participantes agrupados por disciplina, categoria y rama</div>
<p style="color:#5b6472;margin-top:-8px">Los participantes se organizan automaticamente segun su disciplina, categoria y rama.</p>

<%
    if (jugadores != null && !jugadores.isEmpty()) {
        String grupoActual = null;
        boolean tablaAbierta = false;
        for (Jugador j : jugadores) {
            String disc = j.getNombreDisciplina() == null ? "Sin disciplina" : j.getNombreDisciplina();
            String clave = disc + " | " + j.getCategoria() + " | " + j.getRama();
            if (!clave.equals(grupoActual)) {
                if (tablaAbierta) { %>
                  </tbody></table></div>
                <% }
                grupoActual = clave;
                tablaAbierta = true;
%>
  <div class="section-title" style="font-size:15px"><%= esc(disc) %>
    &middot; <span class="badge badge-blue"><%= esc(j.getCategoria()) %></span>
    <span class="badge badge-gray"><%= esc(j.getRama()) %></span>
  </div>
  <div class="table-wrap">
    <table>
      <thead><tr><th>ID</th><th>Participante</th><th>Equipo</th><th>Edad</th></tr></thead>
      <tbody>
<%          }
%>
        <tr>
          <td><%= j.getIdJugador() %></td>
          <td><%= esc(j.getNombreCompleto()) %></td>
          <td><%= j.getNombreEquipo() == null ? "Bolsa" : esc(j.getNombreEquipo()) %></td>
          <td><%= j.getEdad() == null ? "-" : j.getEdad() %></td>
        </tr>
<%      }
        if (tablaAbierta) { %>
      </tbody></table></div>
<%      }
    } else { %>
  <div class="table-wrap"><table><tbody>
    <tr><td class="empty">No hay participantes para clasificar.</td></tr>
  </tbody></table></div>
<% } %>
<%@ include file="/partials/footer.jsp" %>
