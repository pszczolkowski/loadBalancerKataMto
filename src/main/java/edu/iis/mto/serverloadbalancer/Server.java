package edu.iis.mto.serverloadbalancer;


public class Server {

	public double currentLoadPercentage;
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

}
