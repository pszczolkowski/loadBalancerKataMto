package edu.iis.mto.serverloadbalancer;


import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasCurrentLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
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
	
	@Test
	public void balancingAServerWithOneSlot_andOneSlotVm_serverShouldBeFilledWithTheVm(){
		Server theServer = a( server().withCapacity( 1 ) );
		Vm theVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theVm ) );
		
		assertThat( theServer , hasCurrentLoadPercentageOf( 100.0d ));
		assertThat( "the server should contain the vm" , theServer.contains( theVm ));
	}


	private Vm[] aListOfVmsWith(Vm theVm) {
		return new Vm[]{ theVm };
	}

	private <T> T a(Builder< T > builder) {
		return builder.build();
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

}
