package edu.iis.mto.serverloadbalancer;


import static edu.iis.mto.serverloadbalancer.ContainingVmMatcher.containsVm;
import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static edu.iis.mto.serverloadbalancer.VmsQuantityMatcher.hasVmCountOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

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
	public void balanceOneServer_noVms_serverStaysEmpty(){
		Server theServer = a( server().withCapacity( 1 ) );
		
		balance( aListOfServersWith( theServer ) , anEmptyListOfVms() );
		
		assertThat( theServer , hasLoadPercentageOf( 0.0d ) );
	}
	
	@Test
	public void balanceOneServerWithOneSlot_andOneSlotVm_fillsTheServerWithTheVm(){
		Server theServer = a( server().withCapacity( 1 ) );
		Vm theVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theVm ) );
		
		assertThat( theServer , hasLoadPercentageOf( 100.0d ) );
		assertThat( theServer , containsVm( theVm ) );
	}
	
	@Test
	public void balanceOneServerWithTenSlotCapacity_andOneSlotVm_fillTheServerInTenPercent(){
		Server theServer = a( server().withCapacity( 10 ) );
		Vm theVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theVm ) );
		
		assertThat( theServer , hasLoadPercentageOf( 10.0d ) );
		assertThat( theServer , containsVm( theVm ) );
	}
	
	@Test
	public void balanceOnseServerWithEnoughCapacity_filledWithAllVms(){
		Server theServer = a( server().withCapacity( 10 ) );
		Vm theFirstVm = a( vm().ofSize( 1 ) );
		Vm theSecondVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theFirstVm , theSecondVm ) );
		
		assertThat( theServer , hasVmCountOf( 2 ) );
		assertThat( theServer , containsVm( theFirstVm ) );
		assertThat( theServer , containsVm( theSecondVm ) );
	}
	
	@Test
	public void balanceTwoServers_vmShouldBeAssignedToLeastLoadedServer(){
		Server lessLoadedServer = a( server().withCapacity( 10 ).withCurrentLoadOf( 45.0d ) );
		Server moreLoadedServer = a( server().withCapacity( 10 ).withCurrentLoadOf( 50.0d ) );
		Vm theVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( moreLoadedServer , lessLoadedServer ) , aListOfVmsWith( theVm ) );
		
		assertThat( lessLoadedServer , containsVm( theVm ) );
		// the test need to fail at invoking contains method from Server class
		// so it is necessary to check whether moreLoadedServer contains
		// the vm (it shouldn't)
		assertThat( moreLoadedServer , not( containsVm( theVm ) ) );
	}
	
	@Test
	public void balanceOneServerWithNotEnoughCapacity_shouldNotBeFilledWithTheVm(){
		Server theServer = a( server().withCapacity( 10 ).withCurrentLoadOf( 90.0d ) );
		Vm theVm = a( vm().ofSize( 2 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theVm ) );
		
		assertThat( theServer , not( containsVm( theVm ) ) );
	}
	
	@Test
	public void balanceServersAndVms(){
        Server server1 = a(server().withCapacity(4));
        Server server2 = a(server().withCapacity(6));
        
        Vm vm1 = a(vm().ofSize(1));
        Vm vm2 = a(vm().ofSize(4));
        Vm vm3 = a(vm().ofSize(2));
        
        balance(aListOfServersWith(server1, server2), aListOfVmsWith(vm1, vm2, vm3));
        
        assertThat( server1 , containsVm( vm1 ) );
        assertThat( server2 , containsVm( vm2 ) );
        assertThat( server1 , containsVm( vm3 ) );
        
        assertThat(server1, hasLoadPercentageOf(75.0d));
        assertThat(server2, hasLoadPercentageOf(66.66d));

	}
	

	private List<Vm> aListOfVmsWith(Vm... vms) {
		return new ArrayList< Vm >( Arrays.asList( vms ) );
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
		return new ArrayList< Server >( Arrays.asList( servers ));
	}
	
}
