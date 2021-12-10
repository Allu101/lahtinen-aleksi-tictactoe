import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.io.Console;
import java.lang.Math;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Game extends JFrame {
    
    private boolean playerXTurn = true;
    private int boardSize;
    private int winRowLength;
    private JButton[] buttons;
    private String[][] gameBoard;

    private TicTacToePlayer opponent;

    public Game() {
        initialGame();
        initialJFrame();
        opponent = new RandomAiOpponent(gameBoard, this);
    }

    public void handleClick(int slotNumber) {
        handleClick(buttons[slotNumber]);
    }

    public void handleClick(JButton clickedButton) {
        String currentMark = playerXTurn ? "X" : "0";
        clickedButton.setText(currentMark);
        clickedButton.setEnabled(false);
        String[] array = clickedButton.getName().split(";");
        int row = Integer.parseInt(array[1]);
        int column = Integer.parseInt(array[2]);
        setMarkToGameBoard(currentMark, row, column);
        
        playerXTurn = !playerXTurn;
        if (!playerXTurn) {
            opponent.play();
            playerXTurn = true;
        }
        checkPossibleWinOrDraw();
    }

    private void checkPossibleWinOrDraw() {
        int emptySlots = 0;
        for (int row = 0; row < gameBoard.length; row++) {
            for (int column = 0; column < gameBoard.length; column++) {
                if (gameBoard[row][column] == null) {
                    emptySlots++;
                }
            }
        }
        if (emptySlots == 0) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            System.out.println("Draw Game!");
        }
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
        buttons = new JButton[boardSize*boardSize];

        GridLayout grid = new GridLayout(boardSize, boardSize);
        setLayout(grid);
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                JButton button = new JButton();
                String slotNumberAndCoords = TicTacToe.getSlotNumber(boardSize, row, column)  + ";" + row + ";" + column;
                button.setName(slotNumberAndCoords);
                button.setFont(new Font("Arial", Font.PLAIN, 50));
                button.addActionListener(e -> handleClick(button));
                add(button);
                buttons[Integer.parseInt(slotNumberAndCoords.split(";")[0])] = button;
            }
        }
    }

    private void setMarkToGameBoard(String mark, int row, int column) {
        gameBoard[row][column] = mark;
        opponent.onPlayerTurnEnd(row, column);
    }
}
