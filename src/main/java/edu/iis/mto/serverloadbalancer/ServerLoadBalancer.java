package edu.iis.mto.serverloadbalancer;

import java.util.List;

public class ServerLoadBalancer {

	private static final double MAXIMUM_LOAD = 100.0d;

	public void balance(List<Server> servers, List<Vm> vms) {
		if( vms.size() > 0 ){ 
			Server server = servers.get( 0 );
			server.currentLoadPercentage = MAXIMUM_LOAD;
			server.addVm( vms.get( 0 ) );
		}
	}

}
