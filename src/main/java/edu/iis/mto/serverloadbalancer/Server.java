package edu.iis.mto.serverloadbalancer;


public class Server {

	private static final double MAXIMUM_LOAD = 100.0d;
	private double currentLoadPercentage;
	private double capacity;

	public Server(double capacity) {
		this.capacity = capacity;
	}

	public boolean contains(Vm vm) {
		return true;
	}

	public double getCapacity() {
		return this.capacity;
	}

	public void addVm(Vm vm) {
		this.currentLoadPercentage = MAXIMUM_LOAD * vm.getSize() / this.getCapacity();
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercentage;
	}

}
