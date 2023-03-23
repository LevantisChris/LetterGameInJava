/* ΑΥΤΗ Η ΣΥΝΑΡΤΗΣΗ ΑΝΑΠΑΡΙΣΑ ΤΟ ΑΣΠΡΟ ΚΟΥΜΠΙ */
import java.awt.*;
public class WhiteButt extends ButtonGameScreen {
    private final Color DEFAULT_COLOR = Color.WHITE;
    WhiteButt(int X_IN_GAME, int Y_IN_GAME, String letter) {
        super(X_IN_GAME, Y_IN_GAME, letter);
        color = DEFAULT_COLOR;
        createButton(color);
    }
    @Override
    protected void createButton(Color color) {
        super.createButton(color);
        this.setFont(new Font("Serif", Font.BOLD, 15));
        System.out.println("STATUS:A WHITE BUTTON CREATED");
    }
    protected void setNewLetter(String letter) {
        this.letter = letter;
        this.points = findPoint(this.letter);
        this.letter = this.letter + makeItSubscript(this.points);
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
