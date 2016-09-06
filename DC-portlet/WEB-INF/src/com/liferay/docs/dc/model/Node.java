package com.liferay.docs.dc.model;

public class Node{
	private int node_No;
	private int rack_No;
	private double cpu_Utilisation;
	private double disk_Utilisation;
	private double memory_Utilisation;
	private double power_Consumption;
	
	public int getNode_No() {
		return node_No;
	}
	public void setNode_No(int node_No) {
		this.node_No = node_No;
	}
	public int getRack_No() {
		return rack_No;
	}
	public void setRack_No(int rack_No) {
		this.rack_No = rack_No;
	}
	public double getCpu_Utilisation() {
		return cpu_Utilisation;
	}
	public void setCpu_Utilisation(double cpu_Utilisation) {
		this.cpu_Utilisation = cpu_Utilisation;
	}
	public double getDisk_Utilisation() {
		return disk_Utilisation;
	}
	public void setDisk_Utilisation(double disk_Utilisation) {
		this.disk_Utilisation = disk_Utilisation;
	}
	public double getMemory_Utilisation() {
		return memory_Utilisation;
	}
	public void setMemory_Utilisation(double memory_Utilisation) {
		this.memory_Utilisation = memory_Utilisation;
	}
	public double getPower_Consumption() {
		return power_Consumption;
	}
	public void setPower_Consumption(double power_Consumption) {
		this.power_Consumption = power_Consumption;
	}
	
}