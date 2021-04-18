package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>PurchaseOrderDAO</h1>
 * <p>
 *     This class is the Database Access Object class for Purchase Order. It retrieves the items list for the searched
 *     vendor, adds a purchase order to the database, gets a list of all purchase ID's, and returns a purchase order
 *     upon request.
 * </p>
 *
 * @author Arif Hasan
 * @version 1.0
 * @since 03/29/21
 */
public class PurchaseOrderDAO {

    Connection conn = new DBConnection().getConnection();

    /**
     * Searches the database for a provided vendor and returns a list of their items, if found
     *
     * @param search entry from the search text field
     * @return List of Item objects available from a given vendor
     * @throws SQLException
     */
    public List<Item> getSearchVendor(String search) throws SQLException {
        String sql = "SELECT item.id, name, vendorId, sellingPrice, category, expDate, purchasePrice, unit, quantity " +
                "FROM distributor.item " +
                "JOIN distributor.vendor ON distributor.vendor.id=distributor.item.vendorId " +
                "WHERE vendor.fname='" + search + "' and expDate >= now();";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Item> itemList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int vendorId = rs.getInt("vendorId");
            Double sellingPrice = rs.getDouble("sellingPrice");
            Double purchasePrice  = rs.getDouble("purchasePrice");

            Item item = new Item(id, name, vendorId, sellingPrice);
            item.setPurchasePrice(purchasePrice);
            itemList.add(item);
        }

        return  itemList;
    }

    /**
     * Adds the provided purchase order to the database and returns a boolean based on whether or not it was successful
     *
     * @param orderList list of purchase order items
     * @param vendor name of the vendor
     * @param purchaseId ID of the current purchase order
     * @return <code>true</code> if the purchase was successfully inserted; otherwise <code>false</code>
     * @throws SQLException
     */
    public boolean insertPurchaseOrder(List<PurchaseOrder> orderList, String vendor, int purchaseId)
            throws SQLException {
        String sql = "INSERT INTO distributor.purchaseOrder" +
                "(purchaseId, vendorName, itemId, needBy, quantity, subtotal) " +
                "VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        for (PurchaseOrder order : orderList) {
            ps.setInt(1, purchaseId);
            ps.setString(2, vendor);
            ps.setInt(3, order.item.getId());
            ps.setString(4, order.getNeedBy());
            ps.setDouble(5, order.getQuantity());
            ps.setString(6, order.getSubtotal());

            ps.addBatch();
        }

        double total = orderList.stream().mapToDouble(order -> Double.parseDouble(order.getSubtotal())).sum();
        String sql2 = "UPDATE distributor.vendor SET balance = balance + " + total + " WHERE fname = '" + vendor + "';";
        Statement st = conn.createStatement();

        return ps.executeBatch().length > 0;
    }

    /**
     * This method is used to retrieve a list of available purchase orders for a given vendor.
     *
     * @param name name of vendor
     * @return List of integers corresponding to the searched vendor's purchase orders.
     * @throws SQLException
     */
    public List<Integer> getPurchaseIds(String name) throws SQLException {
        String sql = "SELECT DISTINCT purchaseId FROM distributor.purchaseOrder";

        if (!name.isBlank())
            sql +=  " WHERE vendorName = '" + name + "';";

        List<Integer> purchaseIdList = new ArrayList<>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            purchaseIdList.add(rs.getInt(1));
        }

        return purchaseIdList;
    }

    /**
     * This method is used to retrieve an existing purchase order using a purchase ID.
     *
     * @param purchaseId ID of a purchase order
     * @return List of purchase order items
     * @throws SQLException
     */
    public List<PurchaseOrder> getPurchaseOrder(int purchaseId) throws SQLException {
        String sql = "SELECT * FROM distributor.purchaseOrder WHERE purchaseId = '" + purchaseId + "';";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<PurchaseOrder> orderList = new ArrayList<>();
        ItemDAO itemDAO = new ItemDAO(); // Using ItemDAO to get an item using itemId

        while (rs.next()) {
            Item item = itemDAO.getSearchItem(rs.getInt("itemId")+"");
            String needBy = rs.getString("needBy");
            double quantity = rs.getDouble("quantity");

            PurchaseOrder order = new PurchaseOrder(item, needBy, quantity);
            orderList.add(order);
        }

        return orderList;
    }
}
