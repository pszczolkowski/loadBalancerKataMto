package edu.iis.mto.serverloadbalancer;


import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.ServerVmsCountMatcher.hasVmCountOf;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ServerLoadBalancerTest {
	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
	public void balancingAServerWithOneSlot_noVms_serverShouldStayEmpty(){
		Server theServer = a( server().withCapacity( 1 ) );
		
		balance( aListOfServersWith( theServer ) , anEmptyListOfVms() );
		
		assertThat( theServer , hasLoadPercentageOf( 0.0d ));
	}
	
	@Test
	public void balancingAServerWithOneSlot_andOneSlotVm_serverShouldBeFilledWithTheCm(){
		Server theServer = a( server().withCapacity( 1 ) );
		Vm theVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theVm ) );
		
		assertThat( theServer , hasLoadPercentageOf( 100.0d ));
		assertThat( "servers should contain the vm" , theServer.contains( theVm )  );
	}
	
	@Test
	public void balancingAServerWithTenSlots_andOneSlotVm_serverShouldBeFilledInTenPercent(){
		Server theServer = a( server().withCapacity( 10 ) );
		Vm theVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theVm ) );
		
		assertThat( theServer , hasLoadPercentageOf( 10.0d ));
		assertThat( "server should contain the vm" , theServer.contains( theVm )  );
	}
	
	@Test
	public void balancingAServerWithEnoughRCapacity_shouldBeFilledWithAllVms(){
		Server theServer = a( server().withCapacity( 10 ) );
		Vm theFirstVm = a( vm().ofSize( 1 ) );
		Vm theSecondVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theFirstVm , theSecondVm ) );
		
		assertThat( theServer , hasVmCountOf( 2 ));
		assertThat( "server should contain the first vm" , theServer.contains( theFirstVm )  );
		assertThat( "server should contain the second vm" , theServer.contains( theSecondVm )  );
	}
	
	@Test
	public void balancingTwoServers_vmShouldBeAssignedToLessLoadedOne(){
		Server moreLoadedServer = a( server().withCapacity( 100 ).withInitialLoadOf( 60.0d ) );
		Server lessLoadedServer = a( server().withCapacity( 100 ).withInitialLoadOf( 50.0d ) );
		Vm theVm = a( vm().ofSize( 10 ) );
		
		balance( aListOfServersWith( moreLoadedServer , lessLoadedServer ) , aListOfVmsWith( theVm ) );
		
		assertThat( "less server should contain the vm" , lessLoadedServer.contains( theVm )  );
		assertFalse( "more server should not contain the vm" , moreLoadedServer.contains( theVm )  );
	}
	
	@Test
	public void balancingAServerWithNotEnoughCapacity_shouldNotBeFilledWithVm(){
		Server theServer = a( server().withCapacity( 100 ).withInitialLoadOf( 95.0d ) );
		Vm theVm = a( vm().ofSize( 10 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theVm ) );
		
		assertFalse( "server should not contain the vm" , theServer.contains( theVm )  );
	}
	
	@Test
	public void balanceServersAndVms(){
		Server theFirstServer = a( server().withCapacity( 4 ) );
		Server theSecondServer = a( server().withCapacity( 6 ) );
		Vm theFirstVm = a( vm().ofSize( 1 ) );
		Vm theSecondVm = a( vm().ofSize( 4 ) );
		Vm theThirdVm = a( vm().ofSize( 2 ) );
		
		balance( aListOfServersWith( theFirstServer , theSecondServer ) , aListOfVmsWith( theFirstVm , theSecondVm , theThirdVm ) );
		
		assertThat( theFirstServer , hasVmCountOf( 2 ));
		assertThat( theSecondServer , hasVmCountOf( 1 ));
		assertThat( "first server should contain the first vm" , theFirstServer.contains( theFirstVm )  );
		assertThat( "second server should contain the second vm" , theSecondServer.contains( theSecondVm )  );
		assertThat( "first server should contain the third vm" , theFirstServer.contains( theThirdVm )  );
		
		assertThat( theFirstServer , hasLoadPercentageOf( 75.0d ));
		assertThat( theSecondServer , hasLoadPercentageOf( 66.66d ));
	}
	

	private List<Vm> aListOfVmsWith(Vm... vms) {
		return Arrays.asList( vms );
	}

	private <T> T a(Builder< T > builder) {
		return builder.build();
	}

	private void balance(List<Server> servers,
			List<Vm> vms) {
		new ServerLoadBalancer().balance( servers , vms );
	}

	private List< Vm > anEmptyListOfVms() {
		return new ArrayList< Vm >();
	}

	private List< Server > aListOfServersWith(Server... servers) {
		return Arrays.asList( servers );
	}

}
