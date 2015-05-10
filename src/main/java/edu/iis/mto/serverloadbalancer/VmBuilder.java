package edu.iis.mto.serverloadbalancer;

public class VmBuilder implements Builder< Vm > {

	public VmBuilder ofSize(int size) {
		return this;
	}

	public Vm build() {
		return new Vm();
	}
	
	public static VmBuilder vm() {
		return new VmBuilder();
	}

}
