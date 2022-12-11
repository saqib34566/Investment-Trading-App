import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Prompt extends JFrame{

    private JButton submit;

    public Prompt(JButton button){
        this.setLayout(new GridLayout(0,2));
        submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent evt) {
                ((JFrame)(evt.getSource())).dispose();
                button.setEnabled(true);
            }
        });
    }

    public void addSubmitListener(ActionListener listener){
        submit.addActionListener(listener);
    }

    public void activate(){
        this.add(submit);
        this.pack(); // Resizes to tightly fit all its components
        this.setLocationRelativeTo(null); // Centers the window on the screen
        this.setVisible(true);
    }

    public void activateNoSub() {
        this.setLocationRelativeTo(null); // Centers the window on the screen
        this.setVisible(true);
    }
}
