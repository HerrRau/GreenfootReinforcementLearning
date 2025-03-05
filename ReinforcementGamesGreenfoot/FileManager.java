import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.Insets;

public class FileManager
{
    static String praefixNeuronalesNetz = "";//"saveNN\\";

    public static void bildEinpassen(JButton button, String bildname) {
        button.setMargin(new Insets(0, 0, 0, 0));
        int x = button.getWidth();
        int y = button.getHeight();
        ImageIcon icon = createImageIcon(bildname);
        button.setIcon( resizeImageIcon(icon, x, y) );
        button.setPressedIcon( resizeImageIcon(icon, x-2, y-2) );
        button.setText("");
        button.setContentAreaFilled(false);
        // button.setBorderPainted(false);
        button.setBorder(null);

    }

    public static ImageIcon createImageIcon(String bildname) {
        bildname = URLHelper.pfadZumOberverzeichnis()+bildname;
        return new ImageIcon(bildname);
    }

    private static BufferedImage createFallbackImage() {
        return new BufferedImage(50,50,BufferedImage.TYPE_INT_ARGB); // kleines unsichtbares Bild als Standard
    }    

    public static Image resizeImage(Image image, int x, int y) {
        if (image == null) {
            System.out.println("Diese Bild existiert nicht, stattdessen wird ein leeres Bild erzeugt.");
            image = createFallbackImage();
        }
        Image newimg = image.getScaledInstance(x, y, java.awt.Image.SCALE_SMOOTH); // alternativ: SCALE_FAST 
        return newimg;
    }

    public static ImageIcon resizeImageIcon(ImageIcon icon, int x, int y) {
        if (icon == null) icon = new ImageIcon(createFallbackImage());
        Image image = icon.getImage();
        image = resizeImage(image, x, y);
        return new ImageIcon(image);
    }

    public static void speicherePNG(BufferedImage image, String filename) {
        filename = URLHelper.pfadZumOberverzeichnis()+filename;
        try {            
            ImageIO.write(image, "png", new File(filename));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * fuer Perzeptron
     */

    public static boolean existiertDatei(String filename) {
        filename = URLHelper.pfadZumOberverzeichnis()+filename;
        try {
            FileReader fr = new FileReader(filename);
            return true;
        }
        catch (Exception e)
        {
            // System.out.println("FILEMANAGER: DATEI NICHT GEFUNDEN");
            // System.out.println(e);
            return false;
        }
    }

    
    public static double[][][] ladeEinstellungen(String filename, int anzahlEbenen, int[][]funktion, double[][]bias, String [][] labels)
    {
        filename = URLHelper.pfadZumOberverzeichnis()+filename;

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br=new BufferedReader(fr);

            double [][][] gewichte = new double [anzahlEbenen][][];
            //erste Zeile/Ebene
            int eingangFile = Integer.parseInt(br.readLine().split(": ")[1]);
            //weitere Zeilen/Ebenen
            for (int e=0; e<anzahlEbenen-1; e++) {
                String s = br.readLine().split(": ")[1];        
                gewichte[e] = new double [Integer.parseInt(s)][];
            }
            //letzte Zeile/Ebene
            gewichte[anzahlEbenen-1] = new double [Integer.parseInt(br.readLine().split(": ")[1])][];

            // if (eingangFile!=bias[0].length || anzahlVersteckteEbenen hiddenFile!=hidden || ausgangFile!=ausgang) {
            // System.out.println("Gewichte: Fehler - Anzahl der Knoten passt nicht zu gespeicherten Daten");
            // return null;
            // }

            // lese aktivierungsfunktion und bias
            for (int e=0; e<bias.length; e++) {
                for (int k=0; k<bias[e].length; k++) {
                    funktion[e][k] = Integer.parseInt(br.readLine().split(": ")[1]);
                    bias[e][k] = Double.parseDouble(br.readLine().split(": ")[1]);
                    String [] t = br.readLine().split(": ");
                    if (t.length==1) labels[e][k] = "";
                    else labels[e][k] = t[1];
                }
            }

            //lese gewichtungen
            for (int e=0; e<gewichte.length; e++) {
                for (int k=0; k<gewichte[e].length; k++) {
                    if (e==0) gewichte [e][k] = new double [eingangFile];
                    else gewichte [e][k] = new double [gewichte[e-1].length];                    
                    for (int i=0; i<gewichte[e][k].length; i++) {
                        gewichte[e][k][i] = Double.parseDouble(br.readLine().split(": ")[1]);
                    }

                }
            }

            fr.close();
            return gewichte;
        }
        catch (Exception e)
        {
            System.out.println("FILEMANAGER: DATEI NICHT GEFUNDEN oder PARSE-PROBLEM");
            e.printStackTrace();
            System.out.println(e);
            return null;
        }    
    }

    public static boolean speichereEinstellungen(String filename, double [][][] gewichte, double[][] biases, int[][]aktivierungsfunktionen) {
        boolean hatFunktioniert = true;
        filename = URLHelper.pfadZumOberverzeichnis()+filename;
        try {
            PrintWriter pr = new PrintWriter(filename);    

            //Knoten Info
            pr.println("Eingaenge: "+gewichte[0][0].length);
            for (int e=0; e<gewichte.length-1; e++) {
                pr.println("Versteckte Ebenen "+e+": "+gewichte[e].length);
            }
            pr.println("Ausgaenge: "+gewichte[gewichte.length-1].length);

            //biases und aktivierungsfunktionen
            for (int e=0; e<biases.length; e++) {
                for (int k=0; k<biases[e].length; k++) {
                    pr.print("Knoten Ebene "+e+" Nummer "+k+" Typ: ");
                    pr.print(aktivierungsfunktionen[e][k]);
                    pr.println();
                    pr.print("Knoten Ebene "+e+" Nummer "+k+" Bias: ");
                    pr.print(biases[e][k]);
                    pr.println();
                }
            }

            //gewichte
            for (int e=0; e<gewichte.length; e++) { //ebenen
                for (int k=0; k<gewichte[e].length; k++) { //knoten
                    for (int i=0; i<gewichte[e][k].length; i++) { //eingaenge
                        pr.print("Gewichtung Ebene "+e+", Knoten "+k+", Eingang "+i+": ");
                        pr.print(gewichte[e][k][i]);
                        pr.println();
                    }
                }
            }

            //ende
            pr.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            hatFunktioniert = false;
        }
        return hatFunktioniert;
    }

    public static boolean speichereEinstellungen(String filename, double [][][] gewichte, double[][] biases, int[][]aktivierungsfunktionen, String [][] labels) {
        boolean hatFunktioniert = true;
        filename = URLHelper.pfadZumOberverzeichnis()+filename;
        try {
            PrintWriter pr = new PrintWriter(filename);    

            //Knoten Info
            pr.println("Eingaenge: "+gewichte[0][0].length);
            for (int e=0; e<gewichte.length-1; e++) {
                pr.println("Versteckte Ebenen "+e+": "+gewichte[e].length);
            }
            pr.println("Ausgaenge: "+gewichte[gewichte.length-1].length);

            //biases und aktivierungsfunktionen
            for (int e=0; e<biases.length; e++) {
                for (int k=0; k<biases[e].length; k++) {
                    pr.print("Knoten Ebene "+e+" Nummer "+k+" Typ: ");
                    pr.print(aktivierungsfunktionen[e][k]);
                    pr.println();
                    pr.print("Knoten Ebene "+e+" Nummer "+k+" Bias: ");
                    pr.print(biases[e][k]);
                    pr.println();
                    labels[e][k].replace(':', '=');
                    pr.print("Knoten Ebene "+e+" Nummer "+k+" Beschriftung: ");
                    pr.print(labels[e][k]);
                    pr.println();
                }
            }

            //gewichte
            for (int e=0; e<gewichte.length; e++) { //ebenen
                for (int k=0; k<gewichte[e].length; k++) { //knoten
                    for (int i=0; i<gewichte[e][k].length; i++) { //eingaenge
                        pr.print("Gewichtung Ebene "+e+", Knoten "+k+", Eingang "+i+": ");
                        pr.print(gewichte[e][k][i]);
                        pr.println();
                    }
                }
            }

            //ende
            pr.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            hatFunktioniert = false;
        }
        return hatFunktioniert;
    }

    /**
     * fuer Neuronales Netz
     */

    public static double[][][] ladeGewichte(String filename, int eingang, int hidden, int ausgang)
    {
        filename = praefixNeuronalesNetz+filename;
        filename = URLHelper.pfadZumOberverzeichnis()+filename;
        double[][][] gewichte;
        gewichte = new double [2][][];
        gewichte[0] = new double[hidden][eingang];
        gewichte[1] = new double[ausgang][hidden];

        try
        {
            FileReader fr = new FileReader(filename);
            BufferedReader br=new BufferedReader(fr);

            int eingangFile = Integer.parseInt(br.readLine().split(": ")[1]);
            int hiddenFile =  Integer.parseInt(br.readLine().split(": ")[1]);
            int ausgangFile = Integer.parseInt(br.readLine().split(": ")[1]);

            if (eingangFile!=eingang || hiddenFile!=hidden || ausgangFile!=ausgang) {
                System.out.println("Gewichte: Fehler - Anzahl der Knoten passt nicht zu gespeicherten Daten");
                return null;
            }

            for (int j=0; j<hidden; j++) {
                for (int k=0; k<eingang; k++) {
                    String s = br.readLine();
                    //System.out.println(s);
                    if (s==null) { System.out.println("Gewichte: Fehler"); break; }
                    s = s.split(": ")[1];
                    gewichte[0][j][k] = Double.parseDouble(s);
                }
            }
            for (int j=0; j<ausgang; j++) {
                for (int k=0; k<hidden; k++) {
                    String s = br.readLine();
                    //System.out.println(s);
                    if (s==null) { System.out.println("Gewichte: Fehler"); break; }
                    s = s.split(": ")[1];                    
                    gewichte[1][j][k] = Double.parseDouble(s);
                }
            }
            fr.close();
            return gewichte;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }

    public static void speichereGewichte(String filename, int eingang, int hidden, int ausgang, double[][][] gewichte) {
        filename = praefixNeuronalesNetz+filename;
        filename = URLHelper.pfadZumOberverzeichnis()+filename;
        double [][] gewichteEingangNachVersteckt = gewichte[0];
        double [][] gewichteVerstecktNachAusgabe = gewichte[1];
        try
        {
            PrintWriter pr = new PrintWriter(filename);    

            pr.println("Eingaenge: "+eingang);
            pr.println("Versteckte Knoten: "+hidden);
            pr.println("Ausgaenge: "+ausgang);

            for (int j=0; j<gewichteEingangNachVersteckt.length; j++) {
                for (int k=0; k<gewichteEingangNachVersteckt[j].length ; k++) {
                    pr.print("Gewichtung Eingang "+k+" nach Hidden "+j+": ");
                    pr.println(gewichteEingangNachVersteckt[j][k]);
                }
            }
            for (int j=0; j<gewichteVerstecktNachAusgabe.length; j++) {
                for (int k=0; k<gewichteVerstecktNachAusgabe[j].length ; k++) {
                    pr.print("Gewichtung Hidden "+k+" nach Ausgang "+j+": ");
                    pr.println(gewichteVerstecktNachAusgabe[j][k]);
                }
            }
            pr.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    // public static double[][][] createArraysFromCSV(String filename, int inputLength, int outputLength, boolean withHeading, int number) {
    // InputStream stream = new URLHelper().getInputStream(filename); //immer absolut zu oberstem Verzeichnis
    // BufferedReader in = new BufferedReader(new InputStreamReader(stream));
    // String inputLine;
    // int numberOfLines = 0;
    // double[][][]array = new double[2][number][];
    // try {
    // while ((inputLine = in.readLine()) != null && numberOfLines<number) {
    // if (numberOfLines==0 && withHeading) { 
    // withHeading = false;
    // continue; 
    // }

    // String [] items = inputLine.split(",");
    // array[0][numberOfLines] = new double [inputLength];
    // array[1][numberOfLines] = new double [outputLength];
    // for (int i=0;i<inputLength;i++) {
    // double d = Double.parseDouble(items[i]);
    // array[0][numberOfLines][i] = d;
    // }
    // for (int i=0;i<outputLength;i++) {
    // double d = Double.parseDouble(items[inputLength+i]);
    // array[1][numberOfLines][i] = d;
    // }

    // numberOfLines++;
    // }
    // in.close();                
    // }
    // catch (Exception e) {
    // System.out.println(e);
    // }
    // return array;
    // }

    public static int getNumberOfLines(String filename) {
        InputStream stream = new URLHelper().getInputStream(filename); //immer absolut zu oberstem Verzeichnis - nicht zu Projekt!
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String inputLine;
        int numberOfLines = 0;
        try {
            while ((in.readLine()) != null) {
                numberOfLines++;
            }
            in.close();                
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return numberOfLines;

    }

    public static double[][][] createArraysFromCSV(String filename, int inputLength, int outputLength, 
    boolean solutionFirst, boolean withHeading, int number) {
        // InputStream stream = new URLHelper().getInputStream(filename); //immer absolut zu oberstem Verzeichnis - nicht zu Projekt!
        // System.out.println("FileManager: stream = "+stream);        
        // BufferedReader in = new BufferedReader(new InputStreamReader(stream));

        filename = URLHelper.pfadZumOberverzeichnis()+filename;

        BufferedReader in = null;
        try {
            in = 
                new BufferedReader(
                    new FileReader(
                        new File(filename)
                    )
                );
        } 
        catch (Exception e) {
            System.out.println("Filemanager: "+e);
        }

        String inputLine;
        int numberOfLines = 0;
        double[][][]array = new double[2][number][];
        try {
            while ((inputLine = in.readLine()) != null && numberOfLines<number) {
                if (numberOfLines==0 && withHeading) { 
                    withHeading = false;
                    continue; 
                }

                String [] items = inputLine.split(",");
                array[0][numberOfLines] = new double [inputLength];
                array[1][numberOfLines] = new double [outputLength]; 
                if (solutionFirst) {
                    for (int i=0;i<outputLength;i++) {
                        double d = Double.parseDouble(items[i]);
                        array[1][numberOfLines][i] = d;
                    }
                    for (int i=0;i<inputLength;i++) {
                        double d = Double.parseDouble(items[outputLength+i]);
                        array[0][numberOfLines][i] = d;
                    }
                } else {
                    for (int i=0;i<inputLength;i++) {
                        double d = Double.parseDouble(items[i]);
                        array[0][numberOfLines][i] = d;
                    }
                    for (int i=0;i<outputLength;i++) {
                        double d = Double.parseDouble(items[inputLength+i]);
                        array[1][numberOfLines][i] = d;
                    }
                }

                numberOfLines++;
            }
            in.close();                
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return array;
    }

}