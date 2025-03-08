import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class MazeGameKI extends MazeGame
{

    public MazeGameKI()
    {
        // 1. ActOrder festlegen, damit AnzeigeNextMoves als Letztes kommt (fuer Spiel nicht relevant, nur fuer Anzeige)
        // 2. mit setPlayers einen Agenten erzeugen, der dann die Aufgaben von player0 uebernehmen soll
    }

    @Override
    public void setup() {
        super.setup();
        removeObject(player1);
        // man koennte player0 ersetzen durch eine nicht lenkbare Standardfigur, die man dann aber noch als Objekt auf der Welt
        // platzieren muesste
        // player1 wird entfernt, weil der sonst nervt
    }

    @Override 
    public int [] getLegalMoves() { 
        // gib zurueck ein Feld mit den Zuegen, zum Beispiel mit den Zahlen 0 bis 3 einschliesslich,
        // die spaeter fuer die Optionen Nord, Ost, Sued, West stehen werden
        // kann ueber ein Attribut gemacht werden, muss aber nicht
        return null;
    }

    @Override
    public String getNameForMove(int move) { 
        // kann man auch ganz weglassen, die Methode; sie macht nur die Anzeige leichter verstaendlich
        // wenn 0 -> 'N', 1->'S', 2->'O', 3->'W' wird
        return null;
    }

    @Override
    public void makeMove(int player, int move) {
        //wenn der player 0 ist, und der move 0, dann soll player0 nach Norden gehen; die Methode gibt es im Plaerobjekt, also: player0.geheNord()
        //wenn der player 0 ist, und der move 1, dann soll player0 nach Osten
        //wenn der player 0 ist, und der move 2, dann soll player0 nach Sueden
        //wenn der player 0 ist, und der move 3, dann soll player0 nach Westen
        //die Zuordnung der Zahlen zu den Aktionen ist willkuerlich, sollte aber zur vorherigen Methode passen
    }

    @Override
    public String getState(int irrelevantPlayerID) {
        // fuer den Anfang koennte der Zustand aus den x- und y-Koordinaten von player0 bestehen,
        // getrennt durch einen Doppelpunkt - die Koordinaten kriegt man mit den 
        // Greenfoot-Actor-Methoden getX() und getY()
        return null;
    }

    @Override
    public int getWinner() {
        // fuer den Anfang hat man gewonnen, wenn man rechts unten ist, also wenn die 
        // Koordinaten von player0 entweder getWidth()-2 und getHeight()-2 sind, oder
        // gleich direkt, wenn sie 13 bzw. 9 sind
        // die Koordinaten kriegt man wieder mit getX() und getY()
        // - dann gibt man 0 zurueck (Spieler 0 hat gewonnen), sonst -1 (Spiel laeuft noch)
        return -1;
    }

    private void respawn() {
        player0.setLocation(1,1); // respawn
    }

    @Override
    public double getRewardForPlayer(int id) {
        // wenn die Koordinaten der Figur rechts unten sind, dann wird 10 zurueckgegeben,
        // sonst 0
        // außerdem sollte im Fall des Gewinns auch die Methode respawn() aufgerufen
        // werden,
        return 0;
    }

}