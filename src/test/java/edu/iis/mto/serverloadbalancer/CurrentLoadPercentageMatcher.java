package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class CurrentLoadPercentageMatcher extends TypeSafeMatcher< Server > {

	private static final double EPSILON = 0.01;
	private double loadPercentage;

	public CurrentLoadPercentageMatcher(double loadPercentage) {
		this.loadPercentage = loadPercentage;
	}

	public void describeTo(Description description) {
		description.appendText( " a server with load percentage of " ).appendValue( loadPercentage );
	}

	@Override
	protected void describeMismatchSafely(Server server,
			Description description) {
		description.appendText( " a server with load percentage of " ).appendValue( server.getCurrentLoadPercentage() );
	}
	
	@Override
	protected boolean matchesSafely(Server server) {
		return equalsDouble(server.getCurrentLoadPercentage(), loadPercentage);
	}

	private boolean equalsDouble(double d1, double d2) {
		return d1 == d2 || Math.abs( d1 - d2 ) < EPSILON;
	}
	
	public static Matcher<? super Server> hasLoadPercentageOf(double loadPercentage) {
		return new CurrentLoadPercentageMatcher( loadPercentage );
	}

}
