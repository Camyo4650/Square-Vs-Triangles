package net.camonoxe.sVSt;

import java.awt.Rectangle;
import java.util.ArrayList;

public class MiniSquare {

	public static ArrayList<Rectangle> r = new ArrayList<Rectangle>();

	public static void shoot() {
		for(int i = 0; i < 2; i++) {
			Rectangle e = new Rectangle(0,0,3,3);
			r.add(e);
		}
	}

}
