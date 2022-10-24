import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AppWindow
{
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JTextArea textArea = new JTextArea(5 ,18);
    private JTextArea portInfo;
    private ButtonSound sound = new ButtonSound();

    public AppWindow(User user){
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        textArea.setText("Funds: \u00A3" + user.getAccount().getFunds());
        textArea.setSize(400, 400);
        textArea.setFont(textArea.getFont().deriveFont(30f));
        textArea.setEditable(false);

        //create button to view portfolio
        JButton viewPortfolio = new JButton("View Portfolio");
        viewPortfolio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound.playClickSound();
                viewPortfolio.setEnabled(false);

                Prompt prompt = new Prompt(viewPortfolio);
                prompt.setSize(450, 450);
                prompt.setLayout(new FlowLayout());
                prompt.setTitle("Portfolio");
                JPanel sellPanel = new JPanel(new GridLayout(0,1));
                portInfo = new JTextArea(10, 18);
                updatePortfolio(user);
                portInfo.setFont(portInfo.getFont().deriveFont(15f));

                for (Asset asset : user.getPortfolio().getAssets()) {
                   JButton assetButton = new JButton("Sell " + asset.getName());
                   assetButton.addActionListener(new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent e) {
                           sound.playClickSound();
                           user.sellAsset(asset); // selling the asset
                           sellPanel.remove(assetButton);
                           updateTextArea(user);
                           updatePortfolio(user);
                       }
                   });
                   sellPanel.add(assetButton);
                }

                prompt.add(portInfo);
                prompt.add(sellPanel);
                prompt.pack();
                prompt.activateNoSub();
            }
        });
        panel.add(viewPortfolio);

        //create button to add Funds
        JButton addFunds = new JButton("Add Funds");
        addFunds.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound.playClickSound();
                addFunds.setEnabled(false);
                Prompt prompt = new Prompt(addFunds);
                JTextField amount = new JTextField("Enter fund amount");

                prompt.add(amount);
                prompt.addSubmitListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sound.playClickSound();
                        try {
                            if (Double.parseDouble(amount.getText()) < 0){ //error pop up window for negative input
                                errorPopup("Error: Negative number entered");
                            } else {
                                double fundAmount = Double.parseDouble(amount.getText());
                                user.deposit(fundAmount);
                                updateTextArea(user);
                            }
                        } catch (NumberFormatException amountException) {
                            errorPopup("Error: Invalid number entered");
                        }
                        addFunds.setEnabled(true);//re-enabling the enter fund button
                    }
                });
                prompt.activate();
            }
        });
        panel.add(addFunds);

        //create a button for withdrawing funds
        JButton withdraw = new JButton("Withdraw");
        withdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound.playClickSound();
                withdraw.setEnabled(false);
                Prompt prompt = new Prompt(withdraw);
                JTextField amount = new JTextField("Enter amount to withdraw");

                prompt.add(amount);
                prompt.addSubmitListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sound.playClickSound();
                        try {
                            if (Double.parseDouble(amount.getText()) < 0) {
                                errorPopup("Error: Negative number entered");
                            } else if(Double.parseDouble(amount.getText()) > user.getAccount().getFunds()){
                                errorPopup("Error: Insufficient funds");
                            } else {
                                double withdrawAmount = Double.parseDouble(amount.getText());
                                user.withdraw(withdrawAmount);
                                updateTextArea(user);
                            }
                        } catch ( NumberFormatException amountException) {
                            errorPopup("Error: Invalid number entered");
                        }
                        withdraw.setEnabled(true);
                    }
                });
                prompt.activate();
            }
        });
        panel.add(withdraw);

        // create a button for buying assets
        JButton buyAsset = new JButton("Buy");
        buyAsset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound.playClickSound();
                buyAsset.setEnabled(false);
                BuyWindow buyWindow = new BuyWindow(user, buyAsset, textArea);
            }
        });

        buyAsset.setEnabled(true);
        panel.add(buyAsset);
        panel.add(textArea);

        // button allows for the user to save the current state of the app to a .txt file
        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound.playClickSound();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(user.getName()+".txt"));
                    oos.writeObject(user);
                    oos.close();
                } catch (IOException ex) {
                    errorPopup("Save Error");
                }

            }
        });

        panel.add(save);
        frame.add(panel);
        WindowCloser wc = new WindowCloser();
        frame.addWindowListener(wc);
        frame.setVisible(true);
    }

    /**
     * displays an error prompt window and plays the error sound
     * whenever an error is encountered
     */
    public void errorPopup(String message){
//        errorSound.playSound(errorFile);
        sound.playErrorSound();
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    //used to update the user's funds after an action
    public void updateTextArea(User user) {
        textArea.setText("Funds: \u00A3" + user.getAccount().getFunds());
    }

    /**
     * updates the text output when viewing portfolio
     * to show the changes to output when buying/selling assets
     */
    public void updatePortfolio(User user) {
        portInfo.setText(user.getName() + "'s Portfolio\n" + user.getAccount().toString()+"\n"+
                "\nOwned Assets:"+"\n----------\n"+ user.getPortfolio().viewPortfolio());
    }
}
