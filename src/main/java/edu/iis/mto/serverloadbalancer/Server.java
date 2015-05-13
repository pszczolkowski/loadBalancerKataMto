package edu.iis.mto.serverloadbalancer;


public class Server {

	private static final double MAXIMUM_LOAD = 100.0d;
	public double currentLoadPercentage;
	private int capacity;

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
		this.currentLoadPercentage = MAXIMUM_LOAD * vm.getSize() / this.getCapacity();
	}

}
