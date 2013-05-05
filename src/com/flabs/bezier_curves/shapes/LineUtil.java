package com.flabs.bezier_curves.shapes;

import java.util.ArrayList;

public class LineUtil {

	private static ArrayList<Pair> plotList = new ArrayList<Pair>();
	
	public static void addCoordinate(final int x, final int y) {
		plotList.add(new Pair(x, y));
	}
	
	public static ArrayList<Pair> getPlotList() {
		return LineUtil.plotList;
	}
	

}
