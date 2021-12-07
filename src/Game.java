import java.io.Console;
import java.lang.Math;

class Game {
    
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
        int boardSize = Math.max(getUserInput("Give a game board size"), 3);
        gameBoard = new String[boardSize+2][boardSize+2];
    }

    public void printGameBoard() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (i == 0 || j == 0 || i == gameBoard.length -1 || j == gameBoard.length -1) {
                    System.out.println("⬜");
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
            if (playerTurn) {

            } else {

            }
        }
    }
}
