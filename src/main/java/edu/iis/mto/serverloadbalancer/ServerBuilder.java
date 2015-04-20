package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;

public class ServerBuilder implements Builder< Server > {

	private int capacity;
	private double loadPercentage = 0;

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public Server build() {
		Server server = new Server( capacity );
		
		if( loadPercentage != 0 ){
			int vmSize = (int) (capacity * loadPercentage / 100.0d);
			server.addVm( vm().ofSize( vmSize ).build() );
		}
		
		return server;
	}
	
	public static ServerBuilder server() {
		return new ServerBuilder();
	}

	public Builder<Server> withCurrentLoadOf(double loadPercentage) {
		this.loadPercentage = loadPercentage;
		return this;
	}

}
