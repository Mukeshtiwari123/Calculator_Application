package Calculator_Task;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Calculator_WithSwing extends JFrame implements ActionListener {
    private JTextField displayField;  // Field to display input and results
    private StringBuilder input;      // Builder to accumulate user input
    private ArrayList<String> history; // List to store calculation history

    public Calculator_WithSwing() {
        // Set Look and Feel to Nimbus for a modern appearance
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Initialize input and history
        input = new StringBuilder();
        history = new ArrayList<>();

        // Frame settings
        setTitle("Calculator");
        setSize(400, 500); // Set the size of the calculator window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit application on close
        setLayout(null); // Use absolute positioning for components

        // Load and scale the clock image for the history button
        ImageIcon clockIcon = new ImageIcon("C:\\Users\\Anudip\\Desktop\\renu\\Mukesh_Anudip_Project\\src\\Calculator_Task\\clock.png");
        Image clockImage = clockIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Scale image
        ImageIcon scaledClockIcon = new ImageIcon(clockImage);

        // Create history button with clock icon
        JButton historyButton = new JButton(scaledClockIcon);
        historyButton.setBounds(350, 5, 30, 30); // Set position and size
        historyButton.setBorderPainted(false); // Remove button border
        historyButton.setContentAreaFilled(false); // Remove background fill
        historyButton.setFocusPainted(false); // Remove focus painting
        historyButton.setOpaque(false); // Make button background transparent
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHistory(); // Show history when button is clicked
            }
        });
        add(historyButton); // Add history button to the frame

        // Create and configure the display field
        displayField = new JTextField();
        displayField.setBounds(50, 40, 300, 50); // Set position and size
        displayField.setEditable(false); // Make display non-editable
        displayField.setFont(displayField.getFont().deriveFont(Font.BOLD)); // Set bold font
        add(displayField); // Add display field to the frame

        // Create buttons for digits and operations
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        int x = 50; // Starting x position for buttons
        int y = 100; // Starting y position for buttons

        // Create and place buttons in a grid layout
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.setBounds(x, y, 70, 50); // Set position and size
            button.addActionListener(this); // Add action listener to handle clicks
            add(button); // Add button to the frame

            x += 80; // Move x position for next button
            if ((i + 1) % 4 == 0) { // After every 4 buttons, move to the next row
                x = 50;
                y += 60;
            }
        }

        // Button for deleting the last digit
        JButton deleteLastButton = new JButton("Del");
        deleteLastButton.setBounds(50, y, 150, 50); // Set position and size
        deleteLastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (input.length() > 0) {
                    input.setLength(input.length() - 1); // Remove last character
                    displayField.setText(input.toString()); // Update display field
                }
            }
        });
        add(deleteLastButton); // Add delete last button to the frame

        // Button for deleting the entire input
        JButton deleteAllButton = new JButton("Delete");
        deleteAllButton.setBounds(200, y, 150, 50); // Set position and size
        deleteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input.setLength(0); // Clear input
                displayField.setText(""); // Clear display field
            }
        });
        add(deleteAllButton); // Add delete all button to the frame
    }

    public static void main(String[] args) {
        Calculator_WithSwing calculator = new Calculator_WithSwing();
        calculator.setVisible(true); // Make the calculator visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("=")) {
            try {
                String resultString;
                // Check if input contains a decimal point
                if (input.toString().contains(".")) {
                    double result = evaluate(input.toString()); // Evaluate as double
                    resultString = input.toString() + " = " + result;
                } else {
                    int result = (int) evaluate(input.toString()); // Evaluate as integer
                    resultString = input.toString() + " = " + result;
                }
                displayField.setText(resultString); // Display result
                history.add(resultString); // Add result to history
                input.setLength(0); // Clear input
            } catch (ArithmeticException | NumberFormatException ex) {
                // Handle exceptions and display error message
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                input.setLength(0); // Clear input
            }
        } else {
            input.append(command); // Append command to input
            displayField.setText(input.toString()); // Update display field
        }
    }

    // Function to evaluate the expression
    private double evaluate(String expression) {
        String[] tokens = expression.split("(?<=[-+*/])|(?=[-+*/])"); // Split expression into tokens
        double result = Double.parseDouble(tokens[0]); // Initialize result with the first token

        // Iterate through tokens and perform calculations
        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            double operand = Double.parseDouble(tokens[i + 1]);

            switch (operator) {
                case "+":
                    result += operand;
                    break;
                case "-":
                    result -= operand;
                    break;
                case "*":
                    result *= operand;
                    break;
                case "/":
                    if (operand == 0) {
                        throw new ArithmeticException("Cannot divide by zero"); // Handle division by zero
                    }
                    result /= operand;
                    break;
            }
        }

        return result; // Return the final result
    }

    // Function to display calculation history
    private void showHistory() {
        if (history.isEmpty()) {
            // Show message if there is no history
            JOptionPane.showMessageDialog(this, "There is no History Yet", "Calculation History", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Build history text and display in a scrollable text area
            StringBuilder historyText = new StringBuilder("History:\n");
            for (String entry : history) {
                historyText.append(entry).append("\n");
            }
            JTextArea textArea = new JTextArea(historyText.toString());
            textArea.setEditable(false); // Make text area non-editable
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(380, 300)); // Set preferred size for scroll pane
            JOptionPane.showMessageDialog(this, scrollPane, "Calculation History", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
