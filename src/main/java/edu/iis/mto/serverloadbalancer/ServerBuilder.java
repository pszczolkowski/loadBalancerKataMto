package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;

public class ServerBuilder implements Builder< Server > {

	private int capacity;
	private double initialLoadPercentage;

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public Server build() {
		Server server = new Server( capacity );
		
		addInitialLoad(server);
		
		return server;
	}

	private void addInitialLoad(Server server) {
		if( initialLoadPercentage > 0 ){
			int vmSize = (int)((double)capacity * (double)initialLoadPercentage / Server.MAXIMUM_LOAD);
			server.addVm( vm().ofSize( vmSize ).build() );
		}
	}

	public static ServerBuilder server() {
		return new ServerBuilder();
	}

	public Builder<Server> withLoadPercentageOf(double initialLoadPercentage) {
		this.initialLoadPercentage = initialLoadPercentage;
		return this;
	}

}
