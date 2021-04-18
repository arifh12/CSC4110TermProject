package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
/*
this class is used to create customers profiles delete customer profiles edit customer profiles.
 and to view them in a table form. also this class is used to search for the customer profile
 */
public class CustomerProfileController implements Initializable {


    DBConnection con;
    Connection connection;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtStaddress;

    @FXML
    private TextField txtCity;

    @FXML
    private ComboBox<String> txtState;

    @FXML
    private TextField txtPhn;

    @FXML
    private TextField txtBalance;

    @FXML
    private TextField txtLastPaid;

    @FXML
    private DatePicker txtLastOrder;


    @FXML
    private Button btnCreate;

    @FXML
    private Button btnBack;

    @FXML
    private TableView<CustomerProfile> tbl;

    @FXML
    private TableColumn<CustomerProfile, String> cfname;

    @FXML
    private TableColumn<CustomerProfile, String> cphn;

    @FXML
    private TableColumn<CustomerProfile, String> cbalance;

    @FXML
    private TableColumn<CustomerProfile, String> clastpaid;

    @FXML
    private TextField txrSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnLogout;

    int id = 0;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = new DBConnection();
        connection = con.getConnection();

        setDefault();
        txtState.getItems().addAll("VA","DC");
        txtBalance.setText("0");
        txtLastPaid.setText("0");
        txtLastOrder.setValue(null);
        btnCreate.setVisible(true);
        btnUpdate.setVisible(false);

        btnSearch.setOnAction(event -> {
            try {
                Statement s = connection.createStatement();
                ResultSet rs = s.executeQuery("Select * from distributor.customer where fname = '" + txrSearch.getText() + "' OR id = '" + txrSearch.getText() + "'");
                if(rs.next()) {
                    id = rs.getInt("id");
                    txtid.setText(rs.getInt("id")+"");
                    txtFullName.setText(rs.getString(2));
                    txtStaddress.setText(rs.getString(3));
                    txtCity.setText(rs.getString(4));
                    txtState.setValue(rs.getString(5));
                    txtPhn.setText(rs.getString(6));
                    txtBalance.setText(rs.getString(7));
                    txtLastPaid.setText(rs.getString(8));
                    txtLastOrder.setValue(LocalDate.parse(rs.getString(9)));
                    // txtSessional.setValue(LocalDate.parse(rs.getString(10)));
                }
                else{

                    AlertController a = new AlertController(Alert.AlertType.WARNING,"Not Found","Profile not found of this user");
                }

                if(txtid.getText().length() != 0){
                    btnCreate.setVisible(false);
                    btnUpdate.setVisible(true);
                }else{

                }
                //connection.close();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        });

        btnCreate.setOnAction(event -> {

            int duplicate = 0;
            String name = txtFullName.getText();
            String address = txtStaddress.getText();
            String city = txtCity.getText();
            String state = txtState.getValue();
            String phone = txtPhn.getText();
            String balance = txtBalance.getText();
            String lastPaid = txtLastPaid.getText();
            String lastdate = "";
            try {
                lastdate = txtLastOrder.getValue().toString();

            }catch(Exception e){

            }

            try {
                String query = "Select fname from distributor.customer";
                Statement s = connection.createStatement();
                ResultSet rs = s.executeQuery(query);

                while (rs.next()) {
                    if(rs.getString(1).equals(name)){
                        duplicate = 1;
                        AlertController a = new AlertController(Alert.AlertType.ERROR,"Duplicate User id","This User id is already exists");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            int check = 0;
            if(ErrorController.nameChecker(name,20,"Company Full Name") && ErrorController.strChecker(address,20,"Street Address") && ErrorController.nameChecker(city,20,"City") && state!=null && ErrorController.phnChecker(phone) && ErrorController.phnChecker(phone) && ErrorController.numChecker(balance,"Balance") && ErrorController.numChecker(lastPaid,"Last Paid") && ErrorController.pastChecker(lastdate)){
                check = 1;
            }

            if(duplicate == 0 && check == 1) {
                try {   // if the name is alphabet and status is not null then category is added to database
                    Statement s = connection.createStatement();
                    String q = "INSERT INTO distributor.customer(fname, staddress, city, state, phone, balance, lastpaidamount, lastorderdate) VALUES ('" + name + "','" + address + "','" + city + "','" + state + "','" + phone + "','" + balance + "','" + lastPaid + "','" + lastdate + "'  )";
                    s.execute(q);
                    // setting default for table view.. because we added a new item so it need to be shown

                    AlertController a = new AlertController(Alert.AlertType.INFORMATION, null, "Successfully Added");
                    setDefault();
                    clear();

                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }

        });

        btnUpdate.setOnAction(event -> {
            String name = txtFullName.getText();
            String address = txtStaddress.getText();
            String city = txtCity.getText();
            String state = txtState.getValue();
            String phone = txtPhn.getText();
            String balance = txtBalance.getText();
            String lastPaid = txtLastPaid.getText();
            String lastdate = "";
            try {
                lastdate = txtLastOrder.getValue().toString();
            }catch(Exception e){

            }
            int check = 0;
            if(ErrorController.strChecker(address,20,"Street Address") && ErrorController.nameChecker(city,20,"City") && state!=null && ErrorController.phnChecker(phone) && ErrorController.phnChecker(phone) && ErrorController.numChecker(balance,"Balance") && ErrorController.numChecker(lastPaid,"Last Paid")){
                check = 1;
            }
            if(check == 1) {
                try {   // if the name is alphabet and status is not null then category is added to database
                    Statement s = connection.createStatement();
                    String q = "UPDATE distributor.customer SET staddress = '" + address + "', city ='" + city + "',state ='" + state + "', phone ='" + phone + "',balance= '" + balance + "', lastpaidamount = '" + lastPaid + "', lastorderdate ='" + lastdate + "' where id = '" + id + "'";
                    s.execute(q);

                    AlertController a = new AlertController(Alert.AlertType.INFORMATION, null, "Successfully Updated");
                    setDefault();
                    clear();
                    btnUpdate.setVisible(false);
                    btnCreate.setVisible(true);
                    txtFullName.setEditable(true);

                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }

        });



        btnClear.setOnAction(event -> {
            clear();
        });

        btnLogout.setOnAction(e ->{
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("LoginScreen.fxml").openStream());
                LoginScreenController Msg = (LoginScreenController) loader.getController();
                Scene scene = new Scene(root);
                scene.getStylesheets().setAll(
                        getClass().getResource("style.css").toExternalForm()
                );
                stage.setScene(scene);
                stage.show();
                ((Node) e.getSource()).getScene().getWindow().hide();

            } catch (Exception ex) {
                System.out.println(ex);
            }


        });

        btnBack.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("Dashboard.fxml").openStream());
                Scene scene = new Scene(root);
                scene.getStylesheets().setAll(
                        getClass().getResource("style.css").toExternalForm()
                );
                stage.setScene(scene);
                stage.show();
                ((Node) event.getSource()).getScene().getWindow().hide();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

    }

    public void clear(){
        txtid.clear();
        txtFullName.clear();
        txtStaddress.clear();
        txtCity.clear();
        txtState.setValue(null);
        txtPhn.clear();
        txtBalance.clear();
        txtLastPaid.clear();
        txtLastOrder.setValue(null);
        btnCreate.setVisible(true);
        btnUpdate.setVisible(false);
        txtFullName.setEditable(true);
    }

    public void setDefault() {

        ObservableList<CustomerProfile> n = FXCollections.observableArrayList();
        try {
            String query;

            query = "Select * from distributor.customer";

            //query = "Select * from distributor.vendor where fname Like '%" + txrSearch.getText() + "%'";

            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) { // setting the values from table view same way as approval brand category
                n.add(new CustomerProfile(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
                //wholeData.add(rs.getString(1)+","+ rs.getString(2)+","+ rs.getString(3)+","+ rs.getString(4)+","+ rs.getString(5)+","+ rs.getString(6)+","+ rs.getString(7)+","+ rs.getString(8)+","+ rs.getString(9)+","+ rs.getString(10));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        cfname.setCellValueFactory(new PropertyValueFactory<>("name"));
        cphn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cbalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        clastpaid.setCellValueFactory(new PropertyValueFactory<>("lastpaidamount"));
        tbl.setItems(n);

    }
}

