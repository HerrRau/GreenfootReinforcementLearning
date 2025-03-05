import greenfoot.*;
public class HumanGreenfootMove extends Agent
{
    @Override
    protected int useStrategy(Moves possibleMoves) {
        String s = possibleMoves.getList();
        return Integer.valueOf( Greenfoot.ask(s) );
    }
}
