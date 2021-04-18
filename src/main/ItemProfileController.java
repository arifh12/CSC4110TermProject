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
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * <h1>ItemProfileController</h1>
 * <p>
 *     This class handles all the inputs and generates the appropriate outputs to be displayed on ItemProfile.fxml.
 *     The main purpose of this class is to act as the mediator between the front-end and the back-end. It uses Item
 *     and ItemDAO objects to facilitate all the interactions between the user and the database.
 * </p>
 *
 * @author Arif Hasan
 * @version 1.0
 * @since 03/19/21
 */
public class ItemProfileController implements Initializable {

    // JavaFX GUI component declarations
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
    private static final Session user = Session.USER;
    final int MAX_NAME_LENGTH = 20;

    private final ItemDAO itemDAO = new ItemDAO();

    /**
     * All the GUI components are initialized when the window is opened.
     *
     * @param url auto-generated parameter; not used
     * @param resourceBundle auto-generated parameter; not used
     */
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

        // Restricting name length to a maximum of 20 characters
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
                    btnClear.fire();
                    displayTable();
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

    /**
     * The window displays the appropriate controls based on the role of the user.
     *
     * @param role current user role
     */
    public void initializeScene(Roles role) {
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

    /**
     * Method to restrict invalid entries for decimal inputs.
     *
     * @return DoubleSpinnerValueFactory that only accepts decimal inputs and increments by 0.01
     */
    public SpinnerValueFactory<Double> getDecimalSpinner() {
        final double MIN = 0.00;
        final double MAX = Double.MAX_VALUE;
        final double INITIAL = 0.00;
        final double INCREMENT = 0.01;

        return new SpinnerValueFactory.DoubleSpinnerValueFactory(MIN, MAX, INITIAL, INCREMENT);
    }

    /**
     * This method is to ensure that the user is not allowed to select a previous date as the expiration date.
     *
     * @return Callback object for the calendar which restricts past dates
     */
    public Callback<DatePicker, DateCell> getMinExpCalendar() {
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

    /**
     * Reads the inputs from the screen into a single item object.
     *
     * @return Item object containing all the inputs from the user
     */
    public Item readInputIntoItem() {
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

    /**
     * This method uses the provided searched Item object and displays all the associated information onto the screen.
     * Users have the option to update the item's information.
     *
     * @param searchResult
     */
    public void displaySearchedItem(Item searchResult) {
        tfName.setText(searchResult.getName());
        cbVendorId.setValue(searchResult.getVendorId());
        spSellingPrice.getValueFactory().setValue(searchResult.getSellingPrice());
        cbCategory.setValue(searchResult.getCategory());
        dateExp.setValue(LocalDate.parse(searchResult.getExpDate()));
        spPurchasePrice.getValueFactory().setValue(searchResult.getPurchasePrice());
        cbUnit.setValue(searchResult.getUnit());
        spQuantity.getValueFactory().setValue(searchResult.getQuantity());

        // Enable update when searched item is found.
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
                    btnClear.fire();
                    displayTable();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    /**
     * This methods resets all the input fields back to their initial states.
     */
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

    /**
     * Uses the ItemDAO class to retrieve all the items from the database and display them on the on-screen table. The
     * corresponding information of each item is separated into columns and every item is listed as a row.
     *
     * @throws SQLException
     */
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

    /**
     * Checks for empty fields where input in required and returns the appropriate boolean value
     *
     * @return <code>true</code> if an empty field exists; otherwise <code>false</code>
     */
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
