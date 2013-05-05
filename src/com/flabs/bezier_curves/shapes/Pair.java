package com.flabs.bezier_curves.shapes;


public  class Pair {
	
	private  int firstVal;

	private  int secondVal;

	public Pair(final int firstVal, final int secondVal) {
		this.firstVal = firstVal;
		this.secondVal = secondVal;
	}

	public int getFirstVal() {
		return firstVal;
	}

	public int getSecondVal() {
		return secondVal;
	}
}