
public class GameRunner extends PlayerManager
{
    // boolean useSimple = false;

    //
    // Play rounds
    //

    /**
     * Play a number of rounds of associated game
     * @param n number of rounds to play
     */
    public void playNewRounds(int n){
        maxNumberOfMoves = 0;
        for (int i=0;i<n;i++) {
            playNewRound();
        }
    }

    /**
     * plays one round of the game 
     * game must have been initialised
     */
    private void playNewRound() 
    {        
        //create new game instance 
        try { setGame ( (Game) getGame().getClass().getConstructor(new Class[]{}).newInstance());   } 
        catch (Exception e) { System.out.println(e); }

        //increase the total number of rounds that have been played
        numberOfRounds++;

        //initialise stuff
        for (int i=0; i<players.length; i++) {
            players[i].setGame(getGame());
            players[i].reset();
        }
        activePlayer = 0;
        //winner = -1;
        numberOfMoves = 0;

        // output
        if (verbose) {            
            System.out.println("===================================="); 
            getGame().printGame(getGame().getState());
        }

        // main thing: game loop
        boolean continueGame = true;
        while (continueGame) {
            continueGame = playNextIteration();
        }

        // find out max length of game
        if (maxNumberOfMoves<numberOfMoves) maxNumberOfMoves = numberOfMoves;        

        //output
        if (verbose) {
            System.out.println("-------------------------------------");
            System.out.println("Length of game: "+numberOfMoves);
            System.out.println("Wins for Player 0: "+numberOfWinsForPlayer[0]+", wins for player 1: "+numberOfWinsForPlayer[1]);
            System.out.println("NumberOfRounds: "+numberOfRounds+", numberOfDraws: "+numberOfDraws);
            System.out.println("Max number of moves so far: "+maxNumberOfMoves);
            System.out.println("=====================================");
        }
    }

    protected boolean playNextIteration() {
        // if (useSimple) return this.continueIterationSimple();
        // if (useSimple) return this.continueIterationSimpleAlt();
        // else 
        return  this.continueIterationContinuous();
    }

    /**
     * Active player makes move. 
     * All players are potentially updated.
     * @return true if to continue, false if end of game
     */
    private boolean continueIterationContinuous() {
        //make play
        players[activePlayer].play();
        super.updateAllPlayersSmart();   

        //bookkeeping
        numberOfMoves++;

        //check if there is a winner: 0, 1, or -1 (for no winner yet) 
        int winner = game.getWinner();
        if (winner>=0) {
            numberOfWinsForPlayer[winner]++;
            if (verbose) System.out.println("Winner: "+winner);
            return false;
        }

        //next player
        activePlayer = (activePlayer+1)%players.length;

        // check if there are no more moves
        if (game.getLegalMoves().length==0) {
            if (verbose) System.out.println("No more moves possible!");
            if (game.getWinner()==-1) {
                numberOfDraws++;   
            }                
            return false;
        }

        return true;
    }


}
