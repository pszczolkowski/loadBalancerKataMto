package edu.iis.mto.serverloadbalancer;


public class Server {

	public int capacity;
	public double currentLoadPercentage;

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public boolean containsVm(Vm vm) {
		return true;
	}

}
