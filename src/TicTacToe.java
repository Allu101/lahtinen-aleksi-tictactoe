
/**
 * TicTacToe
 * @author Aleksi Lahtinen
 */
public class TicTacToe {
    public static void main(String[] args) {
        Game game = new Game();
    }

    public static int getSlotNumber(int boardSize, int row, int column) {
        return row * boardSize + column;
    }
}