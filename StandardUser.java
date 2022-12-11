public class StandardUser extends User
{
    public StandardUser(String name){
        super(name);
    }

    // both methods throw an exception when negative amount is entered
    // generic deposit method
    @Override
    public void deposit(double amount) {
        this.getAccount().deposit(amount);
    }

    // generic withdraw method
    @Override
    public void withdraw(double amount) {
        this.getAccount().withdraw(amount);
    }
}
