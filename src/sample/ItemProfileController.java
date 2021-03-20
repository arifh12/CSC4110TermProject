package sample;

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

public class ItemProfileController implements Initializable {


    DB_Connection con;
    Connection connection;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtName;

    @FXML
    private ComboBox<String> comboid;

    @FXML
    private TextField txtSelling;

    @FXML
    private ComboBox<String> comboCategory;

    @FXML
    private DatePicker dateExpiry;

    @FXML
    private TextField txtPurchase;

    @FXML
    private ComboBox<String> comboUnit;

    @FXML
    private TextField txtQuantity;

    @FXML
    private Button btnCreate;

    @FXML
    private TableView<ItemProfile> tbl;

    @FXML
    private TableColumn<ItemProfile, String> cid;

    @FXML
    private TableColumn<ItemProfile, String> cname;

    @FXML
    private TableColumn<ItemProfile, String> cquantity;

    @FXML
    private TableColumn<ItemProfile, String> cselling;

    @FXML
    private TableColumn<ItemProfile, String> cpurchased;

    @FXML
    private TableColumn<ItemProfile, String> cexpiry;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnBack;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnLogout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = new DB_Connection();
        connection = con.getConnection();

        getVendorId();
        setDefault();
        comboCategory.getItems().addAll("Vegetables", "Fruits", "Nuts", "Dairy", "Meat", "Snacks", "Soda", "Juice", "Bakery Products");
        comboUnit.getItems().addAll("Pound", "Gallon", "Dozen");
        btnCreate.setVisible(true);
        btnUpdate.setVisible(false);

        btnSearch.setOnAction(event -> {
            try {
                Statement s = connection.createStatement();
                ResultSet rs = s.executeQuery("Select * from distributer.item where id = '" + txtSearch.getText() + "' OR itemname = '" + txtSearch.getText() + "'OR expirydate = '" + txtSearch.getText() + "'");
                if(rs.next()) {
                    txtid.setText(rs.getInt("id")+"");
                    txtName.setText(rs.getString(2));
                    comboid.setValue(rs.getString(3));
                    txtSelling.setText(rs.getString(4));
                    comboCategory.setValue(rs.getString(5));
                    dateExpiry.setValue(LocalDate.parse(rs.getString(6)));
                    txtPurchase.setText(rs.getString(7));
                    comboUnit.setValue(rs.getString(8));
                    txtQuantity.setText(rs.getString(9));
                }
                else{

                    AlertController a = new AlertController(Alert.AlertType.WARNING,"Not Found","Item not found with the details");
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
            String name = txtName.getText();
            String vid = comboid.getValue();
            String sellingprice = txtSelling.getText();
            String itemcategory = comboCategory.getValue();
            String expirydate = dateExpiry.getValue().toString();
            String purchaseprice = txtPurchase.getText();
            String unitofmeasurement = comboUnit.getValue();
            String quantityonhand = txtQuantity.getText();

            try {
                String query = "Select itemname from distributer.item";
                Statement s = connection.createStatement();
                ResultSet rs = s.executeQuery(query);

                while (rs.next()) {
                    if(rs.getString(1).equals(name)){
                        duplicate = 1;
                        AlertController a = new AlertController(Alert.AlertType.ERROR,"Duplicate item name","This item name already exists");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }

            if(duplicate == 0) {
                try {   // if the name is alphabet and status is not null then category is added to database
                    Statement s = connection.createStatement();
                    String q = "INSERT INTO distributer.item(itemname, vendorid, sellingprice, itemcategory, expirydate, purchaseprice, unitofmeasurement, quantityonhand) VALUES ('" + name + "','" + vid + "','" + sellingprice + "','" + itemcategory + "','" + expirydate + "','" + purchaseprice + "','" + unitofmeasurement + "','" + quantityonhand + "'  )";
                    s.execute(q);
                    // setting default for table view.. because we added a new item so it need to be shown
                    // setDefault();

                    AlertController a = new AlertController(Alert.AlertType.INFORMATION, null, "Successfully Added");

                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }

        });



        btnClear.setOnAction(event -> {
            txtid.clear();
            txtName.clear();
            comboid.setValue(null);
            txtSelling.clear();
            comboCategory.setValue(null);
            dateExpiry.setValue(null);
            txtPurchase.clear();
            comboUnit.setValue(null);
            txtQuantity.clear();
            btnCreate.setVisible(true);
            btnUpdate.setVisible(false);
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
    public void setDefault() {

        ObservableList<ItemProfile> n = FXCollections.observableArrayList();
        try {
            String query;

            query = "Select * from distributer.item";

            //query = "Select * from distributer.vendor where fname Like '%" + txrSearch.getText() + "%'";

            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) { // setting the values from table view same way as approval brand category
                n.add(new ItemProfile(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
                //wholeData.add(rs.getString(1)+","+ rs.getString(2)+","+ rs.getString(3)+","+ rs.getString(4)+","+ rs.getString(5)+","+ rs.getString(6)+","+ rs.getString(7)+","+ rs.getString(8)+","+ rs.getString(9)+","+ rs.getString(10));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        cid.setCellValueFactory(new PropertyValueFactory<>("id"));
        cname.setCellValueFactory(new PropertyValueFactory<>("name"));
        cquantity.setCellValueFactory(new PropertyValueFactory<>("quantityOnHand"));
        cselling.setCellValueFactory(new PropertyValueFactory<>("sellingprice"));
        cpurchased.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        cexpiry.setCellValueFactory(new PropertyValueFactory<>("expirydate"));
        tbl.setItems(n);

    }

    public void getVendorId(){
        try {
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("Select id from distributer.vendor");
            while (rs.next()) {
                comboid.getItems().add(rs.getString(1));
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
