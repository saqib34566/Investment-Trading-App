import java.io.Serializable;

public abstract class User implements Serializable
{
    private String name;
    private Account account;
    private Portfolio portfolio;

    public User(String name){
        this.name = name;
        this.account = new Account();
        this.portfolio = new Portfolio();
    }

    public String getName(){
        return this.name;
    }

    public abstract void deposit(double amount);

    public abstract void withdraw(double amount);

    public Account getAccount() {
        return this.account;
    }

    public Portfolio getPortfolio() {
        return this.portfolio;
    }

    /***
     * deducts funds from the users account, and adds the asset to their portfolio
     * @param asset
     */
    public void buyAsset(Asset asset) {
        this.account.withdraw(asset.getBuyPrice());
        this.portfolio.addAsset(asset);
    }

    /***
     * adds the sell price of the asset to the users account, and removes
     * the asset from their portfolio
     * @param asset asset intended to be sold
     */
    public void sellAsset(Asset asset) {
        this.portfolio.removeAsset(asset);
        double profit = asset.calcSellPrice();
        this.account.deposit(profit);
    }

}
