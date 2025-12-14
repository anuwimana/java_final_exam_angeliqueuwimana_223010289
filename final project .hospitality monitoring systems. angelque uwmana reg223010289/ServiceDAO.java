package com.hms;


import com.db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {
    private Connection conn;

    public ServiceDAO() {
        conn = DBConnection.getConnection();
    }

    public boolean addService(ServiceModel s) {
        String sql = "INSERT INTO Service (Name, Description, Category, PriceOrValue, Status, CreatedAt) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, s.getName());
            pst.setString(2, s.getDescription());
            pst.setString(3, s.getCategory());
            pst.setDouble(4, s.getPriceOrValue());
            pst.setString(5, s.getStatus());
            pst.setTimestamp(6, new Timestamp(s.getCreatedAt().getTime()));
            pst.executeUpdate();
            return true;
        } catch(SQLException e) { e.printStackTrace(); return false; }
    }

    public List<ServiceModel> getAllServices() {
        List<ServiceModel> list = new ArrayList<>();
        String sql = "SELECT * FROM Service";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while(rs.next()) {
                list.add(new ServiceModel(
                        rs.getInt("ServiceID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("Category"),
                        rs.getDouble("PriceOrValue"),
                        rs.getString("Status"),
                        rs.getTimestamp("CreatedAt")
                ));
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateService(ServiceModel s) {
        String sql = "UPDATE Service SET Name=?, Description=?, Category=?, PriceOrValue=?, Status=?, CreatedAt=? WHERE ServiceID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, s.getName());
            pst.setString(2, s.getDescription());
            pst.setString(3, s.getCategory());
            pst.setDouble(4, s.getPriceOrValue());
            pst.setString(5, s.getStatus());
            pst.setTimestamp(6, new Timestamp(s.getCreatedAt().getTime()));
            pst.setInt(7, s.getServiceID());
            pst.executeUpdate();
            return true;
        } catch(SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteService(int serviceID) {
        String sql = "DELETE FROM Service WHERE ServiceID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, serviceID);
            pst.executeUpdate();
            return true;
        } catch(SQLException e) { e.printStackTrace(); return false; }
    }}
