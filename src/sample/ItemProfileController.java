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
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ItemProfileController implements Initializable {

    @FXML
    private TextField tfName;
    @FXML
    private ComboBox<Integer> cbVendorId;
    @FXML
    private Spinner<Double> spSellingPrice;
    @FXML
    private ComboBox<String> cbCategory;
    @FXML
    private DatePicker dateExp;
    @FXML
    private Spinner<Double> spPurchasePrice;
    @FXML
    private ComboBox<String> cbUnit;
    @FXML
    private Spinner<Double> spQuantity;
    @FXML
    private Button btnCreate;
    @FXML
    private TableView<Item> tblItems;
    @FXML
    private TextField tfSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnRefresh;
    @FXML
    private TableColumn<Item, Integer> colId;
    @FXML
    private TableColumn<Item, String> colName;
    @FXML
    private TableColumn<Item, Double> colSellingPrice;
    @FXML
    private TableColumn<Item, String> colExpDate;
    @FXML
    private TableColumn<Item, Double> colPurchasePrice;
    @FXML
    private TableColumn<Item, Double> colQuantity;

    private final ObservableList<String> ITEM_CATEGORIES = FXCollections.observableArrayList(
            "Vegetable", "Fruit", "Nut", "Dairy", "Meat", "Snack", "Soda", "Juice", "Bakery");
    private final ObservableList<String> UNIT_MEASUREMENT = FXCollections.observableArrayList(
            "Pound", "Gallon", "Dozen");
    private final LocalDate MIN_EXP_DATE = LocalDate.now();
    static final Session user = Session.USER;
    final int MAX_NAME_LENGTH = 20;

    private ItemDAO itemDAO = new ItemDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Roles role = user.getRole();
        initializeScene(role);

        spSellingPrice.setValueFactory(getDecimalSpinner());
        spPurchasePrice.setValueFactory(getDecimalSpinner());
        spQuantity.setValueFactory(getDecimalSpinner());

        cbCategory.setItems(ITEM_CATEGORIES);
        cbUnit.setItems(UNIT_MEASUREMENT);
        dateExp.setDayCellFactory(getMinExpCalendar());
        try {
            cbVendorId.setItems(FXCollections.observableList(itemDAO.getVendorIdList()));
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        tfName.setOnKeyTyped(t -> {
            if (tfName.getText().length() > MAX_NAME_LENGTH) {
                int pos = tfName.getCaretPosition();
                tfName.setText(tfName.getText(0, MAX_NAME_LENGTH));
                tfName.positionCaret(pos);
            }
        });

        btnCreate.setOnAction(e -> {
            try {
                if (hasEmptyFields()) {
                    AlertController a = new AlertController(Alert.AlertType.ERROR,
                            "Invalid Inputs", "Ensure that no fields are left blank.");
                } else if (itemDAO.isDuplicate(tfName.getText() , cbVendorId.getValue())) {
                    AlertController a = new AlertController(Alert.AlertType.ERROR,
                            "Duplicate Item", "This item name and vendor ID combination already exists!");
                } else {
                    Item item = readInputIntoItem();
                    itemDAO.insert(item);
                    AlertController a = new AlertController(Alert.AlertType.INFORMATION,
                            "Success", "Item has been added successfully!");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });

        btnSearch.setOnAction(e -> {
            try {
                String search = tfSearch.getText();
                Item searchResult = itemDAO.getSearchItem(search);
                if(searchResult == null) {
                    AlertController a = new AlertController(Alert.AlertType.WARNING,
                            "Item not found", "Could not find any result for '" + search + "'!");
                } else
                    displaySearchedItem(searchResult);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });

        btnClear.setOnAction(e -> {
            btnCreate.setDisable(false);
            btnUpdate.setDisable(true);
            clearInputs();
        });

        btnRefresh.setOnAction(e -> {
            try {
                displayTable();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });

        btnBack.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("Dashboard.fxml").openStream());
                Scene scene = new Scene(root);
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
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                ((Node) event.getSource()).getScene().getWindow().hide();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
    }

    private void initializeScene(Roles role) {
        btnUpdate.setDisable(true);
        tblItems.setVisible(false);
        if (!(role.equals(Roles.PURCHASER) || role.equals(Roles.OWNER) || role.equals(Roles.INVENTORY_MANAGER))) {
            btnCreate.setDisable(true);
            btnClear.setDisable(true);
        }
        if (!(role.equals(Roles.OWNER) || role.equals(Roles.PURCHASER))) {
            tfSearch.setVisible(false);
            tfSearch.setManaged(false);
            btnSearch.setVisible(false);
            btnSearch.setManaged(false);
        }
        if (role.equals(Roles.OWNER)) {
            try {
                tblItems.setVisible(true);
                displayTable();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());;
            }
        }
    }

    private SpinnerValueFactory<Double> getDecimalSpinner() {
        final double MIN = 0.00;
        final double MAX = Double.MAX_VALUE;
        final double INITIAL = 0.00;
        final double INCREMENT = 0.01;

        return new SpinnerValueFactory.DoubleSpinnerValueFactory(MIN, MAX, INITIAL, INCREMENT);
    }

    private Callback<DatePicker, DateCell> getMinExpCalendar() {
        return new Callback<>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(MIN_EXP_DATE)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
    }

    private Item readInputIntoItem() {
        Item item = new Item();

        item.setName(tfName.getText());
        item.setVendorId(cbVendorId.getValue());
        item.setSellingPrice(spSellingPrice.getValue());
        item.setCategory(cbCategory.getValue());
        item.setExpDate(String.valueOf(dateExp.getValue()));
        item.setPurchasePrice(spPurchasePrice.getValue());
        item.setUnit(cbUnit.getValue());
        item.setQuantity(spQuantity.getValue());

        return item;
    }

    public void displaySearchedItem(Item searchResult) {
        tfName.setText(searchResult.getName());
        cbVendorId.setValue(searchResult.getVendorId());
        spSellingPrice.getValueFactory().setValue(searchResult.getSellingPrice());
        cbCategory.setValue(searchResult.getCategory());
        dateExp.setValue(LocalDate.parse(searchResult.getExpDate()));
        spPurchasePrice.getValueFactory().setValue(searchResult.getPurchasePrice());
        cbUnit.setValue(searchResult.getUnit());
        spQuantity.getValueFactory().setValue(searchResult.getQuantity());

        btnUpdate.setDisable(false);
        btnCreate.setDisable(true);
        btnUpdate.setOnAction(e -> {
            try {
                if (hasEmptyFields()) {
                    AlertController a = new AlertController(Alert.AlertType.ERROR,
                            "Invalid Inputs", "Ensure that no fields are left blank.");
                } else {
                    Item item = readInputIntoItem();
                    item.setId(searchResult.getId());
                    itemDAO.update(item);
                    AlertController a = new AlertController(Alert.AlertType.INFORMATION,
                            "Success", "Item has been updated successfully!");
                    btnUpdate.setDisable(true);
                    btnCreate.setDisable(false);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    public void clearInputs() {
        tfSearch.clear();
        tfName.clear();
        cbVendorId.setValue(null);
        spSellingPrice.getValueFactory().setValue(0.00);
        cbCategory.setValue(null);
        dateExp.setValue(null);
        spPurchasePrice.getValueFactory().setValue(0.00);
        cbUnit.setValue(null);
        spQuantity.getValueFactory().setValue(0.00);
    }

    public void displayTable() throws SQLException {
        ObservableList<Item> itemList = FXCollections.observableArrayList(itemDAO.getAllItems());

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colPurchasePrice.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        colExpDate.setCellValueFactory(new PropertyValueFactory<>("expDate"));

        tblItems.setItems(itemList);
    }

    public boolean hasEmptyFields() {
        if (tfName.getText().isEmpty() || cbCategory.getValue().isEmpty() || cbUnit.getValue().isEmpty()) {
            return true;
        }
        if (cbVendorId.getValue() == null || dateExp.getValue() == null) {
            return true;
        }

        return false;
    }
}
