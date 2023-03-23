/* ΚΛΑΣΗ ΠΟΥ ΑΝΑΠΑΡΣΤΑ ΤΗΝ ΟΘΟΝΗ "ΚΑΛΟΣΟΡΙΣΜΑΤΟΣ */
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class WelcomeScreen extends JFrame implements ActionListener {
    protected GameScreen gameScreen;
    private UIManager UI = new UIManager();
    private JButton exit;
    private JButton playGame;
    public WelcomeScreen() {
        ImageIcon imgIcon = new ImageIcon("./resources/logo.png"); // icon για το παράθυρο ...
        // Αυτό είναι για το JOptionPane
        UI.put("OptionPane.background", Color.darkGray);
        UI.put("Panel.background", Color.darkGray);
        UI.put("OptionPane.messageForeground", Color.WHITE);
        UI.put("OptionPane.messageFont", new FontUIResource(new Font("Arial", Font.BOLD, 18)));
        // GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        // JPanel
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setLayout(new GridBagLayout());
        // Buttons
        exit = new JButton("Exit the game");
        exit.setBackground(Color.gray);
        exit.setPreferredSize(new Dimension(120, 30));
        exit.setFocusable(false);
        playGame = new JButton("Play the game");
        playGame.setPreferredSize(new Dimension(120, 30));
        playGame.setBackground(Color.gray);
        playGame.setFocusable(false);
        // add the buttons to the panel
        panel.add(playGame, gbc);
        panel.add(exit, gbc);
        exit.addActionListener(this);
        playGame.addActionListener(this);
        /////
        this.setSize(300, 150);
        this.setTitle("LETTERS GAME");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setIconImage(imgIcon.getImage());
        this.add(panel);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exit)
        {
            System.exit(99);
        }
        if(e.getSource() == playGame)
        {
            System.out.println("STATUS:THE GAME HAS START");
            this.dispose(); // κλείνουμε το WelcomeScreen αφού πλέον δεν το χρειαζόμαστε
            gameScreen = new GameScreen(100); // Όταν ο παίκτης επιλέξει την επιλογή (κουμπί) να παίξει το παιχνίδι τότε θα του δημιουργηθεί το GameScreen
        }
    }
}
