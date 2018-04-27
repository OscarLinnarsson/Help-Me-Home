package Main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import GUI.Draw;

public class MainMenu implements Navigation, ActionListener {
	
	public void initialize () {
		
	}
	
	public void update (double dT) {
		
	}
	
	public void render () {
		Draw.drawBackground();
	}
	
	public void leftClick (int x, int y) {
		
	}
	
	public void rightClick (int x, int y) {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Play") {
			Boot.goToGame();
		}
	}
	
}
