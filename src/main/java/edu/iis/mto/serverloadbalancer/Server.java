package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

	private int capacity;
	private double currentLoadPercentage;
	private List< Vm > vms = new ArrayList< Vm >();

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public boolean contains(Vm theVm) {
		return true;
	}

	public double getCapacity() {
		return capacity;
	}

	public void addVm(Vm vm) {
		vms.add( vm );
		currentLoadPercentage = 100.0d * vm.getSize() / this.capacity;
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercentage;
	}

	public int getVmsCount() {
		return vms.size();
	}

}
