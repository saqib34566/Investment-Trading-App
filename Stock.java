import java.io.Serializable;

public class Stock extends Asset implements Serializable
{
    private int shares;
    private double bonus;

    public Stock(String name, double bp, double sp) {
        super(name, bp, sp);
        this.shares = 1;
        this.bonus = this.shares * 2.5;
    }

    /***
     * calculates the sell price of the stocks
     * @return returns the value of the stock sell price after a calculation
     */
    @Override
    public double calcSellPrice() {
        return (this.getSellPrice() * shares) * bonus;
    }

    /***
     * @return returns asset sell price information
     */
    @Override
    public String toStringSell() {
        return this.getName() + ": \u00A3" + this.getSellPrice() + "\nShares: " + shares + "\n--------------\n";
    }

}