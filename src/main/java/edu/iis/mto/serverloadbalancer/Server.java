package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

	public static final double MAXIMUM_LOAD = 100.0d;
	private int capacity;
	private double currentLoadPercentage;
	private List< Vm > vms = new ArrayList< Vm >();

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public boolean contains(Vm theVm) {
		return vms.contains( theVm );
	}

	public double getCapacity() {
		return capacity;
	}

	public void addVm(Vm vm) {
		vms.add( vm );
		currentLoadPercentage = loadOfVm(vm);
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercentage;
	}

	public int getVmsCount() {
		return vms.size();
	}

	public boolean canFit(Vm vm) {
		return currentLoadPercentage + loadOfVm(vm) <= MAXIMUM_LOAD;
	}

	private double loadOfVm(Vm vm) {
		return MAXIMUM_LOAD * vm.getSize() / this.capacity;
	}

}
