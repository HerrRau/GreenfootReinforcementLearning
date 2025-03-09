import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class AutoGameKI extends AutoGame //implements Game
{
    // decide on rewarding principle
    // boolean useSimple = false;

    //used to guide car
    GreenfootImage hiddenImage;

    //shortcut for rewards
    private int rewardForward = 1; // 
    private int rewardBack = -1; // wichtig
    private int rewardSame = 0; //
    private int rewardLap = 0; // 
    private int rewardOffTrack = -1; // wichtig 
    //private int rewardFalseStart = -100; // unused

    //optional: information    
    private int respawns;
    private int finishedLaps;
    private boolean lapFinished = false;

    //optional: extra controls
    private boolean stopUponNextColor = false;
    private int stopAtThisManyLaps = 3;
    private boolean stopUponLapFinished = false;

    //optional: handle stops
    private boolean stopUponChange = false;
    private boolean stopAfterReverse = false;
    private boolean stopAfterFalseStart = false;
    private boolean stopUponOffTrack = false;

    //optional: handle respawns
    private boolean respawnAfterLap = true;
    private boolean respawnAfterReverse = true;
    private boolean respawnAfterFalseStart = false; //# ohnehin noch nicht mit return implementiert - gaebe unerwartete Effekte!

    //game controls
    //double lastBrightness = 256; //-> moved to Auto
    protected boolean useAdditionalMovesForSpeed = false;

    //workaround for getState() : remember current player id
    int currentPlayer;

    //moves
    int[] movesRegular= new int [] {0,1,2};
    int[] movesExtended = new int [] {0,1,2,3,4};
    String[] movesNamesExtended = new String[] {"N", "L", "R", ">", "<"};
    String[] movesNamesRegular = new String[] {"N", "L", "R"};
    
    //
    // Constructors

    public void setup() {
        Agent p = new Agent(0.05);
        setPlayers( new Agent []{ p } );   
        chooseBackground(0);
        setActOrder( new Class[]{ AutoElement.class, AnzeigeNextMoves.class }); //#####
        addObject(new AnzeigeNextMoves(), 0,0); // counts next moves
        setAuto (new Auto() );
    }

    
    //
    // Getters & setters & such
    //

    @Override public void chooseBackground(int i) {
        super.chooseBackground(i);
        hiddenImage = new GreenfootImage(getBackgroundString());
    }

    public void setStopAtThisManyLaps(int i) {
        stopAtThisManyLaps = i;
    }

    public void setStopUponLapFinished(boolean b) {
        stopUponLapFinished = b;
    }

    public void setStopUponOffTrack(boolean b) {
        stopUponOffTrack = b;
    }

    public void setRespawnAfterLap(boolean b) {
        respawnAfterLap = b;
    }

    //
    // Getters for game
    //

    @Override public double getInitialValue() {
        return 0;
    }

    @Override public int [] getLegalMoves() {
        if (useAdditionalMovesForSpeed) return movesExtended;
        else return movesRegular;
    }

    @Override public String getNameForMove(int move) { 
        if (useAdditionalMovesForSpeed) return movesNamesExtended[move];
        else return movesNamesRegular[move];
    }

    @Override public String getState() {
        return getAuto(currentPlayer).getState();
    }

    @Override public String getState(int id) {
        return getAuto(id).getState();
    }

    //
    // Helpers

    private int compareColors(Color c0, Color c1) {
        double y0 = getBrightness(c0);
        double y1 = getBrightness(c1);
        if (y0>y1) return 1;
        else if (y0<y1) return -1;
        else return 0;
    }

    private double getBrightness(Color color) {
        return (color.getBlue()+color.getRed()+color.getGreen())/3.0;
    }

    private double berechneEntfernung(int x, int y, int x2, int y2) {
        double ergebnis = Math.sqrt((x-x2)*(x-x2)+(y-y2)*(y-y2));
        return ergebnis;
    }

    private boolean isOffTrack() {
        return isOffTrack(0);
    }

    private boolean isOffTrack(int id) {
        Color c = hiddenImage.getColorAt(getAuto(id).getX(),getAuto(id).getY());
        boolean b = c.equals(getColorGrass());
        if ( b && stopUponOffTrack) Greenfoot.stop(); 
        return (b);
    }

    private void resetScreen() {
        setBackground( new GreenfootImage(getBackgroundString()) );
        getBackground().setColor(Color.WHITE);
    }

    @Override
    public void respawn(int id) {
        super.respawn(id);
        if (id==0) {
            respawns++;
            showText("Respawns: "+respawns, 80, 20);
        }
        getAuto(id).lastBrightness = 256; // re-initialisation
    }

    //
    // save/load

    public void loadNet() { 
    loadNet(0);
    }

    public void loadNet(int id) { 
    String filename = "autoNeuralNetWeights_" + 
    getAuto(id).getType() + "-" +
    ((NeuralAgent) getPlayers(id)).hiddenLayers + "-"+
    ((NeuralAgent) getPlayers(id)).numberOfOutputs;
    loadNet(id, filename+".txt"); 
    }

    private void loadNet(int id, String filename) {
    ((NeuralAgent) getPlayers(id)).load(filename);        
    }

    public void resetNet() {
        resetNet(0);
    }

    public void resetNet(int id) {
        ((NeuralAgent) getPlayers(id)).initialiseNet(3); //3 shouldn't be hard-coded
    }

    //
    // Make actual move

    public void makeMove(int id, int move) {
        if (move==1) getAuto(id).straightLeft();
        else if (move==2) getAuto(id).straightRight();
        else if (move==0) getAuto(id).straight();
        else if (move==3) getAuto(id).accelerate();
        else if (move==4) getAuto(id).decelerate();
    }


    //
    // Rewarding
    //

    // Used for simple reward system (unusable and hence unused here!)
    @Override
    public int getWinner() {
        if (!isOffTrack(0)) return 0;
        else return 1;
    }

    //continuous reward
    public double getRewardForPlayer(int id) {
        // clear screen (at beginning of turn to keep line drawing visible after stop)
        if (lapFinished) { //## muesste umgewandelt werden!!!!! im Moment global fuer "irgendwer lapFinished"
            lapFinished = false;
            resetScreen();
        }

        //
        //is off track
        //

        if (isOffTrack(id)) { 
            this.respawn(id);
            return rewardOffTrack;
        }

        //
        //is still on track
        //

        //check color
        Color c = hiddenImage.getColorAt(getAuto(id).getX(),getAuto(id).getY());

        //calculate brightness (to follow darkness)
        double currentBrightness = getBrightness(c);

        //goes straight to reverse
        if (getAuto(id).lastBrightness==getMaximumBrightness() && (int)currentBrightness==getMinimumBrightness()) {
            if (verbose) System.out.println("False start: Goes straight to reverse.");
            if (stopAfterFalseStart) Greenfoot.stop();
            if (respawnAfterFalseStart) {
                respawn(id);
            }
            //#############################
            //return rewardFalseStart; // was -100
            //return -100;
        }

        // display information
        if (id==0) {
            showText("Current: "+currentBrightness, 500,300);
            showText("LastMax: "+getAuto(id).lastBrightness, 500,320);
        }

        //reaches next/darker stage
        if (currentBrightness<getAuto(id).lastBrightness) {
            if (stopUponNextColor) Greenfoot.stop(); //## nur Auto 0?       
            getAuto(id).lastBrightness = (int) currentBrightness;
            return rewardForward;
        }

        //lap finished
        if (getAuto(id).lastBrightness==getMinimumBrightness() && (int)currentBrightness==getMaximumBrightness()) {
            finishedLaps++; //## nur Auto 0
            if (respawnAfterLap) {
                this.respawn(id);
            }
            else {
                getAuto(id).lastBrightness = 256; //manually, otherwise done in respawn()
            }    
            respawns = 0; //## nur Auto 0?
            lapFinished = true; //## nur Auto 0?
            showText("Laps: "+finishedLaps,80,40);
            if (finishedLaps==stopAtThisManyLaps) Greenfoot.stop();
            if (stopUponLapFinished) Greenfoot.stop();
            return rewardLap;
        }

        // goes back/reaches lighter stage (except lap finished)
        if ((int)currentBrightness>getAuto(id).lastBrightness) {
            if (stopUponNextColor) Greenfoot.stop();                    
            if (verbose) System.out.println("Car went in reverse.");
            if (stopAfterReverse) Greenfoot.stop();
            if (respawnAfterReverse) {
                respawn(id); 
            }
            return rewardBack;
        }

        // remains in same stage
        return rewardSame;
    }

}