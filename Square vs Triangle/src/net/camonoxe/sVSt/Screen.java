package net.camonoxe.sVSt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Screen extends JPanel implements ActionListener {
	public static Player plr;
	Timer t;
	Timer t2;
	
	private static final long serialVersionUID = 1L;

	public Screen() {
		setFocusable(true);
		addKeyListener(new AL());
		plr = new Player();
		t = new Timer(5, this);
		t.start();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		if(!plr.isDead) {
			g2d.drawRect(plr.x, plr.y, 30, 30);
			if(plr.bullet1isMoving) {
				g2d.drawRect(MiniSquare.r.get(0).x, MiniSquare.r.get(0).y, MiniSquare.r.get(0).width, MiniSquare.r.get(0).height);
			}
			if(plr.bullet2isMoving) {
				g2d.drawRect(MiniSquare.r.get(1).x, MiniSquare.r.get(1).y, MiniSquare.r.get(1).width, MiniSquare.r.get(1).height);
			}
			g2d.fillRect(Map.floor.x, Map.floor.y, Map.floor.width, Map.floor.height);
			for(int i = 0; i < (Enemy.triangles.size()); i++) {
				g2d.drawPolygon(Enemy.triangles.get(i));
			}
			for(int i = 0; i < (Map.r.size()); i++) {
				g2d.fillRect(Map.r.get(i).x, Map.r.get(i).y, Map.r.get(i).width, Map.r.get(i).height);
			}
			g2d.setColor(new Color(0,0,25));
			g2d.setFont(new Font("Times New Roman", Font.BOLD, 36));
			g2d.drawString(plr.kills+" kills", 10, 30);
		} else {
			drawDeadGraphics(g2d);
		}
	}
	
	public void drawDeadGraphics(Graphics2D g2d) {
		g2d.setColor(new Color(255,0,0));
		g2d.fillRect(0, 0, SquareVsTriangleClient.SIZE.width, SquareVsTriangleClient.SIZE.height);
		g2d.setColor(new Color(255,255,255));
		g2d.setFont(new Font("Times New Roman", Font.PLAIN, 48));
		g2d.drawString("Game over!", 10, 100);
		g2d.setFont(new Font("Times New Roman", Font.PLAIN, 36));
		g2d.drawString("Press any key to continue.", 10, 200);
	}

	public void actionPerformed(ActionEvent e) {
		plr.physics();
		repaint();
	}
	
	private class AL extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			plr.keyReleased(e.getKeyCode());
		}

		public void keyPressed(KeyEvent e) {
			plr.keyPressed(e.getKeyCode());
		}
		
	}

}
