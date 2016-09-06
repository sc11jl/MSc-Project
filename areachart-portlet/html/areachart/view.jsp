<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="javax.portlet.*" %>
<%@ page import="org.json.JSONObject" %>

<portlet:defineObjects />

<%
JSONObject jsonHistory = (JSONObject) renderRequest.getAttribute("jsonHistory");

%>

This is the <b>Area Chart</b> portlet in View mode.

<div id = "areachartContainer"></div>


<style>

svg {
  font: 10px sans-serif;
}

.axis path,
.axis line {
  fill: none;
  stroke: #000;
  shape-rendering: crispEdges;
}

.area {
  fill: steelblue;
  cursor: move;
  pointer-events: all
}

</style>

<script src="https://d3js.org/d3.v3.min.js"></script>

<%-- <script>

var margin = {top: 20, right: 20, bottom: 30, left: 50},
    width = 460 - margin.left - margin.right,
    height = 300 - margin.top - margin.bottom;

var zoom = d3.behavior.zoom()
    .on("zoom", draw);

var x = d3.scale.linear()
    .range([0, width]);

var y = d3.scale.linear()
    .range([height, 0]);

var xAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom");

var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left")
    .tickSize(-width)
    .tickPadding(6);

var area = d3.svg.area()
    .interpolate("step-after")
    .x(function(d) { return x(d.date); })
    .y0(height)
    .y1(function(d) { return y(d.value); });



var svg = d3.select("areachartContainer").append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")")
    .call(zoom);

svg.append("clipPath")
    .attr("id", "clip")
  .append("rect")
    .attr("x", x(0))
    .attr("y", y(1))
    .attr("width", x(1) - x(0))
    .attr("height", y(0) - y(1));

svg.append("path")
    .attr("class", "area")
    .attr("clip-path","url(#clip");

svg.append("g")
    .attr("class", "x axis")
    .attr("transform", "translate(0," + height + ")");
      

svg.append("g")
    .attr("class", "y axis")
  .append("text")
    .attr("transform", "rotate(-90)")
    .attr("y", 6)
    .attr("dy", ".71em")
    .style("text-anchor", "end")
    .text("Price ($)");


d3.json(<%= jsonHistory %>, function(error, data) {
  if (error) throw error;

  data.forEach(function(d) {
    d.date = + d;
    d.value = +d.value;
  });

  x.domain(d3.extent(data, function(d) { return d.date; }));
  y.domain([0, d3.max(data, function(d) { return d.value; })]);
  zoom.x(x);

  svg.select("path.area").data([data]);

  draw();

});

function draw() {
  svg.select("g.x.axis").call(xAxis);
  svg.select("g.y.axis").call(yAxis);
  svg.select("path.area").attr("d", area);
 
}

</script> --%>