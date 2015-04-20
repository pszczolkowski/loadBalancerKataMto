package edu.iis.mto.serverloadbalancer;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ContainingVmMatcher extends TypeSafeMatcher< Server > {

	private Vm vm;

	public ContainingVmMatcher(Vm vm) {
		this.vm = vm;
	}

	public void describeTo(Description description) {
		description.appendText( " a server contianing vm " ).appendValue( vm );
	}
	
	@Override
	protected void describeMismatchSafely(Server server,
			Description description) {
		description.appendText( " a server containing vms " ).appendValue( Arrays.asList( new Vm[]{ vm }) );
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return server.containsVm( vm );
	}
	
	public static Matcher<? super Server> containsVm(Vm theVm) {
		return new ContainingVmMatcher( theVm );
	}

}
