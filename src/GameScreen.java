/* Η ΒΑΣΙΚΟΤΕΡΗ ΚΛΑΣΗ ΤΟΥ ΠΑΙΧΝΙΔΙΟΥ ΕΔΩ ΔΗΜΙΟΥΡΓΗΤΑΙ ΤΟ ΤΑΜΠΛΟ ΤΗΣ ΕΦΑΡΜΟΓΗΣ ΚΑΙ ΠΟΛΛΑ ΑΛΛΑ */
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GameScreen extends JFrame implements ActionListener, MouseListener {

    Player player;
    HelpSettings helpSettings;
    private final int WINDOW_WIDTH = 1020, WINDOW_HEIGHT = 700;
    private int goal; // Ο στόχος που θα πρέπει αν φτάσει ο παίκτης ...
    private static int total_points; // Συνολική Βαθμολογία
    private static int word_points; // Βαθμολογία λέξης
    private int words_found; // Λέξεις που βρέθηκαν
    private final Border border = BorderFactory.createLineBorder(Color.BLACK, 5); // περίγραμμα για τα JPanels


    private static int deleteLineTries_COUNTER;
    private static int reorderLineTries_COUNTER;
    private static int reorderColumnTries_COUNTER;
    private static int reorderTableTries_COUNTER;
    private static int exchangeLettersTries_COUNTER;
    // Πίνακας με τα γράμματα (κουμπιά) που έχει κάνει επιλογή ο χρήστης
    private final ArrayList <ButtonGameScreen> SELECTED_BUTT = new ArrayList<>();

    // Οι τυχαία επιλεγμένες λέξεις απο τη λίστα GAME_WORDS θα μπαίνουν εδώ, είναι αυτές που τηρούν τις προϋποθέσεις...
    ArrayList <String> randWords = new ArrayList<>();

    // Σε αυτήν τη λίστα θα βάλω τις λέξεις που θα διαβάσω από το αρχείο
    private final ArrayList <String> GAME_WORDS = new ArrayList<>();

    // JPanels
    private JPanel LEFT_JPANEL; // Αυτό το JPanel θα περιέχει το βασικό πίνακα με τα γράμματα και το κουμπί του ελέγχου λέξεις
    private final JPanel LEFT_UP_JPANEL; // Αυτό το JPanel Θα έχει το πίνακα του παιχνιδιού (8x8)
    private static final int LEFT_UP_JPANEL_ROWS = 8;
    private static final int LEFT_UP_JPANEL_COLUMNS = 8;
    private static ButtonGameScreen [][]arrayOfGame; // lines --> 8 and columns --> 8

    private JPanel RIGHT_PANEL;
    private JPanel RIGHT_PANEL_UP_1; // Αυτό το JPanel θα περιέχει τις βοήθειες
    private JPanel RIGHT_PANEL_UP_2;
    private JPanel RIGHT_PANEL_DOWN;


    // Buttons
    private JButton CHECK_WORD;
    private JButton DELETE_LINE;
    private JButton REORDER_LINE;
    private JButton REORDER_COLUMN;
    private JButton REORDER_TABLE;
    private JButton EXCHANGE_LETTERS;

    // JLabels
    private JLabel STATUS_MOVEMENT; // Μνήμα λάθους άμα ο χρήστης επιλέξει λάθος γραμμα...
    private JLabel DELETE_LINE_label;
    private JLabel REORDER_LINE_label;
    private JLabel REORDER_COLUMN_label;
    private JLabel REORDER_TABLE_label;
    private JLabel EXCHANGE_LETTERS_label;
    private JLabel GOAL_label_1;
    private JLabel GOAL_label_2;
    private JLabel TOTAL_POINTS_label_1;
    private JLabel TOTAL_POINTS_label_2;
    private JLabel WORD_POINTS_label_1;
    private JLabel WORD_POINTS_label_2;
    private JLabel WORDS_FOUND_label_1;
    private final JLabel WORDS_FOUND_label_2;

    // JText Fields
    private final JTextField DELETE_LINE_text;
    private final JTextField REORDER_LINE_text;
    private final JTextField REORDER_COLUMN_text;

    /// Exchange Functions
    private int state = 0; // Όταν είναι 1 σημαίνει ότι το ταμπλό μπαίνει σε κατάσταση Exchange
    private final ArrayList<String> excha_rand_butt = new ArrayList<>(); // χρησιμοποιείται όταν έχουμε επιλεγμένα κουμπιά μπαλαντέρ, στην αρχή τα κάνουμε un-select τα κουμπιά άρα πρέπει κάπως να κρατάμε τα letter που έχει επιλέξει ο χρήστης στα κουμπιά μπαλαντέρ (άμα προφανώς έχει επιλέξει)
    private final ArrayList <ButtonGameScreen> EXCHANGE_WORDS_LETTERS = new ArrayList<>();

    /// Menu
    MenuGameScreen menuGameScreen;
    private MenuHandler handler;

    public GameScreen(int goal)
    {
        player = new Player(); // καλούμε τον constrctor της κλασης παίκτη
        helpSettings = new HelpSettings(6,6,6,10,10); // Αυτές είναι οι default τιμές ...

        /* Σε κάθε ξεκίνημα του παιχνιδιού θα μηδενίζονται */
        this.deleteLineTries_COUNTER = 0;
        this.reorderLineTries_COUNTER = 0;
        this.reorderColumnTries_COUNTER = 0;
        this.reorderTableTries_COUNTER = 0;
        this.exchangeLettersTries_COUNTER = 0;

        this.goal = goal;
        this.total_points = 0; // τα αρχικοποιούμε με 0
        this.word_points = 0; // ...
        this.words_found = 0; // ...

        arrayOfGame = new ButtonGameScreen[LEFT_UP_JPANEL_ROWS][LEFT_UP_JPANEL_COLUMNS];

        new ButtonGameScreen(); // αυτό μπήκε για να διαβαστεί το αρχείο με τους πόντους του κάθε γράμματος...

        /////
        ImageIcon imgIcon = new ImageIcon("./resources/logo.png"); // icon για το παράθυρο ...
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setTitle("Letters Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setUndecorated(false);
        this.setLayout(new GridLayout(1, 2));
        this.setIconImage(imgIcon.getImage());

        /// Buttons
        CHECK_WORD = new JButton("Έλεγχος Λέξης");
        CHECK_WORD.setBorder(border);
        CHECK_WORD.setFont(new Font("Serif", Font.BOLD, 25));
        CHECK_WORD.setFocusable(false);
        CHECK_WORD.setBounds(158, 480, 200, 100);
        CHECK_WORD.addActionListener(this);

        DELETE_LINE = new JButton("Διαγραφή Γραμμής");
        setHelpButtons(DELETE_LINE);
        REORDER_LINE = new JButton("Αναδιάταξη Γραμμής");
        setHelpButtons(REORDER_LINE);
        REORDER_COLUMN = new JButton("Αναδιάταξη στήλης");
        setHelpButtons(REORDER_COLUMN);
        REORDER_TABLE = new JButton("Αναδιάταξη ταμπλό");
        setHelpButtons(REORDER_TABLE);
        REORDER_TABLE.setFont(new Font("Serif", Font.BOLD, 25));
        EXCHANGE_LETTERS = new JButton("Εναλλαγή γραμμάτων");
        setHelpButtons(EXCHANGE_LETTERS);
        EXCHANGE_LETTERS.setFont(new Font("Serif", Font.BOLD, 23));

        /// JTextFields
        DELETE_LINE_text = new JTextField();
        setHelpTextFeilds(DELETE_LINE_text);
        REORDER_LINE_text = new JTextField();
        setHelpTextFeilds(REORDER_LINE_text);
        REORDER_COLUMN_text =  new JTextField();
        setHelpTextFeilds(REORDER_COLUMN_text);

        /// JLabel
        STATUS_MOVEMENT = new JLabel();
        setJLabelStatus("ΚΑΛΩΣ ΗΡΘΕΣ " + player.name + "!!", Color.GREEN);
        DELETE_LINE_label = new JLabel();
        setHelpLabels(DELETE_LINE_label, helpSettings.deleteLineTries);
        REORDER_LINE_label = new JLabel();
        setHelpLabels(REORDER_LINE_label, helpSettings.reorderLineTries);
        REORDER_COLUMN_label = new JLabel();
        setHelpLabels(REORDER_COLUMN_label, helpSettings.reorderColumnTries);
        REORDER_TABLE_label = new JLabel();
        setHelpLabels(REORDER_TABLE_label, helpSettings.reorderTableTries);
        REORDER_TABLE_label.setFont(new Font("Serif", Font.BOLD, 50));
        EXCHANGE_LETTERS_label = new JLabel();
        setHelpLabels(EXCHANGE_LETTERS_label, helpSettings.exchangeLettersTries);
        EXCHANGE_LETTERS_label.setFont(new Font("Serif", Font.BOLD, 50));

        GOAL_label_1 = new JLabel();
        setHelpDownLabels(GOAL_label_1, "Στόχος");
        GOAL_label_2 = new JLabel(); // Ο στόχος που είναι παράμετρο στον constructor το περνάμε και στο Label ...
        setHelpDownLabels(GOAL_label_2, String.valueOf(goal));

        TOTAL_POINTS_label_1 = new JLabel();
        setHelpDownLabels(TOTAL_POINTS_label_1, "Συνολική Βαθμολογία");
        TOTAL_POINTS_label_2 = new JLabel();
        setHelpDownLabels(TOTAL_POINTS_label_2, String.valueOf(total_points));

        WORD_POINTS_label_1 = new JLabel();
        setHelpDownLabels(WORD_POINTS_label_1, "Βαθμολογία Λέξης");
        WORD_POINTS_label_2 = new JLabel();
        setHelpDownLabels(WORD_POINTS_label_2, String.valueOf(word_points));

        WORDS_FOUND_label_1 = new JLabel();
        setHelpDownLabels(WORDS_FOUND_label_1, "Λέξεις που βρέθηκαν");
        WORDS_FOUND_label_2 = new JLabel();
        setHelpDownLabels(WORDS_FOUND_label_2, String.valueOf(words_found));

        /// LEFT JPanel
        LEFT_JPANEL = new JPanel();
        LEFT_JPANEL.setBackground(Color.darkGray);
        LEFT_JPANEL.setLayout(null);
        LEFT_UP_JPANEL = new JPanel();
        LEFT_UP_JPANEL.setBorder(border);
        LEFT_UP_JPANEL.setLayout(new GridLayout(LEFT_UP_JPANEL_ROWS, LEFT_UP_JPANEL_COLUMNS)); // θα έχει Grid Layout 8x8
        LEFT_UP_JPANEL.setBounds(0, 0, 500, 350);
        LEFT_UP_JPANEL.setBackground(Color.darkGray);

        LEFT_JPANEL.add(LEFT_UP_JPANEL);
        LEFT_JPANEL.add(CHECK_WORD);
        LEFT_JPANEL.add(STATUS_MOVEMENT);

        /// RIGHT JPanel (Για τις βοήθειες)
        RIGHT_PANEL = new JPanel();
        RIGHT_PANEL.setLayout(new GridLayout(3, 1,20,20)); // 3 γραμμές --> RIGHT_PANEL_UP_1 και RIGHT_PANEL_UP_2 και RIGHT_PANEL_DOWN ...
        RIGHT_PANEL.setBackground(Color.darkGray);
        RIGHT_PANEL.setBorder(border);

        /////

        RIGHT_PANEL_UP_1 = new JPanel();
        RIGHT_PANEL_UP_1.setLayout(new GridLayout(3, 3,20,20));
        RIGHT_PANEL_UP_1.setBackground(Color.darkGray);

        RIGHT_PANEL_UP_1.add(DELETE_LINE);
        RIGHT_PANEL_UP_1.add(DELETE_LINE_text);
        RIGHT_PANEL_UP_1.add(DELETE_LINE_label);

        RIGHT_PANEL_UP_1.add(REORDER_LINE);
        RIGHT_PANEL_UP_1.add(REORDER_LINE_text);
        RIGHT_PANEL_UP_1.add(REORDER_LINE_label);

        RIGHT_PANEL_UP_1.add(REORDER_COLUMN);
        RIGHT_PANEL_UP_1.add(REORDER_COLUMN_text);
        RIGHT_PANEL_UP_1.add(REORDER_COLUMN_label);


        /////

        RIGHT_PANEL_UP_2 = new JPanel();
        RIGHT_PANEL_UP_2.setLayout(new GridLayout(2, 2,20,20));
        RIGHT_PANEL_UP_2.setBackground(Color.darkGray);

        RIGHT_PANEL_UP_2.add(REORDER_TABLE);
        RIGHT_PANEL_UP_2.add(REORDER_TABLE_label);

        RIGHT_PANEL_UP_2.add(EXCHANGE_LETTERS);
        RIGHT_PANEL_UP_2.add(EXCHANGE_LETTERS_label);

        /////

        RIGHT_PANEL_DOWN = new JPanel();
        RIGHT_PANEL_DOWN.setBackground(Color.darkGray);
        RIGHT_PANEL_DOWN.setLayout(new GridLayout(5, 2));

        RIGHT_PANEL_DOWN.add(GOAL_label_1);
        RIGHT_PANEL_DOWN.add(GOAL_label_2);

        RIGHT_PANEL_DOWN.add(TOTAL_POINTS_label_1);
        RIGHT_PANEL_DOWN.add(TOTAL_POINTS_label_2);

        RIGHT_PANEL_DOWN.add(WORD_POINTS_label_1);
        RIGHT_PANEL_DOWN.add(WORD_POINTS_label_2);

        RIGHT_PANEL_DOWN.add(WORDS_FOUND_label_1);
        RIGHT_PANEL_DOWN.add(WORDS_FOUND_label_2);

        RIGHT_PANEL.add(RIGHT_PANEL_UP_1);
        RIGHT_PANEL.add(RIGHT_PANEL_UP_2);
        RIGHT_PANEL.add(RIGHT_PANEL_DOWN);

        // Δημιουργία ενός Μενού το οποίο τοποθετήται στο βασικό μας Frame της κλάσης GameScreen
        menuGameScreen = new MenuGameScreen();
        this.setJMenuBar(menuGameScreen);
        handler = new MenuHandler();
        // Listeners
        menuGameScreen.exit_game.addActionListener(handler);
        menuGameScreen.new_game.addActionListener(handler);
        menuGameScreen.add_player_info.addActionListener(handler);
        menuGameScreen.about.addActionListener(handler);
        menuGameScreen.help_settings.addActionListener(handler);
        menuGameScreen.help.addActionListener(handler);
        menuGameScreen.cancel_end_game.addActionListener(handler);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        readWordFile(); // διαβάζουμε το αρχείο
        /* Με αυτή τη συνάρτηση θα δημιουργήσουμε το table με τα γράμματα (που θα είναι buttons) */
        createGameTable();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        this.add(LEFT_JPANEL);
        this.add(RIGHT_PANEL);

        this.setVisible(true);
    }


    /////////////////////////////////////////////////////////////////////////// MENOY ///////////////////////////////////////////////////////////////////////////
    /* Αυτή η συνάρτηση μας κάνει ενα υποτιθέμενο restart του παιχνιδιού */
    private void newGameScreen()
    {
        this.dispose(); // κλείνουμε το frame που τρέχει τώρα
        arrayOfGame = new ButtonGameScreen[LEFT_UP_JPANEL_ROWS][LEFT_UP_JPANEL_COLUMNS]; // δημιουργούμε ένα νέο πίνακα (άμα δεν το κάνουμε αυτό δε θα μας δουλέυει η συνάρτηση createGameTable λόγω του ...if (arrayOfGame[rows][columns] == null)...)
        GameScreen gameScreen = new GameScreen(goal); // νέο frame του παιχνιδιού ...
    }
    /////////////////////////////////////////////////////////////////////////// TELOS MENOU //////////////////////////////////////////////////////////////////////////
    /* Υπάρχει ένα bug που εμφανίζετε και μπαίνει ένα κενό κουμπί, χωρίς γράμμα
    *  οπότε εδώ το εντοπίζουμε και το διορθώνουμε */
    private void check_for_empty_butt()
    {
        String[] arrGreekLatin = new String[] { "Α", "Β", "Γ", "Δ", "Ε", "Ζ", "Η", "Θ", "Ι", "Κ", "Λ", "Μ", "Ν", "Ξ", "Ο", "Π", "Ρ", "Σ", "Τ", "Υ", "Φ", "Χ", "Ψ", "Ω"};
        int arrGreekLatin_RAND_INDEX;
        for(int i = 0;i < arrayOfGame.length;i++)
        {
            for(int j = 0;j < arrayOfGame.length;j++)
            {
                arrGreekLatin_RAND_INDEX = (int) (Math.random() * (23 - 0 + 1) + 0);
                if(Objects.equals(arrayOfGame[i][j].letter, " ₀"))
                {
                    System.out.println("STATUS:BUG LETTER FOUND IN ROW:" + arrayOfGame[i][j].ROWS_IN_GAME + " AND COLUMN:" + arrayOfGame[i][j].COLUMNS_IN_GAME);
                    arrayOfGame[i][j].setNewLetter(arrGreekLatin[arrGreekLatin_RAND_INDEX]);
                }
            }
        }
    }
    /* Αντί να κάθομαι να γράφω τα ίδια για κάθε κουμπί της βοήθειας φτιάχνω συνάρτηση για αυτό */
    private void setHelpButtons(JButton helpButt)
    {
        helpButt.setFocusable(false);
        helpButt.setBorder(border);
        helpButt.setFont(new Font("Serif", Font.BOLD, 14));
        helpButt.addActionListener(this);
    }
    private void setHelpLabels(JLabel helpLabel, int tries)
    {
        helpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        helpLabel.setVerticalAlignment(SwingConstants.CENTER);
        helpLabel.setForeground (Color.WHITE);
        helpLabel.setFont(new Font("Serif", Font.BOLD, 30));
        helpLabel.setSize(10, 10);
        helpLabel.setBorder(border);
        helpLabel.setText("0/" + tries);
    }
    private void setHelpTextFeilds(JTextField helpTextFeilds)
    {
        helpTextFeilds.setHorizontalAlignment(SwingConstants.CENTER);
        helpTextFeilds.setSize(10,10);
        helpTextFeilds.setFont(new Font("Serif", Font.BOLD, 25));
        helpTextFeilds.setBorder(border);
        helpTextFeilds.setText("0");
    }
    private void setHelpDownLabels(JLabel helpLabel, String value)
    {
        helpLabel.setText(value);
        helpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        helpLabel.setForeground (Color.WHITE);
        helpLabel.setFont(new Font("Serif", Font.BOLD, 25));
    }
    /* Λόγω του ότι θα αλλάζουμε το συγκεκριμένο JLabel πολλές φορές, η συγκεκριμένη συνάρτηση μας βοηθά στο να μη χρειάζεται να επαναλαμβάνουμε κώδικα */
    private void setJLabelStatus(String massage, Color newColor)
    {
        STATUS_MOVEMENT.setText(massage);
        STATUS_MOVEMENT.setHorizontalAlignment(SwingConstants.CENTER);
        STATUS_MOVEMENT.setForeground(newColor);
        if(state == 1)
            STATUS_MOVEMENT.setFont(new Font("Serif", Font.BOLD, 15));
        else
            STATUS_MOVEMENT.setFont(new Font("Serif", Font.BOLD, 20));
        STATUS_MOVEMENT.setBounds(0, 360, 500, 40);
    }

    /* Εδώ διαβάζουμε το αρχείο με τις λέξεις */
    private void readWordFile()
    {
        try {
            File file = new File("./resources/rand_words.txt");
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()) {
                GAME_WORDS.add(scan.nextLine().toUpperCase(Locale.ROOT)); // τα βάζουμε στην λίστα GAME_WORDS
            }
        } catch (FileNotFoundException f)
        {
            f.printStackTrace();
        }
        System.out.println("STATUS:READ FILE SUCCESS");
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* Μέσα απο τη λίστα randWords θα πάρουμε την κάθε λέξη και για κάθε γράμμα της θα "βγάλουμε" τυχαίες συντεταγμένες
    και θα την τοποθετήσουμε ΠΡΩΤΑ ΜΕΣΑ ΣΤΟ ΠΙΝΑΚΑ που ονομάζεται arrayOfGame και μετά αφού ολοκληρώσουμε για την κάθε
    λέξη θα πάμε νε γεμίσουμε τα κενά με τυχαία γράμματα στο arrayOfGame
    ΣΤΟ ΤΕΛΟΣ ΘΑ ΒΑΛΟΥΜΕ ΤΑ ΚΟΥΜΠΙΑ, ΜΕ ΤΗ ΒΟΗΘΕΙΑ ΤΗΣ ΣΥΝΑΡΤΗΣΗΣ createFinalTable, ΣΤΟ ΚΑΤΑΛΗΛΟ ΠΑΝΕΛ */
    private void createGameTable()
    {
        randWords(); // βλέπουμε ποιές λέξεις θα μπουν μέσα στο ταμπλό
        int NUM_OF_RED = (int) (Math.random() * (2 - 1 + 1) + 1), count_red = 0; // το πολύ δύο κόκκινα
        System.out.println("STATUS:NUMBER OF RED BUTTONS MUST GENERATED:" + NUM_OF_RED);
        int NUM_OF_BLUE = (int) (Math.random() * (3 - 1 + 1) + 1), count_blue = 0; // το πολύ τρία μπλε
        System.out.println("STATUS:NUMBER OF BLUE BUTTONS MUST GENERATED:" + NUM_OF_BLUE);
        int NUM_OF_RAND = (int) (Math.random() * (4 - 0 + 1) + 0), count_rand = 0; // 0 - 4 με ερωτηματικό
        System.out.println("STATUS:NUMBER OF RAND BUTTONS MUST GENERATED:" + NUM_OF_RAND);

        int max = 7, min = 0;
        int rows , columns;
        int indexLetters = 0;
        for (String randWord : randWords) { // Για την κάθε λέξη απο της λέξεις που επιλέχθηκαν να μπουν στο ταμπλό και τηρούν τις προϋπόθεσης ...
            do {
                rows = (int) (Math.random() * (max - min + 1) + min); // τυχαία γραμμή ...
                columns = (int) (Math.random() * (max - min + 1) + min); // τυχαία στήλη ...
                if (arrayOfGame[rows][columns] == null) { // δε θέλουμε να βάζει στις συντεταγμένες που έχουν ήδη γράμμα, απλά για να μπουν όλα τα γράμματα των λέξεων για να είναι το παιχνίδι πιο εύκολο και λειτουργικό
                    if (count_red < NUM_OF_RED) { // βάλε το κόκκινο κουμπί μέχρι να "τελειώσει" ο counter_red
                        ButtonGameScreen button_red = new RedButt(rows, columns, String.valueOf(randWord.charAt(indexLetters)));
                        arrayOfGame[rows][columns] = button_red;
                        arrayOfGame[rows][columns].addActionListener(this);
                        arrayOfGame[rows][columns].addMouseListener(this);
                        count_red++;
                    } else if (count_blue < NUM_OF_BLUE) // ...
                    {
                        ButtonGameScreen button_blue = new BlueButt(rows, columns, String.valueOf(randWord.charAt(indexLetters)));
                        arrayOfGame[rows][columns] = button_blue;
                        arrayOfGame[rows][columns].addActionListener(this);
                        arrayOfGame[rows][columns].addMouseListener(this);
                        count_blue++;
                    } else if (count_rand < NUM_OF_RAND) // ...
                    {
                        ButtonGameScreen button_rand = new RandButt(rows, columns);
                        arrayOfGame[rows][columns] = button_rand;
                        arrayOfGame[rows][columns].addActionListener(this);
                        arrayOfGame[rows][columns].addMouseListener(this);
                        count_rand++;
                    } else // όταν τελειώσουν όλα τα ειδικά κουμπιά τότε θα πάει να βάλει και τα υπόλοιπα με άσπρο χρώμα (NOTE:Αυτά δεν είναι τα τυχαία γράμματα, τα τυχαία δημιουργούνται με την fillBlanks())
                    {
                        ButtonGameScreen button = new WhiteButt(rows, columns, String.valueOf(randWord.charAt(indexLetters)));
                        arrayOfGame[rows][columns] = button;
                        arrayOfGame[rows][columns].addActionListener(this);
                        arrayOfGame[rows][columns].addMouseListener(this);
                    }
                    indexLetters++;
                }
            } while (indexLetters != randWord.length()); // μέχρι να τελειώσει η λέξη (κάθε γράμμα) ...
            indexLetters = 0;
        }
        fillBlanks(); // γέμισε τα κενά
        check_for_empty_butt(); // τσέκαρε αν εμφανίστηκε κάποιο κενό κουμπί ...
        createFinalTable(); // βάλε τα κουμπιά απο τη λίστα arrayOfGame στο Πανελ
        System.out.println("STATUS:THE GAME TABLE HAS CREATED SUCCESSFUL");
    }
    private void fillBlanks()
    {
        /* Προσπάθησα να παίξω με το encoding σε ελληνικά αλλά είδα ότι δεν τα κατάφερνα οπότε απλά έκανα τον πίνακα για να μη χάσω χρόνο */
        String[] arrGreekLatin = new String[] { "Α", "Β", "Γ", "Δ", "Ε", "Ζ", "Η", "Θ", "Ι", "Κ", "Λ", "Μ", "Ν", "Ξ", "Ο", "Π", "Ρ", "Σ", "Τ", "Υ", "Φ", "Χ", "Ψ", "Ω"};
        int arrGreekLatin_RAND_INDEX = (int) (Math.random() * (23 - 0 + 1) + 0); // τυχαίο index απο τον πίνακα
        for(int rows = 0; rows < LEFT_UP_JPANEL_ROWS;rows++)
        {
            for(int columns = 0; columns < LEFT_UP_JPANEL_COLUMNS; columns++)
            {
                if(arrayOfGame[rows][columns] == null) // όποιο είναι κενό βάλε ένα τυχαίο
                {
                    ButtonGameScreen button = new WhiteButt(rows, columns, arrGreekLatin[arrGreekLatin_RAND_INDEX]);
                    arrayOfGame[rows][columns] = button;
                    arrayOfGame[rows][columns].addActionListener(this);
                    arrayOfGame[rows][columns].addMouseListener(this);
                    arrGreekLatin_RAND_INDEX = (int) (Math.random() * (23 - 0 + 1) + 0);
                }
            }
        }
    }
    /* Επιλέγουμε τυχαίες λέξεις απο το GAME_WORDS που τηρούν τις προϋποθέσεις */
    private void  randWords ()
    {
        int max = GAME_WORDS.size() - 1;
        int min = 0;
        int lengthWords = 0;
        int temp = 0; // Αυτό απλά μπαίνει για να μας δείξει τελικά ποιο είναι το συνολικό μέγεθος των γραμμάτων που μπήκαν μέσα στο randWords
        while(true) {
            int RAND_INDEX = (int) (Math.random() * (max - min + 1) + min);
            lengthWords = lengthWords + GAME_WORDS.get(RAND_INDEX).length();
            if(lengthWords >= (LEFT_UP_JPANEL_ROWS * LEFT_UP_JPANEL_COLUMNS) - 6) // έχουμε 8χ8 άρα 58 (LEFT_UP_JPANEL_ROWS * LEFT_UP_JPANEL_COLUMNS) - 6
            {
                break;
            }
            temp = lengthWords;
            randWords.add(GAME_WORDS.get(RAND_INDEX));
        }
        System.out.println("STATUS:RAND WORDS GENERATED WITH MAX LETTERS:"+temp);
        System.out.println("STATUS:RAND WORDS GENERATED WITH MAX WORDS:"+randWords.size());
        System.out.println("---------------------------");
        testPrintRandArrayList();
        System.out.println("---------------------------");
    }
    private void testPrintRandArrayList()
    {
        for (String rand_word : randWords) {
            System.out.println(rand_word);
        }
    }
    private void createFinalTable() // τέλος, βαλτά στο πάνελ
    {
        LEFT_UP_JPANEL.removeAll();
        LEFT_UP_JPANEL.revalidate();
        for (int rows = 0; rows < LEFT_UP_JPANEL_ROWS;rows++) {
            for (int column = 0; column < LEFT_UP_JPANEL_COLUMNS; column++)
            {
                LEFT_UP_JPANEL.add(arrayOfGame[rows][column]);
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* Σε αυτή τη συνάρτηση θα ελέγχουμε αν κάθε φορά που κάνει click παίκτης, συμφωνεί με του κανόνες γειτνίασης
    *  Ουσιαστικά μπαίνει με το επιλεγμένο κουμπί (που πάει να πατήσει) και βλέπουμε σε σχέση με ΤΟ ΤΕΛΑΙΥΤΑΙΟ ΚΟΥΜΠΙ ΑΝ
    *  ΕΙΝΑΙ ΚΑΠΟΙΟ ΑΠΟ ΤΑ ΓΕΙΤΟΝΙΚΟ ΤΟΥ, αυτό επιτυγχάνεται με τη βοήθεια της λίστας SELECTED_BUTT.
    *  Έστω i, j οι συντεταγμένες του selectedButt τότε έχουμε:
    *  Up --> i - 1, j
    *  Up Right (Διαγώνια πάνω, δεξιά) --> i - 1, j + 1
    *  Right --> i, j + 1
    *  Down Right (Διαγώνια κάτω, δεξιά) --> i + 1, j + 1
    *  Down --> i + 1, j
    *  Down Left (Διαγώνια κάτω, αριστερά) --> i + 1, j - 1
    *  Left --> i, j - 1
    *  Up Left (Διαγώνια πάνω, αριστερά) --> i - 1, j - 1   */
    private int controlTheClick(ButtonGameScreen selectedButt) {
        if(SELECTED_BUTT.size() == 0) // Αν είναι άδεια σημαίνει ότι επιλέγεται το πρώτο κουμπί (γράμμα) όποτε να κάνουμε έλεγχο δεν έχει νόημα...
            return 0;
        else
        {
            int i = SELECTED_BUTT.size() - 1; // ελέγχουμε σε σχέση με το τελευταίο
                if (selectedButt.ROWS_IN_GAME - 1 == SELECTED_BUTT.get(i).ROWS_IN_GAME && selectedButt.COLUMNS_IN_GAME == SELECTED_BUTT.get(i).COLUMNS_IN_GAME) // up
                {
                    return 0;
                }
                if (selectedButt.ROWS_IN_GAME - 1 == SELECTED_BUTT.get(i).ROWS_IN_GAME && selectedButt.COLUMNS_IN_GAME + 1 == SELECTED_BUTT.get(i).COLUMNS_IN_GAME) // up right (Διαγώνια πάνω, δεξιά)
                {
                    return 0;
                }
                if (selectedButt.ROWS_IN_GAME == SELECTED_BUTT.get(i).ROWS_IN_GAME && selectedButt.COLUMNS_IN_GAME + 1 == SELECTED_BUTT.get(i).COLUMNS_IN_GAME) // right
                {
                    return 0;
                }
                if (selectedButt.ROWS_IN_GAME + 1 == SELECTED_BUTT.get(i).ROWS_IN_GAME && selectedButt.COLUMNS_IN_GAME + 1 == SELECTED_BUTT.get(i).COLUMNS_IN_GAME) // down right (Διαγώνια κάτω, δεξιά)
                {
                    return 0;
                }
                if (selectedButt.ROWS_IN_GAME + 1 == SELECTED_BUTT.get(i).ROWS_IN_GAME && selectedButt.COLUMNS_IN_GAME == SELECTED_BUTT.get(i).COLUMNS_IN_GAME) // down
                {
                    return 0;
                }
                if (selectedButt.ROWS_IN_GAME + 1 == SELECTED_BUTT.get(i).ROWS_IN_GAME && selectedButt.COLUMNS_IN_GAME - 1 == SELECTED_BUTT.get(i).COLUMNS_IN_GAME) // down left (Διαγώνια κάτω, αριστερά)
                {
                    return 0;
                }
                if (selectedButt.ROWS_IN_GAME == SELECTED_BUTT.get(i).ROWS_IN_GAME && selectedButt.COLUMNS_IN_GAME - 1 == SELECTED_BUTT.get(i).COLUMNS_IN_GAME) // left
                {
                    return 0;
                }
                if (selectedButt.ROWS_IN_GAME - 1 == SELECTED_BUTT.get(i).ROWS_IN_GAME && selectedButt.COLUMNS_IN_GAME - 1 == SELECTED_BUTT.get(i).COLUMNS_IN_GAME) // up left (Διαγώνια πάνω, αριστερά)
                {
                    return 0;
                }
        }
        /// Αλλάζουμε το μνμ στο JLabel για να του πούμε ότι επιτρέπονται μόνο γειτονικά
        setJLabelStatus("ΠΡΟΣΟΧΗ, ΓΕΙΤΟΝΙΚΑ ΜΟΝΟ!!", Color.RED);
        return 1; // δεν είναι τελικά έγκυρος ο έλεγχος ...
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* ΠΡΟΣΟΧΗ --> Τα γράμματα μέσα στα κουμπιά είναι STRING ΟΜΩΣ είναι μαζί με τα substring τους (βαθμοί) οπότε για να ελέγχω αν η λέξη είναι
    *  σωστή πρέπει να επεξεργαστώ το STRING και να ΒΓΑΛΩ τους βαθμούς, το κακό είναι ότι αυτά είναι substring άρα αναγκαστικά πρέπει να
    *  δημιουργήσω την isNumeric που και καλά βλέπει αν είναι νούμερο ο χαρακτήρας που παίρνω απο το StringBuilder word
    *  ΑΝ ΕΙΝΑΙ ΔΕΝ ΤΟ γράφω στο StringBuilder newWord
    *  ΑΝ ΔΕΝ ΕΙΝΑΙ το γράφω αφού αυτό σημαίνει ότι είναι ΓΡΑΜΜΑ
    *  και έτσι δημιουργώ τη λέξη χωρίς τους βαθμούς */
    private StringBuilder fixString(StringBuilder word) {
        StringBuilder newWord = new StringBuilder();
        for(int i = 0;i < word.length();i++) {
            if(isNumeric(String.valueOf(word.charAt(i))) != 1) //
                newWord.append(word.charAt(i));
        }
        return newWord;
    }
    private int isNumeric(String string) {
        if(Objects.equals(string, "₀")) return 1;
        if(Objects.equals(string, "₁")) return 1;
        if(Objects.equals(string, "₂")) return 1;
        if(Objects.equals(string, "₃")) return 1;
        if(Objects.equals(string, "₄")) return 1;
        if(Objects.equals(string, "₅")) return 1;
        if(Objects.equals(string, "₆")) return 1;
        if(Objects.equals(string, "₇")) return 1;
        if(Objects.equals(string, "₈")) return 1;
        if(Objects.equals(string, "₉")) return 1;
        if(Objects.equals(string, "₁" + "₀")) return 1;
        else return 0;
    }
    /* Εδώ γίνεται ο έλεγχος για νίκη του παίκτη */
    private void checkWordSelected(StringBuilder wordCreated)
    {
        for (String randWord : randWords) {
            if (Objects.equals(randWord, wordCreated.toString())) {
                System.out.println("STATUS: WORD:" + wordCreated + ":IS CORRECT");
                {
                    setJLabelStatus("ΒΡΗΚΕΣ ΤΗΝ ΛΕΞΗ " + randWord + "!!", Color.GREEN);
                    words_found++;
                    total_points += word_points;
                    setHelpDownLabels(TOTAL_POINTS_label_2, String.valueOf(total_points));
                    setHelpDownLabels(WORDS_FOUND_label_2, String.valueOf(words_found));
                    addRandLetters(); // στη λέξη που βρήκε βάλε τυχαία γράμματα ...
                    if(total_points >= goal) { // έδω είναι η περίπτωση που σχημάτισε και όλους τους πόντους, άρα νίκησε ολοκληρωτικά ...
                        setJLabelStatus("ΒΡΗΚΕΣ ΟΛΕΣ ΤΙΣ ΛΕΞΕΙΣ!!!", Color.GREEN);
                        setJLabelStatus("ΣΥΧΓΑΡΗΤΗΡΙΑ ΝΙΚΗΣΕΣΣΣΣΣΣΣ!!!", Color.GREEN);
                        JOptionPane.showMessageDialog(null, "Συγχαρητήρια έχεις νικήσει, μπορείς να τερματίσεις το παιχνίδι ή να ξεκινήσεις νέο (από το μενού)");
                        disableButts();
                    }
                    return;
                }
            }
        }
        setJLabelStatus("Η ΛΕΞΗ --" + wordCreated + "-- ΕΙΝΑΙ ΛΑΘΟΣ 🤔 !!", Color.RED);
    }
    /* Όταν βρίσκει μια λέξη στη θέση των κουμπιών που βρήκε μπαίνουν άλλα τυχαία γράμματα */
    private void addRandLetters()
    {
        String[] arrGreekLatin = new String[] { "Α", "Β", "Γ", "Δ", "Ε", "Ζ", "Η", "Θ", "Ι", "Κ", "Λ", "Μ", "Ν", "Ξ", "Ο", "Π", "Ρ", "Σ", "Τ", "Υ", "Φ", "Χ", "Ψ", "Ω"};
        for(int i = 0;i < SELECTED_BUTT.size();i++){
            SELECTED_BUTT.get(i).unselectButton(); // κάνουμε αρχικά un-select όλα τα κουμπιά που αποτελούν τη λέξη που βρήκε ...
            if(!(SELECTED_BUTT.get(i) instanceof RandButt)) { // άμα είναι κουμπί μπαλαντέρ απλά το κάνουμε us-select ...
                int indexArrGreekLatin = (int) (Math.random() * (23 - 0 + 1) + 0);
                SELECTED_BUTT.get(i).setNewLetter(arrGreekLatin[indexArrGreekLatin]); //
            } else
                SELECTED_BUTT.get(i).unselectButton(); // εδω θα είναι κουμπί μπαλαντέρ ...
        }
        SELECTED_BUTT.removeAll(SELECTED_BUTT); // και προφανώς αδειάζουμε όλη τη με τα επιλεγμένα κουμπιά αφού η λέξη είναι σωστή ...
        createFinalTable(); // ξαναδημιουργούμε το νέο ταμπλό
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* Βοηθητική συνάρτηση χρησιμοποιείται για να απενεργοποιούμε όλα τα κουμπιά του ταμπλό, όποτε χρηάζεται */
    private void disableButts()
    {
        CHECK_WORD.setEnabled(false);
        DELETE_LINE.setEnabled(false);
        REORDER_LINE.setEnabled(false);
        REORDER_COLUMN.setEnabled(false);
        REORDER_TABLE.setEnabled(false);
        EXCHANGE_LETTERS.setEnabled(false);
    }
    /* Βοηθητική συνάρτηση χρησιμοποιείται για απενεργοποιούμε όλα τα κουμπιά του ταμπλό, όποτε χρειάζεται */
    private void disableTable()
    {
        for(int i = 0;i < LEFT_UP_JPANEL_ROWS;i++)
        {
            for(int j = 0;j < LEFT_UP_JPANEL_COLUMNS;j++)
            {
                arrayOfGame[i][j].setEnabled(false);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* ΥΛΟΠΟΙΗΣΗ ΤΗΣ ΒΟΗΘΕΙΑΣ ΔΙΑΓΡΑΦΗ ΓΡΑΜΜΗΣ */
    /* Παίρνουμε τυχαία απο το πίνακα με τα ελληνικά γράμματα και σε όλα τα κουτάκια βάζουμε το νέο letter καλώντας τη
    *  συνάρτηση setNewLetter
    *
    *  Πάμε στη γραμμή που μας έχει δώσει ο χρήστης και κάνουμε προσπέλαση κάθε κουτάκι άρα 8 φορές
    *  ΠΡΟΣΟΧΗ --> Δεν αλλάζουν τα Rand Buttons (μπαλαντέρ)
    *  Αν τώρα είναι κάποια κουμπιά επιλεγμένα (δλδ έχουν χρώμα ΚΙΤΡΙΝΟ) κάλουμε τη συνάρτηση calcPoints() για να υπολογίσουμε τους νέους πόντους.
    *  */
    private void deleteLineFunc(int lineToDelete)
    {
        String[] arrGreekLatin = new String[] { "Α", "Β", "Γ", "Δ", "Ε", "Ζ", "Η", "Θ", "Ι", "Κ", "Λ", "Μ", "Ν", "Ξ", "Ο", "Π", "Ρ", "Σ", "Τ", "Υ", "Φ", "Χ", "Ψ", "Ω"};
        for(int j = 0;j < LEFT_UP_JPANEL_COLUMNS;j++) {
            if (!(arrayOfGame[arrayOfGame[lineToDelete][j].ROWS_IN_GAME][arrayOfGame[lineToDelete][j].COLUMNS_IN_GAME] instanceof RandButt))
            {
                if(arrayOfGame[arrayOfGame[lineToDelete][j].ROWS_IN_GAME][arrayOfGame[lineToDelete][j].COLUMNS_IN_GAME].color == Color.YELLOW)
                {
                    int randIndex = (int) (Math.random() * (23 - 0 + 1) + 0); // διαλέγουμε τυχαία απο το πίνακα με τα ελληνικά γράμματα ...
                    arrayOfGame[arrayOfGame[lineToDelete][j].ROWS_IN_GAME][arrayOfGame[lineToDelete][j].COLUMNS_IN_GAME].setNewLetter(arrGreekLatin[randIndex]);
                    calcPoints();
                } else {
                    int randIndex = (int) (Math.random() * (23 - 0 + 1) + 0); // διαλέγουμε τυχαία απο το πίνακα με τα ελληνικά γράμματα ...
                    arrayOfGame[arrayOfGame[lineToDelete][j].ROWS_IN_GAME][arrayOfGame[lineToDelete][j].COLUMNS_IN_GAME].setNewLetter(arrGreekLatin[randIndex]);
                }
                setHelpDownLabels(WORD_POINTS_label_2, String.valueOf(word_points));
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* ΥΛΟΠΟΙΗΣΗ ΤΗΣ ΒΟΗΘΕΙΑΣ ΑΝΑΔΙΑΤΑΞΗ ΓΡΑΜΜΗΣ */
    /*
    *  indexesOfRand1 --> αποθηκεύομε τις θέσεις των μπαλαντέρ (?), άμα υπάρχουν, απο τον αρχικό πίνακα, δλδ πριν το shuffle
    *  indexesOfRand2 --> αποθηκεύουμε τις θέσεις των μπαλαντέρ (?), άμα υπάρχουν, μετά το shuffle
    *  ΔΕΣ ΑΝΑΦΟΡΑ ΓΙΑ ΠΕΡΙΣΣΟΤΕΡΕΣ ΛΕΠΤΟΜΕΡΙΕΣ
    */
    private void reorderLineFunc(int lineToReorder) {
        ArrayList<String> suffList = new ArrayList<>();
        ArrayList<Integer> indexesOfRand1 = new ArrayList<>();
        ArrayList<Integer> indexesOfRand2 = new ArrayList<>();
        ArrayList <String> tempArray = new ArrayList<>();
        ArrayList <Integer> yellowRands = new ArrayList<>(); // τα indexes των συντεταγμένων των κουμπιών μπαλαντέρ που είναι κίτρινα
        for (int j = 0; j < LEFT_UP_JPANEL_COLUMNS; j++)
        {
            if (arrayOfGame[lineToReorder][j] instanceof RandButt) {
                if (arrayOfGame[lineToReorder][j].color == Color.YELLOW) {
                    tempArray.add(arrayOfGame[lineToReorder][j].letter);
                    yellowRands.add(j);
                    arrayOfGame[lineToReorder][j].unselectButton(); // για να γίνει (?)
                }
                indexesOfRand1.add(j);
            }
            suffList.add(String.valueOf(arrayOfGame[lineToReorder][j].letter.charAt(0)));
        }
        /* H while μπαίνει γιατί δε θέλουμε να καταλήξουμε να έχουμε την ίδιες συντεταγμένες στον indexesOfRand1 και στον
        *  indexesOfRand2, γιατί αν έχουμε ίδιες δε θα λειτουργεί σωστά ο κώδικας */
        while(true) {
            Collections.shuffle(suffList);
            for (int i = 0;i < suffList.size();i++)
            {
                if (suffList.get(i).equals("?"))
                    indexesOfRand2.add(i);
            }
            if (checkSameArrays(indexesOfRand1, indexesOfRand2) || indexesOfRand2.size() == 0)
                break;
            else
                indexesOfRand2.clear();
        }
        if (!indexesOfRand1.isEmpty()) {
            for (int i = 0; i < indexesOfRand1.size(); i++) {
                Collections.swap(suffList, indexesOfRand1.get(i), indexesOfRand2.get(i));
            }
        }
        int i = 0; // counter για το tempArray
        for (int j = 0;j < suffList.size();j++)
        {
            if (i != yellowRands.size() && suffList.get(j).equals("?") && !(tempArray.isEmpty()) &&  yellowRands.get(i) == j) {
                ((RandButt) arrayOfGame[lineToReorder][j]).pickedLetter = String.valueOf(tempArray.get(i).charAt(0));
                arrayOfGame[lineToReorder][j].selectButton(Color.YELLOW);
                i++;
            }
            else {
                arrayOfGame[lineToReorder][j].setNewLetter(String.valueOf(suffList.get(j).charAt(0)));
            }
            calcPoints();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* Παρόμοια με το επάνω απλά για στήλες */
    /* ΥΛΟΠΟΙΗΣΗ ΤΗΣ ΒΟΗΘΕΙΑΣ ΑΝΑΔΙΑΤΑΞΗ ΣΤΗΛΗΣ */
    private void reorderColumnFunc(int columnToReorder)
    {
        ArrayList<String> suffList = new ArrayList<>();
        ArrayList<Integer> indexesOfRand1 = new ArrayList<>();
        ArrayList<Integer> indexesOfRand2 = new ArrayList<>();
        ArrayList <String> tempArray = new ArrayList<>();
        ArrayList <Integer> yellowRands = new ArrayList<>(); // τα indexes των συντεταγμένων των κουμπιών μπαλαντέρ που είναι κίτρινα
        for (int i = 0; i < LEFT_UP_JPANEL_ROWS; i++)
        {
            if (arrayOfGame[i][columnToReorder] instanceof RandButt) {
                if (arrayOfGame[i][columnToReorder].color == Color.YELLOW) {
                    tempArray.add(arrayOfGame[i][columnToReorder].letter);
                    yellowRands.add(i);
                    arrayOfGame[i][columnToReorder].unselectButton(); // για να γίνει (?)
                }
                indexesOfRand1.add(i);
            }
            suffList.add(String.valueOf(arrayOfGame[i][columnToReorder].letter.charAt(0)));
        }
        /* H while μπαίνει γιατί δε θέλουμε να καταλήξουμε να έχουμε την ίδιες συντεταγμένες στον indexesOfRand1 και στον
         *  indexesOfRand2, γιατί αν έχουμε ίδιες δε θα λειτουργεί σωστά ο κώδικας */
        while(true) {
            Collections.shuffle(suffList);
            for (int i = 0;i < suffList.size();i++)
            {
                if (suffList.get(i).equals("?"))
                    indexesOfRand2.add(i);
            }
            if (checkSameArrays(indexesOfRand1, indexesOfRand2) || indexesOfRand2.size() == 0)
                break;
            else
                indexesOfRand2.clear();
        }
        if (!indexesOfRand1.isEmpty()) {
            for (int i = 0; i < indexesOfRand1.size(); i++) {
                Collections.swap(suffList, indexesOfRand1.get(i), indexesOfRand2.get(i));
            }
        }
        int j = 0; // counter για το tempArray
        for (int i = 0; i < suffList.size(); i++)
        {
            if (j != yellowRands.size() && suffList.get(i).equals("?") && !(tempArray.isEmpty()) &&  yellowRands.get(j) == i) {
                ((RandButt) arrayOfGame[i][columnToReorder]).pickedLetter = String.valueOf(tempArray.get(j).charAt(0));
                arrayOfGame[i][columnToReorder].selectButton(Color.YELLOW);
                j++;
            }
            else {
                arrayOfGame[i][columnToReorder].setNewLetter(String.valueOf(suffList.get(i).charAt(0)));
            }
            calcPoints();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* ΠΡΟΣΟΧΗ --> Στο Αναδιάταξη Ταμπλό ΟΤΙ ΕΠΙΛΕΓΜΕΝΑ ΚΟΥΜΠΙΑ είχε ο παίκτης γίνονται un-select */
    private void reorderTableFunc()
    {
        /* Κάνουμε un-select όλα τα κουμπία που είναι επιλεγμένα */
        for(int i = 0;i < SELECTED_BUTT.size();i++) {
            SELECTED_BUTT.get(i).unselectButton();
        }
        word_points = 0;
        setHelpDownLabels(WORD_POINTS_label_2, String.valueOf(word_points));
        SELECTED_BUTT.removeAll(SELECTED_BUTT);

        shuffle(arrayOfGame);

    }
    /* Fisher–Yates algorithm ( https://stackoverflow.com/questions/20190110/2d-int-array-shuffle )
    *  Είναι τροποποιμένος για δισδιάστατο πίνακα */
    private void shuffle(ButtonGameScreen[][] a) {
        Random random = new Random();
        int m, n;
        for (int i = a.length - 1; i > 0; i--) {
            for (int j = a[i].length - 1; j > 0; j--) {
                if(!(a[i][j] instanceof RandButt)) { // οι μπαλαντέρ δεν αλλάζουν θέση (ούτε τα κόκκινα ή μπλέ κουμπιά) ...
                    do {
                        m = random.nextInt(i + 1);
                        n = random.nextInt(j + 1);
                    } while (a[m][n] instanceof RandButt);
                    String temp = String.valueOf(a[i][j].letter.charAt(0));
                    a[i][j].setNewLetter(String.valueOf(a[m][n].letter.charAt(0)));
                    a[m][n].setNewLetter(temp);
                }
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* ΥΛΟΠΟΙΗΣΗ ΤΗΣ ΒΟΗΘΕΙΑΣ ΕΝΑΛΛΑΓΗΣ ΓΡΑΜΜΑΤΩΝ (ΔΕΣ ΑΝΑΦΟΡΑ) */
    private void exchangeLettersFunc()
    {
        state++; // αυξάνουμε το state κατά ένα, άρα καταλαβαίνουμε ότι μπαίνουμε σε κατάσταση exchange Letters ...
        setJLabelStatus("ΕΠΕΛΕΞΕ ΔΥΟ ΓΡΑΜΜΑΤΑ, ΟΤΑΝ ΤΕΛΙΩΣΕΙΣ ΠΑΤΑ ΔΕΞΙ ΚΛΙΚ", Color.GREEN);
        CHECK_WORD.setEnabled(false); // απενεργοποιούμε τι κουμπί του διότι δεν το χρειαζόμαστε ...
        RIGHT_PANEL.setVisible(false); // και κάνουμε το δεξί πάνελ μη φανερό γιατί δε μας χρειάζεται δε κάτι ...
        for(int i = 0;i < SELECTED_BUTT.size();i++) {
            if(SELECTED_BUTT.get(i) instanceof RandButt) {
                excha_rand_butt.add(String.valueOf(SELECTED_BUTT.get(i).letter.charAt(0))); // εδώ βάζουμε τις τιμές των μπαλαντέρ που έχουν επιλεγεί (αν έχουν επιλεγεί)
            }
            SELECTED_BUTT.get(i).unselectButton(); // κάνουμε unselect όλα τα κουμπια που έχει ήδη επιλέξει ο χρήστης ...
        }
    }
    /* Καλείται όταν ο χρήστης πατήσει να καταχωρήσει τα γράμματα που επέλεξε για αλλαγή
    *  */
    private void exchangeLetters()
    {
        setJLabelStatus("ΕΠΕΛΕΞΕ ΔΥΟ ΓΡΑΜΜΑΤΑ!!!!", Color.GREEN);
        /// swap τα γράμματα το κουμπιών που επέλεξε ...
        String temp = EXCHANGE_WORDS_LETTERS.get(0).letter;
        EXCHANGE_WORDS_LETTERS.get(0).setNewLetter(String.valueOf(EXCHANGE_WORDS_LETTERS.get(1).letter.charAt(0)));
        EXCHANGE_WORDS_LETTERS.get(1).setNewLetter(String.valueOf(temp.charAt(0)));
        /// Εδώ ουσιαστικά τελίωσε η βοήθεια εναλλαγής γραμμάτω, τώρα κάνουμε επαναφορά του ταμπλό στην αρχική του κατάσταση ...
        for (ButtonGameScreen exchange_words_letter : EXCHANGE_WORDS_LETTERS) {
            exchange_words_letter.unselectButton(); // αφού κάναμε το swap κάνουμε un-select τα ΠΡΑΣΙΝΑ κουμπιά (ΠΡΟΣΟΧΗ --> ΟΤΑΝ ΤΟ ΠΑΙΧΝΙΔΙ ΜΠΑΙΝΕΙ ΣΕ ΚΑΤΑΣΤΑΣΗ 1 ΚΑΙ Ο ΧΡΗΣΤΗΣ ΕΠΙΛΕΓΕΙ ΠΟΙΑ ΘΑ ΚΑΝΕΙ ΑΛΛΑΓΗ ΑΥΤΑ ΟΤΑΝ ΕΠΙΛΕΓΟΝΤΑΙ ΓΙΝΟΝΤΑ ΠΡΑΣΙΝΑ, ΑΝΤΙ ΓΙΑ ΚΙΤΡΙΝΑ)
        }
        EXCHANGE_WORDS_LETTERS.removeAll(EXCHANGE_WORDS_LETTERS); // άδειασε τη λίστα
        /* Ξανακάνεις επιλογή τα γράμματα που είχες κάνει un-select στην αρχή */
        int j = 0;
        for(int i = 0;i < SELECTED_BUTT.size();i++) {
            if(SELECTED_BUTT.get(i) instanceof RandButt) {
                ((RandButt) SELECTED_BUTT.get(i)).pickedLetter = excha_rand_butt.get(j); // εδώ βάζουμε τις τιμές των μπαλαντέρ που έχουν επιλεγεί (αν έχουν επιλεγεί)
                j++;
            }
            SELECTED_BUTT.get(i).selectButton(Color.YELLOW);
        }
        excha_rand_butt.removeAll(excha_rand_butt); // άδειασμα και τους μπαλαντέρ ...
        calcPoints();
        createFinalTable(); // δημιουργία του ανανεωμένου πλέον πίνακα ...
        CHECK_WORD.setEnabled(true); // ξανακάνε διαθέσιμο το κουμπί ...
        RIGHT_PANEL.setVisible(true); // ξανακάνε ορατό το δεξί πάνελ ...
        setJLabelStatus("Η ΑΛΛΑΓΗ ΕΓΙΝΕ ΜΕ ΕΠΥΤΙΧΙΑ!!!!", Color.GREEN);
    }
    /* ΜΑΣ ΒΟΗΘΑ ΣΤΗ ΣΥΓΚΡΙΣΗ ΤΩΝ ΤΙΜΩΝ ΠΟΥ ΕΧΟΥΝ ΔΥΟ ArrayList */
    private boolean checkSameArrays(ArrayList <Integer> indexesOfRand1, ArrayList <Integer> indexesOfRand2)
    {
        for (int i = 0; i < indexesOfRand1.size(); i++) {
            for (int j = 0; j < indexesOfRand2.size(); j++) {
                if (Objects.equals(indexesOfRand1.get(i), indexesOfRand2.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* Υπολογισμός νέων πόντων */
    private static int STATE_BLUE;  // αυτό βοηθά στο αν μην πολλαπλασιάσουμε δυο φορές όταν έχουμε μπλε κουμπιά
    private void calcPoints()
    {
        word_points = 0; // μηδενισμός των πόντων, ώστε να τους ξαναυπολογίσουμε
        STATE_BLUE = 0;  // μηδενισμός της κατάστασης του μπλε κουμπιού όταν μπαίνει στην συνάρτηση ...
        for(int i = 0;i < SELECTED_BUTT.size();i++)
        {
            /* Όταν έχουμε ένα μπλε κουμπί (στα επιλεγμένα) και η κατάσταση του είναι ΔΙΑΦΟΡΗ του 1 τότε σημαίνει ότι δεν έχει βρεί ακόμα κάποιο μπλε
             * κουμπί άρα πρέπει να αλλάξει την κατάσταση σε 1, ώστε να πα μετά στην if και να πολλαπλασιάσει το αποτέλεσμα με 2 ...
             */
            if(SELECTED_BUTT.get(i) instanceof BlueButt && STATE_BLUE != 1) {
                System.out.println("MPIKE SE MPLE");
                STATE_BLUE = 1;
            }
            word_points += SELECTED_BUTT.get(i).points;
        }
        if(STATE_BLUE == 1) {
            word_points *= 2;
        }
        setHelpDownLabels(WORD_POINTS_label_2, String.valueOf(word_points));
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* ΕΔΩ ΤΕΛΙΩΝΟΥΝ ΟΙ ΒΑΣΙΚΟΤΕΡΣ ΣΥΝΑΡΤΗΣΗΣ ΟΛΗΣ ΤΗΣ ΕΦΑΡΜΟΓΗΣ */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == CHECK_WORD) // Έλεγχος Λέξης
        {
            System.out.println("\nSTATUS:BUTTON CHECK WORD IS PRESSED\n");
            StringBuilder wordCreated = new StringBuilder();
            // Δημιουργία του String που περιέχει ότι έχει επιλέξει ο χρήστης ...
            for (ButtonGameScreen buttonGameScreen : SELECTED_BUTT) {
                wordCreated.append(buttonGameScreen.letter);
            }
            wordCreated = fixString(wordCreated); // Φτιάχνουμε το String που δημιουργήθηκε ... (πριν την fixString έχουμε Φ₈Μ₃Σ₁Τ₁ μετά τη fixString έχουμε ΦΜΣΤ)
            if(wordCreated.length() < 3) { // Έλεγχος αν έχει επιλέξει τουλάχιστον 3 γράμματα ...
                JOptionPane.showMessageDialog(null, "Η λέξη πρέπει να είναι τουλάχιστον 3 γράμματα!!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            checkWordSelected(wordCreated); // Ελέγχουμε αν τελικά η λέξη που έχει δημιουργήσει υπάρχει μέσα στο ταμπλό του παιχνιδιού ...
            System.out.println("STATUS:WORD CREATED IS:" + wordCreated);
            return;
        }
        if(e.getSource() == DELETE_LINE) // Διαγραφή Γραμμής
        {
            System.out.println("\nSTATUS:DELETE LINE IS PRESSED\n");
            try {
                if (DELETE_LINE_text.getText().equals("0") || Integer.parseInt(DELETE_LINE_text.getText()) < 1 || Integer.parseInt(DELETE_LINE_text.getText()) > 8) // Αρχικά όλα είναι ορισμένα στο 0 άμα πατηθεί το κουμπί και είναι ακόμα 0 σημαίνει ότι δεν έχει βάλει (ή έχει ξεχάσει) να επιλέξει γραμμή ο χρήστης
                {
                    setJLabelStatus("ΞΑΝΑΔΕΣ ΤΙΣ ΣΥΝΤΕΤΑΓΜΕΝΕΣ", Color.RED); // του εμφανίζουμε και μνμ αλλάζοντας το Jlabel ...
                } else {
                    deleteLineTries_COUNTER++; // Αυξάνουμε μέχρι 3 μπορεί ...
                    if (deleteLineTries_COUNTER > helpSettings.deleteLineTries) {
                        JOptionPane.showMessageDialog(null, "Έχετε φτάσει το μέγιστο της επιλογής διαγραφή γραμμής", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    DELETE_LINE_label.setText(deleteLineTries_COUNTER+"/"+helpSettings.deleteLineTries);
                    deleteLineFunc(Integer.parseInt(DELETE_LINE_text.getText()) - 1);
                    createFinalTable();
                    setJLabelStatus("Η ΓΡΑΜΜΗ ΠΟΥ ΕΠΕΛΕΞΕΣ ΑΝΑΝΕΩΘΗΚΕ!!", Color.GREEN);
                }
            } catch (NumberFormatException n) {
                System.out.println("STATUS:NULL VALUE IN TEXT FIELD --DELETE LINE--");
            }
            return;
        }
        if(e.getSource() == REORDER_LINE) // Αναδιάταξη γραμμής
        {
            System.out.println("\nSTATUS:REORDER LINE IS PRESSED\n");
            try {
                if (REORDER_LINE_text.getText().equals("0") || Integer.parseInt(REORDER_LINE_text.getText()) < 1 || Integer.parseInt(REORDER_LINE_text.getText()) > 8) // Αρχικά όλα είναι ορισμένα στο 0 άμα πατηθεί το κουμπί και είναι ακόμα 0 σημαίνει ότι δεν έχει βάλει (ή έχει ξεχάσει) να επιλέξει γραμμή ο χρήστης
                {
                    setJLabelStatus("ΞΑΝΑΔΕΣ ΤΙΣ ΣΥΝΤΕΤΑΓΜΕΝΕΣ", Color.RED); // του εμφανίζουμε και μνμ αλλάζοντας το Jlabel ...
                } else {
                    reorderLineTries_COUNTER++; // Αυξάνουμε μέχρι 3 μπορεί ...
                    if (reorderLineTries_COUNTER > helpSettings.reorderLineTries) {
                        JOptionPane.showMessageDialog(null, "Έχετε φτάσει το μέγιστο της επιλογής αναδιάταξη γραμμής", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    REORDER_LINE_label.setText(reorderLineTries_COUNTER+"/"+helpSettings.reorderLineTries);
                    reorderLineFunc(Integer.parseInt(REORDER_LINE_text.getText()) - 1);
                    createFinalTable();
                    setJLabelStatus("Η ΓΡΑΜΜΗ ΠΟΥ ΕΠΕΛΕΞΕΣ ΑΝΑΝΕΩΘΗΚΕ!!", Color.GREEN);
                }
            } catch (NumberFormatException n) {
                System.out.println("STATUS:NULL VALUE IN TEXT FIELD --REORDER LINE--");
            }
            return;
        }
        if(e.getSource() == REORDER_COLUMN) // Αναδιάταξη στήλης
        {
            try {
                if (REORDER_COLUMN_text.getText().equals("0") || Integer.parseInt(REORDER_COLUMN_text.getText()) < 1 || Integer.parseInt(REORDER_COLUMN_text.getText()) > 8) // Αρχικά όλα είναι ορισμένα στο 0 άμα πατηθεί το κουμπί και είναι ακόμα 0 σημαίνει ότι δεν έχει βάλει (ή έχει ξεχάσει) να επιλέξει γραμμή ο χρήστης
                {
                    setJLabelStatus("ΞΑΝΑΔΕΣ ΤΙΣ ΣΥΝΤΕΤΑΓΜΕΝΕΣ", Color.RED); // του εμφανίζουμε και μνμ αλλάζοντας το Jlabel ...
                } else {
                    reorderColumnTries_COUNTER++; // Αυξάνουμε μέχρι 3 μπορεί ...
                    if (reorderColumnTries_COUNTER > helpSettings.reorderColumnTries) {
                        JOptionPane.showMessageDialog(null, "Έχετε φτάσει το μέγιστο της επιλογής αναδιάταξη στήλης", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    REORDER_COLUMN_label.setText(reorderColumnTries_COUNTER+"/"+helpSettings.reorderColumnTries);
                    reorderColumnFunc(Integer.parseInt(REORDER_COLUMN_text.getText()) - 1);
                    createFinalTable();
                    setJLabelStatus("Η ΣΤΗΛΗ ΠΟΥ ΕΠΕΛΕΞΕΣ ΑΝΑΝΕΩΘΗΚΕ!!", Color.GREEN);
                }
            } catch (NumberFormatException n) {
                System.out.println("STATUS:NULL VALUE IN TEXT FIELD --REORDER LINE--");
            }
            return;
        }
        if(e.getSource() == REORDER_TABLE) // Αναδιάταξη ταμπλό
        {
            reorderTableTries_COUNTER++; // Αυξάνουμε μέχρι 3 μπορεί ...
            if (reorderTableTries_COUNTER > helpSettings.reorderTableTries) {
                JOptionPane.showMessageDialog(null, "Έχετε φτάσει το μέγιστο της επιλογής αναδιάταξη ταμπλό (πίνακα)", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            REORDER_TABLE_label.setText(reorderTableTries_COUNTER+"/"+helpSettings.reorderTableTries);
            reorderTableFunc();
            createFinalTable();
            setJLabelStatus("ΤΟ ΤΑΜΠΛΟ ΣΟΥ ΑΝΑΝΕΩΘΗΚΕ!!", Color.GREEN);
            return;
        }
        if(e.getSource() == EXCHANGE_LETTERS) // Εναλλαγή Γραμμάτων
        {
            System.out.println("PATITHIKE");
            exchangeLettersTries_COUNTER++; // Αυξάνουμε μέχρι 3 μπορεί ...
            if (exchangeLettersTries_COUNTER > helpSettings.exchangeLettersTries) {
                JOptionPane.showMessageDialog(null, "Έχετε φτάσει το μέγιστο της επιλογής εναλαγή γραμμάτων", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            EXCHANGE_LETTERS_label.setText(exchangeLettersTries_COUNTER + "/" + helpSettings.exchangeLettersTries);
            exchangeLettersFunc();
            return;
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /* ΟΙ FOR ΜΑΣ ΒΡΙΣΚΕΙ ΠΙΟ ΚΟΥΜΠΑΚΙ ΠΑΤΗΣΕ Ο ΠΑΙΚΤΗΣ ΚΑΙ ΠΟΛΛΑ ΑΛΛΑ  */
        for(int i = 0;i < LEFT_UP_JPANEL_ROWS;i++)
        {
            for(int j = 0;j < LEFT_UP_JPANEL_COLUMNS;j++)
            {
                if(e.getSource() == arrayOfGame[i][j]) // εδώ το βρήκαμε ...
                {
                    System.out.println("\nSTATUS:BUTTON PRESSED:ROW:" + arrayOfGame[i][j].ROWS_IN_GAME+":COLUMN:" + arrayOfGame[i][j].COLUMNS_IN_GAME + ":LETTER:" + arrayOfGame[i][j].letter + ":POINTS:" + arrayOfGame[i][j].points + ":COLOR (BEFORE PRESSED):" + arrayOfGame[i][j].color);
                    if(state == 1) // state of Exchange
                    {
                        if(EXCHANGE_WORDS_LETTERS.size() >= 2)
                        {
                            JOptionPane.showMessageDialog(null, "Δεν μπορείς να επιλέξεις άλλα γράμματα, Αμα έχεις τελιώσει πάτε δεξί κλικ !!", "Warning", JOptionPane.WARNING_MESSAGE);
                            setJLabelStatus("ΑΜΑ ΕΧΕΙΣ ΤΕΛΙΩΣΕΙ, ΠΑΤΑ ΔΕΞΙ ΚΛΙΚ!!", Color.GREEN);
                        }
                        else if(arrayOfGame[i][j] instanceof RandButt) {
                            JOptionPane.showMessageDialog(null, "Δεν μπορείς να επιλέξεις μπαλαντέρ!!", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                        else
                        {
                            EXCHANGE_WORDS_LETTERS.add(arrayOfGame[i][j]); // Βάλε εδώ αυτά που επέλεξε για αλλαγή
                            arrayOfGame[i][j].selectButton(Color.GREEN); // τα γράμματα που επιλέγει όταν η εφαρμογή είναι σε κατάσταση 1 θα είναι πράσινα ...
                        }
                        return;
                    }
                    if(arrayOfGame[i][j] instanceof RandButt && arrayOfGame[arrayOfGame[i][j].ROWS_IN_GAME][arrayOfGame[i][j].COLUMNS_IN_GAME].color != Color.YELLOW && controlTheClick(arrayOfGame[arrayOfGame[i][j].ROWS_IN_GAME][arrayOfGame[i][j].COLUMNS_IN_GAME]) != 1) { /// Όταν θα είναι ενα κουμπί μπαλαντέρ τότε πρέπει να ακολουθήσουμε άλλο μονοπάτι...
                    {
                        System.out.println("STATUS:A RAND BUTTON CLICKED");
                        ((RandButt) arrayOfGame[i][j]).pickLetter(); // την καλούμε για να επιλέξει ο χρήστης το γράμμα που θέλει
                        ((RandButt) arrayOfGame[i][j]).selectButton(Color.YELLOW); // το αλλάζουμε στο επιλεγμένο γράμμα...
                        SELECTED_BUTT.add(arrayOfGame[arrayOfGame[i][j].ROWS_IN_GAME][arrayOfGame[i][j].COLUMNS_IN_GAME]); // το βάζουμε στο ArrayList με τα επιλεγόμενα γράμματα...
                        setJLabelStatus("ΣΥΝΕΧΙΣΕ ΚΟΝΤΑ ΕΙΣΑΙ!!", Color.GREEN);
                        createFinalTable();
                    } // αν δεν έχει επιλέγει ακόμα...
                    } else if (arrayOfGame[arrayOfGame[i][j].ROWS_IN_GAME][arrayOfGame[i][j].COLUMNS_IN_GAME].color != Color.YELLOW && controlTheClick(arrayOfGame[arrayOfGame[i][j].ROWS_IN_GAME][arrayOfGame[i][j].COLUMNS_IN_GAME]) != 1) {
                        arrayOfGame[arrayOfGame[i][j].ROWS_IN_GAME][arrayOfGame[i][j].COLUMNS_IN_GAME].selectButton(Color.YELLOW); // κάνε το κίτρινο... (ανεξαρτήτως την κουμπί είναι, εκτός αν είναι μπαλαντέρ)
                        SELECTED_BUTT.add(arrayOfGame[arrayOfGame[i][j].ROWS_IN_GAME][arrayOfGame[i][j].COLUMNS_IN_GAME]); // το βάζουμε στο ArrayList με τα επιλεγόμενα γράμματα...
                        setJLabelStatus("ΣΥΝΕΧΙΣΕ ΚΟΝΤΑ ΕΙΣΑΙ!!", Color.GREEN);
                        createFinalTable();
                    }
                    else if (arrayOfGame[arrayOfGame[i][j].ROWS_IN_GAME][arrayOfGame[i][j].COLUMNS_IN_GAME].color == Color.YELLOW)
                    {
                        /* NOTE --> ΔΙΑΓΡΑΦΟΥΜΕ ΤΗΝ ΤΕΛΑΙΥΤΑΙΑ ΕΠΙΛΟΓΗ ΤΟΥ ΠΑΙΧΤΗ */ // NOTE --> Αν είναι μπαλαντέρ και έχει επιλεχτεί γραμμα τότε αμα έχουμε φτάσει εδώ σημαίνει ότι θέλουμε να το κάνουμε un-select δλδ να το επαναφέρουμε στην αρχική μορφή του (με εικονίδιο ?)
                        SELECTED_BUTT.remove(SELECTED_BUTT.size() - 1).unselectButton();
                        createFinalTable();
                    }
                    calcPoints(); // υπολογισμός νέων πόντων μετά απο οποιαδήποτε ενέργεια ...
                }
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3)
        {
            System.out.println("STATUS:RIGHT CLICK PRESSED");
            if(EXCHANGE_WORDS_LETTERS.size() < 2 && state == 1) // αν έχει επιλέξει λιγότερο απο δύο
            {
                setJLabelStatus("ΕΠΕΛΕΞΕ ΔΥΟ ΓΡΑΜΜΑΤΑ!!!!", Color.RED);
                return;
            }
            if(state == 1)
            {
                exchangeLetters();
                state = 0; // όταν θα πατήσει ο παίκτης το δεξί κλικ θα καταχωρηθεί η αλλαγή ...
            }
            else // για state = 0
            {
                setJLabelStatus("ΠΑΜΕ ΓΙΑ ΑΛΛΑ!!", Color.GREEN);
                for (ButtonGameScreen buttonGameScreen : SELECTED_BUTT) {
                    buttonGameScreen.unselectButton();
                }
                word_points = 0; // Μηδενίζουμε τους πόντους αφού πάτησε δεξί κλίκ και έκανε un-select όλα τα κούμπια που είχε πατήσει
                setHelpDownLabels(WORD_POINTS_label_2, String.valueOf(word_points)); // ...
                SELECTED_BUTT.removeAll(SELECTED_BUTT);
            }
        }
    }
    @Override public void mousePressed(MouseEvent e) {} @Override public void mouseReleased(MouseEvent e) {} @Override  public void mouseEntered(MouseEvent e) {} @Override public void mouseExited(MouseEvent e) {}
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// Εδώ χειριζόμαστε τις ενέργειες του μενού
    class MenuHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == menuGameScreen.exit_game)
            {
                System.out.println("STATUS:GAME SHUT DOWN");
                System.exit(100); // κλείνουμε την εφαρμογή
            }
            if(e.getSource() == menuGameScreen.new_game)
            {
                System.out.println("STATUS:NEW GAME");
                newGameScreen(); // νέο παιχνίδι
            }
            if(e.getSource() == menuGameScreen.add_player_info)
            {
                System.out.println("STATUS:SET ADDITIONAL INFO");
                player.setAdditionalinfo(); // εισαγωγή όλων των στοιχείων του παίκτη
            }
            if(e.getSource() == menuGameScreen.about)
            {
                System.out.println("STATUS:ABOUT");
                About about = new About(); // πληροφορίες για την ομάδα ανάπτυξης
            }
            if(e.getSource() == menuGameScreen.help_settings)
            {
                System.out.println("STATUS:HELP SETTINGS");
                helpSettings.changeHelpSettings(); // διαμόρφωση των βοηθειών
            }
            if(e.getSource() == menuGameScreen.help)
            {
                Help help = new Help(); // βοήθεια
            }
            if(e.getSource() == menuGameScreen.cancel_end_game) // ακύρωση παιχνιδιού
            {
                JOptionPane.showMessageDialog(null,"Το παιχνίδι ακυρώθηκε, πατήστε Νέο Παιχνίδι από το μενού");
                setJLabelStatus("ΤΟ ΠΑΙΧΙΔΙ ΑΚΥΡΩΘΗΚΕ!!", Color.red);
                disableButts(); // κάνουμε μη διαθέσιμα ολά τα κουμπιά ...
                disableTable(); // κάνουμε μη διαθεσιμο το ταμπλό ...
                menuGameScreen.disableGameMenu(); // κάποιες επιλογές από το μενού τις κάνουμε μη διαθέσιμες, το μόνο που μπορεί να πατήσει είναι το Νεο Παιχνίδι και το Έξοδος παιχνιδιού ...
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* Οι ρυθμίσεις του παιχνιδιού για τις βοήθειες
    *  έγινε τοπική κλάση γιατί αναγκαστικά έπρεπε να ανανεώνονται οι βοήθειες άμεσα */
    class HelpSettings implements ActionListener {
        private JFrame frame;
        private JPanel panel;
        private JButton sumbit;
        private JLabel sumbit_label;
        private JLabel deleteLine_label;
        private JLabel reorderLine_label;
        private JLabel reorderColumn_label;
        private JLabel reorderTable_label;
        private JLabel exchangeLetters_label;
        private JTextField deleteLine_textField;
        private JTextField reorderLine_textField;
        private JTextField reorderColumn_textField;
        private JTextField reorderTable_textField;
        private JTextField exchangeLetters_textField;
        protected int deleteLineTries;
        protected int reorderLineTries;
        protected int reorderColumnTries;
        protected int reorderTableTries;
        protected int exchangeLettersTries;
        public HelpSettings(int deleteLine, int reorderLine, int reorderColumn, int reorderTable, int exchangeLetters)
        {
            this.deleteLineTries = deleteLine;
            this.reorderLineTries = reorderLine;
            this.reorderColumnTries = reorderColumn;
            this.reorderTableTries = reorderTable;
            this.exchangeLettersTries = exchangeLetters;
        }
        protected void changeHelpSettings()
        {
            createHelpSettingsFrame();
        }
        private void createHelpSettingsFrame()
        {
            frame = new JFrame("Set information");
            /////
            ImageIcon imgIcon = new ImageIcon("./resources/logo.png"); // icon για το παράθυρο ...
            frame.setSize(800, 800);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(true);
            frame.setLocationRelativeTo(null);
            frame.setUndecorated(false);
            frame.setIconImage(imgIcon.getImage());

            panel = new JPanel();
            panel.setLayout(new GridLayout(6, 6));
            panel.setSize(800,800);

            sumbit_label = new JLabel("Καταχώρηση");
            setLabels(sumbit_label);

            deleteLine_label = new JLabel("Διαγραφή Γραμμής");
            setLabels(deleteLine_label);

            reorderLine_label = new JLabel("Αναδιάταξη Γραμμής");
            setLabels(reorderLine_label);

            reorderColumn_label = new JLabel("Αναδιάταξη Στήλης");
            setLabels(reorderColumn_label);

            reorderTable_label = new JLabel("Αναδιάταξη Ταμπλό");
            setLabels(reorderTable_label);

            exchangeLetters_label = new JLabel("Εναλλαγή Γραμμάτων");
            setLabels(exchangeLetters_label);

            ////////////////

            deleteLine_textField = new JTextField();
            deleteLine_textField.setText(String.valueOf(this.deleteLineTries));
            setTextFeilds(deleteLine_textField);

            reorderLine_textField = new JTextField();
            reorderLine_textField.setText(String.valueOf(this.reorderLineTries));
            setTextFeilds(reorderLine_textField);

            reorderColumn_textField = new JTextField();
            reorderColumn_textField.setText(String.valueOf(this.reorderColumnTries));
            setTextFeilds(reorderColumn_textField);

            reorderTable_textField = new JTextField();
            reorderTable_textField.setText(String.valueOf(this.reorderTableTries));
            setTextFeilds(reorderTable_textField);

            exchangeLetters_textField = new JTextField();
            exchangeLetters_textField.setText(String.valueOf(this.exchangeLettersTries));
            setTextFeilds(exchangeLetters_textField);

            ////////////////

            sumbit = new JButton("Submit");
            setButtons(sumbit);

            panel.add(deleteLine_label);
            panel.add(deleteLine_textField);

            panel.add(reorderLine_label);
            panel.add(reorderLine_textField);

            panel.add(reorderColumn_label);
            panel.add(reorderColumn_textField);

            panel.add(reorderTable_label);
            panel.add(reorderTable_textField);

            panel.add(exchangeLetters_label);
            panel.add(exchangeLetters_textField);

            panel.add(sumbit_label);
            panel.add(sumbit);

            frame.add(panel);

            frame.setVisible(true);
        }

        private void setLabels(JLabel helpLabel)
        {
            helpLabel.setHorizontalAlignment(SwingConstants.CENTER);
            helpLabel.setVerticalAlignment(SwingConstants.CENTER);
            helpLabel.setForeground (Color.WHITE);
            helpLabel.setFont(new Font("Serif", Font.BOLD, 30));
            helpLabel.setSize(20, 20);
        }
        private void setTextFeilds(JTextField helpTextFeilds)
        {
            helpTextFeilds.setHorizontalAlignment(SwingConstants.CENTER);
            helpTextFeilds.setSize(50,20);
            helpTextFeilds.setFont(new Font("Serif", Font.BOLD, 25));
        }
        private void setButtons(JButton helpButt) // int x, int y, int width, int height
        {
            helpButt.setFocusable(false);
            helpButt.setFont(new Font("Serif", Font.BOLD, 30));
            helpButt.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == sumbit)
            {
                this.deleteLineTries = Integer.parseInt(deleteLine_textField.getText());
                this.reorderLineTries = Integer.parseInt(reorderLine_textField.getText());
                this.reorderColumnTries = Integer.parseInt(reorderColumn_textField.getText());
                this.reorderTableTries = Integer.parseInt(reorderTable_textField.getText());
                this.exchangeLettersTries = Integer.parseInt(exchangeLetters_textField.getText());

                /* Μηδενίζονται γιατί άμα ο παίκτης βάλει μικρότερο αριθμό προσπαθειών που έχει και έχει ήδη
                κάνει έναν αριθμό από προσπάθειες μεγαλύτερο από αυτού που έβαλε τότε θα έχουμε πρόβλημα */
                deleteLineTries_COUNTER = 0;
                reorderLineTries_COUNTER = 0;
                reorderColumnTries_COUNTER = 0;
                reorderTableTries_COUNTER = 0;
                exchangeLettersTries_COUNTER = 0;

                setHelpLabels(DELETE_LINE_label, helpSettings.deleteLineTries);
                setHelpLabels(REORDER_LINE_label, helpSettings.reorderLineTries);
                setHelpLabels(REORDER_COLUMN_label, helpSettings.reorderColumnTries);
                setHelpLabels(REORDER_TABLE_label, helpSettings.reorderTableTries);
                setHelpLabels(EXCHANGE_LETTERS_label, helpSettings.exchangeLettersTries);

                frame.dispose();
            }
        }
    }
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
