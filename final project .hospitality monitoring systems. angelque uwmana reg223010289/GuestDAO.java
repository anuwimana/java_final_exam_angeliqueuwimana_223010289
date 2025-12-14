package com.hms;


import com.db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestDAO {
    private Connection conn;

    public GuestDAO() {
        conn = DBConnection.getConnection();
    }

    public boolean addGuest(GuestModel g) {
        String sql = "INSERT INTO Guest (Username, PasswordHash, Email, FullName, Role, CreatedAt) VALUES (?, ?, ?, ?, ?, NOW())";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, g.getUsername());
            pst.setString(2, g.getPasswordHash());
            pst.setString(3, g.getEmail());
            pst.setString(4, g.getFullName());
            pst.setString(5, g.getRole());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<GuestModel> getAllGuests() {
        List<GuestModel> list = new ArrayList<>();
        String sql = "SELECT * FROM Guest";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new GuestModel(
                        rs.getInt("GuestID"),
                        rs.getString("Username"),
                        rs.getString("PasswordHash"),
                        rs.getString("Email"),
                        rs.getString("FullName"),
                        rs.getString("Role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateGuest(GuestModel g) {
        String sql = "UPDATE Guest SET Username=?, PasswordHash=?, Email=?, FullName=?, Role=? WHERE GuestID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, g.getUsername());
            pst.setString(2, g.getPasswordHash());
            pst.setString(3, g.getEmail());
            pst.setString(4, g.getFullName());
            pst.setString(5, g.getRole());
            pst.setInt(6, g.getGuestID());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteGuest(int guestID) {
        String sql = "DELETE FROM Guest WHERE GuestID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, guestID);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
                      
