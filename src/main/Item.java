package main;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * <h1>Item</h1>
 * <p>
 *     This class stores the data relating to a single item. It is used for creating an item profile and easily storing
 *     the data read from the database since the properties correspond to the columns of the item table.
 * </p>
 *
 * @author Arif Hasan
 * @version 1.0
 * @since 03/19/21
 */
public class Item {

    // SimpleObjectProperties enable the JavaFX table to be updated dynamically when any of the properties change.
    private SimpleIntegerProperty id, vendorId;
    private SimpleStringProperty name, category, expDate, unit;
    private SimpleDoubleProperty sellingPrice, purchasePrice, quantity;

    /**
     * Constructor to allow for a blank object to be created when nothing is passed in as parameters.
     */
    public Item() {};

    /**
     * Constructor with 4 parameters, which is the maximum as we have learned in class.
     *
     * @param id unique auto-generated item id
     * @param name name of the item provided by the user
     * @param vendorId selected vendor ID by user
     * @param sellingPrice decimal selling price of the item
     */
    public Item(int id, String name, int vendorId, double sellingPrice) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.vendorId = new SimpleIntegerProperty(vendorId);
        this.sellingPrice = new SimpleDoubleProperty(sellingPrice);
    }

    // Setters and getters; conversion to SimpleObjectProperty is done internally.
    public int getId() { return id.get(); }
    public void setId(int id) { this.id = new SimpleIntegerProperty(id); }

    public String getName() { return name.get(); }
    public void setName(String name) { this.name = new SimpleStringProperty(name); }

    public int getVendorId() { return vendorId.get(); }
    public void setVendorId(int vendorId) { this.vendorId = new SimpleIntegerProperty(vendorId); }

    public double getSellingPrice() { return sellingPrice.get(); }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = new SimpleDoubleProperty(sellingPrice); }

    public String getCategory() { return category.get(); }
    public void setCategory(String category) { this.category = new SimpleStringProperty(category); }

    public String getExpDate() { return expDate.get(); }
    public void setExpDate(String expDate) { this.expDate = new SimpleStringProperty(expDate); }

    public double getPurchasePrice() { return purchasePrice.get(); }
    public void setPurchasePrice(double purchasePrice) { this.purchasePrice = new SimpleDoubleProperty(purchasePrice); }

    public String getUnit() { return unit.get(); }
    public void setUnit(String unit) { this.unit = new SimpleStringProperty(unit); }

    public double getQuantity() { return quantity.get(); }
    public void setQuantity(double quantity) { this.quantity = new SimpleDoubleProperty(quantity); }
}
