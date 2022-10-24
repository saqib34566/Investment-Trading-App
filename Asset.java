import javax.swing.*;
import java.io.Serializable;
import java.util.Random;

public abstract class Asset implements Serializable, Runnable
{
    private final String name;
    private double buyPrice;
    private double sellPrice;

    public Asset(String name, double bp, double sp){
        this.name = name;
        this.buyPrice = bp;
        this.sellPrice = sp;
    }

    public String getName(){
        return this.name;
    }

    public double getBuyPrice(){
        return this.buyPrice;
    }

    public double getSellPrice(){
        return this.sellPrice;
    }

    //returns the value of sell price after a calculation
    public abstract double calcSellPrice();

    //returns asset sell price information
    public abstract String toStringSell();

    /**
     * @return returns the purchase information of the asset
     */
    @Override
    public String toString(){
        return name + ":" + "\nbuy price: " + this.getBuyPrice() + "\nsell price: " + this.getSellPrice() + "\n--------------\n";
    }

    /**
     * generates a random number between -300 and 300, and adds this to num
     * @param num double value to be changed
     * @return returns num after being changed
     */
    public Double randomGen(double num) {
        double min = -5;
        double max = 5;
        Random rNum = new Random();
        double randVal = min + (max - min) * rNum.nextDouble();
        num += randVal;
        return Math.round(num*100.0)/100.0;
    }

    /**
     * thread run method continuously fluctuates the buy and sell price
     * of the asset with a delay of 4 seconds
     */
    @Override
    public void run() {
        while (true) {
            this.buyPrice = randomGen(this.buyPrice);
            this.sellPrice = randomGen(this.sellPrice);

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {}
        }
    }


}
