package edu.iis.mto.serverloadbalancer;

import java.util.List;

public class ServerLoadBalancer {

	public void balance(List<Server> servers, List<Vm> vms) {
		if( vms.size() > 0 ){
			servers.get(0).addVm( vms.get( 0 ) );
		}
	}

}
