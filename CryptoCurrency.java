import java.io.Serializable;

public class CryptoCurrency extends Asset implements Serializable
{
    private int tradingFee = 25;
    final private double CONST = 2.0;

    public CryptoCurrency(String name, double bp, double sp) {
        super(name, bp, sp);
    }

    /***
     * calculates the sell price of the cryptocurrency
     * @return  returns the value of crypto sell price after a calculation
     */
    @Override
    public double calcSellPrice() {
        double sellVal = this.getSellPrice() + (this.getSellPrice() * (CONST / tradingFee));
        sellVal -= tradingFee;
        return sellVal;
    }

    /***
     * @return returns asset sell price information
     */
    @Override
    public String toStringSell() {
        return this.getName() + ": \u00A3" + this.getSellPrice() + "\nTrading Fee: \u00A3" + tradingFee + "\n--------------\n";
    }

}

