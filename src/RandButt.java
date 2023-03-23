/* ΚΟΥΜΠΙ ΜΠΑΛΑΝΤΕΡ */
import javax.swing.*;
import java.awt.*;
/// 0 - 4
public class RandButt extends ButtonGameScreen {
    protected String pickedLetter;
    private final Color DEFAULT_COLOR = Color.WHITE;
    RandButt(int X_IN_GAME, int Y_IN_GAME) {
        super(X_IN_GAME, Y_IN_GAME, "?");
        color = DEFAULT_COLOR;
        createButton(color);
    }
    protected void pickLetter() {
        String inputDialog = "Παρακαλώ δώστε ένα κεφαλαίο γράμμα";
        while(true) {
            pickedLetter = JOptionPane.showInputDialog(inputDialog);
            if(!pickedLetter.isEmpty() && checkIfGreekLetter(pickedLetter))
            {
                break;
            } else // Αλλάζουμε και το input στο JOptionPane ...
            {
                inputDialog = "Ξαναπροσπαθήστε το γράμμα πρέπει να είναι του ελληνικού αλφαβήτου";
            }
        }
    }
    /* Με αυτή τη συνάρτηση βλέπουμε αν το γράμμα είναι ελληνικό ή όχι μπορεί καταλάΘος ο χρήστης να βάλει αγγλικού αλφαβήτου */
    private boolean checkIfGreekLetter(String letter)
    {
        String[] arrGreekLatin = new String[] { "Α", "Β", "Γ", "Δ", "Ε", "Ζ", "Η", "Θ", "Ι", "Κ", "Λ", "Μ", "Ν", "Ξ", "Ο", "Π", "Ρ", "Σ", "Τ", "Υ", "Φ", "Χ", "Ψ", "Ω"};
        for(int i = 0;i < arrGreekLatin.length;i++)
        {
            if(letter.equals(arrGreekLatin[i]))
                return true;
        }
        return false;
    }
    /* Θα αλλάζει το randButton στο επιλεγμένο γράμμα */
    protected void selectButton(Color COLOR) {
        System.out.println("pickedLetter" + pickedLetter);
        this.points = findPoint(pickedLetter);
        pickedLetter = pickedLetter + makeItSubscript(this.points);
        this.letter = pickedLetter;
        this.setText(this.letter);
        this.color = COLOR;
        super.createButton(this.color); // θα είναι και κίτρινο αφού υποτίθεται ότι πλέον το έχει επιλέξει
        this.setFont(new Font("Serif", Font.BOLD, 15));
        System.out.println("STATUS:A PickedLetter RAND BUTTON CREATED");
    }
    protected void unselectButton() {
        pickedLetter = ""; // Το επιλεγόμενο αδειάζει ...
        this.letter = "?"; // ξανα πίσω στο ? ...
        this.setText(this.letter);
        this.setFont(new Font("Serif", Font.BOLD, 25));
        this.color = DEFAULT_COLOR;
        this.setBackground(DEFAULT_COLOR);
    }
    @Override
    protected void createButton(Color color) {
        super.createButton(color);
        this.setFont(new Font("Serif", Font.BOLD, 25));
        System.out.println("STATUS:A RAND BUTTON CREATED");
    }
}
