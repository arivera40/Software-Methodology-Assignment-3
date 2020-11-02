package MVC;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This is the Controller class in
 * which we will handle how our GUI will
 * be controlled ranging from buttons pressed
 * to what will be entered by the user
 * 
 * @author Andy Rivera and Joseph Shamma
 *
 */


public class Controller {
    //FXML Variables for openAccountTab
    @FXML
    private Tab openAccountTab;
    @FXML
    private Button openAccount;
    @FXML
    private TextField openFirstName;
    @FXML
    private TextField openLastName;
    @FXML
    private TextField openAmount;
    @FXML
    private TextField date;
    @FXML
    RadioButton openChecking;
    @FXML
    RadioButton openSavings;
    @FXML
    RadioButton openMoneyMarket;
    @FXML
    private CheckBox directDeposit;
    @FXML
    private CheckBox loyalCustomer;
    @FXML
    private TextArea openOutput;
    //-------------

    //FXML Variables for closeAccountTab
    @FXML
    private Tab closeAccountTab;
    @FXML
    private Button closeAccount;
    @FXML
    private TextField closeFirstName;
    @FXML
    private TextField closeLastName;
    @FXML
    RadioButton closeChecking;
    @FXML
    RadioButton closeSavings;
    @FXML
    RadioButton closeMoneyMarket;
    @FXML
    private TextArea closeOutput;
    //-------------

    //FXML Variables for transactionTab
    @FXML
    private Tab transactionTab;
    @FXML
    private Button transactionSubmit;
    @FXML
    private TextField transactionFirstName;
    @FXML
    private TextField transactionLastName;
    @FXML
    private TextField transactionAmount;
    @FXML
    RadioButton transactionChecking;
    @FXML
    RadioButton transactionSavings;
    @FXML
    RadioButton transactionMoneyMarket;
    @FXML
    private RadioButton deposit;
    @FXML
    private RadioButton withdraw;
    @FXML
    private TextArea transactionOutput;
    //---------------

    //FXML Variables for importTab
    @FXML
    private Button importButton;
    @FXML
    private TextArea importOutput;
    //---------------

    //FXML Variables for printTab
    @FXML
    private Button exportButton;
    @FXML
    private Button printButton;
    @FXML
    private Button printByNameButton;
    @FXML
    private Button printByDateButton;
    @FXML
    private TextArea exportOutput;
    //---------------

    @FXML
    private ToggleGroup accountType;    //may not be necessary
    @FXML
    private ToggleGroup transactionType;




    protected static AccountDatabase account_interaction = new AccountDatabase();

    @FXML
    void initialize(){
        loyalCustomer.setDisable(true);
        directDeposit.setDisable(true);
    }

    @FXML
    /**
     * Event Handler for openAccount button
     * @param event
     */
    void openAccount(ActionEvent event) {
        if(!openFormCheck()) return;
        Profile holder = new Profile(openFirstName.getText(), openLastName.getText());
        Double initialDeposit = Double.parseDouble(openAmount.getText());
        Date openDate = parseDate(date.getText());

        if(openChecking.isSelected()) {
            Checking account = new Checking(initialDeposit, holder, openDate);
            account.setDirectDeposit((directDeposit.isSelected()) ? true : false);
            openOutput.appendText(account_interaction.add(account));
        }else if(openSavings.isSelected()){
            Savings account = new Savings(initialDeposit, holder, openDate);
            account.setIsLoyal((loyalCustomer.isSelected()) ? true : false);
            openOutput.appendText(account_interaction.add(account));
        }else{
            MoneyMarket account = new MoneyMarket(initialDeposit, holder, openDate);
            openOutput.appendText(account_interaction.add(account));
        }
        clearOpenFormFields();
    }
    /**
     * Event Handler for openForm Check
     * @return 
     */
    private boolean openFormCheck(){
        int dateCheck = checkDate(date.getText());
        if (!openChecking.isSelected() && !openSavings.isSelected() && !openMoneyMarket.isSelected()) {
            openOutput.appendText("Please select an Account type.\n");
            return false;
        }else if(openFirstName.getText().length() == 0){
            openOutput.appendText("Please enter your first name.\n");
            return false;
        }else if(!isAlphabetic(openFirstName.getText())){
            openOutput.appendText("Please enter only alphabetic characters for the first name field.\n");
            return false;
        }else if(openLastName.getText().length() == 0){
            openOutput.appendText("Please enter your last name.\n");
            return false;
        }else if(!isAlphabetic(openLastName.getText())){
            openOutput.appendText("Please enter only alphabetic characters for the last name field.\n");
            return false;
        }else if(dateCheck == -1){
            openOutput.appendText("Please enter a properly formatted date.\n");
            return false;
        }else if(dateCheck == 0){
            openOutput.appendText("Please enter a valid date.\n");
            return false;
        }else if(openAmount.getText().length() == 0){
            openOutput.appendText("Please enter an amount you wish to deposit, otherwise enter 0.\n");
            return false;
        }else if(!isNumeric(openAmount.getText())){
            openOutput.appendText("Please enter numeric values only for the deposit field.\n");
            return false;
        }
        return true;
    }

    @FXML
    /**
     * Event Handler for closeAccount button
     * @param event
     */
    void closeAccount(ActionEvent event) {
        if (!closeFormCheck()) return;
        Profile holder = new Profile(closeFirstName.getText(), closeLastName.getText());
        Date tempDate = parseDate("12/12/2012");

        if(closeChecking.isSelected()) {
            Checking account = new Checking(0, holder, tempDate);
            closeOutput.appendText(account_interaction.remove(account));
        }else if(closeSavings.isSelected()){
            Savings account = new Savings(0, holder, tempDate);
            closeOutput.appendText(account_interaction.remove(account));
        }else{
            MoneyMarket account = new MoneyMarket(0, holder, tempDate);
            closeOutput.appendText(account_interaction.remove(account));
        }
        clearCloseFormFields();
    }
    /**
     * Event Handler for closeForm Check
     * @return 
     */
    private boolean closeFormCheck(){
        if (!closeChecking.isSelected() && !closeSavings.isSelected() && !closeMoneyMarket.isSelected()) {
            closeOutput.appendText("Please select an Account type.\n");
            return false;
        }else if(closeFirstName.getText().length() == 0){
            closeOutput.appendText("Please enter your first name.\n");
            return false;
        }else if(!isAlphabetic(closeFirstName.getText())){
            closeOutput.appendText("Please enter only alphabetic characters for the first name field.\n");
            return false;
        }else if(closeLastName.getText().length() == 0){
            closeOutput.appendText("Please enter your last name.\n");
            return false;
        }else if(!isAlphabetic(closeLastName.getText())) {
            closeOutput.appendText("Please enter only alphabetic characters for the last name field.\n");
            return false;
        }
        return true;
    }

    @FXML
    /**
     * Event Handler for transactionSubmit button
     * @param event
     */
    void transactionSubmit(ActionEvent event) {
        if (!transactionFormCheck()) return;
        Profile holder = new Profile(transactionFirstName.getText(), transactionLastName.getText());
        Date tempDate = parseDate("12/12/2012");
        Double amount = Double.parseDouble(transactionAmount.getText());

        String status = "";
        if(transactionChecking.isSelected()) {
            Checking account = new Checking(0, holder, tempDate);
            status = (deposit.isSelected()) ? account_interaction.deposit(account, amount) : account_interaction.withdrawal(account, amount);
        }else if(transactionSavings.isSelected()){
            Savings account = new Savings(0, holder, tempDate);
            status = (deposit.isSelected()) ? account_interaction.deposit(account, amount) : account_interaction.withdrawal(account, amount);
        }else{
            MoneyMarket account = new MoneyMarket(0, holder, tempDate);
            status = (deposit.isSelected()) ? account_interaction.deposit(account, amount) : account_interaction.withdrawal(account, amount);
        }
        transactionOutput.appendText(status);
        clearTransactionFormFields();
    }
    /**
     * Event Handler for openForm Check
     * @return 
     */
    private boolean transactionFormCheck(){
        if (!transactionChecking.isSelected() && !transactionSavings.isSelected() && !transactionMoneyMarket.isSelected()) {
            transactionOutput.appendText("Please select an Account type.\n");
            return false;
        }else if(transactionFirstName.getText().length() == 0){
            transactionOutput.appendText("Please enter your first name.\n");
            return false;
        }else if(!isAlphabetic(transactionFirstName.getText())){
            transactionOutput.appendText("Please enter only alphabetic characters for the first name field.\n");
            return false;
        }else if(transactionLastName.getText().length() == 0){
            transactionOutput.appendText("Please enter your last name.\n");
            return false;
        }else if(!isAlphabetic(transactionLastName.getText())){
            transactionOutput.appendText("Please enter only alphabetic characters for the last name field.\n");
            return false;
        }else if(transactionAmount.getText().length() == 0){
            transactionOutput.appendText("Please enter an amount you wish to deposit or withdraw, otherwise enter 0.\n");
            return false;
        }else if(!isNumeric(transactionAmount.getText())){
            transactionOutput.appendText("Please enter numeric values only for the amount field.\n");
        }
        return true;
    }

    @FXML
    /**
     * Event Handler for importFile button
     * @param event
     */
    void importFile(ActionEvent event){
        if(importOutput.getText().length() > 0) importOutput.clear();
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Text File for the Import");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        //Write code to read from the file
        try {
            File inputFile = chooser.showOpenDialog(stage);
            if(!inputFile.getName().endsWith(".txt")){
                importOutput.appendText("Source file is not a Text File.\n");
                return;
            }
            Scanner reader = new Scanner(inputFile);
            for(int lineNum = 1; reader.hasNextLine(); lineNum++){
                String line = reader.nextLine();
                String token[] = line.split(",");

                //Base check before any further code is executed
                if(token.length != 6){
                    importOutput.appendText("Incorrect number of arguments in line " + lineNum +"\n");
                    continue;
                }

                try {
                    Profile holder = new Profile(token[1], token[2]);
                    Double balance = Double.parseDouble(token[3]);

                    //Checks if date is formatted correctly, if so Date object is created
                    int validateDate = checkDate(token[4]);
                    if (validateDate == -1) {
                        importOutput.appendText("Input type mismatch for date field in line " + lineNum + "\n");
                        continue;
                    } else if (validateDate == 0) {
                        importOutput.appendText("Invalid date field in line " + lineNum + "\n");
                        continue;
                    }
                    Date date = parseDate(token[4]);

                    //Checks which Account Type to create
                    if (token[0].equals("C")) {
                        //Validates last argument is of type boolean
                        if(token[5].equals("true") || token[5].equals("false")) {
                            Checking account = new Checking(balance, holder, date);
                            account.setDirectDeposit(Boolean.parseBoolean(token[5]));
                            importOutput.appendText(account_interaction.add(account));
                        }else{
                            importOutput.appendText("Input type mismatch for Direct Deposit field in line " + lineNum + "\n");
                        }
                    } else if (token[0].equals("S")) {
                        //Validates last argument is of type boolean
                        if(token[5].equals("true") || token[5].equals("false")){
                            Savings account = new Savings(balance, holder, date);
                            account.setIsLoyal(Boolean.parseBoolean(token[5]));
                            importOutput.appendText(account_interaction.add(account));
                        }else{
                            importOutput.appendText("Input type mismatch for Loyal Customer field in line " + lineNum + "\n");
                        }
                    } else if (token[0].equals("M")) {
                        MoneyMarket account = new MoneyMarket(balance, holder, date);
                        account.addWithdraw(Integer.parseInt(token[5]));
                        importOutput.appendText(account_interaction.add(account));
                    } else {  //Unknown Account Type
                        importOutput.appendText("Unknown Account type in line " + lineNum + "\n");
                    }
                //Catches any exception that may occur when parsing a double for balance and parsing an integer for withdraw count
                }catch(NumberFormatException e){
                    importOutput.appendText("Input type mismatch in line " + lineNum + "\n");
                }
            }
            reader.close();
        }catch(FileNotFoundException e) {
            importOutput.appendText("File not found.\n");
        }catch(NullPointerException e){
            importOutput.appendText("No file imported.\n");
        }
    }

    @FXML
    /**
     * Event Handler for exportFile button
     * @param event
     */
    void exportFile(ActionEvent event){
        if(exportOutput.getText().length() > 0) exportOutput.clear();
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Specify Text File to Export to");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();

        try{
            File outputFile = chooser.showSaveDialog(stage);
            if(!outputFile.getName().endsWith(".txt")){
                exportOutput.appendText("Target file is not a Text File. File remains unchanged.\n");
                return;
            }
            FileWriter writer = new FileWriter(outputFile);
            String status = account_interaction.writeAccounts();
            if(status.equals("Database is empty.")){
                exportOutput.appendText(status + " File remains unchanged.\n");
            }else{
                exportOutput.appendText("File has been updated.\n");
                writer.write(status);
            }
            writer.close();
        }catch(IOException e){
            exportOutput.appendText("Cannot write to File.\n");
        }catch(NullPointerException e){
            exportOutput.appendText("No file specified for export.\n");
        }
    }
/**
 * Print accounts
 * @param event
 */
    @FXML
    void print(ActionEvent event){
        if(exportOutput.getText().length() > 0) exportOutput.clear();
        exportOutput.appendText(account_interaction.printAccounts());
    }
    /**
     * Print accounts by their name
     * @param event
     */
    @FXML
    void printByName(ActionEvent event){
        if(exportOutput.getText().length() > 0) exportOutput.clear();
        exportOutput.appendText(account_interaction.printByLastName());
    }
    /**
     * Print accounts by their date
     * @param event
     */
    @FXML void printByDate(ActionEvent event){
        if(exportOutput.getText().length() > 0) exportOutput.clear();
        exportOutput.appendText(account_interaction.printByDateOpen());
    }
    /**
     * 
     * This will disable the check box because you can not be a loyal customer
     * or use direct deposit while creating a money market account
     * @param moneyMarket
     */
    @FXML
    void disableCheckBox(ActionEvent moneyMarket){
        if(openChecking.isSelected()){
            if(directDeposit.isSelected()) directDeposit.setSelected(false);
            directDeposit.setDisable(false);
            loyalCustomer.setDisable(true);
        }else if(openSavings.isSelected()){
            if(loyalCustomer.isSelected()) loyalCustomer.setSelected(false);
            loyalCustomer.setDisable(false);
            directDeposit.setDisable(true);
        }else{
            if(directDeposit.isSelected()) directDeposit.setSelected(false);
            if(loyalCustomer.isSelected()) loyalCustomer.setSelected(false);
            loyalCustomer.setDisable(true);
            directDeposit.setDisable(true);
        }
    }
    /**
     * To make sure you are only using letter in the name fields
     * @param name
     * @return
     */
    private boolean isAlphabetic(String name){
        return name.matches("^[a-zA-Z]*$");
    }
    /**
     * To make sure deposit and date fields can only accept numbers 
     * and certain symbols
     * @param amount
     * @return
     */
    private boolean isNumeric(String amount){
        return amount.matches("[0-9]*\\.?[0-9]+$");
    }
    /**
     * clears the open form fields
     */
    private void clearOpenFormFields(){
        if(openChecking.isSelected()) openChecking.setSelected(false);
        if(openSavings.isSelected()) openSavings.setSelected(false);
        if(openMoneyMarket.isSelected()) openMoneyMarket.setSelected(false);
        openFirstName.clear();
        openLastName.clear();
        date.clear();
        openAmount.clear();
        if(directDeposit.isSelected()) directDeposit.setSelected(false);
        if(loyalCustomer.isSelected()) directDeposit.setSelected(false);
    }
    /**
     * clears the close form fields
     */
    private void clearCloseFormFields(){
        if(closeChecking.isSelected()) openChecking.setSelected(false);
        if(closeSavings.isSelected()) openSavings.setSelected(false);
        if(closeMoneyMarket.isSelected()) openMoneyMarket.setSelected(false);
        closeFirstName.clear();
        closeLastName.clear();

    }
    /**
     * clearing the transaction form fields so that after
     * someone submits they can have a clean slate
     */
    private void clearTransactionFormFields(){
        if(transactionChecking.isSelected()) transactionChecking.setSelected(false);
        if(transactionSavings.isSelected()) transactionSavings.setSelected(false);
        if(transactionMoneyMarket.isSelected()) transactionMoneyMarket.setSelected(false);
        transactionFirstName.clear();
        transactionLastName.clear();
        transactionAmount.clear();
        if(deposit.isSelected()) deposit.setSelected(false);
        if(withdraw.isSelected()) withdraw.setSelected(false);
    }
    /**
     * 
     * parsing the date to have it read correctly
     * @param date
     * @return
     */
    private Date parseDate(String date){
        StringTokenizer tokens = new StringTokenizer(date, "/");
        int month = Integer.parseInt(tokens.nextToken());
        int day = Integer.parseInt(tokens.nextToken());
        int year = Integer.parseInt(tokens.nextToken());

        Date openDate = new Date(year, month, day);
        return openDate;
    }
    /** 
     * checking to see if the date format is correct and if it is a valid date
     * @param date
     * @return
     */
    public int checkDate(String date){
        int count = 0;
        for(int i=0; i < date.length(); i++){
            if(date.charAt(i) == '/') count++;
        }
        if(count != 2){
            return -1;  //Input data type mismatch
        }
        StringTokenizer tokens = new StringTokenizer(date, "/");
        int day = 0;
        int month = 0;
        int year = 0;
        for(int i=0; tokens.hasMoreTokens(); i++){
            String temp = tokens.nextToken();
            try{
                if(i == 0){
                    month = Integer.parseInt(temp);
                }else if(i == 1){
                    day = Integer.parseInt(temp);
                }else if(i == 2){
                    year = Integer.parseInt(temp);
                }else{
                    return -1;	//malformed
                }
            }catch(NumberFormatException e){
                return -1;  //Input data type mismatch
            }
        }
        if(new Date(year, month, day).isValid()){
            return 1;  //valid date
        }else{
            return 0;  //invalid date
        }
    }
}