package edu.iis.mto.serverloadbalancer;

import java.util.List;

public class ServerLoadBalancer {

	public void balance(List<Server> servers, List<Vm> vms) {
		Server server = servers.get( 0 );
		
		for( Vm vm : vms )
			server.addVm( vm );
		
	}

}
