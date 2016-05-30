package net.camonoxe.sVSt;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

public class Map {
	
	public static int realX=100,realY=300;
	public static int cameraX,cameraY;
	public static Rectangle floor = new Rectangle(-20, 380, 1244, 100);
	public static ArrayList<Rectangle> r;
	
	static {
		create();
	}
	
	public static void create() {
		Random rand = new Random();
		r = new ArrayList<Rectangle>();
		for (int i = 0; i < rand.nextInt(20)+10; i++) {
			Rectangle e = new Rectangle(0,rand.nextInt(330)+10,70,10);
			e.x = rand.nextInt(floor.width-e.width);
			r.add(e);
		}
	}

}
