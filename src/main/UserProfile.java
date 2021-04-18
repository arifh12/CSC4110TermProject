package main;

import javafx.beans.property.SimpleStringProperty;
/*
this class only used string property and is used to fill the data in the tables and to implement the model
 */
public class UserProfile {
    SimpleStringProperty id, fname, lname, userid, password, role;

    public UserProfile(String id, String fname, String lname, String userid, String password, String role) {

        this.id = new SimpleStringProperty(id);
        this.fname = new SimpleStringProperty(fname);
        this.lname = new SimpleStringProperty(lname);
        this.userid = new SimpleStringProperty(userid);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getFname() {
        return fname.get();
    }

    public void setFname(String fname) {
        this.fname.set(fname);
    }

    public String getLname() {
        return lname.get();
    }

    public void setLname(String lname) {
        this.lname.set(lname);
    }

    public String getUserid() {
        return userid.get();
    }

    public void setUserid(String userid) {
        this.userid.set(userid);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

}
