import java.net.URL;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class URLHelper
{
    /*
     * ************************************* URL-Methoden *************************************
     */
    //relative
    URL getURLNonstatic(Object o, String filename) {
        return o.getClass().getResource(filename);
    } 

    //absolute
    URL getURLNonstatic(String filename) { 
        return this.getClass().getClassLoader().getResource(filename); 
    }

    public static URL getURL(Object o, String filename) {
        return getURLRelative(o, filename);
    }    

    private static URL getURLRelative(Object o, String filename) {
        //Class cls = o.getClass().getEnclosingClass();
        Class cls = o.getClass();
        return cls.getResource(filename);
    }

    public static URL getURL(String filename) {
        return getURLAbsolute(filename);
    }

    private static URL getURLAbsolute(String filename) {
        //         Class cls = new Object() {}.getClass();
        //         return cls.getResource(filename);

        return new Object(){}.getClass().getClassLoader().getResource(filename);
    }    

    /*
     * ************************************* Input-Stream_Methoden *************************************
     */
    static InputStream getInputStream(String filename) {
        return getInputStreamAbsolute(filename);
    }

    static InputStream getInputStream(Object o, String filename) {
        return getInputStreamRelative(o, filename);
    }

    private static InputStream getInputStreamAbsolute(String filename) {
        InputStream is = null;
        try {
            Class cls = new Object() { }.getClass();
            ClassLoader cLoader = cls.getClassLoader();
            is =  cLoader.getResourceAsStream(filename);
        } catch (Exception e) {
            System.out.println(e);
        }
        if (is==null) {
            System.out.println("URLHelper: Fehler1!!!");
            System.out.println("URLHelper: filename = "+filename);
        }
        return is;
    }

    private static InputStream getInputStreamRelative(Object o, String filename) {
        InputStream is = null;
        try {
            //             Class cls = new Object() { }.getClass();
            Class cls = o.getClass();
            is =  cls.getResourceAsStream(filename);
        } catch (Exception e) {
            System.out.println(e);
        }
        if (is==null) System.out.println("URLHelper: Fehler2!!!");
        return is;
    }

    public InputStream getInputStreamNonstaticRelative(Object o, String filename) {
        //Class currentClass = o.getClass().getEnclosingClass(); //macht manchmal Probleme
        Class currentClass = o.getClass();
        InputStream resource = currentClass.getResourceAsStream(filename);
        return resource;
    }

    /*
     * ************************************* Buffered-Stream-Methoden *************************************
     */
    //absolute
    static BufferedReader getBufferedReaderFromFileName(String filename) {
        InputStream stream = URLHelper.getInputStream(filename);
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        return in;
    }

    //relative
    static BufferedReader getBufferedReaderFromFileName(Object o, String filename) {
        InputStream stream = URLHelper.getInputStream(o, filename);
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        return in;
    }  

    /*
     * ************************************* Pfadgeber-Methoden *************************************
     */
    /*
     * To do:
     * Als originaer statische Methode implementieren
     */
    /**
     * Diese Methode gibt den absoluten Pfad zum Oberverzeichnis des aktuellen BlueJ-Projekts zurueck.
     * Funktioniert auch in Packages, gilt jeweils fuer die oberste Ebene,
     * Zunaechst wird der Pfad zum aktuellen Verzeichnis ermittelt, aber *ohne* das Verzeichnis selbst.
     * Wenn sich die .class-Datei also im Order "C:\Projekte BlueJ\Projekt42\" befindet, gibt die Methode
     * "C:\Projekte BlueJ" zurueck, plus einem angehaengten "/" ("\\" ginge auch)
     * (Dabei wird in einem Zwischenschritt der Pfad "C:\Projekte%20BlueJ" ermittelt und dann in UTF 8 umgeformt.)
     * 
     * @return der Pfad zum Verzeichnis, in dem diese .class-Datei liegt, ohne dieses Verzeichnis selbst!
     */
    //http://stackoverflow.com/questions/16076911/how-to-find-absolute-path-from-a-relative-path-in-file-system
    public String pfadZumOberverzeichnisNonstatic(){
        //         java.security.ProtectionDomain pd = getClass().getProtectionDomain(); //        java.security.ProtectionDomain pd =DBVerbindung.class.getProtectionDomain();
        java.security.ProtectionDomain pd = getClass().getProtectionDomain(); //        java.security.ProtectionDomain pd =DBVerbindung.class.getProtectionDomain();
        if ( pd == null ) return null;
        java.security.CodeSource cs = pd.getCodeSource();
        if ( cs == null ) return null;
        java.net.URL url = cs.getLocation();
        if ( url == null ) return null;
        java.io.File file = new java.io.File( url.getFile() );
        if (file == null) return null;

        String pfad = "";
        try {
            pfad = file.getParentFile().getAbsolutePath();         // Ermitteln des Pfades zum Oberverzeichnis
            pfad = java.net.URLDecoder.decode(pfad, "utf-8");   // Rueckverwandeln von als "%20" dargestellten evtl. Leerzeichen und anderen Sonderzeichen
        } catch (Exception e) { }
        // System.out.println("\nAkt. Verzeichnis: "+System.getProperty("user.dir"));
        // System.out.println("Absoluter Pfad:  " +pfad);
        return pfad + "/";
        //wenn stattdessen "" zurueckgegeben wird, handelt es sich um einen rein relativen Pfad
        //wenn stattdessen zurueckgegeben wird "J:/TdI 2013/DEMO_Tester" + "/", handelt es sich um einen absoluten Pfad
    }

    public static String pfadZumOberverzeichnis(){
        URLHelper u = new URLHelper();
        return u.pfadZumOberverzeichnisNonstatic();
    }

    //won't work with jar! try using inputStream instead
    public static String pfadZumVerzeichnis(){
        URLHelper u = new URLHelper();
        return u.pfadZumVerzeichnisNonstatic();
    }

    //won't work with jar! try using inputStream instead
    public String pfadZumVerzeichnisNonstatic(){
        //         java.security.ProtectionDomain pd = getClass().getProtectionDomain(); //        java.security.ProtectionDomain pd =DBVerbindung.class.getProtectionDomain();
        java.security.ProtectionDomain pd = getClass().getProtectionDomain(); //        java.security.ProtectionDomain pd =DBVerbindung.class.getProtectionDomain();
        if ( pd == null ) return null;
        java.security.CodeSource cs = pd.getCodeSource();
        if ( cs == null ) return null;
        java.net.URL url = cs.getLocation();
        if ( url == null ) return null;
        java.io.File file = new java.io.File( url.getFile() );
        if (file == null) return null;

        String pfad = "";
        try {
            //             pfad = file.getParentFile().getAbsolutePath();         // Ermitteln des Pfades zum Oberverzeichnis
            pfad = file.getAbsolutePath();         // Ermitteln des Pfades zum Oberverzeichnis
            pfad = java.net.URLDecoder.decode(pfad, "utf-8");   // Rueckverwandeln von als "%20" dargestellten evtl. Leerzeichen und anderen Sonderzeichen
        } catch (Exception e) { }
        // System.out.println("\nAkt. Verzeichnis: "+System.getProperty("user.dir"));
        // System.out.println("Absoluter Pfad:  " +pfad);
        return pfad + "/";
        //wenn stattdessen "" zurueckgegeben wird, handelt es sich um einen rein relativen Pfad
        //wenn stattdessen zurueckgegeben wird "J:/TdI 2013/DEMO_Tester" + "/", handelt es sich um einen absoluten Pfad
    }
}
