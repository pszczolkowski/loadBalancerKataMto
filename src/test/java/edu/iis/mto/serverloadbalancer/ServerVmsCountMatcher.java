package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ServerVmsCountMatcher extends TypeSafeMatcher< Server > {

	private int expectedVmQuantity;

	public ServerVmsCountMatcher(int expectedVmQuantity) {
		this.expectedVmQuantity = expectedVmQuantity;
	}

	public void describeTo(Description description) {
		description.appendText( " a server with " ).appendValue( expectedVmQuantity ).appendText( " vms" );
	}

	@Override
	protected void describeMismatchSafely(Server server,
			Description description) {
		description.appendText( " a server with " ).appendValue( server.vmsCount() ).appendText( " vms" );
	}
	
	@Override
	protected boolean matchesSafely(Server server) {
		return server.vmsCount() == expectedVmQuantity;
	}

}
