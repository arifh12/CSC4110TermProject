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
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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

    @FXML
    private TableView<UserProfile> tblUser;

    @FXML
    private TableColumn<UserProfile, String> cid;

    @FXML
    private TableColumn<UserProfile, String> cfname;

    @FXML
    private TableColumn<UserProfile, String> clname;

    @FXML
    private TableColumn<UserProfile, String> crole;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnSearch;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnLogout;


    int tempId;

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
        setDefault();
        btnCreate.setVisible(true);
        btnUpdate.setVisible(false);
        txtUserid.setEditable(true);
        cRole.setDisable(false);
        cRole.getItems().addAll("Inventory Manager", "Purchaser", "Accountant","Sales Person","System Administrator");

        btnSearch.setOnAction(event -> {
            try {
                Statement s = connection.createStatement();
                ResultSet rs = s.executeQuery("Select * from distributor.user where userid = '" + txtSearch.getText() + "'");
                if(rs.next()) {
                    tempId = rs.getInt("id");
                    txtFirst.setText(rs.getString(2));
                    txtLast.setText(rs.getString(3));
                    txtUserid.setText(rs.getString(4));
                    txtPass.setText(rs.getString(5));
                    txtConfirm.setText(rs.getString(5));
                    cRole.setValue(rs.getString(6));
                }
                else{

                    AlertController a = new AlertController(Alert.AlertType.WARNING,"Not Found","Profile not found of this user");
                }

                if(txtUserid.getText().length() != 0){
                    btnCreate.setVisible(false);
                    btnUpdate.setVisible(true);
                    txtUserid.setEditable(false);
                    cRole.setDisable(true);
                }else{

                }
                //connection.close();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        });

        btnCreate.setOnAction(event -> {
            int check = 0;
            int duplicate = 0;// zero mean no duplicate
            String fname = txtFirst.getText();
            String lname = txtLast.getText();
            String userid = txtUserid.getText();
            String pass = txtPass.getText();
            String pass2 = txtConfirm.getText();
            System.out.println("1");
            if(ErrorController.nameChecker(fname,15,"First Name") && ErrorController.nameChecker(lname,15,"Last Name") && ErrorController.strChecker(userid,6,"User Id") && ErrorController.passwordChecker(pass,pass2,"Password") && cRole.getValue()!=null){
                check = 1;
                System.out.println("2");
            }
            try {
                String query = "Select userid from distributor.user";
                Statement s = connection.createStatement();
                ResultSet rs = s.executeQuery(query);

                while (rs.next()) {
                    if(rs.getString(1).equals(userid)){
                        System.out.println("dup");
                        duplicate = 1;
                        AlertController a = new AlertController(Alert.AlertType.ERROR,"Duplicate User id","This User id is already exists");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            if(check == 1 && duplicate == 0) {
                System.out.println("4");
                try {   // if the name is alphabet and status is not null then category is added to database
                    Statement s = connection.createStatement();
                    String q = "INSERT INTO distributor.user(fname, lname, userid, password, role) VALUES('" + fname + "','" + lname + "','" + userid + "','" + pass + "','" + cRole.getValue() + "')";
                    s.execute(q);
                    // setting default for table view.. because we added a new item so it need to be shown
                    //setDefault();
                    clear();
                    AlertController a = new AlertController(Alert.AlertType.INFORMATION,null,"Successfully Added");
                    setDefault();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //(id int NOT NULL AUTO_INCREMENT,fname varchar(255) NOT NULL,lname varchar(255) NOT NULL,userid varchar(255) NOT NULL,password varchar(255) NOT NULL,role varchar(255) NOT NULL
                //AlertController a = new AlertController(Alert.AlertType.ERROR,"Score Error","Enter Score in Correct Format");
            }
        });

        btnUpdate.setOnAction(event -> {
            int check = 0;
            String fname = txtFirst.getText();
            String lname = txtLast.getText();
            String userid = txtUserid.getText();
            String pass = txtPass.getText();
            String pass2 = txtConfirm.getText();
            System.out.println("1");
            if(ErrorController.nameChecker(fname,15,"First Name") && ErrorController.nameChecker(lname,15,"Last Name") && ErrorController.strChecker(userid,6,"User Id") && ErrorController.passwordChecker(pass,pass2,"Password") && cRole.getValue()!=null){
                check = 1;
                System.out.println("2");
            }
            if(check == 1) {
                System.out.println("4");
                try {   // if the name is alphabet and status is not null then category is added to database
                    Statement s = connection.createStatement();
                    String q = "UPDATE distributor.user SET fname = '" + fname + "', lname ='" + lname + "', password ='" + pass + "',role = '" + cRole.getValue() + "' where id = '" + tempId + "'";
                    s.execute(q);
                    // setting default for table view.. because we added a new item so it need to be shown
                    //setDefault();
                    clear();
                    AlertController a = new AlertController(Alert.AlertType.INFORMATION,null,"Successfully Updated");
                    setDefault();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //(id int NOT NULL AUTO_INCREMENT,fname varchar(255) NOT NULL,lname varchar(255) NOT NULL,userid varchar(255) NOT NULL,password varchar(255) NOT NULL,role varchar(255) NOT NULL
                //AlertController a = new AlertController(Alert.AlertType.ERROR,"Score Error","Enter Score in Correct Format");
            }

        });



        btnDelete.setOnAction(event -> {
            String query1 = "delete from distributor.user where id = '" + tempId + "'";
            try {
                if (txtFirst.getLength() >= 1) {
                    Statement s = connection.createStatement();
                    s.execute(query1);

                    setDefault();

                    AlertController a = new AlertController(Alert.AlertType.INFORMATION, null, "Successfully Deleted");
                    clear();
                } else {
                    AlertController a = new AlertController(Alert.AlertType.WARNING, null, "Search a user then press delete Button");
                }
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        });


        btnClear.setOnAction(event -> {
            clear();
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

        btnLogout.setOnAction(event -> {
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
                ((Node) event.getSource()).getScene().getWindow().hide();

            } catch (Exception ex) {
                System.out.println(ex);
            }

        });
    }
    public  void clear(){
        txtFirst.clear();
        txtLast.clear();
        txtUserid.clear();
        txtPass.clear();
        txtConfirm.clear();
        cRole.setValue(null);
        btnCreate.setVisible(true);
        btnUpdate.setVisible(false);
        txtUserid.setEditable(true);
        cRole.setDisable(false);
    }

    public void setDefault() {

        ObservableList<UserProfile> n = FXCollections.observableArrayList();
        try {
            String query;

            query = "Select * from distributor.user";

            //query = "Select * from distributor.vendor where fname Like '%" + txrSearch.getText() + "%'";

            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) { // setting the values from table view same way as approval brand category
                n.add(new UserProfile(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
                //wholeData.add(rs.getString(1)+","+ rs.getString(2)+","+ rs.getString(3)+","+ rs.getString(4)+","+ rs.getString(5)+","+ rs.getString(6)+","+ rs.getString(7)+","+ rs.getString(8)+","+ rs.getString(9)+","+ rs.getString(10));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        cfname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        clname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        cid.setCellValueFactory(new PropertyValueFactory<>("userid"));
        crole.setCellValueFactory(new PropertyValueFactory<>("role"));
        tblUser.setItems(n);

    }
}