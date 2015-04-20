package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class CurrentLoadPercentageMatcher extends TypeSafeMatcher< Server > {

	private double loadPercentage;

	public CurrentLoadPercentageMatcher(double loadPercentage) {
		this.loadPercentage = loadPercentage;
	}

	public void describeTo(Description description) {
		description.appendText( " a server with load percentage " ).appendValue( loadPercentage );
	}
	
	@Override
	protected void describeMismatchSafely(Server server,
			Description description) {
		description.appendText( " a server with load percentage " ).appendValue( server.currentLoadPercentage );
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return server.currentLoadPercentage == loadPercentage || Math.abs( server.currentLoadPercentage - loadPercentage ) < 0.01;
	}

}
