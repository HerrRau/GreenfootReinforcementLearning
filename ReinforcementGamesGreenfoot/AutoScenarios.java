import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class AutoScenarios extends AutoGameKI
{
    // 0: position, non-smooth, table, course 0
    // 1: position, non-smooth, table, course 1
    // 2: sensor 5, non-smooth, table, course 0
    // 3: sensor 5, non-smooth, table, course 1
    // 4: sensor 5, non-smooth, 10 Knoten, course 0
    // 5: sensor 5, non-smooth, 10 Knoten, course 1
    // 6: sensor 5, smooth, 10 Knoten, course 0
    // 7: sensor 5, smooth, 10 Knoten, course 1
    // 8: sensor 3, smooth, 12 Knoten, course 1
    // 9: position, non-smooth, 10 Knoten, course 0 experimentell
    // 10: sensor 3, smooth, 4 Knoten, course 1 - laedt automatisch, explorationrate = 0.05
    // 11: sensor 3, smooth, 4 Knoten, course 1 - laedt automatisch, explorationrate = 0.0, , lernrate = 0

    public AutoScenarios()
    {
        int option = 12-1; //8 //10 //12
        option = 4-4;
        setupScenario(option);
    }

    //convenience
    @Override public void chooseBackground(int i) {
        super.chooseBackground(i);
    }

    public void setupScenario(int option) {        
        chooseBackground(0);
        Agent p;
        NeuralAgent n;

        setStopUpdating(false);
        setStopUponLapFinished(false);
        setStopUponOffTrack(false);
        setStopAtThisManyLaps(1000);
        setRespawnAfterLap(false);

        setDisplayChanges(false);
        setDisplayStatistics(false);
        setEverybodyVerbose(false);

        double exploration = 0.05;
        int anzahlSensoren = 6;
        int anzahlKnoten = 4;

        useAdditionalMovesForSpeed = false;
        // setUpdateOnEveryMove();

        switch(option) {
            case 0:
                //non-smooth, non-sensor, table, course 1
                //works
                chooseBackground(0);
                exploration = 0.05;
                p = new Agent(exploration);
                setPlayers( new Agent []{ p } );   
                setAuto (new Auto() );        
                showMessage(option+": Position, Q-Table, Non-Smooth, "+exploration);
                setDisplayStatistics(true);
                break;
            case 1:
                //non-smooth, non-sensor, table, course 2
                //works
                chooseBackground(1);
                exploration = 0.05;
                p = new Agent(exploration);
                setPlayers( new Agent []{ p } );   
                setAuto (new Auto() );        
                showMessage(option+": Position, Q-Table, Non-Smooth, "+exploration);
                setDisplayStatistics(true);
                break;
            case 2:
                //non-smooth, sensor, table, course 1
                //works
                chooseBackground(0);
                anzahlSensoren = 5;
                exploration = 0.05;
                p = new Agent(exploration);
                setPlayers( new Agent []{ p } );   
                setAuto (new AutoSensor(anzahlSensoren) );        
                showMessage(option+": Sensor, Q-Table, Non-Smooth, "+exploration+", "+anzahlSensoren+" Sensoren");
                setDisplayStatistics(false);
                break;
            case 3:
                //non-smooth, sensor, table, course 2
                //works
                chooseBackground(1);
                anzahlSensoren = 5    ; // with 3 it seems much harder than with 5
                exploration = 0.05;
                p = new Agent(exploration);
                setPlayers( new Agent []{ p } );   
                setAuto (new AutoSensor(anzahlSensoren) );        
                showMessage(option+": Sensor, Q-Table, Non-Smooth, "+exploration+", "+anzahlSensoren+" Sensoren");
                setDisplayStatistics(true);
                break;
            case 4:
                //non-smooth, sensor, neural net, course 1
                // doesn't work well, and only somewhat with 3 sensors
                chooseBackground(0);
                anzahlSensoren = 5;
                anzahlKnoten = 10;
                exploration = 0.05;
                p = new NeuralAgent(anzahlSensoren, anzahlKnoten, 3, exploration    );
                setPlayers( new Agent []{ p } );   
                setAuto (new AutoSensor(anzahlSensoren) );        
                showMessage(option+": Sensor, Q-Net ("+anzahlSensoren+"-"+anzahlKnoten+"-3, 0, 3), Non-Smooth, "+exploration+", "+anzahlSensoren+" Sensoren");
                setEverybodyVerbose(false);
                break;
            case 5:
                //non-smooth, sensor, neural net, course 2
                // doesn't work well, and only somewhat with 3 sensors
                chooseBackground(1);
                anzahlSensoren = 5;
                anzahlKnoten = 10;
                exploration = 0.05;
                p = new NeuralAgent(anzahlSensoren, anzahlKnoten, 3, exploration    );
                setPlayers( new Agent []{ p } );   
                setAuto (new AutoSensor(anzahlSensoren) );        
                showMessage(option+": Sensor, Q-Net ("+anzahlSensoren+"-"+anzahlKnoten+"-3, 0, 3), Non-Smooth, "+exploration+", "+anzahlSensoren+" Sensoren");
                break;
            case 6:
                //smooth, sensor, neural net, course 1
                //works
                chooseBackground(0);
                anzahlSensoren = 5;
                anzahlKnoten = 10;
                exploration = 0.05;
                p = new NeuralAgent(anzahlSensoren, anzahlKnoten, 3, exploration    );
                p.setVerbose(false);
                setPlayers( new Agent []{ p } );   
                setAuto (new SmoothAutoSensor(anzahlSensoren) );        
                showMessage(option+": Sensor, Q-Net ("+anzahlSensoren+"-"+anzahlKnoten+"-3, 0, 3), Smooth, "+exploration+", "+anzahlSensoren+" Sensoren");

                // setVerbose(false);
                verbose = false;
                setDisplayChanges(true);
                setDisplayNextMoves(true);
                setDisplayStatistics(true);

                break;
            case 7:
                //smooth, sensor, neural net, course 2
                //works 
                chooseBackground(1);
                anzahlSensoren = 5;
                anzahlKnoten = 10;
                exploration = 0.05;
                p = new NeuralAgent(anzahlSensoren, anzahlKnoten, 3, exploration    );
                setPlayers( new Agent []{ p } );   
                setAuto (new SmoothAutoSensor(anzahlSensoren) );
                showMessage(option+": Sensor, Q-Net ("+anzahlSensoren+"-"+anzahlKnoten+"-3, 0, 3), Smooth, "+exploration+", "+anzahlSensoren+" Sensoren");
                break;
            case 8:
                //smooth, sensor, neural net, course 2, experiment
                //works IMPORTANT
                chooseBackground(1);
                anzahlSensoren = 3;
                anzahlKnoten = 4-1;
                exploration = 0.05;
                p = new NeuralAgent(anzahlSensoren, anzahlKnoten, 3, exploration    );
                setPlayers( new Agent []{ p } );   
                setAuto (new SmoothAutoSensor(anzahlSensoren) );        
                showMessage(option+": Sensor, Q-Net ("+anzahlSensoren+"-"+anzahlKnoten+"-3, 0, 3), Smooth, "+exploration+", "+anzahlSensoren+" Sensoren");
                loadNet();
                setStopUponLapFinished(true);
                setExplorationRate(0);
                setStopUpdating(true);
                setEverybodyVerbose(true);
                break;
            case 9:
                //works
                chooseBackground(1);
                anzahlSensoren = 3;
                anzahlKnoten = 4;
                exploration = 0.0;
                p = new NeuralAgent(anzahlSensoren, anzahlKnoten, 3, exploration    );
                p.setLearningRate(0);
                //setStopUpdating = true; //##stop learning > keine Rewards > keine Kontrolle, ob Lap finished
                setPlayers( new Agent []{ p } );   
                setAuto (new SmoothAutoSensor(anzahlSensoren) );        
                setDisplayChanges(true); //but there are no changes when learning == 0
                setDisplayStatistics(true);
                setStopUponOffTrack(true);
                loadNet();
                showMessage(option+": Sensor, Q-Net ("+anzahlSensoren+"-"+anzahlKnoten+"-3, 0, 3), Smooth, "+exploration+", "+anzahlSensoren+" Sensoren");
                break;
            case 10: 
                //smooth, sensor, neural net, course 2, experiment
                //????????????????
                chooseBackground(1);
                anzahlSensoren = 3;
                anzahlKnoten = 4;
                exploration = 0.05;
                p = new NeuralAgent(anzahlSensoren, anzahlKnoten, 3, exploration    );
                setPlayers( new Agent []{ p } );   
                setAuto (new SmoothAutoSensor(anzahlSensoren) );        
                showMessage(option+": Sensor, Q-Net ("+anzahlSensoren+"-"+anzahlKnoten+"-3, 0, 3), Smooth, "+exploration+", "+anzahlSensoren+" Sensoren");
                //loadNet();
                setStopUponLapFinished(true);
                setEverybodyVerbose(false);
                setRespawnAfterLap(false);
                break;
            case 11:
                //one car with speed changes
                chooseBackground(0);
                anzahlSensoren = 3;
                anzahlKnoten = 8;
                exploration = 0.05;
                p = new NeuralAgent(anzahlSensoren+1, anzahlKnoten, 3+2, exploration);
                p.setLearningRate(0.05); // standard
                setPlayers( new Agent []{ p } );   
                setAutos ( new Auto[] {
                        //## SmoothAutoSensor hat als state speed!
                        new SmoothAutoSensor(anzahlSensoren, true), 
                    }
                );        
                // loadNet(0);
                // setExplorationRate(0);
                showMessage(option+": Sensor, Q-Net ("+(anzahlSensoren+1)+"-"+anzahlKnoten+"-5, 0, 3), Smooth, "+exploration+", "+anzahlSensoren+" Sensoren");
                setEverybodyVerbose(true);
                pm.setVerbose(false);
                setStopUpdating(false);
                useAdditionalMovesForSpeed = true;
                break;
            case 12: 
                //several cars
                chooseBackground(2);
                anzahlSensoren = 3;
                anzahlKnoten = 8;
                exploration = 0.05;
                Agent p0 = new NeuralAgent(anzahlSensoren, anzahlKnoten, 3, exploration);
                Agent p1 = new NeuralAgent(anzahlSensoren, anzahlKnoten, 3, exploration);
                Agent p2 = new NeuralAgent(anzahlSensoren, anzahlKnoten, 3, exploration);
                p0.setLearningRate(0.05); // standard
                p1.setLearningRate(0.05);
                p2.setLearningRate(0.05);
                setPlayers( new Agent []{ p0, p1,p2 } );   
                setAutos ( new Auto[] {
                        new SmoothAutoSensor(anzahlSensoren), 
                        new SmoothAutoSensor(anzahlSensoren), 
                        new SmoothAutoSensor(anzahlSensoren) 
                    }
                );        
                // loadNet(0);
                // loadNet(1);
                // loadNet(2);
                // setExplorationRate(0);
                showMessage(option+": Sensor, Q-Net ("+anzahlSensoren+"-"+anzahlKnoten+"-3, 0, 3), Smooth, "+exploration+", "+anzahlSensoren+" Sensoren");
                setEverybodyVerbose(true);
                setStopUpdating(false);
                break;
            case 13:
                //smooth, sensor, table, course 1
                //works
                chooseBackground(1);
                exploration = 0.05;
                anzahlSensoren = 3;
                p = new Agent(exploration);
                setPlayers( new Agent []{ p } );   
                setAuto (new SmoothAutoSensor(anzahlSensoren) );        
                showMessage(option+": Sensor, Q-Table, Smooth, "+exploration+", "+anzahlSensoren+" Sensoren");
                setEverybodyVerbose(true);
                pm.setVerbose(false);
                break;
            default:
                p = new Agent(0.05);
                setPlayers( new Agent []{ p } );   
                setAuto (new Auto() );
        }

    }
}