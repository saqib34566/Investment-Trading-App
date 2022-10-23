import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class AccountWindow
{
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel(new GridLayout(0,1));
    private JButton standardButton = new JButton("Standard User");
    private JButton premiumButton = new JButton("Premium User");
    private JButton loadSave = new JButton("Load Save");
    private ButtonSound sound = new ButtonSound();


    public AccountWindow(String name) {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //closes window pressing "X"
        frame.setSize(420,300);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.setLocationRelativeTo(null);
        JLabel label = new JLabel("Hi "+ name + ", a new account will be created");
        JLabel label2 = new JLabel("what type of user are you: ");
        JLabel label3 = new JLabel("Or you can load from you're saved file:");

        /*
         * These two action listeners are used to instantiate the appropriate user type
         * when chosen.
         */
        standardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound.playClickSound();;
                StandardUser user = new StandardUser(name);
                loadUser(user);
            }
        });

        premiumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound.playClickSound();
                PremiumUser user = new PremiumUser(name);
                loadUser(user);
            }
        });

        // reads the save file under the name of the logged in user
        loadSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound.playClickSound();
                try {
                    ObjectInputStream ois  = new ObjectInputStream(new FileInputStream(name+".txt"));
                    User user = (User)ois.readObject(); //reads the text file of the written User Object
                    ois.close();
                    AppWindow appWindow = new AppWindow(user);
                } catch (IOException | ClassNotFoundException ex) {
                    sound.playErrorSound();
                    JOptionPane.showMessageDialog(null, "No save file for this user OR reading error", "error", JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        panel.add(label);
        panel.add(label2);
        panel.add(standardButton);
        panel.add(premiumButton);
        panel.add(label3);
        panel.add(loadSave);

        frame.add(panel);
        WindowCloser wc = new WindowCloser();
        frame.addWindowListener(wc);
        frame.setVisible(true);
    }

    // instantiates a new appWindow class with the given user type
    private void loadUser(User user) {
        frame.dispose();
        AppWindow appWindow = new AppWindow(user);
    }

}
