package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;


public class Server {

	public static final double MAXIMUM_LOAD = 100.0d;
	private double currentLoadPercentage;
	private double capacity;
	private List< Vm > vms = new ArrayList< Vm >();

	public Server(double capacity) {
		this.capacity = capacity;
	}

	public boolean contains(Vm vm) {
		return vms.contains( vm );
	}

	public double getCapacity() {
		return this.capacity;
	}

	public void addVm(Vm vm) {
		vms.add( vm );
		this.currentLoadPercentage += loadOf(vm);
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercentage;
	}

	public int vmsCount() {
		return vms.size();
	}

	public boolean canFit(Vm vm) {
		return this.currentLoadPercentage + loadOf(vm) <= MAXIMUM_LOAD;
	}

	private double loadOf(Vm vm) {
		return MAXIMUM_LOAD * vm.getSize() / this.getCapacity();
	}

}
