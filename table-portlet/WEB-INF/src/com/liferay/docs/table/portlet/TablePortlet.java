package com.liferay.docs.table.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class TablePortlet
 */
public class TablePortlet extends MVCPortlet {
	public String getDisplayInfo(RenderRequest request){
		String currentURL = PortalUtil.getCurrentURL(request);
        String displayInfo = null; 
        if (currentURL.contains("displayRackInfo")){
    	    displayInfo = "displayRackInfo";
        }else if (currentURL.contains("displayNodeInfo")){
    	    displayInfo = "displayNodeInfo";
        }else if (currentURL.contains("displayVMInfo")){
    	    displayInfo = "displayVMInfo";
        }else{
        }
        return displayInfo;
	}
	
	@Override
	public void doView(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {
		
		PortletSession ps = request.getPortletSession();		
		
		String currentURL = PortalUtil.getCurrentURL(request);
		
		//Assume timestamp is 0.0.
		String timestamp = "Time-0.0";
		
		//utilFaultAttribute0 means Utilisation = 0,
		//utilFaultAttribute1 means 0<Utilisation<10%,
		//utilFaultAttribute2 means Utilisation>90%
		List utilFaultAttribute0 = new ArrayList();
		List utilFaultAttribute1 = new ArrayList();
		List utilFaultAttribute2 = new ArrayList();
		String level = null;
		
        if (currentURL.contains("displayRackInfo")){
        	level = "Nodes";
        	HashMap<String,List<String>> nodeInfo = (HashMap<String,List<String>>) ps.getAttribute("nodeTable",PortletSession.APPLICATION_SCOPE);
        	List<String> nodeList = nodeInfo.get(timestamp);
        	for (int i=0; i<nodeList.size();i++){
        		double util = Double.parseDouble((nodeList.get(i).split("/")[1]).split(":")[1]);
        	    String nodeNo = (nodeList.get(i).split("/")[0]).split(":")[1];
        		if (util == 0){
        			utilFaultAttribute0.add(nodeNo);
        		} else if (util>0 && util<=10){
        			utilFaultAttribute1.add(nodeNo);
        		} else if (util>=90){
        			utilFaultAttribute2.add(nodeNo);
        		}else{
        			
        		}
        	}
        }else if (currentURL.contains("displayNodeInfo")){
        	level = "VMs";
        	HashMap<String,List<String>> vmInfo = (HashMap<String,List<String>>) ps.getAttribute("vmTable",PortletSession.APPLICATION_SCOPE);
        	List<String> vmList = vmInfo.get(timestamp);
        	for (int i=0; i<vmList.size();i++){
        		double util = Double.parseDouble((vmList.get(i).split("/")[1]).split(":")[1]);
        	    String vmNo = (vmList.get(i).split("/")[0]).split(":")[1];
        		if (util == 0){
        			utilFaultAttribute0.add(vmNo);
        		} else if (util>0 && util<=10){
        			utilFaultAttribute1.add(vmNo);
        		} else if (util>=90){
        			utilFaultAttribute2.add(vmNo);
        		}else{
        			
        		}
        	}
        }else{
        	level = "Racks";
        	HashMap<String,List<String>> rackInfo = (HashMap<String,List<String>>) ps.getAttribute("rackTable",PortletSession.APPLICATION_SCOPE);
        	List<String> rackList = rackInfo.get(timestamp);
        	for (int i=0; i<rackList.size();i++){
        		double util = Double.parseDouble((rackList.get(i).split("/")[1]).split(":")[1]);
        	    String rackNo = (rackList.get(i).split("/")[0]).split(":")[1];
        		if (util == 0){
        			utilFaultAttribute0.add(rackNo);
        		} else if (util>0 && util<=10){
        			utilFaultAttribute1.add(rackNo);
        		} else if (util>=90){
        			utilFaultAttribute2.add(rackNo);
        		}else{
        			
        		}
        	}
        }
		
        request.setAttribute("utilFaultAttribute0", utilFaultAttribute0);
        request.setAttribute("utilFaultAttribute1", utilFaultAttribute1);
        request.setAttribute("utilFaultAttribute2", utilFaultAttribute2);
        request.setAttribute("level", level);

	    super.doView(request, response);
	}

}
