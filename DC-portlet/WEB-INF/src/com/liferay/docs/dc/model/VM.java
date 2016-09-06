package com.liferay.docs.dc.model;

public class VM {
	private int vm_No;
	private int node_No;
	private double cpu_Utilisation;
	private double disk_Utilisation;
	private double memory_Utilisation;
	public int getVm_No() {
		return vm_No;
	}
	public void setVm_No(int vm_No) {
		this.vm_No = vm_No;
	}
	public int getNode_No() {
		return node_No;
	}
	public void setNode_No(int node_No) {
		this.node_No = node_No;
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
	
}
