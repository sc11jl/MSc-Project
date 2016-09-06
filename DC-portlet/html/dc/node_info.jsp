<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@ page  import="com.liferay.docs.dc.model.*" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<portlet:defineObjects />

<%
String displayInfo = (String)renderRequest.getAttribute("displayInfo");
String rackNo = (String)renderRequest.getAttribute("rackNo");
String nodeNo = (String)renderRequest.getAttribute("nodeNo");
HashMap<String, List<String>> vmInfoMap = (HashMap<String, List<String>>)renderRequest.getAttribute("vmInfoMap");

%>

<style>
.archi {
    margin:0;
    width:500px;
    height:300px;
    border:0;
    cellpadding:0;
    cellspacing:0;
    overflow:auto;
}

td {
   width: 50px;
   height: 50px;
}

.buttons {
    margin:0;
    width:30px;
    height:30px;
}
.low {
    background-color:#00FF00;
}
.medium{
    background-color:#FFFF00;
}
.high{
    background-color:#FF0000;
}

</style>

<h1>Rack No: <%= rackNo %>, Node No: <%= nodeNo %></h1>
<br/>
This is Node level, the following buttons display the <b>VMs No</b> in this Rack.
<br/>
<b style="color:#00FF00">Green</b> means low utilisation: 0-20%.
<br/>
<b style="color:#FFFF00">Yellow</b> means medium utilisation: 20-80%.
<br/>
<b style="color:#FF0000">Red</b> means high utilisation: 80-100%.


<portlet:actionURL var="displayVMInfoURL" name="displayVMInfo">
</portlet:actionURL>

<div class="archi">
<table>

<% 
List<String> vmList = vmInfoMap.get("Time-0.0");
for(int i = 0; i < vmList.size();i++){
	String info = vmList.get(i);
    String vmNo = (info.split("/")[0]).split(":")[1];
    double displayUtil = Double.parseDouble((info.split("/")[1]).split(":")[1]);
    String utilClass = null;
	if (displayUtil < 20){
		utilClass = "low";
	} else if (displayUtil >= 20 && displayUtil <= 80){
	    utilClass = "medium";
	} else {
		utilClass = "high";
	}
	if (i%5 == 0){ %>
	    <tr>	
	<%}
	%>
	<td>
	<div class="buttons">
	    <aui:form action="<%= displayVMInfoURL %>" name="<portlet:namespace />fm">
	        <input type="hidden" name="<portlet:namespace />displayInfo" value="<%= displayInfo %>"></input>
	        <input type="hidden" name="<portlet:namespace />rackNo" value="<%= rackNo %>"></input>
	        <input type="hidden" name="<portlet:namespace />nodeNo" value="<%= nodeNo %>"></input>
            <input class="<%= utilClass %>" type="submit" name="<portlet:namespace />button" value="<%= vmNo %>"></input>
        </aui:form>
    </div>
    </td>  	
<% 
}%> 
</tr>
</table>
</div>

