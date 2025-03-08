import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public abstract class AbstractGameWorld extends World implements Game
{
    //## required
    protected PlayerManager pm;
    protected boolean verbose;
    private boolean stopUpdating = false;

    //## optional
    private int wins;                                   // optional: bookkeeping
    private int losses;                                 // optional: bookkeeping
    private int breakPeriodicallyAfterSoManyWins = 0;   // optional: break at certain point
    private boolean hasBreaked = true;                  // optional: break at certain point
    private boolean stopUponChange = false;             // optional: stop when sth changes, rarely useful
    private int observedMove;                           // optional: use observation of changes
    private double observedValue;                       // optional: use observation of changes   

    private boolean displayChanges = true;              // optional: display changes
    private boolean displayNextMoves = true;            // optional: display next move
    private boolean displayStatistics = true;           // optional: display wins/losses/number of known states

    //
    // Constructors
    //

    public AbstractGameWorld() {
        this(600,400,1);
    }

    public AbstractGameWorld(int x, int y, int pixel) {
        super(x,y,pixel);
        pm = new PlayerManager();
        setGame(this);
        addObject(new AnzeigeNextMoves(), 0,0); // counts next moves
        verbose = false;
    }


    //
    // Getters & setters & such

    public final Agent [] getPlayers() { return pm.getPlayers(); }

    public final Agent getPlayers(int id) { return pm.getPlayers()[id]; }

    public final Game getGame() { return pm.getGame(); }

    public final void setGame(Game g) { pm.setGame(g); }

    public final void setExplorationRate(double e) { pm.setExplorationRate(e); }

    public void setBreakPeriodically(int i) { breakPeriodicallyAfterSoManyWins = i; }

    public final void setDisplayChanges(boolean b) { displayChanges = b; }

    public final void setDisplayNextMoves(boolean b) { displayNextMoves = b; }

    public final void setDisplayStatistics(boolean b) { displayStatistics = b; }

    public final void setStopUpdating(boolean b) { stopUpdating = b; }

    public final void setPlayers(Agent... p) { pm.setPlayers(p); }

    public void setEverybodyVerbose(boolean b) {
        verbose = b;
        pm.setVerbose(b); // which includes all the Agents!
    }

    //
    // Helper

    private String getPossibleMovesAsString() {
        String erg = "";
        for (int i : getLegalMoves()) erg = erg+",";
        return erg.substring(0, erg.length()-1);
    }

    private int getMax(double [] values) {
        int index = 0;
        for (int i=1; i<values.length; i++) {
            if (values[i]>values[index]) {
                index = i;
            }
        }
        return index;
    }

    //
    // display message

    protected void showMessage(String s) {
        showText(s, getWidth()/2, (int) (getHeight()*0.9)); 
    }

    //
    // Next moves

    /**
     * Must be called right *before* the Agent takes its turn.
     * So: Can be called in World.act() if the world is the only element responsible for state change
     * Otherwise, must be called at the end of the last Actor's act() call
     * just before it is the Player's turn to act again (via the continueIteration call)
     * Displays the state of the world just before the Player's turn, after the world has changed.
     * Is purely informational.
     */
    public void showNextMoves() {
        showNextMoves(0); 
    }

    public void showNextMoves(int x, int y) {
        showNextMoves(0, x, y); 
    }

    public void showNextMoves(int id) {
        showNextMoves(id, getWidth()/2, getHeight()/2); 
    }

    public void showNextMoves(int id, int x, int y) {
        if (pm.getPlayers()==null) { return; }     
        if (!displayNextMoves) return;
        showText(getNextMoves(id), x, y); 
    }

    //# must be called *after* moving
    private String getNextMoves(int id) {
        String state = getState(id); // state of game
        Moves m = getPlayers(id).getMoves(state);
        String result = "Moves for state: "+state;
        if (m!=null) {
            double [] d = new double[getLegalMoves().length];
            for (int i=0; i<d.length; i++) {
                d[i] = m.getValue(i);
                boolean useShort = true;
                if (useShort) result = result + "\n"+getNameForMove(i)+":"+(Math.floor(d[i] * 10000)/10000);                               
                else result = result + "\n"+getNameForMove(i)+":"+d[i];                
            }
            String max = getNameForMove(getMax(d));
            result = result + "\nBest: "+max;
        }        
        else {
            result = "\nNew State!\n\n\n";
        }
        String lastMove = "\nLast move: "+getNameForMove(getPlayers()[0].getMove());
        result = result + lastMove;
        return result; 
    }

    //
    // the loop 

    @Override
    public void act() {
        if (getPlayers()==null) return;         //so you can play regular game without any changes
        for (Agent a : getPlayers()) a.play();  // make move -> state change
        if (!stopUpdating) learnFromResults();  // learn
        checkOptionalElements();                // what it says
    }

    protected final void learnFromResults(){
        if (displayChanges) //optional: observe changes, i.e. remember values
        {
            String stateOld = getPlayers(0).getState();
            if (stateOld != null) {
                observedMove = getPlayers(0).move;
                observedValue = getPlayers(0).getMoves(stateOld).getValue(observedMove); 
            }
        }
        pm.updateAllPlayersSmart();
        //##pm.updateAllPlayersSimple(); //## find a way to offer this as an option?
    }

    protected final void checkOptionalElements() {
        int posx = getWidth()/6;
        int posy = getHeight()/6;

        //# optional: observe changes
        if (displayChanges) {
            double newValue = getPlayers(0).getMoves(getPlayers(0).getState()).getValue(observedMove);
            String message = "No changes.\n ";
            if (newValue!=observedValue) {
                if (stopUponChange) Greenfoot.stop();
                double newValueShort = Math.floor(newValue * 10000)/10000;
                String direction = "-";
                double delta = newValue - observedValue;
                if (delta>0) direction = "↑";
                else if (delta<0) direction = "↓";
                message = "Move "+getNameForMove(observedMove)+"\n"+newValueShort+"\n change "+direction+"\nin "+getPlayers(0).getState();                showText(message, posx, posy);
            }
            else {
                showText("", posy, posx);
            }
        }

        //# optional: pause periodically
        if (breakPeriodicallyAfterSoManyWins>0) {
            if (!hasBreaked && wins%breakPeriodicallyAfterSoManyWins==0) {
                hasBreaked = true;
                Greenfoot.stop();
            } 
            else if (wins%breakPeriodicallyAfterSoManyWins==1) {
                hasBreaked = false;
            }
        }

        //# count wins/losses, bookkeeping only
        int winner = getWinner();
        if (winner==0) wins++;
        else if (winner == 1) losses++;

        //# optional: displaying numbers of wins, losses, states
        if (displayStatistics) {
            String temp = "Wins: "+wins;
            temp = temp + "\nLosses: "+losses;
            double ratio = ((wins*1.0)/losses);
            ratio = Math.floor(ratio*1000)/1000;
            if (losses>0) temp = temp + "\nRtio: " +ratio;
            temp = temp + "\nStates: "+getPlayers(0).getNumberOfKnownStates();
            this.showText(temp, getWidth()-1-posx, getHeight()-1-posy);        

        }

    }

    //##
    //## from here on, overwrite methods

    @Override 
    public void printGame(String state) { }

    @Override 
    public int [] getLegalMoves() { return new int [0]; }

    public String getNameForMove(int move) { return ""+getLegalMoves()[0]; }

    @Override 
    public String getState(int playerID) { return ""; }

    @Override 
    public String getState() { return getState(0); }

    @Override 
    public void makeMove(int player, int optionDecidedUpon) { }

    @Override 
    public double getInitialValue() { return 0; } //relevant only for Q-Table, not Q-Net

    //
    @Override 
    public double getRewardForPlayer(int playerID) {   return 1; }

    //
    @Override
    public int getWinner() { return -1; }

}