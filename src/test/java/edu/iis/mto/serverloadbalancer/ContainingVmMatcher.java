package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ContainingVmMatcher extends TypeSafeMatcher< Server > {

	private Object vm;

	public ContainingVmMatcher(Vm vm) {
		this.vm = vm;
	}

	public void describeTo(Description description) {
		description.appendText( " a server containing the vm " ).appendValue( vm );
	}
	
	@Override
	protected void describeMismatchSafely(Server item,
			Description description) {
		description.appendText( " a server containing with vms " ).appendValue( item.getVms() );
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return server.getVms().contains( vm );
	}
	
	public static Matcher<? super Server> containsVm(Vm theVm) {
		return new ContainingVmMatcher( theVm );
	}

}
