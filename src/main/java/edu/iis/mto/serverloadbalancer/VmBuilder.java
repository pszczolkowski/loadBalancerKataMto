package edu.iis.mto.serverloadbalancer;

public class VmBuilder {

	public VmBuilder ofSize(int size) {
		return this;
	}

	public Vm build() {
		return new Vm();
	}

}
