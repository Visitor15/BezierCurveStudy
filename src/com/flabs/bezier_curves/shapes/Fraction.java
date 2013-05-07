package com.flabs.bezier_curves.shapes;

public class Fraction {

	private int numerator;
	
	private int denominator;
	
	public Fraction(final int numerator, final int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	public int getNumerator() {
		return this.numerator;
	}
	
	public int getDenominator() {
		return this.denominator;
	}
	
	@Override
	public String toString() {
		return this.getNumerator() + "/" + this.getDenominator();
	}
}
