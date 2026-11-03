<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, mx.uaa.arena.model.JuegoDisciplina, mx.uaa.arena.model.Equipo, mx.uaa.arena.model.Enfrentamiento" %>
<% String titulo = "Generacion de torneos"; String activo = "torneos"; %>
<%@ include file="/partials/header.jsp" %>
<%
    List<JuegoDisciplina> disciplinas = (List<JuegoDisciplina>) request.getAttribute("disciplinas");
    JuegoDisciplina disciplina = (JuegoDisciplina) request.getAttribute("disciplina");
    Integer idJuego = (Integer) request.getAttribute("idJuego");
    String categoria = (String) request.getAttribute("categoria");
    String rama = (String) request.getAttribute("rama");
    List<Equipo> equipos = (List<Equipo>) request.getAttribute("equipos");
    List<Enfrentamiento> enfrentamientos = (List<Enfrentamiento>) request.getAttribute("enfrentamientos");

    String catSel = categoria == null ? "Universitaria" : categoria;
    String ramaSel = rama == null ? "Mixto" : rama;
    String[] cats = {"Universitaria", "Libre", "Juvenil"};
    String[] ramas = {"Varonil", "Femenil", "Mixto"};

    String msg = request.getParameter("msg");
%>
<div class="section-title">Generar bracket o tabla de torneo</div>

<% if (msg != null && msg.startsWith("ok:")) { %>
  <div class="alert alert-ok"><%= esc(msg.substring(3)) %></div>
<% } else if (msg != null && msg.startsWith("error:")) { %>
  <div class="alert alert-error"><%= esc(msg.substring(6)) %></div>
<% } %>

<div class="form-card">
  <form method="get" action="<%= ctx %>/torneos">
    <div class="form-grid">
      <div class="field">
        <label>Disciplina</label>
        <select name="idJuego" required>
          <option value="">-- Selecciona --</option>
          <% if (disciplinas != null) for (JuegoDisciplina d : disciplinas) {
               boolean sel = idJuego != null && idJuego == d.getIdJuego(); %>
            <option value="<%= d.getIdJuego() %>" <%= sel ? "selected" : "" %>><%= esc(d.getNombreDisciplina()) %> (<%= esc(d.getTipoCompetencia()) %>)</option>
          <% } %>
        </select>
      </div>
      <div class="field">
        <label>Categoria</label>
        <select name="categoria">
          <% for (String c : cats) { %>
            <option value="<%= c %>" <%= c.equals(catSel) ? "selected" : "" %>><%= c %></option>
          <% } %>
        </select>
      </div>
      <div class="field">
        <label>Rama</label>
        <select name="rama">
          <% for (String r : ramas) { %>
            <option value="<%= r %>" <%= r.equals(ramaSel) ? "selected" : "" %>><%= r %></option>
          <% } %>
        </select>
      </div>
    </div>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary">Ver grupo</button>
    </div>
  </form>
</div>

<% if (disciplina != null && idJuego != null) { %>
  <div class="section-title" style="font-size:15px">
    <%= esc(disciplina.getNombreDisciplina()) %>
    &middot; <span class="badge badge-blue"><%= esc(catSel) %></span>
    <span class="badge badge-gray"><%= esc(ramaSel) %></span>
    &middot; Formato: <strong><%= esc(disciplina.getTipoCompetencia()) %></strong>
  </div>

  <div class="table-wrap">
    <table>
      <thead><tr><th>Siembra</th><th>Equipo</th></tr></thead>
      <tbody>
      <% if (equipos != null && !equipos.isEmpty()) {
           for (Equipo e : equipos) { %>
        <tr><td><%= e.getSeed() == 0 ? "-" : e.getSeed() %></td><td><%= esc(e.getNombreEquipo()) %></td></tr>
      <%   }
         } else { %>
        <tr><td colspan="2" class="empty">No hay equipos en este grupo.</td></tr>
      <% } %>
      </tbody>
    </table>
  </div>

  <form method="post" action="<%= ctx %>/torneos" style="margin:14px 0">
    <input type="hidden" name="idJuego" value="<%= idJuego %>">
    <input type="hidden" name="categoria" value="<%= esc(catSel) %>">
    <input type="hidden" name="rama" value="<%= esc(ramaSel) %>">
    <button type="submit" class="btn btn-primary"
            onclick="return confirm('Generar el torneo reemplazara los enfrentamientos previos de este grupo. Continuar?');">
      Generar torneo
    </button>
  </form>

  <% if (enfrentamientos != null && !enfrentamientos.isEmpty()) { %>
    <div class="section-title" style="font-size:15px">Enfrentamientos generados</div>
    <div class="table-wrap">
      <table>
        <thead><tr><th>Fase</th><th>Local</th><th></th><th>Visitante</th></tr></thead>
        <tbody>
        <% for (Enfrentamiento en : enfrentamientos) { %>
          <tr>
            <td><%= esc(en.getFase()) %></td>
            <td><%= esc(en.getNombreEquipoA()) %><% if (en.getSeedA() > 0) { %> <span class="badge badge-gray">#<%= en.getSeedA() %></span><% } %></td>
            <td style="text-align:center">
              <% if (en.isEsBye()) { %><span class="badge badge-green">Pase directo (bye)</span><% } else { %>vs<% } %>
            </td>
            <td><% if (!en.isEsBye()) { %><%= esc(en.getNombreEquipoB()) %><% if (en.getSeedB() > 0) { %> <span class="badge badge-gray">#<%= en.getSeedB() %></span><% } %><% } %></td>
          </tr>
        <% } %>
        </tbody>
      </table>
    </div>
  <% } %>
<% } %>
<%@ include file="/partials/footer.jsp" %>
