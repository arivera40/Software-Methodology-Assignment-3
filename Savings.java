package MVC;

public class Savings extends Account {
    public int v;
    private boolean isLoyal;

    public Savings(double balance, Profile holder, Date dateOpen) {
        super(balance, holder, dateOpen);
        isLoyal = false;
    }

    @Override
    public double monthlyFee() {
        if(this.getBalance() >= 300){
            return 0;
        }
        return 5;
    }

    public void setIsLoyal(boolean isLoyal){
        this.isLoyal = isLoyal;
    }

    public boolean getIsLoyal(){
        return this.isLoyal;
    }

    @Override
    public double monthlyInterest() {
        if(isLoyal == true) {
            return (this.getBalance() * (0.35 / 12)) / 100;
        }
        return (this.getBalance() * (0.25 / 12)) / 100;
    }

    @Override
    public String getAccountType(){
        return "Savings";
    }
}
