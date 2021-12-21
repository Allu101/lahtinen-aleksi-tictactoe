/**
 * The class ProAiOpponent uses a same logic than win check to make moves.
 */
public class HardOpponent implements TicTacToePlayer {

    private boolean isMeTurn = false;
    private int boardSize;
    private int winRowLength;
    private int[] nextTurnSlot;
    private int[] potentialNextTurns;
    private String mark;
    private String[][] gameBoard;

    private Game game;

    /**
     * Created opponent to TicTacToe
     * @param gameBoard Game board array
     * @param game The game class
     * @param mark This player game mark
     */
    public HardOpponent(String[][] gameBoard, Game game, int winRowLength, String mark) {
        this.gameBoard = gameBoard;
        this.game = game;
        this.winRowLength = winRowLength;
        this.mark = mark;
        this.boardSize = gameBoard.length;
        nextTurnSlot = new int[2];
    }

    /**
     * This method read and save slots where oppnent can prevent a player from winning.
     */
    @Override
    public void onPlayerTurnEnd(int latestTurnRow, int latestTurnColumn) {
        gameBoard[latestTurnRow][latestTurnColumn] = isMeTurn ? game.O : game.X;
        if (!isMeTurn) {
            int longestRow = 0;
            int[] potentialNextTurn = new int[2];
            for (int i = 1; i <= 4; i++) {
                int[][] array = getSameMarkRowStartAndEndLoc(latestTurnRow, latestTurnColumn, i);
                for (int j = 0; j <= 1; j++) {
                    if (array[2][j] >= longestRow && array[j][0] >= 0 && array[j][1] >= 0
                                && array[j][0] < gameBoard.length && array[j][1] < gameBoard.length) {
                        if (gameBoard[array[j][0]][array[j][1]].isBlank()) {
                            longestRow = array[2][j];
                            potentialNextTurn = array[j];
                        }
                    }
                }
            }
            nextTurnSlot = potentialNextTurn;
        }
        isMeTurn = !isMeTurn;
    }

    /**
     * Select a nextTurnSlots button and "clicks" it.
     */
    @Override
    public void play() {
        game.handleClick(TicTacToe.getSlotNumber(boardSize, nextTurnSlot[0], nextTurnSlot[1]));
    }

    /**
     * Calcucate and return array that contains start and end location row and column numbers.
     * @param latestTurnRow Middlepoint row of the line to be checked
     * @param latestTurnColumn Middlepoint column of the line to be checked
     * @param mode Direction of the line to be checked (1 = Horizontal, 2 = Vectical, 3, Diagonal \, 4 = Diagonal /).
     * @return Array that contains start and end location row and column numbers
     */
    private int[][] getSameMarkRowStartAndEndLoc(int latestTurnRow, int latestTurnColumn, int mode) {
        int[][] data = new int[][]{{-1, -1}, {-1, -1}, { -1, -1}};
        int longestRowLenght = 0;
        int currentRowLength = 0;

        int startRow = mode > 2 ? latestTurnRow : latestTurnRow - winRowLength < 0 ? 0
            : latestTurnRow - winRowLength;
        int startColumn = mode > 2 ? latestTurnColumn : latestTurnColumn - winRowLength < 0 ? 0
            : latestTurnColumn - winRowLength;
        int endIndex = winRowLength * 2 + 1;
        int[] currentLoc = new int[]{startRow, startColumn};

        int index = 0;
        while (mode == 3 && index <= winRowLength && currentLoc[0] -1 >= 0 && currentLoc[1] -1 >= 0) {
            currentLoc[0] = currentLoc[0] - 1;
            currentLoc[1] = currentLoc[1] - 1;
            index++;
        }
        while (mode == 4 && index <= winRowLength && currentLoc[0] -1 >= 0 && currentLoc[1] +1 < boardSize) {
            currentLoc[0] = currentLoc[0] - 1;
            currentLoc[1] = currentLoc[1] + 1;
            index++;
        }

        if (mode == 1) {
            currentLoc[0] = latestTurnRow;
        } else if (mode == 2) {
            currentLoc[1] = latestTurnColumn;
        }
        for (int i = 0; i < endIndex; i++) {
            if ((currentLoc[0] >= boardSize || currentLoc[1] >= boardSize) || currentLoc[0] < 0 || currentLoc[1] < 0) {
                data = nonOwnMarkSlotCheck(currentLoc, currentRowLength, data, longestRowLenght, mode);
                break;
            }
            if (gameBoard[currentLoc[0]][currentLoc[1]].isBlank() || gameBoard[currentLoc[0]][currentLoc[1]].equals(mark)) {
                data = nonOwnMarkSlotCheck(currentLoc, currentRowLength, data, longestRowLenght, mode);

                currentRowLength = 0;
            } else {
                currentRowLength++;
                longestRowLenght = currentRowLength >= longestRowLenght ? currentRowLength : longestRowLenght;
            }
            currentLoc = TicTacToe.movePointer(currentLoc, 1, mode);
        }
        return data;
    }

    /**
     * Calculates and return the 2d array that contains startPoint, endPoint and length of the finished line.
     * @param currentLoc Pointer current slot on gameboard.
     * @param currentRowLength Length of the line in the current position
     * @param data longest row start- and endpoints and row length.
     * @param longestRowLenght Longest row on the current directional.
     * @param mode Direction of the line to be checked (1 = Horizontal, 2 = Vectical, 3, Diagonal \, 4 = Diagonal /).
     * @return 2d array that contains StartPoint, endPoint and length of the finished line.
     */
    private int[][] nonOwnMarkSlotCheck(int[] currentLoc, int currentRowLength, int[][] data, int longestRowLenght, int mode) {
        if (currentRowLength >= longestRowLenght && longestRowLenght >= 1) {
            data[2][1] = currentRowLength;
            data[2][0] = currentRowLength;
            data[1][0] = currentLoc[0];
            data[1][1] = currentLoc[1];
            data[0] = TicTacToe.movePointer(data[1], -currentRowLength-1, mode);
        }
        return data;
    }
}
