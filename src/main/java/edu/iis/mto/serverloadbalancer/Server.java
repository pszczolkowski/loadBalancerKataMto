package edu.iis.mto.serverloadbalancer;


public class Server {

	private static final double MAXIMUM_LOAD = 100.0d;
	private int capacity;
	private double currentLoadPercentage;

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public boolean containsVm(Vm vm) {
		return true;
	}

	public void addVm(Vm vm) {
		this.currentLoadPercentage = MAXIMUM_LOAD * vm.getSize() / this.capacity;
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercentage;
	}

}
