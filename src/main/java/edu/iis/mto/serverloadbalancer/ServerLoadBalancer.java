package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class ServerLoadBalancer {

	public void balance(Server[] servers, Vm[] vms) {
		for( Vm vm: vms ){
			List< Server > serversWithEnoughRoom = new ArrayList< Server >();
			for( Server server : servers ){
				if( server.canFit( vm ) ){
					serversWithEnoughRoom.add( server );
				}
			}
			
			Server leastLoadedServer = findLeastLoadedServerIn(serversWithEnoughRoom);
			
			if( leastLoadedServer != null )
				leastLoadedServer.addVm( vm );
		}
	}

	private Server findLeastLoadedServerIn(List< Server > servers) {
		Server leastLoadedServer = null;
		for( Server server : servers ){
			if( leastLoadedServer == null || server.currentLoadPercentage <= leastLoadedServer.currentLoadPercentage ){
				leastLoadedServer = server;
			}
		}
		return leastLoadedServer;
	}

}
