import java.util.*;

/**
 * @author Sto 
 */

public class NeuronalesNetz //implements Netz
{
    // Anzahl der Knoten in der Eingabe-, der versteckten und der Ausgabeschicht, Lernrate
    private int anzahlEingabeKnoten;
    private int anzahlVersteckteKnoten;
    private int anzahlAusgabeKnoten;
    private double lernrate;
    // Matrizen der Verbindungsgewichte e -> v und v -> a
    double[][] gewichteEingangNachVersteckt;
    double[][] gewichteVerstecktNachAusgabe;
    // Zufallsgenerator für Anfangsbelegung der Gewichtsmatrizen
    private Random zufall;
    // output
    boolean verbose = false;
    // Aktivierungsfunktion
    int aktivierungsfunktion = 0;
    // 0 = sigmoid
    // 1 = cutoff
    // 2 = relu
    // 3 = leaky relu
    // Initialisierung
    int initialisierung = 3;
    // 0 = zufall original
    // 1 = HeUniform experimentell
    // 2 = alle mit fixem Wert 0.01
    // 3 = von -1 bis 1
    // 4 = alle -0.01 oder 0.01
    // 5 = von -10 bis 10

    /**
     * Constructor for objects of class NeuronalesNetz
     */
    public NeuronalesNetz(int eingabeKnoten, int versteckteKnoten, int ausgabeKnoten, double lernRate)
    {
        anzahlEingabeKnoten = eingabeKnoten;
        anzahlVersteckteKnoten = versteckteKnoten;
        anzahlAusgabeKnoten = ausgabeKnoten;
        lernrate = lernRate;
        gewichteEingangNachVersteckt = new double[anzahlVersteckteKnoten][anzahlEingabeKnoten];
        gewichteVerstecktNachAusgabe = new double[anzahlAusgabeKnoten][anzahlVersteckteKnoten];
        zufall = new Random();

        initialisiere(initialisierung);       
    }

    public void setzeAktivierungsfunktion(int select) {
        aktivierungsfunktion = select;
    }

    public void initialisiere(int select) {
        initialisierung = select;
        switch(select) {
            case 0: this.belegeGewichteZufaellig(); break;
            case 1: this.belegeGewichteHeUniform(); break;
            case 2: this.belegeGewichteMit(0.01); break;
            case 3: this.belegeGewichteZufaelligVonBis(-1, 1); break;
            case 4: this.belegeGewichteZufaelligMitEinemVon(-0.1, 0.1); break;
            case 5: this.belegeGewichteZufaelligVonBis(-10.1, 10.1); break;
            case 6: this.belegeGewichteZufaelligVonBis(0.5, 10*Math.sqrt(2/3.)); break;
            default: this.belegeGewichteZufaellig(); break;
        }
        if (verbose) this.ausgabeGewichte(gibGewichte());
    }

    private void belegeGewichteZufaellig() {
        //System.out.println("Gewichte Eingang -> Versteckt");
        for(int i = 0; i < anzahlVersteckteKnoten; i++)
        {   //System.out.println();
            for (int j = 0; j < anzahlEingabeKnoten; j++)
            {  gewichteEingangNachVersteckt[i][j] = (zufall.nextDouble() - 0.5)/4;
                //System.out.print(ev[i][j]+"   "); 
            } 
        }
        //System.out.println("Gewichte Versteckt -> Ausgang"); 
        for(int k = 0; k < anzahlAusgabeKnoten; k++)
        {   //System.out.println();
            for(int h = 0; h < anzahlVersteckteKnoten; h++)
            {   gewichteVerstecktNachAusgabe[k][h] = (zufall.nextDouble() - 0.5)/4;
                //System.out.print(va[k][h]+"   ");
            }
        }
    }

    private void belegeGewichteZufaelligVonBis(double von, double bis) {
        //System.out.println("Gewichte Eingang -> Versteckt");
        for(int i = 0; i < anzahlVersteckteKnoten; i++)
        {   //System.out.println();
            for (int j = 0; j < anzahlEingabeKnoten; j++)
            {
                gewichteEingangNachVersteckt[i][j] = (zufall.nextDouble() * (bis-von)) + von;
            } 
        }
        //System.out.println("Gewichte Versteckt -> Ausgang"); 
        for(int k = 0; k < anzahlAusgabeKnoten; k++)
        {
            for(int h = 0; h < anzahlVersteckteKnoten; h++)
            {
                gewichteVerstecktNachAusgabe[k][h] = (zufall.nextDouble() * (bis-von)) + von;
            }
        }
    }

    private void belegeGewichteZufaelligMitEinemVon(double d1, double d2) {
        //System.out.println("Gewichte Eingang -> Versteckt");
        for(int i = 0; i < anzahlVersteckteKnoten; i++)
        {   //System.out.println();
            for (int j = 0; j < anzahlEingabeKnoten; j++)
            {
                if (Math.random()<0.5) gewichteEingangNachVersteckt[i][j] = d1;
                else gewichteEingangNachVersteckt[i][j] = d2;
            } 
        }
        //System.out.println("Gewichte Versteckt -> Ausgang"); 
        for(int k = 0; k < anzahlAusgabeKnoten; k++)
        {
            for(int h = 0; h < anzahlVersteckteKnoten; h++)
            {
                if (Math.random()<0.5) gewichteVerstecktNachAusgabe[k][h] = d1;
                else gewichteVerstecktNachAusgabe[k][h] = d2;
            }
        }
    }

    private void belegeGewichteMit(double d) {
        //System.out.println("Gewichte Eingang -> Versteckt");
        for(int i = 0; i < anzahlVersteckteKnoten; i++)
        {
            for (int j = 0; j < anzahlEingabeKnoten; j++)
            {
                gewichteEingangNachVersteckt[i][j] = d;
            } 
        }
        //System.out.println("Gewichte Versteckt -> Ausgang"); 
        for(int k = 0; k < anzahlAusgabeKnoten; k++)
        {   
            for(int h = 0; h < anzahlVersteckteKnoten; h++)            
            {   
                gewichteVerstecktNachAusgabe[k][h] = d;
            }
        }
    }

    // MAY BE INCORRECT1!!
    private void belegeGewichteHeUniform() {
        double min = -Math.sqrt(6.0/anzahlEingabeKnoten);
        double max = Math.sqrt(6.0/anzahlVersteckteKnoten);
        double size = max - min; 

        //System.out.println("Gewichte Eingang -> Versteckt");
        for(int i = 0; i < anzahlVersteckteKnoten; i++)
        {   //System.out.println();
            for (int j = 0; j < anzahlEingabeKnoten; j++)
            {
                gewichteEingangNachVersteckt[i][j] = min + (zufall.nextDouble() * size);
                //System.out.print(ev[i][j]+"   "); 
            } 
        }

        min = -Math.sqrt(6.0/anzahlVersteckteKnoten);
        max = Math.sqrt(6.0/anzahlAusgabeKnoten);
        size = max - min; 

        //System.out.println();
        //System.out.println("Gewichte Versteckt -> Ausgang"); 
        for(int k = 0; k < anzahlAusgabeKnoten; k++)
        {   //System.out.println();
            for(int h = 0; h < anzahlVersteckteKnoten; h++)
            {
                gewichteVerstecktNachAusgabe[k][h] = min + (zufall.nextDouble() * size);
                //System.out.print(va[k][h]+"   ");
            }
        }
        //System.out.println();    
    }

    /**
     * Trainieren des Netzes
     * @param eingabevektor
     * @param zielvektor
     */
    public void trainiere(double[] eingabevektor, double[] zielvektor)
    {
        // Ausgabe der versteckten Schicht
        double[] ausgabeV = new double[anzahlVersteckteKnoten];
        ausgabeV = fktVonVektor(matrixMalVektor(gewichteEingangNachVersteckt,eingabevektor));
        // Endausgabe des Netzes
        double[] ausgabeA = new double[anzahlAusgabeKnoten];
        ausgabeA = fktVonVektor(matrixMalVektor(gewichteVerstecktNachAusgabe, ausgabeV));
        // Fehler Ausgabeschicht
        double[] afehler = new double[anzahlAusgabeKnoten];
        afehler = vektorPlusVektor(zielvektor, ZahlMalVektor(-1,ausgabeA));
        // Fehler versteckte Schicht
        double[] vfehler = new double[anzahlVersteckteKnoten];
        vfehler = matrixMalVektor(transponieren(gewichteVerstecktNachAusgabe),afehler);
        // Korrektur va
        double[] kVektor = new double[anzahlVersteckteKnoten];
        kVektor = korrekturVektor(lernrate, afehler, ausgabeA);
        for(int a = 0; a<anzahlAusgabeKnoten; a++)
        {
            for(int b = 0; b<anzahlVersteckteKnoten; b++)
            {
                gewichteVerstecktNachAusgabe[a][b] = gewichteVerstecktNachAusgabe[a][b] + kVektor[a]*ausgabeV[b];
            }
        }
        // Korrektur ev
        double[] korrVektor = new double[anzahlEingabeKnoten];
        korrVektor = korrekturVektor(lernrate, vfehler, ausgabeV);
        for(int a = 0; a<anzahlVersteckteKnoten; a++)
        {
            for(int b = 0; b<anzahlEingabeKnoten; b++)
            {
                gewichteEingangNachVersteckt[a][b] = gewichteEingangNachVersteckt[a][b] + korrVektor[a]*eingabevektor[b];
            }
        }
        /*System.out.println();
        System.out.println("Trainiert Gewichte Eingang -> Versteckt");
        for(int i = 0; i < anzahlVersteckteKnoten; i++)
        {   //System.out.println();
        for (int j = 0; j < anzahlEingabeKnoten; j++)
        {  
        System.out.print(ev[i][j]+"   "); 
        } 
        }
        System.out.println();
        System.out.println("Trainiert Gewichte Versteckt -> Ausgang"); 
        for(int k = 0; k < anzahlAusgabeKnoten; k++)
        {   System.out.println();
        for(int h = 0; h < anzahlVersteckteKnoten; h++)
        {   
        System.out.print(va[k][h]+"   ");
        }
        }
        System.out.println();*/
    }

    /** Backpropagating-Vektor bestimmen
     * @param r Lernrate für das Netz
     * @param fehler Fehlervektor
     * @param ausgabe alter Ausgabevektor
     */
    //################################################################
    private double[] korrekturVektor(double r, double[] fehler, double[] ausgabe)
    {
        //System.out.println("korrekturVektor:");
        double[] kVektor = new double[ausgabe.length];
        for(int k = 0; k<ausgabe.length; k++)
        {
            if (aktivierungsfunktion==0) {
                //Sigmoid
                kVektor[k] = r*fehler[k]*ausgabe[k]*(1-ausgabe[k]);
            } 
            else {
                //Sigmoid
                kVektor[k] = r*fehler[k]*ausgabe[k]*(1-ausgabe[k]);
            } 
            //System.out.print(kVektor[k]+"   ");
        }
        //System.out.println();
        return kVektor;

    }

    /**
     * Abfrage des Netzes
     * @param eingabevektor
     * @return ausgabevektor
     */
    private double[] eingabeBearbeiten(double[] eingabevektor)
    {
        double[] y = fktVonVektor(matrixMalVektor(gewichteEingangNachVersteckt,eingabevektor));
        double [] ausgabe = fktVonVektor(matrixMalVektor(gewichteVerstecktNachAusgabe, y));
        return ausgabe;
    }

    /**
     * Aktivierungsfunktion
     * @param x double-Argument
     * @return y 
     */

    private double aktivierungsfunktion(double x) {
        switch (aktivierungsfunktion) {
            case 0: return sigmoid(x);
            case 1: return cutoff(x);
            case 2: return relu(x);
            case 3: return leakyRelu(x);
            default: return sigmoid(x);
        }
    }

    private double sigmoid(double x)
    {
        return 1/(1+Math.exp(-x));
    }

    private double cutoff(double d) {
        if (d>=0) return 0.99;
        return 0.01;
    } 

    // ReLU rectified linear unit, experimentell
    private double relu(double d) {
        if (d>=0) return d;
        return 0;
    } 

    // ReLU rectified linear unit, experimentell
    private double leakyRelu(double d) {
        if (d>=0) return d;
        else return d*0.2;
    } 

    /** 
     * Aktivierungsfunktion auf Ausgabe-Vektor einer Schicht
     * anwenden.
     * @param vektor
     * @return ergvektor
     */
    private double[] fktVonVektor(double[] vektor)
    {
        double[] ergvektor = new double[vektor.length];

        for(int i = 0; i<vektor.length; i++)
        {
            ergvektor[i] = aktivierungsfunktion(vektor[i]);
        }
        return ergvektor;
    }

    /**
     * VektorPlusVektor
     * @param v1 Erster Vektor
     * @param v2 Zweiter Vektor
     * @return sum Summenvektor
     */
    private double[] vektorPlusVektor(double[] v1, double[] v2)
    {
        double[] sum = new double[v1.length];
        if(v1.length == v2.length)
        {
            for(int i = 0; i < v1.length; i++)
            {
                sum[i] = v1[i] + v2[i];
            }
        }
        return sum;
    }

    /**
     * ZahlMalVektor
     * @param v Vektor
     * @param z double-Zahl
     * @return erg Ergebnisvektor
     */
    private double[] ZahlMalVektor(double z, double[] v)
    {
        double[] erg = new double[v.length];
        for(int i = 0; i < v.length; i++)
        {
            erg[i] = z*v[i];
        }
        return erg;
    }

    /**
     * MatrixMalVektor
     * @param m Matrix
     * @param v Vektor
     * @return ergvektor
     */
    private double[] matrixMalVektor(double[][] m, double[] v)
    {
        double[] ergvektor = new double[m.length];
        for(int i = 0; i< m.length; i++)
        {
            ergvektor[i] = 0;
        }

        if(m[0].length == v.length)
        {   for(int k = 0; k< m.length; k++)
            {
                for(int h = 0; h<v.length; h++)
                {
                    ergvektor[k] += m[k][h]*v[h];  
                }
            }
        }
        return ergvektor;
    }

    /**
     * Transponierte Matrix
     * @param m Matrix
     * @return mt transponierte Matrix
     */
    private double[][] transponieren(double[][] m)
    {
        double[][] mt = new double[m[0].length][m.length];
        for(int i = 0; i < m[0].length; i++)
        {
            for(int j = 0; j < m.length; j++)
            {
                mt[i][j] = m[j][i];
            }
        }
        return mt;
    }

    //unbenutzt/manuell?
    /**
     * Matrix Multiplikation
     * 
     * @param m1 Matrix 1
     * @param m2 Matrix 2
     * @return ergebnismatrix
     */
    private  double[][] matrizenMult(double[][] m1, double[][] m2) {
        double[][] ergebnismatrix = null;

        if (m1[0].length == m2.length) {
            int zeilenm1 = m1.length;
            int spaltenm1 = m1[0].length;
            int spaltenm2 = m2[0].length;

            ergebnismatrix = new double[zeilenm1][spaltenm2];

            for (int i = 0; i < zeilenm1; i++) {
                for (int j = 0; j < spaltenm2; j++) {
                    ergebnismatrix[i][j] = 0;
                    for (int k = 0; k < spaltenm1; k++) {
                        ergebnismatrix[i][j] += m1[i][k] * m2[k][j];
                    }
                }
            }
        } else {
            int zeilen = m1.length;
            int spalten = m1[0].length;
            System.out.println("Matrizen passen nicht zusammen!");
            ergebnismatrix = new double[zeilen][spalten];
            for (int i = 0; i < m1.length; i++) {
                for (int j = 0; j < m1[0].length; j++) {
                    ergebnismatrix[i][j] = 0;
                }
            }
        }
        return ergebnismatrix;
    }

    // neue Methoden
    public int gibAnzahlEingang(){ return anzahlEingabeKnoten; }
    public int gibAnzahlHidden() { return anzahlVersteckteKnoten;}
    public int gibAnzahlAusgang() {return anzahlAusgabeKnoten;}
    public double gibLernrate(){ return lernrate; }
    public void setzeLernrate(double neuerWert) { lernrate = neuerWert; }
    public double [][][] gibGewichte() 
    {
        double [][][] erg = new double[2][][];
        erg[0] = gewichteEingangNachVersteckt;
        erg[1] = gewichteVerstecktNachAusgabe;
        return erg;
    }

    public boolean setzeGewichte(double [][][] gewichte) 
    {
        if(gewichte.length!=2) {
            System.out.println("Netz: *2* zweidimensionale Felder erwartet.");
            return false;
        }
        if(gewichte[0].length != gewichteEingangNachVersteckt.length) {
            System.out.println("Netz: Laenge von gewichteEingangNachVersteckt passt nicht.");
            return false;
        }
        if(gewichte[1].length != gewichteVerstecktNachAusgabe.length) {
            System.out.println("Netz: Laenge von gewichteVerstecktNachAusgabe passt nicht.");
            System.out.println("Netz: Erwartet "+gewichteVerstecktNachAusgabe.length+", erhalten "+gewichte[1].length);
            return false;
        }
        gewichteEingangNachVersteckt = gewichte[0];
        gewichteVerstecktNachAusgabe = gewichte[1];
        return true;
    }

    public double[] werteAus(double[] eingabevektor) {
        return eingabeBearbeiten(eingabevektor);
    }

    // gibt Gewichte aus    
    public void ausgabeGewichte(double [][][] belegung) 
    {
        double [][] eingang = belegung[0];
        double [][] ausgang = belegung[1];

        println("// Info: Belegung der Gewichtungen wird ausgegeben.");
        for (int schicht=0; schicht<belegung.length; schicht++)
        {
            println();
            print("// --- "+schicht+". ");
            if (schicht==0) {
                println("// Gewichtung von Eingangs- nach versteckter Schicht");
                println("// --- Anzahl Knoten in versteckter Schicht: "+belegung[0].length);
            }
            else if (schicht==1) {
                println("// Gewichtung von versteckter nach Ausgangs- Schicht");
                println("// --- Anzahl Knoten in Ausgangsschicht: "+belegung[1].length);
            }
            println();
            for (int nach=0; nach<belegung[schicht].length; nach++)
            {
                for (int von=0; von<belegung[schicht][nach].length; von++)
                {
                    print("Gewichtung Knoten "+nach+", Eingang "+von+": ");
                    //print("Gewichtung von "+von+" nach "+nach+": ");
                    print(belegung[schicht][nach][von]);
                    println(";");
                }
                println(""); //Knoten erledigt
            }
            print(""); // Schicht erledigt 
        }

        boolean gibCodeAusZumKopieren = false;
        if (gibCodeAusZumKopieren) {
            for (int schicht=0; schicht<belegung.length; schicht++)
            {

                for (int nach=0; nach<belegung[schicht].length; nach++)
                {
                    for (int von=0; von<belegung[schicht][nach].length; von++)
                    {
                        print("setzeGewichtung("+schicht+", "+nach+", "+von+", ");
                        print(belegung[schicht][nach][von]);
                        println(");");
                    }
                    println(""); //Knoten erledigt
                }
                print(""); // Schicht erledigt 
            }
        }


        println("// Info: ######################################");
        println();
    }

    private void println(String s) {
        System.out.println(s);
    }

    private void println() {
        System.out.println();
    }

    private void print(String s) {
        System.out.print(s);
    }

    private void print(double d) {
        System.out.print(d);
    }

}
