package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;

public class ServerBuilder implements Builder< Server > {

	private int capacity;
	private double initialLoad;

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public Server build() {
		Server server = new Server( capacity );
		
		addInitialLoadTo(server);
		
		return server;
	}

	private void addInitialLoadTo(Server server) {
		if( initialLoad > 0 ){
			int vmSize = (int) (initialLoad * capacity / Server.MAXIMUM_LOAD);
			server.addVm( vm().ofSize( vmSize ).build() );
		}
	}

	public static ServerBuilder server() {
		return new ServerBuilder();
	}

	public Builder<Server> withInitialLoadOf(double initialLoad) {
		this.initialLoad = initialLoad;
		return this;
	}

}
