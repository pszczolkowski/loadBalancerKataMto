package edu.iis.mto.serverloadbalancer;


import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasCurrentLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class ServerLoadBalancerTest {
	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
	public void balancingOneServer_noVms_serverShouldStayEmpty(){
		Server theServer = a( server().withCapacity( 1 ) );
		
		balance( aListOfServersWith( theServer ) , anEmptyListOfVms() );
		
		assertThat( theServer , hasCurrentLoadPercentageOf( 0.0d ));
	}


	private void balance(Server[] servers, Vm[] vms) {
		new ServerLoadBalancer().balance( servers , vms );
	}

	private Vm[] anEmptyListOfVms() {
		return new Vm[]{};
	}

	private Server[] aListOfServersWith(Server theServer) {
		return new Server[]{ theServer };
	}

	private Server a(ServerBuilder builder) {
		return builder.build();
	}

}
