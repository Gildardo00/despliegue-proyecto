<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<% String titulo = "Inscripciones y filtros"; String activo = "configuracion"; %>
<%@ include file="/partials/header.jsp" %>
<%
    Boolean abiertasObj = (Boolean) request.getAttribute("abiertas");
    boolean abiertas = (abiertasObj == null) || abiertasObj;
    String fechaCierre = (String) request.getAttribute("fechaCierre");
    if (fechaCierre == null) fechaCierre = "";
    List<String> palabras = (List<String>) request.getAttribute("palabras");
%>
<div class="section-title">Control de inscripciones</div>
<div class="form-card">
  <p>Estado actual:
    <% if (abiertas) { %><span class="badge badge-green">Abiertas</span>
    <% } else { %><span class="badge badge-gray">Cerradas</span><% } %>
  </p>
  <form method="post" action="<%= ctx %>/configuracion">
    <input type="hidden" name="accion" value="inscripciones">
    <div class="form-grid">
      <div class="field">
        <label>Estado de inscripciones</label>
        <select name="abiertas">
          <option value="true"  <%= abiertas ? "selected" : "" %>>Abiertas</option>
          <option value="false" <%= !abiertas ? "selected" : "" %>>Cerradas</option>
        </select>
      </div>
      <div class="field">
        <label>Fecha de cierre (opcional)</label>
        <input type="date" name="fechaCierre" value="<%= esc(fechaCierre) %>">
      </div>
    </div>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary">Guardar</button>
    </div>
  </form>
</div>

<div class="section-title">Filtro de lenguaje ofensivo</div>
<div class="form-card">
  <form method="post" action="<%= ctx %>/configuracion" class="toolbar" style="margin-bottom:14px">
    <input type="hidden" name="accion" value="palabra-add">
    <input type="text" name="palabra" placeholder="Nueva palabra a bloquear" required>
    <button type="submit" class="btn btn-primary">Agregar</button>
  </form>
  <div class="table-wrap">
    <table>
      <thead><tr><th>Palabra bloqueada</th><th>Acciones</th></tr></thead>
      <tbody>
      <% if (palabras != null && !palabras.isEmpty()) {
           for (String p : palabras) { %>
        <tr>
          <td><%= esc(p) %></td>
          <td>
            <form method="post" action="<%= ctx %>/configuracion" style="display:inline">
              <input type="hidden" name="accion" value="palabra-del">
              <input type="hidden" name="palabra" value="<%= esc(p) %>">
              <button type="submit" class="btn btn-danger btn-sm">Quitar</button>
            </form>
          </td>
        </tr>
      <%   }
         } else { %>
        <tr><td colspan="2" class="empty">No hay palabras configuradas.</td></tr>
      <% } %>
      </tbody>
    </table>
  </div>
</div>
<%@ include file="/partials/footer.jsp" %>
