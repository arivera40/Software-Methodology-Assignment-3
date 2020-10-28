package MVC;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.event.ActionEvent;

import java.util.StringTokenizer;


public class Controller {
    @FXML
    private Tab openAccountTab;

    @FXML
    private Tab closeAccountTab;

    @FXML
    private Button openAccount;

    @FXML
    private Button closeAccount;

    @FXML
    private Button submitDepositWithdraw;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField amount;

    @FXML
    private TextField date;

    @FXML
    private ToggleGroup accountType;

    @FXML
    private ToggleGroup transactionType;

    @FXML
    private RadioButton checking;

    @FXML
    private RadioButton savings;

    @FXML
    private RadioButton moneyMarket;

    @FXML
    private CheckBox directDeposit;

    @FXML
    private CheckBox loyalCustomer;

    @FXML
    private RadioButton deposit;

    @FXML
    private RadioButton withdraw;

    @FXML
    private TextArea output;

    protected static AccountDatabase account_interaction = new AccountDatabase();

    @FXML
    void initialize(){
        System.out.println("Enters initialize");
//        checking = new RadioButton("checking");
//        savings = new RadioButton("savings");
//        moneyMarket = new RadioButton("moneyMarket");
//        loyalCustomer = new CheckBox("loyalCustomer");
//        directDeposit = new CheckBox("directDeposit");
//        loyalCustomer.setSelected(false);
//        directDeposit.setSelected(false);
    }

//    @FXML
//    void tabChange(Event event){
//        loyalCustomer.setSelected(false);
//        directDeposit.setSelected(false);
//    }

    @FXML
    /**
     * Event Handler for openAccount button
     * @param event
     */
    void openAccount(ActionEvent event) {
        System.out.println("Enters openAccount control...");
        if (!checking.isSelected() && !savings.isSelected() && !moneyMarket.isSelected()) {
            output.appendText("Please select an Account type\n");
            return;
        }
        Profile holder = new Profile(firstName.getText(), lastName.getText());
        Double initialDeposit = Double.parseDouble(amount.getText());
        Date openDate = parseDate(date.getText());

        RadioButton selectedAccountType = (RadioButton) accountType.getSelectedToggle();
        String accountType = selectedAccountType.getText();
        String status = "";
        if(accountType.equals("checking")) {
            Checking account = new Checking(initialDeposit, holder, openDate);
            account.setDirectDeposit((directDeposit.isSelected()) ? true : false);
            status = account_interaction.add(account);
        }else if(accountType.equals("savings")){
            Savings account = new Savings(initialDeposit, holder, openDate);
            account.setIsLoyal((loyalCustomer.isSelected()) ? true : false);
            status = account_interaction.add(account);
        }else{
            MoneyMarket account = new MoneyMarket(initialDeposit, holder, openDate);
            status = account_interaction.add(account);
        }
        output.appendText(status);
    }

    @FXML
    void disableCheckBox(ActionEvent moneyMarket){
        if(checking.isSelected()){
            directDeposit.setSelected(true);
            loyalCustomer.setSelected(false);
        }else if(savings.isSelected()){
            loyalCustomer.setSelected(true);
            directDeposit.setSelected(false);
        }else{
            loyalCustomer.setSelected(false);
            directDeposit.setSelected(false);
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
