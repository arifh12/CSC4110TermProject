/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

/*
this class display the buttons for the dashboard screen and also check that which user use which features. and show him features accordingly
 */
public class DashboardController implements Initializable {


    @FXML
    private Button btnUser;

    @FXML
    private Button btnVendor;

    @FXML
    private Button btnItem;

    @FXML
    private Button btnPurchaseOrder;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnCustomerOrder;

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnCustomerProfile;

    static boolean Disable = true;

    DBConnection con;
    Connection connection;
    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        con = new DBConnection();
        connection = con.getConnection();


        switch (Session.USER.getRole()) {
            case OWNER:
                btnUser.setVisible(true);
                btnItem.setVisible(true);
                btnCustomer.setVisible(true);
                btnCustomerOrder.setVisible(true);
                btnCustomerProfile.setVisible(true);
                btnPurchaseOrder.setVisible(true);
                btnVendor.setVisible(true);
                break;
            case ADMINISTRATOR:
                btnUser.setVisible(true);
                btnItem.setVisible(true);
                btnCustomer.setVisible(true);
                btnCustomerOrder.setVisible(true);
                btnCustomerProfile.setVisible(true);
                btnPurchaseOrder.setVisible(true);
                btnVendor.setVisible(true);
                break;
            case SALES_PERSON:
                btnUser.setVisible(false);
                btnItem.setVisible(false);
                btnCustomer.setVisible(false);
                btnCustomerOrder.setVisible(true);
                btnCustomerProfile.setVisible(false);
                btnPurchaseOrder.setVisible(false);
                btnVendor.setVisible(false);
                alertExpired();
                break;
            case ACCOUNTANT:
                btnUser.setVisible(false);
                btnItem.setVisible(false);
                btnCustomer.setVisible(true);
                btnCustomerOrder.setVisible(false);
                btnCustomerProfile.setVisible(false);
                btnPurchaseOrder.setVisible(false);
                btnVendor.setVisible(false);
                alertAccountant();
                break;
            case PURCHASER:
                btnUser.setVisible(false);
                btnItem.setVisible(true);
                btnCustomer.setVisible(false);
                btnCustomerOrder.setVisible(false);
                btnCustomerProfile.setVisible(false);
                btnPurchaseOrder.setVisible(true);
                btnVendor.setVisible(true);
                alertOutOfStock();
                break;
            case INVENTORY_MANAGER:
                btnUser.setVisible(false);
                btnItem.setVisible(true);
                btnCustomer.setVisible(false);
                btnCustomerOrder.setVisible(false);
                btnCustomerProfile.setVisible(false);
                btnPurchaseOrder.setVisible(false);
                btnVendor.setVisible(false);
                break;
            default:
                break;
        }

        btnUser.setOnAction(event ->{
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("UserProfile.fxml").openStream());
                UserProfileController Msg = (UserProfileController) loader.getController();
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

        btnVendor.setOnAction(e ->{
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("VendorCustomer.fxml").openStream());
                VendorCustomerController Msg = (VendorCustomerController) loader.getController();
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

        btnCustomerProfile.setOnAction(e ->{
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("CustomerProfile.fxml").openStream());
                CustomerProfileController Msg = (CustomerProfileController) loader.getController();
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

        btnItem.setOnAction(e ->{
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("ItemProfile.fxml").openStream());
                ItemProfileController Msg = (ItemProfileController) loader.getController();
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

        btnPurchaseOrder.setOnAction(e ->{
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("PurchaseOrder.fxml").openStream());
                PurchaseOrderController Msg = (PurchaseOrderController) loader.getController();
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

        btnCustomerOrder.setOnAction(e ->{
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("CustomerOrder.fxml").openStream());
                CustomerOrderController Msg = (CustomerOrderController) loader.getController();
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

        btnCustomer.setOnAction(e ->{
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("CustomerInvoice.fxml").openStream());
                CustomerInvoiceController Msg = (CustomerInvoiceController) loader.getController();
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

        btnBack.setOnAction(e ->{
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
    public void alertAccountant(){
        int temp = 0;
        String query = "Select status from distributor.customerorder";
        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet rs = s.executeQuery(query);
            while(rs.next()){
                if((rs.getString(1)).equals("deactive")){
                    temp++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(temp >= 2){
            AlertController a = new AlertController(Alert.AlertType.INFORMATION, "Accountant User", "More then two Customers Orders are Avaliable");
        }
    }
    public void alertExpired(){
        int temp = 0;
        String query = "Select expirydate from distributor.item";
        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet rs = s.executeQuery(query);
            while(rs.next()){
                SimpleDateFormat
                        sdfo
                        = new SimpleDateFormat("yyyy-MM-dd");

                // Get the two dates to be compared
                Date d1 = null;
                Date d2 = null;
                try {
                    LocalDate todayDate = LocalDate.now();
                    d1 = sdfo.parse(rs.getString(1));
                    d2 = sdfo.parse(todayDate.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (d1.compareTo(d2) < 0) {
                    temp++;
                }
                else if (d1.compareTo(d2) == 0) {
                    temp++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("temp "+temp);
        if(temp >= 2){
            AlertController a = new AlertController(Alert.AlertType.INFORMATION, "Sales User", "More then two Items are Expired");
        }
    }
    public void alertOutOfStock(){
        int temp = 0;
        String query = "Select quantityonhand from distributor.item";
        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet rs = s.executeQuery(query);
            while(rs.next()){
                if(rs.getString(1).equals("0")) {
                    temp++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("temp "+temp);
        if(temp >= 2){
            AlertController a = new AlertController(Alert.AlertType.INFORMATION, "Purchaser User", "More then two Items are Out of Stock");
        }
    }

}
