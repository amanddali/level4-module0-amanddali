package _03_Conways_Game_of_Life;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class WorldPanel extends JPanel implements MouseListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private int cellsPerRow;
	private int cellSize;

	private Timer timer;

	// 1. Create a 2D array of Cells. Do not initialize it.
	Cell[][] cell;

	public WorldPanel(int w, int h, int cpr) {
		setPreferredSize(new Dimension(w, h));
		addMouseListener(this);
		timer = new Timer(500, this);
		this.cellsPerRow = cpr;

		// 2. Calculate the cell size.
		cellSize = w / cpr;
		// 3. Initialize the cell array to the appropriate size.
		cell = new Cell[cpr][cpr];
		// 3. Iterate through the array and initialize each cell.
		// Don't forget to consider the cell's dimensions when
		// passing in the location.
		for (int i = 0; i < cell.length; i++) {
			for (int j = 0; j < cell[0].length; j++) {
				cell[i][j] = new Cell(i * cellSize, j * cellSize, cellSize);
			}
		}

	}

	public void randomizeCells() {
		// 4. Iterate through each cell and randomly set each
		// cell's isAlive memeber to true of false
		for (int i = 0; i < cell.length; i++) {
			for (int j = 0; j < cell.length; j++) {
				cell[i][j].isAlive = new Random().nextBoolean();
			}
		}

		repaint();
	}

	public void clearCells() {
		// 5. Iterate through the cells and set them all to dead.
		for (int i = 0; i < cell.length; i++) {
			for (int j = 0; j < cell[0].length; j++) {
				cell[i][j].isAlive = false;
			}
		}
		repaint();
	}

	public void startAnimation() {
		timer.start();
	}

	public void stopAnimation() {
		timer.stop();
	}

	public void setAnimationDelay(int sp) {
		timer.setDelay(sp);
	}

	@Override
	public void paintComponent(Graphics g) {
		// 6. Iterate through the cells and draw them all
		for (int i = 0; i < cell.length; i++) {
			for (int j = 0; j < cell[0].length; j++) {
				cell[i][j].draw(g);
			}
		}

		// draws grid
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	}

	// advances world one step
	public void step() {
		// 7. iterate through cells and fill in the livingNeighbors array
		// . using the getLivingNeighbors method.
		int[][] livingNeighbors = new int[cellsPerRow][cellsPerRow];
		for (int i = 0; i < livingNeighbors.length; i++) {
			for (int j = 0; j < livingNeighbors[0].length; j++) {
				livingNeighbors[i][j] = getLivingNeighbors(i, j);
			}
		}
		// 8. check if each cell should live or die
		for (int i = 0; i < cell.length; i++) {
			for (int j = 0; j < cell[0].length; j++) {
				cell[i][j].liveOrDie(getLivingNeighbors(i, j));
			}
		}
		repaint();
	}

	// 9. Complete the method.
	// It returns an int of 8 or less based on how many
	// living neighbors there are of the
	// cell identified by x and y
	public int getLivingNeighbors(int x, int y) {
		int numneighbors = 0;
		if (x != 0) {
			if (cell[x - 1][y].isAlive) {
				numneighbors++;
			}
		}
		if (x != 49) {
			if (cell[x + 1][y].isAlive) {
				numneighbors++;
			}
		}
		if (y != 0) {
			if (cell[x][y - 1].isAlive) {
				numneighbors++;
			}
		}
		if (y != 49) {
			if (cell[x][y + 1].isAlive) {
				numneighbors++;
			}
		}
		if (x != 0 && y != 0) {
			if (cell[x - 1][y - 1].isAlive) {
				numneighbors++;
			}
		}
		if (x != 0 && y != 49) {
			if (cell[x - 1][y + 1].isAlive) {
				numneighbors++;
			}
		}
		if (x != 49 && y != 0) {
			if (cell[x + 1][y - 1].isAlive) {
				numneighbors++;
			}
		}
		if (x != 49 && y != 49) {
			if (cell[x + 1][y + 1].isAlive) {
				numneighbors++;
			}
		}
		return numneighbors;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// 10. Use e.getX() and e.getY() to determine
		// which cell is clicked. Then toggle
		// the isAlive variable for that cell.
		for (int i = 0; i < cell.length; i++) {
			for (int j = 0; j < cell[0].length; j++) {
				// if (cell[i][j].getX() <= e.getX() && cell[i][j].getX() + cell.length >=
				// e.getX()) {
				// cell[i][j].isAlive = !cell[i][j].isAlive;
				// }
				// if (cell[i][j].getY() <= e.getY() && cell[i][j].getY() + cell.length >=
				// e.getY()) {
				// cell[i][j].isAlive = !cell[i][j].isAlive;
				// }
				Cell c = cell[i][j];
				if (e.getX() > c.getX() && e.getX() < (c.getX() + cellSize) && e.getY() > c.getY()
						&& e.getY() < (c.getY() + cellSize)) {
					c.isAlive = !c.isAlive;
				}
			}
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		step();
	}
}
