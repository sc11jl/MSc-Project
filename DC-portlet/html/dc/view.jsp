<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="javax.portlet.*" %>
<portlet:defineObjects />

<%
String displayInfo = (String)renderRequest.getAttribute("displayInfo");

HashMap<String,List<String>> rackInfo = (HashMap<String,List<String>>)renderRequest.getAttribute("rackInfo");
List<String> rackList = rackInfo.get("Time-0.0");

%>


<style>

.archi {
    margin:0;
    width:500px;
    height:300px;
    
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

This is the <b>Rm Archit</b> portlet in View mode.
<br/>
The following buttons display the <b>racks No</b> with coded colour.
<br/>
<b style="color:#00FF00">Green</b> means low utilisation: 0-20%.
<br/>
<b style="color:#FFFF00">Yellow</b> means medium utilisation: 20-80%.
<br/>
<b style="color:#FF0000">Red</b> means high utilisation: 80-100%.



<script type="text/javascript">
    
   
</script>

<portlet:actionURL var="displayRackInfoURL" name="displayRackInfo">
</portlet:actionURL>

<div class="archi">
<table>
<% 
for(int i = 0; i < rackList.size();i++){
    String info = rackList.get(i);
    String rackNo = (info.split("/")[0]).split(":")[1];
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
	    <aui:form action="<%= displayRackInfoURL %>" name="<portlet:namespace />fm">
	        <input type="hidden" name="<portlet:namespace />displayInfo" value="<%= displayInfo %>"></input>
            <input class="<%= utilClass %>" type="submit" name="<portlet:namespace />button" value="<%= rackNo %>"></input>
        </aui:form>
    
    </div>
   	</td>
<% 
}%> 
</tr>
</table>
</div>


