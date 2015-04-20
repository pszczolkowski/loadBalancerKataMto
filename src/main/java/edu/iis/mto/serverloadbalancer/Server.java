package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;


public class Server {

	public static final double MAXIMUM_LOAD = 100.0d;
	private int capacity;
	private double currentLoadPercentage;
	private List< Vm > vms = new ArrayList<Vm>();

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public boolean containsVm(Vm vm) {
		return vms.contains( vm );
	}

	public void addVm(Vm vm) {
		vms.add( vm );
		this.currentLoadPercentage = MAXIMUM_LOAD * vm.getSize() / this.capacity;
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercentage;
	}

	public int countVms() {
		return vms.size();
	}

}
