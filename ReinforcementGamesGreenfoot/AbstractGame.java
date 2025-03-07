public abstract class AbstractGame  implements Game
{
    protected double reward;

    public abstract double getRewardForPlayer(int playerID);

    public double getInitialValue() { 
        return 0; 
    }


}
