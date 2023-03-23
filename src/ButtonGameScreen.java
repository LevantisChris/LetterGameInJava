/* ΚΛΑΣΗ ΠΟΥ ΑΝΑΠΑΡΙΣΤΑ ΤΑ ΚΟΥΜΠΙΑ ΠΟΥ ΘΑ ΕΧΕΙ ΤΟ ΤΑΜΠΛΟ ΤΟΥ ΠΑΙΧΝΙΔΙΟΥ
*
*
*   str = str.replaceAll("0", "₀");
    str = str.replaceAll("1", "₁");
    str = str.replaceAll("2", "₂");
    str = str.replaceAll("3", "₃");
    str = str.replaceAll("4", "₄");
    str = str.replaceAll("5", "₅");
    str = str.replaceAll("6", "₆");
    str = str.replaceAll("7", "₇");
    str = str.replaceAll("8", "₈");
    str = str.replaceAll("9", "₉");
*
*
*
* */
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

class ButtonGameScreen extends JButton {

    private final static String[][] pointsArr = new String[24][2];
    protected Color color;
    protected int points; // πόντοι που έχει το εκάστοτε γράμμα
    protected int ROWS_IN_GAME; // η συντεταγμένες στο παιχνίδι (X)
    protected int COLUMNS_IN_GAME; // η συντεταγμένες στο παιχνίδι (Y)
    protected String letter; // το γράμμα που θα έχει μέσα στο κουμπί
    protected int BUTT_WIDTH = 20;
    protected int BUTT_HEIGHT = 20;

    ButtonGameScreen() {createPointsMap();} // καλείται στην αρχή του προγράμματος για να αρχικοποιείσει τους πόντους...

    ButtonGameScreen(int X_IN_GAME, int COLUMNS_IN_GAME, String letter) {
        this.ROWS_IN_GAME = X_IN_GAME;
        this.COLUMNS_IN_GAME = COLUMNS_IN_GAME;
        this.letter = letter;
        if(!Objects.equals(this.letter, "?")) // στα ? RandButt δε θέλουμε να μπαίνει βαθμός
        {
            this.points = findPoint(this.letter);
            this.letter = this.letter + makeItSubscript(this.points);
        }
    }

    protected void createButton(Color color) {
        this.setSize(BUTT_WIDTH, BUTT_HEIGHT);
        this.setBackground(color);
        this.setText(String.valueOf(this.letter));
        this.setFocusable(false);
    }

    /* Τη συγκεκριμένη συνάρτηση τη χρησιμοποιούμε για να αλλάξουμε το χρώμα των κουμπιών
    που επιλέγει ο χρήστης, για να του δείξουμε ότι το επέλεξε (το κάνουμε κίτρινο) */
    protected void selectButton(Color COLOR) {};
    protected void unselectButton() {};

    /* Σκανάρει το πίνακα pointsArr (που έχει 24 γραμμές και 2 στήλες 1η == γράμματα, 2η == οι πόντοι τους) για να βρει τον πόντο του συγκεκριμένου γράμματος (letter) */
    protected int findPoint(String letter)
    {
        int point = 0; // κανένα γράμμα δεν έχει βαθμό 0...
        for (int i = 0; i < 24; i++) {
            if (Objects.equals(pointsArr[i][0], letter)) {
                point = Integer.parseInt(pointsArr[i][1]);
            }
        }
        return point;
    }

    /* Εδώ αλλάζουμε/ανανεώνουμε τις τιμές του κάθε νέου BUTTON (σε εισαγωγικά νέο)
    *  Χρησιμοποιείται
    *  deleteLineFunc
    *  reorderLineFunc
    *  */
    protected void setNewLetter(String letter) {
        this.letter = letter;
        if(!letter.equals("?")) {
            this.points = findPoint(this.letter);
            this.letter = this.letter + makeItSubscript(this.points);
        }
        createButton(this.color);
    }


    /* Αυτός ο τρόπος δεν είναι πολύ κομψός αλλά για τώρα δουλεύει, ουσιαστικά παίρνει ως παράμετρο το
    *  έναν πόντο και το μετατρέπει (επιστρέφει) με τη μορφή String ως Subscript */
    protected String makeItSubscript(int point)
    {
        if(point == 0) return "₀";
        if(point == 1) return "₁";
        if(point == 2) return "₂";
        if(point == 3) return "₃";
        if(point == 4) return "₄";
        if(point == 5) return "₅";
        if(point == 6) return "₆";
        if(point == 7) return "₇";
        if(point == 8) return "₈";
        if(point == 9) return "₉";
        if(point == 10) return "₁" + "₀";
        return "";
    }
    /* Δημιουργεί το πίνακα pointsArr σκανάροντας το αρχείο pointsTest που περιέχει τα γράμματα και τους πόντους τους */
    private void createPointsMap() {
        int rows = 0;
        int column = 0;
        try {
            File file = new File("./resources/pointsTest.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNext()) {
                pointsArr[rows][0] = scan.next().toUpperCase(Locale.ROOT);
                pointsArr[rows][1] = scan.next().toUpperCase(Locale.ROOT);
                rows++;
            }
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }
}
