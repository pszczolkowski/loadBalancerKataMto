package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Server> {

	private static final double EPSILON = 0.01;
	private double expectedLoadPercentage;

	public CurrentLoadPercentageMatcher(double expectedLoadPercentage) {
		this.expectedLoadPercentage = expectedLoadPercentage;
	}

	public void describeTo(Description description) {
		description.appendText( " a server with current load percentage of " ).appendValue( expectedLoadPercentage );
	}
	
	@Override
	protected void describeMismatchSafely(Server server,
			Description description) {
		description.appendText( " a server with current load percentage of " ).appendValue( server.currentLoadPercentage );
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return equalsDouble(server.currentLoadPercentage, expectedLoadPercentage);
	}

	private boolean equalsDouble(double d1, double d2) {
		return d1 == d2 || Math.abs( d1 - d2 ) < EPSILON;
	}

	public static Matcher<? super Server> hasCurrentLoadPercentageOf(double expectedLoadPercentage) {
		return new CurrentLoadPercentageMatcher( expectedLoadPercentage );
	}
	
}
