import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame {
    private int randomNumber;
    private int attempts;
    private int score;
    private int maxAttempts;
    private int round;

    private JFrame frame;
    private JTextField guessField;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private JLabel roundLabel;

    public NumberGuessingGame() {
        // Initialize game variables
        maxAttempts = 10;
        round = 1;
        score = 0;
        resetGame();

        // Set up the Swing components
        frame = new JFrame("Number Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Round label at the top
        roundLabel = new JLabel("Round: 1");
        roundLabel.setForeground(new Color(0, 102, 204));  // Different color for the round label
        roundLabel.setFont(new Font("Arial", Font.BOLD, 24));  // Larger font size
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(roundLabel, gbc);

        JLabel promptLabel = new JLabel("Enter your guess (1-100):");
        promptLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(promptLabel, gbc);

        guessField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(guessField, gbc);

        JButton guessButton = new JButton("Guess");
        guessButton.setBackground(new Color(50, 150, 250));
        guessButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        frame.add(guessButton, gbc);

        feedbackLabel = new JLabel("");
        feedbackLabel.setForeground(new Color(255, 140, 0));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        frame.add(feedbackLabel, gbc);

        attemptsLabel = new JLabel("Attempts left: " + maxAttempts);
        attemptsLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        frame.add(attemptsLabel, gbc);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 4;
        frame.add(scoreLabel, gbc);

        // Set up the action listener for the guess button
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });

        frame.setVisible(true);
    }

    private void resetGame() {
        Random rand = new Random();
        randomNumber = rand.nextInt(100) + 1;  // Generate a random number between 1 and 100
        attempts = maxAttempts;
    }

    private void handleGuess() {
        String guessText = guessField.getText();
        try {
            int guess = Integer.parseInt(guessText);

            if (guess < 1 || guess > 100) {
                feedbackLabel.setText("Please enter a number between 1 and 100.");
                return;
            }

            attempts--;

            if (guess == randomNumber) {
                int roundScore = attempts * 10;  // Give points based on remaining attempts
                score += roundScore;
                feedbackLabel.setText("Correct! The number was " + randomNumber + ". You scored " + roundScore + " points.");

                // Display the score in a big font before asking if they want to play again
                JLabel scoreMessage = new JLabel("Round " + round + " Score: " + roundScore);
                scoreMessage.setFont(new Font("Arial", Font.BOLD, 24));
                JOptionPane.showMessageDialog(frame, scoreMessage, "Score", JOptionPane.INFORMATION_MESSAGE);

                // Show total score message
                JLabel totalScoreMessage = new JLabel("Total Score: " + score);
                totalScoreMessage.setFont(new Font("Arial", Font.BOLD, 20));
                JOptionPane.showMessageDialog(frame, totalScoreMessage, "Total Score", JOptionPane.INFORMATION_MESSAGE);

                round++;
                roundLabel.setText("Round: " + round);

                int playAgain = JOptionPane.showConfirmDialog(frame, "Do you want to play another round?", "Play Again", JOptionPane.YES_NO_OPTION);
                if (playAgain == JOptionPane.YES_OPTION) {
                    resetGame();
                } else {
                    System.exit(0);
                }
            } else if (guess < randomNumber) {
                feedbackLabel.setText("Too low! Try again.");
            } else {
                feedbackLabel.setText("Too high! Try again.");
            }

            if (attempts == 0) {
                feedbackLabel.setText("Game over! The number was " + randomNumber + ".");
                int playAgain = JOptionPane.showConfirmDialog(frame, "Do you want to play another round?", "Play Again", JOptionPane.YES_NO_OPTION);
                if (playAgain == JOptionPane.YES_OPTION) {
                    resetGame();
                } else {
                    System.exit(0);
                }
            }

            attemptsLabel.setText("Attempts left: " + attempts);
            scoreLabel.setText("Score: " + score);

        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Invalid input! Please enter a number.");
        }
    }

    public static void main(String[] args) {
        new NumberGuessingGame();
    }
}
