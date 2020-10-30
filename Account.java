package MVC;

import java.text.DecimalFormat;

public abstract class Account {
    private Profile holder;
    private double balance;
    private Date dateOpen;

    final private String pattern = "0.00";

    public Account(double balance, Profile holder, Date dateOpen){
        this.balance = balance;
        this.holder = holder;
        this.dateOpen = dateOpen;
    }

    //decrease the balance by amount
    public void debit(double amount) {
        if(amount <= balance) {
            balance -= amount;
        }
    }

    //increase the balance by amount
    public void credit(double amount) {
        balance += amount;
    }

    public String toStringExportFormat() {
        String accountStr = "";
        String accountBalance = formatNumber(this.getBalance());
        if(this.getAccountType().equals("Money Market")){
            MoneyMarket account = (MoneyMarket)this;
            int withdrawCount = account.getWithdrawCount();
            accountStr =  "M," + this.holder.getFirstName() + ","
                    + this.holder.getLastName() + ","
                    + accountBalance + ","
                    + this.dateOpen.toString() + ","
                    + withdrawCount;
        }else if(this.getAccountType().equals("Savings")){
            Savings account = (Savings)this;
            accountStr =  "S," + this.holder.getFirstName() + ","
                    + this.holder.getLastName() + ","
                    + accountBalance + ","
                    + this.dateOpen.toString() + ","
                    + account.getIsLoyal();
        }else{
            Checking account = (Checking)this;
            accountStr =  "C," + this.holder.getFirstName() + ","
                    + this.holder.getLastName() + ","
                    + accountBalance + ","
                    + this.dateOpen.toString() + ","
                    + account.getDirectDeposit();
        }
        return accountStr;
    }

    @Override
    public String toString(){
        String accountStr = "";
        String accountBalance = formatNumber(this.getBalance());
        if(this.getAccountType().equals("Money Market")){
            MoneyMarket account = (MoneyMarket)this;
            int withdrawCount = account.getWithdrawCount();
            accountStr =  "*" + this.getAccountType() + "*"
                    + this.holder.getFirstName() + " "
                    + this.holder.getLastName() + "*"
                    + accountBalance + "*"
                    + this.dateOpen.toString() + "*"
                    + withdrawCount + " withdrawals*";
        }else if(this.getAccountType().equals("Savings")){
            Savings account = (Savings)this;
            accountStr =  "*" + this.getAccountType() + "*"
                    + this.holder.getFirstName() + " "
                    + this.holder.getLastName() + "*"
                    + accountBalance + "*"
                    + this.dateOpen.toString();
            if(account.getIsLoyal()){
                accountStr += "*special Savings account*";
            }
        }else{
            Checking account = (Checking)this;
            accountStr =  "*" + this.getAccountType() + "*"
                    + this.holder.getFirstName() + " "
                    + this.holder.getLastName() + "*"
                    + accountBalance + "*"
                    + this.dateOpen.toString();
            if(account.getDirectDeposit()){
                accountStr += "*direct deposit account";
            }
        }
        return accountStr;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Account){
            Account account = (Account) obj;
            if(account.holder.equals(this.holder) && account.getAccountType().equals(this.getAccountType())){
                return true;
            }
        }
        return false;
    }

    private String formatNumber(double balance){
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String numberStr = decimalFormat.format(balance);
        return numberStr;
    }

    public void updateBalance(double newBalance){
        this.balance = newBalance;
    }

    public abstract double monthlyInterest();

    public abstract double monthlyFee();

    public abstract String getAccountType();

    public Profile getHolder() {
        return holder;
    }

    public Date getDateOpen() {
        return dateOpen;
    }

    public double getBalance() {
        return balance;
    }

}
