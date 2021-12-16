import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * The class Game contais main logic for TicTacToe Game.
 * 
 * @author Aleksi Lahtinen
 */
public class Game extends JFrame {
    
    private final static String O  = "0";
    private final static String X  = "X";

    private boolean isGameOn = true;
    private boolean playerXTurn = true;
    private int boardSize;
    private int difficulty;
    private int winRowLength;
    private JButton[] buttons;
    private String[][] gameBoard;

    private TicTacToePlayer opponent;

    /**
     * Create and start new TicTacToe game.
     */
    public Game() {
        initialGame();
        initialJFrame();
        opponent = difficulty == 2 ? new ProAiOpponent(gameBoard, this, winRowLength) :  new RandomAiOpponent(gameBoard, this);
    }

    /**
     * Called when opponent make turn.
     * @param slotNumber Clicked slot of gameBoard
     */
    public void handleClick(int slotNumber) {
        handleClick(buttons[slotNumber]);
    }

    /**
     * Called when player clicked someone button of gui.
     * This method set correct mark to clicked button and disables it, checks possible win or draw and change turn to other player.
     * @param clickedButton Clicked button.
     */
    public void handleClick(JButton clickedButton) {
        String currentMark = playerXTurn ? X : O;
        clickedButton.setText(currentMark);
        clickedButton.setEnabled(false);
        String[] array = clickedButton.getName().split(";");
        int row = Integer.parseInt(array[1]);
        int column = Integer.parseInt(array[2]);
        setMarkToGameBoard(currentMark, row, column);
        
        checkPossibleWinOrDraw(row, column);
        playerXTurn = !playerXTurn;
        if (!playerXTurn && isGameOn) {
            opponent.play();
            playerXTurn = true;
        }
    }

    /**
     * This methods calcucate empty slots, stop game if it's draw or check possible win.
     * @param currentTurnRow Latest turn row number
     * @param currentTurnColumn Latest turn column number
     */
    private void checkPossibleWinOrDraw(int currentTurnRow, int currentTurnColumn) {
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
        } else {
            String mark = playerXTurn ? X : O;
            for (int i = 1; i <= 4; i++) {
                if (getSameMarkRowLength(currentTurnRow, currentTurnColumn, mark, i) >= winRowLength) {
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                    System.out.println((playerXTurn ? "Player" : "PC") + " won the game!");
                    isGameOn = false;
                }
            }
        }
    }

    /**
     * Calcucate and return max same mark row lenght of given direction.
     * @param latestTurnRow Middlepoint row of the line to be checked
     * @param latestTurnColumn Middlepoint column of the line to be checked
     * @param mark Current player.
     * @param mode Direction of the line to be checked (1 = Horizontal, 2 = Vectical, 3, Diagonal \, 4 = Diagonal /).
     * @return max same mark row lenght of given direction.
     */
    private int getSameMarkRowLength(int latestTurnRow, int latestTurnColumn, String mark, int mode) {
        int rowLength = 0;
        int startRow = mode > 2 ? latestTurnRow : latestTurnRow - winRowLength + 1 < 0 ? 0
            : latestTurnRow - winRowLength + 1;
        int startColumn = mode > 2 ? latestTurnColumn : latestTurnColumn - winRowLength + 1 < 0 ? 0
            : latestTurnColumn - winRowLength + 1;
        int endIndex = winRowLength * 2 - 1;
        int[] currentLoc = new int[]{startRow, startColumn};

        while (mode == 3 && currentLoc[0] -1 >= 0 && currentLoc[1] -1 >= 0) {
            currentLoc[0] = currentLoc[0] - 1;
            currentLoc[1] = currentLoc[1] - 1;
        }
        while (mode == 4 && currentLoc[0] -1 >= 0 && currentLoc[1] +1 < boardSize) {
            currentLoc[0] = currentLoc[0] - 1;
            currentLoc[1] = currentLoc[1] + 1;
        }

        if (mode == 1) {
            currentLoc[0] = latestTurnRow;
        } else if (mode == 2) {
            currentLoc[1] = latestTurnColumn;
        }
        for (int i = 0; i < endIndex; i++) {
            if ((currentLoc[0] >= boardSize || currentLoc[1] >= boardSize) || currentLoc[0] < 0 || currentLoc[1] < 0) {
                break;
            }
            if (gameBoard[currentLoc[0]][currentLoc[1]] == null || !gameBoard[currentLoc[0]][currentLoc[1]].equals(mark)) {
                rowLength = 0;
            } else {
                rowLength++;
                if (rowLength >= winRowLength) {
                    return rowLength;
                }
            }
            if (mode == 1 || mode == 3) {
                currentLoc[1] = currentLoc[1] + 1;
            }
            if (mode == 2 || mode == 3 || mode == 4) {
                currentLoc[0] = currentLoc[0] + 1;
            }
            if (mode == 4) {
                currentLoc[1] = currentLoc[1] - 1;
            }
        }
        return rowLength;
    }

    /**
     * Return the input that user given until it's valid.
     * @param message Shown to user before asking the input.
     * @param min Min possible value for user input.
     * @param max Max possible value for user input.
     * @return user input until it's valid.
     */
    private int getUserInput(String message, int min, int max) {
        int input = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(message);
            try {
                input = Integer.parseInt(System.console().readLine());
                if (input >= min && input <= max) {
                    validInput = true;
                } else {
                    System.out.println("Please give number between [" + min + "-" + max + "]");
                }
            } catch (Exception e) {
                System.out.println("Please give a number");
            }
        }
        return input;
    }

    /**
     * This method ask board size and winrow lenght from user and create 2d array.
     */
    private void initialGame() {
        boardSize = getUserInput("Give a game board size: ", 3, 15);
        gameBoard = new String[boardSize][boardSize];
        winRowLength = getUserInput("How much marks must be a row to win?: ", boardSize >= 10 ? 5 : 3, boardSize);
        difficulty = getUserInput("Select difficulty (1 = Easy, 2 = hard)", 1, 2);
    }

    /**
     * This method sets title, size, layoout and create all 9 buttons to JFrame.
     */
    private void initialJFrame() {
        setTitle("Tic Tac Toe");
        setSize(600, 600);
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
        setVisible(true);
    }

    /**
     * This method sets the played mark to 2d and call the onPlayerTurnEnd methods on the TicTacToe interface.
     * @param mark Played mark
     * @param row Played mark row number
     * @param column Played mark column number
     */
    private void setMarkToGameBoard(String mark, int row, int column) {
        gameBoard[row][column] = mark;
        opponent.onPlayerTurnEnd(row, column);
    }
}
