package MVC;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
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
    private Button submitTransaction;
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

        String status = "";
        if(openChecking.isSelected()) {
            System.out.println("Enters checking if statement...");
            Checking account = new Checking(initialDeposit, holder, openDate);
            account.setDirectDeposit((directDeposit.isSelected()) ? true : false);
            status = account_interaction.add(account);
        }else if(openSavings.isSelected()){
            Savings account = new Savings(initialDeposit, holder, openDate);
            account.setIsLoyal((loyalCustomer.isSelected()) ? true : false);
            status = account_interaction.add(account);
        }else{
            MoneyMarket account = new MoneyMarket(initialDeposit, holder, openDate);
            status = account_interaction.add(account);
        }
        openOutput.appendText(status);
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
        Profile holder = new Profile(openFirstName.getText(), openLastName.getText());
        Date tempDate = parseDate("12/12/2012");

        String status = "";
        if(closeChecking.isSelected()) {
            Checking account = new Checking(0, holder, tempDate);
            status = account_interaction.remove(account);
        }else if(closeSavings.isSelected()){
            Savings account = new Savings(0, holder, tempDate);
            status = account_interaction.remove(account);
        }else{
            MoneyMarket account = new MoneyMarket(0, holder, tempDate);
            status = account_interaction.remove(account);
        }
        closeOutput.appendText(status);
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
        File file = chooser.showOpenDialog(stage);
        //Write code to read from the file
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
}
