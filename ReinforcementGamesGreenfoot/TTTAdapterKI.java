import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class TTTAdapterKI extends AbstractGameWorld implements Game
{

    Actor [] boardPieces;
    AbstractGame game = new TicTacToe();
    int active = 0;

    public TTTAdapterKI() {
        super(400,600,1);
        boardPieces = new Actor[9];
        // setGame(this);
        
        addObject(new AnzeigeNextMoves(), 0,0);

        setup();
    }
    
    public void setup() {
        setActOrder( new Class[]{ AutoElement.class, AnzeigeNextMoves.class }); //#####
        playTTTUntrained();
    }

    //# delegation to game

    public int getWinner() { return game.getWinner(); }

    public double getRewardForPlayer(int id) { return game.getRewardForPlayer(id);}

    public int[] getLegalMoves() { return game.getLegalMoves(); }

    @Override
    public String getNameForMove(int move) { 
        return ""+move;
    }

    public void makeMove(int player, int move) { game.makeMove(player, move); }

    public String getState(int irrelevantID) { return game.getState(irrelevantID); }

    public String getState() { return game.getState(); }

    //# 

    @Override 
    public void printGame(String state) {
        for (int i=0; i<state.length(); i++) {
            removeObject(boardPieces[i]);
            char c = state.charAt(i);
            if (c=='X') {
                // System.out.println(c);
                boardPieces[i] = new Belohnung2();
            }
            else if (c=='O') {
                // System.out.println(c);
                boardPieces[i] = new Belohnung1();
            }
            else if (c=='-') {
                // System.out.println(c);
                boardPieces[i] = new Pearl();
            }
            addObject(boardPieces[i], i%3 * 400/3+400/6, i/3 * 400/3+400/6);
        }
    }

    protected void playTTTUntrained()
    {
        Agent trainee = new Agent(0.0);
        trainee.useSimplifiedMoves(false);
        Agent opponent = new HumanGreenfootMove(); //waits for input
        opponent.useSimplifiedMoves(false);
        setPlayers( new Agent[] {opponent, trainee} );
        setVerbose(true);
        // while (true) {   
        // System.out.println();
        // System.out.println("Continue?");
        // if (fetchInput().equals("")) break;
        // gr.setPlayers( opponent, trainee);
        // gr.setVerbose(true);
        // gr.playNewRounds(1);    
        // }
    }

    private void warte(int ms) {
        try { Thread.sleep(ms); }
        catch(Exception e) {}
    }

    public boolean continueIteration() {
        warte(2000);
        getPlayers()[active].play(); 
        learnFromResults();
        // boolean useSimple = true;        
        // if (useSimple) super.updateAllPlayersSimple();
        // else super.updateAllPlayersSmart();
        // checkOptionalElements();                  // what it says
        
        active = (active+1)%2; // next player's turn        
        if (getWinner()>=0) return false; //somebody has won
        if (getLegalMoves().length==0) return false; //it's a draw
        return true;
    }

}
