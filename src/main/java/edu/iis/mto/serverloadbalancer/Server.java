package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;


public class Server {

	public static final double MAXIMUM_LOAD = 100.0d;
	public double currentLoadPercentage;
	private int capacity;
	private List< Vm > vms = new ArrayList<Vm>();

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
		vms.add(vm);
		this.currentLoadPercentage += loadOf(vm);
	}

	private double loadOf(Vm vm) {
		return MAXIMUM_LOAD * vm.getSize() / this.getCapacity();
	}

	public int vmsCount() {
		return vms.size();
	}

	public boolean canFit(Vm vm) {
		return this.currentLoadPercentage + loadOf(vm) <= MAXIMUM_LOAD;
	}

}
