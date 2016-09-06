package com.liferay.docs.dc.portlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.docs.dc.model.Node;
import com.liferay.docs.dc.model.Rack;
import com.liferay.docs.dc.model.VM;
import com.liferay.docs.dc.portlet.DBUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class DCPortlet
 */
public class DCPortlet extends MVCPortlet {
	public List<Double> getTimeList() {
		String sql = "SELECT Timestamp from nodedata group by Timestamp";
		DBUtil util = new DBUtil();
		Connection conn = util.getConnection();
		PreparedStatement pstmt;
		List<Double> timeList = new ArrayList<Double>();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				double timestamp = rs.getDouble(1);
				timeList.add(timestamp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(conn);
		}
		return timeList;
	}

	// Get current display info through get the current URL.
	public String getDisplayInfo(RenderRequest request) {
		String currentURL = PortalUtil.getCurrentURL(request);
		System.out.println("Current Page's URL: " + currentURL);
		String displayInfo = null;
		if (currentURL.contains("cpu")) {
			displayInfo = "cpu";
		} else if (currentURL.contains("disk")) {
			displayInfo = "disk";
		} else if (currentURL.contains("memory")) {
			displayInfo = "memory";
		} else if (currentURL.contains("power")) {
			displayInfo = "power";
		} else {
		}
		return displayInfo;
	}

	public HashMap<String, String> getDCInfo(String displayInfo,
			List<Double> timeList) {
		String sql = "SELECT Timestamp, sum(CPU_Utilisation)/count(Node_No), "
				+ "sum(Disk_Utilisation)/count(Node_No), sum(Memory_Utilisation)/count(Node_No), "
				+ "sum(Power_Consumption) from nodedata where Timestamp=? order by Timestamp";
		DBUtil util = new DBUtil();
		Connection conn = util.getConnection();
		PreparedStatement pstmt;
		HashMap<String, String> dcInfo = new HashMap<String, String>();
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < timeList.size(); i++) {
				pstmt.setDouble(1, timeList.get(i));
				ResultSet rs = pstmt.executeQuery();
				String timestamp = "Time-" + timeList.get(i);
				String info = null;
				while (rs.next()) {
					double dcDisplayUtil = 0;
					if (displayInfo.contains("cpu")) {
						dcDisplayUtil = rs.getDouble(2);
					} else if (displayInfo.contains("disk")) {
						dcDisplayUtil = rs.getDouble(3);
					} else if (displayInfo.contains("memory")) {
						dcDisplayUtil = rs.getDouble(4);
					} else if (displayInfo.contains("power")) {
						dcDisplayUtil = rs.getDouble(5);
					} else {
					}
					info = "dcDisplayUtil:" + dcDisplayUtil;
				}
				dcInfo.put(timestamp, info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(conn);
		}
		return dcInfo;
	}

	public HashMap<String, List<String>> getRackInfo(String displayInfo,
			List<Double> timeList) {
		// String sql = "SELECT DISTINCT Rack_No FROM nodedata;";
		String sql = "SELECT Rack_No, sum(CPU_Utilisation)/count(CPU_Utilisation), sum(Disk_Utilisation)/count(Disk_Utilisation), sum(Memory_Utilisation)/count(Memory_Utilisation), sum(Power_Consumption) from nodedata where Timestamp=? group by Rack_No;";

		DBUtil util = new DBUtil();
		Connection conn = util.getConnection();
		PreparedStatement pstmt;
		HashMap<String, List<String>> rackInfoMap = new HashMap<String, List<String>>();
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < timeList.size(); i++) {
				pstmt.setDouble(1, timeList.get(i));
				ResultSet rs = pstmt.executeQuery();
				String timestamp = "Time-" + timeList.get(i);
				List<String> rackInfoList = new ArrayList<String>();
				while (rs.next()) {
					int rackNo = rs.getInt(1);
					double rackDisplayUtil = 0;
					if (displayInfo.contains("cpu")) {
						rackDisplayUtil = rs.getDouble(2);
					} else if (displayInfo.contains("disk")) {
						rackDisplayUtil = rs.getDouble(3);
					} else if (displayInfo.contains("memory")) {
						rackDisplayUtil = rs.getDouble(4);
					} else if (displayInfo.contains("power")) {
						rackDisplayUtil = rs.getDouble(5);
					} else {
					}
					String info = "RackNo:" + rackNo + "/rackDisplayUtil:"
							+ rackDisplayUtil;
					rackInfoList.add(info);
					// System.out.println("rr:"+info);
				}
				rackInfoMap.put(timestamp, rackInfoList);

			}
			return rackInfoMap;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(conn);
		}
		return null;
	}

	public HashMap<String, List<String>> getNodeInfo(String displayInfo,
			int rackNo, List<Double> timeList) {
		String sql = "SELECT * FROM nodedata where Rack_No = ? and Timestamp=?";
		DBUtil util = new DBUtil();
		Connection conn = util.getConnection();
		PreparedStatement pstmt;
		HashMap<String, List<String>> nodeInfoMap = new HashMap<String, List<String>>();

		try {
			pstmt = conn.prepareStatement(sql);

			for (int i = 0; i < timeList.size(); i++) {
				pstmt.setInt(1, rackNo);
				pstmt.setDouble(2, timeList.get(i));
				ResultSet rs = pstmt.executeQuery();
				String timestamp = "Time-" + timeList.get(i);
				List<String> nodeInfoList = new ArrayList<String>();
				while (rs.next()) {
					int nodeNo = rs.getInt(2);
					// int rackNo = rs.getInt(3);
					double nodeDisplayUtil = 0;
					if (displayInfo.contains("cpu")) {
						nodeDisplayUtil = rs.getDouble(4);
					} else if (displayInfo.contains("disk")) {
						nodeDisplayUtil = rs.getDouble(5);
					} else if (displayInfo.contains("memory")) {
						nodeDisplayUtil = rs.getDouble(6);
					} else if (displayInfo.contains("power")) {
						nodeDisplayUtil = rs.getDouble(7);
					} else {
					}
					String info = "NodeNo:" + nodeNo + "/nodesDisplayUtil:"
							+ nodeDisplayUtil;
					// System.out.println("nn:"+info);
					nodeInfoList.add(info);
				}
				nodeInfoMap.put(timestamp, nodeInfoList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(conn);
		}
		return nodeInfoMap;
	}

	public HashMap<String, List<String>> getVMInfo(String displayInfo,
			int nodeNo, List<Double> timeList) {
		String sql = "SELECT * FROM vmdata where Node_No = ? and Timestamp = ?";
		DBUtil util = new DBUtil();
		Connection conn = util.getConnection();
		PreparedStatement pstmt;
		HashMap<String, List<String>> vmInfoMap = new HashMap<String, List<String>>();

		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < timeList.size(); i++) {
				pstmt.setInt(1, nodeNo);
				pstmt.setDouble(2, timeList.get(i));
				ResultSet rs = pstmt.executeQuery();
				String timestamp = "Time-" + timeList.get(i);
				List<String> vmInfoList = new ArrayList<String>();
				while (rs.next()) {
					int vmNo = rs.getInt(2);
					// int nodeNo = rs.getInt(3);
					double vmDisplayUtil = 0;
					if (displayInfo.contains("cpu")) {
						vmDisplayUtil = rs.getDouble(4);
					} else if (displayInfo.contains("disk")) {
						vmDisplayUtil = rs.getDouble(5);
					} else if (displayInfo.contains("memory")) {
						vmDisplayUtil = rs.getDouble(6);
					} else {
					}
					String info = "VMNo:" + vmNo + "/vmDisplayUtil:"
							+ vmDisplayUtil;
					// System.out.println("vm:"+info);
					vmInfoList.add(info);
				}
				vmInfoMap.put(timestamp, vmInfoList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(conn);
		}
		return vmInfoMap;
	}

	public void displayRackInfo(ActionRequest request, ActionResponse response) {
		String rackNo = ParamUtil.getString(request, "button");
		String displayInfo = ParamUtil.getString(request, "displayInfo");
		List<Double> timeList = getTimeList();
		HashMap<String, List<String>> nodeInfo = getNodeInfo(displayInfo,
				Integer.parseInt(rackNo), timeList);
		System.out.println("RackNo: " + rackNo);
		request.setAttribute("rackNo", rackNo);
		request.setAttribute("displayInfo", displayInfo);
		request.setAttribute("nodeInfoMap", nodeInfo);

		PortletSession session = request.getPortletSession();
		HashMap<String, List<String>> rackInfo = getRackInfo(displayInfo,
				timeList);
		session.setAttribute("rackGauge", rackInfo,
				PortletSession.APPLICATION_SCOPE);
		session.setAttribute("nodeTable", nodeInfo,
				PortletSession.APPLICATION_SCOPE);
		session.setAttribute("rackNo", rackNo, PortletSession.APPLICATION_SCOPE);
		response.setRenderParameter("mvcPath", "/html/dc/rack_info.jsp");
	}

	public void displayNodeInfo(ActionRequest request, ActionResponse response) {
		String rackNo = ParamUtil.getString(request, "rackNo");
		String nodeNo = ParamUtil.getString(request, "button");
		List<Double> timeList = getTimeList();
		String displayInfo = ParamUtil.getString(request, "displayInfo");
		HashMap<String, List<String>> vmInfo = getVMInfo(displayInfo,
				Integer.parseInt(nodeNo), timeList);
		System.out.println("RackNo: " + rackNo);
		System.out.println("NodeNo: " + nodeNo);
		request.setAttribute("vmInfoMap", vmInfo);
		request.setAttribute("nodeNo", nodeNo);
		request.setAttribute("rackNo", rackNo);
		request.setAttribute("displayInfo", displayInfo);

		PortletSession session = request.getPortletSession();
		HashMap<String, List<String>> nodeInfo = getNodeInfo(displayInfo,
				Integer.parseInt(rackNo), timeList);
		session.setAttribute("nodeGauge", nodeInfo,
				PortletSession.APPLICATION_SCOPE);
		session.setAttribute("vmTable", vmInfo,
				PortletSession.APPLICATION_SCOPE);
		session.setAttribute("nodeNo", nodeNo, PortletSession.APPLICATION_SCOPE);
		response.setRenderParameter("mvcPath", "/html/dc/node_info.jsp");
	}

	public void displayVMInfo(ActionRequest request, ActionResponse response) {
		String vmNo = ParamUtil.getString(request, "button");
		String nodeNo = ParamUtil.getString(request, "nodeNo");
		String rackNo = ParamUtil.getString(request, "rackNo");
		String displayInfo = ParamUtil.getString(request, "displayInfo");
		List<Double> timeList = getTimeList();
		HashMap<String, List<String>> vmInfo = getVMInfo(displayInfo,
				Integer.parseInt(nodeNo), timeList);
		System.out.println("RackNo: " + rackNo);
		System.out.println("NodeNo: " + nodeNo);
		System.out.println("VMNo: " + vmNo);
		request.setAttribute("nodeNo", nodeNo);
		request.setAttribute("rackNo", rackNo);
		request.setAttribute("vmInfoMap", vmInfo);
		request.setAttribute("displayInfo", displayInfo);

		PortletSession session = request.getPortletSession();
		session.setAttribute("vmGauge", vmInfo,
				PortletSession.APPLICATION_SCOPE);
		session.setAttribute("vmNo", vmNo, PortletSession.APPLICATION_SCOPE);
		response.setRenderParameter("mvcPath", "/html/dc/node_info.jsp");
	}

	@Override
	public void doView(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {
		List<Double> timeList = getTimeList();
		String displayInfo = getDisplayInfo(request);
		System.out.println(displayInfo);
		HashMap<String, List<String>> rackInfo = getRackInfo(displayInfo,
				timeList);

		request.setAttribute("rackInfo", rackInfo);
		request.setAttribute("displayInfo", displayInfo);

		HashMap<String, String> dcInfo = getDCInfo(displayInfo, timeList);
		PortletSession session = request.getPortletSession();
		session.setAttribute("dcGauge", dcInfo,
				PortletSession.APPLICATION_SCOPE);
		session.setAttribute("rackTable", rackInfo,
				PortletSession.APPLICATION_SCOPE);
		super.doView(request, response);
	}

}
