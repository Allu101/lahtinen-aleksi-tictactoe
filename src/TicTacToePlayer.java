public interface TicTacToePlayer {
    
    public void onPlayerTurnEnd(int latestChangedRow, int latestChangedColumn);

    public void play();
}
