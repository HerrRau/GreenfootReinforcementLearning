
public class BestMove extends Agent
{
    public BestMove(int id)
    {
        super(id);
    }
    
    protected int useStrategy(MovesList m) {
        return m.getBestMove();
    }
}
