public class PremiumUser extends User
{
    private double interest;

    public PremiumUser(String name) {
        super(name);
        this.interest = 1.35;
    }

    /***
     * an interest bonus is applied to the deposited amount for premium users
     */
    @Override
    public void deposit(double amount) {
        this.getAccount().deposit(amount * interest);
    }

    /***
     * when withdrawing, the interesting bonus is deducted from the amount,
     * so the premium user withdraws less from their account.
     */
    @Override
    public void withdraw(double amount) {
        double deduction = (amount * this.interest) - amount;
        this.getAccount().withdraw(amount - deduction);
    }

}
