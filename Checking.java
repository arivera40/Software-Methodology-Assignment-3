package MVC;

public class Checking extends Account {
    public Checking(double balance, Profile holder, Date dateOpen) {
        super(balance, holder, dateOpen);
    }

    private boolean directDeposit;

    @Override
    public double monthlyFee() {
        if(this.directDeposit || this.getBalance() >= 1500){
            return 0;
        }
        return 25;
    }

    public String toString(){
        return super.toString();
    }

    @Override
    public double monthlyInterest() {
        return (this.getBalance() * (0.05 / 12)) / 100;
    }

    public boolean isDirectDeposit() {
        return directDeposit;
    }

    public void setDirectDeposit(boolean directDeposit){
        this.directDeposit = directDeposit;
    }

    public boolean getDirectDeposit(){
        return this.directDeposit;
    }

    @Override
    public String getAccountType(){
        return "Checking";
    }

}

