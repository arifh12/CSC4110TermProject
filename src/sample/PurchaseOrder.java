package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class PurchaseOrder {
    Item item;
    SimpleStringProperty itemName;
    SimpleStringProperty needBy, subtotal;
    SimpleDoubleProperty quantity;

    public PurchaseOrder(Item item, String needBy,
                         double quantity) {
        this.item = item;
        this.itemName = new SimpleStringProperty(item.getName());
        this.needBy = new SimpleStringProperty(needBy);
        this.quantity = new SimpleDoubleProperty(quantity);
        this.subtotal = new SimpleStringProperty( String.format("%.2f", quantity * item.getPurchasePrice()));
    }

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
