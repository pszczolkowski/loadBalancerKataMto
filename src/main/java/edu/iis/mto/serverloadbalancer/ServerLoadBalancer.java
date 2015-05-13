package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {

	public void balance(Server[] servers, Vm[] vms) {
		for( Vm vm: vms ){
			Server leastLoadedServer = null;
			for( Server server : servers ){
				if( leastLoadedServer == null || server.currentLoadPercentage <= leastLoadedServer.currentLoadPercentage ){
					leastLoadedServer = server;
				}
			}
			
			if( leastLoadedServer != null )
				leastLoadedServer.addVm( vm );
		}
	}

}
