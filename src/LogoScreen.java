/* ΑΥΤΟ ΕΙΝΑΙ ΕΝΑ WELCOME SCREEN ΜΕ ΤΟ LOGO */
import javax.swing.*;
public class LogoScreen extends JFrame {
    private JLabel game_logo;
    public LogoScreen()
    {
        ImageIcon imgIcon = new ImageIcon("./resources/logo.png"); // icon για το παράθυρο ...
        game_logo = new JLabel();
        ImageIcon iconLogo = new ImageIcon("./resources/logo.png");
        game_logo.setIcon(iconLogo);
        /////
        this.setSize(405, 296);
        this.setTitle("LETTERS GAME");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setIconImage(imgIcon.getImage());
        this.add(game_logo);
        this.setVisible(true);
    }
}
