package edu.iis.mto.serverloadbalancer;

import java.util.List;

public class ServerLoadBalancer {

	public void balance(List<Server> servers, List<Vm> vms) {
		for( Vm vm: vms ){
			Server leastLoadedServer = findLeastLoadedServerIn(servers);
			
			if( leastLoadedServer != null )
				leastLoadedServer.addVm( vm );
		}
	}

	private Server findLeastLoadedServerIn(List<Server> servers) {
		Server leastLoadedServer = null;
		for( Server server : servers ){
			if( leastLoadedServer == null || server.getCurrentLoadPercentage() < leastLoadedServer.getCurrentLoadPercentage() ){
				leastLoadedServer = server;
			}
		}
		return leastLoadedServer;
	}

}
