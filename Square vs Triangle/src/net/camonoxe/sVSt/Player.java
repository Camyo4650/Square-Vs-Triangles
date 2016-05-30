package net.camonoxe.sVSt;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

public class Player {
	
	public int x,dx,pastDx,totalSkidX,skidTimeX,y,dy,jumpTimes,kills=0,maxKillsTillNextRound=3,timesRan=2,delayOfSpeed=500;
	public Rectangle center;
	public boolean isStarted = false;
	public boolean isRepelled = false;
	public boolean isFiring = true,bullet1isMoving = false,bullet2isMoving = false;
	public boolean isMoving = false;
	public boolean isMovable = false;
	public boolean isFalling = false;
	public boolean isJumping = false;
	public boolean isJumpable = false;
	public boolean isDead = false;
	Timer t;
	
	public Player() {
		x = 100;
		y = 300;
		totalSkidX = 100;
		jumpTimes = 0;
		center = new Rectangle(x+14,y+14,1,1);
		t = new Timer(delayOfSpeed, new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (isJumping) {
					isJumping = false;
					isFalling = true;
					dy=2;
				}
				if (isFalling && jumpTimes == 2) {
					isJumpable = false;
				}
			}
			
		});
		t.start();
	}
	
	public void physics() {
		if(isDivisible(kills,maxKillsTillNextRound)) {
			delayOfSpeed -= 10;
		}
		if(new Rectangle(x,y,30,30).intersects(Map.floor) && !isJumping) {
			if (jumpTimes > 0) {
				jumpTimes = 0;
			}
			isMovable = true;
			isJumpable = true;
			isFalling = false;
			dy=0;
		} else {
			if(isJumping) {
				dy = -2;
			} else {
				isFalling = true;
				dy=2;
			}
		}
		center = new Rectangle(x+14,y+14,1,1);
		if(new Rectangle(x,y,30,30).intersects(new Rectangle(-20,0,20,380)) || new Rectangle(x,y,30,30).intersects(new Rectangle(SquareVsTriangleClient.SIZE.width,0,20,380))) {
			if(isMoving){
				isRepelled = true;
				dx = dx*-1;
			}
		}
		for(int i = 0; i < 5; i++) {
			if (timesRan == 2) {
				if (Enemy.triangles.get(i).ypoints[0] != y) {
					Enemy.isY[i] = true;
					if (Enemy.triangles.get(i).ypoints[0] < y) {
						Enemy.triangles.get(i).ypoints[0]=Enemy.triangles.get(i).ypoints[0]+1;
						Enemy.triangles.get(i).ypoints[1]=Enemy.triangles.get(i).ypoints[1]+1;
						Enemy.triangles.get(i).ypoints[2]=Enemy.triangles.get(i).ypoints[2]+1;
					}
					if (Enemy.triangles.get(i).ypoints[0] > y) {
						Enemy.triangles.get(i).ypoints[0]=Enemy.triangles.get(i).ypoints[0]-1;
						Enemy.triangles.get(i).ypoints[1]=Enemy.triangles.get(i).ypoints[1]-1;
						Enemy.triangles.get(i).ypoints[2]=Enemy.triangles.get(i).ypoints[2]-1;
					}
				} else if (Enemy.triangles.get(i).ypoints[0] == y) {
					Enemy.isY[i] = false;
					if (!Enemy.isY[i]) {
						if (Enemy.triangles.get(i).xpoints[0] < x) {
							Enemy.triangles.get(i).xpoints[0]=Enemy.triangles.get(i).xpoints[0]+1;
							Enemy.triangles.get(i).xpoints[1]=Enemy.triangles.get(i).xpoints[1]+1;
							Enemy.triangles.get(i).xpoints[2]=Enemy.triangles.get(i).xpoints[2]+1;
						}
						if (Enemy.triangles.get(i).xpoints[0] > x) {
							Enemy.triangles.get(i).xpoints[0]=Enemy.triangles.get(i).xpoints[0]-1;
							Enemy.triangles.get(i).xpoints[1]=Enemy.triangles.get(i).xpoints[1]-1;
							Enemy.triangles.get(i).xpoints[2]=Enemy.triangles.get(i).xpoints[2]-1;
						}
					}
				}
				timesRan = 0;
			} else {
				timesRan++;
			}
			if (new Rectangle(x,y,30,30).intersectsLine(Enemy.triangles.get(i).xpoints[0], Enemy.triangles.get(i).ypoints[0], Enemy.triangles.get(i).xpoints[1], Enemy.triangles.get(i).ypoints[1]) ||
				new Rectangle(x,y,30,30).intersectsLine(Enemy.triangles.get(i).xpoints[0], Enemy.triangles.get(i).ypoints[0], Enemy.triangles.get(i).xpoints[2], Enemy.triangles.get(i).ypoints[2]) ||
				new Rectangle(x,y,30,30).intersectsLine(Enemy.triangles.get(i).xpoints[1], Enemy.triangles.get(i).ypoints[1], Enemy.triangles.get(i).xpoints[2], Enemy.triangles.get(i).ypoints[2])) {
				isDead = true;
			}
			if ((bullet1isMoving || bullet2isMoving) || (bullet1isMoving && bullet2isMoving)) {
				Rectangle bullet1 = MiniSquare.r.get(0);
				Rectangle bullet2 = MiniSquare.r.get(1);
				if (bullet1isMoving) {
					if(bullet1.intersectsLine(Enemy.triangles.get(i).xpoints[0], Enemy.triangles.get(i).ypoints[0], Enemy.triangles.get(i).xpoints[1], Enemy.triangles.get(i).ypoints[1]) ||
							bullet1.intersectsLine(Enemy.triangles.get(i).xpoints[0], Enemy.triangles.get(i).ypoints[0], Enemy.triangles.get(i).xpoints[2], Enemy.triangles.get(i).ypoints[2]) ||
							bullet1.intersectsLine(Enemy.triangles.get(i).xpoints[1], Enemy.triangles.get(i).ypoints[1], Enemy.triangles.get(i).xpoints[2], Enemy.triangles.get(i).ypoints[2])) {
						Enemy.createForOne(Enemy.triangles.get(i));
						bullet1isMoving = false;
						kills++;
					} else {
						bullet1.x-=1;
					}
				}
				if (bullet2isMoving) {
					if(bullet2.intersectsLine(Enemy.triangles.get(i).xpoints[0], Enemy.triangles.get(i).ypoints[0], Enemy.triangles.get(i).xpoints[1], Enemy.triangles.get(i).ypoints[1]) ||
							bullet2.intersectsLine(Enemy.triangles.get(i).xpoints[0], Enemy.triangles.get(i).ypoints[0], Enemy.triangles.get(i).xpoints[2], Enemy.triangles.get(i).ypoints[2]) ||
							bullet2.intersectsLine(Enemy.triangles.get(i).xpoints[1], Enemy.triangles.get(i).ypoints[1], Enemy.triangles.get(i).xpoints[2], Enemy.triangles.get(i).ypoints[2])) {
						Enemy.createForOne(Enemy.triangles.get(i));
						bullet2isMoving = false;
						kills++;
					} else {
						bullet2.x+=1;
					}
				}
			}
		}
		for(int i = 0; i < (Map.r.size()); i++) {
			if(new Rectangle(x,y,30,30).intersects(Map.r.get(i))) {
				if (jumpTimes > 0) {
					jumpTimes = 0;
				}
				isMovable = true;
				isJumpable = true;
				isFalling = false;
				if (!isJumping) {
					dy = 0;
				}
			}
		}
		if(isFiring) {
			MiniSquare.shoot();
			Rectangle bullet1 = MiniSquare.r.get(0);
			Rectangle bullet2 = MiniSquare.r.get(1);
			bullet1.x=center.x-18;
			bullet1.y=center.y-1;
			bullet2.x=center.x+18;
			bullet2.y=center.y-1;
			isFiring = false;
			bullet1isMoving = true;
			bullet2isMoving = true;
		}
		if(isMovable) {
			Map.realX=Map.realX+dx;
			if (center.intersectsLine(Map.cameraX+(SquareVsTriangleClient.SIZE.width/2), Map.cameraY, Map.cameraX+(SquareVsTriangleClient.SIZE.width/2), Map.cameraY+SquareVsTriangleClient.SIZE.height) && Map.realX >= 306 && Map.realX <= 612) {
				Map.floor.x=Map.floor.x-dx;
				for(int i = 0; i < (Map.r.size()); i++) {
					Map.r.get(i).x=Map.r.get(i).x-dx;
				}
				for(int i = 0; i < 5; i++) {
					Enemy.triangles.get(i).xpoints[0]=Enemy.triangles.get(i).xpoints[0]-dx;
					Enemy.triangles.get(i).xpoints[1]=Enemy.triangles.get(i).xpoints[1]-dx;
					Enemy.triangles.get(i).xpoints[2]=Enemy.triangles.get(i).xpoints[2]-dx;
				}
			} else {
				x=x+dx;
			}
		}
		Map.realY=Map.realY+dy;
		if (center.intersectsLine(Map.cameraX, Map.cameraY+(SquareVsTriangleClient.SIZE.height/2), Map.cameraX+SquareVsTriangleClient.SIZE.width, Map.cameraY+(SquareVsTriangleClient.SIZE.height/2)) && Map.realY <= 240) {
			Map.floor.y=Map.floor.y-dy;
			for(int i = 0; i < (Map.r.size()); i++) {
				Map.r.get(i).y=Map.r.get(i).y-dy;
			}
			for(int i = 0; i < 5; i++) {
				Enemy.triangles.get(i).ypoints[0]=Enemy.triangles.get(i).ypoints[0]-dy;
				Enemy.triangles.get(i).ypoints[1]=Enemy.triangles.get(i).ypoints[1]-dy;
				Enemy.triangles.get(i).ypoints[2]=Enemy.triangles.get(i).ypoints[2]-dy;
			}
		} else {
			y=y+dy;
		}
	}
	
	private boolean isDivisible(int div, int divi) {
		boolean returnValue = true;
		try {
			int answer = div/divi;
		} catch (NumberFormatException n) {
			returnValue = false;
		}
		return returnValue;
	}

	public void keyPressed(int key) {
		if (isDead) {
			Map.realX=100;
			Map.realY=300;
			Map.floor = new Rectangle(-20, 380, 1244, 100);
			Screen.plr = new Player();
			Enemy.create();
			Map.create();
		}
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			isMoving = true;
			pastDx = dx;
			dx = -2;
		} else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			isMoving = true;
			pastDx = dx;
			dx = 2;
		}
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			if (jumpTimes < 3) {
				isJumping = true;
				jumpTimes++;
			}
		}
		if (key == KeyEvent.VK_SPACE) {
			isFiring = true;
		}
	}
	public void keyReleased(int key) {
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			for (int i = 0; i < totalSkidX; i++) {
				if (i > (totalSkidX / 2)) {
					isMoving = false;
					pastDx = dx;
					dx = -1;
				}
			}
			dx = 0;
		} else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			for (int i = 0; i < totalSkidX; i++) {
				if (i > (totalSkidX / 2)) {
					isMoving = false;
					pastDx = dx;
					dx = 1;
				}
			}
			dx = 0;
		}
	}
	
	
	
}
