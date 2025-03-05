import java.util.ArrayList;

public class MovesArraySimple extends Moves
{
    private double [] values;
    static boolean makeDeterministic = false;

    //
    // Constructors
    //

    public MovesArraySimple(double [] values)
    {
        this.values = values;
    }

    public MovesArraySimple(int numberOfMoves, double initialValue)
    {
        values = new double[numberOfMoves];
        for (int i=0; i<numberOfMoves; i++) {
            values[i]=initialValue;
        }
    }

    //
    // Printers
    //

    // private void printExplanationBest(int bestMove, ArrayList<Integer> candidates) {
    // System.out.print("  Best move is "+bestMove);
    // System.out.print(" (Picked from equally possible candidates: ");
    // for (int i : candidates) {
    // System.out.print(i+" ");
    // }
    // System.out.println(")");
    // System.out.println("  Picked from all possible options: ");
    // for (int i=0; i<moves.size(); i++) { 
    // System.out.println("  "+moves.get(i)+" ("+values.get(i)+")");
    // }
    // }

    // private void printExplanationRandom(int randomMove) {
    // System.out.println("  Random move is "+randomMove);
    // System.out.println("  Picked from all possible options: ");
    // for (int i=0; i<moves.size(); i++) { 
    // System.out.println("  "+moves.get(i)+" ("+values.get(i)+")");
    // }
    // }

    /**
     * Prints all possible moves managed by this object, including values
     */
    public void printList() {
        System.out.println("MovesList: ");
        for (int i=0; i<values.length; i++) {
            System.out.println("Move "+i+" ("+values[i]+")");
        }
    }

    public String getList() {
        String result = "MovesList: ";
        for (int i=0; i<values.length; i++) {
            result += (i+" ");
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
        values[move] = values[move]+amount;
    } 

    /**
     * Set the q-value for a particular move
     * @param move The move in question.
     * @param newValue The new value to be assigned.
     */
    public void setValue(int move, double newValue)
    {
        values[move] = newValue;
    }

    /**
     * Get the q-value for a particular move
     * @param move The move in question.
     * @return The current q-value.
     */
    public double getValue(int move)
    {
        return values[move];
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
        if (values.length==0) return -9999;
        double max = values[0];
        for (int i=1; i<values.length; i++)
        {
            if (values[i] > max )
            {
                max = values[i];
            }
        } 
        return max;
    }

    /**
     * gets move with highest value
     * @return The int representing the move.
     */
    public int getBestMove() {

        if (values.length==0) return -1; // shouldn't happen

        // in case there are several candidates
        ArrayList<Integer> candidates = new ArrayList<Integer>();

        //pick first move as best, for starters
        int bestMove = 0;
        double max = values[0];
        candidates.add(bestMove);

        //for all other moves, compare values and remember best
        for (int i=1; i<values.length; i++) 
        {
            if (values[i]>max) {
                max = values[i];
                bestMove = i;
                candidates.clear();
                candidates.add(bestMove);
            }
            else if (values[i]==max) {
                candidates.add(i);
            }
        }
        if (makeDeterministic) bestMove = candidates.get(0);
        else bestMove = candidates.get( (int) (Math.random()*candidates.size()) );        
        //output
        // if (verbose) printExplanationBest(bestMove, candidates); 
        return bestMove;
    }

    /**
     * Gets random legal move, including losing ones.
     * @return The int representing the move.
     */
    public int getRandomMove()
    {
        if (values.length==0) return -1; // shouldn't happen
        int move = (int) (Math.random()*values.length);
        // if (verbose) printExplanationRandom(move);
        return move;
    }

    //
    // unused, probably
    //    

    /**
     * @return returns true only when there is a possible move left
     */
    public boolean hasReasonableMovesLeft()
    {
        for (int i=0; i<values.length; i++) {
            if (values[i]>0) return true;
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
        for (int i=0; i<values.length; i++) {
            if (values[i]>0) {
                tempMoves.add(i);   
                tempValues.add(values[i]);
                sumOfValues += values[i];
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
        for (int i=0; i<values.length; i++) {
            if (values[i]>0) tempMoves.add(i);
        }
        // if there are no reasonable moves left, get random move, including losing ones
        if (tempMoves.size()==0) return getRandomMove();
        // otherwise, pick a random one
        int rnd = 0;
        rnd = (int) (Math.random()*tempMoves.size());
        return tempMoves.get( rnd );
    }

}