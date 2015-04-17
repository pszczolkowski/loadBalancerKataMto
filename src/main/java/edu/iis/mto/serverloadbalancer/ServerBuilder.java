package edu.iis.mto.serverloadbalancer;

public class ServerBuilder {
	
	private int capacity = 0;

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public Server build() {
		return new Server( capacity );
	}

}
