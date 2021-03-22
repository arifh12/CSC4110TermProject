/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class DashboardController implements Initializable {

    @FXML
    private Button btnUser;
    @FXML
    private Button btnVendor;
    @FXML
    private Button btnItem;
    @FXML
    private Button btnCustomerProfile;
    @FXML
    private Button btnApproval;
    @FXML
    private Button btnBack;

    static boolean Disable = true;
    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if(Disable == false){
            btnApproval.setVisible(false);
        }
        btnUser.setOnAction(event ->{
            try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("UserProfile.fxml").openStream());
            UserProfileController Msg = (UserProfileController) loader.getController();
            Scene scene = new Scene(root);
            //scene.getStylesheets().setAll(
              //  getClass().getResource("style.css").toExternalForm()
            //);
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
           // scene.getStylesheets().setAll(
             //   getClass().getResource("style.css").toExternalForm()
            //);
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
            //scene.getStylesheets().setAll(
              //  getClass().getResource("style.css").toExternalForm()
            //);
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
                // scene.getStylesheets().setAll(
                //   getClass().getResource("style.css").toExternalForm()
                //);
                stage.setScene(scene);
                stage.show();
                ((Node) e.getSource()).getScene().getWindow().hide();

            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
        /*
        btnApproval.setOnAction(e ->{
            try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("ApprovalScreen.fxml").openStream());
            ApprovalScreenController Msg = (ApprovalScreenController) loader.getController();
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

         */
    }    
    
}
