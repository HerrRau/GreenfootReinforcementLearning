import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class SnakeGameKI extends SnakeGame
{
    private int [] moves = { 0, 1, 2, 3};                 // Reihenfolge egal...
    private String[] movesNames = { "N", "O", "S", "W" }; // ...ist aber Index fuer hier
    private boolean restartAllSnakesUponWin;
    private int stateType;
    private int [] countingWins;
    protected int [] countingLosses;
    protected int countingRounds;
    private boolean useTournamentMode = true;
    private int tournamentActualRounds;
    int tournamentTrainingEndsAfter = 100;
    int tournamentPrintEveryNCountingRounds = 10;
    int tournamentResetEveryNcountingRounds = 100;

    public SnakeGameKI() {
        super(); //#calls setup
        countingWins = new int[getSnakes().length];
        countingLosses = new int[getSnakes().length];
        setActOrder( SnakeHead.class, SnakeBodyPart.class, AnzeigeNextMoves.class );
        setPaintOrder( SnakeHead.class, SnakeBodyPart.class, AnzeigeNextMoves.class); //# for Karol
    }

    /**
     * Hier koennen Spielparameter festgelegt werden, insbesondere die 
     * teilnehmenden Schlangen (KI oder nicht) und die teilnehmenden Player (Agenten).
     * Außerdem muss die ActOrder der Greenfoot-Objekte festgelegt werden, so dass 
     * Objekte der Klasse AnzeigeNextMoves *nach* allen Schlangen aufgerufen werden,
     * damit showNextMoves moeglich ist.
     * Wird vom Konstruktor der Oberklasse aufgerufen.
     */
    public void setup() {
        setUpdateOnGameEndOnly();
        Agent a = new Agent(0.0);    
        Agent b = new Agent(0.0);    
        setPlayers(  a, b);
        setSnakes ( new SnakeKI(), new SnakeKI() );
    }

    /**
     * Entscheidet, ob bei der Niederlage einer Schlange alle anderen Schlangen vorne beginnen sollen.
     * Wenn nein, fuehrt das zu laengeren Lern- und Spielzeiten fuer alle anderen Schlangen, dafuer 
     * muessen die neueren Schlangen bereits gegen laengere und etabliertere Schlangen spielen, was 
     * den Wettbewerb  verzerren kann.
     * @param b Der neue Wert 
     */    
    protected void setRestartAllSnakesUponWin(boolean b) {
        restartAllSnakesUponWin = b;
    }

    /**
     * Entscheidet, was geschieht, wenn eine Schlange meldet, dass sie verloren hat: es starten entweder
     * alle Schlangen neu, oder nur die Verliererschlange. Alle neu startenden Schlangen werden
     * respawned. Ihre Koerperteile verschwinden entweder sofort oder weiterhin nach und nach.
     * @param The snake in question.
     */
    @Override
    public void verliereSpiel(SnakeHead s) {
        countingRounds++;
        countingLosses[s.gibNummer()]++;
        if (restartAllSnakesUponWin) {
            removeObjects( getObjects(SnakeBodyPart.class));
            for (SnakeHead sh : getSnakes()) {
                respawn(sh);
            }
        } else {
            if (removeDeadBoyPartsInstantly) removeBodyParts(s.gibNummer());
            respawn(s);
        }
        if (useTournamentMode) {
            tournamentActualRounds++;

            //#print wins/losses/ratio every n rounds
            boolean countingCountDuringTraining = true;
            if (countingCountDuringTraining && countingRounds>0 && countingRounds%tournamentPrintEveryNCountingRounds==0) {
                for (SnakeHead sh : getSnakes() ) {
                    System.out.println(sh.gibNummer()+" "+sh.gibName()+": "+countingLosses[sh.gibNummer()] +" verloren ("+(int)Math.floor( 100 * countingLosses[sh.gibNummer()] / countingRounds)+"%)");
                }
                System.out.print(countingRounds + " Runden. (Insgesamt bisher: "+tournamentActualRounds+")");
                if (tournamentActualRounds<=tournamentTrainingEndsAfter) System.out.print(" - noch im Trainingsmodus");
                System.out.println();
                System.out.println();
            }

            //#reset wins/losses every n rounds (after trainining period)
            if (tournamentActualRounds>tournamentTrainingEndsAfter && countingRounds%tournamentResetEveryNcountingRounds==0) {
                countingRounds = 0;
                for (SnakeHead sh : getSnakes() ) {
                    countingLosses[sh.gibNummer()] = 0;
                }
            }

            //#reset and end training period after n rounds
            if (countingRounds == tournamentTrainingEndsAfter && tournamentActualRounds>tournamentTrainingEndsAfter ) {
                countingRounds = 0;
                for (SnakeHead sh : getSnakes() ) {
                    countingLosses[sh.gibNummer()] = 0;
                }
                countingLosses[s.gibNummer()] = 1;
            }    
        }
    }

    /**
     * Die Schlange wird auf Laenge 10 gesetzt und startet an einem neuen zufaelligen Ort.
     * @param s The snake in question. 
     */
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
    
    //#
    public void setName(int id, String name) {
        getSnakes()[id].setzeName(name);
    }

    /**
     * Speichert das Neuronale Netz des gewaehlten Spielers, sofern der ein NeuralAgent ist.
     * 
     */
    public void save(int playerNumber) {
        Agent a = getPlayers()[playerNumber];
        if (a instanceof NeuralAgent) {
            NeuralAgent n = (NeuralAgent)a;
            int numberOfInputs = 4;
            String filename = "snakesNeuralNetWeights_"+n.net.gibAnzahlEingang()+"-"+n.net.gibAnzahlHidden()+"-"+n.net.gibAnzahlAusgang()+".txt";
            n.saveNet(filename);
        }
    }

    public void load(int playerNumber, String filename) {
        Agent a = getPlayers()[playerNumber];
        if (a instanceof NeuralAgent) {
            NeuralAgent n = (NeuralAgent)a;
            n.loadNet(filename);
        }
    }

    //##
    //## from here on, overrides

    //
    // Moves

    @Override
    public int [] getLegalMoves() { 
        return moves; 
    }

    @Override 
    public String getNameForMove(int move) {
        return movesNames[move];
    }

    @Override
    public void makeMove(int player, int move) {
        if      (move == 0 ) getSnakes()[player].blickeNachNorden();        
        else if (move == 1)  getSnakes()[player].blickeNachOsten();
        else if (move == 2)  getSnakes()[player].blickeNachSueden();
        else if (move == 3)  getSnakes()[player].blickeNachWesten();
        else { }
        getSnakes()[player].macheSchritt();
    }    
    //
    // States

    @Override
    public String getState() {
        return getState(0);
    }

    /**
     * Selects which method is used to get the game state for the player.
     * There are various options, e.g. 0 means to check whether N, E, S, W is free
     * @param selector Sets the type of method of getting the state 
     */
    protected final void setStateType(int selector) {
        stateType = selector;
    }

    @Override
    public String getState(int playerID) {
        if (stateType==0) return getStateSimple(playerID);
        else if (stateType==1) return getStateSimplePlusCoordinates(playerID);
        else if (stateType==2) return getStateWithDistancesMax(playerID);
        else if (stateType==4) return getStateWithDistances(playerID);
        else if (stateType==3) return getStateSurroundings(playerID);
        else return getStateSimple(playerID);
    }

    private String getStateSimple(int playerID) {
        String s = "";
        // s = s +((getSnakes()[playerID].getRotation()/90+5)%4)+":";
        return s+
        (getSnakes()[playerID].istFreiNorden() ? 1:0)+":"+
        (getSnakes()[playerID].istFreiOsten() ? 1:0)+":"+
        (getSnakes()[playerID].istFreiSueden() ? 1:0)+":"+
        (getSnakes()[playerID].istFreiWesten() ? 1:0);
    }

    private String getStateSimplePlusCoordinates(int playerID) {
        return getStateSimple(playerID) + ":" + getSnakes()[playerID].getX()+ ":" + getSnakes()[playerID].getY();
    } 

    private String getStateWithDistances(int playerID) {
        String s = "";
        // s = s +((getSnakes()[playerID].getRotation()/90+5)%4)+":";
        return s+
        getSnakes()[playerID].berechnePlatzNorden()+":"+
        getSnakes()[playerID].berechnePlatzOsten()+":"+
        getSnakes()[playerID].berechnePlatzSueden()+":"+
        getSnakes()[playerID].berechnePlatzWesten();
    }

    private String getStateWithDistancesMax(int playerID) {
        int maxDist = 2;
        String s = "";
        // s = s + ((getSnakes()[playerID].getRotation()/90+5)%4)+":";
        return s+
        Math.min(getSnakes()[playerID].berechnePlatzNorden(), maxDist)+":"+
        Math.min(getSnakes()[playerID].berechnePlatzOsten(),maxDist)+":"+
        Math.min(getSnakes()[playerID].berechnePlatzSueden(), maxDist)+":"+
        Math.min(getSnakes()[playerID].berechnePlatzWesten(), maxDist);
    }

    private String getStateSurroundings(int playerID) {
        String erg = "";
        // erg = erg + (getSnakes()[playerID].getRotation()/90+5)%4 + ":";
        int radius = 3;
        int x = getSnakes()[playerID].getX(); //###
        int y = getSnakes()[playerID].getY(); //###
        for (int dx = 0; dx<radius; dx++) {
            for (int dy = 0; dy<radius; dy++) {
                // System.out.print((x - radius/2 + dx)+","+(y - radius/2 + dy)+": ");
                if (getSnakes()[playerID].istFrei(x - radius/2 + dx, y - radius/2 + dy)) {
                    erg = erg+"1:";
                    // System.out.println(1);
                }
                else {
                    erg = erg + "0:";   
                    // System.out.println(0);
                }
            }
        }
        // System.out.println();
        return erg.substring(0, erg.length()-1);
    }
    //
    // Rewards

    @Override
    public double getRewardWin() {
        return 1;
    }

    @Override
    public double getRewardLose() {
        return -100; // war: 10
    }

    //## -1 wenn Spiel noch laeuft, 0 wenn Spieler 1 verloren hat, 1 wenn Spieler 0 verloren hat

    /**
     * Wird nach jedem Zug jedes Teilnehmenden aufgerufen, also oft mehrfach!
     */
    @Override
    public int getWinner() {
        // bei 1 Schlange: als gewonnen zaehlt, wenn man nicht verloren hat
        if (getSnakes().length==1) {
            if (getSnakes()[0].gibVerloren()) {
                getSnakes() [0].setzeVerloren(false);
                return 1;
            }
            return 0;
        }
        //## bei 3+ Schlangen: System fkt nur kontinuierlicher Bewertung???
        if (getSnakes().length>2) {
            for (int i=0; i<getSnakes().length; i++) {
                if (getSnakes()[i].gibVerloren()) {
                    //### only happens when last snake looses
                    getSnakes() [i].setzeVerloren(false);
                    // respawn(getSnakes() [i]); 
                    return -1;
                }
            }
        }
        // bei 2 Schlangen: als gewonnen zaehlt, wenn die andere verloren hat        
        else {
            for (int i=0; i<getSnakes().length; i++) {
                if (getSnakes()[i].gibVerloren()) {        
                    getSnakes()[i].setzeVerloren(false);
                    return (i+1)%2;
                }
            }
        }
        return -1;
    }

    @Override
    public double getRewardForPlayer(int id) {
        if (getSnakes()[id].gibVerloren()) {
            getSnakes() [id].setzeVerloren(false);
            return -10 * 10;
        }
        else {
            return 10;
        }
    }

}