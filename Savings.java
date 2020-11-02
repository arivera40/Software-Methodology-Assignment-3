package MVC;

/**
 * This is the class for the Savings account in
 * which we will handle monthly fee, interest,
 * and direct deposits and withdraws
 * 
 * @author Andy Rivera and Joseph Shamma
 *
 */

public class Savings extends Account {
    private boolean isLoyal;

    public Savings(double balance, Profile holder, Date dateOpen) {
        super(balance, holder, dateOpen);
        isLoyal = false;
    }
    /**
     * adding monthly fee if balance is not greater 
     * than $300
     */
    @Override
    public double monthlyFee() {
        if(this.getBalance() >= 300){
            return 0;
        }
        return 5;
    }
    /**
     * to string method
     */
    public String toString(){
        return super.toString();
    }
    /**
     * to set if account holder is loyal
     * @param isLoyal
     */
    public void setIsLoyal(boolean isLoyal){
        this.isLoyal = isLoyal;
    }
    /**
     * to get if account holder is loyal
     * @return
     */
    public boolean getIsLoyal(){
        return this.isLoyal;
    }
    /**
     * monthly interest charge for loyal customers and for
     * non-loyal customers
     */
    @Override
    public double monthlyInterest() {
        if(isLoyal == true) {
            return (this.getBalance() * (0.35 / 12)) / 100;
        }
        return (this.getBalance() * (0.25 / 12)) / 100;
    }
    /**
     * return the account type which is savings
     */
    @Override
    public String getAccountType(){
        return "Savings";
    }
}