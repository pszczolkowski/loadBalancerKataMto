package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class ServerLoadBalancer {

	public void balance(List<Server> servers, List<Vm> vms) {
		for( Vm vm: vms ){
			addVmToCapableLeastLoadedServer(servers, vm);
		}
	}

	private void addVmToCapableLeastLoadedServer(List<Server> servers, Vm vm) {
		List<Server> serversWithEnoughCapacity = findServersWithEnoughCapacity(servers, vm);
		
		Server leastLoadedServer = findLeastLoadedServerIn( serversWithEnoughCapacity );
		
		if( leastLoadedServer != null )
			leastLoadedServer.addVm( vm );
	}

	private List<Server> findServersWithEnoughCapacity(List<Server> servers, Vm vm) {
		List< Server > serversWithEnoughCapacity = new ArrayList< Server >();
		for( Server server : servers ){
			if( server.canFit( vm ) ){
				serversWithEnoughCapacity.add( server );
			}
		}
		return serversWithEnoughCapacity;
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
