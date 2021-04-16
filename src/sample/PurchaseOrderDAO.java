package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderDAO {

    Connection conn = new DBConnection().getConnection();

    public List<Item> getSearchVendor(String search) throws SQLException {
        String sql = "SELECT item.id, name, vendorId, sellingPrice, category, expDate, purchasePrice, unit, quantity " +
                "FROM distributor.item " +
                "JOIN distributor.vendor ON distributor.vendor.id=distributor.item.vendorId " +
                "WHERE vendor.fname='" + search + "';";
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

    public List<PurchaseOrder> getPurchaseOrder(int purchaseId) throws SQLException {
        String sql = "SELECT * FROM distributor.purchaseOrder WHERE purchaseId = '" + purchaseId + "';";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<PurchaseOrder> orderList = new ArrayList<>();
        ItemDAO itemDAO = new ItemDAO();

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
