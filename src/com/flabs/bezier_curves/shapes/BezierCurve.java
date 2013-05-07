package com.flabs.bezier_curves.shapes;

import java.util.ArrayList;

public class BezierCurve {

	private Pair[] pointList = new Pair[4];
	
	private ArrayList<Pair> bezierPlotPoints = new ArrayList<Pair>();
	
	private ArrayList<Fraction> plotPointSlopes = new ArrayList<Fraction>();
	
	private double mWeight = 2;
	
	private double tVal = 0;
	
	public BezierCurve() {
		
	}
	
	public BezierCurve(Pair p0, Pair p1, Pair p2, Pair p3) {
		pointList[0] = p0;
		pointList[1] = p1;
		pointList[2] = p2;
		pointList[3] = p3;
		
		calcBezierCurve();
	}
	
	private void calcBezierCurve() {
		Pair p0 = pointList[0];
		Pair p1 = pointList[1];
		Pair p2 = pointList[2];
		Pair p3 = pointList[3];
		
		
		for(int i = 0; tVal <= 1.0; i++) {
//			int x = (int) (((Math.pow((1 - tVal), 2)) * p0.getFirstVal()) + (2 * (1 - tVal) * (tVal * p1.getFirstVal())) + (Math.pow(tVal, 2) * p2.getFirstVal()));
//			int y = (int) (((Math.pow((1 - tVal), 2)) * p0.getSecondVal()) + (2 * (1 - tVal) * (tVal * p1.getSecondVal())) + (Math.pow(tVal, 2) * p2.getSecondVal()));
			
//			double p0WeightedFirstVal = (mWeight / (1 + mWeight)) * p0.getFirstVal();
//			double p0WeightedSecondVal = (mWeight / (1 + mWeight)) * p0.getSecondVal();
//			
//			double p1WeightedFirstVal = (mWeight / (1 + mWeight)) * p1.getFirstVal();
//			double p1WeightedSecondVal = (mWeight / (1 + mWeight)) * p1.getSecondVal();
//			
//			double p2WeightedFirstVal = (mWeight / (1 + mWeight)) * p2.getFirstVal();
//			double p2WeightedSecondVal = (mWeight / (1 + mWeight)) * p2.getSecondVal();
//			
//			double p3WeightedFirstVal = (mWeight / (1 + mWeight)) * p3.getFirstVal();
//			double p3WeightedSecondVal = (mWeight / (1 + mWeight)) * p3.getSecondVal();
			
			double p0WeightedFirstVal = p0.getFirstVal();
			double p0WeightedSecondVal = p0.getSecondVal();
			
			double p1WeightedFirstVal = p1.getFirstVal();
			double p1WeightedSecondVal = p1.getSecondVal();
			
			double p2WeightedFirstVal = p2.getFirstVal();
			double p2WeightedSecondVal = p2.getSecondVal();
			
			double p3WeightedFirstVal = p3.getFirstVal();
			double p3WeightedSecondVal = p3.getSecondVal();
					
			int x = (int) ((p0WeightedFirstVal * Math.pow((1 - tVal), 3)) + (p1WeightedFirstVal * 3 * Math.pow((1 - tVal), 2) * tVal) + (p2WeightedFirstVal * 3 * (1 - tVal) * Math.pow(tVal, 2)) + (p3WeightedFirstVal * Math.pow(tVal, 3)));
			int y = (int) ((p0WeightedSecondVal * Math.pow((1 - tVal), 3)) + (p1WeightedSecondVal * 3 * Math.pow((1 - tVal), 2) * tVal) + (p2WeightedSecondVal * 3 * (1 - tVal) * Math.pow(tVal, 2)) + (p3WeightedSecondVal * Math.pow(tVal, 3)));
			
			System.out.println("GENERATED COORDINATE: " + x + " ," + y);
			
			addBezierCoordinate(x, y);
			
			tVal += 0.02;
			
			if(i > 0) {
				plotPointSlopes.add(calcSlope(this.getBezierPlotPoints().get(i - 1), this.getBezierPlotPoints().get(i)));
			}
			else {
				plotPointSlopes.add(calcSlope(this.getBezierPlotPoints().get(i), this.getBezierPlotPoints().get(i)));
			}
		}
	}
	
	public Fraction calcSlope(final Pair p0, final Pair p1) {
		int deltaX = p1.getFirstVal() - p0.getFirstVal();
		int deltaY = p1.getSecondVal() - p0.getSecondVal();
		
		Fraction fraction = new Fraction(deltaX, deltaY);
		
		System.out.println("ADDING SLOPE: " + fraction.toString());
		
		return fraction;
	}
	
	public void addBezierCoordinate(final int x, final int y) {
		bezierPlotPoints.add(new Pair(x, y));
		
		if(bezierPlotPoints.size() > 1) {
			plotPointSlopes.add(calcSlope(this.getBezierPlotPoints().get(bezierPlotPoints.size() - 2), this.getBezierPlotPoints().get(bezierPlotPoints.size() - 1)));
		}
		else {
			plotPointSlopes.add(calcSlope(this.getBezierPlotPoints().get(bezierPlotPoints.size() - 1), this.getBezierPlotPoints().get(bezierPlotPoints.size() - 1)));
		}
	}
	
	public ArrayList<Pair> getBezierPlotPoints() {
		return this.bezierPlotPoints;
	}
	
	public ArrayList<Fraction> getBezierPlotPointSlopes() {
		return this.plotPointSlopes;
	}
}
