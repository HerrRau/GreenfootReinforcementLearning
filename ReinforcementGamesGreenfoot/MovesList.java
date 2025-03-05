import java.util.ArrayList;

public class MovesList extends Moves
{
    private ArrayList<Integer> moves;// = new ArrayList<Integer>();
    private ArrayList<Double> values;// = new ArrayList<Integer>();
    // private static int instanceID;
    static boolean makeDeterministic = false;

    //
    // Constructors
    //
    
    public MovesList(ArrayList<Integer> moves)
    {
        this(moves, 1.0);
    }
    
    public MovesList(int [] m, double [] v)
    {
        moves = new ArrayList<Integer>();
        values = new ArrayList<Double>();
        for (int i=0; i<m.length; i++) {
            moves.add(m[i]);
            values.add(v[i]);
        }
    }
    
    public MovesList(ArrayList<Integer> moves, double initialValue)
    {
        // instanceID++;
        this.moves=moves;
        values = new ArrayList<Double>();
        for (int i=0; i<moves.size(); i++) {
            values.add(initialValue);
        }
        //if (verbose) System.out.println("  New Moveslist, ID: "+instanceID);
    }
    
    //
    // Printers
    //
    
        private void printExplanationBest(int bestMove, ArrayList<Integer> candidates) {
        System.out.print("  Best move is "+bestMove);
        System.out.print(" (Picked from equally possible candidates: ");
        for (int i : candidates) {
            System.out.print(i+" ");
        }
        System.out.println(")");
        System.out.println("  Picked from all possible options: ");
        for (int i=0; i<moves.size(); i++) { 
            System.out.println("  "+moves.get(i)+" ("+values.get(i)+")");
        }
    }

    private void printExplanationRandom(int randomMove) {
        System.out.println("  Random move is "+randomMove);
        System.out.println("  Picked from all possible options: ");
        for (int i=0; i<moves.size(); i++) { 
            System.out.println("  "+moves.get(i)+" ("+values.get(i)+")");
        }
    }

    /**
     * Prints all possible moves managed by this object, including values
     */
    public void printList() {
        System.out.println("MovesList: ");
        for (int i=0; i<moves.size(); i++) {
            System.out.println("Move "+moves.get(i)+" ("+values.get(i)+")");
        }
    }

    public String getList() {
        String result = "MovesList: ";
        for (int i=0; i<moves.size(); i++) {
            result += moves.get(i)+" ";
        }
        return result;
    }
    //
    // Simple getters & setters & such
    //
    
    public void setVerbose(boolean b) {
        verbose = b;
    }
    
    /**
     * Increases the value for a particular move
     * @param move The move in question.
     * @param amount The amount to be added.
     */
    public void increaseValue(int move, double amount)
    {
        //timesUpdated++;
        for (int i=0; i<moves.size(); i++)
        {
            if (moves.get(i) == move)
            {
                values.set(i, values.get(i)+amount);
                return;
            }
        } 
    }

    /**
     * Set the q-value for a particular move
     * @param move The move in question.
     * @param newValue The new value to be assigned.
     */
    public void setValue(int move, double newValue)
    {
        //timesUpdated++;
        for (int i=0; i<moves.size(); i++)
        {
            if (moves.get(i) == move)
            {
                values.set(i, newValue);
                return;
            }
        } 
    }

    /**
     * Get the q-value for a particular move
     * @param move The move in question.
     * @return The current q-value.
     */
    public double getValue(int move)
    {
        for (int i=0; i<moves.size(); i++)
        {
            if (moves.get(i) == move)
            {
                return values.get(i);
            }
        } 
        return -99999;
    }

    //
    // Getters using a strategy
    //
    
    /**
     * Get the highest possible q-value for any move
     * @return The highest possible q-value.
     */
    public double getHighestValue()
    {
        if (values.size()==0) return -9999;
        double max = values.get(0);
        for (int i=1; i<values.size(); i++)
        {
            if (values.get(i) > max )
            {
                max = values.get(i);
            }
        } 
        return max;
    }

    /**
     * gets move with highest value
     * @return The int representing the move.
     */
    public int getBestMove() {

        if (moves.size()==0) return -1; // shouldn't happen

        // in case there are several candidates
        ArrayList<Integer> candidates = new ArrayList<Integer>();

        //pick first move as best, for starters
        int bestMove = moves.get(0);
        double max = values.get(0);
        candidates.add(bestMove);

        //for all other moves, compare values and remember best
        for (int i=1; i<moves.size(); i++) 
        {
            if (values.get(i)>max) {
                max = values.get(i);
                bestMove = moves.get(i);
                candidates.clear();
                candidates.add(bestMove);
            }
            else if (values.get(i)==max) {
                candidates.add(moves.get(i));
            }
        }
        if (makeDeterministic) bestMove = candidates.get(0);
        else bestMove = candidates.get( (int) (Math.random()*candidates.size()) );        
        //output
        if (verbose) printExplanationBest(bestMove, candidates); 
        return bestMove;
    }

    /**
     * Gets random legal move, including losing ones.
     * @return The int representing the move.
     */
    public int getRandomMove()
    {
        if (moves.size()==0) return -1; // shouldn't happen
        int rnd = 0;
        rnd = (int) (Math.random()*moves.size());
        int temp = moves.get( rnd );
        if (verbose) printExplanationRandom(temp);
        return temp;
    }


    //
    // unused, probably
    //    

    /**
     * @return returns true only when there is a possible move left
     */
    public boolean hasReasonableMovesLeft()
    {
        for (int i=0; i<values.size(); i++) {
            if (values.get(i)>0) return true;
        }
        return false;
    }

    /**
     * Gets random weighted legal move (barring losing ones, i.e. with threshold?)
     * @return The int representing the move.
     */
    public int getRandomWeightedMove()
    {
        ArrayList<Integer> tempMoves = new ArrayList<Integer>();
        ArrayList<Double> tempValues = new ArrayList<Double>();
        double sumOfValues = 0;
        for (int i=0; i<values.size(); i++) {
            if (values.get(i)>0) {
                tempMoves.add(moves.get(i));   
                tempValues.add(values.get(i));
                sumOfValues += values.get(i);
            }
        }

        // if there are no reasonable moves left, get random move, including losing ones
        if (tempMoves.size()==0) return getRandomMove();

        // otherwise, use weighted probabilities
        // one by one, except for the last one, use the calculated chance
        for (int i=0; i<tempMoves.size()-1; i++) {
            if (Math.random()< tempValues.get(i)/sumOfValues) {
                return tempMoves.get(i);
            }
        }
        // if none has been picked, use the last one        
        return tempMoves.get(tempMoves.size()-1);
    }

    /**
     * Gets random legal move with a positive value.
     * @return The int representing the move.
     */
    public int getRandomSensibleMove()
    {
        ArrayList<Integer> tempMoves = new ArrayList<Integer>();
        for (int i=0; i<values.size(); i++) {
            if (values.get(i)>0) tempMoves.add(moves.get(i));
        }
        // if there are no reasonable moves left, get random move, including losing ones
        if (tempMoves.size()==0) return getRandomMove();
        // otherwise, pick a random one
        int rnd = 0;
        rnd = (int) (Math.random()*tempMoves.size());
        return tempMoves.get( rnd );
    }

}