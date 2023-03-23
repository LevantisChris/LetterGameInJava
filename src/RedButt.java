/* ΚΟΥΜΠΙ ΚΟΚΚΙΝΟ */
import java.awt.*;
/// 1 ή 2
public class RedButt extends ButtonGameScreen  {
    private final Color DEFAULT_COLOR = Color.RED;
    RedButt(int X_IN_GAME, int Y_IN_GAME, String letter) {
        super(X_IN_GAME, Y_IN_GAME, letter);
        this.points *= 2; //
        color = Color.RED;
        createButton(DEFAULT_COLOR);
    }
    @Override
    protected void createButton(Color color) {
        super.createButton(color);
        this.setFont(new Font("Serif", Font.BOLD, 15));
        System.out.println("STATUS:A RED BUTTON CREATED");
    }
    protected void setNewLetter(String letter) {
        this.letter = letter;
        this.points = findPoint(this.letter);
        this.letter = this.letter + makeItSubscript(this.points);
        this.points *= 2; //
        createButton(this.color);
    }
    protected void selectButton(Color COLOR) {
        this.color = COLOR;
        this.setBackground(color);
    }
    protected void unselectButton() {
        color = DEFAULT_COLOR;
        this.setBackground(DEFAULT_COLOR);
    }
}
