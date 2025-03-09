
public abstract class Moves
{
    static boolean verbose = false;

    abstract int getBestMove();
    abstract int getRandomWeightedMove();
    abstract int getRandomMove();
    abstract void setVerbose(boolean b);
    abstract void printList();
    abstract String getList();
    abstract String getMovesAndValues();
    abstract void increaseValue(int move, double amount);
    abstract void setValue(int move, double newValue);
    abstract double getValue(int move);
    abstract double getHighestValue();
}
