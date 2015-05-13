package edu.iis.mto.serverloadbalancer;


import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasCurrentLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.ServerVmsCountMatcher.hasVmsCountOf;
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
	
	@Test
	public void balancingAServerWithTenSlotsCapacity_andOneSlotVm_serverShouldBeFilledInTenPercent(){
		Server theServer = a( server().withCapacity( 10 ) );
		Vm theVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theVm ) );
		
		assertThat( theServer , hasCurrentLoadPercentageOf( 10.0d ));
		assertThat( "the server should contain the vm" , theServer.contains( theVm ));
	}
	
	@Test
	public void balancingAServerWithEnoughRoom_shouldBeFilledWithAllVms(){
		Server theServer = a( server().withCapacity( 10 ) );
		Vm theFirstVm = a( vm().ofSize( 1 ) );
		Vm theSecondVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theFirstVm , theSecondVm ) );
		
		assertThat( theServer , hasVmsCountOf( 2 ));
		assertThat( "the server should contain the first vm" , theServer.contains( theFirstVm ));
		assertThat( "the server should contain the second vm" , theServer.contains( theSecondVm ));
	}
	
	@Test
	public void balancingTwoServers_vmShouldBeAssignedToLessLoadedOne(){
		Server moreLoadedServer = a( server().withCapacity( 100 ).withInitialLoadOf( 60.0d ) );
		Server lessLoadedServer = a( server().withCapacity( 100 ).withInitialLoadOf( 50.0d ) );
		Vm theVm = a( vm().ofSize( 10 ) );
		
		balance( aListOfServersWith( moreLoadedServer , lessLoadedServer ) , aListOfVmsWith( theVm ) );
		
		assertThat( "the less loaded server should contain the vm" , lessLoadedServer.contains( theVm ));
		assertThat( "the more loaded server should contain the vm" , !moreLoadedServer.contains( theVm ));
	}
	
	@Test
	public void balancingAServerWithNotEnoughRoom_shouldNotBeFilledWithTheVm(){
		Server theServer = a( server().withCapacity( 100 ).withInitialLoadOf( 95.0d ) );
		Vm theVm = a( vm().ofSize( 10 ) );
		
		balance( aListOfServersWith( theServer ) , aListOfVmsWith( theVm ) );
		
		assertThat( "the server should not contain the vm" , !theServer.contains( theVm ));
	}
	
	@Test
	public void balanceServersAndVms(){
		Server theFirstServer = a( server().withCapacity( 4 ) );
		Server theSecondServer = a( server().withCapacity( 6 ) );
		Vm theFirstVm = a( vm().ofSize( 1 ) );
		Vm theSecondVm = a( vm().ofSize( 4 ) );
		Vm theThirdVm = a( vm().ofSize( 2 ) );
		
		balance( aListOfServersWith( theFirstServer , theSecondServer ) , aListOfVmsWith( theFirstVm , theSecondVm , theThirdVm ) );
		
		assertThat( theFirstServer , hasVmsCountOf( 2 ));
		assertThat( theSecondServer , hasVmsCountOf( 1 ));
		assertThat( "the first server should contain the first vm" , theFirstServer.contains( theFirstVm ));
		assertThat( "the second server should contain the second vm" , theSecondServer.contains( theSecondVm ));
		assertThat( "the first server should contain the third vm" , theFirstServer.contains( theThirdVm ));
		
		assertThat( theFirstServer , hasCurrentLoadPercentageOf( 75.0d ));
		assertThat( theSecondServer , hasCurrentLoadPercentageOf( 66.66d ));
	}


	private Vm[] aListOfVmsWith(Vm...vms) {
		return vms;
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

	private Server[] aListOfServersWith(Server...servers) {
		return servers;
	}

}
