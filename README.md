# Greenfoot Reinforcement Learning

## Was das hier ist

Das hier ist ein Greenfoot-Projekt mit mehreren eigentlich voneinander unabhängigen rudimentären Spielen: Ein einfaches **Breakout,** ein einfacheres **Autorennen**, ein **Snake**. Sie sind alle deshalb in einem Projekt, weil sie alle die gleiche zusätzliche Infrastruktur nutzen, um nämlich automatisierte Bots alleine oder gegeneinander spielen zu lassen. Diese Bots können durch KI-Agenten gesteuert werden (Reinforcement Learning) oder, insbesondere bei Snake, auch einen einfachen Algorithmus verwenden, wie er von Schülern oder Schülerinnen entwickelt werden könnte.

Das Projekt ist nicht so ordentlich, wie man es sich vielleicht erhofft; diese Datei als Textdatei im Markdown-Format soll Überblick verschaffen. Man kann sie, wie das ganze Projekt, herunterladen und mit einem Markdown-Betrachter öffnen, theoretisch natürlich auch mit jedem Textverarbeitungsprogramm.

Greenfoot ist eine didaktische Programmierumgebung für Java (oder Stride), die in Schulen viel verwendet wird: https://www.greenfoot.org/download

_Fußnote: In früheren Fassungen des Projekts gab es noch zwei unterschiedliche Belohnungssysteme. Ich baue das alte, primitivere Modell gelegentlich als Option wieder ein, es schien mir aber im Moment doch zu verwirrend zu sein._

## Ausprobieren (1)

Zum Ausprobieren des Projekts solltest du dir erst einmal die Spiele anschauen. Dazu:

1. Lege per Mausklick ein Objekt der Klasse `BreakoutGame` an. Du kannst dann ein einfaches Spiel spielen, gesteuert mit den Tasten A und D. 
2. Lege per Mausklick ein Objekt der Klasse `AutoGame` an. Du kannst dann ein einfaches Spiel spielen, gesteuert mit den Tasten A und D. Das Spiel ist nicht sehr interessant, und man muss sich die Geschwindigkeit herabsetzen.
3. Lege per Mausklick ein Objekt der Klasse `SnakeGame` an. Du kannst dann ein einfaches Spiel spielen, gesteuert mit den Tasten ASDW.

## Ausprobieren (2)

Zum weiteren Ausprobieren solltest du anschauen, wie die Bots die verschiedenen Spiele spielen. 

Lege dazu per Mausklick ein Objekt der Klasse `BreakoutGameKI`, `AutoGameKI` oder `SnakeGameKI` an. Dann läuft jeweils ein kurzes Beispiel-Szenario mit einer lernenden KI ab. Es gibt dabei immer sehr viele Konfigurationsmöglichkeiten; die Unterklasse AutoScenarios, BreakoutScenarios und SnakeScenarios bieten einige vorkonfigurierte Szenarien an.

![Breakout](/Material/images/breakout.png)


## Allgemeines Vorgehen

Hier wird erklärt, wie man beim Entwickeln dieser Spiele allgemein vorgeht, und wie die wichtigsten Klassen zusammenhängen, so dass man eigene Spielideen umsetzen kann.

**Schritt 1**

Man erstellt zuerst einmal ein einfaches grafisches Spiel mit Greenfoot. Beispiele: `AutoGame`, `BreakoutGame`, `SnakeGame`. Ich habe das bei mir so gehalten, dass das Spiel, weil es das später einfacher macht, fast alles Nötige im Konstruktor erstellt, nur das Erstellen des zu steuernden Objekts (also das Auto oder der Schläger, die später durch einen KI-Bot ersetzt werden) habe ich in einer setup-Methode ausgelagert sein, die vom Konstruktor aufgerufen wird und von den Unterklassen überschrieben wird. 

Es ist außerdem günstig, aber ebenfalls nicht nötig, alle zu dem Spiel gehörenden Actor-Unterklassen in einer gemeinsamen Oberklasse zu sammeln, siehe die Klassen `AutoElement`, `BreakoutElement` oder `SnakeElement`. Das hilft mir, den Überblick über die Klassen zu bewahren; außerdem ist das manchmal für die Spielsteuerung ganz praktisch.

**Schritt 2**

Wenn das Spiel steht, macht man die Greenfoot-Spielwelt, die bisher eine Unterklasse von `World` war, zu einer Unterklasse von `AbstractGameWorld`. Das Spiel sollte sich weiterhin wie gewohnt spielen lassen, wenn man Greenfoot die eigentliche Spielklasse als Startwelt verwenden lässt. Die neue Oberklasse `AbstractGameWorld` beeinflusst das Ausführen der Spielwelt eigentlich überhaupt nicht, jedenfalls wenn alles funktioniert.

**Schritt 3**

Man erstellt eine Unterklasse zur Spieleklasse, zum Beispiel bisher `AutoKI`, `BreakoutKI` oder `SnakeGameKI`. Diese Klassen müssen, um sinnvoll arbeiten zu können, bestimmte ererbte Methoden überschreiben. Das ist die Hauptaufgabe, die im nächsten Abschnitt erklärt wird. Die Trennung in Spiel-Welt und KI-Welt erfolgt deshalb, weil man so das Spiel unabhängig von der KI-Anwendung entwickeln kann.

**Schritt 4**

Da es so viele Varianten gibt, die man ausprobieren möchte, legt man sich eine Unterklasse der KI-Klasse an (die ja selber, Schritt 2, eine Unterklasse von `AbstractGameWorld` ist), um so einigermaßen elegant die Varianten anbieten zu können. Bisher heißen sie `AutoSencarios`, `BreakoutScenarios` und `SnakeScenarios`.

## Die KI-Klasse und ihre Methoden

Die Hauptaufgabe besteht darin, eine KI-Unterklasse zur Spielwelt zu schaffen und mit den nötigen Methoden zu bestücken. Diese Methoden könnten in `AbstractGameWorld` alle als abstrakte Methoden angelegt sein, so dass man leicht überprüfen kann, ob man sie alle hat. Ich habe mich dagegen entschieden, sondern stattdessen zu überschreibende Dummy-Methoden in `AbstractGameWorld` implementiert, damit sich das ursprüngliche Spiel eben auch ohne diese KI-Unterklasse verwenden lässt.

Beispiel: AutoGameKI, BreakoutGameKI, SnakeGameKI und BreakoutGameKIMinimal. Das letztere ist eine abgespeckte Klasse zur Veranschaulichung, die wir jetzt der Reihe nach durchgehen. Der Code passt auf eine Textseite.

Ab jetzt folgen Kommentare zu Methoden, die angelegt werden sollten. Als Beispiel dient dazu die Klasse `BreakoutGameKIMinimal`, die Klasse `SnakeGameKIMinimal` ist ein ähnliches minimales Beispiel, das man anschauen kann.

### Die ganz einfachen Methoden ohne KI-Bezug

`public void setup()`  
Überschreibt lediglich die Methode der Oberklasse, die als Teil des Konstruktors dort aufgerufen wird.  
Ein Objekt vom Typ SchlaegerKI wird platziert, der sich vom ursprünglichen Schläger nur dadurch unterscheidet, dass seine act-Methode, mit der er sonst vom Spielenden gesteuert würde, leer ist. Sonst könnte ja nicht nur die KI, sondern gleichzeitig auch der Mensch den Schläger bewegen.
Die Greenfoot-World-Methode `setActOrder` wird aufgerufen, die festlegt, welche Reihenfolge der Actor-Unterklassen beim Aufruf ihrer act-Methode verwendet wird. Der Grund dafür ist, dass Objekte der Klasse `AnzeigeNextMoves` nach allen anderen drankommen sollen. Das muss man nicht machen, aber nur dann stimmt die Angabe des kommenden Spielzugs, weil dazu nach dem Zug des Agenten (also KI-Bots) der Zustand der Welt nicht mehr verändert werden sollte, damit vorhergesagt werden kann, welchen Zug der Agent beim nächsten Mal macht.  
Der Aufruf von `setPlayers(  new Agent(0.0)  )` legt fest, dass ein Standard-Agent den Spielpart übernehmen soll. Standard heißt: Reinforcement Learning mit einer Q-Tabelle. In anderen Spielen wird man auch mehrere Agenten für mehrere zu bewegende Objekte haben. 
(Die Q-Tabelle speichert für jeden aufgetretenen Zustand der Spielwelt alle möglichen Züge des Agenten und deren Bewertung. Beim Reinforcement Learning ändert sich diese Bewertung automatisch, und die Option mit der höchsten Bewertung wird gewählt, sofern die Explorationsrate nicht zu einem zufälligen anderen Zug greifen lässt. Greenfoot kann vermutlich sinnvoll wenige zehntausend Zustände verwalten, bei den meisten Spielen wird des aber sehr viel mehr Zustände geben, so dass die Q-Tabelle versagt. Dann greift man zu einem Q-Net, einem neuronalen Netz. Das geht mit dem Agenten `NeuralAgent`, wozu später mehr.)

`public void erhoeheLevel()`  
Ist nur dazu dazu, um die ererbte Methode, mit der man im regulären Spiel zum nächsten Schwierigkeitsgrad kommt, zu überschreiben, und das Spiel damit zu vereinfachen.

`private double berechneEntfernung(int x, int y, int x2, int y2)`  
Eine Hilfsmethode, die die Entfernung zweier Punkte in einem kartesischen Koordinatensystem berechnet. Sie wird hier gar nicht verwendet, aber vielleicht will man sie ja für einen Zustand heranziehen.

### Die einfachen überschreibenden KI-Methoden

Diese Methoden werden von `AbstractGameWorld` und verschiedenen weiteren Klassen verwendet. Sie sind in `AbstractGameWorld` vordeklariert, müssen aber überschrieben werden.

`public int [] getLegalMoves() { return new int [] { 1, 2, 0}; }`  
Die verschiedenen Spielzüge oder Entscheidungsmöglichkeiten, zwischen denen die KI sich entscheichen kann, werden als `int` modelliert. Diese Methode gibt einfach ein Array zurück mit allen grundsätzlich möglichen Spielzügen.

`public String getNameForMove(int move)`  
Zur Anzeige kann man sich statt der Zahlen für die Züge auch einen schöneren Namen geben lassen. Wird diese Methode nicht überschrieben, werden stattdessen einfach die Zahlenwerte dargestellt. Im Moment wird 0 auf "N" (nichts), 1 auf "L" (nach links gehen), 2 auf "R" (nach rechts gehen) abgebildet. Normalerweise würde man das sicher mit einem Array-Attribut umsetzen, aber ich wollte die Minimalklasse minimal halten.

`public void makeMove(int player, int move)`  
Der eigentliche Zug: Was bedeuten die oben angelegten Zahlen in der Spiewelt, welche Auswirkungen haben sie? 1 bedeutet, dass der Schläger etwas nach links bewegt wird, 2 bedeutet, dass er etwas nach rechts bewegt wird. 0 bedeutet, dass er da bleibt, wo er ist. Andere Zahlen dürften nicht auftauchen, sie kommen ja nur aus dem Pool der legalen Züge der vorherigen Methode. Bei einem Einspieler-Spiel wie im Beispiel wird der player-Parameter ignoriert, ansonsten wird diese Methode von jedem Agenten mit der Spielernummer als Argument aufgerufen, der damit der Spielwelt die Entscheidung kundtut.

`public double getInitialValue()`  
Kann man auch weglassen. Hier geht es um die Anfangsbelegung für die Inhalte der Q-Tabelle. Standard ist 1.

### Die schwierigeren überschreibenden KI-Methoden

Diese Methoden werden von `AbstractGameWorld` und verschiedenen weiteren Klassen verwendet. Sie sind in `AbstractGameWorld` vordeklariert, müssen aber überschrieben werden. Anders als bei den bisherigen Methoden muss man hier viel nachdenken.

`public String getState() {  return getState(0); }`  
Hier geht es um den Zustand des Spiels. Manchmal ist der für jeden Spieler anders (bei unterschiedlichen Positionen einer Spielfigur, oder bei teilweise versteckter Information), manchmal ist er für alle identisch (etwa: Schach, Tic-Tac-Toe; alle Einpersonenspiele). Da es hier nur einen Zustand gibt statt unterschiedlicher Perspektiven, wird einfach die Perspektive von Player 0 aufgerufen.

`public String getState(int playerID)`  
Eine der schwierigsten Entscheidungen: Wie man den Zustand codiert. Er solle zur leichteren Weiterverarbeitung später nur aus durch Doppelpunkte getrennten Ganzzahlen bestehen. Im Beispiel ist der Zustand einfach die Differenz zwischen x-Position von Kugel und Schläger. Damit ist viel wesentliche Information für eine Lösung enthalten, andere mögliche Zustände wären eine Kombinationen von x- und y-Position von Kugel und Schläger, und Blickrichtung der Kugel, eventuell noch ihre Geschwindigkeit, wenn es verschiedene Geschwindigkeiten gibt. Hier wird man viel experimentieren wollen. Deshalb enthält diese Methode in den tatsächlichen KI-Klassen auch meist einen switch, um einfach Verschiedenes ausprobieren zu können.  
Im Beispiel sind die Zustände also "0" oder "-1" oder "-10" oder "8", Doppelpunkte tauchen gar nicht auf, weil es ich ja nur um einen einzigen Wert handelt. In der Klasse `BreakoutGameKI` gibt es Alternativen, die dann etwa das Format "10:20:40:8" haben.

`public int getWinner()`  
Diese Methoden wir herangezogen um zu ermitteln, wer das Spiel gewonnen hat. Sie wird unabhängig von den Belohnungen aufgerufen, sollte also nichts am Zustand der Welt in einer Form ändern, die etwas an der Belohnung ändern würde. Die Methode gibt zurück, ob das Spiel gewonnen wurde (Rückgabewert 0 für Spieler 0) oderman  verloren hat  (Rückgabewert 1, beziehungsweise eine andere Zahl > 0) oder ob das Spiel noch läuft (Rückgabe -1, oder irgendetwas <0). Tatsächlich ist diese Methode eher für die Statistik wichtig.

`public double getRewardForPlayer(int id)`  
Das ist das eigentliche Belohnungssystem. Nach `jeder` einzelnen Entscheidung wird diese Methode aufgerufen. 
Für das Breakout wird hier die 10 zurückgegeben für die Berührung mit dem Schläger, und -10, wenn der Ball im Aus ist, ansonstn 0. Auch hier kann man sich sehr viel verschiedene Varianten denken. 
Bei dem Beispiel zählt als Gewinn, wenn der Schläger die Kugel berührt, und als Niederlage, wenn die Kugel im Aus ist. Alles andere ist Fortsetzung des Spiels. Belohnt wird hier also nicht kontinuierlich.

Hier die Klasse dazu:

    import greenfoot.*;
    public class BreakoutGameKIMinimal extends BreakoutGame {

        // Uberschreiben von Breakout-spezifischen Methoden, und Hilfsmethoden
        
        @Override    
        public void setup() {
            setActOrder( BreakoutElement.class, AnzeigeNextMoves.class );
            schlaeger = new SchlaegerKI();
            addObject(schlaeger, 360/2, 480-20);   
            setPlayers( new Agent(0.0) );    
        }
    
        @Override
        public void erhoeheLevel() {}
    
        private double berechneEntfernung(int x, int y, int x2, int y2) {
            return Math.sqrt((x-x2)*(x-x2)+(y-y2)*(y-y2));
        }
    
        // Uberschreiben von KI-relevanten Methoden
    
        @Override 
        public int [] getLegalMoves() { return new int [] { 1, 2, 0}; }
    
        @Override
        public String getNameForMove(int move) { 
            if (move==0) return "N";
            else if (move==1) return "L";
            else if (move==2) return "R"; 
            else return null;
        }
    
        @Override
        public void makeMove(int player, int move) {
            if      (move == 1) for (int i=0; i<5; i++) schlaeger.geheNachLinks();        
            else if (move == 2) for (int i=0; i<5; i++) schlaeger.geheNachRechts();
            else { }
        }
    
        @Override
        public double getInitialValue() { return 1; }
    
        @Override
        public String getState() {  return getState(0); }
    
        @Override
        public String getState(int irrelevantPlayerID) {
            return "" + (schlaeger.getX() - kugel.getX())/10;
        }
        
        @Override
        public int getWinner() {
            if (kugel.beruehrtSchlaeger()) return 0;
            else if (istImAus()) {
                kugel.respawn();
                return 1;
            }
            return -1;
        }
        
        @Override
        public double getRewardForPlayer(int id) {
            if (kugel.beruehrtSchlaeger()) return 10;
            else if (istImAus()) return -10;
            else return 0;
        }
    }

### Weitere Hilfsmethoden

In der Klasse `AbstractGameWorld` gibt es Hilfsmethoden, die man in den Unterklassen, typischerweise beim Setup, aber auch zur Laufzeit, aufrufen kann:

`public final void setPlayers(Agent... p)`  
Diese Methode **muss** aufgerufen werden, um einen oder mehrere oder ein Array von Agenten ins Spiel zu bringen. Die Agenten erhalten fortlaufende Nummern, sie werden der Reihe nach über die Zustände des Systems informiert (via getState), können dann Entscheidungen treffen, die sie via makeMove kommunizieren. Der Methode makeMove obliegt dann die Umsetzung in der Spielwelt.

`public final void setExplorationRate(double e)`  
Eigentlich nur zur Diagnose, so kann man das Programm laufen lassen, dann auf Pause drücken und die Explorationsrate auf einen anderen Wert setzen.

`public final void setBreakPeriodically(int i)`  
Alle i Siege pausiert der Ablauf. Zur Diagnose einsetzbar.

`public final void setDisplayChanges(boolean b)`  
Sollen Änderungen in den Tabellenwerten angezeigt werden? Ist schön, dauert Zeit.

`public final void setDisplayNextMoves(boolean b)`  
Soll der nächste Zug angezeigt werden? Ist schön, dauert Zeit.

`public final void setDisplayWinsLossesStates(boolean b)`  
Sollen die Anzahl von Siegen, Niederlagen und verrwalteten Zuständen angezeigt werden?

`protected void showMessage(String s)`  
Zeigt unten an eine Nachricht an.

## Agenten

Agenten heißen die Bots, die von der KI aufgefordert werden, einen Zug zu machen, indem deren Methode `play()` aufgerufen wird. Darin wird überlicherweise erst der Zustand der Spielwelt erfragt, dann wird beim Verwenden einer Q-Tabelle nachgeschlagen, was für diesen Zustand der beste Zug ist; wenn zu diesem Zustand keine Information erhalten ist, wird ein neuer Eintrag in der Tabelle angelegt. Dann wird der ausgewählte Zug dem Spiel kommuniziert, das wiederum den Zug durchführt und die Belohnung dafür kommuniziert. Unterklasse von `Agent` nutzen ein Q-Net statt einer Tabelle (`NeuralAgent`), oder - das braucht man nur in speziellen Fällen und zum Testen - wählen einen zufälligen Zug (`RandomAgent`) oder geben einem Menschen Gelegenheit, zu entscheiden (`HumanGreenfootMove`).

### Agent

Der normale Agent benutzt eine Q-Tabelle für das Reinforcement Learning. Der Konstruktor hat als Parameter die Explorationsrate, also 0.0 oder 0.05, die angibt, mit welcher Wahrscheinlichkeit ein zufälliger statt des höchstbewerteten Zugs gemacht werden soll.

### NeuralAgent

Dieser Agent benutzt ein Neuronales Netz für das Reinforcement Learning. Er erfordert das manuelle oder meist automatische Setzen `setUpdateOnEveryMove()` in der KI-Spiel-Klasse. Der Konstruktor des `NeuralAgent` hat vier Parameter:  

* Erstens die Anzahl an Eingangsknoten – sie entsprechen den durch Doppelpunkt : getrennten Ganzzahlen, die den Zustand repräsentieren. Wenn getState das Format „1“ hat, ist das ein Eingangsknoten, bei Werten wie „1:1“ sind es zwei, bei Werten wie „1:1:1:0:32“ sind es fünf.
* Zweitens die Anzahl an Knoten der einen versteckten Ebene. 10 ist eine gute Zahl.
* Drittens die Anzahl an Knoten der Ausgabeebene; sie muss der Anzahl an möglichen Zügen entsprechen; beim Breakout also 3 (für nichts, links, rechts), bei Snake 4 (für Nord, Ost, Süd, West).
* Viertens die Explorationsrate, wieder 0.0 oder 0.05, zum Beispiel.

Der `NeuralAgent` besitzt Methoden zum laden und Speichern von Netzen und Belegungen, Speicherort ist standardmäßig unmittelbar außerhalb des Projektverzeichnisses.

## Die Szenario-Unterklassen

Das sind reine Hilfsklassen, die das Arbeiten mit verschiedenen Szenarien erleichtern.

## Das Auto-Spiel

Das Autogame ist komplex. Es gibt zwei verschiedene Hintergründe, so dass man mit dem einen trainieren und das trainierte Modell dann am anderen Hintergrund testen kann. Ziel ist, dass ein Auto selbständig den Weg zum Ziel findet. (Mehrfachspielermodus ist nur in Ansätzen imoplementiert.) Das Spielfeld ist farbig kodiert, je dunkler die Farbe wird, desto näher ist man der Ziellinie aus der richtigen Richtung gekommen.

Der Zustand wird vom Fahrzeug-Objekt erzeugt. Es kann dabei zur Klasse Auto/SmoothAuto gehören, dann ist der Zustand sehr naiv und besteht aus x/y-Koordinaten: dann lernt das System sehr schnell, aber natürlich nur für diesen einen expliziten Kurs. Oder man nimmt die Klasse AutoSensor/SmoothAutoSensor: dann hat das Fahrzeug 3 oder 5 oder 7 (am besten erst einmal: 3) Sensoren, mit denen der Abstand nach vorne und zur Seite gemessen und als State verwendet wird. Das ganze geht außerdem als regulärer Actor oder als SmoothActor; letzteres ist eine beliebte Greenfoot-Lösung, um Rundungsfehler bei den x/y-Koordinaten zu reduzieren, die im Prinzip zu Winkel in Vielfachen von 45° führen.
Man kann darüber hinaus wie immer als Player einen `Agent` haben oder einen `NeuralAgent` - für die Sensor-Varianten ist das nötig, weil der Zustandsraum bei dem Sensoren-Fahrzeug sehr groß ist. 

Auch das Belohnungssystem ist komplex. Positive oder negative Belohnung kann es geben für: Vorwärtsfahren (eine dunklere Farbe erreicht, außer natürlich an der Startlinie), Rückwärtsfahren (hellere Farbe), Überqueren der Ziellinie, Verlassen der Fahrbahn.
Laden und Speichern: Geht mit Methoden der Klasse NeuralAgent.

![KI ohne SmootMover, daher nur immer in Vielfachen von 45°](/Material/images/ki_auto_sensor_strecke1.gif)  
Ein trainierter `Agent` fährt Auto: Das Auto hat hier fünf Sensoren, die zusammen den Zustand des Spiels für die KI ausmachen. Der Agent verwaltet gut 45.000 ihm bekannte Zustände in einer Tabelle. Das Auto ist ein Standard-Greenfoot-Actor und dreht sich damit immer nur in Vielfachen von 45°, SmoothMover und Varianten davon ermöglichen eleganteres Verhalten.


## Das Snake-Spiel

Das Snake-Spiel unterscheidet sich von den anderen Spielen dadurch, dass es hier interessant ist, nicht nur mit KI-Agenten zu arbeiten, sondern auch mit einfacheren Algorithmen. Mit `setSnakes` werden die mitspielenden Schlangen ausgewählt, mit `setPlayers` die mitspielenden Agenten. Es kann zum Beispiekl vier Snakes geben, drei davon gehören zur Klasse SnakeKI, eine zu einer anderen Snake-Klasse. Wenn es drei KI-Snakes gibt, müssen auch drei Agenten ausgewählt werden (ohne Agent macht die Schlange einfach nichts). Die Agenten werden in der Reihenfolge der Angabe auf die Snakes verteilt. Die vierte Snake, ohne Agent, reagiert nach ihrem eigenen Algorithmus.

![Snake Game](/Material/images/snakes_kurz.gif)

## Wie gut geht das alles?

Weniger gut, als man meint. Damit die KI bei Snake so gut spielt wie ein einfacher Algorithmus, muss die KI als Zustand nicht nur die Felder um sich herum kennen, oder die Anzahl freier Felder orthogonal zu ihr selbst, sondern im Idealfall das ganze Spielfeld im Blick haben – das sind mit 60 x 40 Eingangsknoten viele, und noch dazu rächt sich hier die Entscheidung, den Zustand als String zu modellieren und nicht gleich als Array von Zahlen.

## Ginge das nicht auch einfacher?

Ja. Das Projekt ist halt entstanden aus bereits existierenden BlueJ-Projekten für Neuronale Netze und Reinforcement Learning, und greift außerdem auf bereits bestehende Greenfoot-Spieleprojekt zurück. Die Klassen PlayerManager und Game und so weiter, die nicht zur Greenfoot-Hierarchie gehören, kommen ursprünglich von BlueJ. 

## Was ist das mit dem Tic-Tac-Toe?

Lange Geschichte, der Versuch, das BlueJ-Projekt irgendwie zu visualisieren. Ich arbeite noch daran.

## Jetzt selber ausprobieren!

![Maze Game](/Material/images/mazeGame.png)

Die Klasse MazeGame ist vorgegeben, in ihr werden ein kleines Labyrinth erzeugt, eine mit ASDW lenkbare Spielfigur und ein mehr oder weniger zufällig agierender Bot. 

Aufgabe 1: Schreibe eine KI-Unterklasse, so dass die Figur den Weg nach rechts unten findet. Die Klasse MazeGameKI ist dazu vorgegeben, alle notwendigen Methoden sind als Kopf enthalten, ihr Inhalt muss aber meist geaendert werden. Siehe Kommentare in der Klasse.

Aufgabe 2: Passe die Klasse so an, dass das Ziel jetzt ist, den Bot zu fangen. Dazu muss der Zustand erweitert werden (mindestens um die Position des Bot), außerde die Methoden `getWinner` und getRewardForPlayer`, vielleicht auch die respawn-Methode. 

Aufgabe 3: Experimentiere mit größeren Labyrinthen. Der Agent oder NeuralAgent tut sich manchmal erstaunlich schwer bei der Aufgabe.

## Kontakt

Fragen gerne an Thomas Rau, lehrerzimmer@herr-rau.de oder https://fnordon.de/@herr_rau
