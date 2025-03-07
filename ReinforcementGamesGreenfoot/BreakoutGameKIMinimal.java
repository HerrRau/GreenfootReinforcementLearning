import greenfoot.*;

public class BreakoutGameKIMinimal extends BreakoutGame {
    //
    // Uberschreiben von Breakout-spezifischen Methoden, und Hilfsmethoden

    @Override    
    public void setup() {
        setActOrder( BreakoutElement.class, AnzeigeNextMoves.class );
        schlaeger = new SchlaegerKI();
        addObject(schlaeger, 360/2, 480-20);   
        setPlayers( new Agent(0.0) );
        // setUpdateOnGameEndOnly(); //geht schneller, ist aber nicht der Standardfall
    }

    @Override
    public void erhoeheLevel() {
    }

    private double berechneEntfernung(int x, int y, int x2, int y2) {
        double ergebnis = Math.sqrt((x-x2)*(x-x2)+(y-y2)*(y-y2));
        return ergebnis;
    }

    //
    // Uberschreiben von KI-relevanten Methoden

    @Override 
    public int [] getLegalMoves() { return new int [] { 1, 2, 0}; }

    @Override
    public String getNameForMove(int move) { 
        if (move==0) return "N";
        else if (move==1) return "L";
        else if (move==2) return "R"; 
        else return null;
    }

    @Override
    public void makeMove(int player, int move) {
        if      (move == 1) for (int i=0; i<5; i++) schlaeger.geheNachLinks();        
        else if (move == 2) for (int i=0; i<5; i++) schlaeger.geheNachRechts();
        else { }
    }

    @Override
    public double getInitialValue() { return 1; }

    @Override
    public String getState() {  return getState(0); }

    @Override
    public String getState(int irrelevantPlayerID) {
        return "" + (schlaeger.getX() - kugel.getX())/10;
    }

    @Override
    public double getRewardWin() { return 10; }

    @Override
    public double getRewardLose() { return -5; }

    @Override
    public int getWinner() {
        if (kugel.beruehrtSchlaeger()) return 0;
        else if (istImAus()) {
            kugel.respawn();
            return 1;
        }
        return -1;
    }

    @Override
    public double getRewardForPlayer(int id) {
        if (kugel.beruehrtSchlaeger()) return 2;
        else if (kugel.getY()>219) {
            return -5 * berechneEntfernung(kugel.getX(), kugel.getY(), schlaeger.getX(), schlaeger.getY());
        }
        else return 0;
    }

}
