package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the Database Access Object class for Item Profile. This class is responsible for handling the database
 * related requests from the ItemProfileController class. This acts as the Model of the Model of MVC architecture style.
 * Some of the functions include inserting an item, updating an item, and retrieving item(s).
 *
 * @author Arif hasan
 * @version 1.0
 * @since 03/19/21
 */
public class ItemDAO {

    private Connection conn = new DBConnection().getConnection();

    /**
     * Method for storing an user entered item into the database.
     *
     * @param item provided item object to be added to the database.
     * @return <code>true</code> if item as successfully inserted into the database; otherwise, <code>false</code>.
     * @throws SQLException
     */
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

    /**
     * This method is used for checking the database whether an item of the same name already exists under the same
     * vendor ID. The appropriate boolean value is returned.
     *
     * @param name item name
     * @param vendorId vendor ID
     * @return <code>true</code> if item already exists; otherwise, <code>false</code>.
     * @throws SQLException
     */
    public boolean isDuplicate(String name, int vendorId) throws SQLException {
        String sql = "SELECT * FROM distributor.item WHERE name='" + name +"' AND vendorId='" + vendorId + "';";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        return rs.next();
    }

    /**
     * Retrieves a list of all the vendor ID's that exists in the database.
     *
     * @return List of all the ID's
     * @throws SQLException
     */
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

    /**
     * Searches the database for the given ID, item name, or the item's expiration date.
     *
     * @param search search key that may be an ID, name, or expiration date.
     * @return An item object containing all necessary data if the item is found.
     * @throws SQLException
     */
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

    /**
     * This method reads all the items from the database and returns them as a list of Item objects.
     *
     * @return List of all the items that exist in the database
     * @throws SQLException
     */
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

    /**
     * Updates the provided item based on the item ID using all the properties of an item object.
     *
     * @param item corresponding item object to be updated
     * @return <code>true</code> if update was successful; otherwise, <code>false</code>.
     * @throws SQLException
     */
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

    /**
     * This method is used for deleting an item that does not have associated purchase order or customer invoice. It
     * returns the appropriate boolean value.
     *
     * @param id
     * @return <code>true</code> if item was deleted successfully; otherwise, <code>false</code>.
     * @throws SQLException
     */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE " +
                "FROM distributor.item " +
                "WHERE id=? AND id NOT IN (" +
                "SELECT distinct purchaseOrder.itemId " +
                "FROM distributor.purchaseOrder" +
                ");";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        return ps.executeUpdate() > 0;
    }
}
