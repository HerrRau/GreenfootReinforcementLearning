import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class SnakeScenarios extends SnakeGameKI
{

    public void setup() {
        //# 0: 1 Agent, Typ 0
        //# 1: 1 Agent vs 1 AutoMax, Typ 0
        //# 2: 1 NeuralAgent, Typ 0
        //# 3: 2 Neural Agent, Typ 0
        //# 4: 2 Neural Agent, 1 AutoMax, Typ 0

        //# 5: 1 NeuralAgent, 1 Agent, 1 AutoMax, Typ 0
        //# 6: 1 NeuralAgent, 1 Agent, 1 AutoMax, Typ 2
        //# 7: 1 NeuralAgent, 1 Agent, 1 AutoMax, Typ 3

        //# 8: 4 AutoMax, 
        //# 9: 1 Q-Net v 1 AutoMax
        //# 10: 1 Linki v AutoMax
        //# 11: 1 Q-Net mit State+Coordinates

        //# 12: 1 Neural Agent, State 4
        //# 13: 2 NeuralAgent, 1 AutoMax, State 4
        //# 14: 1 NeuralAgent, 1 AutoMax, State 4

        //# 15: 1 Neural Agent, Typ 3
        //# 16: 1 Agent, Typ 3.
        //# 17: 4 Agent, Typ 0
        // setupScenario(14);
        setupScenario(0);
    }

    // state 1 wie 2: gute orthogonale Abdeckung, aber keine Spiralen

    public void setupScenario(int option) {
        verbose = true;
        setDisplayStatistics(false);
        setDisplayChanges(false);
        setStateType(0);
        setRestartAllSnakesUponWin(true);
        int inputs = 4; //  
        int outputs = 4; //NOSW
        switch (option)  {
            case 0: {
                    setSnakes( new SnakeKI() );
                    Agent a;
                    a = new Agent(0.0);    
                    setPlayers( new Agent[] { a } );
                    showMessage("1 Agent (Q-Table)");
                    verbose = true;
                    break;                    
                }
            case 1: {
                    setSnakes( new SnakeKI(), new SnakeAutoMax()  );
                    Agent a;
                    a = new Agent(0.0);    
                    setPlayers( new Agent[] { a } );
                    setRestartAllSnakesUponWin(true);   
                    break;
                }
            case 2: {
                    //# 1 NeuralAgent, state 0
                    setSnakes( new SnakeKI() );
                    Agent a;
                    a = new NeuralAgent(inputs,12,inputs,0.0);
                    a.setVerbose(false);
                    setPlayers( a );
                    setStateType(0);
                    break;
                }
            case 3: {
                    //# 2 NeuralAgent, state 0
                    setSnakes( new SnakeKI(), new SnakeKI()  );
                    Agent a;
                    Agent b;
                    a = new NeuralAgent(inputs, 20, outputs, 0.0);   
                    a.setVerbose(false);
                    b = new NeuralAgent(inputs, 20, outputs, 0.0);   
                    b.setVerbose(false);
                    setStateType(0);
                    setPlayers( a, b  );
                    break;
                }
            case 4: {
                    //# 2 NeuralAgent, 1 AutoMax state 0
                    setSnakes( new SnakeKI(), new SnakeKI(), new SnakeAutoMax()  );
                    Agent a;
                    Agent b;
                    int numberOfNeurons = 20;
                    a = new NeuralAgent(inputs, numberOfNeurons, outputs, 0.0);   
                    a.setVerbose(false);
                    b = new NeuralAgent(inputs, numberOfNeurons, outputs, 0.0);   
                    b.setVerbose(false);
                    setStateType(0);
                    setPlayers( a, b  );
                    break;
                }
            case 5: {
                    //# 1 NeuralAgent, 1 Agent, 1 AutoMax
                    Agent a = new NeuralAgent(inputs, 12, outputs, 0.0);
                    a.setVerbose(false);
                    Agent b = new Agent(0.0);
                    setSnakes( new SnakeKI(), new SnakeKI(), new SnakeAutoMax()  );
                    setPlayers( new Agent[] { a, b} );
                    setStateType(0);
                    setRestartAllSnakesUponWin(true);
                    break;
                }
            case 6: {
                    //# 1 NeuralAgent, 1 Agent, 1 AutoMax - type 2
                    Agent a = new NeuralAgent(inputs, 12, outputs, 0.0);
                    a.setVerbose(false);
                    Agent b = new Agent(0.0);
                    setSnakes( new SnakeKI(), new SnakeKI(), new SnakeAutoMax()  );
                    setPlayers( new Agent[] { a, b} );
                    setStateType(2);
                    setRestartAllSnakesUponWin(true);
                    break;
                }
            case 7: {
                    //# 1 NeuralAgent, 1 Agent, 1 AutoMax - type 2
                    inputs = 3*3;
                    Agent a = new NeuralAgent(inputs, 90, outputs, 0.0);
                    a.setVerbose(false);
                    Agent b = new Agent(0.0);
                    setSnakes( new SnakeKI(), new SnakeKI(), new SnakeAutoMax()  );
                    setPlayers( new Agent[] { a, b} );
                    setStateType(3);
                    setRestartAllSnakesUponWin(true);
                    break;
                }
            case 8: {
                    //# 3 AutoMax
                    setSnakes( new SnakeAutoMax(), new SnakeAutoMax(), new SnakeAutoMax()  );
                    break;
                }
            case 9: {
                    //# Neural Snake v. AutoMax, restarting all, type 0 
                    setSnakes( new SnakeKI(), new SnakeAutoMax()  );
                    Agent a = new NeuralAgent(inputs, 40, outputs, 0.0);
                    a.setVerbose(false);
                    setPlayers( new Agent[] { a } );
                    setRestartAllSnakesUponWin(false); // takes longer to learn
                    setStateType(0);
                    break;
                }
            case 10: {
                    setSnakes( new SnakeAutoLinki() , new SnakeAutoMax() );
                    break;
                }
            case 11: {
                    //# test state type 1 with single neural snake > DOESNT WORK
                    setSnakes( new SnakeKI() );
                    Agent a = new NeuralAgent(inputs+2, 12, outputs, 0.0);
                    a.setVerbose(false);
                    setPlayers( new Agent[] { a } );
                    setRestartAllSnakesUponWin(true);
                    setStateType(1); //#######################################################################################
                    break;
                }
            case 12: {
                    //# 1 NeuralAgent with state 4
                    setSnakes( new SnakeKI() );
                    Agent a = new NeuralAgent(inputs, 10, outputs, 0.0);   
                    a.setVerbose(false);
                    setPlayers( new Agent[] { a } );
                    setRestartAllSnakesUponWin(true);
                    setStateType(4);
                    setDisplayChanges(true);
                    break;
                }
            case 13: {
                    //# 2 NeuralAgent, 1 AutoMax with experimental distance state!
                    Agent a = new NeuralAgent(inputs, 10, outputs, 0.0);
                    a.setVerbose(false);
                    Agent b = new NeuralAgent(inputs, 10, outputs, 0.0);
                    b.setVerbose(false);
                    setSnakes( new SnakeKI(), new SnakeKI(), new SnakeAutoMax()  );
                    setPlayers( new Agent[] { a, b} );
                    setStateType(4);
                    setRestartAllSnakesUponWin(true);
                    break;
                }
            case 14: {
                    //# 1 NeuralAgent, 1 AutoMax with state 4
                    Agent a = new NeuralAgent(inputs, 10, outputs, 0.0);
                    a.setVerbose(false);
                    setSnakes( new SnakeKI(), new SnakeAutoMax() );
                    setPlayers( a );
                    setStateType(4);
                    setRestartAllSnakesUponWin(true);
                    setName(0, "Neural");
                    break;
                }
            case 15: {
                    //# 1 NeuralAgent with state 3
                    setSnakes( new SnakeKI() );
                    int radius = 3;
                    Agent a = new NeuralAgent(radius*radius, 90, outputs, 0.0);   //# adjust
                    a.setVerbose(false);
                    setPlayers( new Agent[] { a } );
                    setRestartAllSnakesUponWin(true);
                    setStateType(3);
                    setDisplayChanges(true);
                    break;
                }
            case 16: {
                    //# 1 NeuralAgent with state 2
                    setSnakes( new SnakeKI() );
                    Agent a = new Agent(0.0);   
                    a.setVerbose(false);
                    setPlayers( new Agent[] { a } );
                    setRestartAllSnakesUponWin(true);
                    setStateType(2);
                    setDisplayChanges(true);
                    break;
                }
            case 17: {
                    setSnakes( new SnakeKI(), new SnakeKI() , new SnakeKI() );
                    // setSnakes( new SnakeKI() );
                    Agent n = new NeuralAgent(4,10,4,0.0);   
                    n.setVerbose(false);
                    Agent a = new Agent(0.0);   
                    a.setVerbose(false);
                    Agent b = new Agent(0.0);   
                    b.setVerbose(false);
                    Agent c = new Agent(0.0);   
                    c.setVerbose(false);
                    setPlayers( a, b, c);
                    setRestartAllSnakesUponWin(true);
                    setDisplayChanges(true);
                    break;
                }
        }

    }

}
