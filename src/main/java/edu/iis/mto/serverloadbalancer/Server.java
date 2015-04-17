package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {
	
	private int capacity;
	public double currentLoadPercentage;
	private List<Vm> vms = new ArrayList<Vm>();

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public List< Vm > getVms() {
		return vms ;
	}

	public void addVm(Vm vm) {
		vms.add( vm );
		currentLoadPercentage += vm.getSize() / this.capacity * 100;
	}

}
