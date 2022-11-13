import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;

// creates a login window for the user to enter name and login
public class LoginWindow
{
    private JFrame frame;
    private JPanel panel;
    private JButton button;
    private ButtonSound sound = new ButtonSound();

    public LoginWindow(){
        run();
    }

    /***
     * creates a login window for the user to enter their name and password
     */
    private void run() {
        frame = new JFrame();
        panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Login");
        frame.setSize(300,200);
        frame.setLocationRelativeTo(null);
        frame.add(panel);

        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(10,20, 80, 25);
        panel.add(userLabel);

        JTextField textInput = new JTextField();
        textInput.setBounds(100, 20, 165, 25);
        panel.add(textInput);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(10, 50, 80, 25);
        panel.add(passLabel);

        JPasswordField passInput = new JPasswordField();
        passInput.setBounds(100, 50, 165, 25);
        panel.add(passInput);

        button = new JButton("Login");
        button.setBounds(10, 80, 80, 25);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound.playClickSound();
                frame.dispose();
                AccountWindow accWindow = new AccountWindow(textInput.getText());
            }
        });
        panel.add(button);

        WindowCloser wc = new WindowCloser();
        frame.addWindowListener(wc);
        frame.setVisible(true);
    }

}
