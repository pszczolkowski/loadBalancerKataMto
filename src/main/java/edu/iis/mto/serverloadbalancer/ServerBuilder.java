package edu.iis.mto.serverloadbalancer;

public class ServerBuilder implements Builder< Server > {
	
	private int capacity = 0;
	private double loadPercentage = 0;

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public Server build() {
		Server server = new Server( capacity );
		
		if( loadPercentage > 0.0 ){
			int vmSize = (int) (capacity * (loadPercentage / 100));
			server.addVm( VmBuilder.vm().ofSize( vmSize ).build() );
		}
		
		return server; 
	}
	
	public static ServerBuilder server() {
		return new ServerBuilder();
	}

	public Builder<Server> withLoadPercentage(double loadPercentage) {
		this.loadPercentage = loadPercentage;
		return this;
	}

}
