package edu.iis.mto.serverloadbalancer;


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
	public void balancingServer_noVm_ServerStaysEmpty(){
		Server server = a( server().withCapacity( 1 ) );
		
		balance( aListOfServersWith( server ) , anEmptyListOfVms() );
		
		assertThat( server , hasLoadPercentageOf( 0.0d ) );
	}
	
	@Test
	public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm(){
		Server server = a( server().withCapacity( 1 ) );
		Vm theVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( server ) ,  aListOfVmsWith( theVm ) );
		
		assertThat( server , hasLoadPercentageOf( 100.0d ) );
		assertThat( server , containsVm( theVm ) );
	}
	
	@Test
	public void balancingOneServerWithTenSlotsCapacity_andOneSlotVm_fillTheServerWithTenPercent(){
		Server server = a( server().withCapacity( 10 ) );
		Vm theVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( server ) ,  aListOfVmsWith( theVm ) );
		
		assertThat( server , hasLoadPercentageOf( 10.0d ) );
		assertThat( server , containsVm( theVm ) );
	}
	
	@Test
	public void balancingAServerWithEnoughRoom_getsFilledWithAllVms(){
		Server server = a( server().withCapacity( 10 ) );
		Vm theFirstVm = a( vm().ofSize( 1 ) );
		Vm theSecondVm = a( vm().ofSize( 1 ) );
		
		balance( aListOfServersWith( server ) ,  aListOfVmsWith( theFirstVm , theSecondVm ) );
		
		assertThat( server , hasLoadPercentageOf( 20.0d ) );
		assertThat( server , hasVmCaountOf( 2 ) );
		assertThat( server , containsVm( theFirstVm ) );
		assertThat( server , containsVm( theSecondVm ) );
	}

	private Matcher<? super Server> containsVm(Vm theVm) {
		return new ContainingVmMatcher( theVm );
	}

	private List<Vm> aListOfVmsWith(Vm... theVms) {
		return new ArrayList< Vm >( Arrays.asList( theVms ) );
	}

	private void balance(List<Server> servers,
			List<Vm> vms) {
		ServerLoadBalancer balancer = new ServerLoadBalancer();
		balancer.balance( servers , vms );
	}

	private List< Vm > anEmptyListOfVms() {
		return new ArrayList< Vm >();
	}

	private List< Server > aListOfServersWith(Server... servers) {
		return new ArrayList< Server >( Arrays.asList( servers ) );
	}

	private < T > T a(Builder< T > builder) {
		return builder.build();
	}

}
