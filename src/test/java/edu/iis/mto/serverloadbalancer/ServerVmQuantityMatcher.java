package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ServerVmQuantityMatcher extends TypeSafeMatcher< Server > {

	private int vmsQuantity;

	public ServerVmQuantityMatcher(int vmsQuantity) {
		this.vmsQuantity = vmsQuantity;
	}

	public void describeTo(Description description) {
		description.appendText( " a server with " ).appendValue( vmsQuantity ).appendText( " vms" );
	}
	
	protected void describeMismatchSafely(Server server, Description description) {
		description.appendText( " a server with " ).appendValue( server.vmsCount() ).appendText( " vms" );
	};

	@Override
	protected boolean matchesSafely( Server server ) {
		return server.vmsCount() == vmsQuantity;
	}
	
	public static Matcher<? super Server> hasVmCountof(int vmsQuantity) {
		return new ServerVmQuantityMatcher( vmsQuantity );
	}

}
