package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class VmsQuantityMatcher extends TypeSafeMatcher< Server > {

	private int quantity;

	public VmsQuantityMatcher(int quantity) {
		this.quantity = quantity;
	}

	public void describeTo(Description description) {
		description.appendText( " a server containing " ).appendValue( quantity ).appendText( " vms" );
	}
	
	@Override
	protected void describeMismatchSafely(Server server,
			Description description) {
		description.appendText( " a server containing " ).appendValue( server.getVmsCount() ).appendText( " vms" );
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return server.getVmsCount() == quantity;
	}
	
	public static Matcher<? super Server> hasVmCaountOf(int quantity) {
		return new VmsQuantityMatcher( quantity );
	}

}
