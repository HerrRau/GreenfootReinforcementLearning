import java.util.ArrayList;

public class Agent 
{
    // necessary for all players
    protected Game game;
    public boolean verbose = false;
    protected int playerNumber;    
    protected String currentState;
    protected int move;

    //necessary for Agents
    public java.util.HashMap<String, Moves> map;
    double learningRate = 0.75;
    double gamma = 0.5; // discount factor; Diskontierungsfaktor
    double explorationRate = 0.05; //0.05
    static boolean useSimpleMoves = true; //set to true in Neural Agent  
    //# use true if moves are of type [0,1,2...n], false if of type [3,13,14...] 

    //
    // Constructors
    //

    public Agent()
    {
        //initializes map which associates a list of moves with the state of play (as String)
        map = new java.util.HashMap<String,Moves>();
    }

    /**
     * @param id The id number associated with this player
     * @param e The exploration rate. 1 means completely random
     */
    public Agent(double e)
    {
        //initializes map which associates a list of moves with the state of play (as String)
        map = new java.util.HashMap<String,Moves>();
        explorationRate = e;
    }
    //
    // Setters & getters & such
    //

    public void setGame(Game game) { 
        this.game = game;
    }

    public void setVerbose(boolean b) {
        verbose = b;
        Moves.verbose = b;
    }

    //should not be called by hand
    public void setPlayerNumber(int i) {
        playerNumber = i;
    }

    public int getMove() {
        return move;
    }

    public String getState() {
        return currentState;
    }

    protected int getNumberOfKnownStates() {
        return map.size();
    }

    public void setExplorationRate(double e) {
        explorationRate = e;
    }

    public void setGamma(double d) {
        gamma = d;
    }

    public Moves getMoves(String state) {
        return map.get(state);
    }

    public void useSimplifiedMoves(boolean b) {
        useSimpleMoves = b;
    }

    public void setLearningRate(double d) {
        learningRate = d;
    }

    private void increaseValueOfMoveForState(String state, int move, double amount) {
        map.get(state).increaseValue(move, amount);
    }

    private void decreaseValueOfMoveForState(String state, int move, double amount) { 
        this.increaseValueOfMoveForState(state, move, -amount);
    }

    //
    //
    //

    //called at beginning of playRounds
    protected void reset() { 
        currentState = null; 
    }

    //
    // Printers
    //

    public void printMovesForState(String state)
    {
        System.out.println("Moves for state: "+state);
        System.out.println("(Player number: "+playerNumber+")");
        Moves movesList = map.get(state);
        if (movesList!=null) {
            movesList.printList();   
        }
        else {
            System.out.println("Unknown state.");
        }
        System.out.println();
    }

    public void printAllMovesLists() {
        System.out.println();
        for (java.util.Map.Entry<String, Moves> entry : map.entrySet()) {
            String state = entry.getKey();
            Moves values = entry.getValue();
            System.out.println("Moves for state: "+state);

            game.printGame(state);

            System.out.println("(Player number: "+playerNumber+")");
            values.printList();
            System.out.println();
        }
        System.out.println();
        System.out.println("Number of known states: "+map.size());
    }

    public void printInfoBeforeMove(String state, int [] legalMoves) 
    {
        System.out.println("====================================");
        System.out.print("Moving: player number "+playerNumber);        
        System.out.println(", in game state: "+state);        
        System.out.print("Legal moves: ");
        for (int i : legalMoves) 
        {
            // if (i<10) System.out.print("0"); // only useful for two-digit moves where the first digit may be 0
            // else System.out.print(i+" ");
            System.out.print(i+" ");
        }                
        //printMovesForState(state);
        System.out.println();
    }

    public void printInfoMove(int move) {
        //System.out.println("------------------------------------");        
        System.out.println("Player "+playerNumber+" moves: *** "+move+" ***");
    } 

    public void printInfoAfterMove()
    {
        System.out.println("------------------------------------");        
        System.out.println("Game state after move is: "+game.getState(0));
        game.printGame(game.getState(0));
        // System.out.println("Game state after move is: "+game.getState());
        // game.printGame(game.getState());
    }

    //
    // Actual play
    //

    /**
     * wird aufgerufen, wenn Player einen Zug machen soll
     * (Player macht Zug beim Spiel)
     */
    public void play() 
    {
        //# get game state (and, for display purposes only, all legal moves)
        currentState = game.getState(playerNumber);
        int [] legalMoves = game.getLegalMoves();

        //# optional print
        if (verbose) printInfoBeforeMove(currentState, legalMoves);

        //# get values for all moves in this state
        Moves m = map.get(currentState);

        //# add new state if there are no values for the current state
        if (m==null) m = addNewState(currentState, legalMoves); // state not yet in list

        //# from all moves, *decide* on one, using the current strategy
        move = useStrategy(m); //use strategy method

        //# optional print
        if (verbose) printInfoMove(move);

        //# actually make the decided-upon move, by telling the game about it
        game.makeMove(playerNumber, move);

        //# optional print
        if (verbose) printInfoAfterMove();
    }

    //
    // Strategy method
    //

    //## standard strategy: use best move, unless exploration makes you pick a random one (will be overwritten by subclasses)
    protected int useStrategy(Moves moves) {
        if (Math.random()<explorationRate) {
            int random = moves.getRandomMove();
            return random;
        }
        int best = moves.getBestMove();
        return best;
    }

    //
    //
    //

    /**
     * Adds a new state with its associated possible moves.
     * @param state The new state to be added.
     * @param legalMoves The possible moves in this state.
     * @return the Moves with its associated values
     */
    private Moves addNewState(String state, int [] legalMoves) {
        //create new list
        ArrayList<Integer> list = new ArrayList<Integer>();
        //fill list with moves for state
        for (int i : legalMoves) {
            list.add(Integer.valueOf(i));
        }       
        //add state-move-combo to map
        if (useSimpleMoves)  {
            map.put(state, new MovesArraySimple(legalMoves.length, game.getInitialValue()));
            //map.put(state, new MovesListSimple(legalMoves.length, game.getInitialValue()));
        }
        else {       
            map.put(state, new MovesList(list, game.getInitialValue()));
        }//continue with moveslist
        Moves m = map.get(state);
        return m;
    }

    //
    // Update methods
    //

    //
    // used for regular updating only

    /**
     * IMPORTANT, double-checked, should work
     */
    public void update(String stateOld, String stateNew, double reward, int move) {

        boolean outputMore = false;
        if (outputMore && verbose) 
        {
            System.out.println("-----------------------");
            System.out.println("Update stateOld: "+stateOld);
            System.out.println("Update stateNew: "+stateNew);
        }

        Moves qOld = map.get(stateOld);
        Moves qNew = map.get(stateNew);

        //safety - should be impossible
        if (qOld == null) {
            //if (verbose) System.out.println("Agent: Didn't find moves for (old) '"+stateOld+"' - possible at beginning");
            if (outputMore && verbose) // && !(this instanceof HumanMove)) 
            { 
                System.out.println("Agent: Didn't find moves for (old) '"+stateOld+"' - possible at beginning");
            }
            return;
        }

        double newValue;

        //safety - can happen if losing move
        if (qNew == null) {
            // ??? 
            if (verbose) // && !(this instanceof HumanMove)) 
            { 
                System.out.println("Agent: Didn't find moves for (new) '"+stateNew+"'");
            }
            //calculate new value
            newValue = qOld.getValue(move) * (1-learningRate) + learningRate*reward;
        } 
        else {
            //calculate reward
            reward = reward + gamma * qNew.getHighestValue();
            //calculate new value
            newValue = qOld.getValue(move) * (1-learningRate) + learningRate*reward;
        }

        //output
        if (verbose) {
            if (qOld.getValue(move) != newValue)   {
                System.out.println("Something has changed for player "+playerNumber+", move "+move+", state "+stateOld);
                System.out.println("(was: "+qOld.getValue(move)+", is: "+newValue+")");
            } 
            else {
                System.out.println("Nothing has changed for player "+playerNumber+", move "+move+", state "+stateOld);                
            }
        } 

        //apply
        qOld.setValue(move, newValue);
    }

    //
    // relevant for naive updating only

    //# called only with naive system
    public void draw() {
        if (verbose) System.out.println("-----------------------------------\nA draw! ");
    }

    //# called only with naive system
    public void won() {
        winOrLose(true);
    }

    //# called only with naive system
    public void lost() {
        winOrLose(false);
    }

    private void winOrLose(boolean b) {
        String outcome = (b) ? "wins" : "loses";
        if (verbose) {
            System.out.println("-----------------------------------");
            System.out.println("Player "+playerNumber+" "+outcome+"! State to change: "+currentState + " (Current state: "+game.getState(playerNumber)+")");
            System.out.print("Winning move: "+move);
        }
        
        if (verbose) System.out.print(" (was: "+map.get(currentState).getValue(move));
        increaseValueOfMoveForState(currentState, move, game.getRewardForPlayer(playerNumber));   
        if (verbose) System.out.println(", is: "+map.get(currentState).getValue(move)+")");

    }

    public void save(String filename) {
        String outputFilePath = filename;
        java.io.File file = new java.io.File(outputFilePath);
        try {
            java.io.BufferedWriter bf = new java.io.BufferedWriter( new java.io.FileWriter(file));
            // iterate map entries 
            for (java.util.Map.Entry<String, Moves> entry : map.entrySet()) { 

                bf.write(entry.getKey() + ";" + ((Moves)entry.getValue()).getMovesAndValues());

                // new line 
                bf.newLine(); 
            } 

            bf.flush(); 
            bf.close();
        } catch(Exception e) {
            System.out.println(e);
        }

    }

    public void load(String filename) {
        System.out.println("Agent: Laden noch nicht wirklich getestet.");
        String outputFilePath = filename;
        java.io.File file = new java.io.File(outputFilePath);
        try {
            map = new java.util.HashMap<String, Moves>();
            java.util.Scanner myReader = new java.util.Scanner(file);
            while(myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String [] entries = data.split(";");
                int [] options = new int[entries.length-1];
                double [] values = new double[entries.length-1];
                for (int i=1; i<entries.length; i++) {
                    String [] item = entries[i].split(":");
                    options[i-1] = Integer.parseInt(item[0]);
                    values[i-1] = Double.parseDouble(item[1]);
                }              
                Moves moves = new MovesList(options, values);
                boolean printOutLines = false;
                if (printOutLines) {
                    System.out.print("Added for "+entries[0]+": ");
                    for (int i=0; i<values.length; i++) {
                        System.out.print(options[i]+":"+values[i]+" ");
                    }
                    System.out.println();
                }
                map.put( entries[0], moves);
            } 
            myReader.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

}
