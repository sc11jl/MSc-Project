package com.liferay.docs.guage.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class GaugePortlet
 */
public class GaugePortlet extends MVCPortlet {
	
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
		double testUtil = 0.0;
		//Assume timestamp is 0.0.
		String timestamp = "Time-0.0";
		String level = null;
		String no = null;
		
        if (currentURL.contains("displayRackInfo")){
        	level = "Rack";
        	HashMap<String,List<String>> rackInfo = (HashMap<String,List<String>>) ps.getAttribute("rackGauge",PortletSession.APPLICATION_SCOPE);
        	String getRackNo = (String) ps.getAttribute("rackNo",PortletSession.APPLICATION_SCOPE);
        	//System.out.println(getRackNo);
        	List<String> rackList = rackInfo.get(timestamp);
        	for (int i=0; i<rackList.size();i++){
        		String info = rackList.get(i);
        	    String rackNo = (info.split("/")[0]).split(":")[1];
        		if (rackNo.equals(getRackNo)){
        			testUtil = Double.parseDouble((info.split("/")[1]).split(":")[1]);
        			no = rackNo;
        			
        		}
        	}
        }else if (currentURL.contains("displayNodeInfo")){
        	level = "Node";
        	HashMap<String,List<String>> nodeInfo = (HashMap<String,List<String>>) ps.getAttribute("nodeGauge",PortletSession.APPLICATION_SCOPE);
        	String getnodeNo = (String) ps.getAttribute("nodeNo",PortletSession.APPLICATION_SCOPE);
        	List<String> nodeList = nodeInfo.get(timestamp);
        	for (int i=0; i<nodeList.size();i++){
        		String info = nodeList.get(i);
        	    String nodeNo = (info.split("/")[0]).split(":")[1];
        		if (nodeNo.equals(getnodeNo)){
        			testUtil = Double.parseDouble((info.split("/")[1]).split(":")[1]);
        			no = nodeNo;
        		}
        	}
        }else if (currentURL.contains("displayVMInfo")){
        	level = "VM";
        	HashMap<String,List<String>> vmInfo = (HashMap<String,List<String>>) ps.getAttribute("vmGauge",PortletSession.APPLICATION_SCOPE);
        	String getVMNo = (String) ps.getAttribute("vmNo",PortletSession.APPLICATION_SCOPE);
        	List<String> vmList = vmInfo.get(timestamp);
        	for (int i=0; i<vmList.size();i++){
        		String info = vmList.get(i);
        	    String vmNo = (info.split("/")[0]).split(":")[1];
        		if (vmNo.equals(getVMNo)){
        			testUtil = Double.parseDouble((info.split("/")[1]).split(":")[1]);
        			no = vmNo;
        		}
        	}
        }else{
        	level = "DC";
        	HashMap<String,String> dcInfo =  (HashMap<String,String>) ps.getAttribute("dcGauge",PortletSession.APPLICATION_SCOPE);
        	String dcUtil = dcInfo.get(timestamp);
        	testUtil = Double.parseDouble((dcUtil.split(":"))[1]);
        	no = "level";
        }
		
        request.setAttribute("testUtil", testUtil);
        request.setAttribute("level", level);
        request.setAttribute("no", no);

	    super.doView(request, response);
	}

}
