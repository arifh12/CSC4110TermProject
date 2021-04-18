/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/*
this class create database and tables and make connection with database through objects
 */
public class DBConnection {

    public Connection getConnection() {
        DBConnection obj_DB_Connection = new DBConnection();
        Connection connection = null;
        connection = obj_DB_Connection.create_connection();
        System.out.println("Connection Established Successfully");
        String q0 = "CREATE DATABASE if not exists distributor;";
        String q1 = "CREATE TABLE if not exists distributor.user (id int NOT NULL AUTO_INCREMENT,fname varchar(255) NOT NULL,lname varchar(255) NOT NULL,userid varchar(255) NOT NULL,password varchar(255) NOT NULL,role varchar(255) NOT NULL,PRIMARY KEY (id));";
        String q2 = "CREATE TABLE if not exists distributor.customer (id int NOT NULL AUTO_INCREMENT,fname varchar(255) not null,staddress varchar(255) not null,city varchar(755) not null,state varchar(255) not null,phone varchar(255) not null,balance varchar(255) not null,lastpaidamount varchar(255) not null,lastorderdate varchar(255) not null,PRIMARY KEY (id));";
        String q3 = "CREATE TABLE if not exists distributor.vendor (id int NOT NULL AUTO_INCREMENT,fname varchar(255) not null,staddress varchar(255) not null,city varchar(755) not null,state varchar(255) not null,phone varchar(255) not null,balance varchar(255) not null,lastpaidamount varchar(255) not null,lastorderdate varchar(255) not null,sessionaldiscountstartdate varchar(255) not null, PRIMARY KEY (id));";
        String q4 = "CREATE TABLE if not exists distributor.item (" +
                "    id int(6) NOT NULL AUTO_INCREMENT," +
                "    name varchar(20) not null," +
                "    vendorId int not null," +
                "    sellingPrice double NOT NULL," +
                "    category varchar(20) not null," +
                "    expDate date not null," +
                "    purchasePrice double not null," +
                "    unit varchar(20) not null," +
                "    quantity double not null," +
                "    PRIMARY KEY(id));";
        String q5 = "CREATE TABLE if not exists distributor.purchaseOrder (" +
                "    id int NOT NULL AUTO_INCREMENT," +
                "    purchaseId int(6) NOT NULL," +
                "    vendorName varchar(20)," +
                "    itemId int(6)," +
                "    needBy date," +
                "    quantity double," +
                "    subtotal double," +
                "    PRIMARY KEY(id)" +
                ");";
        String q6 = "CREATE TABLE if not exists distributor.customerorder (id int NOT NULL AUTO_INCREMENT,givencustomername varchar(255) not null,subtotal varchar(255) not null,orderdate varchar(255),status varchar(255), item1 varchar(255) not null,need1 varchar(255) NOT NULL,quantity1 varchar(255) not null,totalcost1 varchar(255) not null, item2 varchar(255),need2 varchar(255),quantity2 varchar(255),totalcost2 varchar(255), item3 varchar(255),need3 varchar(255),quantity3 varchar(255),totalcost3 varchar(255), item4 varchar(255),need4 varchar(255),quantity4 varchar(255),totalcost4 varchar(255), item5 varchar(255),need5 varchar(255),quantity5 varchar(255),totalcost5 varchar(255),PRIMARY KEY(id));";
        String q7 = "CREATE TABLE if not exists distributor.customerinvoice (id int NOT NULL AUTO_INCREMENT,customerorderid varchar(255) not null,invoicedate varchar(255) not null,orderdate varchar(255),PRIMARY KEY(id));";

        try {
            Statement s = connection.createStatement();
            s.execute(q0);
            s.execute(q1);
            s.execute(q2);
            s.execute(q3);
            s.execute(q4);
            s.execute(q5);
            s.execute(q6);
            s.execute(q7);

            System.out.println("Successful");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return connection;
    }

    public Connection create_connection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?serverTimezone=UTC", "root", "password");

        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }
}
