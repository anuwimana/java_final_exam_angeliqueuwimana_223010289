package com.hms;


import com.db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    private Connection conn;

    public StaffDAO() {
        conn = DBConnection.getConnection();
    }

    public boolean addStaff(StaffModel s) {
        String sql = "INSERT INTO Staff (Name, Identifier, Status, Location, Contact, AssignedSince) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, s.getName());
            pst.setString(2, s.getIdentifier());
            pst.setString(3, s.getStatus());
            pst.setString(4, s.getLocation());
            pst.setString(5, s.getContact());
            pst.setTimestamp(6, new Timestamp(s.getAssignedSince().getTime()));
            pst.executeUpdate();
            return true;
        } catch(SQLException e) { e.printStackTrace(); return false; }
    }

    public List<StaffModel> getAllStaff() {
        List<StaffModel> list = new ArrayList<>();
        String sql = "SELECT * FROM Staff";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while(rs.next()) {
                list.add(new StaffModel(
                        rs.getInt("StaffID"),
                        rs.getString("Name"),
                        rs.getString("Identifier"),
                        rs.getString("Status"),
                        rs.getString("Location"),
                        rs.getString("Contact"),
                        rs.getTimestamp("AssignedSince")
                ));
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateStaff(StaffModel s) {
        String sql = "UPDATE Staff SET Name=?, Identifier=?, Status=?, Location=?, Contact=?, AssignedSince=? WHERE StaffID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, s.getName());
            pst.setString(2, s.getIdentifier());
            pst.setString(3, s.getStatus());
            pst.setString(4, s.getLocation());
            pst.setString(5, s.getContact());
            pst.setTimestamp(6, new Timestamp(s.getAssignedSince().getTime()));
            pst.setInt(7, s.getStaffID());
            pst.executeUpdate();
            return true;
        } catch(SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteStaff(int staffID) {
        String sql = "DELETE FROM Staff WHERE StaffID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, staffID);
            pst.executeUpdate();
            return true;
        } catch(SQLException e) { e.printStackTrace(); return false; }
    }
}
