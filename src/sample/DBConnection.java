/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class DB_Connection {

    public Connection getConnection() {
        DB_Connection obj_DB_Connection = new DB_Connection();
        Connection connection = null;
        connection = obj_DB_Connection.create_connection();
        System.out.println("Connection Established Successfully");
        String q0 = "CREATE DATABASE if not exists distributer;";
        String q1 = "CREATE TABLE if not exists distributer.user (id int NOT NULL AUTO_INCREMENT,fname varchar(255) NOT NULL,lname varchar(255) NOT NULL,userid varchar(255) NOT NULL,password varchar(255) NOT NULL,role varchar(255) NOT NULL,PRIMARY KEY (id));";
        String q2 = "CREATE TABLE if not exists distributer.customer (id int NOT NULL AUTO_INCREMENT,cid varchar(255) NOT NULL,fname varchar(255) not null,staddress varchar(255) not null,city varchar(755) not null,state varchar(255) not null,phone varchar(255) not null,balance varchar(255) not null,lastpaidamount varchar(255) not null,lastorderdate varchar(255) not null,PRIMARY KEY (id));";
        String q3 = "CREATE TABLE if not exists distributer.vendor (id int NOT NULL AUTO_INCREMENT,fname varchar(255) not null,staddress varchar(255) not null,city varchar(755) not null,state varchar(255) not null,phone varchar(255) not null,balance varchar(255) not null,lastpaidamount varchar(255) not null,lastorderdate varchar(255) not null,sessionaldiscountstartdate varchar(255) not null, PRIMARY KEY (id));";
        String q4 = "CREATE TABLE if not exists distributer.item (id int NOT NULL AUTO_INCREMENT,itemname varchar(255) not null, vendorid varchar(255) not null,sellingprice varchar(255) NOT NULL,itemcategory varchar(255) not null,expirydate varchar(255) not null,purchaseprice varchar(255) not null,unitofmeasurement varchar(255) not null,quantityonhand varchar(255) not null,PRIMARY KEY(id));";
        String q5 = "CREATE TABLE if not exists brand (id int NOT NULL AUTO_INCREMENT,bname varchar(255),contact varchar(255),status varchar(255),PRIMARY KEY (id));";

        try {
            Statement s = connection.createStatement();
            s.execute(q0);
            s.execute(q1);
            s.execute(q2);
            s.execute(q3);
            s.execute(q4);

            System.out.println("Successfull");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return connection;
    }

    public Connection create_connection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //  String host = "database-1.cfpfdldjn2bz.us-east-1.rds.amazonaws.com";
            //  String username = "musman";
            //  String password = "Java3365";
            //  String port = "3306";
            //connection = DriverManager.getConnection("jdbc:mysql://database-1.cfpfdldjn2bz.us-east-1.rds.amazonaws.com:3306/database-1", username, password);
            // tg
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?serverTimezone=UTC", "root", "password");

        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }
}
