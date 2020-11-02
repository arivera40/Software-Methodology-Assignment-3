package MVC;

/**
 * This is the class for MoneyMarket  in
 * which we will handle monthly fee, interest,
 * and direct deposits and withdraws
 * 
 * @author Andy Rivera and Joseph Shamma
 *
 */

public class MoneyMarket extends Account {
    public MoneyMarket(double balance, Profile holder, Date dateOpen) {
        super(balance, holder, dateOpen);
        withdrawals = 0;
    }

    private int withdrawals;
    /**
     * placing a monthly fee on the account if 
     * the account holder has less than $2,500
     * and less than or equal to six
     */
    @Override
    public double monthlyFee() {
        if(this.getBalance() >= 2500 && withdrawals <= 6){
            return 0;
        }
        return 12;
    }
    /**
     * calculating the monthly interest rates for money market accounts
     */
    @Override
    public double monthlyInterest() {
        //Monthly interest rate = annual interest / payment periods
        return  (this.getBalance() * (0.65 / 12)) / 100;
    }

    public String toString(){
        return super.toString();
    }
    /**
     *  adding withdraw
     * @param withdraws
     */
    public void addWithdraw(int withdraws){
        withdrawals = withdraws;
    }
    /**
     *  getting the amount of withdrawals
     * @return
     */
    public int getWithdrawCount(){
        return withdrawals;
    }
    /**
     * getting the account type which is money market
     */
    @Override
    public String getAccountType(){
        return "Money Market";
    }
}