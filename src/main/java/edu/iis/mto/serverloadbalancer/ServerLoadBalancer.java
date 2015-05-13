package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {

	public void balance(Server[] servers, Vm[] vms) {
		for( Vm vm: vms ){
			Server leastLoadedServer = findLeastLoadedServerIn(servers);
			
			if( leastLoadedServer != null )
				leastLoadedServer.addVm( vm );
		}
	}

	private Server findLeastLoadedServerIn(Server[] servers) {
		Server leastLoadedServer = null;
		for( Server server : servers ){
			if( leastLoadedServer == null || server.currentLoadPercentage <= leastLoadedServer.currentLoadPercentage ){
				leastLoadedServer = server;
			}
		}
		return leastLoadedServer;
	}

}
