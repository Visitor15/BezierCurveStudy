package com.flabs.bezier_curves.main;

import com.flabs.bezier_curves.ui.MainUI;

public class MainActivity {

	
	private static MainUI mainUI;
	
	public static void main(String[] args) {
		init();
	}

	private static void init() {
		mainUI = new MainUI();
		mainUI.setVisible(true);
	}
}
