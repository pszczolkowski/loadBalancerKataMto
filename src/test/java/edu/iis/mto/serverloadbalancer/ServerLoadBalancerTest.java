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
	public void balancingOneServer_noVm_serverShouldStayEmpty(){
		Server theServer = a( server().withCapacity( 1 ) );
		
		balance( aListOfServersWith( theServer ) , anEmptyListOfVms() );
		
		assertThat( theServer , hasLoadPercentageOf( 0.0d ));
	}
	
	@Test
	public void balancingOneServerWithOneSlot_andOneSlotVm_serverShouldBeFilledWithTheVm(){
		Server theServer = a( server().withCapacity( 1 ) );
		Vm theVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theVm ) );
		
		assertThat( theServer , hasLoadPercentageOf( 100.0d ));
		assertThat( "the server should contain the vm" , theServer.contains( theVm ) );
	}
	
	@Test
	public void balancingServerWithTenSlots_andOneSlotVm_serverShouldBeFilledInTenPercent(){
		Server theServer = a( server().withCapacity( 10 ) );
		Vm theVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theVm ) );
		
		assertThat( theServer , hasLoadPercentageOf( 10.0d ));
		assertThat( "the server should contain the vm" , theServer.contains( theVm ) );
	}
	
	@Test
	public void balancingOneServerWithEnoughSlots_shouldBeFilledWithAllVms(){
		Server theServer = a( server().withCapacity( 10 ) );
		Vm theFirstVm = a( vm().ofSize( 1 ) );
		Vm theSecondVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theFirstVm , theSecondVm ) );
		
		assertThat( theServer , hasVmCountOf( 2 ));
		assertThat( "the server should contain the first vm" , theServer.contains( theFirstVm ) );
		assertThat( "the server should contain the second vm" , theServer.contains( theSecondVm ) );
	}
	
	@Test
	public void balancingTwoServers_vmShouldBeAssignedToLessLoadedServer(){
		Server theFirstServer = a( server().withCapacity( 100 ).withLoadPercentageOf( 50.0d ) );
		Server theSecondServer = a( server().withCapacity( 100 ).withLoadPercentageOf( 45.0d ) );
		Vm theVm = a( vm().ofSize( 10 ) );
		
		balance( aListOfServersWith( theFirstServer , theSecondServer ) , aListOfVmsWith( theVm ) );
		
		assertThat( "the less loaded server should contain the vm" , theSecondServer.contains( theVm ) );
		assertFalse( "the more loaded server should not contain the vm" ,  theFirstServer.contains( theVm ) );
	}
	
	@Test
	public void balancingAServerWithNotEnoughCapacity_shouldNotBeFilledWithAVm(){
		Server theServer = a( server().withCapacity( 100 ).withLoadPercentageOf( 95.0d ) );
		Vm theVm = a( vm().ofSize( 10 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theVm ) );
		
		assertFalse( "the more loaded server should not contain the vm" ,  theServer.contains( theVm ) );
	}
	
	@Test
	public void balanceServersAnsVms(){
        Server server1 = a(server().withCapacity(4));
        Server server2 = a(server().withCapacity(6));
        
        Vm vm1 = a(vm().ofSize(1));
        Vm vm2 = a(vm().ofSize(4));
        Vm vm3 = a(vm().ofSize(2));
        
        balance(aListOfServersWith(server1, server2), aListOfVmsWith(vm1, vm2, vm3));
        
        assertThat("The server 1 should contain the vm 1", server1.contains(vm1));
        assertThat("The server 2 should contain the vm 2", server2.contains(vm2));
        assertThat("The server 1 should contain the vm 3", server1.contains(vm3));
        
        assertThat(server1, hasLoadPercentageOf(75.0d));
        assertThat(server2, hasLoadPercentageOf(66.66d));
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

	private List< Server > aListOfServersWith(Server...servers) {
		return Arrays.asList( servers );
	}

}
