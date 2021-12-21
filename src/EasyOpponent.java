import java.lang.Math;

/**
 * The class RandomAiOpponent uses a Math.random() to make moves.
 */
public class EasyOpponent implements TicTacToePlayer {

    private int boardSize;
    private int[] freeSlots;
    private String[][] gameBoard;

    private Game game;

    /**
     * Created opponent to TicTacToe
     * @param gameBoard Game board array
     * @param game The game class
     */
    public EasyOpponent(String[][] gameBoard, Game game) {
        this.gameBoard = gameBoard;
        this.game = game;
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
     * This method recalcucate free slots on gameboard and adds it to array.
     */
    private void recreateFreeSlotsList() {
        int index = 0;
        for (int row = 0; row < gameBoard.length; row++) {
            for (int column = 0; column < gameBoard.length; column++) {
                if (gameBoard[row][column].isBlank()) {
                    int slotNumber  = TicTacToe.getSlotNumber(boardSize, row, column);
                    freeSlots[index] = slotNumber;
                    index++;
                }
            }
        }
    }

}
