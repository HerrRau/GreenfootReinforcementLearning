import greenfoot.*;

public class BreakoutGameKI extends BreakoutGame //implements Game 
{
    // game
    private int repetitionsPerMovement = 5;

    // moves
    private int [] moves = { 1, 2, 0}; //## Reihenfolge egal, wird aber als Index fuer die Namen verwendet
    private String[] movesNames = { "N", "L", "R"}; //## *nicht* die Reihenfolge von oben, sondern Index

    // state
    protected int stateType = 0;

    // reward systemaw
    protected int rewardType; 
    // 0 Original
    // 1 Schwierig
    // 2 simplified original
    // 3 very simplified original
    // 4 modified
    // 5 minimiereX

    @Override    
    public void setup() {
        setActOrder( BreakoutElement.class, AnzeigeNextMoves.class ); //#####
        schlaeger = new SchlaegerKI();
        addObject(schlaeger, 360/2, 480-20);   
        setPlayers( new Agent(0.0)  );    
        verbose = true;
    }

    @Override
    public void erhoeheLevel() {
    }

    //
    // Getters and setters

    @Override 
    public int [] getLegalMoves() { return moves; }

    @Override
    public String getNameForMove(int move) { return movesNames[move]; }

    @Override
    public double getInitialValue() { return 1; }

    protected void setRewardType(int i) { rewardType = i; }

    protected void setStateType(int i) { 
        stateType = 0; 
    }
    //
    // State

    @Override
    public String getState() { return getState(0); }

    @Override
    public String getState(int irrelevantPlayerID) {
        if (stateType==1)  return getStateCompleteState();
        else return getStateXDifferenceOnly();
    }

    private String getStateCompleteState() {
        int schlaegerX = schlaeger.getX();
        int kugelX = kugel.getX();
        int kugelY = kugel.getY();
        int kugelRotation = kugel.getRotation();
        return kugelX/20+":"+kugelY/40+":"+ kugelRotation/20+":"+schlaegerX/20;
    }

    protected String getStateXDifferenceOnly() {
        int schlaegerX = schlaeger.getX();
        int kugelX = kugel.getX();
        return ""+(schlaegerX - kugelX)/10;
    }

    // 
    // Helpers

    private double berechneEntfernung(int x, int y, int x2, int y2) {
        double ergebnis = Math.sqrt((x-x2)*(x-x2)+(y-y2)*(y-y2));
        return ergebnis;
    }

    //
    // Making your move

    @Override
    public void makeMove(int player, int move) {
        if      (move == 1) for (int i=0; i<repetitionsPerMovement; i++) schlaeger.geheNachLinks();        
        else if (move == 2) for (int i=0; i<repetitionsPerMovement; i++) schlaeger.geheNachRechts();
        else { }
    }

    //
    // Rewards

    // (used for simple reward system only)
    @Override
    public int getWinner() {
        if (kugel.beruehrtSchlaeger()) {
            return 0; // Spieler 0 (der einzige) ist Gewinner
        }
        else if (istImAus()) {
            kugel.respawn();
            return 1; //Spieler 0 hat verloren
        }
        return -1; //Spiel laeuft noch
    }

    @Override
    public double getRewardForPlayer(int id) {
        switch(rewardType) {
            case 0: return belohnungOriginal();
            case 1: return belohnungSchwierig();
            case 2: return belohnungSimplifiedOriginal();
            case 3: return belohnungVerySimplifiedOriginal();
            case 4: return belohnungModified();
            case 5: return belohnungMinimizeX();
            default: return belohnungOriginal();
        }
    }

    private double belohnungOriginal() {
        if (kugel.beruehrtSchlaeger()) return 2;
        else if (kugel.getY()>219) {
            return -5 * berechneEntfernung(kugel.getX(), kugel.getY(), schlaeger.getX(), schlaeger.getY());
        }
        else return 0;
    }

    //unused
    private double belohnung0() {
        if (kugel.beruehrtSchlaeger()) return 2;
        else if (kugel.getY()>getHeight()-2) return -5 * berechneEntfernung(kugel.getX(), kugel.getY(), schlaeger.getX(), schlaeger.getY());
        else return 0;
    }

    //unused
    private double belohnung1() {
        if (kugel.beruehrtSchlaeger()) return 2;
        else if (kugel.getY()>getHeight()/2-2) return -5 * berechneEntfernung(kugel.getX(), kugel.getY(), schlaeger.getX(), schlaeger.getY());
        else return 0;
    }

    private double belohnungSimplifiedOriginal() {
        if (kugel.beruehrtSchlaeger()) return 2;
        else return -5 * berechneEntfernung(kugel.getX(), kugel.getY(), schlaeger.getX(), schlaeger.getY());
    }

    private double belohnungVerySimplifiedOriginal() {
        if (kugel.beruehrtSchlaeger()) return 2;
        else return -1 * berechneEntfernung(kugel.getX(), kugel.getY(), schlaeger.getX(), schlaeger.getY());
    }

    private double belohnungModified() {
        if (kugel.beruehrtSchlaeger()) return 2;
        else return 1.0 * ( 
                600 - berechneEntfernung(kugel.getX(), kugel.getY(), schlaeger.getX(), schlaeger.getY())
            ); //## why 600 magic number?
    }

    private double belohnungMinimizeX() {
        if (kugel.beruehrtSchlaeger()) return 2;
        else return 0.1 *  - Math.abs ( kugel.getX() - schlaeger.getX() );
    }

    private double belohnungSchwierig() {
        if (kugel.beruehrtSchlaeger()) {
            return 1;
        }
        else if (istImAus()) {
            return -1;
        }
        else return 0;
    }

}
