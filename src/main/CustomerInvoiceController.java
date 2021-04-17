package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerInvoiceController implements Initializable {


    @FXML
    private ComboBox<String> comboSelect;

    @FXML
    private Button btnCreate;

    @FXML
    private Label invoice_date;

    @FXML
    private Label order_date;

    @FXML
    private Label invoice_id;

    @FXML
    private Label customer_order_no;

    @FXML
    private Label item1;

    @FXML
    private Label item2;

    @FXML
    private Label item3;

    @FXML
    private Label item4;

    @FXML
    private Label item5;

    @FXML
    private Label need1;

    @FXML
    private Label need2;

    @FXML
    private Label need3;

    @FXML
    private Label need4;

    @FXML
    private Label need5;

    @FXML
    private Label q1;

    @FXML
    private Label q2;

    @FXML
    private Label q3;

    @FXML
    private Label q4;

    @FXML
    private Label q5;

    @FXML
    private Label c1;

    @FXML
    private Label c2;

    @FXML
    private Label c3;

    @FXML
    private Label c4;

    @FXML
    private Label c5;

    @FXML
    private Label lbltotal;

    @FXML
    private Pane paneRecipt;


    @FXML
    private Button btnBack;

    @FXML
    private Button btnLogout;

    DBConnection con;
    Connection connection;


    ArrayList<String> id = new ArrayList<>();
    int idfromdb;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = new DBConnection();
        connection = con.getConnection();

        paneRecipt.setVisible(false);
        addingOrdersToCombo();


        comboSelect.setOnAction(event -> {
            idfromdb = Integer.parseInt(id.get(comboSelect.getSelectionModel().getSelectedIndex()));
        });

        btnCreate.setOnAction(event -> {
            paneRecipt.setVisible(true);
            gettingDataOfInvoice();
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
    public void gettingDataOfInvoice(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        try {
            String query = "Select * from distributor.customerorder where id = '" + idfromdb + "'";
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(query);

            rs.next();
            customer_order_no.setText(rs.getString(1));
            order_date.setText(rs.getString(4));
            lbltotal.setText(rs.getString(3));
            invoice_date.setText(dtf.format(now));
            item1.setText(rs.getString(6));
            need1.setText(rs.getString(7));
            q1.setText(rs.getString(8));
            c1.setText(rs.getString(9));

            if (!rs.getString(10).equals("null")) {
                item2.setText(rs.getString(10));
                need2.setText(rs.getString(11));
                q2.setText(rs.getString(12));
                c2.setText(rs.getString(13));
            }

            if (!rs.getString(14).equals("null")) {
                item3.setText(rs.getString(14));
                need3.setText(rs.getString(15));
                q3.setText(rs.getString(16));
                c3.setText(rs.getString(17));
            }

            if (!rs.getString(18).equals("null")) {
                item4.setText(rs.getString(18));
                need4.setText(rs.getString(19));
                q4.setText(rs.getString(20));
                c4.setText(rs.getString(21));
            }

            if (!rs.getString(22).equals("null")) {
                item5.setText(rs.getString(22));
                need5.setText(rs.getString(23));
                q5.setText(rs.getString(24));
                c5.setText(rs.getString(25));
            }

            query = "Select * from distributor.customerinvoice";
            s = connection.createStatement();
            rs = s.executeQuery(query);
            int temp = 0;
            while(rs.next()){
                temp = Integer.parseInt(rs.getString(1));
            }
            invoice_id.setText(++temp+"");


            s = connection.createStatement();
            query = "INSERT INTO distributor.customerinvoice(customerorderid, invoicedate, orderdate) VALUES ('" + customer_order_no.getText() + "','" + dtf.format(now) + "','" + order_date.getText() + "'  )";
            s.execute(query);

            query = "update distributor.customerorder set status = '" + "active" + "' where id = '" + customer_order_no.getText() + "'";
            s = connection.createStatement();
            s.execute(query);


        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void addingOrdersToCombo(){
        try {
            String query = "Select givencustomername,id from distributor.customerorder where status = '" + "deactive" + "'";
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                comboSelect.getItems().add(rs.getString(1));
                id.add(rs.getString(2));
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
