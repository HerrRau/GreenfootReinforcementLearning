/**
 * This class connects a Game with a number of players, of class Agent.
 * It keeps track of the number of rounds and wins and draws and so on.
 * It contains the method to update all players, according to the game
 * state (won/lost/continously) and the rewards set in the game, delegating
 * the actual updating to the Agents.
 */

public class PlayerManager
{
    //necessary
    Agent [] players;
    Game game;
    //necessary, practically
    int numberOfMoves = 0;
    int activePlayer = 0;
    //bookkeeping
    int [] numberOfWinsForPlayer;
    int numberOfRounds;
    int maxNumberOfMoves = 0;
    int numberOfDraws;
    //debugging    
    boolean verbose = false;

    //
    // Constructors
    //

    public PlayerManager() {
    }

    //
    // Setters & getters
    //

    public void setPlayers(Agent [] players) {        
        this.players = players;
        this.numberOfWinsForPlayer = new int[players.length];
        for (int i=0; i< players.length; i++) {
            players[i].setPlayerNumber(i);
            if (game!=null) {
                players[i].setGame(game); 
            }
        }

    } 

    public void setPlayers(Agent p0, Agent p1) {
        setPlayers( new Agent[] { p0, p1} );
    }

    public Agent [] getPlayers() {
        return players;
    }

    public Agent getPlayer(int id) {
        //no safety checks
        return players[id];
    }

    public void setGame(Game g) {
        game = g;
        if (players!=null) {
            for (int i=0; i<players.length; i++) {
                if (players[i]!=null) {
                    players[i].setGame(game);   
                    players[i].setPlayerNumber(i);   
                }
            }
        }
    }

    public Game getGame() {
        return game;
    }

    public void setVerbose(boolean b){
        verbose = b;
        if (players==null) return;
        for (Agent a : players) a.setVerbose(b);     
    }

    public void setExplorationRate(double e) {
        if (players==null) return;
        for (Agent a : players) a.setExplorationRate(e);     

    }

    //
    // Printers
    //

    public void printInfo() {
        System.out.println("=====================================");
        System.out.println("NumberOfRounds: "+numberOfRounds+", numberOfDraws: "+numberOfDraws);
        System.out.println("Wins for Player 0: "+numberOfWinsForPlayer[0]+", wins for player 1: "+numberOfWinsForPlayer[1]);
        System.out.println("Max number of moves: "+maxNumberOfMoves);
    }

    //
    // Updating
    //

    public boolean updateAllPlayersSimple() {
        int winner = -1;
        if (((AbstractGameWorld)game).experimental) {
            winner = ((AbstractGameWorld)game).winnerCurrentRound; //#################1
        }
        else {
            winner = game.getWinner();

        }
        
        boolean gameOver = false;       
        //verbose = true;
        for (int playerID=0; playerID<players.length; playerID++) {  
            //game unfinished
            if (winner<0) {                
            }
            //winner
            else if (winner==playerID) {
                if (verbose) System.out.println("!Increased: move "+players[playerID].getMove()+" in state "+players[playerID].getState()+ " for player "+playerID);
                players[playerID].won();   
                numberOfWinsForPlayer[playerID]++;
                gameOver = true;
            }
            //losing players
            else {
                if (verbose) System.out.println("!Decreased: move "+players[playerID].getMove()+" in state "+players[playerID].getState()+ " for player "+playerID);
                players[playerID].lost();   
            }
        }        
        return gameOver;
    }

    public void updateAllPlayersSmart() {
        for (int i=0; i<players.length; i++) {
            int move = players[i].getMove();
            String stateOld = players[i].getState();
            double reward = game.getRewardForPlayer(i);            
            players[i].update(stateOld, game.getState(i), reward, move);
        }
    }
}

