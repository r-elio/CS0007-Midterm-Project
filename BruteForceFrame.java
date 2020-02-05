import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;


public class BruteForceFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private GridBagConstraints constraints;
	private JPanel sortPanel;
	private JPanel comboPanel;
	private JLabel sortLabel;
	private JComboBox<String> sortBox;
	private JPanel buttonPanel;
	private JButton inputButton;
	private JButton sortButton;

	private String inputStr = "";
	private ArrayList<Integer> inputArray;

	private static final String[] SORTALGO = {"Bubble Sort","Selection Sort"};

	public BruteForceFrame(){
		setTitle("Brute Force Algorithm");

		sortLabel = new JLabel("Sort Type: ");

		sortBox = new JComboBox<String>(SORTALGO);
		sortBox.setSelectedIndex(0);
		sortBox.setFocusable(false);

		inputButton = new JButton("Input");
		inputButton.setPreferredSize(new Dimension(75,25));
		inputButton.setFocusable(false);
		inputButton.addActionListener(new InputListener());

		sortButton = new JButton("Sort");
		sortButton.setPreferredSize(new Dimension(75,25));
		sortButton.setFocusable(false);
		sortButton.addActionListener(new SortListener());

		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(inputButton);
		buttonPanel.add(sortButton);

		comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		comboPanel.add(sortLabel);
		comboPanel.add(sortBox);

		constraints = new GridBagConstraints();

		sortPanel = new JPanel(new GridBagLayout());
		constraints.insets = new Insets(5,5,5,5);

		constraints.gridx = 0;
		constraints.gridy = 0;
		sortPanel.add(comboPanel,constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
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

	private class InputListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event){
			if (inputArray != null && inputArray.size() != 0){
				int response = JOptionPane.showConfirmDialog(rootPane, "Do you want to change the input?", 
				"Input", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION){
					setInputArray();
				}
			}
			else {
				setInputArray();
			}
		}
	}

	private class SortListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event){
			if (inputArray == null || inputArray.size() == 0){
				JOptionPane.showMessageDialog(rootPane, "You must set the input first.", 
				"Null Input", JOptionPane.WARNING_MESSAGE);
			}
			else {
				setOutputArray();
			}
		}
	}

	private void setInputArray(){
		JTextArea inputArea = new JTextArea(inputStr);
		inputArea.setFont(new Font("Consolas",Font.PLAIN,16));
		JScrollPane inputScroll = new JScrollPane(inputArea);
		inputScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		inputScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		inputScroll.setPreferredSize(new Dimension(300,300));

		Object[] prompt = {inputScroll};
		Object[] options = {"Save","Reset","Cancel"};

		JOptionPane optionPane = new JOptionPane(prompt, JOptionPane.PLAIN_MESSAGE,
		JOptionPane.OK_CANCEL_OPTION, null, options);

		JDialog dialog = new JDialog(this, "Input", true);
		dialog.setContentPane(optionPane);

		optionPane.addPropertyChangeListener(new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (JOptionPane.VALUE_PROPERTY.equals(event.getPropertyName())){
					if (optionPane.getValue().equals(options[0])){
						optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
						try {
							ArrayList<Integer> tempArray = new ArrayList<Integer>();

							inputStr = inputArea.getText();
							String str = "";
							for (int i = 0; i < inputStr.length(); ++i){
								if (inputStr.charAt(i) != ' ' && 
									inputStr.charAt(i) != '\n' && 
									inputStr.charAt(i) != '\t'){
									str = str + inputStr.charAt(i);
								}

								if (!(str.isEmpty()) && (inputStr.charAt(i) == ' ' || inputStr.charAt(i) == '\n' || 
									inputStr.charAt(i) == '\t' || i == (inputStr.length() - 1))){
									Integer input = Integer.valueOf(str);

									for (int j = 0; j < tempArray.size(); ++j){
										if (input == tempArray.get(j)){
											throw new NumberFormatException("Duplicate Number Detected (" + input + ")");
										}
									}

									tempArray.add(input);
									str = "";
								}
							}

							inputArray = tempArray;

							for (Integer i : inputArray){
								System.out.print(i + " ");
							}
							System.out.println();

							dialog.dispose();
						}
						catch (NumberFormatException e){
							JOptionPane.showMessageDialog(rootPane, "NumberFormatException:\n" 
							+ e.getMessage(), "Number Format Exception", JOptionPane.ERROR_MESSAGE);
						}
						catch (Exception e){
							e.printStackTrace();
							JOptionPane.showMessageDialog(rootPane, "Unimplemented Exception:\n" 
							+ e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
						}
					}
					else if (optionPane.getValue().equals(options[1])){
						optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
						inputArea.setText("");
						inputStr = "";
						inputArray = null;
					}
					else if (optionPane.getValue().equals(options[2])){
						optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
						dialog.dispose();
					}
				}
			}
		});

		dialog.pack();
		dialog.setLocationRelativeTo(rootPane);
		dialog.setVisible(true);
	}

	private void setOutputArray(){
		
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