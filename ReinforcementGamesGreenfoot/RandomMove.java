
public class RandomMove extends Agent
{
    @Override
    protected int useStrategy(Moves m) {
        int i = m.getRandomMove();
        return i;
    }


}
