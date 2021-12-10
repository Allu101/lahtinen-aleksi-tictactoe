import java.lang.Math;

public class RandomAiOpponent implements TicTacToePlayer {

    private int boardSize;
    private int[] freeSlots = new int[boardSize*boardSize];
    private String[][] gameBoard;

    private Game game;

    public RandomAiOpponent(String[][] gameBoard, Game game) {
        this.gameBoard = gameBoard;
        this.game = game;
        this.boardSize = gameBoard.length;
        freeSlots = new int[boardSize*boardSize];
        recreateFreeSlotsList();
    }

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

    @Override
    public void play() {
        int randomSlot = (int) (Math.random() * freeSlots.length);
        game.handleClick(freeSlots[randomSlot]);
    }

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
