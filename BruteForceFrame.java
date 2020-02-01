import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;


public class BruteForceFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private GridBagConstraints constraints;
	private JPanel sortPanel;
	private JButton sourceButton;
	private JPanel comboPanel;
	private JLabel sortLabel;
	private JComboBox<String> sortBox;
	private JPanel buttonPanel;
	private JButton resetButton;
	private JButton sortButton;

	private ArrayList<Integer> srcArray;

	private static final String[] SORTALGO = {"Bubble Sort","Selection Sort"};

	public BruteForceFrame(){
		setTitle("Brute Force Algorithm");

		sourceButton = new JButton("Input Source");
		sourceButton.setFocusable(false);
		sourceButton.addActionListener(new SourceListener());

		sortLabel = new JLabel("Sort Type: ");

		sortBox = new JComboBox<String>(SORTALGO);
		sortBox.setSelectedIndex(0);
		sortBox.setFocusable(false);

		resetButton = new JButton("Reset");
		resetButton.setPreferredSize(new Dimension(75,25));
		resetButton.setFocusable(false);
		resetButton.addActionListener(new ResetListener());

		sortButton = new JButton("Sort");
		sortButton.setPreferredSize(new Dimension(75,25));
		sortButton.setFocusable(false);

		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(resetButton);
		buttonPanel.add(sortButton);

		comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		comboPanel.add(sortLabel);
		comboPanel.add(sortBox);

		constraints = new GridBagConstraints();

		sortPanel = new JPanel(new GridBagLayout());
		constraints.insets = new Insets(5,5,5,5);

		constraints.gridx = 0;
		constraints.gridy = 0;
		sortPanel.add(sourceButton,constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		sortPanel.add(comboPanel,constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		sortPanel.add(buttonPanel,constraints);

		add(sortPanel);

		pack();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent event){
				int response = JOptionPane.showConfirmDialog(rootPane, "Do you want to exit?", 
				"Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION){
					dispose();
				}
			}
		});
	}

	private class ResetListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event){
			if (srcArray != null){
				int response = JOptionPane.showConfirmDialog(rootPane, "Do you want to reset?", 
				"Reset", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION){
					srcArray = null;
				}
			}
		}
	}

	private class SourceListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event){
			if (srcArray != null){
				int response = JOptionPane.showConfirmDialog(rootPane, "Do you want to change the array source?", 
				"Array Source", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION){
					setArraySource();
				}
			}
			else {
				setArraySource();
			}
		}
	}

	private void setArraySource(){
		try {
			JFileChooser sourceFile = new JFileChooser();
			sourceFile.showOpenDialog(rootPane);

			FileReader fReader = new FileReader(sourceFile.getSelectedFile());
			BufferedReader bReader = new BufferedReader(fReader);

			ArrayList<Integer> tempArray = new ArrayList<Integer>();

			String inputStr = null;
			while ((inputStr = bReader.readLine()) != null){
				if (inputStr.isEmpty()){
					bReader.close();
					throw new IOException("Empty String Detected");
				}

				Integer input = Integer.valueOf(inputStr);

				for (int i = 0; i < tempArray.size(); ++i){
					if (input == tempArray.get(i)){
						bReader.close();
						throw new NumberFormatException("Duplicate Number Detected!");
					}
				}

				tempArray.add(input);
			}

			srcArray = tempArray;

			for (Integer i : srcArray){
				System.out.print(i + " ");
			}

			bReader.close();
		}
		catch (IOException e){
			JOptionPane.showMessageDialog(rootPane, "IOException:\n" 
            + e.getMessage(), "I/O Exception", JOptionPane.ERROR_MESSAGE);
		}
		catch (NumberFormatException e){
			JOptionPane.showMessageDialog(rootPane, "NumberFormatException:\n" 
            + e.getMessage(), "Number Format Exception", JOptionPane.ERROR_MESSAGE);
		}
		catch (NullPointerException e){
			// Expected Exception
		}
		catch (Exception e){
			e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, "Unimplemented Exception:\n" 
            + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		BruteForceFrame bruteFrame = new BruteForceFrame();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			bruteFrame.setSize(300,200);
			bruteFrame.setResizable(false);
			bruteFrame.setLocationRelativeTo(null);
			bruteFrame.setVisible(true);
		}
		catch (Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(bruteFrame, "Unimplemented Exception:\n" 
			+ e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
		}
	}
}