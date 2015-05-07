package edu.iis.mto.serverloadbalancer;

public class Server {

	private int capacity;
	private double currentLoadPercentage;

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
		 currentLoadPercentage = 100.0d * vm.getSize() / this.capacity;
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercentage;
	}

}
