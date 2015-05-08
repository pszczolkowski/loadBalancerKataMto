package edu.iis.mto.serverloadbalancer;

import java.util.List;

public class ServerLoadBalancer {

	public void balance(List<Server> servers, List<Vm> vms) {
		for( Vm vm : vms ){
			servers.get(0).addVm( vm );
		}
	}

}
