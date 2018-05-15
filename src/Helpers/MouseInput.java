package Helpers;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Main.Boot;

public class MouseInput implements MouseListener {
	
	private JFrame frame;
	private int frameX;
	private int frameY;
	private int topBarHeight = 40;
	private Point p;
	private static int x;
	private static int y;
	private int pointerWidth = 15;
	private int pointerHeight = 20;
	
	public MouseInput(JFrame f) {
		frame = f;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		frameX = (int) frame.getLocationOnScreen().getX();
		frameY = (int) frame.getLocationOnScreen().getY();
		p = MouseInfo.getPointerInfo().getLocation();
		x = (int) p.getX() - frameX - pointerWidth;
		y = (int) p.getY() - frameY - pointerHeight - topBarHeight;
		Boot.render();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		frameX = (int) frame.getLocationOnScreen().getX();
		frameY = (int) frame.getLocationOnScreen().getY();
		p = MouseInfo.getPointerInfo().getLocation();
		x = (int) p.getX() - frameX - pointerWidth;
		y = (int) p.getY() - frameY - pointerHeight - topBarHeight;
		Boot.render();
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		frameX = (int) frame.getLocationOnScreen().getX();
		frameY = (int) frame.getLocationOnScreen().getY();
		p = MouseInfo.getPointerInfo().getLocation();
		x = (int) p.getX() - frameX - pointerWidth;
		y = (int) p.getY() - frameY - pointerHeight - topBarHeight;
		//x = (int) p.getX();
		//y = (int) p.getY() - topBarHeight;
		
		if (SwingUtilities.isLeftMouseButton(e)) {
			Boot.leftClick(x, y);
		} else if (SwingUtilities.isRightMouseButton(e)) {
			Boot.rightClick(x, y);
		}
	}
	
	public static int[] getMouseCords(){
		int[] cords = {x,y};
		return cords;
	}

}
