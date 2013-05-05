package com.flabs.bezier_curves.shapes;

import java.util.ArrayList;

public class BezierCurve {

	private Pair[] pointList = new Pair[4];
	
	private ArrayList<Pair> bezierPlotPoints = new ArrayList<Pair>();
	
	private double mWeight = 1;
	
	private double tVal = 0;
	
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
		
		
		for(int i = 0; i <= 11; i++) {
//			int x = (int) (((Math.pow((1 - tVal), 2)) * p0.getFirstVal()) + (2 * (1 - tVal) * (tVal * p1.getFirstVal())) + (Math.pow(tVal, 2) * p2.getFirstVal()));
//			int y = (int) (((Math.pow((1 - tVal), 2)) * p0.getSecondVal()) + (2 * (1 - tVal) * (tVal * p1.getSecondVal())) + (Math.pow(tVal, 2) * p2.getSecondVal()));
			
			int x = (int) ((p0.getFirstVal() * Math.pow((1 - tVal), 3)) + (p1.getFirstVal() * 3 * Math.pow((1 - tVal), 2) * tVal) + (p2.getFirstVal() * 3 * (1 - tVal) * Math.pow(tVal, 2)) + (p3.getFirstVal() * Math.pow(tVal, 3)));
			int y = (int) ((p0.getSecondVal() * Math.pow((1 - tVal), 3)) + (p1.getSecondVal() * 3 * Math.pow((1 - tVal), 2) * tVal) + (p2.getSecondVal() * 3 * (1 - tVal) * Math.pow(tVal, 2)) + (p3.getSecondVal() * Math.pow(tVal, 3)));
			
			System.out.println("GENERATED COORDINATE: " + x + " ," + y);
			
			addBezierCoordinate(x, y);
			
			tVal += 0.1;
		}
	}
	
	private void addBezierCoordinate(final int x, final int y) {
		bezierPlotPoints.add(new Pair(x, y));
	}
	
	public ArrayList<Pair> getBezierPlotPoints() {
		return this.bezierPlotPoints;
	}
}
