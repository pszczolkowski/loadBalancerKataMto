package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {
	
	private static final int MAXIMUM_LOAD = 100;
	private int capacity;
	private double currentLoadPercentage = 0;
	private List<Vm> vms = new ArrayList<Vm>();

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public List< Vm > getVms() {
		return vms ;
	}

	public void addVm(Vm vm) {
		vms.add( vm );
		currentLoadPercentage += vm.getSize() / this.capacity * MAXIMUM_LOAD;
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercentage;
	}

}
