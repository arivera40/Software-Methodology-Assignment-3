package MVC;

import java.text.DecimalFormat;

/**
 * This is the class for AccountDatabase
 * which will be used for methods such as adding accounts, removing accounts,
 * growing the amount of accounts and formatting decimals correctly.
 * 
 * @author Andy Rivera and Joseph Shamma
 *
 */


public class AccountDatabase {
    private Account[] accounts;
    private int size;

    final private String pattern = "0.00"; // setting the decimal format

    public AccountDatabase() {
        accounts = new Account[5];
        size = 0;                  // setting the initial size of the accounts
    }

    /**
     *  method to to find account
     */
    private int find(Account account) {
        if(size > 0){
            for( int i = 0; i < size; i++ ){
                if(accounts[i].equals(account)){
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     *  method to grow account by 5 when amount of accounts reach five
     */
    private void grow() {
        Account[] moreAccounts = new Account[accounts.length + 5];
        System.arraycopy(accounts, 0, moreAccounts, 0, accounts.length);
        accounts = moreAccounts;
    }

    /**
     * Add method adds an account to the database
     * return false if account already exists, otherwise add account and return true
     * @param account
     * @return
     */
    public String add(Account account) {
        if(find(account) == -1){
            accounts[size] = account;
            size++;
            if(size == accounts.length) {
                grow();
            }
            return "Account opened and added to the database.\n";
        }
        return "Account is already in the database.\n";
    }

    /**
     * Remove method removes account from our database
     * return false if account doesn't exists
     * @param account
     * @return
     */
    public String remove(Account account) {
        int accountIndex = find(account);
        if(accountIndex != -1) {
            accounts[accountIndex] = accounts[size - 1];
            accounts[size - 1] = null;
            size--;
            return "Account closed and removed from the database.\n";
        }
        return "Account does not exist.\n";
    }

    /**
     * deposit method adds money to account balance
     * return false if account doesn’t exist
     * @param account
     * @param amount
     * @return
     */
    public String deposit(Account account, double amount) {
        int accountIndex = find(account);
        if(accountIndex != -1){
            accounts[accountIndex].credit(amount);
            return amount + " deposited to account.\n";
        }
        return "Account does not exist.\n";
    }
    /**
     * Withdraw method removes amount from balance
     * if account does not exist return false
     * if insufficient funds return message "Insufficient funds"
     * @param account
     * @param amount
     * @return
     */
    public String withdrawal(Account account, double amount) {
        int accountIndex = find(account);
        if(accountIndex == -1){
            return "Account does not exist.\n";
        }else if(accounts[accountIndex].getBalance() >= amount) {
            accounts[accountIndex].debit(amount);
            if(account.getAccountType().equals("Money Market")){
                MoneyMarket tempAccount = (MoneyMarket)accounts[accountIndex];
                tempAccount.addWithdraw(1);
            }
            return amount + " withdrawn from account.\n";
        }else {
            return "Insufficient funds.\n";
        }
    }

    /**
     *  formatting the decimal to pattern set
     * @param number
     * @return
     */
    private String formatNumber(double number){
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String numberStr = decimalFormat.format(number);
        return numberStr;
    }

    /**
     *  method to sort accounts by the date they were opened
     */
    private void sortByDateOpen() {
        Account tempAccount;
        for(int i=0; i < size; i++){
            for(int j=i+1; j < size; j++){
                if(accounts[i].getDateOpen().compareTo(accounts[j].getDateOpen()) == 1){
                    tempAccount = accounts[i];
                    accounts[i] = accounts[j];
                    accounts[j] = tempAccount;
                }
            }
        }
    }
    /**
     * method to sort accounts by holder's last name
     */
    private void sortByLastName() {
        Account tempAccount;
        int compare = 0;
        for(int i=0; i < size; i++){
            for(int j=0; j < size; j++){
                String lnameI = accounts[i].getHolder().getLastName();
                String fnameI = accounts[i].getHolder().getFirstName();
                String lnameJ = accounts[j].getHolder().getLastName();
                String fnameJ = accounts[j].getHolder().getFirstName();
                compare = lnameI.compareToIgnoreCase(lnameJ);
                if(compare < 0){
                    tempAccount = accounts[i];
                    accounts[i] = accounts[j];
                    accounts[j] = tempAccount;
                }else if(compare == 0){
                    compare = fnameI.compareTo(fnameJ);
                    if(compare < 0){
                        tempAccount = accounts[i];
                        accounts[i] = accounts[j];
                        accounts[j] = tempAccount;
                    }
                }
            }
        }
    }
    /**
     *  method to print accounts by when they were opened
     * @return
     */
    public String printByDateOpen() {
        String status = "";
        if(size > 0){
            status += "--Printing statements by date opened--\n";
            sortByDateOpen();
            for(int i=0; i < size; i++){
                double monthlyInterest = accounts[i].monthlyInterest();
                double monthlyFee = accounts[i].monthlyFee();
                double newBalance = accounts[i].getBalance() - monthlyFee + monthlyInterest;
                String monthlyInterestStr = formatNumber(monthlyInterest);
                String monthlyFeeStr = formatNumber(monthlyFee);
                String newBalanceStr = formatNumber(newBalance);
                status += accounts[i].toString() + "\n";
                status += "-interest: $ " + monthlyInterestStr + "\n";
                status += "-fee: $ " + monthlyFeeStr + "\n";
                status += "-new balance: $ " + ((i != size-1) ? newBalanceStr + "\n" : newBalanceStr) + "\n";
                accounts[i].updateBalance(newBalance);
            }
            status += "--end of printing--\n";
        }else{
            status += "Database is empty.\n";
        }
        return status;
    }
    /**
     *  method to print accounts by the holder's last name
     * @return
     */
    public String printByLastName() {
        String status = "";
        if(size > 0){
            status += "--Printing statements by last name--\n";
            sortByLastName();
            for(int i=0; i < size; i++){
                double monthlyInterest = accounts[i].monthlyInterest();
                double monthlyFee = accounts[i].monthlyFee();
                double newBalance = accounts[i].getBalance() - monthlyFee + monthlyInterest;
                String monthlyInterestStr = formatNumber(monthlyInterest);
                String monthlyFeeStr = formatNumber(monthlyFee);
                String newBalanceStr = formatNumber(newBalance);
                status += accounts[i].toString() + "\n";
                status += "-interest: $ " + monthlyInterestStr + "\n";
                status += "-fee: $ " + monthlyFeeStr + "\n";
                status += "-new balance: $ " + ((i != size-1) ? newBalanceStr + "\n" : newBalanceStr) + "\n";
                accounts[i].updateBalance(newBalance);
            }
            status += "--end of printing--\n";
        }else{
            status = "Database is empty.\n";
        }
        return status;
    }

    /**
     *  method to print all accounts in the database
     * @return
     */
    
    public String printAccounts() {
        String output = "";
        if(size > 0){
            output += "--Listing accounts in the database--\n";
            for(int i=0; i < size; i++){
                output += accounts[i].toString() + "\n";
            }
            output += "--end of listing--\n";
        }else{
            output = "Database is empty.\n";
        }
        return output;
    }

    public String writeAccounts(){
        String output = "";
        if(size > 0){
            for(int i=0; i < size; i++){
                output += (i == size - 1) ? accounts[i].toStringExportFormat() : accounts[i].toStringExportFormat() + "\n";
            }
        }else{
            output = "Database is empty.";
        }
        return output;
    }

}
