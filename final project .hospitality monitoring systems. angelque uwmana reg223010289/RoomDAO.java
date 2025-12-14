package com.hms;


import com.db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    private Connection conn;

    public RoomDAO() {
        conn = DBConnection.getConnection();
    }

    public boolean addRoom(RoomModel r) {
        String sql = "INSERT INTO Room (Name, Description, Category, PriceOrValue, Status, CreatedAt) VALUES (?, ?, ?, ?, ?, NOW())";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, r.getName());
            pst.setString(2, r.getDescription());
            pst.setString(3, r.getCategory());
            pst.setDouble(4, r.getPriceOrValue());
            pst.setString(5, r.getStatus());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<RoomModel> getAllRooms() {
        List<RoomModel> list = new ArrayList<>();
        String sql = "SELECT * FROM Room";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while(rs.next()) {
                list.add(new RoomModel(
                        rs.getInt("RoomID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("Category"),
                        rs.getDouble("PriceOrValue"),
                        rs.getString("Status")
                ));
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateRoom(RoomModel r) {
        String sql = "UPDATE Room SET Name=?, Description=?, Category=?, PriceOrValue=?, Status=? WHERE RoomID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, r.getName());
            pst.setString(2, r.getDescription());
            pst.setString(3, r.getCategory());
            pst.setDouble(4, r.getPriceOrValue());
            pst.setString(5, r.getStatus());
            pst.setInt(6, r.getRoomID());
            pst.executeUpdate();
            return true;
        } catch(SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteRoom(int roomID) {
        String sql = "DELETE FROM Room WHERE RoomID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, roomID);
            pst.executeUpdate();
            return true;
        } catch(SQLException e) { e.printStackTrace(); return false; }
    }
}









