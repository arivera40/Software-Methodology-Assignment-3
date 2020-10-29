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
    private Button printButton;
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
        System.out.println("Enters initialize");
        loyalCustomer.setDisable(true);
        directDeposit.setDisable(true);
    }

    @FXML
    /**
     * Event Handler for openAccount button
     * @param event
     */
    void openAccount(ActionEvent event) {
        System.out.println("Enters openAccount control...");
        System.out.println(openFirstName.getText());
        if (!openChecking.isSelected() && !openSavings.isSelected() && !openMoneyMarket.isSelected()) {
            System.out.println("Enters isSelected if statement...");
            openOutput.appendText("Please select an Account type\n");
            return;
        }
        Profile holder = new Profile(openFirstName.getText(), openLastName.getText());
        Double initialDeposit = Double.parseDouble(openAmount.getText());
        Date openDate = parseDate(date.getText());

        if(openChecking.isSelected()) {
            System.out.println("Enters checking if statement...");
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
    }

    @FXML
    /**
     * Event Handler for closeAccount button
     * @param event
     */
    void closeAccount(ActionEvent event) {
        System.out.println("Enters closeAccount control...");
        if (!closeChecking.isSelected() && !closeSavings.isSelected() && !closeMoneyMarket.isSelected()) {
            System.out.println("Enters isSelected if statement...");
            closeOutput.appendText("Please select an Account type\n");
            return;
        }
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
    }

    @FXML
    /**
     * Event Handler for transactionSubmit button
     * @param event
     */
    void transactionSubmit(ActionEvent event) {
        System.out.println("Enters transactionSubmit control...");
        if (!transactionChecking.isSelected() && !transactionSavings.isSelected() && !transactionMoneyMarket.isSelected()) {
            System.out.println("Enters isSelected if statement...");
            closeOutput.appendText("Please select an Account type\n");
            return;
        }
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
    }

    @FXML
    /**
     * Event Handler for importFile button
     * @param event
     */
    void importFile(ActionEvent event){
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
                        account.addWithdrawl(Integer.parseInt(token[5]));
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
        System.out.println("Enters export");
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
            String status = account_interaction.printAccounts();
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

    @FXML
    void disableCheckBox(ActionEvent moneyMarket){
        if(openChecking.isSelected()){
            directDeposit.setDisable(false);
            loyalCustomer.setDisable(true);
        }else if(openSavings.isSelected()){
            loyalCustomer.setDisable(false);
            directDeposit.setDisable(true);
        }else{
            loyalCustomer.setDisable(true);
            directDeposit.setDisable(true);
        }
    }

    private Date parseDate(String date){
        StringTokenizer tokens = new StringTokenizer(date, "/");
        int month = Integer.parseInt(tokens.nextToken());
        int day = Integer.parseInt(tokens.nextToken());
        int year = Integer.parseInt(tokens.nextToken());

        Date openDate = new Date(year, month, day);
        return openDate;
    }

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
