package com.hms;

import com.db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class LoginForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginForm() {
        setTitle("Hospitality Management System - Login");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login");
        add(loginButton);
        add(new JLabel("")); 

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                login();
            }
        });

        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM staff WHERE username=? AND password=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful! Welcome " + rs.getString("Name"));
                this.dispose();
                showDashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void showDashboard() {
        final JFrame dashboard = new JFrame("HMS Dashboard");
        dashboard.setSize(600, 300);
        dashboard.setLocationRelativeTo(null);
        dashboard.setLayout(new GridLayout(2, 3, 10, 10));

        Map<String, Runnable> actions = new LinkedHashMap<>();
        actions.put("Guest", new Runnable() {
            @Override
            public void run() {
                new Guest();
            }
        });
        actions.put("Invoice", new Runnable() {
            
            public void run() {
                new Invoice();
            }
        });
        actions.put("Reservation", new Runnable() {
           
            public void run() {
                new Reservation();
            }
        });
        actions.put("Room", new Runnable() {
            
            public void run() {
                new Room();
            }
        });
        actions.put("Service", new Runnable() {
            
            public void run() {
                new Service();
            }
        });
        actions.put("Staff", new Runnable() {
            
            public void run() {
                new Staff();
            }
        });

        for (Map.Entry<String, Runnable> entry : actions.entrySet()) {
            final String table = entry.getKey();
            final Runnable action = entry.getValue();

            JButton btn = new JButton(table);

            btn.addActionListener(new ActionListener() {
                
                public void actionPerformed(ActionEvent evt) {
                    action.run(); 
                }
            });

            dashboard.add(btn);
        }

        dashboard.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginForm();
    }

}