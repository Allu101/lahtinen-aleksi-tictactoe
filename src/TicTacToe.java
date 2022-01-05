
/**
 * The class TicTacToe contain main method of TicTacToe game and "utils" method(s).
 * @author Aleksi Lahtinen
 */
public class TicTacToe {
    public static void main(String[] args) {
        new Game();
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

    /**
     * Move and return target location.
     * @param currentLoc Move start location
     * @param amount move amount (slots)
     * @param mode Direction of the move (1 = Horizontal >, 2 = Vectical v, 3, Diagonal \, 4 = Diagonal /).
     * @return Target location
     */
    public static int[] movePointer(int[] currentLoc, int amount, int mode) {
        int[] newLoc = new int[]{currentLoc[0], currentLoc[1]};
        if (mode == 1 || mode == 3) {
            newLoc[1] = currentLoc[1] + amount;
        }
        if (mode == 2 || mode == 3 || mode == 4) {
            newLoc[0] = currentLoc[0] + amount;
        }
        if (mode == 4) {
            newLoc[1] = currentLoc[1] - amount;
        }
        return newLoc;
    }
}