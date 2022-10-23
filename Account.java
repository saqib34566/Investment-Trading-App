import java.io.Serializable;

public class Account implements Serializable
{
    private double funds;

    public Account(){
        this.funds = 0;
    }

    public double getFunds(){
        decimalRound(this.funds);
        return this.funds;
    }

    public void deposit(double amount){
        this.funds += amount;
        decimalRound(this.funds);
    }

    // throws an exception when withdrawing more than available
    public void withdraw(double amount) {
        this.funds -= amount;
        decimalRound(this.funds);
    }

    // rounds doubles to two decimal places
    private void decimalRound(double num) {
       this.funds = Math.round(num*100.0)/100.0;
    }

    // returns the amount of funds the account has
    @Override
    public String toString(){
        return "Your Funds: \u00A3" + this.funds;
    }
}
