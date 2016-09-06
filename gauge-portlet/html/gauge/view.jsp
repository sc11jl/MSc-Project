<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>

<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="javax.portlet.*" %>
<portlet:defineObjects />

<style>
.label{
	font-size:11.5px;
	fill:#ffffff;
	text-anchor:middle;
	alignment-baseline:middle;
}
.face{
	stroke:#c8c8c8;
	stroke-width:2;
}
.minorTicks{
	stroke-width:2;
	stroke:white;
}
.majorTicks{
	stroke:white;
	stroke-width:3;
}
.util{
    color:#FF0000;
}

</style>



<%
double testUtil = (Double) renderRequest.getAttribute("testUtil");
String level = (String) renderRequest.getAttribute("level");
String no = (String) renderRequest.getAttribute("no");
%>


This is the <b>Gauge</b> portlet to display current status:

<h2><%= level %> <%= no %></h2>

<p class="util">Utilisation: <%= testUtil %>%</p>



<svg width="200" height="200"></svg>
<script src="https://d3js.org/d3.v3.min.js"></script>
<script src="http://vizjs.org/viz.v1.0.0.min.js"></script>
<script>
  var svg=d3.select("svg");
  var g=svg.append("g").attr("transform","translate(100,100)");
  var domain = [0,100];
  
  var gg = viz.gg()
	.domain(domain)
	.innerFaceColor("#000000")
	.faceColor("#003CEC")
	.needleColor("#f00")
	.outerRadius(100)
	.innerRadius(10)
	.value(0.5*(domain[1]+domain[0]))
	.duration(1000);
  
  gg.defs(svg);
  g.call(gg);  
  
  d3.select(self.frameElement).style("height", "100px");
  gg.setNeedle(<%= testUtil %>);
</script>