package net.camonoxe.sVSt;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {

	public static ArrayList<Polygon> triangles = new ArrayList<Polygon>();
	public static boolean[] isY = {false,false,false,false,false};
	
	static {
		create();
	}
	
	public static void create() {
		Random rand = new Random();
		triangles = new ArrayList<Polygon>();
		for (int i = 0; i < 5; i++) {
			int offset = (rand.nextInt(1200))+(i+2);
			int xpoints[] = {(18)+offset-18,(9)+offset-27,(27)+offset-9};
			int ypoints[] = {(2),(29),(29)};
			Polygon e = new Polygon(xpoints,ypoints,3);
			triangles.add(e);
		}
	}
	
	private static int getTriangleIndex(Polygon p) {
		int index = -1;
		index = triangles.indexOf(p);
		return index;
	}
	
	public static void createForOne(Polygon p) {
		Random rand = new Random();
		int offset = (rand.nextInt(1200));
		int xpoints[] = {(18)+offset-18,(9)+offset-27,(27)+offset-9};
		int ypoints[] = {(2),(29),(29)};
		int index = getTriangleIndex(p);
		triangles.remove(index);
		Polygon e = new Polygon(xpoints,ypoints,3);
		triangles.add(e);
	}

}
