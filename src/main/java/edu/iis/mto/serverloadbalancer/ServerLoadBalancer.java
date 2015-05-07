package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class ServerLoadBalancer {

	public void balance(List<Server> servers, List<Vm> vms) {
		for( Vm vm : vms ){
			List< Server > serversWithEnoughCapacity = new ArrayList< Server >();
			for( Server server : servers ){
				if( server.canFit( vm ) ){
					serversWithEnoughCapacity.add( server );
				}
			}
			
			Server leastLoadedServer = findLeastLoadedServer(serversWithEnoughCapacity);
			
			if( leastLoadedServer != null )
				leastLoadedServer.addVm( vm );
		}
	}

	private Server findLeastLoadedServer(List<Server> servers) {
		Server leastLoadedServer = null;
		for( Server server : servers ){
			if( leastLoadedServer == null || leastLoadedServer.getCurrentLoadPercentage() > server.getCurrentLoadPercentage() ){
				leastLoadedServer = server;
			}
		}
		return leastLoadedServer;
	}

}
