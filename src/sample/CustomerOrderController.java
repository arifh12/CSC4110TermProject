package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerOrderController implements Initializable {


    DB_Connection con;
    Connection connection;

    @FXML
    private ComboBox<String> cSelectVendor;

    @FXML
    private VBox vb;

    @FXML
    private HBox hb1;

    @FXML
    private ComboBox<String> citem1;

    @FXML
    private DatePicker dneed1;

    @FXML
    private TextField txtq1;

    @FXML
    private TextField total1;

    @FXML
    private Button btnAdd;

    @FXML
    private HBox hb2;

    @FXML
    private ComboBox<String> citem2;

    @FXML
    private DatePicker dneed2;

    @FXML
    private TextField txtq2;

    @FXML
    private TextField total2;

    @FXML
    private HBox hb3;

    @FXML
    private ComboBox<String> citem3;

    @FXML
    private DatePicker dneed3;

    @FXML
    private TextField txtq3;

    @FXML
    private TextField total3;

    @FXML
    private HBox hb4;

    @FXML
    private ComboBox<String> citem4;

    @FXML
    private DatePicker dneed4;

    @FXML
    private TextField txtq4;

    @FXML
    private TextField total4;

    @FXML
    private HBox hb5;

    @FXML
    private ComboBox<String> citem5;

    @FXML
    private DatePicker dneed5;

    @FXML
    private TextField txtq5;

    @FXML
    private TextField total5;

    @FXML
    private Label lblTotaCostl;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnLogout;

    int tempQuantity = 0;
    int count = 0;
    ArrayList<String> itemList = new ArrayList<>();
    double totalPrice = 0,tempTotal = 0, tempPrice = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = new DB_Connection();
        connection = con.getConnection();

        updateVendorAndItems();

        citem1.getItems().addAll(itemList);
        citem2.getItems().addAll(itemList);
        citem3.getItems().addAll(itemList);
        citem4.getItems().addAll(itemList);
        citem5.getItems().addAll(itemList);
        total1.setText("0");
        total2.setText("0");
        total3.setText("0");
        total4.setText("0");
        total5.setText("0");

        count = 1;
        hb2.setVisible(false);
        hb3.setVisible(false);
        hb4.setVisible(false);
        hb5.setVisible(false);
        btnAdd.setOnAction(event -> {
            vb.getChildren().clear();
            if(count == 1) {
                hb2.setVisible(true);
                vb.getChildren().addAll(hb1, hb2, btnAdd);
                count++;
            }else if(count == 2){
                hb3.setVisible(true);
                vb.getChildren().addAll(hb1, hb2, hb3, btnAdd);
                count++;
            }else if(count == 3){
                hb4.setVisible(true);
                vb.getChildren().addAll(hb1, hb2, hb3, hb4, btnAdd);
                count++;
            }else if(count == 4){
                hb5.setVisible(true);
                vb.getChildren().addAll(hb1, hb2, hb3, hb4, hb5);
                count++;
            }
        });
        citem1.setOnAction(event -> {
            updateItems(citem1.getSelectionModel().getSelectedItem(),citem2,citem3,citem4,citem5);
        });
        citem2.setOnAction(event -> {
            updateItems(citem2.getSelectionModel().getSelectedItem(),citem1,citem3,citem4,citem5);
        });
        citem3.setOnAction(event -> {
            updateItems(citem3.getSelectionModel().getSelectedItem(),citem2,citem1,citem4,citem5);
        });
        citem4.setOnAction(event -> {
            updateItems(citem4.getSelectionModel().getSelectedItem(),citem2,citem3,citem1,citem5);
        });
        citem5.setOnAction(event -> {
            updateItems(citem5.getSelectionModel().getSelectedItem(),citem2,citem3,citem4,citem1);
        });

        txtq1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(txtq1.getText().matches("[0-9]+")){
                txtq1.setStyle("-fx-border-color : lightgrey");
                tempPrice = Double.valueOf(fetchPrice(citem1.getValue()))*Double.valueOf(txtq1.getText());
                total1.setText(tempPrice+"");
                lblTotaCostl.setText(Double.valueOf(total1.getText())+Double.valueOf(total2.getText())+Double.valueOf(total3.getText())+Double.valueOf(total4.getText())+Double.valueOf(total5.getText())+"");
            }else{
                txtq1.setStyle("-fx-border-color : red");// if we put alphabet.. border color will be red
                total1.setText("0");
                lblTotaCostl.setText(Double.valueOf(total1.getText())+Double.valueOf(total2.getText())+Double.valueOf(total3.getText())+Double.valueOf(total4.getText())+Double.valueOf(total5.getText())+"");
            }

        });

        txtq2.textProperty().addListener((observable, oldValue, newValue) -> {
            if(txtq2.getText().matches("[0-9]+")){
                txtq2.setStyle("-fx-border-color : lightgrey");
                tempPrice = Double.valueOf(fetchPrice(citem2.getValue()))*Double.valueOf(txtq2.getText());
                total2.setText(tempPrice+"");
                lblTotaCostl.setText(Double.valueOf(total1.getText())+Double.valueOf(total2.getText())+Double.valueOf(total3.getText())+Double.valueOf(total4.getText())+Double.valueOf(total5.getText())+"");
            }else{
                txtq2.setStyle("-fx-border-color : red");// if we put alphabet.. border color will be red
                total2.setText("0");
                lblTotaCostl.setText(Double.valueOf(total1.getText())+Double.valueOf(total2.getText())+Double.valueOf(total3.getText())+Double.valueOf(total4.getText())+Double.valueOf(total5.getText())+"");
            }

        });

        txtq3.textProperty().addListener((observable, oldValue, newValue) -> {
            if(txtq3.getText().matches("[0-9]+")){
                txtq3.setStyle("-fx-border-color : lightgrey");
                tempPrice = Double.valueOf(fetchPrice(citem3.getValue()))*Double.valueOf(txtq3.getText());
                total3.setText(tempPrice+"");
                lblTotaCostl.setText(Double.valueOf(total1.getText())+Double.valueOf(total2.getText())+Double.valueOf(total3.getText())+Double.valueOf(total4.getText())+Double.valueOf(total5.getText())+"");
            }else{
                txtq3.setStyle("-fx-border-color : red");// if we put alphabet.. border color will be red
                total3.setText("0");
                lblTotaCostl.setText(Double.valueOf(total1.getText())+Double.valueOf(total2.getText())+Double.valueOf(total3.getText())+Double.valueOf(total4.getText())+Double.valueOf(total5.getText())+"");
            }
        });

        txtq4.textProperty().addListener((observable, oldValue, newValue) -> {
            if(txtq4.getText().matches("[0-9]+")){
                txtq4.setStyle("-fx-border-color : lightgrey");
                tempPrice = Double.valueOf(fetchPrice(citem4.getValue()))*Double.valueOf(txtq4.getText());
                total4.setText(tempPrice+"");
                lblTotaCostl.setText(Double.valueOf(total1.getText())+Double.valueOf(total2.getText())+Double.valueOf(total3.getText())+Double.valueOf(total4.getText())+Double.valueOf(total5.getText())+"");
            }else{
                txtq4.setStyle("-fx-border-color : red");// if we put alphabet.. border color will be red
                total4.setText("0");
                lblTotaCostl.setText(Double.valueOf(total1.getText())+Double.valueOf(total2.getText())+Double.valueOf(total3.getText())+Double.valueOf(total4.getText())+Double.valueOf(total5.getText())+"");
            }
        });

        txtq5.textProperty().addListener((observable, oldValue, newValue) -> {
            if(txtq5.getText().matches("[0-9]+")){
                txtq5.setStyle("-fx-border-color : lightgrey");
                tempPrice = Double.valueOf(fetchPrice(citem5.getValue()))*Double.valueOf(txtq5.getText());
                total5.setText(tempPrice+"");
                lblTotaCostl.setText(Double.valueOf(total1.getText())+Double.valueOf(total2.getText())+Double.valueOf(total3.getText())+Double.valueOf(total4.getText())+Double.valueOf(total5.getText())+"");
            }else{
                txtq5.setStyle("-fx-border-color : red");// if we put alphabet.. border color will be red
                total5.setText("0");
                lblTotaCostl.setText(Double.valueOf(total1.getText())+Double.valueOf(total2.getText())+Double.valueOf(total3.getText())+Double.valueOf(total4.getText())+Double.valueOf(total5.getText())+"");
            }
        });

        btnCreate.setOnAction(event -> {
            tempQuantity = 0;
            checkingQuantity(citem1.getValue(), txtq1);
            checkingQuantity(citem2.getValue(), txtq2);
            checkingQuantity(citem3.getValue(), txtq3);
            checkingQuantity(citem4.getValue(), txtq4);
            checkingQuantity(citem5.getValue(), txtq5);

            if(checkingEmpty() && count == tempQuantity) {
                addingItemstodb(citem1.getValue(), txtq1);
                addingItemstodb(citem2.getValue(), txtq2);
                addingItemstodb(citem3.getValue(), txtq3);
                addingItemstodb(citem4.getValue(), txtq4);
                addingItemstodb(citem5.getValue(), txtq5);

                updateToDB();
                updatingCustomerBalance();
                clear();
            }
        });



        btnBack.setOnAction(event -> {
            try {
                Stage stage = new Stage();  // go to dashboard if information is correct
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("Dashboard.fxml").openStream());
                DashboardController Msg = (DashboardController) loader.getController();
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

    }

    public void updateToDB(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        try {   // if the name is alphabet and status is not null then category is added to database
            Statement s = connection.createStatement();
            String q = "INSERT INTO distributer.customerorder(givencustomername, subtotal, orderdate, status, item1, need1, quantity1, totalcost1, item2, need2, quantity2, totalcost2, item3, need3, quantity3, totalcost3, item4, need4, quantity4, totalcost4, item5, need5, quantity5, totalcost5) VALUES ('" + cSelectVendor.getValue() + "','" + lblTotaCostl.getText() + "','" + dtf.format(now) + "','" + "deactive" + "','" + citem1.getValue() + "','" + dneed1.getValue() + "','" + txtq1.getText() + "','" + total1.getText() + "','" + citem2.getValue() + "','" + dneed2.getValue() + "','" + txtq2.getText() + "','" + total2.getText() + "','" + citem3.getValue() + "','" + dneed3.getValue() + "','" + txtq3.getText() + "','" + total3.getText() + "','" + citem4.getValue() + "','" + dneed4.getValue() + "','" + txtq4.getText() + "','" + total4.getText() + "','" + citem5.getValue() + "','" + dneed5.getValue() + "','" + txtq5.getText() + "','" + total5.getText() + "'  )";
            s.execute(q);
            AlertController a = new AlertController(Alert.AlertType.INFORMATION, null, "Order Created Successfully");

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void updatingCustomerBalance(){
        try {       // updating the brand table
            String query1 = "Select balance from distributer.customer where fname = '" + cSelectVendor.getValue() + "'";
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(query1);
            rs.next();
            double balance = Double.valueOf(rs.getString(1))-Double.valueOf(lblTotaCostl.getText());
            query1 = "update distributer.customer set balance = '" + balance + "' where fname = '" + cSelectVendor.getValue() + "'";
            s = connection.createStatement();
            s.execute(query1);

        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }

    public void checkingQuantity(String item,TextField txt){


        try {       // updating the brand table
            String query1 = "Select quantityonhand from distributer.item where itemname = '" + item + "'";
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(query1);
            rs.next();
            int quantity = Integer.parseInt(rs.getString(1))-Integer.parseInt(txt.getText());
            if(quantity >= 0){
                tempQuantity++;
            }else{
                AlertController a = new AlertController(Alert.AlertType.WARNING, null, item+ " is less in Quantity on hand then the quantity customer wants");
            }


        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }

    public void addingItemstodb(String item,TextField txt){


        try {       // updating the brand table
            String query1 = "Select quantityonhand from distributer.item where itemname = '" + item + "'";
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(query1);
            rs.next();
            int quantity = Integer.parseInt(rs.getString(1))-Integer.parseInt(txt.getText());
            if(quantity >= 0){
                query1 = "update distributer.item set quantityonhand = '" + quantity + "' where itemname = '" + item + "'";
                s = connection.createStatement();
                s.execute(query1);
            }else{
                AlertController a = new AlertController(Alert.AlertType.INFORMATION, null, "Quantity on hand cannot be less then quantity customer wants");
            }


        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    public String fetchPrice(String item){
        try {


            String query = "Select purchaseprice from distributer.item where itemname = '" + item + "'";
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(query);

            rs.next();
            return rs.getString(1);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "";
    }
    public void updateItems(String item,ComboBox c1,ComboBox c2,ComboBox c3, ComboBox c4){
        c1.getItems().remove(item);
        c2.getItems().remove(item);
        c3.getItems().remove(item);
        c4.getItems().remove(item);
        //citem5.getItems().remove(item);
    }
    public void updateVendorAndItems(){
        try {
            String query = "Select fname from distributer.customer";
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                cSelectVendor.getItems().add(rs.getString(1));
            }

            query = "Select itemname from distributer.item";
            s = connection.createStatement();
            rs = s.executeQuery(query);

            while (rs.next()) {
                itemList.add(rs.getString(1));
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public boolean checkingEmpty(){
        int num = 0;
        if(cSelectVendor.getValue() == null){
            AlertController a = new AlertController(Alert.AlertType.ERROR, null, "Select a Vendor from a List");
            return false;
        }
        if(hb1.isVisible()){
            if(citem1.getValue() != null){
                if(dneed1.getValue() != null) {
                    if (ErrorController.pastChecker(dneed1.getValue().toString())) {
                        if (ErrorController.numChecker(txtq1.getText(), "Quantity 1")) {
                            if (Integer.valueOf(txtq1.getText()) > 0) {
                                num++;
                            } else {
                                AlertController a = new AlertController(Alert.AlertType.ERROR, null, "Quantity 1 cannot be less than zero");
                            }
                        }
                    }
                }else{
                    AlertController a = new AlertController(Alert.AlertType.ERROR, null, "date 1 cannot be null");
                }
            }else{
                AlertController a = new AlertController(Alert.AlertType.ERROR, null, "Item 1 cannot be null");
            }
        }
        if(hb2.isVisible()){
            if(citem2.getValue() != null){
                if(dneed2.getValue() != null) {
                    if (ErrorController.pastChecker(dneed2.getValue().toString())) {
                        if (ErrorController.numChecker(txtq2.getText(), "Quantity 2")) {
                            if (Integer.valueOf(txtq2.getText()) > 0) {
                                num++;
                            } else {
                                AlertController a = new AlertController(Alert.AlertType.ERROR, null, "Quantity 2 cannot be less than zero");
                            }
                        }
                    }
                }else{
                    AlertController a = new AlertController(Alert.AlertType.ERROR, null, "date 2 cannot be null");
                }
            }else{
                AlertController a = new AlertController(Alert.AlertType.ERROR, null, "Item 2 cannot be null");
            }
        }
        if(hb3.isVisible()){
            if(citem3.getValue() != null){
                if(dneed3.getValue() != null) {
                    if (ErrorController.pastChecker(dneed3.getValue().toString())) {
                        if (ErrorController.numChecker(txtq3.getText(), "Quantity 3")) {
                            if (Integer.valueOf(txtq3.getText()) > 0) {
                                num++;
                            } else {
                                AlertController a = new AlertController(Alert.AlertType.ERROR, null, "Quantity 3 cannot be less than zero");
                            }
                        }
                    }
                }else{
                    AlertController a = new AlertController(Alert.AlertType.ERROR, null, "date 3 cannot be null");
                }
            }else{
                AlertController a = new AlertController(Alert.AlertType.ERROR, null, "Item 3 cannot be null");
            }
        }
        if(hb4.isVisible()){
            if(citem4.getValue() != null){
                if(dneed4.getValue() != null) {
                    if (ErrorController.pastChecker(dneed4.getValue().toString())) {
                        if (ErrorController.numChecker(txtq4.getText(), "Quantity 4")) {
                            if (Integer.valueOf(txtq4.getText()) > 0) {
                                num++;
                            } else {
                                AlertController a = new AlertController(Alert.AlertType.ERROR, null, "Quantity 4 cannot be less than zero");
                            }
                        }
                    }
                }else{
                    AlertController a = new AlertController(Alert.AlertType.ERROR, null, "date 4 cannot be null");
                }
            }else{
                AlertController a = new AlertController(Alert.AlertType.ERROR, null, "Item 4 cannot be null");
            }
        }
        if(hb5.isVisible()){
            if(citem5.getValue() != null){
                if(dneed5.getValue() != null) {
                    if (ErrorController.pastChecker(dneed5.getValue().toString())) {
                        if (ErrorController.numChecker(txtq5.getText(), "Quantity 5")) {
                            if (Integer.valueOf(txtq5.getText()) > 0) {
                                num++;
                            } else {
                                AlertController a = new AlertController(Alert.AlertType.ERROR, null, "Quantity 5 cannot be less than zero");
                            }
                        }
                    }
                }else{
                    AlertController a = new AlertController(Alert.AlertType.ERROR, null, "date 5 cannot be null");
                }
            }else{
                AlertController a = new AlertController(Alert.AlertType.ERROR, null, "Item 5 cannot be null");
            }
        }

        if(count == num){
            return true;
        }else{
            return false;
        }
    }
    public void clear(){
        cSelectVendor.getSelectionModel().clearSelection();
        citem1.getSelectionModel().clearSelection();
        dneed1.setValue(null);
        txtq1.clear();
        total1.setText("0");
        citem2.getSelectionModel().clearSelection();
        dneed2.setValue(null);
        txtq2.clear();
        total2.setText("0");
        citem3.getSelectionModel().clearSelection();
        dneed3.setValue(null);
        txtq3.clear();
        total3.setText("0");
        citem4.getSelectionModel().clearSelection();
        dneed4.setValue(null);
        txtq4.clear();
        total4.setText("0");
        citem5.getSelectionModel().clearSelection();
        dneed5.setValue(null);
        txtq5.clear();
        total5.setText("0");
    }

}