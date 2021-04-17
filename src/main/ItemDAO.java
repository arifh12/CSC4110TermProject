package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    private Connection conn = new DBConnection().getConnection();

    public boolean insert(Item item) throws SQLException {
        String sql = "INSERT INTO distributor.item(name, vendorId, sellingPrice, category, expDate, " +
                "purchasePrice, unit, quantity) VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, item.getName());
        ps.setInt(2, item.getVendorId());
        ps.setDouble(3, item.getSellingPrice());
        ps.setString(4, item.getCategory());
        ps.setString(5, item.getExpDate());
        ps.setDouble(6, item.getPurchasePrice());
        ps.setString(7, item.getUnit());
        ps.setDouble(8, item.getQuantity());

        return ps.executeUpdate() > 0;
    }

    public boolean isDuplicate(String name, int vendorId) throws SQLException {
        String sql = "SELECT * FROM distributor.item WHERE name='" + name +"' AND vendorId='" + vendorId + "';";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        return rs.next();
    }

    public List<Integer> getVendorIdList() throws SQLException {
        String sql = "SELECT id FROM distributor.vendor";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<Integer> vendorIdList = new ArrayList<>();
        while (rs.next()) {
            vendorIdList.add(rs.getInt(1));
        }

        return vendorIdList;
    }

    public Item getSearchItem(String search) throws SQLException {
        String sql = "SELECT * FROM distributor.item WHERE ? IN (id, name, expDate)";
        Item item = null;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, search);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int vendorId = rs.getInt("vendorId");
            double sellingPrice = rs.getDouble("sellingPrice");

            item = new Item(id, name, vendorId, sellingPrice);
            item.setCategory(rs.getString("category"));
            item.setExpDate(rs.getString("expDate"));
            item.setPurchasePrice(rs.getDouble("purchasePrice"));
            item.setUnit(rs.getString("unit"));
            item.setQuantity(rs.getDouble("quantity"));
        }

        return item;
    }

    public List<Item> getAllItems() throws SQLException {
        List<Item> itemList = new ArrayList<>();
        String sql = "SELECT * FROM distributor.item;";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Item item;

            int id = rs.getInt("id");
            String name = rs.getString("name");
            int vendorId = rs.getInt("vendorId");
            double sellingPrice = rs.getDouble("sellingPrice");

            item = new Item(id, name, vendorId, sellingPrice);
            item.setCategory(rs.getString("category"));
            item.setExpDate(rs.getString("expDate"));
            item.setPurchasePrice(rs.getDouble("purchasePrice"));
            item.setUnit(rs.getString("unit"));
            item.setQuantity(rs.getDouble("quantity"));

            itemList.add(item);
        }

        return itemList;
    }

    public boolean update(Item item) throws SQLException {
        String sql = "UPDATE distributor.item " +
                "SET name=?, vendorId=?, sellingPrice=?, category=?, expDate=?, purchasePrice=?, unit=?, quantity=?" +
                "WHERE id=" + item.getId() + ";";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,item.getName());
        ps.setInt(2, item.getVendorId());
        ps.setDouble(3, item.getSellingPrice());
        ps.setString(4, item.getCategory());
        ps.setString(5, item.getExpDate());
        ps.setDouble(6, item.getPurchasePrice());
        ps.setString(7, item.getUnit());
        ps.setDouble(8, item.getQuantity());

        return ps.executeUpdate() > 0;
    }
}
