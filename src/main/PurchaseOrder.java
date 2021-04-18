package main;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * <h1>PurchaseOrder</h1>
 * <p>
 *     This class is instantiated to store a single item entry of the Purchase Order. This class uses the composition
 *     OO principle through the inclusion of an Item object. The properties correspond to columns of the PurchaseOrder
 *     table in the database.
 * </p>
 *
 * @author Arif Hasan
 * @version 1.0
 * @since 03/29/21
 */
public class PurchaseOrder {
    // Item object - composition
    Item item;
    // SimpleObjectProperties enable the JavaFX table to be updated dynamically when any of the properties change.
    SimpleStringProperty itemName, needBy, subtotal;
    SimpleDoubleProperty quantity;

    /**
     * Constructor 3 essential parameters required to create a PurchaseOrder row. Subtotal is calculated using the
     * item's selling price and quantity.
     *
     * @param item selected item for the purchase order entry
     * @param needBy string conversion of specified need by date
     * @param quantity decimal quantity value
     */
    public PurchaseOrder(Item item, String needBy,
                         double quantity) {
        this.item = item;
        this.itemName = new SimpleStringProperty(item.getName());
        this.needBy = new SimpleStringProperty(needBy);
        this.quantity = new SimpleDoubleProperty(quantity);
        this.subtotal = new SimpleStringProperty( String.format("%.2f", quantity * item.getPurchasePrice()));
    }

    // Setters and getters; conversion to SimpleObjectProperty is done internally.
    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }

    public String getItemName() {
        return itemName.get();
    }
    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public String getNeedBy() {
        return needBy.get();
    }
    public void setNeedBy(String needBy) {
        this.needBy = new SimpleStringProperty(needBy);
    }

    public double getQuantity() {
        return quantity.get();
    }
    public void setQuantity(double quantity) {
        this.quantity = new SimpleDoubleProperty(quantity);
    }

    public String getSubtotal() {
        return subtotal.get();
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = new SimpleStringProperty(String.valueOf(subtotal));
    }
}
