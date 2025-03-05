public class HumanMove extends Agent
{

    @Override
    protected int useStrategy(Moves possibleMoves) {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(System.in)); 
        try {
            move = Integer.parseInt(br.readLine());
        } catch (Exception e) {
            System.out.println(e);
        }
        return move;
    }

}
