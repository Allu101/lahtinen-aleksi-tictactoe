import java.lang.Math;

/**
 * The class ProAiOpponent uses a same logic than win check to make moves.
 */
public class ProAiOpponent implements TicTacToePlayer {

    private int boardSize;
    private int winRowLength;
    private int[] freeSlots = new int[boardSize*boardSize];
    private String[][] gameBoard;

    private Game game;

    /**
     * Created opponent to TicTacToe
     * @param gameBoard Game board array
     * @param game The game class
     */
    public ProAiOpponent(String[][] gameBoard, Game game, int winRowLength) {
        this.gameBoard = gameBoard;
        this.game = game;
        this.winRowLength = winRowLength;
        this.boardSize = gameBoard.length;
        freeSlots = new int[boardSize*boardSize];
        recreateFreeSlotsList();
    }

    /**
     * This method calculates free slots count.
     */
    @Override
    public void onPlayerTurnEnd(int latestChangedRow, int latestChangedColumn) {
        if (freeSlots.length <= 1) {
            return;
        }
        int tempIndex = 0;
        int[] tempFreeSlots = new int[freeSlots.length - 1];
        for (int i = 0; i < freeSlots.length; i++) {
            if (TicTacToe.getSlotNumber(boardSize, latestChangedRow, latestChangedColumn) != i) {
                tempFreeSlots[tempIndex] = freeSlots[i];
            }
        }
        freeSlots = tempFreeSlots;
        recreateFreeSlotsList();
    }

    /**
     * Select a random free button and "clicks" it.
     */
    @Override
    public void play() {
        int randomSlot = (int) (Math.random() * freeSlots.length);
        game.handleClick(freeSlots[randomSlot]);
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
     * This method recalcucate free slots on gameboard and adds it to array.
     */
    private void recreateFreeSlotsList() {
        int index = 0;
        for (int row = 0; row < gameBoard.length; row++) {
            for (int column = 0; column < gameBoard.length; column++) {
                if (gameBoard[row][column] == null) {
                    int slotNumber  = TicTacToe.getSlotNumber(boardSize, row, column);
                    freeSlots[index] = slotNumber;
                    index++;
                }
            }
        }
    }

}
