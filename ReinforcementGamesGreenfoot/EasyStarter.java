public class EasyStarter
{

    public static String fetchInput() {
        String input = "";
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(System.in)); 
        try {
            input = br.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        return input;
    }

    public static void playTTTTrained()
    {
        GameRunner gr = new GameRunner();
        Agent trainee = new Agent(0.0);
        //trainee = new RandomMove();
        Agent opponent = new RandomMove();
        opponent = new Agent(0.1);
        gr.setPlayers(opponent, trainee);
        gr.setGame(new TicTacToe() );
        gr.setVerbose(false);
        gr.playNewRounds(100000);
        gr.printInfo();
        opponent = new HumanMove();
        while (true) {   
            System.out.println();
            System.out.println("Continue?");
            if (fetchInput().equals("")) break;
            gr.setPlayers( opponent, trainee);
            gr.setVerbose(true);
            if (false) {
                trainee.printAllMovesLists();  
            } else {
                // trainee.printMovesForState("101202010");
                trainee.printMovesForState("100000000");
            }
            gr.playNewRounds(1);    
        }
    }

    public static void playTTTUntrained()
    {
        GameRunner gr = new GameRunner();
        Agent trainee = new Agent(0.0);
        //trainee = new RandomMove();
        Agent opponent = new RandomMove();
        opponent = new Agent(0.1);
        gr.setPlayers(opponent, trainee);
        // gr.setGame(new TicTacToe() );
        greenfoot.World w = new TTTAdapterKI();
        greenfoot.Greenfoot.setWorld(w);
        Game game = (Game) w;
        gr.setGame(game );
        gr.setVerbose(false);
        gr.printInfo();
        opponent = new HumanMove();
        while (true) {   
            System.out.println();
            System.out.println("Continue?");
            if (fetchInput().equals("")) break;
            gr.setPlayers( opponent, trainee);
            gr.setVerbose(true);
            gr.playNewRounds(1);    
        }
    }


}