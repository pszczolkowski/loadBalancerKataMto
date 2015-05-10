package edu.iis.mto.serverloadbalancer;


import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasCurrentLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
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
		
		Assert.assertThat( theServer , hasCurrentLoadPercentageOf( 0.0d ));
	}
	
	@Test
	public void balancingAServerWithOneSlot_andOneSlotVm_serverShouldBeFilledWithTheVm(){
		Server theServer = a( server().withCapacity( 1 ) );
		Vm theVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theVm ) );
		
		Assert.assertThat( theServer , hasCurrentLoadPercentageOf( 100.0d ));
		Assert.assertTrue( "server should contain the vm" , theServer.contains( theVm ) );
	}

	
	private List<Vm> aListOfVmsWith(Vm...vms) {
		return Arrays.asList( vms );
	}

	private Vm a(VmBuilder builder) {
		return builder.build();
	}

	private VmBuilder vm() {
		return new VmBuilder();
	}

	private void balance(List<Server> servers,
			List<Vm> vms) {
		new ServerLoadBalancer().balance( servers , vms );
	}

	private List< Vm > anEmptyListOfVms() {
		return new ArrayList< Vm >();
	}

	private List< Server > aListOfServersWith(Server...servers) {
		return Arrays.asList( servers );
	}

	private Server a(ServerBuilder builder) {
		return builder.build();
	}
	
}
