
/**
 * The class TicTacToe contain main method of TicTacToe game and "utils" method(s).
 * @author Aleksi Lahtinen
 */
public class TicTacToe {
    public static void main(String[] args) {
        Game game = new Game();
    }

    /**
     * Return the order number of given slot (e.g. boardSize = 3, row = 1, colum = 2 -> return 5)
     * @param boardSize Gameboard side length
     * @param row Slot's row number
     * @param column Slot's column number
     * @return Slot order number.
     */
    public static int getSlotNumber(int boardSize, int row, int column) {
        return row * boardSize + column;
    }
}