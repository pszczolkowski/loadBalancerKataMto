package edu.iis.mto.serverloadbalancer;

import java.util.List;

public class ServerLoadBalancer {

	public void balance(List<Server> servers, List<Vm> vms) {
		if( vms.size() > 0 ){ 
			Server server = servers.get( 0 );
			server.currentLoadPercentage = 100.0d;
			server.addVm( vms.get( 0 ) );
		}
	}

}
