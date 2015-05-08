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
	public void balancingAServerWithOneSlot_noVms_serverShouldStayEmpty(){
		
		Server theServer = a( server().withCapacity( 1 ) );
		balance( aListOfServersWith( theServer ) , anEmptyListOfVms() );
		
		assertThat( theServer , hasLoadPercentageOf( 0.0d ));
	}

	private Matcher<? super Server> hasLoadPercentageOf(double loadPercentage) {
		return new CurrentLoadPercentageMatcher( loadPercentage );
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

	private Server a(ServerBuilder builder) {
		return builder.build();
	}

	private ServerBuilder server() {
		return new ServerBuilder();
	}

}
