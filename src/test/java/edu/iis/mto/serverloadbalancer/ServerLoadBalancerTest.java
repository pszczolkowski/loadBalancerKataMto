package edu.iis.mto.serverloadbalancer;


import static edu.iis.mto.serverloadbalancer.ContainingVmMatcher.containsVm;
import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matcher;
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
	

	private Matcher<? super Server> hasVmCountOf(int quantity) {
		return new VmsQuantityMatcher( quantity );
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
