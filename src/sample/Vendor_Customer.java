package sample;

import javafx.beans.property.SimpleStringProperty;

public class Vendor_Customer {
    SimpleStringProperty id, name, staddress, city, state, phone, balance, lastpaidamount, lastorderdate, sessionaldiscount;

    public Vendor_Customer(String id, String name, String staddress, String city, String state,
                           String phone, String balance, String lastpaidamount, String lastorderdate, String sessionaldiscount) {

        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.staddress = new SimpleStringProperty(staddress);
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
        this.phone = new SimpleStringProperty(phone);
        this.balance = new SimpleStringProperty(balance);
        this.lastpaidamount = new SimpleStringProperty(lastpaidamount);
        this.lastorderdate = new SimpleStringProperty(lastorderdate);
        this.sessionaldiscount = new SimpleStringProperty(sessionaldiscount);
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

    public String getStaddress() {
        return staddress.get();
    }

    public void setStaddress(String staddress) {
        this.staddress.set(staddress);
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getState() {
        return state.get();
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getBalance() {
        return balance.get();
    }

    public void setBalance(String balance) {
        this.balance.set(balance);
    }

    public String getLastpaidamount() {
        return lastpaidamount.get();
    }

    public void setLastpaidamount(String lastpaidamount) {
        this.lastpaidamount.set(lastpaidamount);
    }

    public String getLastorderdate() {
        return lastorderdate.get();
    }

    public void setLastorderdate(String lastorderdate) {
        this.lastorderdate.set(lastorderdate);
    }

    public String getSessionaldiscount() {
        return sessionaldiscount.get();
    }

    public void setSessionaldiscount(String sessionaldiscount) {
        this.sessionaldiscount.set(sessionaldiscount);
    }
}
