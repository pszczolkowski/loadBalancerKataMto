package edu.iis.mto.serverloadbalancer;


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

	private Matcher<? super Server> hasLoadPercentageOf(double expectedLoadPercentage) {
		return new CurrentLoadPercentageMatcher( expectedLoadPercentage );
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

	private Server a(ServerBuilder builder) {
		return builder.build();
	}

	private static ServerBuilder server() {
		return new ServerBuilder();
	}

}
