package com.flabs.bezier_curves.shapes;

import java.util.ArrayList;

public class BezierCurve {

	private Pair[] pointList = new Pair[4];
	
	private ArrayList<Pair> bezierPlotPoints = new ArrayList<Pair>();
	
	private ArrayList<Fraction> plotPointSlopes = new ArrayList<Fraction>();
	
	private double mWeight = 1;
	
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
			
			int x = (int) ((p0.getFirstVal() * Math.pow((1 - tVal), 3)) + (p1.getFirstVal() * 3 * Math.pow((1 - tVal), 2) * tVal) + (p2.getFirstVal() * 3 * (1 - tVal) * Math.pow(tVal, 2)) + (p3.getFirstVal() * Math.pow(tVal, 3)));
			int y = (int) ((p0.getSecondVal() * Math.pow((1 - tVal), 3)) + (p1.getSecondVal() * 3 * Math.pow((1 - tVal), 2) * tVal) + (p2.getSecondVal() * 3 * (1 - tVal) * Math.pow(tVal, 2)) + (p3.getSecondVal() * Math.pow(tVal, 3)));
			
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
