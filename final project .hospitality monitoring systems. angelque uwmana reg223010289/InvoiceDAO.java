package com.hms;


import com.db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {
    private Connection conn;

    public InvoiceDAO() {
        conn = DBConnection.getConnection();
    }

    public boolean addInvoice(InvoiceModel i) {
        String sql = "INSERT INTO Invoice (Amount, Date, Type, Reference, Status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDouble(1, i.getAmount());
            pst.setTimestamp(2, new Timestamp(i.getDate().getTime()));
            pst.setString(3, i.getType());
            pst.setString(4, i.getReference());
            pst.setString(5, i.getStatus());
            pst.executeUpdate();
            return true;
        } catch(SQLException e) { e.printStackTrace(); return false; }
    }

    public List<InvoiceModel> getAllInvoices() {
        List<InvoiceModel> list = new ArrayList<>();
        String sql = "SELECT * FROM Invoice";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while(rs.next()) {
                list.add(new InvoiceModel(
                        rs.getInt("InvoiceID"),
                        rs.getDouble("Amount"),
                        rs.getTimestamp("Date"),
                        rs.getString("Type"),
                        rs.getString("Reference"),
                        rs.getString("Status")
                ));
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateInvoice(InvoiceModel i) {
        String sql = "UPDATE Invoice SET Amount=?, Date=?, Type=?, Reference=?, Status=? WHERE InvoiceID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDouble(1, i.getAmount());
            pst.setTimestamp(2, new Timestamp(i.getDate().getTime()));
            pst.setString(3, i.getType());
            pst.setString(4, i.getReference());
            pst.setString(5, i.getStatus());
            pst.setInt(6, i.getInvoiceID());
            pst.executeUpdate();
            return true;
        } catch(SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteInvoice(int invoiceID) {
        String sql = "DELETE FROM Invoice WHERE InvoiceID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, invoiceID);
            pst.executeUpdate();
            return true;
        } catch(SQLException e) { e.printStackTrace(); return false; }
    }
}
