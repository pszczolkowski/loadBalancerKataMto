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
	protected boolean matchesSafely(Server server) {

		return server.currentLoadPercentage == expectedLoadPercentage
				|| Math.abs(server.currentLoadPercentage
						- expectedLoadPercentage) < 0.01;
	}

}
