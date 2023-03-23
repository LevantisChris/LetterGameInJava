/* ΚΛΑΣΗ ΠΟΥ ΑΝΑΠΑΡΙΣΤΑ ΤΟ ΠΑΡΑΘΥΡΟ ΣΧΕΤΙΚΑ ΜΕ ΤΙΣ ΠΛΗΡΟΦΟΡΙΕΣ ΜΕ ΤΑ ΜΕΛΗ ΤΗΣ ΟΜΑΔΑΣ */
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class About extends JFrame implements ActionListener {

    private final Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
    private JPanel main_panel;
    private JPanel about_panel;
    private JLabel about_label;
    private JButton ok_butt;

    public About()
    {
        /////
        ImageIcon imgIcon = new ImageIcon("./resources/logo.png"); // icon για το παράθυρο ...
        this.setSize(600, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("About");
        this.setLocationRelativeTo(null);
        this.setUndecorated(false);
        this.setIconImage(imgIcon.getImage());

        main_panel = new JPanel();
        main_panel.setLayout(null);
        main_panel.setBorder(border);

        ok_butt = new JButton("Εντάξει");
        ok_butt.setBounds(216, 350, 150,50);
        ok_butt.setFocusable(false);
        ok_butt.setFont(new Font("Serif", Font.BOLD, 30));
        ok_butt.addActionListener(this);
        ok_butt.setBorder(border);

        about_label = new JLabel();
        about_label.setText("<html>--Implementation by Chris Levantis<br/>--Email:levantischris@gmail.com<br/>--Student at University of Aegean in Samos<br/>--December 2022</html>");
        about_label.setHorizontalAlignment(SwingConstants.CENTER);
        about_label.setVerticalAlignment(SwingConstants.CENTER);
        about_label.setForeground (Color.WHITE);
        about_label.setFont(new Font("Serif", Font.BOLD, 30));
        about_label.setSize(600, 300);
        about_label.setBorder(border);


        about_panel = new JPanel();
        about_panel.setLayout(null);
        about_panel.setBounds(0, 0,  500, 150);
        about_panel.add(about_label);

        main_panel.add(about_label);
        main_panel.add(ok_butt);

        this.add(main_panel);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
