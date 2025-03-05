
public class TicTacToe extends AbstractGame
{
    private char [] grid;
    private int activePlayer;
    private final int numberOfPlayers = 2;
    protected char [] marks = new char [] { 'X', 'O', '-' };
    private char emptyMark = marks[2];

    private String state;
    private boolean verbose;

    public TicTacToe() {
        grid = new char[9];
        for (int i=0; i<9; i++) {
            grid[i] = emptyMark;
        }
        // 0 1 2
        // 3 4 5
        // 6 7 8
        activePlayer = 0;
    }

    /**
     * Have a player make a certain move.
     * @param player The player number.
     * @param move The move made, represented by an integer. Only useful in games where a move can be thus represented.
     */
    @Override public void makeMove(int player, int move) {
        if (player!=activePlayer)   
            System.out.println("Not your turn.");
        if (grid[move]!=emptyMark)
            System.out.println("The place for your move is not empty.");
        grid[move]=marks[player];                           //make the actual move
        activePlayer = (activePlayer+1)%numberOfPlayers;    //set next player as active:         
        if (verbose) printGame();
    }

    /**
     * Gives the number of the player in a winning state, or -1 if nobody has won.
     * @return 0 if player 0 is in winning state, 1 if player 1 is in winning state, and -1 if nobody has won yet
     */
    @Override public int getWinner() {
        if (grid[0]==grid[1] && grid[1]==grid[2] && grid[0]!=emptyMark) return convertMarkToPlayerNumber(grid[0]);
        if (grid[3]==grid[4] && grid[4]==grid[5] && grid[3]!=emptyMark) return convertMarkToPlayerNumber(grid[3]);
        if (grid[6]==grid[7] && grid[7]==grid[8] && grid[6]!=emptyMark) return convertMarkToPlayerNumber(grid[6]);

        if (grid[0]==grid[3] && grid[3]==grid[6] && grid[0]!=emptyMark) return convertMarkToPlayerNumber(grid[0]);
        if (grid[1]==grid[4] && grid[4]==grid[7] && grid[1]!=emptyMark) return convertMarkToPlayerNumber(grid[1]);
        if (grid[2]==grid[5] && grid[5]==grid[8] && grid[2]!=emptyMark) return convertMarkToPlayerNumber(grid[2]);

        if (grid[0]==grid[4] && grid[4]==grid[8] && grid[0]!=emptyMark) return convertMarkToPlayerNumber(grid[0]);
        if (grid[2]==grid[4] && grid[4]==grid[6] && grid[2]!=emptyMark) return convertMarkToPlayerNumber(grid[2]);

        return -1;
    }

    private int convertMarkToPlayerNumber(char c) {
        if (c==marks[0]) return 0;
        else if (c==marks[1]) return 1;
        else return -1;
    }

    /**
     * print a graphical representation of a game state to output 
     * @param state a String representing the current state of the game, e.g. 001002001 
     */
    @Override public void printGame(String state) 
    {
        System.out.print( state.charAt(0) );
        System.out.print( "|");
        System.out.print( state.charAt(1) );
        System.out.print( "|");
        System.out.println( state.charAt(2) );
        // System.out.println("-----");

        System.out.print( state.charAt(3) );
        System.out.print( "|");
        System.out.print( state.charAt(4) );
        System.out.print( "|");
        System.out.println( state.charAt(5) );
        // System.out.println("-----");

        System.out.print( state.charAt(6) );
        System.out.print( "|");
        System.out.print( state.charAt(7) );
        System.out.print( "|");
        System.out.println( state.charAt(8) );
        //System.out.println();
    }

    /**
     * print a graphical representation of the current game grid to output 
     */
    public void printGame() {
        printGame(getState());
    }

    /**
     * Get a representation of current game state in String form.
     * For example, 001002001 for a game of tic-tac-toe after three moves
     * @return the current game state as a Strig
     */
    @Override public String getState() {
        return getState(-1);
    }

    @Override public String getState(int irrelevantPlayerID) {
        String result = "";
        for (int i=0;i<9;i++) {
            result = result + grid[i];
        }
        return result;
    }

    /**
     * All the possible moves that can be made at the current state of the game. 
     * Does not currently differentiate between whose turn it is.
     * @return An array of numbers representing the (remaining) possible moves, here: the empty slots
     */
    @Override public int [] getLegalMoves()
    {
        java.util.ArrayList<Integer> list = new java.util.ArrayList<Integer>(); //prepare an empty list of Integers
        for (int i=0; i<9; i++) {   //add the numbers of all the empty slots in the game grid        
            if (grid[i]==emptyMark) {
                list.add(i);
            }
        }
        int [ ] result = new int [list.size()]; //prepare an array of appropriate size
        for (int i=0; i<result.length; i++) {   //fill the array from the list
            result[i] = list.get(i);
        }
        return result;  //return the array
    }

    /**
     * All the possible moves than can be made at the state of the game. 
     * Does not currently differentiate between whose turn it is.
     * @param state The String representatiopn of the game state in question
     * @return An array of numbers representing the (remaining) possible moves, here: the empty slots
     */
    private int [] getLegalMoves(String state) //unused
    {
        java.util.ArrayList<Integer> list = new java.util.ArrayList<Integer>();
        for (int i=0; i<9; i++) {
            if (state.charAt(i)==emptyMark)
            {
                list.add(i);
            }
        }        
        int [ ] result = new int [list.size()];
        for (int i=0; i<result.length; i++) 
        {
            result[i] = list.get(i);
        }
        return result;        
    }

    @Override public double getInitialValue() { 
        return 0;
    }

}
