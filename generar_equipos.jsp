<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, mx.uaa.arena.model.JuegoDisciplina, mx.uaa.arena.model.Jugador" %>
<% String titulo = "Generar equipos"; String activo = "generar"; %>
<%@ include file="/partials/header.jsp" %>
<%
    List<JuegoDisciplina> disciplinas = (List<JuegoDisciplina>) request.getAttribute("disciplinas");
    Integer idJuego = (Integer) request.getAttribute("idJuego");
    JuegoDisciplina disciplina = (JuegoDisciplina) request.getAttribute("disciplina");
    List<Jugador> pool = (List<Jugador>) request.getAttribute("pool");
    String[] cats = {"Universitaria", "Libre", "Juvenil"};
    String[] ramas = {"Varonil", "Femenil", "Mixto"};
    String msg = request.getParameter("msg");
%>
<div class="section-title">Crear equipos automaticamente desde la bolsa de inscritos</div>
<p style="color:#5b6472;margin-top:-8px">Los participantes inscritos sin equipo se reparten de forma aleatoria y balanceada (diferencia maxima de 1 integrante).</p>

<% if (msg != null && msg.startsWith("ok:")) { %>
  <div class="alert alert-ok"><%= esc(msg.substring(3)) %></div>
<% } else if (msg != null && msg.startsWith("error:")) { %>
  <div class="alert alert-error"><%= esc(msg.substring(6)) %></div>
<% } %>

<div class="form-card">
  <form method="get" action="<%= ctx %>/generar-equipos">
    <div class="form-grid">
      <div class="field full">
        <label>Disciplina</label>
        <select name="idJuego" required onchange="this.form.submit()">
          <option value="">-- Selecciona --</option>
          <% if (disciplinas != null) for (JuegoDisciplina d : disciplinas) {
               boolean sel = idJuego != null && idJuego == d.getIdJuego(); %>
            <option value="<%= d.getIdJuego() %>" <%= sel ? "selected" : "" %>><%= esc(d.getNombreDisciplina()) %> (<%= esc(d.getModalidad()) %>)</option>
          <% } %>
        </select>
      </div>
    </div>
  </form>
</div>

<% if (disciplina != null && idJuego != null) {
     int n = (pool == null) ? 0 : pool.size(); %>
  <div class="section-title" style="font-size:15px">
    Bolsa de <%= esc(disciplina.getNombreDisciplina()) %>: <span class="badge badge-blue"><%= n %> participantes sin equipo</span>
  </div>

  <div class="table-wrap">
    <table>
      <thead><tr><th>ID</th><th>Participante</th><th>Categoria</th><th>Rama</th></tr></thead>
      <tbody>
      <% if (pool != null && !pool.isEmpty()) {
           for (Jugador j : pool) { %>
        <tr>
          <td><%= j.getIdJugador() %></td>
          <td><%= esc(j.getNombreCompleto()) %></td>
          <td><%= esc(j.getCategoria()) %></td>
          <td><%= esc(j.getRama()) %></td>
        </tr>
      <%   }
         } else { %>
        <tr><td colspan="4" class="empty">No hay participantes sin equipo en esta disciplina.</td></tr>
      <% } %>
      </tbody>
    </table>
  </div>

  <% if (n >= 2) { %>
  <div class="form-card" style="margin-top:14px">
    <form method="post" action="<%= ctx %>/generar-equipos">
      <input type="hidden" name="idJuego" value="<%= idJuego %>">
      <div class="form-grid">
        <div class="field">
          <label>Nombre base de los equipos</label>
          <input type="text" name="nombreBase" required placeholder="Ej. Escuadron">
        </div>
        <div class="field">
          <label>Numero de equipos</label>
          <input type="number" name="numEquipos" min="2" max="<%= n %>" value="2" required>
        </div>
        <div class="field">
          <label>Categoria</label>
          <select name="categoria">
            <% for (String c : cats) { %><option value="<%= c %>"><%= c %></option><% } %>
          </select>
        </div>
        <div class="field">
          <label>Rama</label>
          <select name="rama">
            <% for (String r : ramas) { %><option value="<%= r %>" <%= "Mixto".equals(r) ? "selected" : "" %>><%= r %></option><% } %>
          </select>
        </div>
      </div>
      <div class="form-actions">
        <button type="submit" class="btn btn-primary">Generar equipos</button>
      </div>
    </form>
  </div>
  <% } %>
<% } %>
<%@ include file="/partials/footer.jsp" %>
