import java.awt.Font;
import java.awt.GridLayout;
import java.io.Console;
import java.lang.Math;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Game extends JFrame {
    
    private boolean playerXTurn = true;
    private int boardSize;
    private int winRowLength;
    private String[][] gameBoard;

    private TicTacToePlayer opponent;

    public Game() {
        initialGame();
        initialJFrame();

        opponent = new RandomAiOpponent();
        startGame();
    }

    public boolean setMarkToGameBoard(String mark, int xLoc, int yLoc) {
        if (gameBoard[yLoc][xLoc].isBlank()) {
            gameBoard[yLoc][xLoc] = mark;
            return true;
        }
        return false;
    }

    public void handleClick(JButton clickedButton) {
        clickedButton.setText(playerXTurn ? "X" : "0");
        clickedButton.setEnabled(false);
        String[] array = clickedButton.getName().split(";");
        gameBoard[Integer.parseInt(array[0])][Integer.parseInt(array[1])] = playerXTurn ? "X" : "0";
        opponent.onPlayerTurnEnd(gameBoard);
        playerXTurn = !playerXTurn;
    }

    private int getUserInput(String message) {
        Console console = System.console();
        int input = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(message);
            try {
                input = Integer.parseInt(console.readLine());
                validInput = true;
            } catch (Exception e) {
                System.out.println("Please give a number");
            }
        }
        return input;
    }

    private void initialGame() {
        boardSize = Math.max(getUserInput("Give a game board size: "), 3);
        gameBoard = new String[boardSize][boardSize];
        int winRowRawLength = getUserInput("How much marks must be a row to win?: ");
        winRowLength = Math.min(boardSize >= 10 ? Math.max(winRowRawLength, 5) : Math.max(winRowRawLength, 3), boardSize);
    }

    private void initialJFrame() {
        setTitle("Tic Tac Toe");
        setSize(600, 600);
        setVisible(true);

        GridLayout grid = new GridLayout(boardSize, boardSize);
        setLayout(grid);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                JButton b = new JButton();
                String coordinates = i + ";" + j;
                b.addActionListener(e -> {
                    b.setName(coordinates);
                    b.setFont(new Font("Arial", Font.PLAIN, 50));
                    handleClick(b);
                });
                add(b);
            }
        }
    }

    private void startGame() {
        boolean isGameOn = true;
        while (isGameOn) {
            if (!playerXTurn) {
                opponent.play();
            }
        }
    }
}
