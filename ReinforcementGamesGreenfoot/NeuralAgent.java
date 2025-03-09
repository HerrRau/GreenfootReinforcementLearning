import java.util.ArrayList;

public class NeuralAgent extends Agent
{
    // for the net
    int numberOfOutputs;
    NeuronalesNetz net;
    int hiddenLayers = 20;
    double lernrate = 0.3; //for net 0.3
    String regex = ":"; //only used to split state String

    //for learning
    int learningLoops = 100;

    // output
    private boolean outputStuff = true;

    double maxValueNew;
    double[] stateOldAsDouble;
    double [] ergebnisse;
    double oldValue;
    double change;

    //observe changes
    double [] tempErgebnisseAlt;// = new double[3];
    double [] tempErgebnisseNeu;// = new double[3];
    double [] tempErgebnisseZiel;// = new double[3];

    public NeuralAgent(int inputStates, int hiddenLayers, int outputActions, double exploration)
    {
        super.useSimpleMoves = true;
        explorationRate = exploration;
        numberOfOutputs = outputActions;
        this.hiddenLayers = hiddenLayers;
        net = new NeuronalesNetz(inputStates, hiddenLayers, outputActions, lernrate);        
        learningRate = 0.05; //############### necessary for value range in Auto game
        gamma = 0.5;
        tempErgebnisseAlt = new double[outputActions];
        tempErgebnisseZiel = new double[outputActions];
        setzeAktivierungsfunktion(0); // Standard: Sigmoid
        initialiseNet(3);
        
        outputStuff = false;
    }

    void setzeEinstellungen(int aktivierung, int initialisierung, double lernrateNetz, double learningRate, double gamma) {
        setGamma(gamma);
        setLearningRate(learningRate);
        setzeAktivierungsfunktion(aktivierung);
        net.initialisiere(initialisierung);
        setzeLernrateNetz(lernrateNetz);
    }

    void setzeAktivierungsfunktion(int i) {
        net.setzeAktivierungsfunktion(i);
    }

    public void setzeLernrateNetz(double d) {
        net.setzeLernrate(d);
    }

    @Override public void update(String stateOld, String stateNew, double reward, int move) {

        // get highest value for new state
        maxValueNew = getMaximum(net.werteAus(stringStateToDoubleArray(stateNew)));

        //prepare variables
        stateOldAsDouble = stringStateToDoubleArray(stateOld);
        ergebnisse = net.werteAus(stateOldAsDouble);
        oldValue = ergebnisse[move];

        //calculate change
        change = learningRate * (gamma * maxValueNew - oldValue + reward);

        //temp als Kopie von zielvektor
        if (outputStuff) {
            for (int i=0; i<ergebnisse.length; i++) {
                tempErgebnisseAlt[i]=ergebnisse[i];
                tempErgebnisseZiel[i]=ergebnisse[i];                
            }
            tempErgebnisseZiel[move] = ergebnisse[move] + change;
        }
        //apply change
        ergebnisse[move] = ergebnisse[move] + change;

        // train net
        for (int i=0; i<learningLoops; i++) {
            net.trainiere(stateOldAsDouble, ergebnisse);
        }

        //output temp
        if (outputStuff) {
            System.out.println("-------------");
            System.out.println("Neural Agent: was in state: "+stateOld+", is in state "+stateNew);
            tempErgebnisseNeu = net.werteAus(stateOldAsDouble);
            System.out.println("move "+move+", value changes by "+change);
            System.out.println(learningRate+" * ("+gamma +" * "+maxValueNew+" - "+oldValue + " + "+ reward+")");
            for (int i=0; i<3; i++) {
                if (tempErgebnisseAlt[i]!=tempErgebnisseNeu[i]) {
                    System.out.print("!!! ");
                }
                System.out.println("move "+i+": was "+tempErgebnisseAlt[i]+", is "+tempErgebnisseNeu[i]+", should be "+tempErgebnisseZiel[i]);
            }
        }
    }

    @Override public void setVerbose(boolean b) {
        verbose = false;
        outputStuff = b;
    }

    private double getMaximum(double[] values) {
        double max = values[0];
        for (int i=1;i<values.length; i++) {
            if (values[i]>max) max = values[i];
        }
        return max;
    }

    private double [] stringStateToDoubleArray(String s) {
        String [] temp = s.split(regex);
        double [] result = new double[temp.length];
        try{
            for (int i=0; i<temp.length; i++) {
                result[i] = Double.parseDouble(temp[i]);
            }
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }        
        return result;
    }

    private double [] getBest(NeuronalesNetz n, double [] input) {
        double [] result = n.werteAus(input);
        int favourite = 0;
        double max = result[0];
        for (int i=1; i<result.length; i++) {
            if (result[i]>max) {
                favourite = i;
                max = result[i];
            }
        }
        return new double[]{ favourite, max };
    }

    @Override public Moves getMoves(String state) {
        double [] output = net.werteAus(stringStateToDoubleArray(state));
        //Moves m = new Moves ( new int[] { 0, 1, 2}, output);
        Moves m = new MovesArraySimple ( output);
        return m;
    }

    @Override public void play() 
    {
        // get moves for current state
        currentState = game.getState(playerNumber);
        int [] legalMoves = game.getLegalMoves();

        //output
        if (verbose) printInfoBeforeMove(currentState, legalMoves);

        //decide on the move
        if (Math.random()<explorationRate) {
            move = (int) (Math.random()*numberOfOutputs); 
        }
        else {
            move = (int) (getBest(net, stringStateToDoubleArray(currentState))[0]);                   
        }

        // output
        if (verbose) printInfoMove(move);

        //make the move
        game.makeMove(playerNumber, move);

        //output
        if (verbose) printInfoAfterMove();
    }

    // 

    // speichert nicht: (Biases), Aktivierungsfunktionen, Lernrate, Smooth/nicht
    public void save(String filename) {
        FileManager.speichereGewichte(filename, net.gibAnzahlEingang(), net.gibAnzahlHidden(), net.gibAnzahlAusgang(), net.gibGewichte());
    } 

    public void load(String filename) {
        net.setzeGewichte( FileManager.ladeGewichte(filename, net.gibAnzahlEingang(), net.gibAnzahlHidden(), net.gibAnzahlAusgang()) );
    }

    public void initialiseNet(int i) {
        net.initialisiere(i);
    }


}