/* Είναι μια κλάση που αναπαριστά το μενού που έχουμε στο βασικό μας GameScreen */
import javax.swing.*;
public class MenuGameScreen extends JMenuBar {
    private  JMenu menu; // Μενού
    private  JMenu tools; // Εργαλεία
    // Μενού
    protected   JMenuItem new_game; // Έναρξη ενός παιχνιδιού
    protected  JMenuItem cancel_end_game; // Ακύρωση/Τερματισμός παιχνιδιού
    protected  JMenuItem add_player_info; // Εισαγωγή στοιχείων του χρήστη
    protected  JMenuItem help_settings; // Ρυθμίσεις βοηθειών
    protected  JMenuItem search_word; // Αναζήτηση αρχείου λέξεων (.txt) στο κατάλογο με χρήση JFileChooser
    protected  JMenuItem exit_game; // Έξοδος από το παιχνίδι
    // Εργαλεία
    protected  JMenuItem help; // Βοήθεια
    protected  JMenuItem about; // Ενημέρωση (about) για τα στοιχεία των μελών της ομάδας που υλοποίησαν την εφαρμογή
    public MenuGameScreen ()
    {
        menu = new JMenu("Μενού");
        tools = new JMenu("Εργαλεία");
        // Μενού
        new_game = new JMenuItem("Νέο παιχνίδι");
        cancel_end_game = new JMenuItem("Ακύρωση/Τερματισμός παιχνιδιού");
        add_player_info = new JMenuItem("Εισαγωγή στοιχείων του χρήστη");
        help_settings = new JMenuItem("Ρυθμίσεις βοηθειών");
        search_word = new JMenuItem("Αναζήτηση αρχείου λέξεων");
        exit_game = new JMenuItem("Έξοδος");
        // Εργαλεία
        help = new JMenuItem("Βοήθεια");
        about = new JMenuItem("About...");
        // Μενού εισαγωγή
        menu.add(new_game);
        menu.add(cancel_end_game);
        menu.add(add_player_info);
        menu.add(help_settings);
        //menu.add(search_word);
        menu.add(exit_game);
        // Εργαλεία εισαγωγή
        tools.add(help);
        tools.add(about);
        this.add(menu);
        this.add(tools);
    }
    protected void disableGameMenu() {
        cancel_end_game.setEnabled(false);
        add_player_info.setEnabled(false);
        help_settings.setEnabled(false);
        search_word.setEnabled(false);
    }
}
