package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class VmsQuantityMatcher extends TypeSafeMatcher< Server > {

	private int quantity;

	public VmsQuantityMatcher(int quantity) {
		this.quantity = quantity;
	}

	public void describeTo(Description description) {
		description.appendText( " a server with " ).appendValue( quantity ).appendText( " vms" );
	}
	
	@Override
	protected void describeMismatchSafely(Server server,
			Description description) {
		description.appendText( " a server with " ).appendValue( server.countVms() ).appendText( " vms" );
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return server.countVms() == quantity;
	}

}
