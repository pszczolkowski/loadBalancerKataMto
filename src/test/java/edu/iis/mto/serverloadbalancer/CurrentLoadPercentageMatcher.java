package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Server> {

	private double expectedLoadPercentage;

	public CurrentLoadPercentageMatcher(double expectedLoadPercentage) {
		this.expectedLoadPercentage = expectedLoadPercentage;
	}

	public void describeTo(Description description) {
		description.appendText(" a server with load perecentage of ")
				.appendValue(expectedLoadPercentage);
	}

	@Override
	protected void describeMismatchSafely(Server item,
			Description description) {
		description.appendText( " a server with load perecentage of " ).appendValue( item.getCurrentLoadPercentage() );
	}
	
	@Override
	protected boolean matchesSafely(Server server) {
		return equalsDouble(server.getCurrentLoadPercentage(), expectedLoadPercentage);
	}

	private boolean equalsDouble(double d1, double d2) {
		return d1 == d2 || Math.abs(d1 - d2) < 0.01;
	}

	public static Matcher<? super Server> hasLoadPercentageOf(
			double expectedLoadPercentage) {
		return new CurrentLoadPercentageMatcher(expectedLoadPercentage);
	}

}
