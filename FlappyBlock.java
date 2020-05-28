/**
 * Name: Shuhao Bai
 * CWID: 10437318
 * Group#11
 * CS501 - Final Project - Assignment 8 Project
 *
 * This is a simple game called Flappy Block.
 * Simply left click your mouse to start the game and control the motion of the block.
 * The block will jump upwards for each mouse click, you need to manage the block to go through the open-space between the black obstacle.
 * Game will be over once if you hit the ground, sky or any black obstacles.
 * Click again for another round of game.
 *
 */


package flappyBlock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBlock implements ActionListener, MouseListener {

	public static void main(String[] args) {
		flappyBlock = new FlappyBlock();
	}

	public static FlappyBlock flappyBlock;
	public final int WIDTH = 1600;
	public final int HEIGHT = 800;
	public RenderUI renderUI;
	public Rectangle block;
	public int marker;
	public int yMotion;
	public int score;
	public ArrayList<Rectangle> columns;
	public Random rand;
	public boolean gameOver;
	public boolean started;

	public FlappyBlock() {

		JFrame jframe = new JFrame();
		Timer timer = new Timer(19, this);

		renderUI = new RenderUI();
		rand = new Random();

		jframe.setTitle("Simple Flappy Block Version:1.0");
		jframe.add(renderUI);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.addMouseListener(this);
		jframe.setVisible(true);
		jframe.setResizable(false);

		// create the block
		block = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
		columns = new ArrayList<Rectangle>();
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		// set the speed of the columns motion
		int speed = 14;

		marker++;

		if (started) {
			for (int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);
				column.x -= speed;
			}

			if (marker % 2 == 0 && yMotion < 15) {
				yMotion += 2;
			}

			for (int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);

				if (column.x + column.width < 0) {
					columns.remove(column);
					if (column.y == 0) {
						addColumn(false);
					}
				}
			}

			block.y += yMotion;

			// check collision
			for (Rectangle column : columns) {

				// add score
				if (column.y == 0 && block.x + block.width / 2 > column.x + column.width / 2 - 7
						&& block.x + block.width / 2 < column.x + column.width / 2 + 7) {
					score++;
				}
				if (column.intersects(block)) {
					gameOver = true;
				}
				if (block.y + yMotion >= HEIGHT - 120) {
					block.y = HEIGHT - 120 - block.height;
				}
			}
			if (block.y > HEIGHT - 150 || block.y < 0) {
				gameOver = true;
			}
		}
		renderUI.repaint();
	}

	public void addColumn(boolean start) {
		int space = 300;
		int width = 200;
		int height = 50 + rand.nextInt(300);

		if (start) {
			columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
		} else {
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
		}

	}

	public void paintColumn(Graphics g, Rectangle column) {
		g.setColor(Color.black);
		g.fillRect(column.x, column.y, column.width, column.height);
	}

	void repaint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.pink);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, HEIGHT - 120, WIDTH, 150);

		g.setColor(Color.red);
		g.fillRect(0, HEIGHT - 120, WIDTH, 10);

		g.setColor(Color.yellow);
		g.fillRect(block.x, block.y, block.width, block.height);

		for (Rectangle column : columns) {
			paintColumn(g, column);
		}

		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", 1, 100));

		if (!started) {
			g.drawString("Click to Start", 75, HEIGHT / 2 - 50);
		}
		if (gameOver) {

			g.drawString("Game Over, Click Again!", 50, HEIGHT / 2);
		}
		if (!gameOver && started) {
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
			if(score == 0){
				g.drawString("It's hard..", 75, HEIGHT /8);
			}
			if(score == 1){
				g.drawString("Yep", 75, HEIGHT /8);
				g.setColor(Color.green);
				g.fillRect(block.x, block.y, block.width, block.height);
			}
			if(score == 2){
				g.drawString("Good job!", 75, HEIGHT /8);
				g.setColor(Color.blue);
				g.fillRect(block.x, block.y, block.width, block.height);
			}
			if(score == 3){
				g.drawString("WOW!!", 75, HEIGHT /8);
				g.setColor(Color.CYAN);
				g.fillRect(block.x, block.y, block.width, block.height);

			}
			if(score == 4){
				g.drawString("Keep Going!!!", 75, HEIGHT /8);
				g.setColor(Color.magenta);
				g.fillRect(block.x, block.y, block.width, block.height);
			}
			if(score == 5 || score == 6){
				g.drawString("God Like!!!!", 75, HEIGHT /8);
				g.setColor(Color.white);
				g.fillRect(block.x, block.y, block.width, block.height);
			}
			if(score > 6){
				g.drawString("OMG!", 75, HEIGHT /8);
				g.setColor(Color.red);
				g.fillRect(block.x, block.y, block.width, block.height);
			}

		}
	}

	public void jump() {
		if (gameOver) {

			block = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
			columns.clear();
			yMotion = 0;
			score = 0;

			addColumn(true);
			addColumn(true);
			addColumn(true);
//			addColumn(true);
			gameOver = false;
		}
		if (!started) {
			started = true;
		} else if (!gameOver) {
			if (yMotion > 0) {
				yMotion = 0;
			}

			yMotion -= 10;
		}

	}



	@Override
	public void mouseClicked(MouseEvent e) {
		jump();
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
