package com.flabs.bezier_curves.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.flabs.bezier_curves.shapes.BezierCurve;
import com.flabs.bezier_curves.shapes.Fraction;
import com.flabs.bezier_curves.shapes.LineUtil;
import com.flabs.bezier_curves.shapes.Pair;

public class MainUI extends JFrame {

	public static final int DEF_X_WIDTH = 1280;

	public static final int DEF_Y_HEIGHT = 800;

	private static int X_WIDTH;

	private static int Y_HEIGHT;

	private Canvas mainPanel;

	private ArrayList<BezierCurve> bezierCurveList = new ArrayList<BezierCurve>();

	public MainUI() {
		init();
	}

	public MainUI(final int xWidth, final int yHeight) {
		MainUI.X_WIDTH = xWidth;
		MainUI.Y_HEIGHT = yHeight;
	}

	private void init() {
		if(X_WIDTH == 0 || Y_HEIGHT == 0) {
			setDefaultDimensions();
		}

		this.setPreferredSize(new Dimension(X_WIDTH, Y_HEIGHT));
		this.setBackground(Color.BLACK);

		mainPanel = new Canvas();
		mainPanel.setSize(new Dimension(X_WIDTH, Y_HEIGHT));

		addMouseAdapter(mainPanel);
		this.add(mainPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void setDefaultDimensions() {
		X_WIDTH = DEF_X_WIDTH;
		Y_HEIGHT = DEF_Y_HEIGHT;
	}

	private void paintLine() {
		ArrayList<Pair> plotList = LineUtil.getPlotList();
		if(plotList.size() > 1) {
			Pair p0 = plotList.get(plotList.size() - 2);
			Pair p1 = plotList.get(plotList.size() - 1);

			Graphics line = mainPanel.getGraphics();
			line.setColor(Color.WHITE);
			line.drawLine(p0.getFirstVal(), p0.getSecondVal(), p1.getFirstVal(), p1.getSecondVal());
		}
		if(plotList.size() > 3) {
			Pair p0 = plotList.get(plotList.size() - 4);
			Pair p1 = plotList.get(plotList.size() - 3);
			Pair p2 = plotList.get(plotList.size() - 2);
			Pair p3 = plotList.get(plotList.size() - 1);

			BezierCurve bezierCurve = new BezierCurve(p0, p1, p2, p3);
			drawBezierCurve(bezierCurve);
			drawAverageBezierCurve();
		}
		else {
			System.out.println("NOT DRAWING ANYTHING");
		}
	}

	private void drawBezierCurve(final BezierCurve bezierCurve) {
		Graphics painter = mainPanel.getGraphics();
		ArrayList<Pair> plots = bezierCurve.getBezierPlotPoints();

		painter.setColor(Color.YELLOW);

		for(int i = 0; i < plots.size() - 2; i++) {
			Pair p0 = plots.get(i);
			Pair p1 = plots.get(i + 1);

			System.out.println("DRAWING CURVE AT: (" + p0.getFirstVal() + ", " + p0.getSecondVal() + ") & (" + p1.getFirstVal() + ", " + p1.getSecondVal() + ")");

			if(i % 4 == 0) {
				painter.fillOval(p1.getFirstVal() - 4, p1.getSecondVal() - 4, 8, 8);
			}

			painter.drawLine(p0.getFirstVal(), p0.getSecondVal(), p1.getFirstVal(), p1.getSecondVal());
		}
		
		bezierCurveList .add(bezierCurve);
	}
	
	private void drawAveragedBezierCurve(final BezierCurve bezierCurve) {
		Graphics painter = mainPanel.getGraphics();
		ArrayList<Pair> plots = bezierCurve.getBezierPlotPoints();

		painter.setColor(Color.RED);

		for(int i = 0; i < plots.size() - 2; i++) {
			Pair p0 = plots.get(i);
			Pair p1 = plots.get(i + 1);

			System.out.println("DRAWING CURVE AT: (" + p0.getFirstVal() + ", " + p0.getSecondVal() + ") & (" + p1.getFirstVal() + ", " + p1.getSecondVal() + ")");

			if(i % 4 == 0) {
				painter.fillOval(p1.getFirstVal() - 4, p1.getSecondVal() - 4, 8, 8);
				drawSlopeLine(p1, bezierCurve.getBezierPlotPointSlopes().get(i));
			}

			painter.drawLine(p0.getFirstVal(), p0.getSecondVal(), p1.getFirstVal(), p1.getSecondVal());
		}
	}
	
	private void drawSlopeLine(final Pair mPoint, final Fraction mSlope) {
		int SCALE_FACTOR = 2;
		
		int midPointX = mPoint.getFirstVal();
		int midPointY = mPoint.getSecondVal();
		int mRise = mSlope.getNumerator();
		int mRun = mSlope.getDenominator();
		
//		Pair p0 = new Pair(((mRise * SCALE_FACTOR) - midPointX), ((mRun * SCALE_FACTOR) - midPointY));
//		Pair p1 = new Pair(((mRise * SCALE_FACTOR) + midPointX), ((mRun * SCALE_FACTOR) + midPointY));
		
		Pair p0 = new Pair((Math.abs((mRise * SCALE_FACTOR) - midPointX)), Math.abs((mRun * SCALE_FACTOR) - midPointY));
		Pair p1 = new Pair(((mRise * SCALE_FACTOR) + midPointX), ((mRun * SCALE_FACTOR) + midPointY));
		
		Graphics painter = mainPanel.getGraphics();
		painter.setColor(Color.GREEN);
//		painter.drawLine(p0.getFirstVal(), p0.getSecondVal(), p1.getFirstVal(), p1.getSecondVal());
		
		painter.drawLine(midPointX, midPointY, p0.getFirstVal(), p0.getSecondVal());
		painter.drawLine(midPointX, midPointY, p1.getFirstVal(), p1.getSecondVal());
	}
	
	private void drawAverageBezierCurve() {
		for(int i = 0; i < bezierCurveList.size() - 1; i++) {
			BezierCurve bCurve1 = bezierCurveList.get(i);
			BezierCurve bCurve2 = bezierCurveList.get(i+1);
			BezierCurve averagedCurve = new BezierCurve();
			for(int j = 0; j < bCurve1.getBezierPlotPoints().size(); j++) {
				int c1X = bCurve1.getBezierPlotPoints().get(j).getFirstVal();
				int c2X = bCurve2.getBezierPlotPoints().get(j).getFirstVal();
				int c1Y = bCurve1.getBezierPlotPoints().get(j).getSecondVal();
				int c2Y = bCurve2.getBezierPlotPoints().get(j).getSecondVal();
				
				int averagedX = (c1X + c2X) / 2;
				int averagedY = (c1Y + c2Y) / 2;
				
				
				averagedCurve.addBezierCoordinate(averagedX, averagedY);
				
				
			}
			drawAveragedBezierCurve(averagedCurve);
		}
	}

	private void addMouseAdapter(final Canvas panel) {
		panel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Firing: " + e.getXOnScreen() + " ," + e.getYOnScreen());
				Graphics circle = panel.getGraphics();
				circle.setColor(Color.BLUE);
				circle.fillOval(e.getX() - 16, e.getY() - 16, 32, 32);

				LineUtil.addCoordinate(e.getX(), e.getY());

				paintLine();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}
}
