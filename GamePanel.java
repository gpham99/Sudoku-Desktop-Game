package sudokuDesktopGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.font.FontRenderContext;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class GamePanel extends JPanel{
	private SudokuPuzzle puzzle;	
	private int currentlySelectedCol;
	private int currentlySelectedRow;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(450, 450));
		//take that list iterate through the elements and fill in the blanks of paintComponent
		this.puzzle = SudokuGenerator.generateSudoku();
		//System.out.println("bool" + puzzle.mutable[3][3]); //print the element in the already made sudoku
		this.addMouseListener(new SudokuPanelMouseAdapter());
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Color var = Color.white;
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g; //cast g to Graphics2D but why do we need to cast g to graphics2D
		g2d.setColor(var);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight()); //the board will be the whole panel
				
		
		//function that draw a sudoku grid like drawGrid();
		//display that grid
		
		int slotWidth = this.getWidth() / 9;
		int slotHeight = this.getHeight() / 9;
		g2d.setColor(Color.BLACK);
		
		for(int i = 0; i <= this.getWidth(); i += slotWidth) {
			if (i == 450) {
				g2d.setStroke(new BasicStroke(3));
				g2d.drawLine(i, 0, i, this.getHeight());
			}
			else if (i % 3 == 0) {
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(i, 0, i, this.getHeight());
			}
			else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(i, 0, i, this.getHeight());
			}
		}
		
		for(int j = 0; j <= this.getHeight(); j += slotHeight) {
			if(j == 450) {
				g2d.setStroke(new BasicStroke(3));
				g2d.drawLine(0, j, this.getWidth(), j);
			}
			else if (j % 3 == 0) {
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(0, j, this.getWidth(), j);
			}
			else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(0, j, this.getWidth(), j);
			}
		}
		
		Font f = new Font("Times New Roman", Font.PLAIN, 26);
		g2d.setFont(f);
		FontRenderContext fContext = g2d.getFontRenderContext();
		for(int row = 0; row < puzzle.getNumRows(); row++) {
			for(int col = 0; col < puzzle.getNumColumns(); col++) {
				int textWidth = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getWidth();
				int textHeight = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getHeight();
				String content = puzzle.getValue(row, col);
				if (!content.equals("0")) {
					if(puzzle.mutable[row][col]) {
						g2d.setColor(Color.RED);
					}
					else {
						g2d.setColor(Color.BLACK);
					}
				g2d.drawString(content, (col*slotWidth)+((slotWidth/2)-(textWidth/2)), (row*slotHeight)+((slotHeight/2)+(textHeight/2)));
				}
			}
		}
		
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
			g2d.setColor(new Color(0.0f,0.0f,1.0f,0.3f));
			g2d.fillRect(currentlySelectedCol * slotWidth,currentlySelectedRow * slotHeight,slotWidth,slotHeight);
		}
	}
	
	private class SudokuPanelMouseAdapter extends MouseInputAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			//System.out.println("x cord " + e.getX());
			//System.out.println("y cord " + e.getY());
			
			int x = e.getX();
			int y = e.getY();
			if(e.getButton() == MouseEvent.BUTTON1) {
				currentlySelectedRow = e.getY() / 50;
				currentlySelectedCol = e.getX() / 50;
				e.getComponent().repaint();
			}
		}
	}
	
	public void messageFromNumActionListener(String buttonValue) {
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
			puzzle.UsermakeMove(currentlySelectedRow, currentlySelectedCol, buttonValue);
			repaint();
		}
		else {
			System.out.println("choose something");
		}
	}
	
	public class NumActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton boxmess = (JButton) e.getSource();
			//System.out.println(boxmess.getText());
			
			messageFromNumActionListener(boxmess.getText());	//this doesn't lead to a repaint
		}
	}
	
	public class SubmitActionListener implements ActionListener{
		JLabel resultText;
		public SubmitActionListener(JLabel result) {
			resultText = result;
		}

		public void actionPerformed(ActionEvent e) {
			//add the puzzle stuff all together
			boolean finished = false;
			int rowSum = 0;
			int colSum = 0;
			int boxSum = 0;
			
			for(int i = 0; i < 9; i++) {
				for(int j = 0; j < 9; j++) {
					if (puzzle.getValue(i, j).equals("0")) {
						resultText.setText("Sudoku Not All Filled.");
						finished = true;
						return;
					}
					// else
					rowSum += Integer.valueOf(puzzle.getValue(i, j));
					colSum += Integer.valueOf(puzzle.getValue(j, i));
				}
				
				if(!finished) {
					if(rowSum != 45 || colSum != 45) {
						resultText.setText("Answer incorrect. Try again.");
						finished = true;
						return;
					}
				}
				//System.out.println("col Sum " + colSum);
				colSum = 0;
				rowSum = 0;
			}
			
			//check the boxes
			if(!finished) {
				
				for(int a = 0; a < 9;  a += 3) {
					for(int b = 0; b < 9; b += 3) {
						for(int i = 0; i < 3; i ++) {
							for(int j = 0; j < 3; j++) {
							boxSum += Integer.valueOf(puzzle.getValue(a + i, b + j));
							}
						}
						if (boxSum != 45) {
							resultText.setText("Answer incorrect. Try again.");
							finished = true;
							return;
						}
						boxSum = 0;
					}
				}
				}
			
			if(!finished) {
				resultText.setText("You win!");
			}
		}
	}
}