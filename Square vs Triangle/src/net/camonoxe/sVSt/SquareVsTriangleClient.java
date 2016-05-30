package net.camonoxe.sVSt;

import java.awt.Dimension;

import javax.swing.JFrame;

public class SquareVsTriangleClient {
	private static JFrame frame;
	static final Dimension SIZE = new Dimension(640,480);
	public static final String TITLE_GAME = "SquareVsTriangleClient";

	public static void main(String args[]) {
		frame = new JFrame(TITLE_GAME);
		frame.setSize(SIZE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Screen());
		frame.setVisible(true);
	}

}
