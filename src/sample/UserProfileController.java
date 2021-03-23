package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {

    @FXML
    private TextField txtFirst;

    @FXML
    private TextField txtLast;

    @FXML
    private TextField txtUserid;

    @FXML
    private PasswordField txtPass;

    @FXML
    private PasswordField txtConfirm;

    @FXML
    private ComboBox<String> cRole;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnBack;

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

        cRole.getItems().addAll("Inventory Manager", "Purchaser", "Accountant","Sales Person","System administrator");

        btnCreate.setOnAction(event -> {
        int check = 0;
        int duplicate = 0;// zero mean no duplicate
        String fname = txtFirst.getText();
        String lname = txtLast.getText();
        String userid = txtUserid.getText();
        String pass = txtPass.getText();

            if(ErrorController.nameChecker(fname,15) && ErrorController.nameChecker(lname,15) && userid.length() <= 6 && pass.length() > 8 && cRole.getValue()!=null){
                check = 1;
            }
            try {
                String query = "Select userid from distributor.user";
                Statement s = connection.createStatement();
                ResultSet rs = s.executeQuery(query);

                while (rs.next()) {
                    if(rs.getString(1).equals(userid)){
                        duplicate = 1;
                        AlertController a = new AlertController(Alert.AlertType.ERROR,"Duplicate User id","This User id is already exists");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            if(check == 1 && duplicate == 0) {
                try {   // if the name is alphabet and status is not null then category is added to database
                    Statement s = connection.createStatement();
                    String q = "INSERT INTO distributor.user(fname, lname, userid, password, role) VALUES('" + fname + "','" + lname + "','" + userid + "','" + pass + "','" + cRole.getValue() + "')";
                    s.execute(q);
                    // setting default for table view.. because we added a new item so it need to be shown
                    //setDefault();

                    AlertController a = new AlertController(Alert.AlertType.INFORMATION,null,"Successfully Added");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //(id int NOT NULL AUTO_INCREMENT,fname varchar(255) NOT NULL,lname varchar(255) NOT NULL,userid varchar(255) NOT NULL,password varchar(255) NOT NULL,role varchar(255) NOT NULL
                //AlertController a = new AlertController(Alert.AlertType.ERROR,"Score Error","Enter Score in Correct Format");
            }
        });

        btnBack.setOnAction(event -> {
            try {
                Stage stage = new Stage();  // go to dashboard if information is correct
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("Dashboard.fxml").openStream());
                DashboardController Msg = (DashboardController) loader.getController();
                Scene scene = new Scene(root);
                //scene.getStylesheets().setAll(
                //      getClass().getResource("style.css").toExternalForm()
                //);
                stage.setScene(scene);
                stage.show();
                ((Node) event.getSource()).getScene().getWindow().hide();

            } catch (Exception ex) {
                System.out.println(ex);
            }

        });

    }
}

