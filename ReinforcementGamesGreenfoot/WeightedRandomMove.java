
public class WeightedRandomMove extends Agent
{
    protected int useStrategy(Moves m) {
        return m.getRandomWeightedMove();
    }
}
