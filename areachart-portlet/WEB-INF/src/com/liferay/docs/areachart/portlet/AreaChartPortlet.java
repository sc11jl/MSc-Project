package com.liferay.docs.areachart.portlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.json.JSONObject;

import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class AreaChartPortlet
 */
public class AreaChartPortlet extends MVCPortlet {
	
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
		//double testUtil = 0.0;
		//Assume timestamp is 0.0.
		//String timestamp = "Time-0.0";
		HashMap<String,String> history = new HashMap<String,String>();
        if (currentURL.contains("displayRackInfo")){
        	HashMap<String,List<String>> rackInfo = (HashMap<String,List<String>>) ps.getAttribute("rackGauge",PortletSession.APPLICATION_SCOPE);
        	String getRackNo = (String) ps.getAttribute("rackNo",PortletSession.APPLICATION_SCOPE);
        	String util = null;
        	for(String key:rackInfo.keySet()){
        		List<String> rackList = rackInfo.get(key);
        		for (int i=0; i<rackList.size();i++){
            		String info = rackList.get(i);
            	    String rackNo = (info.split("/")[0]).split(":")[1];
            		if (rackNo.contains(getRackNo)){
            			util = (info.split("/")[1]).split(":")[1];
            			history.put(key, util);
            		}
            	}
        	}	
        }else if (currentURL.contains("displayNodeInfo")){
        	HashMap<String,List<String>> nodeInfo = (HashMap<String,List<String>>) ps.getAttribute("nodeGauge",PortletSession.APPLICATION_SCOPE);
        	String getnodeNo = (String) ps.getAttribute("nodeNo",PortletSession.APPLICATION_SCOPE);
        	String util = null;
        	for(String key:nodeInfo.keySet()){
        		List<String> nodeList = nodeInfo.get(key);
        		for (int i=0; i<nodeList.size();i++){
            		String info = nodeList.get(i);
            	    String nodeNo = (info.split("/")[0]).split(":")[1];
            		if (nodeNo.contains(getnodeNo)){
            			util = (info.split("/")[1]).split(":")[1];
            			history.put(key, util);
            		}
            	}
        	}
        }else if (currentURL.contains("displayVMInfo")){
        	HashMap<String,List<String>> vmInfo = (HashMap<String,List<String>>) ps.getAttribute("vmGauge",PortletSession.APPLICATION_SCOPE);
        	String getVMNo = (String) ps.getAttribute("vmNo",PortletSession.APPLICATION_SCOPE);
        	String util = null;
        	for(String key:vmInfo.keySet()){
        		List<String> vmList = vmInfo.get(key);
        		for (int i=0; i<vmList.size();i++){
            		String info = vmList.get(i);
            	    String vmNo = (info.split("/")[0]).split(":")[1];
            		if (vmNo.contains(getVMNo)){
            			util = (info.split("/")[1]).split(":")[1];
            			history.put(key, util);
            		}
            	}
        	}
        }else{
        	HashMap<String,String> dcInfo =  (HashMap<String,String>) ps.getAttribute("dcGauge",PortletSession.APPLICATION_SCOPE);
        	String util = null;
        	for(String key:dcInfo.keySet()){
        		util = (dcInfo.get(key).split(":"))[1];
        		history.put(key, util);
        	}
        }
		JSONObject jsonHistory = new JSONObject(history);
        request.setAttribute("jsonHistory", jsonHistory);

	    super.doView(request, response);
	}
 

}
