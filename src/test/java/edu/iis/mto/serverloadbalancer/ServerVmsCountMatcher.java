package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ServerVmsCountMatcher extends TypeSafeMatcher<Server> {

	private int expectedVmsCount;

	public ServerVmsCountMatcher(int expectedVmsCount) {
		this.expectedVmsCount = expectedVmsCount;
	}

	public void describeTo(Description description) {
		description.appendText( " a server with " ).appendValue( expectedVmsCount ).appendText( " vms" );
	}
	
	@Override
	protected void describeMismatchSafely(Server server,
			Description description) {
		description.appendText( " a server with " ).appendValue( server.vmsCount() ).appendText( " vms" );
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return server.vmsCount() == expectedVmsCount;
	}
	
	public static Matcher<? super Server> hasVmsCountOf(int expectedVmsCount) {
		return new ServerVmsCountMatcher( expectedVmsCount );
	}

}
