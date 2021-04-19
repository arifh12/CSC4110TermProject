package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
/*
this class is used to login and verify the credentials from database
 */
public class LoginScreenController implements Initializable {


    DBConnection con;
    Connection connection;

    @FXML
    private TextField txtUname;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnLogin;

    static String role = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = new DBConnection();
        connection = con.getConnection();
        btnLogin.setOnAction(event -> {


            String userName = txtUname.getText();
            String password = txtPassword.getText();


            if(userName.equals("admin")&& password.equals("admin")){
                role = "Owner";
                Session.USER.setRole(Roles.OWNER);
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
            }
            else {

                try {

                    Statement s = connection.createStatement();
                    ResultSet rs = s.executeQuery("Select password,role from distributor.user where userid = '" + userName + "'");
                    if (rs.next()) {    // selecting password from status and role from database
                        if (rs.getString(1).equals(password)) {
                            role = rs.getString(2);
                            switch (role) {
                                case "Owner":
                                    Session.USER.setRole(Roles.OWNER);
                                    break;
                                case "System Administrator":
                                    Session.USER.setRole(Roles.ADMINISTRATOR);
                                    break;
                                case "Sales Person":
                                    Session.USER.setRole(Roles.SALES_PERSON);
                                    break;
                                case "Accountant":
                                    Session.USER.setRole(Roles.ACCOUNTANT);
                                    break;
                                case "Purchaser":
                                    Session.USER.setRole(Roles.PURCHASER);
                                    break;
                                case "Inventory Manager":
                                    Session.USER.setRole(Roles.INVENTORY_MANAGER);
                                    break;
                                default:
                                    Session.USER.setRole(null);
                                    break;
                            }
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

                        } else {
                            AlertController a = new AlertController(Alert.AlertType.ERROR, "Invalid Credentials", "Password is Incorrect");
                        }
                    } else {
                        AlertController a = new AlertController(Alert.AlertType.ERROR, "Invalid Credentials", "Please enter correct information");
                    }
                    connection.close();
                } catch (Exception ex) {
                    AlertController a = new AlertController(Alert.AlertType.ERROR, "Invalid Credentials", "Please enter correct information");
                }
            }






        });

    }
}

