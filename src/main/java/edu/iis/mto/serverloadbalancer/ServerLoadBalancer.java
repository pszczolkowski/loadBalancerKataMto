package edu.iis.mto.serverloadbalancer;

import java.util.List;

public class ServerLoadBalancer {

	public void balance(List<Server> servers, List<Vm> vms) {
		for( Vm vm : vms ){
			Server server = servers.get( 0 );
			server.addVm( vm );
		}
	}

}
