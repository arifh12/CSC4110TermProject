package main;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {
    private SimpleIntegerProperty id, vendorId;
    private SimpleStringProperty name, category, expDate, unit;
    private SimpleDoubleProperty sellingPrice, purchasePrice, quantity;

    public Item() {};

    public Item(int id, String name, int vendorId, double sellingPrice) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.vendorId = new SimpleIntegerProperty(vendorId);
        this.sellingPrice = new SimpleDoubleProperty(sellingPrice);
    }

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
