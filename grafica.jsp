<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.Map" %>
<% String titulo = "Grafica de resultados"; String activo = "grafica"; %>
<%@ include file="/partials/header.jsp" %>
<%

    Map<String,Integer> victorias = (Map<String,Integer>) request.getAttribute("victorias");

    int maximo = (request.getAttribute("maximo") != null) ? (Integer) request.getAttribute("maximo") : 1;
%>
<div class="section-title" style="margin-top:0">Victorias por equipo</div>

<div class="chart">
<% if (victorias != null && !victorias.isEmpty()) {
     for (Map.Entry<String,Integer> e : victorias.entrySet()) {
       int v   = e.getValue();

       int pct = (int) Math.round(v * 100.0 / maximo);

       if (v > 0 && pct < 6) pct = 6; %>
  <div class="chart-row">

    <div class="chart-name"><%= esc(e.getKey()) %></div>

    <div class="chart-track">
      <div class="chart-bar" style="width:<%= pct %>%;"></div>
    </div>

    <div class="chart-val"><%= v %></div>
  </div>
<%   }
   } else { %>
  <div class="empty">No hay datos para mostrar todavia.</div>
<% } %>

  <div class="chart-legend">La barra muestra los partidos ganados por cada equipo, segun el marcador de la relacion Enfrentar (local vs visitante).</div>
</div>

<%@ include file="/partials/footer.jsp" %>
