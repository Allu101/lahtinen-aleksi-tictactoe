/**
 * Represents a TicTacToe opponent, which get coordinates from latest and must do something when is it turn.
 * @author Aleksi Lahtinen
 */
public interface TicTacToePlayer {
    
    /**
     * Called when the player's turn ends.
     * @param previousTurnRow Played mark row number of previous turn.
     * @param previousTurnColumn Played mark column number of previous turn.
     */
    public void onPlayerTurnEnd(int previousTurnRow, int previousTurnColumn);

    /**
     * Called when it's this player turn.
     */
    public void play();
}
