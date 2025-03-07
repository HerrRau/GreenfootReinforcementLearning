
public interface Game
{
    /**
     * Gives the number of the player in a winning state, starting with 1, or -1 if nobody has won.
     * @return 1 if player 1 is in winning state, 2 if player 2 is in winning state, and -1 if nobody has won yet
     */
    int getWinner();

    /**
     * Have a player make a certain move.
     * @param player The player number.
     * @param move The move made, represented by an integer. Only useful in games where a move can be thus represented.
     */
    void makeMove(int player, int move);

    /**
     * Get a representation of current game state in String form.
     * For example, 001002001 for a game of tic-tac-toe after three moves
     * For some games, where not all information is available, the states
     * can be different or different players
     * @param id The ID of the player from whose perspective the state is calculated.  
     * @return the current game state as a String
     */
    String getState(int id);

    /**
     * Get a representation of current game state in String form.
     * Use this when the state is the same for all players
     * @return the current game state as a String
     */
    public String getState();  //#used only in PlayerManager, dort mal durch getState(int) ersetzen und auf diese Methode verzichten?

    /**
     * All the possible moves that can be made at the current state of the game. 
     * Does not currently differentiate between whose turn it is.
     * @return An array of numbers representing the (remaining) possible moves, here: the empty slots
     */
    int [] getLegalMoves();

    /**
     * Convenience
     */
    void printGame(String state);

    /**
     * @return initial q-value of all moves
     */
    double getInitialValue();

    //
    //
    //
    
    //relevant for continuous updates only (smart or naive)
    public double getRewardForPlayer(int playerID);

    

}
