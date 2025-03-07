import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class SnakeGameKIMinimal extends SnakeGame
{

    public void setup() {
        Agent a = new Agent(0.0);    
        setPlayers( a );
        setSnakes( new SnakeKI() );
        setActOrder( SnakeGameElement.class, AnzeigeNextMoves.class );
    }

    @Override public void verliereSpiel(SnakeHead s) {
        removeBodyParts(s.gibNummer());
        respawn(s);
    }

    private void respawn(SnakeHead s) {
        s.entferneText();
        s.laenge=10;
        int dir = Greenfoot.getRandomNumber(4);
        if (dir==0) s.blickeNachNorden();
        if (dir==1) s.blickeNachOsten();
        if (dir==2) s.blickeNachSueden();
        if (dir==3) s.blickeNachWesten();
        s.setLocation(Greenfoot.getRandomNumber(getWidth()), Greenfoot.getRandomNumber(getHeight()));
    }

    @Override public int [] getLegalMoves() { 
        return new int []  { 0, 1, 2, 3};
    }

    @Override public String getNameForMove(int move) { 
        if (move==0) return "N";
        else if (move==1) return "O";
        else if (move==2) return "S"; 
        else if (move==3) return "W"; 
        else return null;
    }

    @Override public void makeMove(int player, int move) {
        if      (move == 0 ) getSnakes()[player].blickeNachNorden();        
        else if (move == 1)  getSnakes()[player].blickeNachOsten();
        else if (move == 2)  getSnakes()[player].blickeNachSueden();
        else if (move == 3)  getSnakes()[player].blickeNachWesten();
        else { }
        getSnakes()[player].macheSchritt();
    }    

    @Override public String getState() {
        return getState(0);
    }

    @Override
    public String getState(int playerID) {
        return 
        (getSnakes()[playerID].istFreiNorden() ? 1:0)+":"+
        (getSnakes()[playerID].istFreiOsten() ? 1:0)+":"+
        (getSnakes()[playerID].istFreiSueden() ? 1:0)+":"+
        (getSnakes()[playerID].istFreiWesten() ? 1:0);
    }

    @Override public int getWinner() {
        if (getSnakes().length==1) {
            if (getSnakes()[0].gibVerloren()) {
                return 1;
            }
            return 0;
        }

        if (getSnakes().length>2) {
            for (int i=0; i<getSnakes().length; i++) {
                if (getSnakes()[i].gibVerloren()) {
                    respawn(getSnakes() [i]);
                    return -1;
                }
            }
        }

        for (int i=0; i<getSnakes().length; i++) {
            if (getSnakes()[i].gibVerloren()) {        
                return (i+1)%2;
            }
        }

        return -1;
    }

    @Override public double getRewardForPlayer(int id) {
        if (getSnakes()[id].gibVerloren()) {
            getSnakes() [id].setzeVerloren(false);
            return -10;
        }
        else {
            return 1;
        }
    }

}