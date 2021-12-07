import java.io.Console;
import java.lang.Math;

public class Game {
    
    private int winRowLength;
    private String[][] gameBoard;

    public Game() {
        initialGame();
        startGame();
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
        int boardSize = Math.max(getUserInput("Give a game board size: "), 3);
        gameBoard = new String[boardSize+2][boardSize+2];
        int winRowRawLength = getUserInput("How many chars must be a win row?");
        winRowLength = Math.min(boardSize >= 10 ? Math.max(winRowRawLength, 5) : Math.max(winRowRawLength, 3), boardSize);
    }

    public void printGameBoard() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (i == 0 || j == 0 || i == gameBoard.length -1 || j == gameBoard.length -1) {
                    System.out.print("x");
                } else if (gameBoard[i][j] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(gameBoard[i][j]);
                }
            }
            System.out.println();
        }
    }

    private void startGame() {
        boolean isGameOn = true;
        boolean playerTurn = true;
        while (isGameOn) {
            printGameBoard();
            if (playerTurn) {

            } else {

            }
            playerTurn = !playerTurn;
        }
        
    }
}
