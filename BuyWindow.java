import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class BuyWindow
{
    private JTextArea infoArea = new JTextArea(5, 20);
    private JPanel panel = new JPanel(new FlowLayout());
    private JFrame frame = new JFrame();
    private JButton assetButton;
    private ButtonSound sound = new ButtonSound();

    public BuyWindow(User user, JButton button, JTextArea textArea) {
        frame.setTitle("Buy Assets");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400,500);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.setLocationRelativeTo(null);

        panel.add(infoArea);
        produceAssets(user, button, textArea);
        frame.add(panel);

        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent evt) {
                ((JFrame)(evt.getSource())).dispose();
                button.setEnabled(true);
            }
        });
//        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Instantiates all the available assets available
     * and buttons required to buy the assets
     * @param button button needed to be enabled after closing the window
     * @param textArea GUI text area that gets updated after a transaction
     */
    private void produceAssets(User user, JButton button, JTextArea textArea){
        JPanel buyPanel = new JPanel();
        buyPanel.setLayout(new GridLayout(0, 1));

        ArrayList<Asset> assets = new ArrayList<>();
        CryptoCurrency bitcoin = new CryptoCurrency("Bitcoin", 453.32, 469);
        CryptoCurrency ethereum = new CryptoCurrency("Ethereum", 542.89, 540.11);
        CryptoCurrency dogecoin = new CryptoCurrency("Dogecoin", 738.12, 739.50);
        Stock apple = new Stock("Apple LTD", 472.40, 479.12);
        Stock tesla = new Stock("Tesla INC", 839.0, 839.47);
        Stock amazon = new Stock("Amazon.com", 354.0, 360.52);
        assets.add(bitcoin);
        assets.add(ethereum);
        assets.add(dogecoin);
        assets.add(apple);
        assets.add(tesla);
        assets.add(amazon);
        startPriceThread(assets);

        updatePrice(assets, infoArea);

        //displays corresponding buttons to sell the displayed assets
        for (Asset asset : assets) {
            assetButton = new JButton("Buy " + asset.getName());
            assetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sound.playClickSound();
                    if (asset.getBuyPrice() > user.getAccount().getFunds()) {
                        sound.playErrorSound();
                        JOptionPane.showMessageDialog(null, "Error: Insufficient funds", "error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        user.buyAsset(asset);
                        textArea.setText("Funds: \u00A3" + user.getAccount().getFunds());
                        frame.dispose();
                    }
                    button.setEnabled(true);
                }
            });
            buyPanel.add(assetButton);
            panel.add(buyPanel);
        }
    }

    /**
     * for each asset object, a new thread is started which constantly
     * fluctuates the prices
     */
    public void startPriceThread(ArrayList<Asset> assets) {
        for (Asset asset : assets) {
            Thread thread = new Thread(asset);
            thread.start();
        }
    }

    /**
     * method makes use of the SwingWorker class to allow the GUI concurrently
     * update the price of assets as a separate thread
     */
    public void updatePrice(ArrayList<Asset> assets, JTextArea textArea) {
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (true) {
                    String info = "";
                    for (Asset asset : assets) {
                        info += asset.toString();
                    }
                    publish(info);
                }
            }

            @Override
            protected void process(List<String> infoList) {
                textArea.setText(infoList.get(infoList.size() - 1));
            }
        };
        worker.execute();
    }

}
