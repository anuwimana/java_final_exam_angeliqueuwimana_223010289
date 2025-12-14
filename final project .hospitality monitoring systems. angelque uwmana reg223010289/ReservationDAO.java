package com.hms;


import com.db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private Connection conn;

    public ReservationDAO() {
        conn = DBConnection.getConnection();
    }

    public boolean addReservation(ReservationModel r) {
        String sql = "INSERT INTO Reservation (OrderNumber, Date, Status, TotalAmount, PaymentMethod, Notes, GuestID, RoomID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, r.getOrderNumber());
            pst.setTimestamp(2, new Timestamp(r.getDate().getTime()));
            pst.setString(3, r.getStatus());
            pst.setDouble(4, r.getTotalAmount());
            pst.setString(5, r.getPaymentMethod());
            pst.setString(6, r.getNotes());
            pst.setInt(7, r.getGuestID());
            pst.setInt(8, r.getRoomID());
            pst.executeUpdate();
            return true;
        } catch(SQLException e) { e.printStackTrace(); return false; }
    }

    public List<ReservationModel> getAllReservations() {
        List<ReservationModel> list = new ArrayList<>();
        String sql = "SELECT * FROM Reservation";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while(rs.next()) {
                list.add(new ReservationModel(
                        rs.getInt("ReservationID"),
                        rs.getString("OrderNumber"),
                        rs.getTimestamp("Date"),
                        rs.getString("Status"),
                        rs.getDouble("TotalAmount"),
                        rs.getString("PaymentMethod"),
                        rs.getString("Notes"),
                        rs.getInt("GuestID"),
                        rs.getInt("RoomID")
                ));
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateReservation(ReservationModel r) {
        String sql = "UPDATE Reservation SET OrderNumber=?, Date=?, Status=?, TotalAmount=?, PaymentMethod=?, Notes=?, GuestID=?, RoomID=? WHERE ReservationID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, r.getOrderNumber());
            pst.setTimestamp(2, new Timestamp(r.getDate().getTime()));
            pst.setString(3, r.getStatus());
            pst.setDouble(4, r.getTotalAmount());
            pst.setString(5, r.getPaymentMethod());
            pst.setString(6, r.getNotes());
            pst.setInt(7, r.getGuestID());
            pst.setInt(8, r.getRoomID());
            pst.setInt(9, r.getReservationID());
            pst.executeUpdate();
            return true;
        } catch(SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteReservation(int reservationID) {
        String sql = "DELETE FROM Reservation WHERE ReservationID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, reservationID);
            pst.executeUpdate();
            return true;
        } catch(SQLException e) { e.printStackTrace(); return false; }
    }
}
