package Student_Grade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGradeCalculatorGUI extends JFrame {
    private JTextField assignmentField;
    private JTextField quizField;
    private JTextField midtermField;
    private JTextField finalExamField;
    private JLabel resultLabel;
    private JLabel letterGradeLabel;

    public StudentGradeCalculatorGUI() {
        createView();

        setTitle("Student Grade Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void createView() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel assignmentLabel = new JLabel("Assignment Grade:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(assignmentLabel, gbc);

        assignmentField = createTextField();
        gbc.gridx = 1;
        panel.add(assignmentField, gbc);

        JLabel quizLabel = new JLabel("Quiz Grade:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(quizLabel, gbc);

        quizField = createTextField();
        gbc.gridx = 1;
        panel.add(quizField, gbc);

        JLabel midtermLabel = new JLabel("Midterm Exam Grade:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(midtermLabel, gbc);

        midtermField = createTextField();
        gbc.gridx = 1;
        panel.add(midtermField, gbc);

        JLabel finalExamLabel = new JLabel("Final Exam Grade:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(finalExamLabel, gbc);

        finalExamField = createTextField();
        gbc.gridx = 1;
        panel.add(finalExamField, gbc);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new CalculateButtonActionListener());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(calculateButton, gbc);

        resultLabel = new JLabel("Your final grade is: ");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(resultLabel, gbc);

        letterGradeLabel = new JLabel("Your letter grade is: ");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(letterGradeLabel, gbc);
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(10);
        textField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        return textField;
    }

    private class CalculateButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double assignmentGrade = Double.parseDouble(assignmentField.getText());
                double quizGrade = Double.parseDouble(quizField.getText());
                double midtermGrade = Double.parseDouble(midtermField.getText());
                double finalExamGrade = Double.parseDouble(finalExamField.getText());

                if (isValidGrade(assignmentGrade) && isValidGrade(quizGrade) && isValidGrade(midtermGrade) && isValidGrade(finalExamGrade)) {
                    double finalGrade = calculateFinalGrade(assignmentGrade, quizGrade, midtermGrade, finalExamGrade);
                    String letterGrade = getLetterGrade(finalGrade);

                    resultLabel.setText(String.format("Your final grade is: %.2f", finalGrade));
                    letterGradeLabel.setText("Your letter grade is: " + letterGrade);
                } else {
                    showErrorDialog("Grades must be between 0 and 100.");
                }
            } catch (NumberFormatException ex) {
                showErrorDialog("Please enter valid numeric grades.");
            }
        }
    }

    private boolean isValidGrade(double grade) {
        return grade >= 0 && grade <= 100;
    }

    private double calculateFinalGrade(double assignmentGrade, double quizGrade, double midtermGrade, double finalExamGrade) {
        double assignmentWeight = 0.4;
        double quizWeight = 0.1;
        double midtermWeight = 0.2;
        double finalExamWeight = 0.3;

        return (assignmentGrade * assignmentWeight) +
               (quizGrade * quizWeight) +
               (midtermGrade * midtermWeight) +
               (finalExamGrade * finalExamWeight);
    }

    private String getLetterGrade(double finalGrade) {
        if (finalGrade >= 90) {
            return "A";
        } else if (finalGrade >= 80) {
            return "B";
        } else if (finalGrade >= 70) {
            return "C";
        } else if (finalGrade >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeCalculatorGUI().setVisible(true);
        });
    }
}
