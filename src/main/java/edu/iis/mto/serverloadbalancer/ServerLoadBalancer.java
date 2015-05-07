package edu.iis.mto.serverloadbalancer;

import java.util.List;

public class ServerLoadBalancer {

	public void balance(List<Server> servers, List<Vm> vms) {
		for( Vm vm : vms ){
			Server leastLoadedServer = null;
			for( Server server : servers ){
				if( leastLoadedServer == null || leastLoadedServer.getCurrentLoadPercentage() > server.getCurrentLoadPercentage() ){
					leastLoadedServer = server;
				}
			}
			
			leastLoadedServer.addVm( vm );
		}
	}

}
