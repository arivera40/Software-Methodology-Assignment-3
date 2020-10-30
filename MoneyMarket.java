package MVC;

public class MoneyMarket extends Account {
    public MoneyMarket(double balance, Profile holder, Date dateOpen) {
        super(balance, holder, dateOpen);
        withdrawals = 0;
    }

    private int withdrawals;

    @Override
    public double monthlyFee() {
        if(this.getBalance() >= 2500 && withdrawals > 6){
            return 0;
        }
        return 12;
    }

    @Override
    public double monthlyInterest() {
        //Monthly interest rate = annual interest / payment periods
        return  (this.getBalance() * (0.65 / 12)) / 100;
    }

    public String toString(){
        return super.toString();
    }

    public void addWithdraw(int withdraws){
        withdrawals = withdraws;
    }

    public int getWithdrawCount(){
        return withdrawals;
    }

    @Override
    public String getAccountType(){
        return "Money Market";
    }
}
