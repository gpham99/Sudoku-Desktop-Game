package sudokuDesktopGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Interface extends JFrame {
	private GamePanel gp;
	public Interface() {
		setMinimumSize(new Dimension(600, 650));

		
		JPanel sudokuBoard = new JPanel();
		sudokuBoard.setLayout(new FlowLayout());
		sudokuBoard.setPreferredSize(new Dimension(600, 650));
		
		gp = new GamePanel();
		sudokuBoard.add(gp);
		
		//stupid stuff from here
		JPanel buttonSelectionPanel = new JPanel();
		buttonSelectionPanel.setPreferredSize(new Dimension(90,500));
		
		for(int i = 1; i < 10; i++) {
			String num = String.valueOf(i);
			JButton b = new JButton(num);
			b.setPreferredSize(new Dimension(50,30));
			b.addActionListener(gp.new NumActionListener());

			buttonSelectionPanel.add(b);
		}
		
		//panel
		JPanel submitresult = new JPanel();
		submitresult.setPreferredSize(new Dimension(200, 60));
		submitresult.setLayout(new BoxLayout(submitresult, BoxLayout.PAGE_AXIS));
		
		JLabel result = new JLabel("");
		result.setPreferredSize(new Dimension(200, 35));
		
		JButton submit = new JButton("Submit"); //add action listener
		submit.addActionListener(gp.new SubmitActionListener(result));
		
		submit.setPreferredSize(new Dimension(100, 25));		
		
		submitresult.add(submit);
		submitresult.add(result);
		//end of panel
		
		gp.repaint();//test
		buttonSelectionPanel.repaint();
		sudokuBoard.add(buttonSelectionPanel);
		sudokuBoard.add(submitresult);
		
		add(sudokuBoard);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Interface();
	}
}