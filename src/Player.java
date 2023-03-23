/* ΚΛΑΣΗ ΠΟΥ ΑΝΑΠΑΡΙΣΤΑ ΤΟΝ ΠΑΙΚΤΗ ΤΟΥ ΠΑΙΧΝΙΔΙΟΥ */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Player implements ActionListener {
    protected String name;
    protected String lastName;
    protected String phoneNum;
    protected String email;
    private JFrame frame;
    private JButton sumbit;
    private JLabel sumbit_label;
    private JLabel name_label;
    private JLabel lastname_label;
    private JLabel phone_label;
    private JLabel email_label;
    private JTextField name_text;
    private JTextField lastname_text;
    private JTextField phone_text;
    private JTextField email_text;
    public Player()
    {
        setName_LastName();
    }
    private void setName_LastName() {
        try {
            name = JOptionPane.showInputDialog(null, "Please give us your first name").toUpperCase();
        } catch (NullPointerException e)
        {
            e.getMessage();
            name = "";
        }
        System.out.println("STATUS:NAME OF THE PLAYER:"+name);
        try {
            lastName = JOptionPane.showInputDialog(null, "Please give us your Last name").toUpperCase();
        } catch (NullPointerException e)
        {
            e.getMessage();
            lastName = "";
        }
        System.out.println("STATUS:NAME OF THE PLAYER:"+lastName);

    }
    private void createInfoFrame() {
        frame = new JFrame("Set information");
        /////
        ImageIcon imgIcon = new ImageIcon("./resources/logo.png"); // icon για το παράθυρο ...
        frame.setSize(800, 400);
        frame.setLayout(new GridLayout(5,2));
        //frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(false);
        frame.setIconImage(imgIcon.getImage());

        sumbit = new JButton("Sumbit");
        setButtons(sumbit);

        sumbit_label = new JLabel("Καταχώρηση");
        setLabels(sumbit_label);
        name_label = new JLabel("Όνομα");
        setLabels(name_label);
        lastname_label  = new JLabel("Επώνυμο");
        setLabels(lastname_label);
        phone_label = new JLabel("Τηλέφωνο");
        setLabels(phone_label);
        email_label = new JLabel("Email");
        setLabels(email_label);

        name_text = new JTextField(this.name);
        setTextFeilds(name_text);
        lastname_text = new JTextField(this.lastName);
        setTextFeilds(lastname_text);
        phone_text = new JTextField(this.phoneNum);
        setTextFeilds(phone_text);
        email_text = new JTextField(this.email);
        setTextFeilds(email_text);

        frame.add(name_label);
        frame.add(name_text);

        frame.add(lastname_label);
        frame.add(lastname_text);

        frame.add(phone_label);
        frame.add(phone_text);

        frame.add(email_label);
        frame.add(email_text);

        frame.add(sumbit_label);
        frame.add(sumbit);
    }

    protected void setAdditionalinfo() {
        createInfoFrame();

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
        if(e.getSource() == sumbit){
            this.name = name_text.getText();
            this.lastName = lastname_text.getText();
            this.phoneNum = phone_text.getText();
            this.email = email_text.getText();

            System.out.println("STATUS:INFO ARE:NAME:" + this.name + ":LASTNAME:" + this.lastName + ":PHONENUM:" + this.phoneNum + ":EMAIL:" + this.email);
            JOptionPane.showMessageDialog(null, "INFO ARE\n:NAME:" + this.name + "\n:LASTNAME:" + this.lastName + "\n:PHONENUM:" + this.phoneNum + "\n:EMAIL:" + this.email);
            frame.dispose();
        }
    }
}
