package com.hms;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import com.hms.GuestDAO;
import com.hms.GuestModel;

public class Guest extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtUsername, txtPassword, txtEmail, txtFullName, txtRole;
    private JButton btnAdd, btnUpdate, btnDelete, btnLoad;
    private GuestDAO guestDAO;

    public Guest() {
        setTitle("Guest Monitoring");
        setSize(800, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        guestDAO = new GuestDAO();

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        txtUsername = new JTextField();
        txtPassword = new JTextField();
        txtEmail = new JTextField();
        txtFullName = new JTextField();
        txtRole = new JTextField();

        inputPanel.add(new JLabel("Username"));
        inputPanel.add(new JLabel("Password"));
        inputPanel.add(new JLabel("Email"));
        inputPanel.add(new JLabel("Full Name"));
        inputPanel.add(new JLabel("Role"));

        inputPanel.add(txtUsername);
        inputPanel.add(txtPassword);
        inputPanel.add(txtEmail);
        inputPanel.add(txtFullName);
        inputPanel.add(txtRole);

        add(inputPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Username", "Password", "Email", "Full Name", "Role"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnLoad = new JButton("Load");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnLoad);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load Button
        btnLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadGuests();
            }
        });

        // Add Button
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GuestModel g = new GuestModel(
                        0,
                        txtUsername.getText(),
                        txtPassword.getText(),
                        txtEmail.getText(),
                        txtFullName.getText(),
                        txtRole.getText()
                );
                if (guestDAO.addGuest(g)) {
                    loadGuests();
                }
            }
        });

        // Update Button
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    GuestModel g = new GuestModel(
                            id,
                            txtUsername.getText(),
                            txtPassword.getText(),
                            txtEmail.getText(),
                            txtFullName.getText(),
                            txtRole.getText()
                    );
                    if (guestDAO.updateGuest(g)) {
                        loadGuests();
                    }
                } else {
                    JOptionPane.showMessageDialog(Guest.this, "Select a guest to update");
                }
            }
        });

        // Delete Button
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    if (guestDAO.deleteGuest(id)) {
                        loadGuests();
                    }
                } else {
                    JOptionPane.showMessageDialog(Guest.this, "Select a guest to delete");
                }
            }
        });

        // Table click
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                txtUsername.setText(tableModel.getValueAt(r, 1).toString());
                txtPassword.setText(tableModel.getValueAt(r, 2).toString());
                txtEmail.setText(tableModel.getValueAt(r, 3).toString());
                txtFullName.setText(tableModel.getValueAt(r, 4).toString());
                txtRole.setText(tableModel.getValueAt(r, 5).toString());
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
        loadGuests();
    }

    private void loadGuests() {
        tableModel.setRowCount(0);
        List<GuestModel> list = guestDAO.getAllGuests();
        for (GuestModel g : list) {
            tableModel.addRow(new Object[]{
                    g.getGuestID(),
                    g.getUsername(),
                    g.getPasswordHash(),
                    g.getEmail(),
                    g.getFullName(),
                    g.getRole()
            });
        }
    }
}
