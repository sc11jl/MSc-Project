<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="javax.portlet.*" %>

<portlet:defineObjects />
<%
List utilFaultAttribute0 = (List)renderRequest.getAttribute("utilFaultAttribute0");
List utilFaultAttribute1 = (List)renderRequest.getAttribute("utilFaultAttribute1");
List utilFaultAttribute2 = (List)renderRequest.getAttribute("utilFaultAttribute2");
String level = (String) renderRequest.getAttribute("level");
%>
<style type="text/css">
  
table {
    border-collapse: collapse;
}

table, th, td {
    border: 1px solid black;
}

  td, th {
    padding: 2px 4px;
}

th {
    font-weight: bold;
}

input {
    margin:10px 10px 10px 10px;
}

</style>

This is the <b>Table</b> portlet display the <b>potential fault <%= level %></b>.

<table>
  <tr>
    <th> Utilisation = 0% </th>
    <th> 0 < Utilisatoin <= 10% </th>
    <th> Utilisation >= 90% </th>
  </tr>
  <tr>
    <td>
        <%
            for(int i = 0; i < utilFaultAttribute0.size(); i++){ %>
                <div class="buttons">
	                <input type="button" name="<portlet:namespace />button" value="<%= utilFaultAttribute0.get(i).toString() %>"></input>
                </div> 	
        <%  }
        %>
    </td>
    <td>
        <%
            for(int i = 0; i < utilFaultAttribute1.size(); i++){ %>
                <div class="buttons">
	                <input type="button" name="<portlet:namespace />button" value="<%= utilFaultAttribute1.get(i).toString() %>"></input>
                </div> 	
        <%  }
        %>
    </td>
    <td>
        <%
            for(int i = 0; i < utilFaultAttribute2.size(); i++){ %>
                <div class="buttons">
	                <input type="button" name="<portlet:namespace />button" value="<%= utilFaultAttribute2.get(i).toString() %>"></input>
                </div> 	
        <%  }
        %>
    </td>
  </tr>
</table>

