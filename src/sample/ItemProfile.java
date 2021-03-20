package sample;

import javafx.beans.property.SimpleStringProperty;

public class ItemProfile {
    SimpleStringProperty id, name, vendorId, sellingprice, itemCategory, expirydate, purchasePrice, unitOfMeasurement, quantityOnHand;

    public ItemProfile(String id, String name, String vendorId, String sellingprice, String itemCategory,
                           String expirydate, String purchasePrice, String unitOfMeasurement, String quantityOnHand) {

        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.vendorId = new SimpleStringProperty(vendorId);
        this.sellingprice = new SimpleStringProperty(sellingprice);
        this.itemCategory = new SimpleStringProperty(itemCategory);
        this.expirydate = new SimpleStringProperty(expirydate);
        this.purchasePrice = new SimpleStringProperty(purchasePrice);
        this.unitOfMeasurement = new SimpleStringProperty(unitOfMeasurement);
        this.quantityOnHand = new SimpleStringProperty(quantityOnHand);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getVendorId() {
        return vendorId.get();
    }

    public void setVendorId(String vendorId) {
        this.vendorId.set(vendorId);
    }

    public String getSellingprice() {
        return sellingprice.get();
    }

    public void setSellingprice(String sellingprice) {
        this.sellingprice.set(sellingprice);
    }

    public String getItemCategory() {
        return itemCategory.get();
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory.set(itemCategory);
    }

    public String getExpirydate() {
        return expirydate.get();
    }

    public void setExpirydate(String expirydate) {
        this.expirydate.set(expirydate);
    }

    public String getPurchasePrice() {
        return purchasePrice.get();
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice.set(purchasePrice);
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement.get();
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement.set(unitOfMeasurement);
    }

    public String getQuantityOnHand() {
        return quantityOnHand.get();
    }

    public void setQuantityOnHand(String quantityOnHand) {
        this.quantityOnHand.set(quantityOnHand);
    }
}
